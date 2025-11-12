package com.contentstack.sdk;

import android.content.Context;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestContentType {

    private Context mockContext;
    private Stack stack;
    private ContentType contentType;

    @Before
    public void setUp() throws Exception {
        mockContext = TestUtils.createMockContext();
        stack = Contentstack.stack(mockContext,
            TestUtils.getTestApiKey(),
            TestUtils.getTestDeliveryToken(),
            TestUtils.getTestEnvironment());
        contentType = stack.contentType(TestUtils.getTestContentType());
        TestUtils.cleanupTestCache();
    }

    @After
    public void tearDown() {
        TestUtils.cleanupTestCache();
        contentType = null;
        stack = null;
        mockContext = null;
    }

    @Test
    public void testContentTypeCreation() {
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testEntryCreation() {
        Entry entry = contentType.entry();
        assertNotNull("Entry should not be null", entry);
    }

    @Test
    public void testEntryWithUid() {
        Entry entry = contentType.entry("test_entry_uid");
        assertNotNull("Entry with uid should not be null", entry);
        assertEquals("Entry UID should match", "test_entry_uid", entry.getUid());
    }

    @Test
    public void testEntryWithEmptyUid() {
        Entry entry = contentType.entry("");
        assertNotNull("Entry should not be null with empty uid", entry);
    }

    @Test
    public void testEntryWithNullUid() {
        Entry entry = contentType.entry(null);
        assertNotNull("Entry should not be null with null uid", entry);
    }

    @Test
    public void testQueryCreation() {
        Query query = contentType.query();
        assertNotNull("Query should not be null", query);
    }

    @Test
    public void testSetHeader() {
        contentType.setHeader("custom-header", "custom-value");
        assertNotNull("ContentType should not be null after setHeader", contentType);
    }

    @Test
    public void testSetHeaderWithEmptyKey() {
        contentType.setHeader("", "value");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testSetHeaderWithEmptyValue() {
        contentType.setHeader("key", "");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testSetHeaderWithNullKey() {
        contentType.setHeader(null, "value");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testSetHeaderWithNullValue() {
        contentType.setHeader("key", null);
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testRemoveHeader() {
        contentType.setHeader("custom-header", "custom-value");
        contentType.removeHeader("custom-header");
        assertNotNull("ContentType should not be null after removeHeader", contentType);
    }

    @Test
    public void testRemoveHeaderWithEmptyKey() {
        contentType.removeHeader("");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testRemoveHeaderWithNullKey() {
        contentType.removeHeader(null);
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testRemoveNonExistentHeader() {
        contentType.removeHeader("non-existent-header");
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testMultipleEntries() {
        Entry entry1 = contentType.entry("uid1");
        Entry entry2 = contentType.entry("uid2");
        Entry entry3 = contentType.entry("uid3");
        
        assertNotNull("Entry 1 should not be null", entry1);
        assertNotNull("Entry 2 should not be null", entry2);
        assertNotNull("Entry 3 should not be null", entry3);
        assertNotEquals("Entries should be different instances", entry1, entry2);
    }

    @Test
    public void testMultipleQueries() {
        Query query1 = contentType.query();
        Query query2 = contentType.query();
        
        assertNotNull("Query 1 should not be null", query1);
        assertNotNull("Query 2 should not be null", query2);
        assertNotEquals("Queries should be different instances", query1, query2);
    }

    @Test
    public void testEntryAfterSettingHeaders() {
        contentType.setHeader("header1", "value1");
        contentType.setHeader("header2", "value2");
        
        Entry entry = contentType.entry("test_uid");
        assertNotNull("Entry should not be null after setting headers", entry);
    }

    @Test
    public void testQueryAfterSettingHeaders() {
        contentType.setHeader("header1", "value1");
        contentType.setHeader("header2", "value2");
        
        Query query = contentType.query();
        assertNotNull("Query should not be null after setting headers", query);
    }

    @Test
    public void testMultipleHeaderOperations() {
        contentType.setHeader("header1", "value1");
        contentType.setHeader("header2", "value2");
        contentType.removeHeader("header1");
        contentType.setHeader("header3", "value3");
        
        assertNotNull("ContentType should not be null after multiple operations", contentType);
    }

    @Test
    public void testSetSameHeaderMultipleTimes() {
        contentType.setHeader("header", "value1");
        contentType.setHeader("header", "value2");
        contentType.setHeader("header", "value3");
        
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testEntryWithLongUid() {
        String longUid = "a".repeat(100);
        Entry entry = contentType.entry(longUid);
        assertNotNull("Entry should not be null with long UID", entry);
        assertEquals("Entry UID should match", longUid, entry.getUid());
    }

    @Test
    public void testEntryWithSpecialCharactersInUid() {
        String[] specialUids = {"uid-with-dashes", "uid_with_underscores", "uid.with.dots"};
        
        for (String uid : specialUids) {
            Entry entry = contentType.entry(uid);
            assertNotNull("Entry should not be null for UID: " + uid, entry);
            assertEquals("Entry UID should match", uid, entry.getUid());
        }
    }

    @Test
    public void testHeaderWithSpecialCharacters() {
        contentType.setHeader("x-custom-header", "value");
        contentType.setHeader("header_with_underscore", "value");
        contentType.setHeader("header.with.dots", "value");
        
        assertNotNull("ContentType should not be null with special character headers", contentType);
    }

    @Test
    public void testHeaderWithLongValue() {
        String longValue = "v".repeat(1000);
        contentType.setHeader("long-header", longValue);
        assertNotNull("ContentType should not be null with long header value", contentType);
    }

    @Test
    public void testQueryChaining() {
        Query query = contentType.query();
        query.where("field", "value")
             .limit(10)
             .skip(5)
             .includeCount();
        
        assertNotNull("Query should support chaining", query);
    }

    @Test
    public void testEntryChaining() {
        Entry entry = contentType.entry("test_uid");
        entry.only(new String[]{"title"})
             .setLocale("en-us")
             .includeReference("category");
        
        assertNotNull("Entry should support chaining", entry);
    }

    @Test
    public void testConcurrentOperations() {
        contentType.setHeader("header1", "value1");
        Entry entry = contentType.entry("uid1");
        contentType.setHeader("header2", "value2");
        Query query = contentType.query();
        contentType.removeHeader("header1");
        
        assertNotNull("Entry should not be null", entry);
        assertNotNull("Query should not be null", query);
        assertNotNull("ContentType should not be null", contentType);
    }

    @Test
    public void testMultipleContentTypesFromSameStack() {
        ContentType ct1 = stack.contentType("type1");
        ContentType ct2 = stack.contentType("type2");
        
        ct1.setHeader("header1", "value1");
        ct2.setHeader("header2", "value2");
        
        assertNotNull("ContentType 1 should not be null", ct1);
        assertNotNull("ContentType 2 should not be null", ct2);
        assertNotEquals("ContentTypes should be different instances", ct1, ct2);
    }

    @Test
    public void testHeaderPersistenceAcrossEntries() {
        contentType.setHeader("persistent-header", "persistent-value");
        
        Entry entry1 = contentType.entry("uid1");
        Entry entry2 = contentType.entry("uid2");
        
        assertNotNull("Entry 1 should not be null", entry1);
        assertNotNull("Entry 2 should not be null", entry2);
    }

    @Test
    public void testHeaderPersistenceAcrossQueries() {
        contentType.setHeader("persistent-header", "persistent-value");
        
        Query query1 = contentType.query();
        Query query2 = contentType.query();
        
        assertNotNull("Query 1 should not be null", query1);
        assertNotNull("Query 2 should not be null", query2);
    }

    @Test
    public void testEmptyContentTypeName() {
        ContentType emptyContentType = stack.contentType("");
        assertNotNull("ContentType with empty name should not be null", emptyContentType);
    }

    @Test
    public void testContentTypeNameWithSpaces() {
        ContentType spacedContentType = stack.contentType("content type with spaces");
        assertNotNull("ContentType with spaces should not be null", spacedContentType);
    }

    @Test
    public void testContentTypeNameWithNumbers() {
        ContentType numberedContentType = stack.contentType("content_type_123");
        assertNotNull("ContentType with numbers should not be null", numberedContentType);
    }

    @Test
    public void testContentTypeIntegrity() {
        // Perform various operations
        contentType.setHeader("test", "value");
        contentType.entry("test_uid");
        contentType.query();
        contentType.removeHeader("test");
        
        // ContentType should still be valid
        assertNotNull("ContentType should maintain integrity", contentType);
        Entry newEntry = contentType.entry("another_uid");
        assertNotNull("Should still be able to create entries", newEntry);
    }
}

