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
 * Comprehensive unit tests for GlobalFieldsModel class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestGlobalFieldsModel {

    private GlobalFieldsModel model;

    @Before
    public void setUp() {
        model = new GlobalFieldsModel();
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
        assertEquals(0, model.getResponse().length());
        assertEquals(0, model.getResultArray().length());
    }

    // ========== SET JSON WITH GLOBAL_FIELD TESTS ==========

    @Test
    public void testSetJSONWithSingleGlobalField() throws JSONException {
        JSONObject globalField = new JSONObject();
        globalField.put("uid", "seo_metadata");
        globalField.put("title", "SEO Metadata");
        globalField.put("description", "Global field for SEO");
        
        JSONObject response = new JSONObject();
        response.put("global_field", globalField);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals("seo_metadata", result.getString("uid"));
        assertEquals("SEO Metadata", result.getString("title"));
    }

    @Test
    public void testSetJSONWithGlobalFieldsArray() throws JSONException {
        JSONObject gf1 = new JSONObject();
        gf1.put("uid", "seo");
        gf1.put("title", "SEO");
        
        JSONObject gf2 = new JSONObject();
        gf2.put("uid", "meta");
        gf2.put("title", "Meta");
        
        JSONArray globalFields = new JSONArray();
        globalFields.put(gf1);
        globalFields.put(gf2);
        
        JSONObject response = new JSONObject();
        response.put("global_fields", globalFields);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(2, result.length());
        assertEquals("seo", result.getJSONObject(0).getString("uid"));
        assertEquals("meta", result.getJSONObject(1).getString("uid"));
    }

    @Test
    public void testSetJSONWithBothGlobalFieldAndArray() throws JSONException {
        JSONObject globalField = new JSONObject();
        globalField.put("uid", "single_field");
        
        JSONArray globalFields = new JSONArray();
        JSONObject gf1 = new JSONObject();
        gf1.put("uid", "array_field");
        globalFields.put(gf1);
        
        JSONObject response = new JSONObject();
        response.put("global_field", globalField);
        response.put("global_fields", globalFields);
        
        model.setJSON(response);
        
        assertEquals("single_field", model.getResponse().getString("uid"));
        assertEquals("array_field", model.getResultArray().getJSONObject(0).getString("uid"));
    }

    // ========== NULL AND EMPTY TESTS ==========

    @Test
    public void testSetJSONWithNull() {
        model.setJSON(null);
        assertNotNull(model.getResponse());
        assertNotNull(model.getResultArray());
    }

    @Test
    public void testSetJSONWithEmptyObject() throws JSONException {
        JSONObject emptyResponse = new JSONObject();
        model.setJSON(emptyResponse);
        
        assertEquals(0, model.getResponse().length());
        assertEquals(0, model.getResultArray().length());
    }

    @Test
    public void testSetJSONWithoutGlobalFieldKeys() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("other_key", "other_value");
        response.put("random", "data");
        
        model.setJSON(response);
        
        assertEquals(0, model.getResponse().length());
        assertEquals(0, model.getResultArray().length());
    }

    // ========== MULTIPLE SET JSON CALLS TESTS ==========

    @Test
    public void testMultipleSetJSONCalls() throws JSONException {
        JSONObject gf1 = new JSONObject();
        gf1.put("uid", "first_field");
        JSONObject response1 = new JSONObject();
        response1.put("global_field", gf1);
        model.setJSON(response1);
        assertEquals("first_field", model.getResponse().getString("uid"));
        
        JSONObject gf2 = new JSONObject();
        gf2.put("uid", "second_field");
        JSONObject response2 = new JSONObject();
        response2.put("global_field", gf2);
        model.setJSON(response2);
        assertEquals("second_field", model.getResponse().getString("uid"));
    }

    // ========== GETTER TESTS ==========

    @Test
    public void testGetResponse() throws JSONException {
        JSONObject globalField = new JSONObject();
        globalField.put("uid", "test_uid");
        globalField.put("title", "Test Title");
        
        JSONObject response = new JSONObject();
        response.put("global_field", globalField);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertTrue(result.has("uid"));
        assertTrue(result.has("title"));
        assertEquals("test_uid", result.getString("uid"));
    }

    @Test
    public void testGetResultArray() throws JSONException {
        JSONArray globalFields = new JSONArray();
        
        for (int i = 0; i < 5; i++) {
            JSONObject gf = new JSONObject();
            gf.put("uid", "field_" + i);
            gf.put("index", i);
            globalFields.put(gf);
        }
        
        JSONObject response = new JSONObject();
        response.put("global_fields", globalFields);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(5, result.length());
        
        for (int i = 0; i < 5; i++) {
            assertEquals("field_" + i, result.getJSONObject(i).getString("uid"));
            assertEquals(i, result.getJSONObject(i).getInt("index"));
        }
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testSetJSONWithInvalidGlobalFieldValue() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("global_field", "not_an_object");
        
        model.setJSON(response);
        assertNotNull(model.getResponse());
    }

    @Test
    public void testSetJSONWithInvalidGlobalFieldsValue() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("global_fields", "not_an_array");
        
        model.setJSON(response);
        assertNotNull(model.getResultArray());
    }

    @Test
    public void testSetJSONWithEmptyGlobalField() throws JSONException {
        JSONObject emptyGlobalField = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("global_field", emptyGlobalField);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals(0, result.length());
    }

    @Test
    public void testSetJSONWithEmptyGlobalFieldsArray() throws JSONException {
        JSONArray emptyArray = new JSONArray();
        JSONObject response = new JSONObject();
        response.put("global_fields", emptyArray);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(0, result.length());
    }

    // ========== COMPLEX DATA TESTS ==========

    @Test
    public void testSetJSONWithComplexGlobalField() throws JSONException {
        JSONObject schema = new JSONObject();
        schema.put("field_name", "Meta Title");
        schema.put("data_type", "text");
        
        JSONArray schemaArray = new JSONArray();
        schemaArray.put(schema);
        
        JSONObject globalField = new JSONObject();
        globalField.put("uid", "seo_meta");
        globalField.put("title", "SEO Meta");
        globalField.put("description", "SEO metadata fields");
        globalField.put("schema", schemaArray);
        globalField.put("created_at", "2023-01-01T00:00:00.000Z");
        globalField.put("updated_at", "2023-06-01T00:00:00.000Z");
        
        JSONObject response = new JSONObject();
        response.put("global_field", globalField);
        
        model.setJSON(response);
        
        JSONObject result = model.getResponse();
        assertNotNull(result);
        assertEquals("seo_meta", result.getString("uid"));
        assertEquals("SEO Meta", result.getString("title"));
        assertTrue(result.has("schema"));
        assertEquals(1, result.getJSONArray("schema").length());
    }

    @Test
    public void testSetJSONWithLargeGlobalFieldsArray() throws JSONException {
        JSONArray globalFields = new JSONArray();
        
        for (int i = 0; i < 100; i++) {
            JSONObject gf = new JSONObject();
            gf.put("uid", "field_" + i);
            gf.put("title", "Title " + i);
            gf.put("index", i);
            globalFields.put(gf);
        }
        
        JSONObject response = new JSONObject();
        response.put("global_fields", globalFields);
        
        model.setJSON(response);
        
        JSONArray result = model.getResultArray();
        assertNotNull(result);
        assertEquals(100, result.length());
        assertEquals("field_0", result.getJSONObject(0).getString("uid"));
        assertEquals("field_99", result.getJSONObject(99).getString("uid"));
    }

    // ========== STATE PRESERVATION TESTS ==========

    @Test
    public void testGetResponseAfterMultipleSets() throws JSONException {
        JSONObject gf1 = new JSONObject();
        gf1.put("uid", "first");
        JSONObject response1 = new JSONObject();
        response1.put("global_field", gf1);
        model.setJSON(response1);
        
        assertEquals("first", model.getResponse().getString("uid"));
        
        JSONArray globalFields = new JSONArray();
        JSONObject gf2 = new JSONObject();
        gf2.put("uid", "array_field");
        globalFields.put(gf2);
        
        JSONObject response2 = new JSONObject();
        response2.put("global_fields", globalFields);
        model.setJSON(response2);
        
        assertEquals("first", model.getResponse().getString("uid"));
        assertEquals(1, model.getResultArray().length());
        assertEquals("array_field", model.getResultArray().getJSONObject(0).getString("uid"));
    }

    @Test
    public void testModelIndependence() throws JSONException {
        GlobalFieldsModel model1 = new GlobalFieldsModel();
        GlobalFieldsModel model2 = new GlobalFieldsModel();
        
        JSONObject gf1 = new JSONObject();
        gf1.put("uid", "model1_field");
        JSONObject response1 = new JSONObject();
        response1.put("global_field", gf1);
        model1.setJSON(response1);
        
        JSONObject gf2 = new JSONObject();
        gf2.put("uid", "model2_field");
        JSONObject response2 = new JSONObject();
        response2.put("global_field", gf2);
        model2.setJSON(response2);
        
        assertEquals("model1_field", model1.getResponse().getString("uid"));
        assertEquals("model2_field", model2.getResponse().getString("uid"));
        assertNotEquals(model1.getResponse().getString("uid"), model2.getResponse().getString("uid"));
    }
}

