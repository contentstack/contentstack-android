---
name: contentstack-android-cda
description: Use when implementing or changing CDA features – Stack/Config, entries, assets, sync, HTTP, callbacks, and Content Delivery API alignment
---

# Contentstack Android CDA SDK – CDA Implementation

Use this skill when implementing or changing Content Delivery API (CDA) behavior in the Android SDK.

## When to use

- Adding or modifying Stack, Entry, Query, Asset, Content Type, or Sync behavior.
- Changing Config options (host, version, region, branch, proxy, connection pool).
- Working with the HTTP layer (CSHttpConnection/Volley, Stack/APIService/Retrofit/OkHttp) or callbacks and Error handling.

## Instructions

### Stack and Config

- **Entry point:** `Contentstack.stack(Context, apiKey, deliveryToken, environment)`. Overloads accept `Config` for host, version, region, branch, proxy, connection pool, endpoint.
- **Defaults:** host `cdn.contentstack.io`, version `v3` (see `Config`). Region and branch via `Config.setRegion()`, `Config.setBranch()`.
- **Reference:** `Contentstack.java`, `Stack.java`, `Config.java`.

### CDA resources

- **Entries:** `Stack.contentType(uid).entry(uid)`, query APIs, and entry fetch. Use existing `Entry`, `Query`, `EntriesModel`, and callback types.
- **Assets:** `Stack.assetLibrary()`, asset fetch and query. Use `Asset`, `AssetLibrary`, `AssetModel`, and related callbacks.
- **Content types:** Content type schema and listing. Use `ContentType`, `ContentTypesModel`, `ContentTypesCallback`.
- **Sync:** Sync API usage. Use existing sync request/response and pagination patterns.
- **Official API:** Align with [Content Delivery API](https://www.contentstack.com/docs/apis/content-delivery-api/) for parameters, response shape, and semantics.

### HTTP and retry

- **HTTP:** CDA requests use **CSHttpConnection** (Volley) and/or **Retrofit** + **OkHttp** (e.g. `Stack`, `APIService`). Set headers (User-Agent, auth) per constants and existing request building.
- **Retry:** Volley retry is configured via `DefaultRetryPolicy` in `CSHttpConnection`; constants in `SDKConstant` (e.g. `TimeOutDuration`, `NumRetry`, `BackOFMultiplier`). Keep timeouts and retry behavior consistent when changing the HTTP layer.
- **Reference:** `CSHttpConnection.java`, `CSConnectionRequest.java`, `Stack.java`, `APIService.java`, `SDKConstant.java`.

### Errors and callbacks

- **Errors:** Map API errors to the `Error` class. Pass to the appropriate callback (e.g. `ResultCallBack`) so callers receive a consistent error shape.
- **Callbacks:** Use existing callback types (`ResultCallBack`, `EntryResultCallBack`, `QueryResultsCallBack`, etc.). Do not change callback contracts without considering backward compatibility.

## Key classes

- **Entry points:** `Contentstack`, `Stack`, `Config`
- **CDA:** `Entry`, `Query`, `Asset`, `AssetLibrary`, `ContentType`, sync-related classes
- **HTTP:** `CSHttpConnection`, `CSConnectionRequest`, `APIService`, `Stack` (Retrofit)
- **Errors / results:** `Error`, `QueryResult`, and callback interfaces in `com.contentstack.sdk`

## References

- [Content Delivery API – Contentstack Docs](https://www.contentstack.com/docs/apis/content-delivery-api/)
- Project rules: `.cursor/rules/contentstack-android-cda.mdc`, `.cursor/rules/java.mdc`
