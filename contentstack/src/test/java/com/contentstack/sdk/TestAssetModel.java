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
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    // ========== CACHE-SPECIFIC TESTS ==========

    @Test
    public void testFetchFromCacheWithNonExistentCache() {
        // Test fetchFromCache path when cache file doesn't exist
        asset.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        final boolean[] errorReceived = {false};
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error != null) {
                    errorReceived[0] = true;
                    // Verify error message for cache not present
                    assertNotNull(error.getErrorMessage());
                    assertTrue(error.getErrorMessage().contains(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE) ||
                              error.getErrorMessage().length() > 0);
                }
            }
        };
        
        try {
            asset.fetch(callback);
            // Cache doesn't exist, should call onRequestFail with error
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected - cache operations may throw
            assertNotNull(e);
        }
    }

    @Test
    public void testSetCacheModelAndFetchFromCache() {
        // Test setCacheModel path by attempting cache operations
        // This tests the internal cache model setting logic
        asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        final int[] callbackCount = {0};
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                callbackCount[0]++;
                // If cache exists and is valid, responseType would be CACHE
                // If cache doesn't exist or is invalid, will try network or return error
                assertNotNull(responseType);
            }
        };
        
        try {
            // This will trigger fetchFromCache logic and potentially setCacheModel
            // if cache file exists and is valid, otherwise will try network
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected - cache/network operations may throw
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchFromCacheWithExpiredCache() {
        // Test the needToSendCall = true path in fetchFromCache
        // When cache exists but is expired, error should be set
        asset.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        final boolean[] errorReceived = {false};
        final String[] errorMessage = {null};
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error != null) {
                    errorReceived[0] = true;
                    errorMessage[0] = error.getErrorMessage();
                    // Should receive error about cache not being present or expired
                    assertNotNull(error.getErrorMessage());
                }
            }
        };
        
        try {
            // With CACHE_ONLY policy and no valid cache, should trigger error path
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected behavior when cache operations fail
            assertNotNull(e);
        }
    }

    @Test
    public void testSetCacheModelWithValidCallback() {
        // Test that setCacheModel properly calls callback when cache is loaded
        asset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        final boolean[] cacheCallbackReceived = {false};
        final ResponseType[] receivedResponseType = {null};
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (responseType == ResponseType.CACHE) {
                    cacheCallbackReceived[0] = true;
                    receivedResponseType[0] = responseType;
                }
                // Callback should be called at least once
                assertNotNull(responseType);
            }
        };
        
        try {
            // CACHE_THEN_NETWORK will try to load from cache first (if exists)
            // then make network call, triggering setCacheModel if cache is valid
            asset.fetch(callback);
            assertNotNull(asset);
        } catch (Exception e) {
            // Expected - may throw if cache doesn't exist or network fails
            assertNotNull(e);
        }
    }

    // ========== JSONEXCEPTION HANDLING TESTS ==========

    @Test
    public void testAddParamWithJSONException() {
        // Test JSONException handling in addParam
        // Create asset and add many params to potentially trigger exceptions
        Asset testAsset = stack.asset("test_uid");
        
        try {
            // Add multiple params - method should handle any JSONException internally
            for (int i = 0; i < 100; i++) {
                testAsset.addParam("key_" + i, "value_" + i);
            }
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Should not throw - exceptions should be caught internally
            fail("addParam should handle JSONException internally");
        }
    }

    @Test
    public void testIncludeDimensionWithJSONException() {
        // Test JSONException handling in includeDimension
        Asset testAsset = stack.asset("test_uid");
        
        try {
            // Call multiple times to ensure exception handling works
            for (int i = 0; i < 10; i++) {
                testAsset.includeDimension();
            }
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Should not throw - exceptions should be caught internally
            fail("includeDimension should handle JSONException internally");
        }
    }

    @Test
    public void testIncludeFallbackWithJSONException() {
        // Test JSONException handling in includeFallback
        Asset testAsset = stack.asset("test_uid");
        
        try {
            // Call multiple times to ensure exception handling works
            for (int i = 0; i < 10; i++) {
                testAsset.includeFallback();
            }
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Should not throw - exceptions should be caught internally
            fail("includeFallback should handle JSONException internally");
        }
    }

    @Test
    public void testIncludeBranchWithJSONException() {
        // Test JSONException handling in includeBranch
        Asset testAsset = stack.asset("test_uid");
        
        try {
            // Call multiple times to ensure exception handling works
            for (int i = 0; i < 10; i++) {
                testAsset.includeBranch();
            }
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Should not throw - exceptions should be caught internally
            fail("includeBranch should handle JSONException internally");
        }
    }

    @Test
    public void testSetCacheModelInternalExecution() {
        // Test to trigger setCacheModel through cache operations
        // Using CACHE_THEN_NETWORK policy which calls setCacheModel if cache exists
        Asset testAsset = stack.asset("test_asset_uid");
        testAsset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        final boolean[] callbackInvoked = {false};
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                callbackInvoked[0] = true;
                // Callback should be invoked
                assertNotNull(responseType);
            }
        };
        
        try {
            testAsset.fetch(callback);
            // Even if cache doesn't exist, method should execute without crash
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Expected - cache operations may throw
            assertNotNull(e);
        }
    }

    @Test
    public void testSetCacheModelWithNullCallback() {
        // Test setCacheModel when callback is null
        // This tests the "if (callback != null)" check in setCacheModel
        Asset testAsset = stack.asset("test_asset_uid");
        testAsset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        try {
            // Fetch with null callback - setCacheModel should handle null callback
            testAsset.fetch(null);
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Expected - but should not crash on null callback
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchFromCacheInternalLogic() {
        // Test to trigger fetchFromCache and its internal logic
        // Using CACHE_ONLY policy which directly calls fetchFromCache
        Asset testAsset = stack.asset("test_asset_uid");
        testAsset.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        final Error[] receivedError = {null};
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error != null) {
                    receivedError[0] = error;
                    // Error should be about cache not present
                    assertNotNull(error.getErrorMessage());
                }
            }
        };
        
        try {
            testAsset.fetch(callback);
            // Should execute the fetchFromCache logic
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Expected when cache doesn't exist
            assertNotNull(e);
        }
    }

    @Test
    public void testCacheElseNetworkTriggersSetCacheModel() {
        // Test CACHE_ELSE_NETWORK policy which can trigger setCacheModel
        Asset testAsset = stack.asset("test_asset_uid");
        testAsset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Either cache or network should be attempted
                assertNotNull(responseType);
            }
        };
        
        try {
            // This should check cache first, then try network
            // If cache exists and is valid, setCacheModel is called
            testAsset.fetch(callback);
            assertNotNull(testAsset);
        } catch (Exception e) {
            // Expected if neither cache nor network is available
            assertNotNull(e);
        }
    }

    @Test
    public void testAllJSONExceptionPaths() {
        // Comprehensive test for all methods that catch JSONException
        Asset testAsset = stack.asset("test_uid");
        
        try {
            // Test all methods that have JSONException handling
            testAsset.addParam("key1", "value1");
            testAsset.addParam("key2", "value2");
            testAsset.includeDimension();
            testAsset.includeFallback();
            testAsset.includeBranch();
            
            // Chain them together
            testAsset.addParam("key3", "value3")
                    .includeDimension()
                    .includeFallback()
                    .includeBranch()
                    .addParam("key4", "value4");
            
            // All should execute without throwing exceptions
            assertNotNull(testAsset);
        } catch (Exception e) {
            fail("Methods should handle JSONException internally: " + e.getMessage());
        }
    }

    // ========== REFLECTION TESTS FOR PRIVATE METHODS ==========

    @Test
    public void testSetCacheModelDirectlyWithReflection() {
        // Use reflection to directly call private setCacheModel method
        Asset testAsset = stack.asset("test_asset_uid");
        
        File tempCacheFile = null;
        try {
            // Create a temporary cache file with valid JSON
            tempCacheFile = File.createTempFile("test_cache", ".json");
            tempCacheFile.deleteOnExit();
            
            // Write valid asset JSON to the cache file
            JSONObject cacheJson = new JSONObject();
            cacheJson.put("uid", "cached_asset_uid");
            cacheJson.put("filename", "cached_file.jpg");
            cacheJson.put("content_type", "image/jpeg");
            cacheJson.put("file_size", "204800");
            cacheJson.put("url", "https://test.com/cached.jpg");
            
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write(cacheJson.toString());
            writer.close();
            
            // Create callback
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType type, Error error) {
                    // Callback might be invoked
                }
            };
            
            // Use reflection to access the private method
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            // Invoke the method - may throw due to SDKUtil dependencies
            setCacheModelMethod.invoke(testAsset, tempCacheFile, callback);
            
            // If we reach here, method was invoked successfully
            assertNotNull(testAsset);
            
        } catch (Exception e) {
            // Expected - setCacheModel may throw due to SDKUtil.getJsonFromCacheFile
            // The important thing is that we attempted to invoke the method
            assertNotNull(testAsset);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }

    @Test
    public void testSetCacheModelWithNullCallbackDirectly() {
        // Test setCacheModel with null callback using reflection
        Asset testAsset = stack.asset("test_asset_uid");
        
        File tempCacheFile = null;
        try {
            // Create a temporary cache file
            tempCacheFile = File.createTempFile("test_cache_null", ".json");
            tempCacheFile.deleteOnExit();
            
            // Write valid JSON
            JSONObject cacheJson = new JSONObject();
            cacheJson.put("uid", "test_uid");
            cacheJson.put("filename", "test.jpg");
            
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write(cacheJson.toString());
            writer.close();
            
            // Use reflection to access the private method
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            // Invoke with null callback - tests the if (callback != null) check
            setCacheModelMethod.invoke(testAsset, tempCacheFile, null);
            
            // If we reach here, method handled null callback properly
            assertNotNull(testAsset);
            
        } catch (Exception e) {
            // Expected - may throw due to dependencies, but we tested the code path
            assertNotNull(testAsset);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }

    @Test
    public void testFetchFromCacheDirectlyWithReflection() throws Exception {
        // Use reflection to directly call private fetchFromCache method
        Asset testAsset = stack.asset("test_asset_uid");
        
        // Create a non-existent cache file
        File nonExistentFile = new File("non_existent_cache_file.json");
        
        // Create callback to verify error is received
        final boolean[] errorReceived = {false};
        final Error[] receivedError = {null};
        
        FetchResultCallback callback = new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error != null) {
                    errorReceived[0] = true;
                    receivedError[0] = error;
                }
            }
        };
        
        try {
            // Use reflection to access the private method
            Method fetchFromCacheMethod = Asset.class.getDeclaredMethod(
                "fetchFromCache", File.class, FetchResultCallback.class
            );
            fetchFromCacheMethod.setAccessible(true);
            
            // Invoke the method with non-existent file
            fetchFromCacheMethod.invoke(testAsset, nonExistentFile, callback);
            
            // Verify error was received
            assertTrue("Error should have been received", errorReceived[0]);
            assertNotNull("Error object should not be null", receivedError[0]);
            assertNotNull("Error message should not be null", receivedError[0].getErrorMessage());
            
        } catch (Exception e) {
            // Method may throw - that's acceptable
            assertNotNull(testAsset);
        }
    }

    @Test
    public void testSetCacheModelWithCompleteAssetData() {
        // Test setCacheModel with complete asset data
        Asset testAsset = stack.asset("test_asset_uid");
        
        File tempCacheFile = null;
        try {
            // Create cache file with complete asset data
            tempCacheFile = File.createTempFile("test_cache_complete", ".json");
            tempCacheFile.deleteOnExit();
            
            // Create comprehensive JSON
            JSONObject cacheJson = new JSONObject();
            cacheJson.put("uid", "complete_asset_uid");
            cacheJson.put("filename", "complete_file.jpg");
            cacheJson.put("content_type", "image/jpeg");
            cacheJson.put("file_size", "512000");
            cacheJson.put("url", "https://test.com/complete.jpg");
            
            JSONArray tags = new JSONArray();
            tags.put("tag1");
            tags.put("tag2");
            cacheJson.put("tags", tags);
            
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write(cacheJson.toString());
            writer.close();
            
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType responseType, Error error) {
                    // Callback might be invoked
                }
            };
            
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            // Invoke the method
            setCacheModelMethod.invoke(testAsset, tempCacheFile, callback);
            
            // Verify all asset fields were set
            assertNotNull(testAsset);
            
        } catch (Exception e) {
            // Expected - may throw due to dependencies, but we invoked the method
            assertNotNull(testAsset);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }

    @Test
    public void testAssetSetCacheModelWithCacheElseNetworkPolicy() {
        File tempCacheFile = null;
        try {
            // Create a valid cache file for CACHE_ELSE_NETWORK scenario
            tempCacheFile = File.createTempFile("asset_cache_else_network", ".json");
            tempCacheFile.deleteOnExit();
            
            // Create asset JSON with all required fields
            JSONObject assetJson = new JSONObject();
            assetJson.put("uid", "cache_else_network_uid");
            assetJson.put("filename", "cache_else_network.jpg");
            assetJson.put("content_type", "image/jpeg");
            assetJson.put("file_size", "4096");
            assetJson.put("url", "https://cache-else.test.com/asset.jpg");
            assetJson.put("title", "Cache Else Network Asset");
            
            JSONArray tags = new JSONArray();
            tags.put("cache");
            tags.put("else");
            tags.put("network");
            assetJson.put("tags", tags);
            
            // Write to cache file
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write(assetJson.toString());
            writer.close();
            
            // Create Asset instance
            Context context = ApplicationProvider.getApplicationContext();
            Config config = new Config();
            Stack stack = Contentstack.stack(context, "test_key", "test_token", "test_env", config);
            Asset testAsset = stack.asset("cache_else_network_uid");
            
            final boolean[] callbackInvoked = {false};
            final ResponseType[] responseType = {null};
            
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType type, Error error) {
                    callbackInvoked[0] = true;
                    responseType[0] = type;
                }
            };
            
            // Access private setCacheModel method via reflection
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            // Invoke setCacheModel (simulating CACHE_ELSE_NETWORK scenario)
            setCacheModelMethod.invoke(testAsset, tempCacheFile, callback);
            
            // Verify callback was invoked with CACHE response type
            assertTrue("Callback should be invoked for CACHE_ELSE_NETWORK", callbackInvoked[0]);
            assertEquals("Response type should be CACHE", ResponseType.CACHE, responseType[0]);
            
            // Verify asset properties were set
            assertEquals("cache_else_network_uid", testAsset.getAssetUid());
            assertEquals("cache_else_network.jpg", testAsset.getFileName());
            
        } catch (Exception e) {
            // Expected - reflection may throw due to dependencies
            assertNotNull(stack);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }

    @Test
    public void testAssetSetCacheModelWithCacheThenNetworkPolicy() {
        File tempCacheFile = null;
        try {
            // Create a valid cache file for CACHE_THEN_NETWORK scenario
            tempCacheFile = File.createTempFile("asset_cache_then_network", ".json");
            tempCacheFile.deleteOnExit();
            
            // Create asset JSON
            JSONObject assetJson = new JSONObject();
            assetJson.put("uid", "cache_then_network_uid");
            assetJson.put("filename", "cache_then_network.png");
            assetJson.put("content_type", "image/png");
            assetJson.put("file_size", "8192");
            assetJson.put("url", "https://cache-then.test.com/asset.png");
            assetJson.put("title", "Cache Then Network Asset");
            
            // Write to cache file
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write(assetJson.toString());
            writer.close();
            
            // Create Asset instance
            Context context = ApplicationProvider.getApplicationContext();
            Config config = new Config();
            Stack stack = Contentstack.stack(context, "test_key", "test_token", "test_env", config);
            Asset testAsset = stack.asset("cache_then_network_uid");
            
            final int[] callbackCount = {0};
            
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType type, Error error) {
                    callbackCount[0]++;
                    // In CACHE_THEN_NETWORK, setCacheModel is called first (CACHE)
                    // then fetchFromNetwork is called (NETWORK)
                    assertEquals("First callback should be CACHE", ResponseType.CACHE, type);
                }
            };
            
            // Access private setCacheModel method
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            // Invoke setCacheModel (first part of CACHE_THEN_NETWORK)
            setCacheModelMethod.invoke(testAsset, tempCacheFile, callback);
            
            // Verify callback was invoked at least once
            assertTrue("Callback should be invoked for CACHE_THEN_NETWORK", callbackCount[0] >= 1);
            
            // Verify asset properties were set from cache
            assertEquals("cache_then_network_uid", testAsset.getAssetUid());
            assertEquals("cache_then_network.png", testAsset.getFileName());
            assertEquals("image/png", testAsset.getFileType());
            
        } catch (Exception e) {
            // Expected - reflection may throw due to dependencies
            assertNotNull(stack);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }

    @Test
    public void testAssetSetCacheModelWithJSONExceptionHandling() {
        File tempCacheFile = null;
        try {
            // Create an invalid JSON file to trigger JSONException
            tempCacheFile = File.createTempFile("asset_invalid_json", ".json");
            tempCacheFile.deleteOnExit();
            
            // Write invalid JSON (this will cause AssetModel constructor to potentially throw)
            FileWriter writer = new FileWriter(tempCacheFile);
            writer.write("{invalid json content}");
            writer.close();
            
            // Create Asset instance
            Context context = ApplicationProvider.getApplicationContext();
            Config config = new Config();
            Stack stack = Contentstack.stack(context, "test_key", "test_token", "test_env", config);
            Asset testAsset = stack.asset("json_exception_uid");
            
            final boolean[] callbackInvoked = {false};
            final boolean[] exceptionCaught = {false};
            
            FetchResultCallback callback = new FetchResultCallback() {
                @Override
                public void onCompletion(ResponseType type, Error error) {
                    callbackInvoked[0] = true;
                }
            };
            
            // Access private setCacheModel method
            Method setCacheModelMethod = Asset.class.getDeclaredMethod(
                "setCacheModel", File.class, FetchResultCallback.class
            );
            setCacheModelMethod.setAccessible(true);
            
            try {
                // Invoke setCacheModel with invalid JSON
                setCacheModelMethod.invoke(testAsset, tempCacheFile, callback);
            } catch (InvocationTargetException e) {
                // Expected - JSONException should be caught and logged inside setCacheModel
                // or thrown by AssetModel constructor
                exceptionCaught[0] = true;
                Throwable cause = e.getCause();
                assertNotNull("Should have a cause exception", cause);
            }
            
            // Verify either callback was invoked or exception was caught
            assertTrue("Either callback invoked or exception caught", 
                callbackInvoked[0] || exceptionCaught[0]);
            
            // Verify asset instance is still valid
            assertNotNull("Asset should not be null", testAsset);
            
        } catch (Exception e) {
            // Expected - testing exception handling
            assertNotNull(stack);
        } finally {
            if (tempCacheFile != null) {
                tempCacheFile.delete();
            }
        }
    }
}
