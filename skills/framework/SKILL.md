---
name: framework
description: Use for AGP, Gradle plugins, JaCoCo, signing, and native Android build concerns in contentstack-android.
---

# Build & platform – contentstack-android

## When to use

- Upgrading Android Gradle Plugin, compile SDK, or dependencies
- Fixing signing, packaging excludes, or multidex issues

## Instructions

### Gradle

- Root **`build.gradle`** pins AGP and Nexus publish plugin versions; module plugin **`com.android.library`** applies in `contentstack/build.gradle`.

### Publishing

- Maven coordinates use `PUBLISH_GROUP_ID`, `PUBLISH_ARTIFACT_ID`, `PUBLISH_VERSION`—bump with releases.

### Keystore

- Debug/release signing references **`key.keystore`** in the module—suitable for sample/debug only; production signing is consumer responsibility.
