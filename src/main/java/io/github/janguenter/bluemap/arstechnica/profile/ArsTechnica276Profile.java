/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arstechnica.profile;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `ars-technica-2.7.6-mc1.21.1`. */
public final class ArsTechnica276Profile {

    public static final String PROFILE_ID = "ars-technica-2.7.6-mc1.21.1";
    public static final List<ArtifactPin> ARTIFACTS = List.of(
            new ArtifactPin(
                    "arsTechnica",
                    "ars_technica",
                    "2.7.6",
                    "ars_technica-1.21.1-2.7.6.jar",
                    3_691_171L,
                    "64b70f39f8c8ca38262c69e2b84a6494a8a11c8ef7e570d8136b1818d5d3159d"
            ),
            new ArtifactPin(
                    "arsNouveau",
                    "ars_nouveau",
                    "5.13.0",
                    "ars_nouveau-1.21.1-5.13.0.jar",
                    20_096_005L,
                    "90796df69bfb39b1a9c79edbfa01c2425e5b86aea47dc55ebdcbf30e88f47592"
            ),
            new ArtifactPin(
                    "create",
                    "create",
                    "6.0.10",
                    "create-1.21.1-6.0.10.jar",
                    19_123_767L,
                    "ef87fe5709f1ba1f5b8bb20a2925b5afb4669e178fd6d8bf10c167759eefe37a"
            )
    );

    private ArsTechnica276Profile() {
    }
}
