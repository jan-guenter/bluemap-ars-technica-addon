/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap522;

import java.util.Map;
import java.util.Set;

/** Strict legal-state selectors for the three owned Ars Technica hosts. */
final class StateRoutes {

    static final Set<String> FACINGS = Set.of(
            "north", "south", "west", "east", "up", "down"
    );
    private static final Set<String> BOOLEANS = Set.of("true", "false");

    private StateRoutes() {
    }

    static String sourceMotor(Map<String, String> properties) {
        String facing = properties.get("facing");
        return properties.size() == 1 && FACINGS.contains(facing) ? facing : null;
    }

    static boolean preciseRelay(Map<String, String> properties) {
        return properties.size() == 1 && BOOLEANS.contains(properties.get("waterlogged"));
    }

    static String transmutationTurret(Map<String, String> properties) {
        String facing = properties.get("facing");
        return properties.size() == 3
                && FACINGS.contains(facing)
                && BOOLEANS.contains(properties.get("triggered"))
                && BOOLEANS.contains(properties.get("waterlogged"))
                ? facing : null;
    }

    static float[] sourceMotorRotation(String facing) {
        return switch (facing) {
            case "south" -> new float[]{0F, 0F};
            case "north" -> new float[]{0F, 180F};
            case "west" -> new float[]{0F, 90F};
            case "east" -> new float[]{0F, 270F};
            case "up" -> new float[]{90F, 0F};
            case "down" -> new float[]{270F, 0F};
            default -> throw new IllegalArgumentException("unknown source motor facing");
        };
    }
}
