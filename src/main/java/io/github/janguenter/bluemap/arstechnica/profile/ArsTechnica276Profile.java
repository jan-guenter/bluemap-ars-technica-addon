/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arstechnica.profile;

import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoCompiler;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `ars-technica-2.7.6-mc1.21.1`. */
public final class ArsTechnica276Profile {

    public static final String PROFILE_ID = "ars-technica-2.7.6-mc1.21.1";
    public static final ArtifactPin ARS_TECHNICA = new ArtifactPin(
            "arsTechnica",
            "ars_technica",
            "2.7.6",
            "ars_technica-1.21.1-2.7.6.jar",
            3_691_171L,
            "64b70f39f8c8ca38262c69e2b84a6494a8a11c8ef7e570d8136b1818d5d3159d"
    );
    public static final ArtifactPin ARS_NOUVEAU = new ArtifactPin(
            "arsNouveau",
            "ars_nouveau",
            "5.13.0",
            "ars_nouveau-1.21.1-5.13.0.jar",
            20_096_005L,
            "90796df69bfb39b1a9c79edbfa01c2425e5b86aea47dc55ebdcbf30e88f47592"
    );
    public static final ArtifactPin CREATE = new ArtifactPin(
            "create",
            "create",
            "6.0.10",
            "create-1.21.1-6.0.10.jar",
            19_123_767L,
            "ef87fe5709f1ba1f5b8bb20a2925b5afb4669e178fd6d8bf10c167759eefe37a"
    );
    public static final List<ArtifactPin> ARTIFACTS = List.of(
            ARS_TECHNICA, ARS_NOUVEAU, CREATE
    );
    public static final InstalledGeoCompiler.Contract SOURCE_RELAY_GEO =
            new InstalledGeoCompiler.Contract(9, 17, 102);
    public static final InstalledGeoCompiler.Contract TRANSMUTATION_TURRET_GEO =
            new InstalledGeoCompiler.Contract(5, 14, 84);
    public static final List<ResourcePin> RESOURCES = List.of(
            new ResourcePin(
                    ARS_TECHNICA,
                    "assets/ars_technica/models/block/arcane_shaft_half.json",
                    746,
                    "e325fbbbe888e9bd27a1caa9facf67a998dad302b3d91bd74d3951538a0d797c"
            ),
            new ResourcePin(
                    ARS_TECHNICA,
                    "assets/ars_technica/blockstates/source_motor.json",
                    601,
                    "fb407292e96dca2d04c187e8b5f676de397a9b2886b60d07c8d9d2b00d44a836"
            ),
            new ResourcePin(
                    ARS_TECHNICA,
                    "assets/ars_technica/blockstates/precise_relay.json",
                    78,
                    "f9a598040aa1bb02087113f0523023a953043ad151314c25a88677dc6f90c1f0"
            ),
            new ResourcePin(
                    ARS_TECHNICA,
                    "assets/ars_technica/blockstates/transmutation_turret.json",
                    98,
                    "494550c68d6134c390e260bb6a07eb1d00b116396cc87df12f1d3ee963f0dbb5"
            ),
            new ResourcePin(
                    ARS_TECHNICA,
                    "assets/ars_nouveau/textures/block/transmutation_turret.png",
                    421,
                    "ab79347fd88cb023875f9cdc298d986d51b393558925b5eb94785cef307c5c63"
            ),
            new ResourcePin(
                    ARS_NOUVEAU,
                    "assets/ars_nouveau/geo/source_relay.geo.json",
                    8_288,
                    "ba87df3739427158ff52daa550218ec24f15b1eae1c03c3accb878b58089d608"
            ),
            new ResourcePin(
                    ARS_NOUVEAU,
                    "assets/ars_nouveau/textures/block/source_relay.png",
                    392,
                    "0befa6d3def903cd8f70edcb1da8f07c38dc1b5eb665310d274799834da3ff4b"
            ),
            new ResourcePin(
                    ARS_NOUVEAU,
                    "assets/ars_nouveau/geo/basic_spell_turret.geo.json",
                    6_782,
                    "32fec71ebeb2690c59c7128f843348f76486418dac74376f05074a745924ac0d"
            )
    );

    private ArsTechnica276Profile() {
    }

    /** Exact installed resource admitted only with its containing artifact. */
    public record ResourcePin(
            ArtifactPin artifact,
            String path,
            int size,
            String sha256
    ) {
    }
}
