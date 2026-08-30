/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janguenter.bluemap.arstechnica.profile.ArsTechnica276Profile;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoCompiler;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class InstalledGeoCompilerTest {

    private static final String RELAY =
            "assets/ars_nouveau/geo/source_relay.geo.json";
    private static final String TURRET =
            "assets/ars_nouveau/geo/basic_spell_turret.geo.json";

    @Test
    void compilesBothExactInstalledMeshesDeterministically() throws IOException {
        InstalledGeoModel relay = InstalledGeoCompiler.compile(
                exactEntry(RELAY), ArsTechnica276Profile.SOURCE_RELAY_GEO
        );
        byte[] turretBytes = exactEntry(TURRET);
        InstalledGeoModel turret = InstalledGeoCompiler.compile(
                turretBytes, ArsTechnica276Profile.TRANSMUTATION_TURRET_GEO
        );

        assertEquals(102, relay.quads().size());
        assertEquals(84, turret.quads().size());
        assertEquals(turret, InstalledGeoCompiler.compile(
                turretBytes, ArsTechnica276Profile.TRANSMUTATION_TURRET_GEO
        ));
        assertTrue(relay.quads().stream().flatMap(quad -> java.util.stream.Stream.of(
                quad.first(), quad.second(), quad.third(), quad.fourth()
        )).allMatch(vertex -> finite(vertex.position())
                && Float.isFinite(vertex.u()) && Float.isFinite(vertex.v())));
    }

    @Test
    void rejectsChangedInstalledSchemaAndWrongContract() throws IOException {
        String geometry = new String(exactEntry(RELAY), StandardCharsets.UTF_8);
        byte[] changed = geometry.replace("\"1.12.0\"", "\"9.99.0\"")
                .getBytes(StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> InstalledGeoCompiler.compile(
                changed, ArsTechnica276Profile.SOURCE_RELAY_GEO
        ));
        assertThrows(IllegalArgumentException.class, () -> InstalledGeoCompiler.compile(
                exactEntry(RELAY), ArsTechnica276Profile.TRANSMUTATION_TURRET_GEO
        ));
    }

    private static byte[] exactEntry(String path) throws IOException {
        String property = System.getProperty("arsNouveauJar");
        Assumptions.assumeTrue(property != null && !property.isBlank());
        try (ZipFile zip = new ZipFile(Path.of(property).toFile())) {
            ZipEntry entry = zip.getEntry(path);
            Assumptions.assumeTrue(entry != null && !entry.isDirectory());
            return zip.getInputStream(entry).readAllBytes();
        }
    }

    private static boolean finite(InstalledGeoModel.Vec3 value) {
        return Double.isFinite(value.x())
                && Double.isFinite(value.y())
                && Double.isFinite(value.z());
    }
}
