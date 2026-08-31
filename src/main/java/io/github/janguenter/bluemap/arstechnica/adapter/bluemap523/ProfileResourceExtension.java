/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap523;

import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.ResourcePath;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.Key;
import io.github.janguenter.bluemap.arstechnica.activation.AddonRuntime;
import io.github.janguenter.bluemap.arstechnica.profile.ArsTechnica276Profile;
import io.github.janguenter.bluemap.arstechnica.profile.ArsTechnica276Profile.ResourcePin;
import io.github.janguenter.bluemap.arstechnica.profile.ArtifactPin;
import io.github.janguenter.bluemap.arstechnica.profile.ExactArtifactDetector;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoCompiler;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Exact admission, installed GEO compilation, and target-only route installation. */
final class ProfileResourceExtension implements ResourcePackExtension {

    private static final int MAX_ROOTS = 4_096;
    private static final String RELAY_GEO =
            "assets/ars_nouveau/geo/source_relay.geo.json";
    private static final String TURRET_GEO =
            "assets/ars_nouveau/geo/basic_spell_turret.geo.json";
    private static final ResourcePath<Model> ARCANE_SHAFT = new ResourcePath<>(
            "ars_technica:block/arcane_shaft_half"
    );
    private static final Key ARCANE_AXIS = Key.parse("ars_technica:block/arcane_axis");

    private final ResourcePack resourcePack;
    private final BlockRendererType renderer;
    private final AddonRuntime runtime;
    private InstalledGeoModel relay;
    private InstalledGeoModel turret;

    ProfileResourceExtension(
            ResourcePack resourcePack,
            BlockRendererType renderer,
            AddonRuntime runtime
    ) {
        this.resourcePack = resourcePack;
        this.renderer = renderer;
        this.runtime = runtime;
    }

    @Override
    public void loadResources(Iterable<Path> roots) {
        relay = null;
        turret = null;
        if (Boolean.getBoolean("bluemap.arstechnica.disabled")) {
            runtime.inactive("operator-disabled");
            return;
        }
        List<Path> candidates = boundedRoots(roots);
        if (candidates == null || !ExactArtifactDetector.matchesAll(
                candidates, ArsTechnica276Profile.ARTIFACTS
        )) {
            runtime.inactive("exact-artifact-missing-or-duplicate");
            return;
        }
        Map<ArtifactPin, Path> artifacts = new HashMap<>();
        for (ArtifactPin pin : ArsTechnica276Profile.ARTIFACTS) {
            Path artifact = ExactArtifactDetector.findExact(candidates, pin).orElse(null);
            if (artifact == null) {
                runtime.inactive("exact-artifact-unavailable");
                return;
            }
            artifacts.put(pin, artifact);
        }
        try {
            Map<String, byte[]> resources = new HashMap<>();
            for (ResourcePin pin : ArsTechnica276Profile.RESOURCES) {
                resources.put(pin.path(), readPinned(artifacts.get(pin.artifact()), pin));
            }
            relay = InstalledGeoCompiler.compile(
                    resources.get(RELAY_GEO), ArsTechnica276Profile.SOURCE_RELAY_GEO
            );
            turret = InstalledGeoCompiler.compile(
                    resources.get(TURRET_GEO),
                    ArsTechnica276Profile.TRANSMUTATION_TURRET_GEO
            );
        } catch (IOException | RuntimeException exception) {
            relay = null;
            turret = null;
            runtime.inactive("resource-compile-" + exception.getClass().getSimpleName());
        }
    }

    @Override
    public Set<Key> collectUsedTextureKeys() {
        return Set.of(
                ARCANE_AXIS,
                InstalledGeoMeshEmitter.RELAY_TEXTURE,
                InstalledGeoMeshEmitter.TURRET_TEXTURE
        );
    }

    @Override
    public void bake() {
        if (relay == null || turret == null) {
            return;
        }
        if (resourcePack.getModels().get(ARCANE_SHAFT) == null
                || resourcePack.getTextures().get(ARCANE_AXIS) == null
                || resourcePack.getTextures().get(
                        InstalledGeoMeshEmitter.RELAY_TEXTURE
                ) == null
                || resourcePack.getTextures().get(
                        InstalledGeoMeshEmitter.TURRET_TEXTURE
                ) == null) {
            runtime.inactive("installed-render-resource-missing");
            return;
        }
        try {
            VariantRendererCatalog variants = VariantRendererCatalog.wrap(
                    resourcePack, renderer
            );
            RendererDataRegistry.install(resourcePack, relay, turret, variants);
            runtime.activate();
            System.out.println("BlueMap Ars Technica add-on active: wrapped "
                    + variants.size() + " installed variants across 32 legal states; "
                    + "source motor shaft, precise relay, and transmutation turret "
                    + "use deterministic static poses.");
        } catch (RuntimeException exception) {
            runtime.inactive("route-install-" + exception.getClass().getSimpleName());
        }
    }

    private static List<Path> boundedRoots(Iterable<Path> roots) {
        List<Path> result = new ArrayList<>();
        for (Path root : roots) {
            if (Thread.currentThread().isInterrupted() || result.size() >= MAX_ROOTS) {
                return null;
            }
            result.add(root);
        }
        return List.copyOf(result);
    }

    private static byte[] readPinned(Path artifact, ResourcePin pin) throws IOException {
        if (artifact == null) {
            throw new IOException("installed artifact path is missing");
        }
        try (ZipFile zip = new ZipFile(artifact.toFile())) {
            ZipEntry entry = zip.getEntry(pin.path());
            if (entry == null || entry.isDirectory() || entry.getSize() != pin.size()) {
                throw new IOException("installed resource size changed: " + pin.path());
            }
            byte[] raw;
            try (InputStream input = zip.getInputStream(entry)) {
                raw = input.readNBytes(pin.size() + 1);
            }
            if (raw.length != pin.size() || !pin.sha256().equals(digest(raw))) {
                throw new IOException("installed resource bytes changed: " + pin.path());
            }
            return raw;
        }
    }

    private static String digest(byte[] raw) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(raw)
            );
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
    }
}
