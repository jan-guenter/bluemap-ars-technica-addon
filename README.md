# BlueMap Ars Technica Add-on

A Java 21 BlueMap 5.23 feature-backport add-on for the exact
`ars-technica-2.7.6-mc1.21.1` profile in All the Mons `1.2.0` / Minecraft
`1.21.1`.

Status: unpublished `0.1.0-alpha.3` migration candidate. The owner accepted
and released the same renderer in `0.1.0-alpha.2`. This candidate targets only
BlueMap feature-backport commit
`7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` and API commit
`285c9a60eff3ac2b0cab308ce1058d1565be0971`. The exact artifact gate still
admits only Ars Technica 2.7.6, Ars Nouveau 5.13.0, and Create 6.0.10. The
source motor, relay, turret, installed-resource compiler, and stock fallback
remain unchanged.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with
`git submodule update --init --recursive`. The settings preflight accepts only
the committed toolkit, installed-GEO, and Adapter API gitlinks. It rejects an
uninitialized, changed, dirty, incorrectly pinned, or source-tree-mismatched
checkout.

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport clean check build
```

`check` is the Java, checkstyle, and archive gate. `prototypeCheck` also
requires every exact candidate JAR property and validates the 14-case gallery.
The production and sources JARs contain the four exact Adapter API sources,
never the standalone module JAR. See `provenance/upstreams.json` for immutable
artifact identities and the [execution guide](docs/EXECUTION.md) for the
review and release loop.

The pinned `modules/bluemap-installed-geo-resource-models` source module
supplies the neutral installed Bedrock GEO compiler and mesh records. Gradle
compiles those sources into this add-on with a direct compile-only Gson 2.8.9
pin. No shared module JAR is installed or nested. The settings preflight pins
the `v0.1.0-alpha.1` commit and production-source tree.

## Install

Place the production JAR in BlueMap's add-on pack directory and restart the
BlueMap JVM. Removal plus one restart restores stock behavior; the add-on
creates no custom world state.

Set `-Dbluemap.arstechnica.disabled=true` to leave the exact profile inactive.

## Scope boundary

Live shaft rotation, turret recoil, activity overlays, particles, animation
phase, and unsupported states stay stock or deterministic-neutral unless the
owner explicitly expands scope.

No Ars Technica binary, source, class, asset, captured mesh, or gallery is
bundled in the add-on.
