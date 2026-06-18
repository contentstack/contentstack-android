package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestEndpoint {

    @After
    public void resetCache() {
        Endpoint.resetCache();
    }

    // ── canonical IDs ─────────────────────────────────────────────────────────

    @Test
    public void testNaContentDelivery() {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentDelivery"));
    }

    @Test
    public void testEuContentDelivery() {
        assertEquals("https://eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentDelivery"));
    }

    @Test
    public void testAuContentDelivery() {
        assertEquals("https://au-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("au", "contentDelivery"));
    }

    @Test
    public void testAzureNaContentDelivery() {
        assertEquals("https://azure-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-na", "contentDelivery"));
    }

    @Test
    public void testAzureEuContentDelivery() {
        assertEquals("https://azure-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-eu", "contentDelivery"));
    }

    @Test
    public void testGcpNaContentDelivery() {
        assertEquals("https://gcp-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp-na", "contentDelivery"));
    }

    @Test
    public void testGcpEuContentDelivery() {
        assertEquals("https://gcp-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp-eu", "contentDelivery"));
    }

    // ── aliases ───────────────────────────────────────────────────────────────

    @Test
    public void testAliasUsResolvesToNa() {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("us", "contentDelivery"));
    }

    @Test
    public void testAliasUppercaseEU() {
        assertEquals("https://eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("EU", "contentDelivery"));
    }

    @Test
    public void testAliasAwsNaHyphen() {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("aws-na", "contentDelivery"));
    }

    @Test
    public void testAliasAwsNaUnderscore() {
        assertEquals("https://cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("aws_na", "contentDelivery"));
    }

    @Test
    public void testAliasAzureNaUnderscore() {
        assertEquals("https://azure-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("azure_na", "contentDelivery"));
    }

    @Test
    public void testAliasAzureNaUppercase() {
        assertEquals("https://azure-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("AZURE_NA", "contentDelivery"));
    }

    @Test
    public void testAliasGcpNaUnderscore() {
        assertEquals("https://gcp-na-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("gcp_na", "contentDelivery"));
    }

    @Test
    public void testAliasGcpEuUppercase() {
        assertEquals("https://gcp-eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("GCP-EU", "contentDelivery"));
    }

    // ── services ──────────────────────────────────────────────────────────────

    @Test
    public void testNaContentManagement() {
        assertEquals("https://api.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentManagement"));
    }

    @Test
    public void testEuContentManagement() {
        assertEquals("https://eu-api.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentManagement"));
    }

    @Test
    public void testNaGraphqlDelivery() {
        assertEquals("https://graphql.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "graphqlDelivery"));
    }

    @Test
    public void testNaAuth() {
        assertEquals("https://auth-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "auth"));
    }

    @Test
    public void testEuPreview() {
        assertEquals("https://eu-rest-preview.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "preview"));
    }

    @Test
    public void testNaApplication() {
        assertEquals("https://app.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "application"));
    }

    @Test
    public void testNaAssetManagement() {
        assertEquals("https://am-api.contentstack.com",
                Endpoint.getContentstackEndpoint("na", "assetManagement"));
    }

    // ── omitHttps ─────────────────────────────────────────────────────────────

    @Test
    public void testOmitHttpsNaContentDelivery() {
        assertEquals("cdn.contentstack.io",
                Endpoint.getContentstackEndpoint("na", "contentDelivery", true));
    }

    @Test
    public void testOmitHttpsEuContentDelivery() {
        assertEquals("eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentDelivery", true));
    }

    @Test
    public void testOmitHttpsAzureNaContentManagement() {
        assertEquals("azure-na-api.contentstack.com",
                Endpoint.getContentstackEndpoint("azure-na", "contentManagement", true));
    }

    @Test
    public void testOmitHttpsFalseReturnsFullUrl() {
        String url = Endpoint.getContentstackEndpoint("gcp-eu", "contentDelivery", false);
        assertTrue(url.startsWith("https://"));
    }

    // ── getAllEndpoints ───────────────────────────────────────────────────────

    @Test
    public void testGetAllEndpointsNaContainsContentDelivery() {
        Map<String, String> endpoints = Endpoint.getAllEndpoints("na");
        assertTrue(endpoints.containsKey("contentDelivery"));
        assertEquals("https://cdn.contentstack.io", endpoints.get("contentDelivery"));
    }

    @Test
    public void testGetAllEndpointsEuSize() {
        Map<String, String> endpoints = Endpoint.getAllEndpoints("eu");
        assertFalse(endpoints.isEmpty());
        assertTrue(endpoints.size() >= 4);
    }

    @Test
    public void testGetAllEndpointsOmitHttps() {
        Map<String, String> endpoints = Endpoint.getAllEndpoints("na", true);
        for (String url : endpoints.values()) {
            assertFalse("Expected no https:// prefix but got: " + url, url.startsWith("https://"));
        }
    }

    @Test
    public void testGetAllEndpointsAzureNaOmitHttps() {
        Map<String, String> endpoints = Endpoint.getAllEndpoints("azure-na", true);
        assertEquals("azure-na-cdn.contentstack.com", endpoints.get("contentDelivery"));
    }

    // ── error cases ───────────────────────────────────────────────────────────

    @Test
    public void testEmptyRegionThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("", "contentDelivery"));
    }

    @Test
    public void testBlankRegionThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("   ", "contentDelivery"));
    }

    @Test
    public void testUnknownServiceThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("na", "cms"));
    }

    @Test
    public void testServiceNotAvailableInRegionThrows() {
        // assetManagement exists only in NA
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getContentstackEndpoint("eu", "assetManagement"));
    }

    @Test
    public void testGetAllEndpointsEmptyRegionThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Endpoint.getAllEndpoints(""));
    }

    // ── caching ───────────────────────────────────────────────────────────────

    @Test
    public void testMultipleCallsReturnSameResult() {
        String url1 = Endpoint.getContentstackEndpoint("eu", "contentDelivery");
        String url2 = Endpoint.getContentstackEndpoint("eu", "contentDelivery");
        assertEquals(url1, url2);
    }

    @Test
    public void testCacheResetAllowsReload() {
        String url1 = Endpoint.getContentstackEndpoint("na", "contentDelivery");
        Endpoint.resetCache();
        String url2 = Endpoint.getContentstackEndpoint("na", "contentDelivery");
        assertEquals(url1, url2);
    }

    @Test
    public void testResetCacheClearsLiveRefreshFlag() {
        Endpoint.resetCache();
        String url = Endpoint.getContentstackEndpoint("na", "contentDelivery");
        assertEquals("https://cdn.contentstack.io", url);
    }

    // ── proxy-aware resolution ────────────────────────────────────────────────

    @Test
    public void testProxyOverloadResolvesBundledRegion() {
        // A bundled region never triggers the live download, so the proxy is unused but the
        // proxy-aware overload must still resolve correctly.
        java.net.Proxy proxy = new java.net.Proxy(
                java.net.Proxy.Type.HTTP, new java.net.InetSocketAddress("127.0.0.1", 8080));
        assertEquals("eu-cdn.contentstack.com",
                Endpoint.getContentstackEndpoint("eu", "contentDelivery", true, proxy));
    }
}
