package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for AssetModel class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestAssetModel {

    private JSONObject mockAssetJson;
    private JSONObject mockResponseJson;

    @Before
    public void setUp() throws JSONException {
        // Create mock asset JSON
        mockAssetJson = new JSONObject();
        mockAssetJson.put("uid", "test_asset_uid_123");
        mockAssetJson.put("content_type", "image/jpeg");
        mockAssetJson.put("file_size", "102400");
        mockAssetJson.put("filename", "test_image.jpg");
        mockAssetJson.put("url", "https://images.contentstack.io/test/image.jpg");

        JSONArray tagsArray = new JSONArray();
        tagsArray.put("tag1");
        tagsArray.put("tag2");
        tagsArray.put("tag3");
        mockAssetJson.put("tags", tagsArray);

        JSONObject metadata = new JSONObject();
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        mockAssetJson.put("_metadata", metadata);

        // Create mock response JSON with asset
        mockResponseJson = new JSONObject();
        mockResponseJson.put("asset", mockAssetJson);
        mockResponseJson.put("count", 5);
        mockResponseJson.put("objects", 10);
    }

    @Test
    public void testAssetModelFromResponse() throws JSONException {
        AssetModel model = new AssetModel(mockResponseJson, false, false);

        assertEquals("test_asset_uid_123", model.uploadedUid);
        assertEquals("image/jpeg", model.contentType);
        assertEquals("102400", model.fileSize);
        assertEquals("test_image.jpg", model.fileName);
        assertEquals("https://images.contentstack.io/test/image.jpg", model.uploadUrl);
        assertEquals(5, model.count);
        assertEquals(10, model.totalCount);
    }

    @Test
    public void testAssetModelWithTags() throws JSONException {
        AssetModel model = new AssetModel(mockResponseJson, false, false);

        assertNotNull(model.tags);
        assertEquals(3, model.tags.length);
        assertEquals("tag1", model.tags[0]);
        assertEquals("tag2", model.tags[1]);
        assertEquals("tag3", model.tags[2]);
    }

    @Test
    public void testAssetModelWithMetadata() throws JSONException {
        AssetModel model = new AssetModel(mockResponseJson, false, false);

        assertNotNull(model._metadata);
        assertEquals(2, model._metadata.size());
        assertEquals("value1", model._metadata.get("key1"));
        assertEquals("value2", model._metadata.get("key2"));
    }

    @Test
    public void testAssetModelFromArray() throws JSONException {
        JSONObject arrayJson = new JSONObject();
        arrayJson.put("uid", "array_asset_uid");
        arrayJson.put("content_type", "video/mp4");
        arrayJson.put("file_size", "5242880");
        arrayJson.put("filename", "video.mp4");
        arrayJson.put("url", "https://images.contentstack.io/test/video.mp4");

        AssetModel model = new AssetModel(arrayJson, true, false);

        assertEquals("array_asset_uid", model.uploadedUid);
        assertEquals("video/mp4", model.contentType);
        assertEquals("5242880", model.fileSize);
        assertEquals("video.mp4", model.fileName);
        assertEquals("https://images.contentstack.io/test/video.mp4", model.uploadUrl);
    }

    @Test
    public void testAssetModelWithoutTags() throws JSONException {
        JSONObject assetWithoutTags = new JSONObject();
        assetWithoutTags.put("uid", "no_tags_uid");
        assetWithoutTags.put("content_type", "application/pdf");
        assetWithoutTags.put("filename", "document.pdf");

        JSONObject response = new JSONObject();
        response.put("asset", assetWithoutTags);

        AssetModel model = new AssetModel(response, false, false);

        assertNull(model.tags);
        assertEquals("no_tags_uid", model.uploadedUid);
    }

    @Test
    public void testAssetModelWithEmptyTags() throws JSONException {
        JSONObject assetWithEmptyTags = new JSONObject();
        assetWithEmptyTags.put("uid", "empty_tags_uid");
        assetWithEmptyTags.put("tags", new JSONArray());

        JSONObject response = new JSONObject();
        response.put("asset", assetWithEmptyTags);

        AssetModel model = new AssetModel(response, false, false);

        assertNull(model.tags);
    }

    @Test
    public void testAssetModelWithNullTagsValue() throws JSONException {
        JSONObject assetWithNullTags = new JSONObject();
        assetWithNullTags.put("uid", "null_tags_uid");
        assetWithNullTags.put("tags", JSONObject.NULL);

        JSONObject response = new JSONObject();
        response.put("asset", assetWithNullTags);

        AssetModel model = new AssetModel(response, false, false);

        assertNull(model.tags);
    }

    @Test
    public void testAssetModelWithoutMetadata() throws JSONException {
        JSONObject assetWithoutMetadata = new JSONObject();
        assetWithoutMetadata.put("uid", "no_metadata_uid");
        assetWithoutMetadata.put("filename", "file.txt");

        JSONObject response = new JSONObject();
        response.put("asset", assetWithoutMetadata);

        AssetModel model = new AssetModel(response, false, false);

        assertNull(model._metadata);
    }

    @Test
    public void testAssetModelWithoutCount() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("asset", mockAssetJson);
        // No count field

        AssetModel model = new AssetModel(response, false, false);

        assertEquals(0, model.count);
        assertEquals(0, model.totalCount);
    }

    @Test
    public void testAssetModelDefaultValues() throws JSONException {
        JSONObject minimalAsset = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("asset", minimalAsset);

        AssetModel model = new AssetModel(response, false, false);

        assertNull(model.uploadedUid);
        assertNull(model.contentType);
        assertNull(model.fileSize);
        assertNull(model.fileName);
        assertNull(model.uploadUrl);
        assertNull(model.tags);
        assertEquals(0, model.totalCount);
        assertEquals(0, model.count);
    }

    @Test
    public void testAssetModelWithMultipleTags() throws JSONException {
        JSONArray largeTags = new JSONArray();
        for (int i = 0; i < 10; i++) {
            largeTags.put("tag_" + i);
        }
        mockAssetJson.put("tags", largeTags);

        JSONObject response = new JSONObject();
        response.put("asset", mockAssetJson);

        AssetModel model = new AssetModel(response, false, false);

        assertNotNull(model.tags);
        assertEquals(10, model.tags.length);
        for (int i = 0; i < 10; i++) {
            assertEquals("tag_" + i, model.tags[i]);
        }
    }

    @Test
    public void testAssetModelJsonStorage() throws JSONException {
        AssetModel model = new AssetModel(mockResponseJson, false, false);

        assertNotNull(model.json);
        assertEquals("test_asset_uid_123", model.json.opt("uid"));
        assertEquals("image/jpeg", model.json.opt("content_type"));
    }

    @Test
    public void testAssetModelWithDifferentContentTypes() throws JSONException {
        String[] contentTypes = {"image/png", "video/mp4", "audio/mp3", "application/pdf", "text/plain"};

        for (String contentType : contentTypes) {
            JSONObject asset = new JSONObject();
            asset.put("uid", "uid_" + contentType.replace("/", "_"));
            asset.put("content_type", contentType);

            JSONObject response = new JSONObject();
            response.put("asset", asset);

            AssetModel model = new AssetModel(response, false, false);
            assertEquals(contentType, model.contentType);
        }
    }
}

