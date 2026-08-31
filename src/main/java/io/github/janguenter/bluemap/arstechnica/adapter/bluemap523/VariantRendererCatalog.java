/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap523;

import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.BlockState;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.blockstate.Variant;
import de.bluecolored.bluemap.core.util.Key;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/** Keeps each target variant's original renderer for atomic stock fallback. */
final class VariantRendererCatalog {

    private static final List<Key> TARGETS = List.of(
            Key.parse("ars_technica:source_motor"),
            Key.parse("ars_technica:precise_relay"),
            Key.parse("ars_technica:transmutation_turret")
    );
    private final Map<Variant, BlockRendererType> originals;

    private VariantRendererCatalog(Map<Variant, BlockRendererType> originals) {
        this.originals = Collections.unmodifiableMap(originals);
    }

    static VariantRendererCatalog wrap(ResourcePack pack, BlockRendererType wrapper) {
        IdentityHashMap<Variant, BlockRendererType> originals = new IdentityHashMap<>();
        for (Key target : TARGETS) {
            BlockState state = pack.getBlockStates().get(target);
            if (state == null) {
                throw new IllegalArgumentException("installed target blockstate is missing");
            }
            int before = originals.size();
            state.forEach(variant -> {
                if (variant.getRenderer() != wrapper) {
                    originals.put(variant, variant.getRenderer());
                    variant.setRenderer(wrapper);
                }
            });
            if (originals.size() == before) {
                throw new IllegalArgumentException("installed target has no variants");
            }
        }
        return new VariantRendererCatalog(originals);
    }

    BlockRendererType original(Variant variant) {
        return originals.getOrDefault(variant, BlockRendererType.DEFAULT);
    }

    int size() {
        return originals.size();
    }
}
