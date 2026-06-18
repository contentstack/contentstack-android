package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Resolves Contentstack API endpoints for any region and service without hardcoding host strings.
 *
 * <h3>Resolution chain</h3>
 * <ol>
 *   <li><b>In-memory cache</b> — populated on the first call and reused for the process lifetime
 *       (zero I/O on every subsequent call).</li>
 *   <li><b>Bundled {@code regions.json}</b> — read from the classpath resource
 *       {@code /assets/regions.json} that is packaged inside the SDK. Works
 *       fully offline with zero latency.</li>
 *   <li><b>Live download</b> — if the requested region is not present in the bundled file
 *       (e.g. Contentstack added a new region after this SDK version was released), a single
 *       HTTP request is made to {@value #REGIONS_URL} to fetch the latest registry. The
 *       downloaded data replaces the in-memory cache so all subsequent lookups benefit from it.
 *       This attempt is made at most <em>once</em> per session to avoid repeated network
 *       calls for genuinely invalid region strings.</li>
 * </ol>
 *
 * <p>Region matching is case-insensitive and treats {@code -} and {@code _} as equivalent
 * separators, so {@code "AZURE_NA"}, {@code "azure-na"}, and {@code "Azure_NA"} all resolve
 * to the same region.
 *
 * <p><b>Examples:</b>
 * <pre>
 *   String url  = Endpoint.getContentstackEndpoint("eu", "contentDelivery");
 *   // → "https://eu-cdn.contentstack.com"
 *
 *   String host = Endpoint.getContentstackEndpoint("eu", "contentDelivery", true);
 *   // → "eu-cdn.contentstack.com"
 *
 *   Map&lt;String, String&gt; all = Endpoint.getAllEndpoints("azure-na");
 *   // → {"contentDelivery": "https://azure-na-cdn.contentstack.com", ...}
 * </pre>
 */
public class Endpoint {

    static final String REGIONS_URL = "https://artifacts.contentstack.com/regions.json";

    private static final Logger logger = Logger.getLogger(Endpoint.class.getSimpleName());

    private static volatile JSONArray regionsCache = null;

    private static volatile boolean liveRefreshDone = false;

    private Endpoint() {
    }

    public static String getContentstackEndpoint(String region, String service) {
        return getContentstackEndpoint(region, service, false);
    }

    public static String getContentstackEndpoint(String region, String service, boolean omitHttps) {
        return getContentstackEndpoint(region, service, omitHttps, null);
    }

    /**
     * Internal variant that routes the live-refresh fallback through the given {@code proxy}
     * (typically the one configured on {@link Config}). Used by {@link Stack} so region
     * resolution still works in proxy-only / VPN environments. A {@code null} proxy uses a
     * direct connection.
     */
    static String getContentstackEndpoint(String region, String service, boolean omitHttps, Proxy proxy) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty region provided. Please provide a valid region.");
        }
        JSONObject regionRow = resolveRegion(region, proxy);
        try {
            JSONObject endpoints = regionRow.getJSONObject("endpoints");
            if (!endpoints.has(service)) {
                throw new IllegalArgumentException(
                        "Service \"" + service + "\" not found for region \"" + region + "\"");
            }
            String url = endpoints.getString(service);
            return omitHttps ? stripHttps(url) : url;
        } catch (JSONException e) {
            throw new IllegalStateException("Malformed regions.json: " + e.getMessage(), e);
        }
    }

    public static Map<String, String> getAllEndpoints(String region) {
        return getAllEndpoints(region, false);
    }

    public static Map<String, String> getAllEndpoints(String region, boolean omitHttps) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty region provided. Please provide a valid region.");
        }
        JSONObject regionRow = resolveRegion(region, null);
        try {
            JSONObject endpoints = regionRow.getJSONObject("endpoints");
            Map<String, String> result = new LinkedHashMap<>();
            Iterator<String> keys = endpoints.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String url = endpoints.getString(key);
                result.put(key, omitHttps ? stripHttps(url) : url);
            }
            return result;
        } catch (JSONException e) {
            throw new IllegalStateException("Malformed regions.json: " + e.getMessage(), e);
        }
    }

    static synchronized void resetCache() {
        regionsCache = null;
        liveRefreshDone = false;
    }

    private static JSONObject resolveRegion(String region, Proxy proxy) {
        JSONArray regions = loadRegions(proxy);
        try {
            return findRegion(regions, region);
        } catch (IllegalArgumentException notInBundled) {
            if (!liveRefreshDone) {
                JSONArray fresh = tryLiveRefresh(proxy);
                if (fresh != null) {
                    try {
                        return findRegion(fresh, region);
                    } catch (IllegalArgumentException ignored) {
                        // fall through to re-throw the original error below
                    }
                }
            }
            throw notInBundled;
        }
    }

    private static synchronized JSONArray loadRegions(Proxy proxy) {
        if (regionsCache != null) {
            return regionsCache;
        }
        InputStream stream = Endpoint.class.getResourceAsStream("/assets/regions.json");
        if (stream != null) {
            try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
                String raw = scanner.useDelimiter("\\A").next();
                JSONObject root = new JSONObject(raw);
                regionsCache = root.getJSONArray("regions");
                return regionsCache;
            } catch (JSONException e) {
                throw new IllegalStateException("Bundled regions.json is corrupt: " + e.getMessage(), e);
            }
        }
        logger.warning("Bundled regions.json not found in classpath — attempting live download.");
        JSONArray downloaded = tryLiveRefresh(proxy);
        if (downloaded != null) {
            return downloaded;
        }
        throw new IllegalStateException(
                "regions.json not found in classpath and could not be downloaded from "
                        + REGIONS_URL + ". Ensure the SDK was built correctly, or check network access.");
    }

    private static synchronized JSONArray tryLiveRefresh(Proxy proxy) {
        if (liveRefreshDone) {
            return regionsCache;
        }
        liveRefreshDone = true;
        try {
            logger.info("Refreshing regions from " + REGIONS_URL);
            URL url = new URL(REGIONS_URL);
            HttpURLConnection conn = (HttpURLConnection) (proxy != null
                    ? url.openConnection(proxy)
                    : url.openConnection());
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5_000);
            conn.setReadTimeout(10_000);
            conn.setRequestProperty("Accept", "application/json");
            try (InputStream stream = conn.getInputStream();
                 Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
                String raw = scanner.useDelimiter("\\A").next();
                JSONObject root = new JSONObject(raw);
                regionsCache = root.getJSONArray("regions");
                logger.info("regions.json refreshed from live URL (" + regionsCache.length() + " regions).");
                return regionsCache;
            }
        } catch (Exception e) {
            logger.warning("Live region refresh failed: " + e.getMessage());
            return null;
        }
    }

    private static JSONObject findRegion(JSONArray regions, String region) {
        String normalized = region.trim().toLowerCase().replace('_', '-');

        try {
            for (int i = 0; i < regions.length(); i++) {
                JSONObject row = regions.getJSONObject(i);
                if (row.getString("id").equals(normalized)) {
                    return row;
                }
            }

            for (int i = 0; i < regions.length(); i++) {
                JSONObject row = regions.getJSONObject(i);
                JSONArray aliases = row.optJSONArray("alias");
                if (aliases == null) {
                    continue;
                }
                for (int j = 0; j < aliases.length(); j++) {
                    String alias = aliases.getString(j).toLowerCase().replace('_', '-');
                    if (alias.equals(normalized)) {
                        return row;
                    }
                }
            }
        } catch (JSONException e) {
            throw new IllegalStateException("Malformed regions.json: " + e.getMessage(), e);
        }

        throw new IllegalArgumentException("Invalid region: " + region);
    }

    private static String stripHttps(String url) {
        return url.replaceFirst("^https?://", "");
    }
}
