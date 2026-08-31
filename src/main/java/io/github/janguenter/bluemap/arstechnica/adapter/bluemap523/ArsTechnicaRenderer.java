/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap523;

import de.bluecolored.bluemap.core.map.TextureGallery;
import de.bluecolored.bluemap.core.map.hires.MaxCapacityReachedException;
import de.bluecolored.bluemap.core.map.hires.RenderSettings;
import de.bluecolored.bluemap.core.map.hires.TileModelView;
import de.bluecolored.bluemap.core.map.hires.block.BlockRenderer;
import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.map.hires.block.ResourceModelRenderer;
import de.bluecolored.bluemap.core.resources.ResourcePath;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.util.math.Color;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import io.github.janguenter.bluemap.arstechnica.activation.AddonRuntime;

import java.util.IdentityHashMap;
import java.util.Map;

/** Restores three narrow Ars Technica BER omissions with deterministic static poses. */
final class ArsTechnicaRenderer implements BlockRenderer {

    private static final String SOURCE_MOTOR = "ars_technica:source_motor";
    private static final String PRECISE_RELAY = "ars_technica:precise_relay";
    private static final String TRANSMUTATION_TURRET =
            "ars_technica:transmutation_turret";
    private static final ResourcePath<Model> ARCANE_SHAFT = new ResourcePath<>(
            "ars_technica:block/arcane_shaft_half"
    );
    private static final ThreadLocal<Boolean> STOCK_FALLBACK =
            ThreadLocal.withInitial(() -> Boolean.FALSE);

    private final ResourcePack resourcePack;
    private final TextureGallery textures;
    private final RenderSettings settings;
    private final AddonRuntime runtime;
    private final RendererDataRegistry.Data data;
    private final InstalledGeoMeshEmitter geo;
    private final ResourceModelRenderer partials;
    private final Map<BlockRendererType, BlockRenderer> stockRenderers =
            new IdentityHashMap<>();

    ArsTechnicaRenderer(
            ResourcePack resourcePack,
            TextureGallery textures,
            RenderSettings settings,
            AddonRuntime runtime
    ) {
        this.resourcePack = resourcePack;
        this.textures = textures;
        this.settings = settings;
        this.runtime = runtime;
        data = RendererDataRegistry.get(resourcePack);
        geo = new InstalledGeoMeshEmitter(resourcePack, textures, settings);
        partials = new ResourceModelRenderer(resourcePack, textures, settings);
    }

    @Override
    public void render(
            BlockNeighborhood block,
            Variant variant,
            TileModelView target,
            Color mapColor
    ) {
        int start = target.getStart();
        try {
            if (!renderOwned(block, variant, target, mapColor)) {
                stock(block, variant, target, mapColor);
            }
        } catch (MaxCapacityReachedException exception) {
            throw exception;
        } catch (RuntimeException exception) {
            reset(target, start);
            runtime.inactive("renderer-" + exception.getClass().getSimpleName());
            stockSafely(block, variant, target, mapColor, start);
        }
    }

    private boolean renderOwned(
            BlockNeighborhood block,
            Variant variant,
            TileModelView target,
            Color mapColor
    ) {
        if (!runtime.active() || data == null) {
            return false;
        }
        String blockId = block.getBlockState().getId().getFormatted();
        Map<String, String> properties = block.getBlockState().getProperties();
        if (SOURCE_MOTOR.equals(blockId)) {
            String facing = StateRoutes.sourceMotor(properties);
            return facing != null && renderSourceMotor(
                    facing, block, variant, target, mapColor
            );
        }
        if (PRECISE_RELAY.equals(blockId)) {
            return StateRoutes.preciseRelay(properties)
                    && geo.emit(
                            data.relay(), InstalledGeoMeshEmitter.RELAY_TEXTURE, null,
                            block, target, mapColor
                    );
        }
        if (TRANSMUTATION_TURRET.equals(blockId)) {
            String facing = StateRoutes.transmutationTurret(properties);
            return facing != null && geo.emit(
                    data.turret(), InstalledGeoMeshEmitter.TURRET_TEXTURE, facing,
                    block, target, mapColor
            );
        }
        return false;
    }

    private boolean renderSourceMotor(
            String facing,
            BlockNeighborhood block,
            Variant variant,
            TileModelView target,
            Color mapColor
    ) {
        stock(block, variant, target, mapColor);
        int partialStart = target.getTileModel().size();
        float[] rotation = StateRoutes.sourceMotorRotation(facing);
        partials.render(
                block,
                new Variant(ARCANE_SHAFT, rotation[0], rotation[1], 0F),
                target.initialize(),
                new Color().set(0F, 0F, 0F, 0F, true)
        );
        if (target.getTileModel().size() == partialStart) {
            target.initialize(partialStart);
        }
        return true;
    }

    private void stock(
            BlockNeighborhood block,
            Variant variant,
            TileModelView target,
            Color mapColor
    ) {
        if (STOCK_FALLBACK.get()) {
            return;
        }
        STOCK_FALLBACK.set(Boolean.TRUE);
        try {
            BlockRendererType type = data == null
                    ? BlockRendererType.DEFAULT : data.variants().original(variant);
            stockRenderers.computeIfAbsent(
                    type, found -> found.create(resourcePack, textures, settings)
            ).render(block, variant, target, mapColor);
        } finally {
            STOCK_FALLBACK.set(Boolean.FALSE);
        }
    }

    private void stockSafely(
            BlockNeighborhood block,
            Variant variant,
            TileModelView target,
            Color mapColor,
            int start
    ) {
        try {
            stock(block, variant, target, mapColor);
        } catch (RuntimeException exception) {
            reset(target, start);
            runtime.inactive("stock-fallback-" + exception.getClass().getSimpleName());
        }
    }

    private static void reset(TileModelView target, int start) {
        target.getTileModel().reset(start);
        target.initialize(start);
    }
}
