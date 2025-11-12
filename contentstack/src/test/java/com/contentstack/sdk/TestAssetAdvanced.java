package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;

import static org.junit.Assert.*;
/**
 * Comprehensive tests for Asset class to improve coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestAssetAdvanced {

    private Context context;
    private Stack stack;
    private Asset asset;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        asset = stack.asset("test_asset_uid");
    }

    // ==================== CONSTRUCTOR Tests ====================

    @Test
    public void testAssetCreation() {
        assertNotNull(asset);
    }

    @Test
    public void testAssetCreationWithUid() {
        Asset assetWithUid = stack.asset("specific_asset_uid");
        assertNotNull(assetWithUid);
    }

    // ==================== CONFIGURE Tests ====================

    @Test
    public void testConfigureWithValidJSON() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "asset123");
        assetJson.put("content_type", "image/jpeg");
        assetJson.put("file_size", "1024");
        assetJson.put("filename", "test.jpg");
        assetJson.put("url", "https://example.com/test.jpg");

        Asset result = asset.configure(assetJson);
        assertNotNull(result);
        assertSame(asset, result); // Should return same instance
    }

    @Test
    public void testConfigureWithMinimalJSON() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "asset_minimal");

        Asset result = asset.configure(assetJson);
        assertNotNull(result);
    }

    @Test
    public void testConfigureWithEmptyJSON() throws JSONException {
        JSONObject emptyJson = new JSONObject();
        Asset result = asset.configure(emptyJson);
        assertNotNull(result);
    }

    @Test
    public void testConfigureMultipleTimes() throws JSONException {
        JSONObject json1 = new JSONObject();
        json1.put("uid", "asset1");
        json1.put("filename", "file1.jpg");

        JSONObject json2 = new JSONObject();
        json2.put("uid", "asset2");
        json2.put("filename", "file2.jpg");

        asset.configure(json1);
        Asset result = asset.configure(json2); // Reconfigure
        
        assertNotNull(result);
    }

    // ==================== HEADER Tests ====================

    @Test
    public void testSetHeader() {
        asset.setHeader("custom-header", "custom-value");
        assertNotNull(asset);
    }

    @Test
    public void testSetHeaderMultiple() {
        asset.setHeader("header1", "value1");
        asset.setHeader("header2", "value2");
        asset.setHeader("header3", "value3");
        assertNotNull(asset);
    }

    @Test
    public void testSetHeaderWithNull() {
        asset.setHeader(null, "value");
        asset.setHeader("key", null);
        assertNotNull(asset);
    }

    @Test
    public void testSetHeaderWithEmptyStrings() {
        asset.setHeader("", "value");
        asset.setHeader("key", "");
        assertNotNull(asset);
    }

    @Test
    public void testRemoveHeader() {
        asset.setHeader("test-header", "test-value");
        asset.removeHeader("test-header");
        assertNotNull(asset);
    }

    @Test
    public void testRemoveHeaderThatDoesntExist() {
        asset.removeHeader("non-existent-header");
        assertNotNull(asset);
    }

    @Test
    public void testRemoveHeaderWithNull() {
        asset.removeHeader(null);
        assertNotNull(asset);
    }

    @Test
    public void testRemoveHeaderWithEmptyString() {
        asset.removeHeader("");
        assertNotNull(asset);
    }

    // ==================== ADD PARAM Tests ====================

    @Test
    public void testAddParam() {
        Asset result = asset.addParam("include_dimension", "true");
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testAddParamMultiple() {
        asset.addParam("param1", "value1");
        asset.addParam("param2", "value2");
        assertNotNull(asset);
    }

    @Test
    public void testAddParamWithNull() {
        Asset result = asset.addParam(null, "value");
        assertNotNull(result);
        
        result = asset.addParam("key", null);
        assertNotNull(result);
    }

    @Test
    public void testAddParamWithEmptyStrings() {
        asset.addParam("", "value");
        asset.addParam("key", "");
        assertNotNull(asset);
    }

    @Test
    public void testAddParamOverwrite() {
        asset.addParam("dimension", "true");
        asset.addParam("dimension", "false"); // Overwrite
        assertNotNull(asset);
    }

    // ==================== GET METHODS Tests ====================

    @Test
    public void testGetAssetUid() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "test_uid_123");
        
        asset.configure(assetJson);
        String uid = asset.getAssetUid();
        
        assertNotNull(uid);
        assertEquals("test_uid_123", uid);
    }

    @Test
    public void testGetAssetUidBeforeConfigure() {
        // Asset uid should be set from constructor
        String uid = asset.getAssetUid();
        assertNotNull(uid);
        assertEquals("test_asset_uid", uid);
    }

    @Test
    public void testGetFileType() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("content_type", "image/png");
        
        asset.configure(assetJson);
        String fileType = asset.getFileType();
        
        assertNotNull(fileType);
        assertEquals("image/png", fileType);
    }

    @Test
    public void testGetFileTypeBeforeConfigure() {
        String fileType = asset.getFileType();
        // Should return null or empty before configuration
        assertTrue(fileType == null || fileType.isEmpty());
    }

    @Test
    public void testGetFileSize() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("file_size", "2048");
        
        asset.configure(assetJson);
        String fileSize = asset.getFileSize();
        
        assertNotNull(fileSize);
        assertEquals("2048", fileSize);
    }

    @Test
    public void testGetFileName() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("filename", "my_image.jpg");
        
        asset.configure(assetJson);
        String fileName = asset.getFileName();
        
        assertNotNull(fileName);
        assertEquals("my_image.jpg", fileName);
    }

    @Test
    public void testGetUrl() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("url", "https://cdn.example.com/asset.jpg");
        
        asset.configure(assetJson);
        String url = asset.getUrl();
        
        assertNotNull(url);
        assertEquals("https://cdn.example.com/asset.jpg", url);
    }

    @Test
    public void testToJSON() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "json_test");
        assetJson.put("title", "Test Asset");
        
        asset.configure(assetJson);
        JSONObject result = asset.toJSON();
        
        assertNotNull(result);
    }

    @Test
    public void testGetTags() throws JSONException {
        String[] tags = asset.getTags();
        // Tags should be null or empty array before configuration
        assertTrue(tags == null || tags.length == 0);
    }

    // ==================== SET TAGS Tests ====================

    @Test
    public void testSetTags() {
        String[] tags = {"tag1", "tag2", "tag3"};
        asset.setTags(tags);
        
        String[] result = asset.getTags();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals("tag1", result[0]);
    }

    @Test
    public void testSetTagsWithNull() {
        asset.setTags(null);
        String[] result = asset.getTags();
        assertTrue(result == null || result.length == 0);
    }

    @Test
    public void testSetTagsWithEmptyArray() {
        asset.setTags(new String[]{});
        String[] result = asset.getTags();
        assertTrue(result == null || result.length == 0);
    }

    @Test
    public void testSetTagsOverwrite() {
        asset.setTags(new String[]{"old1", "old2"});
        asset.setTags(new String[]{"new1", "new2", "new3"});
        
        String[] result = asset.getTags();
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    // ==================== CACHE POLICY Tests ====================

    @Test
    public void testSetCachePolicyNetworkOnly() {
        asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(asset);
    }

    @Test
    public void testSetCachePolicyCacheOnly() {
        asset.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(asset);
    }

    @Test
    public void testSetCachePolicyCacheElseNetwork() {
        asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(asset);
    }

    @Test
    public void testSetCachePolicyCacheThenNetwork() {
        asset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(asset);
    }

    @Test
    public void testSetCachePolicyNetworkElseCache() {
        asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(asset);
    }

    @Test
    public void testSetCachePolicyIgnoreCache() {
        asset.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(asset);
    }

    // ==================== GET URL Tests ====================

    @Test
    public void testGetUrlWithoutConfiguration() {
        String url = asset.getUrl();
        // URL should be returned (might be null or empty without configuration)
        // This tests method doesn't throw exception
    }

    @Test
    public void testGetUrlWithConfiguration() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("url", "https://cdn.example.com/image.png");
        
        asset.configure(assetJson);
        String url = asset.getUrl();
        assertNotNull(url);
    }

    // ==================== METHOD CHAINING Tests ====================

    @Test
    public void testMethodChaining() {
        asset.setHeader("custom-header", "value");
        Asset result = asset.addParam("dimension", "true");
        asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        assertNotNull(result);
    }

    // ==================== COMPLEX SCENARIOS Tests ====================

    @Test
    public void testCompleteAssetWorkflow() throws JSONException {
        JSONObject assetData = new JSONObject();
        assetData.put("uid", "complete_asset");
        assetData.put("content_type", "image/jpeg");
        assetData.put("file_size", "1048576");
        assetData.put("filename", "photo.jpg");
        assetData.put("url", "https://cdn.example.com/photo.jpg");
        assetData.put("title", "My Photo");
        
        asset.configure(assetData);
        asset.setHeader("api-version", "v3");
        asset.addParam("include_dimension", "true");
        asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertEquals("complete_asset", asset.getAssetUid());
        assertEquals("image/jpeg", asset.getFileType());
        assertEquals("1048576", asset.getFileSize());
        assertEquals("photo.jpg", asset.getFileName());
        assertNotNull(asset.getUrl());
    }

    @Test
    public void testReconfigureAsset() throws JSONException {
        JSONObject firstConfig = new JSONObject();
        firstConfig.put("uid", "asset_v1");
        firstConfig.put("filename", "version1.jpg");
        
        asset.configure(firstConfig);
        assertEquals("asset_v1", asset.getAssetUid());
        
        JSONObject secondConfig = new JSONObject();
        secondConfig.put("uid", "asset_v2");
        secondConfig.put("filename", "version2.jpg");
        
        asset.configure(secondConfig);
        assertEquals("asset_v2", asset.getAssetUid());
    }

    @Test
    public void testAssetWithSpecialCharacters() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "asset_with_特殊字符");
        assetJson.put("filename", "file with spaces & special.jpg");
        assetJson.put("url", "https://example.com/path/to/file%20name.jpg");
        
        asset.configure(assetJson);
        assertNotNull(asset.getAssetUid());
        assertNotNull(asset.getFileName());
        assertNotNull(asset.getUrl());
    }

    @Test
    public void testAssetWithVeryLargeFileSize() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("file_size", "10737418240"); // 10GB
        
        asset.configure(assetJson);
        assertEquals("10737418240", asset.getFileSize());
    }

    @Test
    public void testAssetGetUrl() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "image_asset");
        assetJson.put("content_type", "image/png");
        assetJson.put("url", "https://cdn.example.com/image.png");
        
        asset.configure(assetJson);
        
        String url = asset.getUrl();
        assertNotNull(url);
    }

    @Test
    public void testAssetWithAllSupportedContentTypes() throws JSONException {
        String[] contentTypes = {
            "image/jpeg", "image/png", "image/gif", "image/webp",
            "video/mp4", "video/mpeg", "video/quicktime",
            "audio/mp3", "audio/mpeg", "audio/wav",
            "application/pdf", "application/zip", "text/plain"
        };
        
        for (String contentType : contentTypes) {
            JSONObject assetJson = new JSONObject();
            assetJson.put("content_type", contentType);
            asset.configure(assetJson);
            assertEquals(contentType, asset.getFileType());
        }
    }

    // ==================== DATE GETTER TESTS ====================

    @Test
    public void testGetCreateAt() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "date_test");
        assetJson.put("created_at", "2023-01-15T10:30:00.000Z");
        
        asset.configure(assetJson);
        
        java.util.Calendar calendar = asset.getCreateAt();
        assertNotNull(calendar);
    }

    @Test
    public void testGetUpdateAt() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "date_test");
        assetJson.put("updated_at", "2023-06-20T14:45:30.000Z");
        
        asset.configure(assetJson);
        
        java.util.Calendar calendar = asset.getUpdateAt();
        assertNotNull(calendar);
    }

    @Test
    public void testGetDeleteAt() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "date_test");
        assetJson.put("deleted_at", "2023-12-31T23:59:59.000Z");
        
        asset.configure(assetJson);
        
        java.util.Calendar calendar = asset.getDeleteAt();
        assertNotNull(calendar);
    }

    @Test
    public void testGetDeleteAtWhenNull() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "date_test");
        // No deleted_at field
        
        asset.configure(assetJson);
        
        java.util.Calendar calendar = asset.getDeleteAt();
        assertNull(calendar);
    }

    // ==================== USER GETTER TESTS ====================

    @Test
    public void testGetCreatedBy() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "user_test");
        assetJson.put("created_by", "user_creator_123");
        
        asset.configure(assetJson);
        
        String createdBy = asset.getCreatedBy();
        assertEquals("user_creator_123", createdBy);
    }

    @Test
    public void testGetUpdatedBy() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "user_test");
        assetJson.put("updated_by", "user_updater_456");
        
        asset.configure(assetJson);
        
        String updatedBy = asset.getUpdatedBy();
        assertEquals("user_updater_456", updatedBy);
    }

    @Test
    public void testGetDeletedBy() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "user_test");
        assetJson.put("deleted_by", "user_deleter_789");
        
        asset.configure(assetJson);
        
        String deletedBy = asset.getDeletedBy();
        assertEquals("user_deleter_789", deletedBy);
    }

    @Test
    public void testGetDeletedByWhenEmpty() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "user_test");
        // No deleted_by field
        
        asset.configure(assetJson);
        
        String deletedBy = asset.getDeletedBy();
        assertEquals("", deletedBy);
    }

    // ==================== COMPREHENSIVE CONFIGURATION TESTS ====================

    @Test
    public void testConfigureWithAllDateFields() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "complete_date_test");
        assetJson.put("created_at", "2023-01-01T00:00:00.000Z");
        assetJson.put("updated_at", "2023-06-15T12:30:00.000Z");
        assetJson.put("deleted_at", "2023-12-31T23:59:59.000Z");
        assetJson.put("created_by", "creator_user");
        assetJson.put("updated_by", "updater_user");
        assetJson.put("deleted_by", "deleter_user");
        
        asset.configure(assetJson);
        
        // Verify all date fields
        assertNotNull(asset.getCreateAt());
        assertNotNull(asset.getUpdateAt());
        assertNotNull(asset.getDeleteAt());
        
        // Verify all user fields
        assertEquals("creator_user", asset.getCreatedBy());
        assertEquals("updater_user", asset.getUpdatedBy());
        assertEquals("deleter_user", asset.getDeletedBy());
    }

    @Test
    public void testConfigureWithMissingDateFields() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "minimal_date_test");
        // No date or user fields
        
        asset.configure(assetJson);
        
        // deleted_at should be null when not provided
        assertNull(asset.getDeleteAt());
        
        // deleted_by should be empty string when not provided
        assertEquals("", asset.getDeletedBy());
    }

    @Test
    public void testGettersWithCompleteAssetData() throws JSONException {
        JSONObject completeData = new JSONObject();
        completeData.put("uid", "complete_asset");
        completeData.put("content_type", "image/jpeg");
        completeData.put("file_size", "3145728");
        completeData.put("filename", "complete_image.jpg");
        completeData.put("url", "https://cdn.example.com/complete_image.jpg");
        completeData.put("created_at", "2023-03-15T08:20:00.000Z");
        completeData.put("updated_at", "2023-09-20T16:45:00.000Z");
        completeData.put("created_by", "blt_creator");
        completeData.put("updated_by", "blt_updater");
        
        asset.configure(completeData);
        
        // Test all getters
        assertEquals("complete_asset", asset.getAssetUid());
        assertEquals("image/jpeg", asset.getFileType());
        assertEquals("3145728", asset.getFileSize());
        assertEquals("complete_image.jpg", asset.getFileName());
        assertEquals("https://cdn.example.com/complete_image.jpg", asset.getUrl());
        assertNotNull(asset.getCreateAt());
        assertNotNull(asset.getUpdateAt());
        assertNull(asset.getDeleteAt());
        assertEquals("blt_creator", asset.getCreatedBy());
        assertEquals("blt_updater", asset.getUpdatedBy());
        assertEquals("", asset.getDeletedBy());
        assertNotNull(asset.toJSON());
    }

    @Test
    public void testDateFieldsWithDifferentFormats() throws JSONException {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "date_format_test");
        assetJson.put("created_at", "2023-01-01T00:00:00.000Z");
        assetJson.put("updated_at", "2023-12-31T23:59:59.999Z");
        
        asset.configure(assetJson);
        
        assertNotNull(asset.getCreateAt());
        assertNotNull(asset.getUpdateAt());
    }

    // ==================== INCLUDE METHOD TESTS ====================

    @Test
    public void testIncludeDimension() {
        Asset result = asset.includeDimension();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testIncludeFallback() {
        Asset result = asset.includeFallback();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testIncludeBranch() {
        Asset result = asset.includeBranch();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testMultipleIncludesCombined() {
        Asset result = asset
            .includeDimension()
            .includeFallback()
            .includeBranch();
        
        assertNotNull(result);
        assertSame(asset, result);
    }
}

