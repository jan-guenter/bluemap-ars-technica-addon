#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Family-owned cases for the bounded Ars Technica visual gallery."""

from __future__ import annotations

from dataclasses import dataclass


NAMESPACE = "ars_technica_gallery"
ENVELOPE = (173, 99, 173, 181, 102, 177)
LEGAL_STATE_COUNTS = {
    "source_motor": 6,
    "precise_relay": 2,
    "transmutation_turret": 24,
}


@dataclass(frozen=True)
class Placement:
    case_id: str
    label: str
    x: int
    y: int
    z: int
    block_state: str
    expected: str


PLACEMENTS = (
    *(
        Placement(
            f"source-motor-{facing}",
            f"source motor static shaft facing {facing}",
            174 + index,
            100,
            174,
            f"ars_technica:source_motor[facing={facing}]",
            "stock-body-plus-static-shaft",
        )
        for index, facing in enumerate(
            ("north", "south", "west", "east", "up", "down")
        )
    ),
    Placement(
        "precise-relay-dry",
        "precise relay static base pose",
        180,
        100,
        174,
        "ars_technica:precise_relay[waterlogged=false]",
        "installed-geo-static",
    ),
    *(
        Placement(
            f"transmutation-turret-{facing}",
            f"transmutation turret idle facing {facing}",
            174 + index,
            100,
            176,
            "ars_technica:transmutation_turret["
            f"facing={facing},triggered=false,waterlogged=false]",
            "installed-geo-static-idle",
        )
        for index, facing in enumerate(
            ("north", "south", "west", "east", "up", "down")
        )
    ),
    Placement(
        "stock-control",
        "stone stock rendering control",
        180,
        100,
        176,
        "minecraft:stone",
        "stock-visible",
    ),
)
