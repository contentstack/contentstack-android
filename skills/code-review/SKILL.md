---
name: code-review
description: Use when reviewing PRs for contentstack-android—API stability, Gradle, tests, and mobile security.
---

# Code review – contentstack-android

## When to use

- Reviewing feature or fix PRs for the Android SDK
- Auditing dependency or manifest changes

## Instructions

### Checklist

- **API**: Public SDK surface changes justified and documented.
- **Gradle**: Version bumps coherent; no accidental signing or secret commits.
- **Tests**: Unit tests for new logic; consider Robolectric limitations.
- **Manifest/resources**: No unnecessary permissions; ProGuard rules updated if needed.
- **Cross-SDK**: Behavior consistent with Java/Swift where applicable.

### Severity hints

- **Blocker**: Broken `:contentstack:testDebugUnitTest`, leaked credentials, or broken publish metadata.
- **Major**: Missing coverage for risky network or caching changes.
- **Minor**: Formatting, comments, internal refactors.
