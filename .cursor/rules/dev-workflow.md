# Development Workflow – Contentstack Android CDA SDK

Use this as the standard workflow when contributing to the Android CDA SDK.

## Branches

- Use feature branches for changes (e.g. `feat/...`, `fix/...`).
- Base work off the appropriate long-lived branch (e.g. `staging`, `development`) per team norms.

## Running tests

- **Unit tests:** `./gradlew :contentstack:testDebugUnitTest`
- **Instrumented / connected tests:** `./gradlew :contentstack:connectedDebugAndroidTest` (device or emulator required)
- **Full test pass:** `./gradlew :contentstack:testDebugUnitTest :contentstack:connectedDebugAndroidTest`
- **Coverage report:** `./gradlew :contentstack:jacocoTestReport`

Run unit tests before opening a PR. Instrumented tests may require `local.properties` with stack credentials (see `contentstack/build.gradle` buildConfigField usage).

## Pull requests

- Ensure the build passes: `./gradlew :contentstack:assembleDebug :contentstack:testDebugUnitTest`
- Follow the **code-review** rule (`.cursor/rules/code-review.mdc`) for the PR checklist.
- Keep changes backward-compatible for public API; call out any breaking changes clearly.

## Optional: TDD

If the team uses TDD, follow RED–GREEN–REFACTOR when adding behavior. The **testing** rule and **skills/testing** skill describe test structure and naming.
