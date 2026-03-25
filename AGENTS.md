# Contentstack Android CDA SDK – Agent Guide

This document is the main entry point for AI agents working in this repository.

## Project

- **Name:** Contentstack Android CDA SDK (contentstack-android)
- **Purpose:** Android client for the Contentstack **Content Delivery API (CDA)**. It fetches content (entries, assets, content types, sync, etc.) from Contentstack for Android apps.
- **Repo:** [contentstack-android](https://github.com/contentstack/contentstack-android)

## Tech stack

- **Languages:** Java (primary SDK source); Kotlin may appear in tests or future code. Target **Java 8** compatibility for the library (see `contentstack/build.gradle`).
- **Build:** Gradle (Android Gradle Plugin), single module **`contentstack`** (AAR).
- **Testing:** JUnit 4, Robolectric (unit tests on JVM), Mockito / PowerMock where used; **androidTest** with AndroidX Test / Espresso for instrumented tests; JaCoCo for coverage (`jacocoTestReport`).
- **HTTP:** **Volley** (`CSHttpConnection`) for much of the CDA traffic; **Retrofit 2 + OkHttp + Gson** for paths such as taxonomy (`Stack`, `APIService`). OkHttp **MockWebServer** in unit tests.

## Main entry points

- **`Contentstack`** – Factory: `Contentstack.stack(Context, apiKey, deliveryToken, environment)` (and overloads with `Config`) returns a **`Stack`**.
- **`Stack`** – Main API: content types, entries, queries, assets, sync, etc.
- **`Config`** – Optional configuration: host, version, region, branch, proxy, connection pool, endpoint.
- **Paths:** `contentstack/src/main/java/com/contentstack/sdk/` (production), `contentstack/src/test/java/com/contentstack/sdk/` (unit tests), `contentstack/src/androidTest/java/` (instrumented tests).

## Commands

Run from the **repository root** (requires Android SDK / `local.properties` for connected tests).

| Goal | Command |
|------|---------|
| **Build library (debug)** | `./gradlew :contentstack:assembleDebug` |
| **Run all unit tests** | `./gradlew :contentstack:testDebugUnitTest` |
| **Run instrumented / connected tests** | `./gradlew :contentstack:connectedDebugAndroidTest` (device or emulator required) |
| **Unit + connected (full local test pass)** | `./gradlew :contentstack:testDebugUnitTest :contentstack:connectedDebugAndroidTest` |
| **Coverage report (unit)** | `./gradlew :contentstack:jacocoTestReport` |

Instrumented tests may need **`local.properties`** entries (e.g. `APIKey`, `deliveryToken`, `environment`, `host`) for stacks that hit a real CDA endpoint—see `contentstack/build.gradle` `buildConfigField` usage.

## Rules and skills

- **`.cursor/rules/`** – Cursor rules for this repo:
  - **README.md** – Index of all rules (globs / always-on).
  - **dev-workflow.md** – Branches, tests, PR expectations.
  - **java.mdc** – Applies to `**/*.java` and `**/*.kt`: language style, `com.contentstack.sdk` layout, logging, null-safety.
  - **contentstack-android-cda.mdc** – SDK core: CDA patterns, Stack/Config, host/version/region/branch, retry, callbacks, CDA alignment.
  - **testing.mdc** – `contentstack/src/test/**` and `contentstack/src/androidTest/**`: naming, unit vs instrumented, JaCoCo.
  - **code-review.mdc** – Always applied: PR/review checklist (aligned with Java CDA SDK).
- **`skills/`** – Reusable skill docs:
  - **contentstack-android-cda** – Implementing or changing CDA behavior (Stack/Config, entries, assets, sync, HTTP, callbacks).
  - **testing** – Writing or refactoring tests.
  - **code-review** – PR review / pre-PR checklist.
  - **framework** – Config, HTTP layer (Volley + Retrofit/OkHttp), retry/timeouts.

Refer to `.cursor/rules/README.md` and `skills/README.md` for details.
