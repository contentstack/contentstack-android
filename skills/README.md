# Skills – Contentstack Android CDA SDK

This directory contains **skills**: reusable guidance for AI agents (and developers) on specific tasks. Each skill is a folder with a `SKILL.md` file.

## When to use which skill

| Skill | Use when |
|-------|----------|
| **contentstack-android-cda** | Implementing or changing CDA features: Stack/Config, entries, assets, content types, sync, HTTP layer (Volley + Retrofit/OkHttp), callbacks, error handling. |
| **testing** | Writing or refactoring tests: JUnit 4, Robolectric, androidTest, naming, MockWebServer, JaCoCo. |
| **code-review** | Reviewing a PR or preparing your own: API design, null-safety, exceptions, backward compatibility, dependencies/security, test coverage. |
| **framework** | Touching config or the HTTP layer: Config options, CSHttpConnection (Volley), Stack/APIService (Retrofit/OkHttp), timeouts/retry, error handling. |

## How agents should use skills

- **contentstack-android-cda:** Apply when editing SDK core (`com.contentstack.sdk`) or adding CDA-related behavior. Follow Stack/Config, CDA API alignment, and existing callback/error patterns.
- **testing:** Apply when creating or modifying test classes. Follow naming (`Test*` for unit tests), Robolectric vs androidTest, and existing Gradle/JaCoCo setup.
- **code-review:** Apply when performing or simulating a PR review. Go through the checklist (API stability, errors, compatibility, dependencies, tests) and optional severity levels.
- **framework:** Apply when changing Config, CSHttpConnection, Stack’s Retrofit/OkHttp setup, or retry/timeout constants. Keep behavior consistent with the rest of the SDK.

Each skill’s `SKILL.md` contains more detailed instructions and references.
