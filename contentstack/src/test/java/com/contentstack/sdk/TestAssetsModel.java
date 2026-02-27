package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for AssetsModel class
 * Based on Java SDK test patterns
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class TestAssetsModel {

    private JSONObject testAssetsJson;

    @Before
    public void setUp() throws Exception {
        testAssetsJson = new JSONObject();
        
        JSONArray assetsArray = new JSONArray();
        for (int i = 1; i <= 5; i++) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "asset_" + i);
            asset.put("filename", "image_" + i + ".jpg");
            asset.put("content_type", "image/jpeg");
            asset.put("url", "https://example.com/image_" + i + ".jpg");
            asset.put("file_size", String.valueOf(1024 * i));
            asset.put("title", "Image " + i);
            assetsArray.put(asset);
        }
        
        testAssetsJson.put("assets", assetsArray);
    }

    // ==================== BASIC CONSTRUCTOR TESTS ====================

    @Test
    public void testAssetsModelConstructor() {
        AssetsModel model = new AssetsModel(testAssetsJson, false);
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(5, model.objects.size());
    }

    @Test
    public void testAssetsModelFromCache() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testAssetsJson);
        
        AssetsModel model = new AssetsModel(cacheJson, true);
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        // When from cache, it should look for response key
    }

    @Test
    public void testAssetsModelNotFromCache() {
        AssetsModel model = new AssetsModel(testAssetsJson, false);
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
    }

    // ==================== ASSET PARSING TESTS ====================

    @Test
    public void testAssetsModelParsesAssets() {
        AssetsModel model = new AssetsModel(testAssetsJson, false);
        
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(5, model.objects.size());
        
        // Verify first asset
        AssetModel firstAsset = (AssetModel) model.objects.get(0);
        assertEquals("asset_1", firstAsset.uploadedUid);
        assertEquals("image_1.jpg", firstAsset.fileName);
        assertEquals("image/jpeg", firstAsset.contentType);
    }

    @Test
    public void testAssetsModelWithSingleAsset() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        JSONObject asset = new JSONObject();
        asset.put("uid", "single_asset");
        asset.put("filename", "single.jpg");
        asset.put("content_type", "image/jpeg");
        assetsArray.put(asset);
        
        json.put("assets", assetsArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(1, model.objects.size());
        
        AssetModel assetModel = (AssetModel) model.objects.get(0);
        assertEquals("single_asset", assetModel.uploadedUid);
    }

    @Test
    public void testAssetsModelWithEmptyArray() throws Exception {
        JSONObject json = new JSONObject();
        json.put("assets", new JSONArray());
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(0, model.objects.size());
    }

    @Test
    public void testAssetsModelWithNullAssets() {
        JSONObject json = new JSONObject();
        // No "assets" field
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(0, model.objects.size());
    }

    // ==================== DIFFERENT FILE TYPES TESTS ====================

    @Test
    public void testAssetsModelWithDifferentFileTypes() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        // Image
        JSONObject image = new JSONObject();
        image.put("uid", "image_asset");
        image.put("filename", "photo.jpg");
        image.put("content_type", "image/jpeg");
        assetsArray.put(image);
        
        // Video
        JSONObject video = new JSONObject();
        video.put("uid", "video_asset");
        video.put("filename", "video.mp4");
        video.put("content_type", "video/mp4");
        assetsArray.put(video);
        
        // PDF
        JSONObject pdf = new JSONObject();
        pdf.put("uid", "pdf_asset");
        pdf.put("filename", "document.pdf");
        pdf.put("content_type", "application/pdf");
        assetsArray.put(pdf);
        
        json.put("assets", assetsArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(3, model.objects.size());
        
        AssetModel imageAsset = (AssetModel) model.objects.get(0);
        assertEquals("image/jpeg", imageAsset.contentType);
        
        AssetModel videoAsset = (AssetModel) model.objects.get(1);
        assertEquals("video/mp4", videoAsset.contentType);
        
        AssetModel pdfAsset = (AssetModel) model.objects.get(2);
        assertEquals("application/pdf", pdfAsset.contentType);
    }

    // ==================== COMPLEX DATA TESTS ====================

    @Test
    public void testAssetsModelWithComplexAssets() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        for (int i = 1; i <= 3; i++) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "complex_asset_" + i);
            asset.put("filename", "complex_" + i + ".jpg");
            asset.put("content_type", "image/jpeg");
            asset.put("url", "https://example.com/complex_" + i + ".jpg");
            asset.put("file_size", String.valueOf(2048 * i));
            asset.put("title", "Complex Asset " + i);
            
            // Add tags
            JSONArray tags = new JSONArray();
            tags.put("tag1");
            tags.put("tag2");
            asset.put("tags", tags);
            
            assetsArray.put(asset);
        }
        
        json.put("assets", assetsArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(3, model.objects.size());
        
        // Verify assets were parsed with complex data
        for (int i = 0; i < 3; i++) {
            AssetModel asset = (AssetModel) model.objects.get(i);
            assertNotNull(asset);
            assertNotNull(asset.uploadedUid);
            assertNotNull(asset.fileName);
        }
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testAssetsModelWithEmptyJson() {
        JSONObject emptyJson = new JSONObject();
        AssetsModel model = new AssetsModel(emptyJson, false);
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(0, model.objects.size());
    }

    @Test
    public void testAssetsModelWithLargeArray() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray largeArray = new JSONArray();
        
        for (int i = 0; i < 100; i++) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "asset_" + i);
            asset.put("filename", "file_" + i + ".jpg");
            asset.put("content_type", "image/jpeg");
            largeArray.put(asset);
        }
        
        json.put("assets", largeArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(100, model.objects.size());
    }

    @Test
    public void testAssetsModelWithSpecialCharacters() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        JSONObject asset = new JSONObject();
        asset.put("uid", "special_asset");
        asset.put("filename", "image with spaces and special-chars_äöü.jpg");
        asset.put("content_type", "image/jpeg");
        asset.put("url", "https://example.com/special.jpg");
        asset.put("title", "Asset with special chars: äöü ñ 中文 日本語");
        assetsArray.put(asset);
        
        json.put("assets", assetsArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(1, model.objects.size());
        
        AssetModel assetModel = (AssetModel) model.objects.get(0);
        assertEquals("image with spaces and special-chars_äöü.jpg", assetModel.fileName);
    }

    @Test
    public void testAssetsModelWithVariousImageFormats() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        String[] formats = {"jpeg", "png", "gif", "webp", "svg"};
        for (String format : formats) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "asset_" + format);
            asset.put("filename", "image." + format);
            asset.put("content_type", "image/" + format);
            assetsArray.put(asset);
        }
        
        json.put("assets", assetsArray);
        
        AssetsModel model = new AssetsModel(json, false);
        
        assertNotNull("AssetsModel should not be null", model);
        assertEquals(formats.length, model.objects.size());
    }

    // ==================== COMBINED SCENARIOS ====================

    @Test
    public void testAssetsModelFromCacheWithComplexData() throws Exception {
        JSONObject cacheJson = new JSONObject();
        JSONObject response = new JSONObject();
        JSONArray assetsArray = new JSONArray();
        
        for (int i = 1; i <= 10; i++) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "cached_asset_" + i);
            asset.put("filename", "cached_image_" + i + ".jpg");
            asset.put("content_type", "image/jpeg");
            asset.put("url", "https://example.com/cached_" + i + ".jpg");
            assetsArray.put(asset);
        }
        
        response.put("assets", assetsArray);
        cacheJson.put("response", response);
        
        AssetsModel model = new AssetsModel(cacheJson, true);
        
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
        assertEquals(10, model.objects.size());
    }

    @Test
    public void testAssetsModelWithResponseKeyNotFromCache() {
        // When not from cache, but JSON has response key
        // Based on the logic: !isFromCache && jsonObject.opt("response") == null ? jsonObject : jsonObject.optJSONObject("response")
        AssetsModel model = new AssetsModel(testAssetsJson, false);
        assertNotNull("AssetsModel should not be null", model);
        // Should process the assets directly since there's no response key
        assertEquals(5, model.objects.size());
    }

    @Test
    public void testAssetsModelFromCacheWithoutResponseKey() throws Exception {
        // When from cache but JSON doesn't have response key
        AssetsModel model = new AssetsModel(testAssetsJson, true);
        assertNotNull("AssetsModel should not be null", model);
        assertNotNull("Objects list should not be null", model.objects);
    }
}

