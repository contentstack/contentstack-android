---
name: android
description: Use for Android module layout, Gradle config, and Kotlin/Java conventions in contentstack-android.
---

# Android project layout – contentstack-android

## When to use

- Editing `contentstack/build.gradle`, manifests, or source under `contentstack/src/`
- Adjusting `minSdk`, multidex, or packaging excludes

## Instructions

### Source trees

- Follow standard Android library layout: **`main`**, **`test`**, **`androidTest`** under `contentstack/src/`.

### Configuration

- **`local.properties`** supplies machine-specific paths and optional keys—do not commit secrets; use the same keys the Gradle file expects (`host`, `APIKey`, etc. in `buildTypes`).

### Java/Kotlin

- Compile options target **Java 17** in the module—match language features accordingly.
