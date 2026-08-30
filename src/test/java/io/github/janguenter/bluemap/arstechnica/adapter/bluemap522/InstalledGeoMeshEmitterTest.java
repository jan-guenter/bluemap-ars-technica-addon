/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap522;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel.Vec3;
import org.junit.jupiter.api.Test;

class InstalledGeoMeshEmitterTest {

    private static final double DELTA = 1.0E-9D;

    @Test
    void pointsTheInstalledSouthFacingModelAcrossSixFacings() {
        Vec3 point = new Vec3(0D, 0.5D, 1D);

        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "south"),
                0.5D, 0.5D, 1.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "north"),
                0.5D, 0.5D, -0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "west"),
                -0.5D, 0.5D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "east"),
                1.5D, 0.5D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "up"),
                0.5D, 1.5D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "down"),
                0.5D, -0.5D, 0.5D);
    }

    @Test
    void leavesTheFacinglessRelayInItsInstalledBasePose() {
        assertEquals(
                new Vec3(0.5D, 0.5D, 1.5D),
                InstalledGeoMeshEmitter.transformPoint(new Vec3(0D, 0.5D, 1D), null)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> InstalledGeoMeshEmitter.transformPoint(
                        new Vec3(0D, 0D, 0D), "sideways"
                )
        );
    }

    private static void assertVector(Vec3 actual, double x, double y, double z) {
        assertEquals(x, actual.x(), DELTA);
        assertEquals(y, actual.y(), DELTA);
        assertEquals(z, actual.z(), DELTA);
    }
}
