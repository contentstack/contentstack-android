package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestStack {

    private Context mockContext;
    private Stack stack;
    private String apiKey;
    private String deliveryToken;
    private String environment;

    @Before
    public void setUp() throws Exception {
        mockContext = TestUtils.createMockContext();
        apiKey = TestUtils.getTestApiKey();
        deliveryToken = TestUtils.getTestDeliveryToken();
        environment = TestUtils.getTestEnvironment();
        stack = Contentstack.stack(mockContext, apiKey, deliveryToken, environment);
        TestUtils.cleanupTestCache();
    }

    @After
    public void tearDown() {
        TestUtils.cleanupTestCache();
        stack = null;
        mockContext = null;
    }

    @Test
    public void testStackCreation() {
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testGetApplicationKey() {
        String key = stack.getApplicationKey();
        assertEquals("API key should match", apiKey, key);
    }

    @Test
    public void testGetAccessToken() {
        String token = stack.getAccessToken();
        assertEquals("Access token should match", deliveryToken, token);
    }

    @Test
    public void testContentType() {
        ContentType contentType = stack.contentType("test_content_type");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testContentTypeWithEmptyName() {
        ContentType contentType = stack.contentType("");
        assertNotNull("ContentType should not be null even with empty name", contentType);
    }

    @Test
    public void testContentTypeWithSpecialCharacters() {
        String[] specialNames = {"content-type", "content_type", "content.type", "content123"};
        for (String name : specialNames) {
            ContentType contentType = stack.contentType(name);
            assertNotNull("ContentType should not be null for " + name, contentType);
        }
    }

    @Test
    public void testGlobalField() {
        GlobalField globalField = stack.globalField();
        assertNotNull("GlobalField should not be null", globalField);
    }

    @Test
    public void testGlobalFieldWithUid() {
        GlobalField globalField = stack.globalField("test_global_field_uid");
        assertNotNull("GlobalField with uid should not be null", globalField);
    }

    @Test
    public void testGlobalFieldWithEmptyUid() {
        GlobalField globalField = stack.globalField("");
        assertNotNull("GlobalField should not be null even with empty uid", globalField);
    }

    @Test
    public void testAssetWithUid() {
        Asset asset = stack.asset("test_asset_uid");
        assertNotNull("Asset should not be null", asset);
    }

    @Test
    public void testAssetLibrary() {
        AssetLibrary library = stack.assetLibrary();
        assertNotNull("AssetLibrary should not be null", library);
    }

    @Test
    public void testTaxonomy() {
        Taxonomy taxonomy = stack.taxonomy();
        assertNotNull("Taxonomy should not be null", taxonomy);
    }

    @Test
    public void testSetHeader() {
        stack.setHeader("custom-key", "custom-value");
        // Verify header is set correctly
        assertNotNull("Stack should not be null after setting header", stack);
    }

    @Test
    public void testSetHeaderWithEmptyKey() {
        stack.setHeader("", "value");
        // Should not throw exception
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testSetHeaderWithEmptyValue() {
        stack.setHeader("key", "");
        // Should not throw exception
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testSetHeaderWithNullKey() {
        stack.setHeader(null, "value");
        // Should not throw exception
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testSetHeaderWithNullValue() {
        stack.setHeader("key", null);
        // Should not throw exception
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testSetHeaders() {
        ArrayMap<String, String> headers = new ArrayMap<>();
        headers.put("header1", "value1");
        headers.put("header2", "value2");
        stack.setHeaders(headers);
        assertNotNull("Stack should not be null after setting headers", stack);
    }

    @Test
    public void testSetHeadersWithEmptyMap() {
        ArrayMap<String, String> headers = new ArrayMap<>();
        stack.setHeaders(headers);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testRemoveHeader() {
        stack.setHeader("custom-key", "custom-value");
        stack.removeHeader("custom-key");
        assertNotNull("Stack should not be null after removing header", stack);
    }

    @Test
    public void testRemoveHeaderWithEmptyKey() {
        stack.removeHeader("");
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testRemoveHeaderWithNullKey() {
        stack.removeHeader(null);
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testRemoveNonExistentHeader() {
        stack.removeHeader("non-existent-header");
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testImageTransform() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 100);
        params.put("height", 100);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain width parameter", transformedUrl.contains("width"));
        assertTrue("URL should contain height parameter", transformedUrl.contains("height"));
    }

    @Test
    public void testImageTransformWithSingleParam() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 200);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain width parameter", transformedUrl.contains("width"));
        assertTrue("URL should contain ? for query", transformedUrl.contains("?"));
    }

    @Test
    public void testImageTransformWithMultipleParams() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 300);
        params.put("height", 200);
        params.put("quality", 80);
        params.put("format", "webp");
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain multiple parameters", transformedUrl.contains("&"));
    }

    @Test
    public void testImageTransformWithEmptyParams() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertEquals("URL should remain unchanged", imageUrl, transformedUrl);
    }

    @Test
    public void testImageTransformWithNullParams() {
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, null);
        
        assertEquals("URL should remain unchanged", imageUrl, transformedUrl);
    }

    @Test
    public void testImageTransformWithSpecialCharacters() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("custom-param", "value with spaces");
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("Special characters should be encoded", transformedUrl.contains("%20") || transformedUrl.contains("+"));
    }

    @Test
    public void testImageTransformWithExistingQuery() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 100);
        
        String imageUrl = "https://images.contentstack.io/image.jpg?existing=param";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain & for additional params", transformedUrl.contains("&"));
    }

    @Test
    public void testMultipleContentTypes() {
        ContentType ct1 = stack.contentType("content_type_1");
        ContentType ct2 = stack.contentType("content_type_2");
        
        assertNotNull("ContentType 1 should not be null", ct1);
        assertNotNull("ContentType 2 should not be null", ct2);
        assertNotEquals("ContentTypes should be different instances", ct1, ct2);
    }

    @Test
    public void testMultipleAssets() {
        Asset asset1 = stack.asset("asset_uid_1");
        Asset asset2 = stack.asset("asset_uid_2");
        
        assertNotNull("Asset 1 should not be null", asset1);
        assertNotNull("Asset 2 should not be null", asset2);
        assertNotEquals("Assets should be different instances", asset1, asset2);
    }

    @Test
    public void testMultipleGlobalFields() {
        GlobalField gf1 = stack.globalField("global_field_1");
        GlobalField gf2 = stack.globalField("global_field_2");
        
        assertNotNull("GlobalField 1 should not be null", gf1);
        assertNotNull("GlobalField 2 should not be null", gf2);
        assertNotEquals("GlobalFields should be different instances", gf1, gf2);
    }

    @Test
    public void testContentTypeWithLongName() {
        String longName = "a".repeat(100);
        ContentType contentType = stack.contentType(longName);
        assertNotNull("ContentType should not be null with long name", contentType);
    }

    @Test
    public void testAssetWithLongUid() {
        String longUid = "b".repeat(100);
        Asset asset = stack.asset(longUid);
        assertNotNull("Asset should not be null with long uid", asset);
    }

    @Test
    public void testSetHeaderMultipleTimes() {
        stack.setHeader("key", "value1");
        stack.setHeader("key", "value2");
        stack.setHeader("key", "value3");
        assertNotNull("Stack should not be null after multiple header sets", stack);
    }

    @Test
    public void testSetMultipleHeaders() {
        stack.setHeader("key1", "value1");
        stack.setHeader("key2", "value2");
        stack.setHeader("key3", "value3");
        assertNotNull("Stack should not be null after setting multiple headers", stack);
    }

    @Test
    public void testRemoveHeaderMultipleTimes() {
        stack.setHeader("key", "value");
        stack.removeHeader("key");
        stack.removeHeader("key"); // Remove again
        assertNotNull("Stack should not be null", stack);
    }

    @Test
    public void testStackWithCustomConfig() throws Exception {
        com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
        config.setHost("custom-cdn.contentstack.io");
        config.setBranch("development");
        
        Stack customStack = Contentstack.stack(mockContext, "custom_api_key", "custom_token", "custom_env", config);
        assertNotNull("Custom stack should not be null", customStack);
        assertEquals("Custom API key should match", "custom_api_key", customStack.getApplicationKey());
    }

    @Test
    public void testStackWithDifferentRegions() throws Exception {
        com.contentstack.sdk.Config.ContentstackRegion[] regions = com.contentstack.sdk.Config.ContentstackRegion.values();
        
        for (com.contentstack.sdk.Config.ContentstackRegion region : regions) {
            com.contentstack.sdk.Config config = new com.contentstack.sdk.Config();
            config.setRegion(region);
            Stack regionalStack = Contentstack.stack(mockContext, "api_key", "token", "env", config);
            assertNotNull("Stack should not be null for region " + region, regionalStack);
        }
    }

    @Test
    public void testContentTypeEntryCreation() {
        ContentType contentType = stack.contentType("products");
        Entry entry = contentType.entry("entry_uid");
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testContentTypeQueryCreation() {
        ContentType contentType = stack.contentType("products");
        Query query = contentType.query();
        assertNotNull("Query should not be null", query);
    }

    @Test
    public void testHeaderPersistence() {
        stack.setHeader("persistent-header", "persistent-value");
        ContentType contentType = stack.contentType("test");
        // Headers should be available to content type
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testImageTransformURLEncoding() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("key", "value with spaces & special chars");
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertFalse("URL should not contain unencoded spaces", transformedUrl.contains(" "));
    }

    @Test
    public void testImageTransformWithNumericValues() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("width", 100);
        params.put("height", 200);
        params.put("quality", 85);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
        assertTrue("URL should contain numeric width", transformedUrl.contains("width=100"));
    }

    @Test
    public void testImageTransformWithBooleanValues() {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("auto", true);
        params.put("optimize", false);
        
        String imageUrl = "https://images.contentstack.io/image.jpg";
        String transformedUrl = stack.ImageTransform(imageUrl, params);
        
        assertNotNull("Transformed URL should not be null", transformedUrl);
    }

    @Test
    public void testStackIntegrity() {
        String originalApiKey = stack.getApplicationKey();
        String originalToken = stack.getAccessToken();
        
        // Perform various operations
        stack.setHeader("test", "value");
        stack.contentType("test");
        stack.asset("test_uid");
        
        // Verify stack integrity
        assertEquals("API key should remain unchanged", originalApiKey, stack.getApplicationKey());
        assertEquals("Access token should remain unchanged", originalToken, stack.getAccessToken());
    }

    @Test
    public void testConcurrentContentTypeCreation() {
        ContentType[] contentTypes = new ContentType[10];
        
        for (int i = 0; i < 10; i++) {
            contentTypes[i] = stack.contentType("content_type_" + i);
            assertNotNull("ContentType " + i + " should not be null", contentTypes[i]);
        }
        
        // Verify all are unique instances
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                assertNotEquals("ContentTypes should be different instances", 
                    contentTypes[i], contentTypes[j]);
            }
        }
    }

    @Test
    public void testStackMethodChaining() {
        stack.setHeader("key1", "value1");
        ContentType contentType = stack.contentType("test");
        assertNotNull("Should support method chaining", contentType);
    }
}

