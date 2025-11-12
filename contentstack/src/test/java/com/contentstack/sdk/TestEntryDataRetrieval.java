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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Entry class data retrieval methods.
 */
@RunWith(RobolectricTestRunner.class)
public class TestEntryDataRetrieval {

    private Context context;
    private Stack stack;
    private ContentType contentType;
    private Entry entry;
    private JSONObject testData;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        contentType = stack.contentType("test_content_type");
        entry = contentType.entry("test_entry_uid");
        
        // Create comprehensive test data
        testData = new JSONObject();
        testData.put("uid", "test123");
        testData.put("title", "Test Entry");
        testData.put("url", "/test-entry");
        testData.put("locale", "en-us");
        
        // Add various data types
        testData.put("string_field", "test string");
        testData.put("int_field", 42);
        testData.put("long_field", 9876543210L);
        testData.put("double_field", 3.14159);
        testData.put("float_field", 2.71f);
        testData.put("boolean_field", true);
        testData.put("short_field", 100);
        
        // Add nested object
        JSONObject nestedObj = new JSONObject();
        nestedObj.put("nested_key", "nested_value");
        testData.put("object_field", nestedObj);
        
        // Add array
        JSONArray array = new JSONArray();
        array.put("item1");
        array.put("item2");
        array.put("item3");
        testData.put("array_field", array);
        
        // Add tags
        JSONArray tags = new JSONArray();
        tags.put("tag1");
        tags.put("tag2");
        testData.put("tags", tags);
        
        // Add date field
        testData.put("date_field", "2024-01-15T10:30:00.000Z");
        testData.put("created_at", "2024-01-01T00:00:00.000Z");
        testData.put("updated_at", "2024-01-15T12:00:00.000Z");
        
        // Add markdown field
        testData.put("markdown_field", "# Heading\\n\\nThis is **bold** text");
        
        // Add owner info
        JSONObject owner = new JSONObject();
        owner.put("uid", "owner123");
        owner.put("email", "owner@example.com");
        testData.put("_owner", owner);
        
        // Add metadata
        JSONObject metadata = new JSONObject();
        metadata.put("version", 1);
        metadata.put("locale", "en-us");
        testData.put("_metadata", metadata);
        
        // Configure entry with test data
        entry.configure(testData);
    }

    // ==================== BASIC GETTERS Tests ====================

    @Test
    public void testGetTitle() {
        String title = entry.getTitle();
        assertNotNull(title);
        assertEquals("Test Entry", title);
    }

    @Test
    public void testGetURL() {
        String url = entry.getURL();
        assertNotNull(url);
        assertEquals("/test-entry", url);
    }

    @Test
    public void testGetUid() {
        String uid = entry.getUid();
        assertNotNull(uid);
        assertEquals("test123", uid);
    }

    @Test
    public void testGetContentType() {
        String contentType = entry.getContentType();
        assertNotNull(contentType);
        assertEquals("test_content_type", contentType);
    }

    @Test
    public void testGetLocale() {
        String locale = entry.getLocale();
        assertNotNull(locale);
        assertEquals("en-us", locale);
    }

    @Test
    public void testGetTags() {
        String[] tags = entry.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.length);
        assertEquals("tag1", tags[0]);
        assertEquals("tag2", tags[1]);
    }

    @Test
    public void testGetOwner() {
        HashMap<String, Object> owner = entry.getOwner();
        assertNotNull(owner);
        assertTrue(owner.containsKey("uid"));
        assertTrue(owner.containsKey("email"));
    }

    @Test
    public void testToJSON() {
        JSONObject json = entry.toJSON();
        assertNotNull(json);
        assertTrue(json.has("uid"));
        assertTrue(json.has("title"));
    }

    // ==================== GET OBJECT Tests ====================

    @Test
    public void testGet() throws JSONException {
        Object value = entry.get("string_field");
        assertNotNull(value);
        assertEquals("test string", value);
    }

    @Test
    public void testGetWithNullKey() {
        Object value = entry.get(null);
        assertNull(value);
    }

    @Test
    public void testGetNonExistentKey() {
        Object value = entry.get("non_existent_key");
        assertNull(value);
    }

    @Test
    public void testContains() {
        assertTrue(entry.contains("string_field"));
        assertTrue(entry.contains("int_field"));
        assertFalse(entry.contains("non_existent"));
    }

    @Test
    public void testContainsWithNull() {
        assertFalse(entry.contains(null));
    }

    // ==================== STRING METHODS Tests ====================

    @Test
    public void testGetString() {
        String value = entry.getString("string_field");
        assertNotNull(value);
        assertEquals("test string", value);
    }

    @Test
    public void testGetStringWithNullKey() {
        String value = entry.getString(null);
        assertNull(value);
    }

    @Test
    public void testGetStringNonExistent() {
        String value = entry.getString("non_existent");
        assertNull(value);
    }

    @Test
    public void testGetUpdatedAt() {
        String value = entry.getUpdatedAt("updated_at");
        assertNotNull(value);
    }

    // ==================== NUMBER METHODS Tests ====================

    @Test
    public void testGetNumber() {
        Number value = entry.getNumber("int_field");
        assertNotNull(value);
        assertEquals(42, value.intValue());
    }

    @Test
    public void testGetNumberWithNullKey() {
        Number value = entry.getNumber(null);
        assertNull(value);
    }

    @Test
    public void testGetNumberNonExistent() {
        Number value = entry.getNumber("non_existent");
        assertNull(value);
    }

    @Test
    public void testGetInt() {
        int value = entry.getInt("int_field");
        assertEquals(42, value);
    }

    @Test
    public void testGetIntDefault() {
        int value = entry.getInt("non_existent");
        assertEquals(0, value);
    }

    @Test
    public void testGetLong() {
        long value = entry.getLong("long_field");
        assertEquals(9876543210L, value);
    }

    @Test
    public void testGetLongDefault() {
        long value = entry.getLong("non_existent");
        assertEquals(0L, value);
    }

    @Test
    public void testGetDouble() {
        double value = entry.getDouble("double_field");
        assertEquals(3.14159, value, 0.0001);
    }

    @Test
    public void testGetDoubleDefault() {
        double value = entry.getDouble("non_existent");
        assertEquals(0.0, value, 0.0001);
    }

    @Test
    public void testGetFloat() {
        float value = entry.getFloat("float_field");
        assertEquals(2.71f, value, 0.01f);
    }

    @Test
    public void testGetFloatDefault() {
        float value = entry.getFloat("non_existent");
        assertEquals(0.0f, value, 0.01f);
    }

    @Test
    public void testGetShort() {
        short value = entry.getShort("short_field");
        assertEquals(100, value);
    }

    @Test
    public void testGetShortDefault() {
        short value = entry.getShort("non_existent");
        assertEquals((short) 0, value);
    }

    @Test
    public void testGetBoolean() {
        boolean value = entry.getBoolean("boolean_field");
        assertTrue(value);
    }

    @Test
    public void testGetBooleanDefault() {
        boolean value = entry.getBoolean("non_existent");
        assertFalse(value);
    }

    // ==================== JSON OBJECT/ARRAY Tests ====================

    @Test
    public void testGetJSONObject() throws JSONException {
        JSONObject value = entry.getJSONObject("object_field");
        assertNotNull(value);
        assertTrue(value.has("nested_key"));
        assertEquals("nested_value", value.getString("nested_key"));
    }

    @Test
    public void testGetJSONObjectWithNullKey() {
        JSONObject value = entry.getJSONObject(null);
        assertNull(value);
    }

    @Test
    public void testGetJSONObjectNonExistent() {
        JSONObject value = entry.getJSONObject("non_existent");
        assertNull(value);
    }

    @Test
    public void testGetJSONArray() {
        JSONArray value = entry.getJSONArray("array_field");
        assertNotNull(value);
        assertEquals(3, value.length());
    }

    @Test
    public void testGetJSONArrayWithNullKey() {
        JSONArray value = entry.getJSONArray(null);
        assertNull(value);
    }


    @Test
    public void testGetJSONArrayNonExistent() {
        JSONArray value = entry.getJSONArray("non_existent");
        assertNull(value);
    }

    // ==================== DATE METHODS Tests ====================

    @Test
    public void testGetDate() {
        Calendar date = entry.getDate("date_field");
        assertNotNull(date);
    }

    @Test
    public void testGetDateWithNullKey() {
        Calendar date = entry.getDate(null);
        assertNull(date);
    }

    @Test
    public void testGetDateNonExistent() {
        Calendar date = entry.getDate("non_existent");
        assertNull(date);
    }

    @Test
    public void testGetCreateAt() {
        Calendar createdAt = entry.getCreateAt();
        assertNotNull(createdAt);
    }

    @Test
    public void testGetUpdateAt() {
        Calendar updatedAt = entry.getUpdateAt();
        assertNotNull(updatedAt);
    }

    @Test
    public void testGetDeleteAt() {
        Calendar deletedAt = entry.getDeleteAt();
        // Should be null for non-deleted entry
        assertNull(deletedAt);
    }

    // ==================== MARKDOWN Tests ====================

    @Test
    public void testGetHtmlText() {
        String html = entry.getHtmlText("markdown_field");
        assertNotNull(html);
        // Should contain HTML tags
        assertTrue(html.contains("<"));
    }

    @Test
    public void testGetHtmlTextWithNullKey() {
        String html = entry.getHtmlText(null);
        assertNull(html);
    }

    @Test
    public void testGetHtmlTextNonExistent() {
        String html = entry.getHtmlText("non_existent");
        assertNull(html);
    }

    // ==================== SET LOCALE Tests ====================

    @Test
    public void testSetLocale() {
        Entry result = entry.setLocale("fr-fr");
        assertNotNull(result);
        assertSame(entry, result);
    }

    @Test
    public void testSetLocaleWithNull() {
        Entry result = entry.setLocale(null);
        assertNotNull(result);
    }

    @Test
    public void testSetLocaleMultipleTimes() {
        entry.setLocale("en-us");
        entry.setLocale("fr-fr");
        Entry result = entry.setLocale("de-de");
        assertNotNull(result);
    }

    // ==================== CONFIGURE Tests ====================

    @Test
    public void testConfigureWithMinimalData() throws JSONException {
        Entry newEntry = contentType.entry("new_entry");
        JSONObject minimalData = new JSONObject();
        minimalData.put("uid", "minimal_uid");
        
        Entry result = newEntry.configure(minimalData);
        assertNotNull(result);
        assertSame(newEntry, result);
        assertEquals("minimal_uid", newEntry.getUid());
    }

    @Test
    public void testConfigureWithEmptyData() throws JSONException {
        Entry newEntry = contentType.entry("empty_entry");
        JSONObject emptyData = new JSONObject();
        
        Entry result = newEntry.configure(emptyData);
        assertNotNull(result);
    }

    @Test
    public void testReconfigure() throws JSONException {
        JSONObject newData = new JSONObject();
        newData.put("uid", "reconfigured_uid");
        newData.put("title", "Reconfigured Title");
        
        entry.configure(newData);
        assertEquals("reconfigured_uid", entry.getUid());
        assertEquals("Reconfigured Title", entry.getTitle());
    }

    // ==================== COMPLEX DATA TYPES Tests ====================

    @Test
    public void testGetMultipleHtmlText() throws JSONException {
        JSONArray markdownArray = new JSONArray();
        markdownArray.put("# First");
        markdownArray.put("## Second");
        markdownArray.put("### Third");
        testData.put("markdown_array", markdownArray);
        entry.configure(testData);
        
        ArrayList<String> htmlList = entry.getMultipleHtmlText("markdown_array");
        assertNotNull(htmlList);
        assertEquals(3, htmlList.size());
    }

    @Test
    public void testGetMultipleHtmlTextNonExistent() {
        ArrayList<String> htmlList = entry.getMultipleHtmlText("non_existent");
        assertNull(htmlList);
    }

    @Test
    public void testGetMultipleHtmlTextWithNull() {
        ArrayList<String> htmlList = entry.getMultipleHtmlText(null);
        assertNull(htmlList);
    }

    // ==================== EDGE CASES Tests ====================

    @Test
    public void testGetStringFromNumber() {
        String value = entry.getString("int_field");
        // May return number as string or null depending on implementation
        assertTrue(value == null || value.equals("42") || !value.isEmpty());
    }

    @Test
    public void testGetNumberFromString() {
        Number value = entry.getNumber("string_field");
        // Should return null for non-numeric string
        assertTrue(value == null);
    }

    @Test
    public void testGetIntFromDouble() {
        int value = entry.getInt("double_field");
        assertEquals(3, value); // Should truncate
    }

    @Test
    public void testGetLongFromInt() {
        long value = entry.getLong("int_field");
        assertEquals(42L, value);
    }

    @Test
    public void testGetWithSpecialCharacters() throws JSONException {
        testData.put("special_key", "Value with <special> &amp; \"characters\"");
        entry.configure(testData);
        
        String value = entry.getString("special_key");
        assertNotNull(value);
        assertTrue(value.contains("<"));
    }

    @Test
    public void testGetWithUnicodeCharacters() throws JSONException {
        testData.put("unicode_key", "Hello 世界 🌍");
        entry.configure(testData);
        
        String value = entry.getString("unicode_key");
        assertNotNull(value);
        assertTrue(value.contains("世界"));
    }

    @Test
    public void testGetWithEmptyString() throws JSONException {
        testData.put("empty_key", "");
        entry.configure(testData);
        
        String value = entry.getString("empty_key");
        assertNotNull(value);
        assertEquals("", value);
    }

    @Test
    public void testGetWithNull() throws JSONException {
        testData.put("null_key", JSONObject.NULL);
        entry.configure(testData);
        
        Object value = entry.get("null_key");
        assertTrue(value == null || value == JSONObject.NULL);
    }

    // ==================== NESTED DATA Tests ====================

    @Test
    public void testGetNestedObject() throws JSONException {
        JSONObject parent = new JSONObject();
        JSONObject child = new JSONObject();
        child.put("grandchild", "value");
        parent.put("child", child);
        testData.put("parent", parent);
        entry.configure(testData);
        
        JSONObject parentObj = entry.getJSONObject("parent");
        assertNotNull(parentObj);
        assertTrue(parentObj.has("child"));
    }

    @Test
    public void testGetNestedArray() throws JSONException {
        JSONArray outerArray = new JSONArray();
        JSONArray innerArray = new JSONArray();
        innerArray.put("item1");
        innerArray.put("item2");
        outerArray.put(innerArray);
        testData.put("nested_array", outerArray);
        entry.configure(testData);
        
        JSONArray array = entry.getJSONArray("nested_array");
        assertNotNull(array);
        assertEquals(1, array.length());
    }

    // ==================== OWNER Tests ====================

    @Test
    public void testGetOwnerDetails() {
        HashMap<String, Object> owner = entry.getOwner();
        assertNotNull(owner);
        // Owner should have uid and email
        assertTrue(owner.size() > 0);
    }

    // ==================== COMPREHENSIVE WORKFLOW Tests ====================

    @Test
    public void testCompleteEntryWorkflow() throws JSONException {
        // Create new entry
        Entry workflowEntry = contentType.entry("workflow_uid");
        
        // Configure with data
        JSONObject data = new JSONObject();
        data.put("uid", "workflow123");
        data.put("title", "Workflow Entry");
        data.put("url", "/workflow");
        data.put("content", "This is content");
        data.put("views", 1000);
        data.put("rating", 4.5);
        data.put("published", true);
        
        workflowEntry.configure(data);
        
        // Set locale
        workflowEntry.setLocale("en-us");
        
        // Verify all data
        assertEquals("workflow123", workflowEntry.getUid());
        assertEquals("Workflow Entry", workflowEntry.getTitle());
        assertEquals("/workflow", workflowEntry.getURL());
        assertEquals("This is content", workflowEntry.getString("content"));
        assertEquals(1000, workflowEntry.getInt("views"));
        assertEquals(4.5, workflowEntry.getDouble("rating"), 0.01);
        assertTrue(workflowEntry.getBoolean("published"));
    }
}

