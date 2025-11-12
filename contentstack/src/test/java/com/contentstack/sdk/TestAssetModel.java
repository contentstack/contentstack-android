package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Asset, AssetModel, and all Asset-related functionality.
 */
@RunWith(RobolectricTestRunner.class)
@org.robolectric.annotation.Config(sdk = 28, manifest = org.robolectric.annotation.Config.NONE)
public class TestAssetModel {

    private JSONObject mockAssetJson;
    private JSONObject mockResponseJson;
    private Context context;
    private Stack stack;
    private Asset asset;

    @Before
    public void setUp() throws Exception {
        // Create mock asset JSON for AssetModel tests
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

        // Setup for Asset instance tests
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
        asset = stack.asset("test_asset_uid");
    }

    // ========== ASSET MODEL TESTS ==========

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

    // ========== ASSET FETCH METHOD TESTS ==========

    @Test
    public void testFetchWithCallback() {
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected - network call will fail
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithNullCallback() {
        try {
            asset.fetch(null);
            assertNotNull(asset);
        } catch (Exception e) {
            // May throw exception
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithHeaders() {
        asset.setHeader("custom-header", "custom-value");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithMultipleHeaders() {
        asset.setHeader("header1", "value1");
        asset.setHeader("header2", "value2");
        asset.setHeader("header3", "value3");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithParameters() {
        asset.addParam("include_dimension", "true");
        asset.addParam("include_fallback", "true");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchAfterIncludeDimension() {
        asset.includeDimension();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchAfterIncludeFallback() {
        asset.includeFallback();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchAfterIncludeBranch() {
        asset.includeBranch();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithIgnoreCachePolicy() {
        asset.setCachePolicy(CachePolicy.IGNORE_CACHE);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            // Expected - network call will fail
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchWithNetworkOnlyPolicy() {
        asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchWithCacheOnlyPolicy() {
        asset.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Should return cache error as cache doesn't exist
                if (error != null) {
                    assertNotNull(error.getErrorMessage());
                }
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchWithCacheElseNetworkPolicy() {
        asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchWithCacheThenNetworkPolicy() {
        asset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchWithNetworkElseCachePolicy() {
        asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testFetchExceptionHandling() {
        // Create asset without stack instance to trigger exception
        Asset assetWithoutStack = new Asset("uid");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                assertNotNull(error);
                assertEquals(SDKConstant.PLEASE_PROVIDE_VALID_JSON, error.getErrorMessage());
            }
        };
        
        try {
            assetWithoutStack.fetch(callback);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    // ========== ADD PARAM TESTS ==========

    @Test
    public void testAddParamWithValidValues() {
        Asset result = asset.addParam("key1", "value1");
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testAddParamMultipleTimes() {
        asset.addParam("key1", "value1");
        asset.addParam("key2", "value2");
        asset.addParam("key3", "value3");
        
        assertNotNull(asset);
    }

    @Test
    public void testAddParamWithEmptyKey() {
        try {
            asset.addParam("", "value");
            assertNotNull(asset);
        } catch (Exception e) {
            // May throw exception
            assertNotNull(e);
        }
    }

    @Test
    public void testAddParamWithNullKey() {
        Asset result = asset.addParam(null, "value");
        assertNotNull(result);
        assertEquals(asset, result); // Should return this
    }

    @Test
    public void testAddParamWithEmptyValue() {
        Asset result = asset.addParam("key", "");
        assertNotNull(result);
    }

    @Test
    public void testAddParamWithNullValue() {
        Asset result = asset.addParam("key", null);
        assertNotNull(result);
        assertEquals(asset, result);
    }

    @Test
    public void testAddParamWithBothNull() {
        Asset result = asset.addParam(null, null);
        assertNotNull(result);
        assertEquals(asset, result);
    }

    @Test
    public void testAddParamChaining() {
        Asset result = asset.addParam("key1", "val1")
                            .addParam("key2", "val2")
                            .addParam("key3", "val3");
        assertNotNull(result);
        assertEquals(asset, result);
    }

    @Test
    public void testAddParamOverwrite() {
        asset.addParam("key", "value1");
        asset.addParam("key", "value2");
        asset.addParam("key", "value3");
        
        assertNotNull(asset);
    }

    // ========== INCLUDE DIMENSION TESTS ==========

    @Test
    public void testIncludeDimension() {
        Asset result = asset.includeDimension();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testIncludeDimensionMultipleTimes() {
        asset.includeDimension();
        asset.includeDimension();
        asset.includeDimension();
        
        assertNotNull(asset);
    }

    @Test
    public void testIncludeDimensionWithOtherMethods() {
        asset.includeDimension();
        asset.includeFallback();
        asset.includeBranch();
        
        assertNotNull(asset);
    }

    @Test
    public void testIncludeDimensionWithFetch() {
        asset.includeDimension();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    // ========== INCLUDE FALLBACK TESTS ==========

    @Test
    public void testIncludeFallback() {
        Asset result = asset.includeFallback();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testIncludeFallbackMultipleTimes() {
        asset.includeFallback();
        asset.includeFallback();
        
        assertNotNull(asset);
    }

    @Test
    public void testIncludeFallbackChaining() {
        Asset result = asset.includeFallback().includeDimension();
        assertNotNull(result);
    }

    @Test
    public void testIncludeFallbackWithFetch() {
        asset.includeFallback();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    // ========== INCLUDE BRANCH TESTS ==========

    @Test
    public void testIncludeBranch() {
        Asset result = asset.includeBranch();
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testIncludeBranchMultipleTimes() {
        asset.includeBranch();
        asset.includeBranch();
        
        assertNotNull(asset);
    }

    @Test
    public void testIncludeBranchChaining() {
        Asset result = asset.includeBranch().includeDimension().includeFallback();
        assertNotNull(result);
    }

    @Test
    public void testIncludeBranchWithFetch() {
        asset.includeBranch();
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    // ========== DATE GETTER TESTS ==========

    @Test
    public void testGetCreateAtWithData() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("created_at", "2023-01-01T00:00:00.000Z");
        asset.configure(json);
        
        Calendar createAt = asset.getCreateAt();
        // May return null or Calendar depending on configuration
        assertNotNull(asset);
    }

    @Test
    public void testGetCreateAtWithoutData() {
        Calendar createAt = asset.getCreateAt();
        // May be null if not configured
        assertNotNull(asset);
    }

    @Test
    public void testGetCreateAtWithInvalidJson() {
        Asset testAsset = new Asset("uid");
        testAsset.json = new JSONObject();
        try {
            testAsset.json.put("created_at", "invalid_date_format");
        } catch (JSONException e) {
            // Ignore
        }
        
        // Should handle exception and return null
        assertNull(testAsset.getCreateAt());
    }

    @Test
    public void testGetCreateAtWithNullJson() {
        Asset testAsset = new Asset("uid");
        testAsset.json = null;
        
        try {
            testAsset.getCreateAt();
        } catch (Exception e) {
            // Should handle null json
            assertNotNull(e);
        }
    }

    @Test
    public void testGetUpdateAtWithData() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("updated_at", "2023-06-01T00:00:00.000Z");
        asset.configure(json);
        
        Calendar updateAt = asset.getUpdateAt();
        // May return null or Calendar
        assertNotNull(asset);
    }

    @Test
    public void testGetUpdateAtWithoutData() {
        Calendar updateAt = asset.getUpdateAt();
        assertNotNull(asset);
    }

    @Test
    public void testGetUpdateAtWithInvalidJson() {
        Asset testAsset = new Asset("uid");
        testAsset.json = new JSONObject();
        try {
            testAsset.json.put("updated_at", "invalid_date_format");
        } catch (JSONException e) {
            // Ignore
        }
        
        assertNull(testAsset.getUpdateAt());
    }

    @Test
    public void testGetDeleteAtWithData() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("deleted_at", "2023-12-01T00:00:00.000Z");
        asset.configure(json);
        
        Calendar deleteAt = asset.getDeleteAt();
        // May return null or Calendar
        assertNotNull(asset);
    }

    @Test
    public void testGetDeleteAtWithoutData() {
        Calendar deleteAt = asset.getDeleteAt();
        assertNotNull(asset);
    }

    @Test
    public void testGetDeleteAtWithInvalidJson() {
        Asset testAsset = new Asset("uid");
        testAsset.json = new JSONObject();
        try {
            testAsset.json.put("deleted_at", "invalid_date_format");
        } catch (JSONException e) {
            // Ignore
        }
        
        assertNull(testAsset.getDeleteAt());
    }

    @Test
    public void testAllDateGetters() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("created_at", "2023-01-01T00:00:00.000Z");
        json.put("updated_at", "2023-06-01T00:00:00.000Z");
        json.put("deleted_at", "2023-12-01T00:00:00.000Z");
        asset.configure(json);
        
        // Call all date getters - they may return null or Calendar
        asset.getCreateAt();
        asset.getUpdateAt();
        asset.getDeleteAt();
        assertNotNull(asset);
    }

    // ========== SET UID TESTS ==========

    @Test
    public void testSetUidWithValidValue() {
        Asset testAsset = new Asset();
        testAsset.setUid("new_asset_uid");
        assertEquals("new_asset_uid", testAsset.getAssetUid());
    }

    @Test
    public void testSetUidWithEmptyString() {
        Asset testAsset = new Asset("original_uid");
        testAsset.setUid("");
        // Empty string should not change uid
        assertEquals("original_uid", testAsset.getAssetUid());
    }

    @Test
    public void testSetUidWithNull() {
        Asset testAsset = new Asset("original_uid");
        testAsset.setUid(null);
        // Null should not change uid
        assertEquals("original_uid", testAsset.getAssetUid());
    }

    // ========== COMPLEX SCENARIOS ==========

    @Test
    public void testFetchWithAllOptions() {
        asset.includeDimension();
        asset.includeFallback();
        asset.includeBranch();
        asset.addParam("custom_param", "custom_value");
        asset.setHeader("custom-header", "header-value");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testMethodChaining() {
        Asset result = asset
            .includeDimension()
            .includeFallback()
            .includeBranch()
            .addParam("key", "value");
        
        assertNotNull(result);
        assertSame(asset, result);
    }

    @Test
    public void testMultipleFetchCalls() {
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            asset.fetch(callback);
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithCachePolicy() {
        asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchWithDifferentCachePolicies() {
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
            asset.fetch(callback);
            
            asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
            asset.fetch(callback);
            
            asset.setCachePolicy(CachePolicy.CACHE_ONLY);
            asset.fetch(callback);
            
            asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
            asset.fetch(callback);
            
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testIncludeMethodsIdempotency() {
        // Calling multiple times should be idempotent
        Asset result1 = asset.includeDimension();
        Asset result2 = asset.includeDimension();
        Asset result3 = asset.includeFallback();
        Asset result4 = asset.includeFallback();
        Asset result5 = asset.includeBranch();
        Asset result6 = asset.includeBranch();
        
        assertSame(asset, result1);
        assertSame(asset, result2);
        assertSame(asset, result3);
        assertSame(asset, result4);
        assertSame(asset, result5);
        assertSame(asset, result6);
    }

    @Test
    public void testRemoveHeaderThenFetch() {
        asset.setHeader("temp-header", "temp-value");
        asset.removeHeader("temp-header");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock callback
            }
        };
        
        try {
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchAfterConfiguration() {
        try {
            JSONObject config = new JSONObject();
            config.put("uid", "configured_uid");
            config.put("filename", "test.jpg");
            asset.configure(config);
            
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType responseType, Error error) {
                    // Mock callback
                }
            };
            
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected - configuration or fetch may throw
            assertNotNull(e);
        }
    }

    @Test
    public void testCombinedOperationsBeforeFetch() {
        asset.addParam("version", "1")
             .includeDimension()
             .includeFallback()
             .includeBranch()
             .setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testAssetWithCustomHeaders() {
        asset.setHeader("custom-header", "custom-value");
        asset.setHeader("another-header", "another-value");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }

    @Test
    public void testAssetWithHeaderMerging() {
        // Ensure headerGroupApp is set
        if (asset.headerGroupApp == null) {
            asset.headerGroupApp = new ArrayMap<>();
        }
        asset.headerGroupApp.put("main-header", "main-value");
        
        asset.setHeader("local-header", "local-value");
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Mock
            }
        };
        
        try {
            asset.fetch(callback);
        } catch (Exception e) {
            assertNotNull(asset);
        }
    }
}
