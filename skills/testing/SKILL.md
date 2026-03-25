---
name: testing
description: Use when writing or refactoring tests – JUnit 4, Robolectric, androidTest, naming, MockWebServer, JaCoCo
---

# Testing – Contentstack Android CDA SDK

Use this skill when adding or refactoring tests in the Android CDA SDK.

## When to use

- Writing new unit or instrumented tests.
- Refactoring test layout, base classes, or test utilities.
- Adjusting test configuration (e.g. timeouts, Robolectric, JaCoCo) or coverage.

## Instructions

### JUnit 4 and layout

- Use **JUnit 4** (junit:junit). Dependencies are in `contentstack/build.gradle`.
- **Unit tests:** Class name should start with **`Test`** (e.g. `TestEntry`, `TestStack`). Place under `contentstack/src/test/java/com/contentstack/sdk/`.
- **Instrumented tests:** Place under `contentstack/src/androidTest/java/com/contentstack/sdk/`. Use **AndroidJUnitRunner**; naming may follow existing style (e.g. `*TestCase`).

### Robolectric and unit tests

- **Robolectric** provides Android context on the JVM. Use it where tests need `Context` (e.g. `Contentstack.stack(context, ...)`).
- For HTTP mocking in unit tests, use **OkHttp MockWebServer** where the SDK uses OkHttp/Retrofit; mock or stub Volley/CSHttpConnection where appropriate.

### Instrumented tests and credentials

- **BuildConfig:** Instrumented tests can use `BuildConfig` fields (APIKey, deliveryToken, environment, host) from `local.properties` (see `contentstack/build.gradle`). Do not commit real credentials; document required variables.
- Use **AndroidX Test** and **Espresso** as in the project; avoid flaky tests (timeouts, IdlingResource if needed).

### Naming and structure

- One test class per production class when possible (e.g. `TestEntry` for `Entry`).
- Keep tests deterministic and readable; prefer meaningful assertions over large blocks of setup.

### Coverage and execution

- **JaCoCo** is configured in `contentstack/build.gradle`. Run `./gradlew :contentstack:jacocoTestReport` for unit-test coverage (e.g. `contentstack/build/reports/jacoco/`).
- **Unit tests:** `./gradlew :contentstack:testDebugUnitTest`
- **Instrumented:** `./gradlew :contentstack:connectedDebugAndroidTest`
- Maintain or improve coverage when changing production code; add tests for new or modified behavior.

## References

- `contentstack/build.gradle` – test dependencies, testOptions, jacoco
- Project rule: `.cursor/rules/testing.mdc`
