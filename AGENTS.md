# Contentstack Android Delivery SDK – Agent guide

**Universal entry point** for contributors and AI agents. Detailed conventions live in **`skills/*/SKILL.md`**.

## What this repo is

| Field | Detail |
|--------|--------|
| **Name:** | [contentstack-android](https://github.com/contentstack/contentstack-android) (`com.contentstack.sdk:android`) |
| **Purpose:** | Android library for the Contentstack Content Delivery API (Kotlin/Java consumers via AAR). |
| **Out of scope:** | Not the Java-only or iOS/Swift SDKs—those live in sibling repositories. |

## Tech stack (at a glance)

| Area | Details |
|------|---------|
| Language | Kotlin/Java; **compileSdk 34**; Java **17** compile options in `contentstack/build.gradle` |
| Build | Gradle (root `build.gradle`, `settings.gradle`, module **`contentstack/`**) |
| Tests | JUnit, Mockito, Robolectric, AndroidX Test—unit tests under `contentstack/src/test/` |
| Lint / coverage | JaCoCo integrated with debug unit tests (`testCoverageEnabled` on debug) |
| CI | `.github/workflows/check-branch.yml`, `publish-release.yml`, `sca-scan.yml`, `policy-scan.yml`, `codeql-analysis.yml` |

## Commands (quick reference)

| Command type | Command |
|--------------|---------|
| Unit tests (typical) | `./gradlew :contentstack:testDebugUnitTest` (from repo root) |
| Clean | `./gradlew clean` |

## Where the documentation lives: skills

| Skill | Path | What it covers |
|-------|------|----------------|
| **Development workflow** | [`skills/dev-workflow/SKILL.md`](skills/dev-workflow/SKILL.md) | Gradle, variants, CI, publishing |
| **Android CDA SDK** | [`skills/contentstack-android-cda/SKILL.md`](skills/contentstack-android-cda/SKILL.md) | Library API and module boundaries |
| **Android project layout** | [`skills/android/SKILL.md`](skills/android/SKILL.md) | `contentstack/` module, manifest, BuildConfig |
| **Testing** | [`skills/testing/SKILL.md`](skills/testing/SKILL.md) | Unit vs instrumented tests, Robolectric, JaCoCo |
| **Build & platform** | [`skills/framework/SKILL.md`](skills/framework/SKILL.md) | AGP, signing placeholders, `local.properties` |
| **Code review** | [`skills/code-review/SKILL.md`](skills/code-review/SKILL.md) | PR checklist |

## Using Cursor (optional)

If you use **Cursor**, [`.cursor/rules/README.md`](.cursor/rules/README.md) only points to **`AGENTS.md`**—same docs as everyone else.
