# BlueMap Ars Technica Add-on

A Java 21 BlueMap add-on for the exact `ars-technica-2.7.6-mc1.21.1` profile in All the Mons
`1.2.0` / Minecraft `1.21.1`.

Version `0.1.0-alpha.2` keeps the owner-accepted renderer while replacing its
private installed-GEO compiler with the pinned shared source module. The exact
artifact gate admits only Ars Technica 2.7.6, Ars Nouveau 5.13.0, and Create
6.0.10. It preserves the source motor's stock body while adding its static
shaft, and replaces the empty animated placeholders for the precise relay and
transmutation turret with static meshes compiled from operator-installed GEO
resources.

## Build

Clone with `--recurse-submodules`, or initialize an existing checkout with
`git submodule update --init --recursive`. The settings preflight accepts only
the committed toolkit and installed-GEO module gitlinks and rejects an
uninitialized, changed, or dirty checkout.

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport clean check build
```

`check` is the quick Java/checkstyle/archive gate. `prototypeCheck` additionally
requires every exact candidate JAR property and validates the 14-case gallery.
See `provenance/upstreams.json` for immutable artifact identities and
the [execution guide](docs/EXECUTION.md) for the prototype-to-release loop.

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
