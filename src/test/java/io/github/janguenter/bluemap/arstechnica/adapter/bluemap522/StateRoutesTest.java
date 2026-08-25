/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap522;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class StateRoutesTest {

    @Test
    void admitsExactlyThirtyTwoLegalStates() {
        int motor = 0;
        int relay = 0;
        int turret = 0;
        for (String facing : StateRoutes.FACINGS) {
            if (StateRoutes.sourceMotor(Map.of("facing", facing)) != null) {
                motor++;
            }
            for (String waterlogged : new String[]{"false", "true"}) {
                if (StateRoutes.preciseRelay(Map.of("waterlogged", waterlogged))) {
                    relay = 2;
                }
                for (String triggered : new String[]{"false", "true"}) {
                    Map<String, String> properties = new HashMap<>();
                    properties.put("facing", facing);
                    properties.put("triggered", triggered);
                    properties.put("waterlogged", waterlogged);
                    if (StateRoutes.transmutationTurret(properties) != null) {
                        turret++;
                    }
                }
            }
        }

        assertEquals(6, motor);
        assertEquals(2, relay);
        assertEquals(24, turret);
        assertEquals(32, motor + relay + turret);
    }

    @Test
    void rejectsUnknownOrPartialStates() {
        assertNull(StateRoutes.sourceMotor(Map.of("facing", "sideways")));
        assertFalse(StateRoutes.preciseRelay(Map.of("waterlogged", "maybe")));
        assertNull(StateRoutes.transmutationTurret(Map.of(
                "facing", "north", "triggered", "false"
        )));
        assertTrue(StateRoutes.preciseRelay(Map.of("waterlogged", "false")));
    }
}
