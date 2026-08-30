/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arstechnica.adapter.bluemap522;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;

import java.util.IdentityHashMap;
import java.util.Map;

/** Classloader-local compiled data keyed by one BlueMap resource pack. */
final class RendererDataRegistry {

    private static final Map<ResourcePack, Data> DATA = new IdentityHashMap<>();

    private RendererDataRegistry() {
    }

    static synchronized void install(
            ResourcePack pack,
            InstalledGeoModel relay,
            InstalledGeoModel turret,
            VariantRendererCatalog variants
    ) {
        DATA.put(pack, new Data(relay, turret, variants));
    }

    static synchronized Data get(ResourcePack pack) {
        return DATA.get(pack);
    }

    record Data(
            InstalledGeoModel relay,
            InstalledGeoModel turret,
            VariantRendererCatalog variants
    ) {
    }
}
