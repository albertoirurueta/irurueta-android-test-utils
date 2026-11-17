#!/usr/bin/env python3
"""Rewrites version references after a release, for the Sync workflow.

Reads RELEASE_VERSION and NEXT_SNAPSHOT from the environment and updates
lib/build.gradle.kts's `val libraryVersion` (app/build.gradle.kts's `versionName` too, if an
app module is ever added), README.md, docs/antora.yml, and any Antora page that carries the same
"Latest release" / "Latest snapshot" dependency snippets as README.md.

Adapted to this repository: the library is consumed as a test dependency, so the dependency
lines are `testImplementation` / `androidTestImplementation` (Groovy or Kotlin DSL), not plain
`implementation`. Besides the marker-driven rewrite, every released (non -SNAPSHOT)
`com.irurueta:irurueta-android-test-utils:<version>` coordinate, the AsciiDoc
`|Latest release |`<version>`` table row and the version-catalog alias pinned to that module
are rewritten to the release version.

CHANGELOG.md is intentionally left untouched: its release section and fresh
"[Unreleased]" heading are written before the tag is published, and arrive on
develop via the merge that precedes this script, not by bumping a
version string.
"""
import os
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[2]

# Published Maven coordinates (lib/build.gradle.kts mavenPublishing { coordinates(...) }).
COORDINATES = "com.irurueta:irurueta-android-test-utils"

# Matches implementation / testImplementation / androidTestImplementation / debugImplementation ...
# followed by an optional "(" and a quoted group:artifact:version string.
DEPENDENCY_LINE = r"(\b[A-Za-z]*[Ii]mplementation\s*\(?['\"][^:'\"]+:[^:'\"]+:)[^'\"]+(['\"])"


def update_lib_module(next_snapshot):
    path = REPO_ROOT / "lib" / "build.gradle.kts"
    if not path.exists():
        return
    text = path.read_text()
    updated, count = re.subn(
        r'(val libraryVersion = )"[^"]*"',
        lambda m: f'{m.group(1)}"{next_snapshot}"',
        text,
        count=1,
    )
    if count == 0:
        sys.exit(f"{path}: libraryVersion pattern not found")
    path.write_text(updated)


def update_app_module(next_snapshot):
    path = REPO_ROOT / "app" / "build.gradle.kts"
    if not path.exists():
        return
    text = path.read_text()
    updated, count = re.subn(
        r'(versionName = )"[^"]*"',
        lambda m: f'{m.group(1)}"{next_snapshot}"',
        text,
        count=1,
    )
    if count == 0:
        sys.exit(f"{path}: versionName pattern not found")
    path.write_text(updated)
    # versionCode is bumped by a *release* (a non -SNAPSHOT version), never by this script, which only ever
    # writes a -SNAPSHOT value — matching iru-android-bump-version's Step 4 rule exactly. Nothing to do here.


def replace_dependency_snippets(text, release_version, next_snapshot):
    """Rewrites the trailing version segment of every `*implementation 'group:artifact:version'` (or Kotlin-DSL
    `*implementation("group:artifact:version")`) line following a "Latest release"/"Latest snapshot" marker,
    up to the end of the first code block (``` or ----) that contained a rewritten line."""
    lines = text.splitlines(keepends=True)
    pending = None
    replaced = False
    for i, line in enumerate(lines):
        stripped = line.strip()
        lower = stripped.lower()
        if lower.startswith("latest release"):
            pending, replaced = release_version, False
        elif lower.startswith("latest snapshot"):
            pending, replaced = next_snapshot, False
        elif not pending:
            continue
        elif re.search(DEPENDENCY_LINE, line):
            lines[i] = re.sub(DEPENDENCY_LINE, lambda m: f"{m.group(1)}{pending}{m.group(2)}", line)
            replaced = True
        elif replaced and (stripped.startswith("```") or stripped == "----"):
            pending, replaced = None, False
    return "".join(lines)


def replace_released_coordinates(text, release_version):
    """Points every released (non -SNAPSHOT) mention of this library at the new release: plain
    `group:artifact:version` coordinates, an AsciiDoc `|Latest release |`x`` table row, and a version-catalog
    alias whose `[libraries]` entry uses `module = "group:artifact"` with `version.ref`."""
    text = re.sub(
        re.escape(COORDINATES) + r":(\d+(?:\.\d+)*)(?![\w.-])",
        lambda m: f"{COORDINATES}:{release_version}",
        text,
    )
    text = re.sub(
        r"(?m)^(\|\s*Latest release\s*\|\s*`)[^`]*(`)",
        lambda m: f"{m.group(1)}{release_version}{m.group(2)}",
        text,
    )
    for alias in re.findall(
        r'module\s*=\s*"' + re.escape(COORDINATES) + r'"\s*,\s*version\.ref\s*=\s*"([^"]+)"', text
    ):
        text = re.sub(
            r'(?m)^(\s*' + re.escape(alias) + r'\s*=\s*")[^"]*(")',
            lambda m: f"{m.group(1)}{release_version}{m.group(2)}",
            text,
        )
    return text


def update_readme(release_version, next_snapshot):
    path = REPO_ROOT / "README.md"
    if not path.exists():
        return
    text = path.read_text()
    # Adjust these two row-label regexes to match this repository's actual README table wording (resolved in
    # Step 1) if it differs from setup-readme's default "Current development version" / "Latest release" labels.
    text = re.sub(
        r"(\| Current development version \| `)[^`]*(` \|)",
        rf"\g<1>{next_snapshot}\g<2>",
        text,
    )
    text = re.sub(
        r"(\| Latest release[^|]*\| `)[^`]*(` \|)",
        rf"\g<1>{release_version}\g<2>",
        text,
    )
    text = replace_released_coordinates(text, release_version)
    text = replace_dependency_snippets(text, release_version, next_snapshot)
    path.write_text(text)


def update_antora_component_version(release_version):
    path = REPO_ROOT / "docs" / "antora.yml"
    if not path.exists():
        return
    text = path.read_text()
    updated, count = re.subn(r"(?m)^version:.*$", f"version: {release_version}", text, count=1)
    if count == 1:
        path.write_text(updated)


def update_antora_pages(release_version, next_snapshot):
    pages_dir = REPO_ROOT / "docs" / "modules" / "ROOT" / "pages"
    if not pages_dir.exists():
        return
    for path in sorted(pages_dir.glob("*.adoc")):
        text = path.read_text()
        if "mplementation" not in text and COORDINATES not in text:
            continue
        updated = replace_released_coordinates(text, release_version)
        updated = replace_dependency_snippets(updated, release_version, next_snapshot)
        if updated != text:
            path.write_text(updated)


def main():
    release_version = os.environ["RELEASE_VERSION"]
    next_snapshot = os.environ["NEXT_SNAPSHOT"]

    update_lib_module(next_snapshot)
    update_app_module(next_snapshot)
    update_readme(release_version, next_snapshot)
    update_antora_component_version(release_version)
    update_antora_pages(release_version, next_snapshot)


if __name__ == "__main__":
    main()
