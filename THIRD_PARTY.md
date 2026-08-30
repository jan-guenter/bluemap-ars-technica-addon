# Third-party material

| Project | Identity | License | Use | Redistributed |
| --- | --- | --- | --- | --- |
| BlueMap | `5.22-agent.backport-5.22-mc1.21.1-2` / `9be321df995a1103808621d529eb72773e719d4d` | MIT | Compile-only internal API | No |
| BlueMap Installed GEO Resource Models | `v0.1.0-alpha.1` / `c80a83eb6e2cb0bb05a69ace9716ef08b9db14f2` / source tree `8db87f933557d54c5ede2db70d94f67eaf44c30b` | MIT | Source-bundled installed GEO compiler and mesh records | Three production classes and sources |
| Ars Technica profile | `ars-technica-2.7.6-mc1.21.1` | See `provenance/upstreams.json` | Exact installed-artifact and resource evidence only | No |

The packaged `META-INF/LICENSE-BlueMap` preserves the license notice for the
API patterns used by this project. Candidate license identities and evidence
tiers are recorded per artifact in immutable packaged provenance.

The consumer gitlink and settings preflight pin the installed-GEO module's
exact commit and production-source tree. The source migration preserves the
frozen parity contract while removing the add-on's private compiler copy. No
standalone module JAR, module test, build file, or frozen oracle is packaged.
