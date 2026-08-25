# Ars Technica visual gallery

This generated 14-case gallery presents all six facings of the source motor and
transmutation turret, one precise relay, and one `minecraft:stone` stock
control. The renderer strictly admits 32 legal block states: 6 motor, 2 relay,
and 24 turret states.

Use the stable commands:

```bash
python gallery/generate.py
python gallery/generate.py --check
python gallery/lint.py
bash gallery/package.sh /tmp/ars_technica-gallery.zip
```

Keep gallery generation deterministic, bounded, synthetic where practical, and
free of candidate assets or captured meshes.
