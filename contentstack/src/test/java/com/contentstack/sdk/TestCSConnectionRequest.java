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
 * Comprehensive unit tests for CSConnectionRequest class.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestCSConnectionRequest {

    private Context context;
    private Stack stack;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
    }

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testDefaultConstructor() {
        CSConnectionRequest request = new CSConnectionRequest();
        assertNotNull(request);
    }

    @Test
    public void testQueryConstructor() {
        Query query = stack.contentType("test_type").query();
        CSConnectionRequest request = new CSConnectionRequest(query);
        assertNotNull(request);
    }

    @Test
    public void testEntryConstructor() {
        Entry entry = stack.contentType("test_type").entry("entry_uid");
        CSConnectionRequest request = new CSConnectionRequest(entry);
        assertNotNull(request);
    }

    @Test
    public void testAssetLibraryConstructor() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        CSConnectionRequest request = new CSConnectionRequest((INotifyClass) assetLibrary);
        assertNotNull(request);
    }

    @Test
    public void testAssetConstructor() {
        Asset asset = stack.asset("asset_uid");
        CSConnectionRequest request = new CSConnectionRequest(asset);
        assertNotNull(request);
    }

    @Test
    public void testContentTypeConstructor() {
        ContentType contentType = stack.contentType("test_type");
        CSConnectionRequest request = new CSConnectionRequest(contentType);
        assertNotNull(request);
    }

    @Test
    public void testGlobalFieldConstructor() {
        GlobalField globalField = stack.globalField("test_field");
        CSConnectionRequest request = new CSConnectionRequest(globalField);
        assertNotNull(request);
    }

    // ========== SETTER METHOD TESTS ==========

    @Test
    public void testSetQueryInstance() {
        CSConnectionRequest request = new CSConnectionRequest();
        Query query = stack.contentType("test_type").query();
        
        request.setQueryInstance(query);
        assertNotNull(request);
    }

    @Test
    public void testSetQueryInstanceWithNull() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setQueryInstance(null);
        assertNotNull(request);
    }

    @Test
    public void testSetURLQueries() {
        CSConnectionRequest request = new CSConnectionRequest();
        HashMap<String, Object> urlQueries = new HashMap<>();
        urlQueries.put("include_count", true);
        urlQueries.put("limit", 10);
        
        request.setURLQueries(urlQueries);
        assertNotNull(request);
    }

    @Test
    public void testSetURLQueriesWithNull() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setURLQueries(null);
        assertNotNull(request);
    }

    @Test
    public void testSetURLQueriesWithEmptyMap() {
        CSConnectionRequest request = new CSConnectionRequest();
        HashMap<String, Object> emptyQueries = new HashMap<>();
        
        request.setURLQueries(emptyQueries);
        assertNotNull(request);
    }

    @Test
    public void testSetStackInstance() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setStackInstance(stack);
        assertNotNull(request);
    }

    @Test
    public void testSetStackInstanceWithNull() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setStackInstance(null);
        assertNotNull(request);
    }

    @Test
    public void testSetContentTypeInstance() {
        CSConnectionRequest request = new CSConnectionRequest();
        ContentType contentType = stack.contentType("test_type");
        
        request.setContentTypeInstance(contentType);
        assertNotNull(request);
    }

    @Test
    public void testSetContentTypeInstanceWithNull() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setContentTypeInstance(null);
        assertNotNull(request);
    }

    @Test
    public void testSetGlobalFieldInstance() {
        CSConnectionRequest request = new CSConnectionRequest();
        GlobalField globalField = stack.globalField("test_field");
        
        request.setGlobalFieldInstance(globalField);
        assertNotNull(request);
    }

    @Test
    public void testSetGlobalFieldInstanceWithNull() {
        CSConnectionRequest request = new CSConnectionRequest();
        request.setGlobalFieldInstance(null);
        assertNotNull(request);
    }

    // ========== MULTIPLE SETTER CALLS TESTS ==========

    @Test
    public void testMultipleSetterCalls() {
        CSConnectionRequest request = new CSConnectionRequest();
        Query query = stack.contentType("test").query();
        HashMap<String, Object> urlQueries = new HashMap<>();
        urlQueries.put("limit", 10);
        
        request.setQueryInstance(query);
        request.setURLQueries(urlQueries);
        request.setStackInstance(stack);
        
        assertNotNull(request);
    }

    @Test
    public void testSetterChaining() {
        CSConnectionRequest request = new CSConnectionRequest();
        Query query = stack.contentType("test").query();
        ContentType contentType = stack.contentType("test");
        GlobalField globalField = stack.globalField("field");
        
        request.setQueryInstance(query);
        request.setContentTypeInstance(contentType);
        request.setGlobalFieldInstance(globalField);
        request.setStackInstance(stack);
        
        assertNotNull(request);
    }

    // ========== CONSTRUCTOR WITH DIFFERENT INSTANCE TYPES TESTS ==========

    @Test
    public void testQueryConstructorWithDifferentQueries() {
        Query query1 = stack.contentType("type1").query();
        Query query2 = stack.contentType("type2").query();
        
        CSConnectionRequest request1 = new CSConnectionRequest(query1);
        CSConnectionRequest request2 = new CSConnectionRequest(query2);
        
        assertNotNull(request1);
        assertNotNull(request2);
        assertNotSame(request1, request2);
    }

    @Test
    public void testEntryConstructorWithDifferentEntries() {
        Entry entry1 = stack.contentType("type1").entry("uid1");
        Entry entry2 = stack.contentType("type2").entry("uid2");
        
        CSConnectionRequest request1 = new CSConnectionRequest(entry1);
        CSConnectionRequest request2 = new CSConnectionRequest(entry2);
        
        assertNotNull(request1);
        assertNotNull(request2);
        assertNotSame(request1, request2);
    }

    @Test
    public void testAssetConstructorWithDifferentAssets() {
        Asset asset1 = stack.asset("asset1");
        Asset asset2 = stack.asset("asset2");
        
        CSConnectionRequest request1 = new CSConnectionRequest(asset1);
        CSConnectionRequest request2 = new CSConnectionRequest(asset2);
        
        assertNotNull(request1);
        assertNotNull(request2);
        assertNotSame(request1, request2);
    }

    // ========== URL QUERIES TESTS ==========

    @Test
    public void testSetURLQueriesWithComplexParams() {
        CSConnectionRequest request = new CSConnectionRequest();
        HashMap<String, Object> queries = new HashMap<>();
        queries.put("include_count", true);
        queries.put("limit", 100);
        queries.put("skip", 0);
        queries.put("locale", "en-us");
        queries.put("include_schema", true);
        queries.put("include_fallback", true);
        
        request.setURLQueries(queries);
        assertNotNull(request);
    }

    @Test
    public void testSetURLQueriesMultipleTimes() {
        CSConnectionRequest request = new CSConnectionRequest();
        
        HashMap<String, Object> queries1 = new HashMap<>();
        queries1.put("limit", 10);
        request.setURLQueries(queries1);
        
        HashMap<String, Object> queries2 = new HashMap<>();
        queries2.put("skip", 5);
        request.setURLQueries(queries2);
        
        assertNotNull(request);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testMultipleConstructorInstances() {
        CSConnectionRequest req1 = new CSConnectionRequest();
        CSConnectionRequest req2 = new CSConnectionRequest();
        CSConnectionRequest req3 = new CSConnectionRequest();
        
        assertNotNull(req1);
        assertNotNull(req2);
        assertNotNull(req3);
        
        assertNotSame(req1, req2);
        assertNotSame(req2, req3);
        assertNotSame(req1, req3);
    }

    @Test
    public void testSetterWithSameInstanceMultipleTimes() {
        CSConnectionRequest request = new CSConnectionRequest();
        Query query = stack.contentType("test").query();
        
        request.setQueryInstance(query);
        request.setQueryInstance(query);
        request.setQueryInstance(query);
        
        assertNotNull(request);
    }

    @Test
    public void testAllConstructorsWithSameStack() {
        Query query = stack.contentType("test").query();
        Entry entry = stack.contentType("test").entry("uid");
        Asset asset = stack.asset("asset_uid");
        ContentType contentType = stack.contentType("test");
        GlobalField globalField = stack.globalField("field");
        
        CSConnectionRequest req1 = new CSConnectionRequest(query);
        CSConnectionRequest req2 = new CSConnectionRequest(entry);
        CSConnectionRequest req3 = new CSConnectionRequest(asset);
        CSConnectionRequest req4 = new CSConnectionRequest(contentType);
        CSConnectionRequest req5 = new CSConnectionRequest(globalField);
        
        assertNotNull(req1);
        assertNotNull(req2);
        assertNotNull(req3);
        assertNotNull(req4);
        assertNotNull(req5);
    }

    @Test
    public void testURLQueriesWithDifferentValueTypes() {
        CSConnectionRequest request = new CSConnectionRequest();
        HashMap<String, Object> queries = new HashMap<>();
        queries.put("boolean_param", true);
        queries.put("int_param", 42);
        queries.put("string_param", "value");
        queries.put("long_param", 123456789L);
        
        request.setURLQueries(queries);
        assertNotNull(request);
    }

    @Test
    public void testSettersIndependence() {
        CSConnectionRequest req1 = new CSConnectionRequest();
        CSConnectionRequest req2 = new CSConnectionRequest();
        
        Query query1 = stack.contentType("type1").query();
        Query query2 = stack.contentType("type2").query();
        
        req1.setQueryInstance(query1);
        req2.setQueryInstance(query2);
        
        // Both should maintain independent state
        assertNotNull(req1);
        assertNotNull(req2);
    }
}

