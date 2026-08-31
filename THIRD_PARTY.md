# Third-party material

| Project | Identity | License | Use | Redistributed |
| --- | --- | --- | --- | --- |
| BlueMap | Feature backport `5.22-feature.backport-5.23-stateless-java-web-server-46` / `7e07f4e74ec1e92a6ead9aa1e66054af3e133aac`; API `285c9a60eff3ac2b0cab308ce1058d1565be0971` | MIT | Compile-only internal API | No |
| BlueMap Add-on Adapter API | `0.1.0-alpha.2` / `e81f08bc4bfbf02d810ec8949a019130e2e61634` | MIT | Four exact source files compiled into this add-on | Source only |
| BlueMap Installed GEO Resource Models | `v0.1.0-alpha.1` / `c80a83eb6e2cb0bb05a69ace9716ef08b9db14f2` / source tree `8db87f933557d54c5ede2db70d94f67eaf44c30b` | MIT | Source-bundled installed GEO compiler and mesh records | Three production classes and sources |
| Ars Technica profile | `ars-technica-2.7.6-mc1.21.1` | See `provenance/upstreams.json` | Exact installed-artifact and resource evidence only | No |

The packaged `META-INF/LICENSE-BlueMap` preserves the license notice for the
API patterns used by this project. Candidate license identities and evidence
tiers are recorded per artifact in immutable packaged provenance.

The consumer gitlink and settings preflight pin the installed-GEO module's
exact commit and production-source tree. The source migration preserves the
frozen parity contract while removing the add-on's private compiler copy. No
standalone module JAR, module test, build file, or frozen oracle is packaged.
