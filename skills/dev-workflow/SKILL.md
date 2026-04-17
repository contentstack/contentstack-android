---
name: dev-workflow
description: Use for Gradle tasks, CI, release publishing, and branch flow in contentstack-android.
---

# Development workflow – contentstack-android

## When to use

- Running local builds/tests before a PR
- Aligning with GitHub Actions (branch checks, SCA, publish)

## Instructions

### Project shape

- Root project **`contentstack-android`** includes module **`:contentstack`** (`settings.gradle`).

### Commands

- From repo root: `./gradlew :contentstack:testDebugUnitTest` for JVM unit tests on the library.
- Use `./gradlew clean` when switching branches or after AGP/cache issues.

### CI

- Workflows under `.github/workflows/` enforce policies and may publish artifacts—coordinate version bumps with `contentstack/build.gradle` `PUBLISH_*` fields.
