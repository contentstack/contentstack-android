---
name: framework
description: Use when touching config or HTTP layer – Config, CSHttpConnection (Volley), Stack/APIService (Retrofit/OkHttp), timeouts, retry, and error handling
---

# Framework – Contentstack Android CDA SDK

Use this skill when changing configuration or the HTTP connection layer (Volley and/or Retrofit/OkHttp).

## When to use

- Modifying **Config** options (host, version, region, branch, proxy, connection pool, endpoint).
- Changing **CSHttpConnection** (Volley), request building, or **Stack**’s Retrofit/OkHttp setup.
- Changing retry/timeout constants (e.g. `SDKConstant.TimeOutDuration`, `NumRetry`, `BackOFMultiplier`) or `DefaultRetryPolicy` usage.
- Introducing or changing connection pooling, timeouts, or interceptors.

## Instructions

### Config

- **Config** holds: host (default `cdn.contentstack.io`), version (default `v3`), environment, branch, region (`ContentstackRegion`), proxy, connection pool, endpoint, earlyAccess.
- Use setter-style APIs (e.g. `setHost`, `setBranch`, `setRegion`). Preserve default values where existing behavior depends on them.
- **Reference:** `contentstack/src/main/java/com/contentstack/sdk/Config.java`.

### Volley and CSHttpConnection

- **CSHttpConnection** uses **Volley** to execute requests. It is invoked via **CSConnectionRequest**; responses and errors are passed to the request’s callback (e.g. `onRequestFinished`).
- **Retry:** Volley’s `DefaultRetryPolicy` is used in `CSHttpConnection` with constants from **SDKConstant** (e.g. `TimeOutDuration`, `NumRetry`, `BackOFMultiplier`). Keep retry/timeout behavior consistent when changing this path.
- **Reference:** `CSHttpConnection.java`, `CSConnectionRequest.java`, `IRequestModelHTTP.java`, `SDKConstant.java`.

### Stack, Retrofit, and OkHttp

- **Stack** builds an **OkHttpClient** and **Retrofit** instance and uses **APIService** for certain CDA calls (e.g. taxonomy). Base URL and headers are derived from Config/stack state.
- Do not bypass the shared client/Config when adding new CDA calls that use Retrofit.
- **Reference:** `Stack.java`, `APIService.java`.

### Error handling

- Map HTTP and API errors to the **Error** class. Pass errors through the same callback mechanism used elsewhere so callers get a consistent contract.
- **Reference:** `Error.java`, and callback interfaces that receive errors.

## Key classes

- **Config:** `Config.java`, `ContentstackRegion`
- **Volley path:** `CSHttpConnection.java`, `CSConnectionRequest.java`, `IRequestModelHTTP.java`, `SDKConstant.java`
- **Retrofit path:** `Stack.java`, `APIService.java`
- **Errors/callbacks:** `Error.java`, `ResultCallBack`, and related callback types

## References

- Project rules: `.cursor/rules/contentstack-android-cda.mdc`, `.cursor/rules/java.mdc`
- CDA skill: `skills/contentstack-android-cda/SKILL.md` for CDA-specific usage of Config and HTTP
