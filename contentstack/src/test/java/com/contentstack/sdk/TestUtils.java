package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Test utilities for creating mock data and common test setup
 */
public class TestUtils {

    public static Context createMockContext() {
        // Use Robolectric's ApplicationProvider instead of Mockito
        return ApplicationProvider.getApplicationContext();
    }

    public static JSONObject createMockEntryJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", "test_entry_uid");
        json.put("title", "Test Entry Title");
        json.put("url", "/test-entry");
        json.put("created_at", "2023-01-01T00:00:00.000Z");
        json.put("updated_at", "2023-01-02T00:00:00.000Z");
        json.put("created_by", "creator_uid");
        json.put("updated_by", "updater_uid");
        json.put("locale", "en-us");
        
        // Add some test fields
        json.put("description", "Test description");
        json.put("test_number", 42);
        json.put("test_boolean", true);
        
        // Add tags
        JSONArray tags = new JSONArray();
        tags.put("tag1");
        tags.put("tag2");
        json.put("tags", tags);
        
        // Add metadata
        JSONObject metadata = new JSONObject();
        metadata.put("version", 1);
        metadata.put("locale", "en-us");
        json.put("_metadata", metadata);
        
        // Add owner
        JSONObject owner = new JSONObject();
        owner.put("uid", "owner_uid");
        owner.put("email", "owner@test.com");
        json.put("_owner", owner);
        
        return json;
    }

    public static JSONObject createMockQueryResult() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray entries = new JSONArray();
        
        for (int i = 0; i < 3; i++) {
            JSONObject entry = new JSONObject();
            entry.put("uid", "entry_uid_" + i);
            entry.put("title", "Entry Title " + i);
            entry.put("url", "/entry-" + i);
            entries.put(entry);
        }
        
        json.put("entries", entries);
        json.put("count", 3);
        
        return json;
    }

    public static JSONObject createMockAssetJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", "test_asset_uid");
        json.put("filename", "test-image.jpg");
        json.put("title", "Test Asset");
        json.put("url", "https://cdn.contentstack.io/test-asset.jpg");
        json.put("content_type", "image/jpeg");
        json.put("file_size", "102400");
        json.put("created_at", "2023-01-01T00:00:00.000Z");
        json.put("updated_at", "2023-01-02T00:00:00.000Z");
        
        JSONObject metadata = new JSONObject();
        metadata.put("width", 1920);
        metadata.put("height", 1080);
        json.put("_metadata", metadata);
        
        return json;
    }

    public static JSONObject createMockContentTypeJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", "test_content_type");
        json.put("title", "Test Content Type");
        json.put("description", "Test content type description");
        
        JSONArray schema = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("uid", "title");
        field.put("data_type", "text");
        field.put("display_name", "Title");
        schema.put(field);
        
        json.put("schema", schema);
        
        return json;
    }

    public static JSONObject createMockSyncJson() throws JSONException {
        JSONObject json = new JSONObject();
        
        JSONArray items = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("type", "entry_published");
        item.put("content_type_uid", "test_content_type");
        item.put("uid", "entry_uid");
        item.put("data", createMockEntryJson());
        items.put(item);
        
        json.put("items", items);
        json.put("sync_token", "test_sync_token");
        json.put("pagination_token", "test_pagination_token");
        
        return json;
    }

    public static JSONObject createMockErrorResponse() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("error_message", "Test error message");
        json.put("error_code", 422);
        
        JSONObject errors = new JSONObject();
        errors.put("title", "Title is required");
        json.put("errors", errors);
        
        return json;
    }

    public static HashMap<String, Object> createMockHeaders() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("api_key", "test_api_key");
        headers.put("access_token", "test_delivery_token");
        headers.put("environment", "test_environment");
        return headers;
    }

    public static String getTestApiKey() {
        return "test_api_key";
    }

    public static String getTestDeliveryToken() {
        return "test_delivery_token";
    }

    public static String getTestEnvironment() {
        return "test_environment";
    }

    public static String getTestContentType() {
        return "test_content_type";
    }

    public static String getTestEntryUid() {
        return "test_entry_uid";
    }

    public static String getTestAssetUid() {
        return "test_asset_uid";
    }

    public static JSONObject createMockGroupJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("field1", "value1");
        json.put("field2", "value2");
        json.put("nested_number", 123);
        return json;
    }

    public static JSONArray createMockGroupsJson() throws JSONException {
        JSONArray array = new JSONArray();
        array.put(createMockGroupJson());
        array.put(createMockGroupJson());
        return array;
    }

    public static JSONObject createMockReferenceJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", "reference_uid");
        json.put("_content_type_uid", "referenced_content_type");
        json.put("title", "Referenced Entry");
        return json;
    }

    public static void cleanupTestCache() {
        File cacheDir = new File("build/test-cache");
        if (cacheDir.exists()) {
            deleteRecursive(cacheDir);
        }
    }

    private static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] children = fileOrDirectory.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursive(child);
                }
            }
        }
        fileOrDirectory.delete();
    }
}

