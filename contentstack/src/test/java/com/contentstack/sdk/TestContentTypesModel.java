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
 * Comprehensive unit tests for ContentTypesModel class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestContentTypesModel {

    private ContentTypesModel model;

    @Before
    public void setUp() {
        model = new ContentTypesModel();
    }

    // ========== CONSTRUCTOR & INITIALIZATION TESTS ==========

    @Test
    public void testModelCreation() {
        assertNotNull(model);
        assertNotNull(model.getResponse());
        assertNotNull(model.getResultArray());
    }

    @Test
    public void testDefaultValues() {
        // Default response should be empty JSON object
        assertEquals(0, model.getResponse().length());
        
        // Default result array should be empty
        assertEquals(0, model.getResultArray().length());
    }

    // ========== SET JSON WITH CONTENT_TYPE TESTS ==========

    @Test
    public void testSetJSONWithSingleContentType() throws JSONException {
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "blog");
        contentType.put("title", "Blog");
        contentType.put("description", "Blog content type");
        
        JSONObject response = new JSONObject();
        response.put("content_type", contentType);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals("blog", result.getString("uid"));
        assertEquals("Blog", result.getString("title"));
    }

    @Test
    public void testSetJSONWithContentTypeArray() throws JSONException {
        JSONObject ct1 = new JSONObject();
        ct1.put("uid", "blog");
        ct1.put("title", "Blog");
        
        JSONObject ct2 = new JSONObject();
        ct2.put("uid", "page");
        ct2.put("title", "Page");
        
        JSONArray contentTypes = new JSONArray();
        contentTypes.put(ct1);
        contentTypes.put(ct2);
        
        JSONObject response = new JSONObject();
        response.put("content_types", contentTypes);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(2, result.length());
        assertEquals("blog", result.getJSONObject(0).getString("uid"));
        assertEquals("page", result.getJSONObject(1).getString("uid"));
    }

    @Test
    public void testSetJSONWithBothContentTypeAndArray() throws JSONException {
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "single_blog");
        
        JSONArray contentTypes = new JSONArray();
        JSONObject ct1 = new JSONObject();
        ct1.put("uid", "array_blog");
        contentTypes.put(ct1);
        
        JSONObject response = new JSONObject();
        response.put("content_type", contentType);
        response.put("content_types", contentTypes);
        
        model.setJSON(response);
        
        // Both should be set
        assertEquals("single_blog", model.getResponse().getString("uid"));
        assertEquals("array_blog", model.getResultArray().getJSONObject(0).getString("uid"));
    }

    // ========== NULL AND EMPTY TESTS ==========

    @Test
    public void testSetJSONWithNull() {
        model.setJSON(null);
        
        // Should not throw exception, defaults should remain
        assertNotNull(model.getResponse());
        assertNotNull(model.getResultArray());
    }

    @Test
    public void testSetJSONWithEmptyObject() throws JSONException {
        JSONObject emptyResponse = new JSONObject();
        model.setJSON(emptyResponse);
        
        // Should handle gracefully
        assertEquals(0, model.getResponse().length());
        assertEquals(0, model.getResultArray().length());
    }

    @Test
    public void testSetJSONWithoutContentTypeKeys() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("other_key", "other_value");
        response.put("random", "data");
        
        model.setJSON(response);
        
        // Should handle gracefully - no content_type or content_types keys
        assertEquals(0, model.getResponse().length());
        assertEquals(0, model.getResultArray().length());
    }

    // ========== MULTIPLE SET JSON CALLS TESTS ==========

    @Test
    public void testMultipleSetJSONCalls() throws JSONException {
        // First call
        JSONObject ct1 = new JSONObject();
        ct1.put("uid", "first");
        JSONObject response1 = new JSONObject();
        response1.put("content_type", ct1);
        model.setJSON(response1);
        assertEquals("first", model.getResponse().getString("uid"));
        
        // Second call - should overwrite
        JSONObject ct2 = new JSONObject();
        ct2.put("uid", "second");
        JSONObject response2 = new JSONObject();
        response2.put("content_type", ct2);
        model.setJSON(response2);
        assertEquals("second", model.getResponse().getString("uid"));
    }

    // ========== GETTER TESTS ==========

    @Test
    public void testGetResponse() throws JSONException {
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "test_uid");
        contentType.put("title", "Test Title");
        
        JSONObject response = new JSONObject();
        response.put("content_type", contentType);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertTrue(result.has("uid"));
        assertTrue(result.has("title"));
        assertEquals("test_uid", result.getString("uid"));
    }

    @Test
    public void testGetResultArray() throws JSONException {
        JSONArray contentTypes = new JSONArray();
        
        for (int i = 0; i < 5; i++) {
            JSONObject ct = new JSONObject();
            ct.put("uid", "type_" + i);
            ct.put("index", i);
            contentTypes.put(ct);
        }
        
        JSONObject response = new JSONObject();
        response.put("content_types", contentTypes);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(5, result.length());
        
        for (int i = 0; i < 5; i++) {
            assertEquals("type_" + i, result.getJSONObject(i).getString("uid"));
            assertEquals(i, result.getJSONObject(i).getInt("index"));
        }
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testSetJSONWithInvalidContentTypeValue() throws JSONException {
        // content_type is not a JSONObject but a string
        JSONObject response = new JSONObject();
        response.put("content_type", "not_an_object");
        
        model.setJSON(response);
        
        // Should handle exception gracefully
        assertNotNull(model.getResponse());
    }

    @Test
    public void testSetJSONWithInvalidContentTypesValue() throws JSONException {
        // content_types is not a JSONArray but a string
        JSONObject response = new JSONObject();
        response.put("content_types", "not_an_array");
        
        model.setJSON(response);
        
        // Should handle exception gracefully
        assertNotNull(model.getResultArray());
    }

    @Test
    public void testSetJSONWithEmptyContentTypeObject() throws JSONException {
        JSONObject emptyContentType = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("content_type", emptyContentType);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    public void testSetJSONWithEmptyContentTypesArray() throws JSONException {
        JSONArray emptyArray = new JSONArray();
        JSONObject response = new JSONObject();
        response.put("content_types", emptyArray);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(0, result.length());
    }

    // ========== COMPLEX DATA TESTS ==========

    @Test
    public void testSetJSONWithComplexContentType() throws JSONException {
        JSONObject schema = new JSONObject();
        schema.put("title", "Title Field");
        schema.put("type", "text");
        
        JSONArray schemaArray = new JSONArray();
        schemaArray.put(schema);
        
        JSONObject contentType = new JSONObject();
        contentType.put("uid", "complex_blog");
        contentType.put("title", "Complex Blog");
        contentType.put("description", "A complex blog content type");
        contentType.put("schema", schemaArray);
        contentType.put("created_at", "2023-01-01T00:00:00.000Z");
        contentType.put("updated_at", "2023-06-01T00:00:00.000Z");
        
        JSONObject response = new JSONObject();
        response.put("content_type", contentType);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals("complex_blog", result.getString("uid"));
        assertEquals("Complex Blog", result.getString("title"));
        assertTrue(result.has("schema"));
        assertEquals(1, result.getJSONArray("schema").length());
    }

    @Test
    public void testSetJSONWithLargeContentTypesArray() throws JSONException {
        JSONArray contentTypes = new JSONArray();
        
        // Create 100 content types
        for (int i = 0; i < 100; i++) {
            JSONObject ct = new JSONObject();
            ct.put("uid", "type_" + i);
            ct.put("title", "Title " + i);
            ct.put("index", i);
            contentTypes.put(ct);
        }
        
        JSONObject response = new JSONObject();
        response.put("content_types", contentTypes);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(100, result.length());
        assertEquals("type_0", result.getJSONObject(0).getString("uid"));
        assertEquals("type_99", result.getJSONObject(99).getString("uid"));
    }

    // ========== STATE PRESERVATION TESTS ==========

    @Test
    public void testGetResponseAfterMultipleSets() throws JSONException {
        // Set first content type
        JSONObject ct1 = new JSONObject();
        ct1.put("uid", "first_type");
        JSONObject response1 = new JSONObject();
        response1.put("content_type", ct1);
        model.setJSON(response1);
        
        assertEquals("first_type", model.getResponse().getString("uid"));
        
        // Set only content_types (no content_type)
        JSONArray contentTypes = new JSONArray();
        JSONObject ct2 = new JSONObject();
        ct2.put("uid", "array_type");
        contentTypes.put(ct2);
        
        JSONObject response2 = new JSONObject();
        response2.put("content_types", contentTypes);
        model.setJSON(response2);
        
        // content_type should remain from first call
        assertEquals("first_type", model.getResponse().getString("uid"));
        
        // content_types should be from second call
        assertEquals(1, model.getResultArray().length());
        assertEquals("array_type", model.getResultArray().getJSONObject(0).getString("uid"));
    }

    @Test
    public void testModelIndependence() throws JSONException {
        ContentTypesModel model1 = new ContentTypesModel();
        ContentTypesModel model2 = new ContentTypesModel();
        
        JSONObject ct1 = new JSONObject();
        ct1.put("uid", "model1_type");
        JSONObject response1 = new JSONObject();
        response1.put("content_type", ct1);
        model1.setJSON(response1);
        
        JSONObject ct2 = new JSONObject();
        ct2.put("uid", "model2_type");
        JSONObject response2 = new JSONObject();
        response2.put("content_type", ct2);
        model2.setJSON(response2);
        
        // Each model should have independent state
        assertEquals("model1_type", model1.getResponse().getString("uid"));
        assertEquals("model2_type", model2.getResponse().getString("uid"));
        assertNotEquals(model1.getResponse().getString("uid"), model2.getResponse().getString("uid"));
    }
}

