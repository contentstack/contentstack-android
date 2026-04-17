---
name: contentstack-android-cda
description: Use for the public Android Content Delivery SDK surface and package com.contentstack.sdk.
---

# Android CDA SDK – contentstack-android

## When to use

- Changing SDK entry points consumed by Android apps
- Working with stack configuration, entries, or HTTP client usage in this module

## Instructions

### Module

- Primary code lives in **`contentstack/`** with namespace **`com.contentstack.sdk`** (`android { namespace ... }`).

### API design

- Keep public Java/Kotlin APIs stable for app developers—use semver for breaking changes in published AARs.

### Integration

- Behavior should stay aligned with other CDA SDKs where features overlap (queries, preview, etc.)—check parity with Java/Swift docs when adding features.
