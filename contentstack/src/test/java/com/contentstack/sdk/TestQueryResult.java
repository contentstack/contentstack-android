package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for QueryResult class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryResult {

    private QueryResult queryResult;
    private Context context;
    private Stack stack;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
        queryResult = new QueryResult();
    }

    // ========== CONSTRUCTOR & INITIALIZATION TESTS ==========

    @Test
    public void testQueryResultCreation() {
        assertNotNull(queryResult);
    }

    @Test
    public void testDefaultValues() {
        assertNull(queryResult.getResultObjects());
        assertEquals(0, queryResult.getCount());
        assertNull(queryResult.getSchema());
        assertNull(queryResult.getContentType());
    }

    // ========== SET JSON TESTS ==========

    @Test
    public void testSetJSONWithBasicData() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 5);
        
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(5, queryResult.getCount());
        assertNotNull(queryResult.getResultObjects());
        assertEquals(0, queryResult.getResultObjects().size());
    }

    @Test
    public void testSetJSONWithEntries() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 2);
        
        List<Entry> entries = new ArrayList<>();
        Entry entry1 = stack.contentType("test_ct").entry("entry1");
        Entry entry2 = stack.contentType("test_ct").entry("entry2");
        entries.add(entry1);
        entries.add(entry2);
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(2, queryResult.getCount());
        assertEquals(2, queryResult.getResultObjects().size());
    }

    @Test
    public void testSetJSONWithSchema() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray schema = new JSONArray();
        
        JSONObject field1 = new JSONObject();
        field1.put("field_name", "title");
        field1.put("data_type", "text");
        schema.put(field1);
        
        JSONObject field2 = new JSONObject();
        field2.put("field_name", "description");
        field2.put("data_type", "text");
        schema.put(field2);
        
        json.put("schema", schema);
        json.put("count", 0);
        
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertNotNull(queryResult.getSchema());
        assertEquals(2, queryResult.getSchema().length());
    }

    @Test
    public void testSetJSONWithContentType() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "blog");
        contentType.put("title", "Blog");
        contentType.put("description", "Blog content type");
        
        json.put("content_type", contentType);
        json.put("count", 0);
        
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertNotNull(queryResult.getContentType());
        assertEquals("blog", queryResult.getContentType().getString("uid"));
        assertEquals("Blog", queryResult.getContentType().getString("title"));
    }

    @Test
    public void testSetJSONWithAllFields() throws Exception {
        JSONObject json = new JSONObject();
        
        // Add count
        json.put("count", 10);
        
        // Add schema
        JSONArray schema = new JSONArray();
        JSONObject field = new JSONObject();
        field.put("field_name", "title");
        schema.put(field);
        json.put("schema", schema);
        
        // Add content_type
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "page");
        json.put("content_type", contentType);
        
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(stack.contentType("test_ct").entry("entry" + i));
        }
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(10, queryResult.getCount());
        assertNotNull(queryResult.getSchema());
        assertEquals(1, queryResult.getSchema().length());
        assertNotNull(queryResult.getContentType());
        assertEquals("page", queryResult.getContentType().getString("uid"));
        assertEquals(10, queryResult.getResultObjects().size());
    }

    // ========== NULL AND EMPTY TESTS ==========

    @Test
    public void testSetJSONWithNullJSON() throws Exception {
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, null, entries);
        
        // Should handle gracefully
        assertEquals(0, queryResult.getCount());
        assertNotNull(queryResult.getResultObjects());
    }

    @Test
    public void testSetJSONWithEmptyJSON() throws Exception {
        JSONObject json = new JSONObject();
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(0, queryResult.getCount());
        assertNull(queryResult.getSchema());
        assertNull(queryResult.getContentType());
    }

    @Test
    public void testSetJSONWithNullEntries() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 5);
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, null);
        
        assertEquals(5, queryResult.getCount());
        assertNull(queryResult.getResultObjects());
    }

    @Test
    public void testSetJSONWithEmptyEntries() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 0);
        
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(0, queryResult.getCount());
        assertEquals(0, queryResult.getResultObjects().size());
    }

    // ========== GETTER TESTS ==========

    @Test
    public void testGetResultObjects() throws Exception {
        JSONObject json = new JSONObject();
        List<Entry> entries = new ArrayList<>();
        entries.add(stack.contentType("test").entry("e1"));
        entries.add(stack.contentType("test").entry("e2"));
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        List<Entry> result = queryResult.getResultObjects();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetCount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 42);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(42, queryResult.getCount());
    }

    @Test
    public void testGetSchema() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray schema = new JSONArray();
        schema.put(new JSONObject().put("field", "value"));
        json.put("schema", schema);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        JSONArray result = queryResult.getSchema();
        assertNotNull(result);
        assertEquals(1, result.length());
    }

    @Test
    public void testGetContentType() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject ct = new JSONObject();
        ct.put("uid", "test_uid");
        json.put("content_type", ct);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        JSONObject result = queryResult.getContentType();
        assertNotNull(result);
        assertEquals("test_uid", result.getString("uid"));
    }

    // ========== COUNT FALLBACK TESTS ==========

    @Test
    public void testCountFallbackToEntriesField() throws Exception {
        JSONObject json = new JSONObject();
        // No "count" field, but has "entries" field
        json.put("entries", 15);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(15, queryResult.getCount());
    }

    @Test
    public void testCountPriorityOverEntries() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 10);
        json.put("entries", 20); // Should use "count" value
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(10, queryResult.getCount());
    }

    @Test
    public void testCountZeroFallbackToEntries() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 0);
        json.put("entries", 5); // Should fallback to "entries"
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(5, queryResult.getCount());
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testSetJSONWithEmptySchema() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray emptySchema = new JSONArray();
        json.put("schema", emptySchema);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertNotNull(queryResult.getSchema());
        assertEquals(0, queryResult.getSchema().length());
    }

    @Test
    public void testSetJSONWithEmptyContentType() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject emptyContentType = new JSONObject();
        json.put("content_type", emptyContentType);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertNotNull(queryResult.getContentType());
        assertEquals(0, queryResult.getContentType().length());
    }

    @Test
    public void testSetJSONWithLargeCount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", 10000);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(10000, queryResult.getCount());
    }

    @Test
    public void testSetJSONWithNegativeCount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("count", -1);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertEquals(-1, queryResult.getCount());
    }

    @Test
    public void testSetJSONWithComplexSchema() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray schema = new JSONArray();
        
        for (int i = 0; i < 20; i++) {
            JSONObject field = new JSONObject();
            field.put("field_name", "field_" + i);
            field.put("data_type", "text");
            field.put("uid", "field_uid_" + i);
            schema.put(field);
        }
        
        json.put("schema", schema);
        List<Entry> entries = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json, entries);
        
        assertNotNull(queryResult.getSchema());
        assertEquals(20, queryResult.getSchema().length());
    }

    // ========== MULTIPLE SET JSON CALLS TESTS ==========

    @Test
    public void testMultipleSetJSONCallsOverwrite() throws Exception {
        // First call
        JSONObject json1 = new JSONObject();
        json1.put("count", 5);
        List<Entry> entries1 = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(queryResult, json1, entries1);
        assertEquals(5, queryResult.getCount());
        
        // Second call - should overwrite
        JSONObject json2 = new JSONObject();
        json2.put("count", 10);
        List<Entry> entries2 = new ArrayList<>();
        method.invoke(queryResult, json2, entries2);
        assertEquals(10, queryResult.getCount());
    }

    // ========== STATE INDEPENDENCE TESTS ==========

    @Test
    public void testMultipleQueryResultInstances() throws Exception {
        QueryResult qr1 = new QueryResult();
        QueryResult qr2 = new QueryResult();
        
        JSONObject json1 = new JSONObject();
        json1.put("count", 5);
        List<Entry> entries1 = new ArrayList<>();
        
        JSONObject json2 = new JSONObject();
        json2.put("count", 10);
        List<Entry> entries2 = new ArrayList<>();
        
        Method method = QueryResult.class.getDeclaredMethod("setJSON", JSONObject.class, List.class);
        method.setAccessible(true);
        method.invoke(qr1, json1, entries1);
        method.invoke(qr2, json2, entries2);
        
        // Both should have independent state
        assertEquals(5, qr1.getCount());
        assertEquals(10, qr2.getCount());
    }
}

