package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for CSBackgroundTask class.
 * Tests all constructor variants and error handling.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestCSBackgroundTask {

    private Context context;
    private Stack stack;
    private ArrayMap<String, Object> headers;
    private HashMap<String, Object> urlParams;
    private JSONObject jsonMain;
    private ResultCallBack callback;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
        
        headers = new ArrayMap<>();
        headers.put("api_key", "test_key");
        headers.put("access_token", "test_token");
        headers.put("environment", "test_env");
        
        urlParams = new HashMap<>();
        urlParams.put("include_count", true);
        
        jsonMain = new JSONObject();
        
        callback = new ResultCallBack() {
            @Override
            public void onRequestFail(ResponseType responseType, Error error) {
                // Test callback
            }

            @Override
            public void always() {
                // Test callback
            }
        };
    }

    // ========== QUERY CONSTRUCTOR TESTS ==========

    @Test
    public void testQueryConstructorWithValidParams() {
        Query query = stack.contentType("test_type").query();
        
        CSBackgroundTask task = new CSBackgroundTask(
            query, stack, "QUERY", "entries", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", 
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testQueryConstructorWithNullHeaders() {
        Query query = stack.contentType("test_type").query();
        
        CSBackgroundTask task = new CSBackgroundTask(
            query, stack, "QUERY", "entries", 
            null, urlParams, jsonMain, 
            "cache_path", "request_info", 
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testQueryConstructorWithEmptyHeaders() {
        Query query = stack.contentType("test_type").query();
        ArrayMap<String, Object> emptyHeaders = new ArrayMap<>();
        
        CSBackgroundTask task = new CSBackgroundTask(
            query, stack, "QUERY", "entries", 
            emptyHeaders, urlParams, jsonMain, 
            "cache_path", "request_info", 
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    // ========== ENTRY CONSTRUCTOR TESTS ==========

    @Test
    public void testEntryConstructorWithValidParams() {
        Entry entry = stack.contentType("test_type").entry("entry_uid");
        
        CSBackgroundTask task = new CSBackgroundTask(
            entry, stack, "ENTRY", "entries/entry_uid", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testEntryConstructorWithNullHeaders() {
        Entry entry = stack.contentType("test_type").entry("entry_uid");
        
        CSBackgroundTask task = new CSBackgroundTask(
            entry, stack, "ENTRY", "entries/entry_uid", 
            null, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    // ========== ASSET LIBRARY CONSTRUCTOR TESTS ==========

    @Test
    public void testAssetLibraryConstructorWithValidParams() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        
        CSBackgroundTask task = new CSBackgroundTask(
            assetLibrary, stack, "ASSETLIBRARY", "assets", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testAssetLibraryConstructorWithNullCallback() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        
        CSBackgroundTask task = new CSBackgroundTask(
            assetLibrary, stack, "ASSETLIBRARY", "assets", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, null
        );
        
        assertNotNull(task);
    }

    // ========== ASSET CONSTRUCTOR TESTS ==========

    @Test
    public void testAssetConstructorWithValidParams() {
        Asset asset = stack.asset("asset_uid");
        
        CSBackgroundTask task = new CSBackgroundTask(
            asset, stack, "ASSET", "assets/asset_uid", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testAssetConstructorWithEmptyUrlParams() {
        Asset asset = stack.asset("asset_uid");
        HashMap<String, Object> emptyParams = new HashMap<>();
        
        CSBackgroundTask task = new CSBackgroundTask(
            asset, stack, "ASSET", "assets/asset_uid", 
            headers, emptyParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    // ========== STACK CONSTRUCTOR TESTS ==========

    @Test
    public void testStackConstructorWithValidParams() {
        CSBackgroundTask task = new CSBackgroundTask(
            stack, stack, "STACK", "content_types", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testStackConstructorWithDifferentMethods() {
        // Test with GET
        CSBackgroundTask task1 = new CSBackgroundTask(
            stack, stack, "STACK", "content_types", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        assertNotNull(task1);
        
        // Test with POST
        CSBackgroundTask task2 = new CSBackgroundTask(
            stack, stack, "STACK", "content_types", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.POST, callback
        );
        assertNotNull(task2);
    }

    // ========== CONTENT TYPE CONSTRUCTOR TESTS ==========

    @Test
    public void testContentTypeConstructorWithValidParams() {
        ContentType contentType = stack.contentType("test_type");
        
        CSBackgroundTask task = new CSBackgroundTask(
            contentType, stack, "CONTENTTYPE", "content_types/test_type", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testContentTypeConstructorWithNullJson() {
        ContentType contentType = stack.contentType("test_type");
        
        CSBackgroundTask task = new CSBackgroundTask(
            contentType, stack, "CONTENTTYPE", "content_types/test_type", 
            headers, urlParams, null, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    // ========== GLOBAL FIELD CONSTRUCTOR TESTS ==========

    @Test
    public void testGlobalFieldConstructorWithValidParams() {
        GlobalField globalField = stack.globalField("test_field");
        
        CSBackgroundTask task = new CSBackgroundTask(
            globalField, stack, "GLOBALFIELD", "global_fields/test_field", 
            headers, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testGlobalFieldConstructorWithEmptyHeaders() {
        GlobalField globalField = stack.globalField("test_field");
        ArrayMap<String, Object> emptyHeaders = new ArrayMap<>();
        
        CSBackgroundTask task = new CSBackgroundTask(
            globalField, stack, "GLOBALFIELD", "global_fields/test_field", 
            emptyHeaders, urlParams, jsonMain, 
            "cache_path", "request_info", false,
            SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    // ========== REQUEST METHOD TESTS ==========

    @Test
    public void testAllRequestMethods() {
        Query query = stack.contentType("test").query();
        
        // GET
        CSBackgroundTask task1 = new CSBackgroundTask(
            query, stack, "QUERY", "entries", headers, urlParams, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.GET, callback
        );
        assertNotNull(task1);
        
        // POST
        CSBackgroundTask task2 = new CSBackgroundTask(
            query, stack, "QUERY", "entries", headers, urlParams, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.POST, callback
        );
        assertNotNull(task2);
        
        // PUT
        CSBackgroundTask task3 = new CSBackgroundTask(
            query, stack, "QUERY", "entries", headers, urlParams, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.PUT, callback
        );
        assertNotNull(task3);
        
        // DELETE
        CSBackgroundTask task4 = new CSBackgroundTask(
            query, stack, "QUERY", "entries", headers, urlParams, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.DELETE, callback
        );
        assertNotNull(task4);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testConstructorWithNullUrlParams() {
        Query query = stack.contentType("test").query();
        
        CSBackgroundTask task = new CSBackgroundTask(
            query, stack, "QUERY", "entries", headers, null, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testConstructorWithNullCachePath() {
        Entry entry = stack.contentType("test").entry("uid");
        
        CSBackgroundTask task = new CSBackgroundTask(
            entry, stack, "ENTRY", "entries/uid", headers, urlParams, jsonMain,
            null, "info", false, SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testMultipleHeadersInMap() {
        ArrayMap<String, Object> multiHeaders = new ArrayMap<>();
        multiHeaders.put("api_key", "key1");
        multiHeaders.put("access_token", "token1");
        multiHeaders.put("environment", "env1");
        multiHeaders.put("custom_header", "custom_value");
        
        Query query = stack.contentType("test").query();
        
        CSBackgroundTask task = new CSBackgroundTask(
            query, stack, "QUERY", "entries", multiHeaders, urlParams, jsonMain,
            "cache", "info", SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testComplexUrlParams() {
        HashMap<String, Object> complexParams = new HashMap<>();
        complexParams.put("include_count", true);
        complexParams.put("limit", 100);
        complexParams.put("skip", 0);
        complexParams.put("locale", "en-us");
        complexParams.put("include_schema", true);
        
        ContentType contentType = stack.contentType("test");
        
        CSBackgroundTask task = new CSBackgroundTask(
            contentType, stack, "CONTENTTYPE", "content_types/test",
            headers, complexParams, jsonMain,
            "cache", "info", false, SDKConstant.RequestMethod.GET, callback
        );
        
        assertNotNull(task);
    }

    @Test
    public void testDifferentControllerTypes() {
        String[] controllers = {"QUERY", "ENTRY", "ASSET", "STACK", "CONTENTTYPE", "GLOBALFIELD"};
        
        Query query = stack.contentType("test").query();
        
        for (String controller : controllers) {
            CSBackgroundTask task = new CSBackgroundTask(
                query, stack, controller, "test_url", headers, urlParams, jsonMain,
                "cache", "info", SDKConstant.RequestMethod.GET, callback
        );
            assertNotNull(task);
        }
    }
}

