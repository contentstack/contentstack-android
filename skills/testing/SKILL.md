---
name: testing
description: Use for JVM unit tests, Robolectric, AndroidX Test, and JaCoCo in contentstack-android.
---

# Testing – contentstack-android

## When to use

- Adding tests under `contentstack/src/test/`
- Debugging JaCoCo or unit-test-only failures

## Instructions

### Unit tests

- Prefer **`testDebugUnitTest`** for fast feedback; Robolectric enables Android APIs on JVM where configured.

### Instrumentation

- **`androidTest`** exists for on-device tests—run on emulator/CI when changing UI-adjacent or integration paths.

### Coverage

- Debug builds enable **`testCoverageEnabled`**—JaCoCo outputs feed into reporting tasks defined in the module `build.gradle`.

### Hygiene

- Use **`MockWebserver`** and fixtures for HTTP; avoid embedding real stack credentials in the repo.
