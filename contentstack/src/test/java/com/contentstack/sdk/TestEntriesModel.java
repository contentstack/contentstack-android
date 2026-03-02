package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for EntriesModel class
 * Based on Java SDK test patterns
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class TestEntriesModel {

    private JSONObject testEntriesJson;

    @Before
    public void setUp() throws Exception {
        testEntriesJson = new JSONObject();
        
        JSONArray entriesArray = new JSONArray();
        for (int i = 1; i <= 5; i++) {
            JSONObject entry = new JSONObject();
            entry.put("uid", "entry_" + i);
            entry.put("title", "Entry " + i);
            entry.put("url", "/entry-" + i);
            entry.put("locale", "en-us");
            entriesArray.put(entry);
        }
        
        testEntriesJson.put("entries", entriesArray);
    }

    // ==================== BASIC CONSTRUCTOR TESTS ====================

    @Test
    public void testEntriesModelConstructor() {
        EntriesModel model = new EntriesModel(testEntriesJson, "entries", false);
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(5, model.objectList.size());
    }

    @Test
    public void testEntriesModelFromCache() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testEntriesJson);
        
        EntriesModel model = new EntriesModel(cacheJson, "entries", true);
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
    }

    @Test
    public void testEntriesModelNotFromCache() {
        EntriesModel model = new EntriesModel(testEntriesJson, "entries", false);
        assertNotNull("EntriesModel should not be null", model);
        assertEquals("entries", model.formName);
    }

    // ==================== ENTRY PARSING TESTS ====================

    @Test
    public void testEntriesModelParsesEntries() {
        EntriesModel model = new EntriesModel(testEntriesJson, "entries", false);
        
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(5, model.objectList.size());
        
        // Verify first entry
        EntryModel firstEntry = (EntryModel) model.objectList.get(0);
        assertEquals("entry_1", firstEntry.entryUid);
        assertEquals("Entry 1", firstEntry.title);
        assertEquals("/entry-1", firstEntry.url);
    }

    @Test
    public void testEntriesModelWithSingleEntry() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray entriesArray = new JSONArray();
        
        JSONObject entry = new JSONObject();
        entry.put("uid", "single_entry");
        entry.put("title", "Single Entry");
        entriesArray.put(entry);
        
        json.put("entries", entriesArray);
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertEquals(1, model.objectList.size());
        
        EntryModel entryModel = (EntryModel) model.objectList.get(0);
        assertEquals("single_entry", entryModel.entryUid);
    }

    @Test
    public void testEntriesModelWithEmptyArray() throws Exception {
        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(0, model.objectList.size());
    }

    @Test
    public void testEntriesModelWithNullEntries() {
        JSONObject json = new JSONObject();
        // No "entries" field
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(0, model.objectList.size());
    }

    // ==================== FORM NAME TESTS ====================

    @Test
    public void testEntriesModelFormName() {
        EntriesModel model = new EntriesModel(testEntriesJson, "entries", false);
        assertEquals("entries", model.formName);
    }

    @Test
    public void testEntriesModelWithCustomFormName() {
        EntriesModel model = new EntriesModel(testEntriesJson, "custom_entries", false);
        assertEquals("custom_entries", model.formName);
    }

    @Test
    public void testEntriesModelWithNullFormName() {
        EntriesModel model = new EntriesModel(testEntriesJson, null, false);
        assertNotNull("EntriesModel should not be null", model);
        assertNull(model.formName);
    }

    // ==================== COMPLEX DATA TESTS ====================

    @Test
    public void testEntriesModelWithComplexEntries() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray entriesArray = new JSONArray();
        
        for (int i = 1; i <= 3; i++) {
            JSONObject entry = new JSONObject();
            entry.put("uid", "complex_entry_" + i);
            entry.put("title", "Complex Entry " + i);
            entry.put("url", "/complex-" + i);
            entry.put("locale", "en-us");
            
            // Add tags
            JSONArray tags = new JSONArray();
            tags.put("tag1");
            tags.put("tag2");
            entry.put("tags", tags);
            
            // Add metadata
            JSONObject metadata = new JSONObject();
            metadata.put("uid", "complex_entry_" + i);
            entry.put("_metadata", metadata);
            
            entriesArray.put(entry);
        }
        
        json.put("entries", entriesArray);
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertEquals(3, model.objectList.size());
        
        // Verify entries were parsed with complex data
        for (int i = 0; i < 3; i++) {
            EntryModel entry = (EntryModel) model.objectList.get(i);
            assertNotNull(entry);
            assertNotNull(entry.entryUid);
            assertNotNull(entry.title);
        }
    }

    @Test
    public void testEntriesModelWithMixedValidAndInvalidEntries() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray entriesArray = new JSONArray();
        
        // Add valid entry
        JSONObject validEntry = new JSONObject();
        validEntry.put("uid", "valid_entry");
        validEntry.put("title", "Valid Entry");
        entriesArray.put(validEntry);
        
        // Add invalid entry (string instead of JSONObject)
        entriesArray.put("invalid_entry");
        
        // Add another valid entry
        JSONObject validEntry2 = new JSONObject();
        validEntry2.put("uid", "valid_entry2");
        validEntry2.put("title", "Valid Entry 2");
        entriesArray.put(validEntry2);
        
        json.put("entries", entriesArray);
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        // Should only process valid JSONObject entries
        assertEquals(2, model.objectList.size());
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEntriesModelWithEmptyJson() {
        JSONObject emptyJson = new JSONObject();
        EntriesModel model = new EntriesModel(emptyJson, "entries", false);
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(0, model.objectList.size());
    }

    @Test
    public void testEntriesModelWithLargeArray() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray largeArray = new JSONArray();
        
        for (int i = 0; i < 100; i++) {
            JSONObject entry = new JSONObject();
            entry.put("uid", "entry_" + i);
            entry.put("title", "Entry " + i);
            largeArray.put(entry);
        }
        
        json.put("entries", largeArray);
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertEquals(100, model.objectList.size());
    }

    @Test
    public void testEntriesModelWithSpecialCharacters() throws Exception {
        JSONObject json = new JSONObject();
        JSONArray entriesArray = new JSONArray();
        
        JSONObject entry = new JSONObject();
        entry.put("uid", "special_entry");
        entry.put("title", "Entry with special chars: äöü ñ 中文 日本語");
        entry.put("url", "/entry-special");
        entriesArray.put(entry);
        
        json.put("entries", entriesArray);
        
        EntriesModel model = new EntriesModel(json, "entries", false);
        
        assertNotNull("EntriesModel should not be null", model);
        assertEquals(1, model.objectList.size());
        
        EntryModel entryModel = (EntryModel) model.objectList.get(0);
        assertEquals("Entry with special chars: äöü ñ 中文 日本語", entryModel.title);
    }

    // ==================== JSON OBJECT FIELD TESTS ====================

    @Test
    public void testEntriesModelJsonObjectField() {
        EntriesModel model = new EntriesModel(testEntriesJson, "entries", false);
        assertNotNull("JSON object should not be null", model.jsonObject);
        assertTrue(model.jsonObject.has("entries"));
    }

    @Test
    public void testEntriesModelJsonObjectFieldWithCache() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testEntriesJson);
        
        EntriesModel model = new EntriesModel(cacheJson, "entries", true);
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("JSON object should not be null", model.jsonObject);
    }

    // ==================== COMBINED SCENARIOS ====================

    @Test
    public void testEntriesModelFromCacheWithComplexData() throws Exception {
        JSONObject cacheJson = new JSONObject();
        JSONObject response = new JSONObject();
        JSONArray entriesArray = new JSONArray();
        
        for (int i = 1; i <= 10; i++) {
            JSONObject entry = new JSONObject();
            entry.put("uid", "cached_entry_" + i);
            entry.put("title", "Cached Entry " + i);
            entry.put("url", "/cached-" + i);
            entriesArray.put(entry);
        }
        
        response.put("entries", entriesArray);
        cacheJson.put("response", response);
        
        EntriesModel model = new EntriesModel(cacheJson, "entries", true);
        
        assertNotNull("EntriesModel should not be null", model);
        assertNotNull("Object list should not be null", model.objectList);
        assertEquals(10, model.objectList.size());
    }
}

