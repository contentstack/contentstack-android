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
 * Comprehensive unit tests for EntryModel class
 * Based on Java SDK test patterns
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class TestEntryModel {

    private JSONObject testEntryJson;
    private JSONObject testEntriesJson;

    @Before
    public void setUp() throws Exception {
        // Create test entry JSON
        testEntryJson = new JSONObject();
        JSONObject entryData = new JSONObject();
        entryData.put("uid", "entry123");
        entryData.put("title", "Test Entry");
        entryData.put("url", "/test-entry");
        entryData.put("locale", "en-us");
        
        // Add tags
        JSONArray tags = new JSONArray();
        tags.put("tag1");
        tags.put("tag2");
        entryData.put("tags", tags);
        
        // Add metadata
        JSONObject metadata = new JSONObject();
        metadata.put("uid", "entry123");
        metadata.put("content_type_uid", "blog_post");
        entryData.put("_metadata", metadata);
        
        // Add owner
        JSONObject owner = new JSONObject();
        owner.put("uid", "owner123");
        owner.put("email", "owner@example.com");
        entryData.put("_owner", owner);
        
        testEntryJson.put("entry", entryData);
    }

    // ==================== BASIC CONSTRUCTOR TESTS ====================

    @Test
    public void testEntryModelBasicConstructor() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertEquals("entry123", model.entryUid);
        assertEquals("Test Entry", model.title);
        assertEquals("/test-entry", model.url);
        assertEquals("en-us", model.language);
    }

    @Test
    public void testEntryModelFromCache() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testEntryJson);
        
        EntryModel model = new EntryModel(cacheJson, "entry123", false, true, false);
        assertNotNull("EntryModel should not be null", model);
    }

    @Test
    public void testEntryModelFromObjectsModel() throws Exception {
        JSONObject directJson = new JSONObject();
        directJson.put("uid", "direct123");
        directJson.put("title", "Direct Entry");
        directJson.put("url", "/direct");
        directJson.put("locale", "en-us");
        
        EntryModel model = new EntryModel(directJson, "direct123", true, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertEquals("direct123", model.entryUid);
        assertEquals("Direct Entry", model.title);
    }

    @Test
    public void testEntryModelFromDeltaResponse() throws Exception {
        JSONObject deltaJson = new JSONObject();
        deltaJson.put("uid", "delta123");
        deltaJson.put("title", "Delta Entry");
        deltaJson.put("url", "/delta");
        
        EntryModel model = new EntryModel(deltaJson, "delta123", false, false, true);
        assertNotNull("EntryModel should not be null", model);
    }

    // ==================== TAGS TESTS ====================

    @Test
    public void testEntryModelWithTags() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("Tags should not be null", model.tags);
        assertEquals(2, model.tags.length);
        assertEquals("tag1", model.tags[0]);
        assertEquals("tag2", model.tags[1]);
    }

    @Test
    public void testEntryModelWithEmptyTags() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "entry_no_tags");
        entry.put("tags", new JSONArray());
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "entry_no_tags", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        // Empty tags array should result in null tags
    }

    @Test
    public void testEntryModelWithoutTags() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "entry_no_tags");
        entry.put("title", "No Tags Entry");
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "entry_no_tags", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertNull("Tags should be null", model.tags);
    }

    // ==================== METADATA TESTS ====================

    @Test
    public void testEntryModelWithMetadata() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("Metadata should not be null", model._metadata);
        assertTrue(model._metadata.containsKey("uid"));
        assertEquals("entry123", model._metadata.get("uid"));
    }

    @Test
    public void testEntryModelWithPublishDetails() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "published_entry");
        
        JSONObject publishDetails = new JSONObject();
        publishDetails.put("environment", "production");
        publishDetails.put("time", "2024-01-01T00:00:00.000Z");
        entry.put("publish_details", publishDetails);
        
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "published_entry", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertNotNull("Metadata should not be null", model._metadata);
        assertTrue(model._metadata.containsKey("publish_details"));
    }

    @Test
    public void testEntryModelWithoutMetadata() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "no_metadata");
        entry.put("title", "No Metadata Entry");
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "no_metadata", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertNull("Metadata should be null", model._metadata);
    }

    // ==================== OWNER TESTS ====================

    @Test
    public void testEntryModelWithOwner() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("Owner map should not be null", model.ownerMap);
        assertEquals("owner123", model.ownerUid);
        assertEquals("owner@example.com", model.ownerEmailId);
    }

    @Test
    public void testEntryModelWithoutOwner() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "no_owner");
        entry.put("title", "No Owner Entry");
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "no_owner", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertNull("Owner map should be null", model.ownerMap);
        assertNull("Owner UID should be null", model.ownerUid);
        assertNull("Owner email should be null", model.ownerEmailId);
    }

    @Test
    public void testEntryModelWithOwnerNoEmail() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "entry_no_email");
        
        JSONObject owner = new JSONObject();
        owner.put("uid", "owner_no_email");
        entry.put("_owner", owner);
        
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "entry_no_email", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertEquals("owner_no_email", model.ownerUid);
        assertNull("Owner email should be null", model.ownerEmailId);
    }

    @Test
    public void testEntryModelWithNullOwner() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "null_owner");
        entry.put("_owner", JSONObject.NULL);
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "null_owner", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertNull("Owner map should be null", model.ownerMap);
    }

    // ==================== FIELD PRESENCE TESTS ====================

    @Test
    public void testEntryModelWithAllFields() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "complete_entry");
        entry.put("title", "Complete Entry");
        entry.put("url", "/complete");
        entry.put("locale", "fr-fr");
        
        JSONArray tags = new JSONArray();
        tags.put("tag1");
        tags.put("tag2");
        tags.put("tag3");
        entry.put("tags", tags);
        
        JSONObject metadata = new JSONObject();
        metadata.put("uid", "complete_entry");
        entry.put("_metadata", metadata);
        
        JSONObject owner = new JSONObject();
        owner.put("uid", "owner_complete");
        owner.put("email", "complete@example.com");
        entry.put("_owner", owner);
        
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "complete_entry", false, false, false);
        
        assertNotNull("EntryModel should not be null", model);
        assertEquals("complete_entry", model.entryUid);
        assertEquals("Complete Entry", model.title);
        assertEquals("/complete", model.url);
        assertEquals("fr-fr", model.language);
        assertNotNull(model.tags);
        assertEquals(3, model.tags.length);
        assertNotNull(model._metadata);
        assertNotNull(model.ownerMap);
        assertEquals("owner_complete", model.ownerUid);
        assertEquals("complete@example.com", model.ownerEmailId);
    }

    @Test
    public void testEntryModelWithMinimalFields() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "minimal");
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "minimal", false, false, false);
        
        assertNotNull("EntryModel should not be null", model);
        assertEquals("minimal", model.entryUid);
        assertNull(model.tags);
        assertNull(model._metadata);
        assertNull(model.ownerMap);
    }

    // ==================== FLAGS COMBINATION TESTS ====================

    @Test
    public void testEntryModelAllFlagsCombination1() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("EntryModel should not be null", model);
    }

    @Test
    public void testEntryModelAllFlagsCombination2() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", true, false, false);
        assertNotNull("EntryModel should not be null", model);
    }

    @Test
    public void testEntryModelAllFlagsCombination3() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testEntryJson);
        
        EntryModel model = new EntryModel(cacheJson, "entry123", false, true, false);
        assertNotNull("EntryModel should not be null", model);
    }

    @Test
    public void testEntryModelAllFlagsCombination4() throws Exception {
        JSONObject deltaJson = new JSONObject();
        deltaJson.put("uid", "delta_entry");
        deltaJson.put("title", "Delta Entry");
        
        EntryModel model = new EntryModel(deltaJson, "delta_entry", false, false, true);
        assertNotNull("EntryModel should not be null", model);
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEntryModelWithNullUid() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", JSONObject.NULL);
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, null, false, false, false);
        assertNotNull("EntryModel should not be null", model);
    }

    @Test
    public void testEntryModelWithSpecialCharacters() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject entry = new JSONObject();
        entry.put("uid", "special_entry");
        entry.put("title", "Entry with special chars: äöü ñ 中文 日本語");
        entry.put("url", "/entry-with-special-chars");
        json.put("entry", entry);
        
        EntryModel model = new EntryModel(json, "special_entry", false, false, false);
        assertNotNull("EntryModel should not be null", model);
        assertEquals("Entry with special chars: äöü ñ 中文 日本語", model.title);
    }

    // ==================== JSON OBJECT FIELD TESTS ====================

    @Test
    public void testEntryModelJsonObjectField() {
        EntryModel model = new EntryModel(testEntryJson, "entry123", false, false, false);
        assertNotNull("JSON object should not be null", model.jsonObject);
        assertTrue(model.jsonObject.has("uid"));
    }

    @Test
    public void testEntryModelJsonObjectFieldWithCache() throws Exception {
        JSONObject cacheJson = new JSONObject();
        cacheJson.put("response", testEntryJson);
        
        EntryModel model = new EntryModel(cacheJson, "entry123", false, true, false);
        assertNotNull("EntryModel should not be null", model);
        // jsonObject should be set from cache response
    }
}

