package com.contentstack.sdk;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestEntry {

    private Context mockContext;
    private Stack stack;
    private ContentType contentType;
    private Entry entry;
    private JSONObject mockEntryJson;

    @Before
    public void setUp() throws Exception {
        mockContext = TestUtils.createMockContext();
        stack = Contentstack.stack(mockContext,
            TestUtils.getTestApiKey(),
            TestUtils.getTestDeliveryToken(),
            TestUtils.getTestEnvironment());
        contentType = stack.contentType(TestUtils.getTestContentType());
        entry = contentType.entry(TestUtils.getTestEntryUid());
        mockEntryJson = TestUtils.createMockEntryJson();
        entry.configure(mockEntryJson);
        TestUtils.cleanupTestCache();
    }

    @After
    public void tearDown() {
        TestUtils.cleanupTestCache();
        entry = null;
        contentType = null;
        stack = null;
        mockContext = null;
    }

    @Test
    public void testEntryCreation() {
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testConfigure() throws JSONException {
        JSONObject json = TestUtils.createMockEntryJson();
        Entry configuredEntry = entry.configure(json);
        assertNotNull("Configured entry should not be null", configuredEntry);
        assertEquals("Entry should return itself", entry, configuredEntry);
    }

    @Test
    public void testGetTitle() {
        String title = entry.getTitle();
        assertNotNull("Title should not be null", title);
        assertEquals("Title should match", "Test Entry Title", title);
    }

    @Test
    public void testGetURL() {
        String url = entry.getURL();
        assertNotNull("URL should not be null", url);
        assertEquals("URL should match", "/test-entry", url);
    }

    @Test
    public void testGetTags() {
        String[] tags = entry.getTags();
        assertNotNull("Tags should not be null", tags);
        assertEquals("Should have 2 tags", 2, tags.length);
    }

    @Test
    public void testGetContentType() {
        String contentTypeName = entry.getContentType();
        assertEquals("Content type should match", TestUtils.getTestContentType(), contentTypeName);
    }

    @Test
    public void testGetUid() {
        String uid = entry.getUid();
        assertEquals("UID should match", "test_entry_uid", uid);
    }

    @Test
    public void testGetLocale() {
        String locale = entry.getLocale();
        assertNotNull("Locale should not be null", locale);
        assertEquals("Locale should be en-us", "en-us", locale);
    }

    @Test
    public void testSetLocale() {
        Entry result = entry.setLocale("fr-fr");
        assertNotNull("Entry should not be null after setLocale", result);
        assertEquals("Should return same entry", entry, result);
    }

    @Test
    public void testGetOwner() {
        java.util.HashMap<String, Object> owner = entry.getOwner();
        assertNotNull("Owner should not be null", owner);
    }

    @Test
    public void testToJSON() {
        JSONObject json = entry.toJSON();
        assertNotNull("JSON should not be null", json);
    }

    @Test
    public void testGet() {
        Object value = entry.get("description");
        assertNotNull("Value should not be null", value);
        assertEquals("Value should match", "Test description", value);
    }

    @Test
    public void testGetWithNullKey() {
        Object value = entry.get(null);
        assertNull("Value should be null for null key", value);
    }

    @Test
    public void testGetWithNonExistentKey() {
        Object value = entry.get("non_existent_key");
        assertNull("Value should be null for non-existent key", value);
    }

    @Test
    public void testContains() {
        Boolean contains = entry.contains("description");
        assertTrue("Should contain description", contains);
    }

    @Test
    public void testContainsWithNonExistentKey() {
        Boolean contains = entry.contains("non_existent");
        assertFalse("Should not contain non-existent key", contains);
    }

    @Test
    public void testContainsWithNullKey() {
        Boolean contains = entry.contains(null);
        assertFalse("Should return false for null key", contains);
    }

    @Test
    public void testGetString() {
        String value = entry.getString("description");
        assertNotNull("String value should not be null", value);
        assertEquals("String value should match", "Test description", value);
    }

    @Test
    public void testGetStringWithNullKey() {
        String value = entry.getString(null);
        assertNull("String should be null for null key", value);
    }

    @Test
    public void testGetBoolean() {
        Boolean value = entry.getBoolean("test_boolean");
        assertNotNull("Boolean should not be null", value);
        assertTrue("Boolean should be true", value);
    }

    @Test
    public void testGetBooleanWithNonBooleanField() {
        Boolean value = entry.getBoolean("description");
        assertFalse("Should return false for non-boolean field", value);
    }

    @Test
    public void testGetNumber() {
        Number value = entry.getNumber("test_number");
        assertNotNull("Number should not be null", value);
        assertEquals("Number should match", 42, value.intValue());
    }

    @Test
    public void testGetInt() {
        int value = entry.getInt("test_number");
        assertEquals("Int should match", 42, value);
    }

    @Test
    public void testGetIntWithNonNumericField() {
        int value = entry.getInt("description");
        assertEquals("Should return 0 for non-numeric field", 0, value);
    }

    @Test
    public void testGetFloat() {
        float value = entry.getFloat("test_number");
        assertEquals("Float should match", 42.0f, value, 0.01f);
    }

    @Test
    public void testGetDouble() {
        double value = entry.getDouble("test_number");
        assertEquals("Double should match", 42.0, value, 0.01);
    }

    @Test
    public void testGetLong() {
        long value = entry.getLong("test_number");
        assertEquals("Long should match", 42L, value);
    }

    @Test
    public void testGetShort() {
        short value = entry.getShort("test_number");
        assertEquals("Short should match", 42, value);
    }

    @Test
    public void testGetDate() {
        Calendar date = entry.getDate("created_at");
        assertNotNull("Date should not be null", date);
    }

    @Test
    public void testGetCreateAt() {
        Calendar createdAt = entry.getCreateAt();
        assertNotNull("CreatedAt should not be null", createdAt);
    }

    @Test
    public void testGetCreatedBy() {
        String createdBy = entry.getCreatedBy();
        assertEquals("CreatedBy should match", "creator_uid", createdBy);
    }

    @Test
    public void testGetUpdateAt() {
        Calendar updatedAt = entry.getUpdateAt();
        assertNotNull("UpdatedAt should not be null", updatedAt);
    }

    @Test
    public void testGetUpdatedBy() {
        String updatedBy = entry.getUpdatedBy();
        assertEquals("UpdatedBy should match", "updater_uid", updatedBy);
    }

    @Test
    public void testSetHeader() {
        entry.setHeader("custom-header", "custom-value");
        assertNotNull("Entry should not be null after setHeader", entry);
    }

    @Test
    public void testSetHeaderWithNullKey() {
        entry.setHeader(null, "value");
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testSetHeaderWithNullValue() {
        entry.setHeader("key", null);
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testRemoveHeader() {
        entry.setHeader("custom-header", "custom-value");
        entry.removeHeader("custom-header");
        assertNotNull("Entry should not be null after removeHeader", entry);
    }

    @Test
    public void testRemoveHeaderWithNullKey() {
        entry.removeHeader(null);
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testExcept() {
        String[] fields = {"field1", "field2"};
        Entry result = entry.except(fields);
        assertNotNull("Entry should not be null after except", result);
        assertEquals("Should return same entry", entry, result);
    }

    @Test
    public void testExceptWithNullArray() {
        Entry result = entry.except(null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testExceptWithEmptyArray() {
        String[] fields = {};
        Entry result = entry.except(fields);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testIncludeReference() {
        Entry result = entry.includeReference("reference_field");
        assertNotNull("Entry should not be null after includeReference", result);
    }

    @Test
    public void testIncludeReferenceWithArray() {
        String[] references = {"ref1", "ref2"};
        Entry result = entry.includeReference(references);
        assertNotNull("Entry should not be null after includeReference", result);
    }

    @Test
    public void testIncludeReferenceWithNullArray() {
        Entry result = entry.includeReference((String[]) null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testOnly() {
        String[] fields = {"title", "description"};
        Entry result = entry.only(fields);
        assertNotNull("Entry should not be null after only", result);
    }

    @Test
    public void testOnlyWithNullArray() {
        Entry result = entry.only(null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testOnlyWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        Entry result = entry.onlyWithReferenceUid(fields, "ref_uid");
        assertNotNull("Entry should not be null after onlyWithReferenceUid", result);
    }

    @Test
    public void testExceptWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        Entry result = entry.exceptWithReferenceUid(fields, "ref_uid");
        assertNotNull("Entry should not be null after exceptWithReferenceUid", result);
    }

    @Test
    public void testCancelRequest() {
        entry.cancelRequest();
        assertNotNull("Entry should not be null after cancelRequest", entry);
    }

    @Test
    public void testSetCachePolicy() {
        entry.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull("Entry should not be null after setCachePolicy", entry);
    }

    @Test
    public void testSetCachePolicyWithAllPolicies() {
        CachePolicy[] policies = CachePolicy.values();
        for (CachePolicy policy : policies) {
            entry.setCachePolicy(policy);
            assertNotNull("Entry should not be null for policy " + policy, entry);
        }
    }

    @Test
    public void testAddParam() {
        Entry result = entry.addParam("key", "value");
        assertNotNull("Entry should not be null after addParam", result);
    }

    @Test
    public void testAddParamWithNullKey() {
        Entry result = entry.addParam(null, "value");
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testAddParamWithNullValue() {
        Entry result = entry.addParam("key", null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testIncludeReferenceContentTypeUID() {
        Entry result = entry.includeReferenceContentTypeUID();
        assertNotNull("Entry should not be null after includeReferenceContentTypeUID", result);
    }

    @Test
    public void testIncludeContentType() {
        Entry result = entry.includeContentType();
        assertNotNull("Entry should not be null after includeContentType", result);
    }

    @Test
    public void testIncludeFallback() {
        Entry result = entry.includeFallback();
        assertNotNull("Entry should not be null after includeFallback", result);
    }

    @Test
    public void testIncludeEmbeddedItems() {
        Entry result = entry.includeEmbeddedItems();
        assertNotNull("Entry should not be null after includeEmbeddedItems", result);
    }

    @Test
    public void testIncludeMetadata() {
        Entry result = entry.includeMetadata();
        assertNotNull("Entry should not be null after includeMetadata", result);
    }

    @Test
    public void testVariantsWithString() {
        Entry result = entry.variants("variant_uid");
        assertNotNull("Entry should not be null after variants", result);
    }

    @Test
    public void testVariantsWithArray() {
        String[] variants = {"variant1", "variant2"};
        Entry result = entry.variants(variants);
        assertNotNull("Entry should not be null after variants", result);
    }

    @Test
    public void testVariantsWithNullString() {
        Entry result = entry.variants((String) null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testVariantsWithEmptyString() {
        Entry result = entry.variants("");
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testVariantsWithNullArray() {
        Entry result = entry.variants((String[]) null);
        assertNotNull("Entry should not be null", result);
    }

    @Test
    public void testComplexEntryChaining() {
        Entry result = entry
            .includeReference("category")
            .only(new String[]{"title", "description"})
            .setLocale("en-us")
            .addParam("key", "value")
            .includeContentType()
            .includeFallback()
            .includeMetadata();
        
        assertNotNull("Entry with complex chaining should not be null", result);
        assertEquals("Should return same entry", entry, result);
    }

    @Test
    public void testMultipleHeaders() {
        entry.setHeader("header1", "value1");
        entry.setHeader("header2", "value2");
        entry.setHeader("header3", "value3");
        assertNotNull("Entry should not be null after multiple headers", entry);
    }

    @Test
    public void testMultipleIncludeReferences() {
        entry.includeReference("ref1")
             .includeReference("ref2")
             .includeReference("ref3");
        assertNotNull("Entry should not be null after multiple includes", entry);
    }

    @Test
    public void testGetJSONArray() {
        org.json.JSONArray jsonArray = entry.getJSONArray("tags");
        assertNotNull("JSONArray should not be null", jsonArray);
    }

    @Test
    public void testGetJSONObject() {
        org.json.JSONObject jsonObject = entry.getJSONObject("_metadata");
        assertNotNull("JSONObject should not be null", jsonObject);
    }

    @Test
    public void testGetJSONObjectWithNonExistentKey() {
        org.json.JSONObject jsonObject = entry.getJSONObject("non_existent");
        assertNull("JSONObject should be null for non-existent key", jsonObject);
    }

    @Test
    public void testEntryWithAllDataTypes() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("uid", "test_uid");
        json.put("string_field", "test string");
        json.put("number_field", 123);
        json.put("boolean_field", true);
        json.put("float_field", 123.45);
        
        entry.configure(json);
        
        assertEquals("String field", "test string", entry.getString("string_field"));
        assertEquals("Number field", 123, entry.getInt("number_field"));
        assertTrue("Boolean field", entry.getBoolean("boolean_field"));
        assertEquals("Float field", 123.45, entry.getDouble("float_field"), 0.01);
    }

    @Test
    public void testLocaleWithDifferentValues() {
        String[] locales = {"en-us", "fr-fr", "de-de", "es-es"};
        for (String locale : locales) {
            entry.setLocale(locale);
            assertNotNull("Entry should not be null for locale " + locale, entry);
        }
    }

    @Test
    public void testVariantsWithMultipleValues() {
        String[] variants = {"var1", "var2", "var3", "var4"};
        Entry result = entry.variants(variants);
        assertNotNull("Entry should not be null with multiple variants", result);
    }

    @Test
    public void testGetHeaders() {
        entry.setHeader("test-header", "test-value");
        android.util.ArrayMap<String, Object> headers = entry.getHeaders();
        assertNotNull("Headers should not be null", headers);
    }
}

