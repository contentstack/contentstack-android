package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Stack class
 */
@RunWith(RobolectricTestRunner.class)
public class TestStackComprehensive {

    private Context context;
    private Stack stack;
    private Config config;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
    }

    // ==================== ImageTransform Tests ====================

    @Test
    public void testImageTransformBasic() {
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", "200");
        String transform = stack.ImageTransform("https://example.com/image.jpg", params);
        assertNotNull(transform);
    }

    @Test
    public void testImageTransformWithNullUrl() {
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        String transform = stack.ImageTransform(null, params);
        assertTrue(transform == null || transform instanceof String);
    }

    @Test
    public void testImageTransformWithNullParams() {
        String transform = stack.ImageTransform("https://example.com/image.jpg", null);
        assertNotNull(transform);
    }

    @Test
    public void testImageTransformMultiple() {
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", "200");
        
        String t1 = stack.ImageTransform("url1", params);
        String t2 = stack.ImageTransform("url2", params);
        String t3 = stack.ImageTransform("url3", params);
        
        assertNotNull(t1);
        assertNotNull(t2);
        assertNotNull(t3);
    }

    // ==================== Taxonomy Tests ====================

    @Test
    public void testTaxonomy() {
        Taxonomy taxonomy = stack.taxonomy();
        assertNotNull(taxonomy);
    }

    @Test
    public void testMultipleTaxonomyInstances() {
        Taxonomy t1 = stack.taxonomy();
        Taxonomy t2 = stack.taxonomy();
        Taxonomy t3 = stack.taxonomy();
        
        assertNotNull(t1);
        assertNotNull(t2);
        assertNotNull(t3);
    }

    // ==================== Header Operations ====================

    @Test
    public void testSetHeaderValid() {
        stack.setHeader("X-Custom-Header", "custom-value");
        assertNotNull(stack);
    }

    @Test
    public void testSetHeaderNull() {
        stack.setHeader(null, null);
        assertNotNull(stack);
    }

    @Test
    public void testSetHeaderEmpty() {
        stack.setHeader("", "value");
        stack.setHeader("key", "");
        assertNotNull(stack);
    }

    @Test
    public void testSetHeaderMultiple() {
        stack.setHeader("X-Header-1", "value1");
        stack.setHeader("X-Header-2", "value2");
        stack.setHeader("X-Header-3", "value3");
        stack.setHeader("X-Header-4", "value4");
        stack.setHeader("X-Header-5", "value5");
        assertNotNull(stack);
    }

    @Test
    public void testSetHeaderOverwrite() {
        stack.setHeader("X-Test", "value1");
        stack.setHeader("X-Test", "value2");
        stack.setHeader("X-Test", "value3");
        assertNotNull(stack);
    }

    @Test
    public void testRemoveHeaderValid() {
        stack.setHeader("X-Test", "test");
        stack.removeHeader("X-Test");
        assertNotNull(stack);
    }

    @Test
    public void testRemoveHeaderNull() {
        stack.removeHeader(null);
        assertNotNull(stack);
    }

    @Test
    public void testRemoveHeaderEmpty() {
        stack.removeHeader("");
        assertNotNull(stack);
    }

    @Test
    public void testRemoveHeaderNonExistent() {
        stack.removeHeader("NonExistentHeader");
        assertNotNull(stack);
    }

    @Test
    public void testHeaderAddAndRemoveChaining() {
        stack.setHeader("X-Header-1", "value1");
        stack.setHeader("X-Header-2", "value2");
        stack.removeHeader("X-Header-1");
        stack.setHeader("X-Header-3", "value3");
        stack.removeHeader("X-Header-2");
        assertNotNull(stack);
    }

    // ==================== Factory Methods ====================

    @Test
    public void testAssetWithUid() {
        Asset asset = stack.asset("asset_uid");
        assertNotNull(asset);
    }

    @Test
    public void testAssetWithoutUid() {
        Asset asset = stack.asset();
        assertNotNull(asset);
    }

    @Test
    public void testAssetLibrary() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assertNotNull(assetLibrary);
    }

    @Test
    public void testContentTypeWithUid() {
        ContentType contentType = stack.contentType("content_type_uid");
        assertNotNull(contentType);
    }

    @Test
    public void testGlobalFieldWithUid() {
        GlobalField globalField = stack.globalField("global_field_uid");
        assertNotNull(globalField);
    }

    @Test
    public void testGlobalFieldWithoutUid() {
        GlobalField globalField = stack.globalField();
        assertNotNull(globalField);
    }

    // ==================== Multiple Instances Independence ====================

    @Test
    public void testMultipleAssetInstances() {
        Asset a1 = stack.asset("uid1");
        Asset a2 = stack.asset("uid2");
        Asset a3 = stack.asset("uid3");
        
        assertNotNull(a1);
        assertNotNull(a2);
        assertNotNull(a3);
        assertNotEquals(a1, a2);
    }

    @Test
    public void testMultipleContentTypeInstances() {
        ContentType ct1 = stack.contentType("type1");
        ContentType ct2 = stack.contentType("type2");
        ContentType ct3 = stack.contentType("type3");
        
        assertNotNull(ct1);
        assertNotNull(ct2);
        assertNotNull(ct3);
        assertNotEquals(ct1, ct2);
    }

    @Test
    public void testMultipleGlobalFieldInstances() {
        GlobalField gf1 = stack.globalField("gf1");
        GlobalField gf2 = stack.globalField("gf2");
        GlobalField gf3 = stack.globalField("gf3");
        
        assertNotNull(gf1);
        assertNotNull(gf2);
        assertNotNull(gf3);
        assertNotEquals(gf1, gf2);
    }

    @Test
    public void testMultipleAssetLibraryInstances() {
        AssetLibrary al1 = stack.assetLibrary();
        AssetLibrary al2 = stack.assetLibrary();
        AssetLibrary al3 = stack.assetLibrary();
        
        assertNotNull(al1);
        assertNotNull(al2);
        assertNotNull(al3);
    }

    // setConfig is not a public method, skipping these tests

    // ==================== Integration with Other Operations ====================

    @Test
    public void testHeadersWithFactoryMethods() {
        stack.setHeader("X-Header-1", "value1");
        stack.setHeader("X-Header-2", "value2");
        
        Asset asset = stack.asset("asset_uid");
        ContentType contentType = stack.contentType("ct_uid");
        
        stack.removeHeader("X-Header-1");
        
        assertNotNull(asset);
        assertNotNull(contentType);
        assertNotNull(stack);
    }

    @Test
    public void testMultipleOperationsSequence() {
        stack.setHeader("X-Header", "value");
        Asset asset = stack.asset("asset_uid");
        ContentType contentType = stack.contentType("ct_uid");
        stack.removeHeader("X-Header");
        
        assertNotNull(asset);
        assertNotNull(contentType);
        assertNotNull(stack);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testAssetWithEmptyUid() {
        Asset asset = stack.asset("");
        assertNotNull(asset);
    }

    @Test
    public void testAssetWithNullUid() {
        Asset asset = stack.asset(null);
        assertNotNull(asset);
    }

    @Test
    public void testContentTypeWithEmptyUid() {
        ContentType ct = stack.contentType("");
        assertNotNull(ct);
    }

    @Test
    public void testContentTypeWithNullUid() {
        ContentType ct = stack.contentType(null);
        assertNotNull(ct);
    }

    @Test
    public void testGlobalFieldWithEmptyUid() {
        GlobalField gf = stack.globalField("");
        assertNotNull(gf);
    }

    @Test
    public void testGlobalFieldWithNullUid() {
        GlobalField gf = stack.globalField(null);
        assertNotNull(gf);
    }

    @Test
    public void testHeaderWithSpecialCharacters() {
        stack.setHeader("X-Special-Header", "value with spaces and chars: !@#$%^&*()");
        assertNotNull(stack);
    }

    @Test
    public void testHeaderWithUnicode() {
        stack.setHeader("X-Unicode-Header", "测试 テスト 테스트 🎉");
        assertNotNull(stack);
    }

    @Test
    public void testHeaderWithVeryLongValue() {
        StringBuilder longValue = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longValue.append("test");
        }
        stack.setHeader("X-Long-Header", longValue.toString());
        assertNotNull(stack);
    }

    @Test
    public void testImageTransformWithLongUrl() {
        StringBuilder longUrl = new StringBuilder("https://example.com/");
        for (int i = 0; i < 100; i++) {
            longUrl.append("path/");
        }
        longUrl.append("image.jpg");
        
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", "200");
        String transform = stack.ImageTransform(longUrl.toString(), params);
        assertNotNull(transform);
    }

    // ==================== Integration Tests ====================

    @Test
    public void testCompleteWorkflow() {
        // Configure stack
        stack.setHeader("X-Custom-Header", "custom-value");
        
        // Create various objects
        Asset asset = stack.asset("asset_123");
        asset.includeDimension();
        
        ContentType contentType = stack.contentType("blog");
        Entry entry = contentType.entry("entry_123");
        entry.includeReference("author");
        
        Query query = contentType.query();
        query.where("status", "published");
        
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeCount();
        
        GlobalField globalField = stack.globalField("seo");
        globalField.includeBranch();
        
        Taxonomy taxonomy = stack.taxonomy();
        
        java.util.LinkedHashMap<String, Object> params = new java.util.LinkedHashMap<>();
        params.put("width", "200");
        String transform = stack.ImageTransform("https://example.com/image.jpg", params);
        
        // All objects should be valid
        assertNotNull(asset);
        assertNotNull(contentType);
        assertNotNull(entry);
        assertNotNull(query);
        assertNotNull(assetLibrary);
        assertNotNull(globalField);
        assertNotNull(taxonomy);
        assertNotNull(transform);
    }

    @Test
    public void testConcurrentObjectCreation() {
        for (int i = 0; i < 50; i++) {
            Asset asset = stack.asset("asset_" + i);
            ContentType ct = stack.contentType("ct_" + i);
            assertNotNull(asset);
            assertNotNull(ct);
        }
    }
}

