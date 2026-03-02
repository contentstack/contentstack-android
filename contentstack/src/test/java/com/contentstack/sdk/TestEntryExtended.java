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
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Extended tests for Entry class to maximize coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestEntryExtended {

    private Context context;
    private Stack stack;
    private ContentType contentType;
    private Entry entry;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        contentType = stack.contentType("test_content_type");
        entry = contentType.entry("test_entry_uid");
    }

    // ==================== LOCALE TESTS ====================

    @Test
    public void testSetLocale() {
        Entry result = entry.setLocale("en-US");
        assertNotNull(result);
        assertSame(entry, result);
    }

    @Test
    public void testSetLocaleWithNull() {
        Entry result = entry.setLocale(null);
        assertNotNull(result);
    }

    @Test
    public void testSetLocaleWithEmptyString() {
        Entry result = entry.setLocale("");
        assertNotNull(result);
    }

    @Test
    public void testMultipleSetLocales() {
        entry.setLocale("en-US");
        entry.setLocale("fr-FR");
        Entry result = entry.setLocale("de-DE");
        assertNotNull(result);
    }

    // ==================== INCLUDE REFERENCE TESTS ====================

    @Test
    public void testIncludeReferenceWithStringArray() {
        Entry result = entry.includeReference(new String[]{"author", "category"});
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithSingleString() {
        Entry result = entry.includeReference("author");
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithNullString() {
        String nullString = null;
        Entry result = entry.includeReference(nullString);
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithNullArray() {
        String[] nullArray = null;
        Entry result = entry.includeReference(nullArray);
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithEmptyArray() {
        Entry result = entry.includeReference(new String[]{});
        assertNotNull(result);
    }

    @Test
    public void testMultipleIncludeReferences() {
        entry.includeReference("author");
        entry.includeReference(new String[]{"category", "tags"});
        Entry result = entry.includeReference("related_posts");
        assertNotNull(result);
    }

    // ==================== ONLY/EXCEPT REFERENCE TESTS ====================

    @Test
    public void testOnlyWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        fields.add("description");
        
        Entry result = entry.onlyWithReferenceUid(fields, "author");
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithReferenceUidNullFields() {
        Entry result = entry.onlyWithReferenceUid(null, "author");
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithReferenceUidNullUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        
        Entry result = entry.onlyWithReferenceUid(fields, null);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("metadata");
        
        Entry result = entry.exceptWithReferenceUid(fields, "author");
        assertNotNull(result);
    }

    @Test
    public void testExceptWithReferenceUidNullFields() {
        Entry result = entry.exceptWithReferenceUid(null, "author");
        assertNotNull(result);
    }

    @Test
    public void testExceptWithReferenceUidNullUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("metadata");
        
        Entry result = entry.exceptWithReferenceUid(fields, null);
        assertNotNull(result);
    }

    // ==================== ONLY/EXCEPT FIELD TESTS ====================

    @Test
    public void testOnlyWithStringArray() {
        Entry result = entry.only(new String[]{"title", "description", "image"});
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithNullArray() {
        String[] nullArray = null;
        Entry result = entry.only(nullArray);
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithEmptyArray() {
        Entry result = entry.only(new String[]{});
        assertNotNull(result);
    }

    @Test
    public void testExceptWithStringArray() {
        Entry result = entry.except(new String[]{"large_field", "unused_field"});
        assertNotNull(result);
    }

    @Test
    public void testExceptWithNullArray() {
        String[] nullArray = null;
        Entry result = entry.except(nullArray);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithEmptyArray() {
        Entry result = entry.except(new String[]{});
        assertNotNull(result);
    }

    @Test
    public void testOnlyAndExceptCombination() {
        entry.only(new String[]{"field1", "field2", "field3"});
        Entry result = entry.except(new String[]{"field4"});
        assertNotNull(result);
    }

    // ==================== ADD PARAM TESTS ====================

    @Test
    public void testAddParam() {
        Entry result = entry.addParam("custom_param", "custom_value");
        assertNotNull(result);
        assertSame(entry, result);
    }

    @Test
    public void testAddParamWithNull() {
        Entry result = entry.addParam(null, "value");
        assertNotNull(result);
        
        result = entry.addParam("key", null);
        assertNotNull(result);
    }

    @Test
    public void testAddParamMultiple() {
        entry.addParam("param1", "value1");
        entry.addParam("param2", "value2");
        Entry result = entry.addParam("param3", "value3");
        assertNotNull(result);
    }

    // ==================== INCLUDE TESTS ====================

    @Test
    public void testIncludeContentType() {
        Entry result = entry.includeContentType();
        assertNotNull(result);
    }

    @Test
    public void testIncludeContentTypeMultipleTimes() {
        entry.includeContentType();
        Entry result = entry.includeContentType();
        assertNotNull(result);
    }

    @Test
    public void testIncludeFallback() {
        Entry result = entry.includeFallback();
        assertNotNull(result);
    }

    @Test
    public void testIncludeFallbackMultipleTimes() {
        entry.includeFallback();
        Entry result = entry.includeFallback();
        assertNotNull(result);
    }

    @Test
    public void testIncludeEmbeddedItems() {
        Entry result = entry.includeEmbeddedItems();
        assertNotNull(result);
    }

    @Test
    public void testIncludeEmbeddedItemsMultipleTimes() {
        entry.includeEmbeddedItems();
        Entry result = entry.includeEmbeddedItems();
        assertNotNull(result);
    }

    // ==================== CACHE POLICY TESTS ====================

    @Test
    public void testSetCachePolicyNetworkOnly() {
        entry.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(entry);
    }

    @Test
    public void testSetCachePolicyCacheOnly() {
        entry.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(entry);
    }

    @Test
    public void testSetCachePolicyCacheElseNetwork() {
        entry.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(entry);
    }

    @Test
    public void testSetCachePolicyCacheThenNetwork() {
        entry.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(entry);
    }

    @Test
    public void testSetCachePolicyNetworkElseCache() {
        entry.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(entry);
    }

    @Test
    public void testSetCachePolicyIgnoreCache() {
        entry.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(entry);
    }

    // ==================== METHOD CHAINING ====================

    @Test
    public void testMethodChaining() {
        Entry result = entry
            .setLocale("en-US")
            .includeReference("author")
            .only(new String[]{"title", "description"})
            .addParam("custom", "value")
            .includeContentType()
            .includeFallback();
        
        assertNotNull(result);
        assertSame(entry, result);
    }

    @Test
    public void testComplexChaining() {
        ArrayList<String> onlyFields = new ArrayList<>();
        onlyFields.add("title");
        onlyFields.add("body");
        
        ArrayList<String> exceptFields = new ArrayList<>();
        exceptFields.add("metadata");
        
        Entry result = entry
            .setLocale("en-US")
            .includeReference(new String[]{"author", "category"})
            .onlyWithReferenceUid(onlyFields, "author")
            .exceptWithReferenceUid(exceptFields, "category")
            .only(new String[]{"title", "body", "image"})
            .except(new String[]{"internal_notes"})
            .addParam("include_dimension", "true")
            .includeContentType()
            .includeFallback()
            .includeEmbeddedItems();
        
        assertNotNull(result);
    }

    // ==================== GET UID ====================

    @Test
    public void testGetUid() {
        String uid = entry.getUid();
        assertNotNull(uid);
        assertEquals("test_entry_uid", uid);
    }

    // ==================== EDGE CASES ====================

    @Test
    public void testEntryWithAllNulls() {
        entry.setLocale(null);
        entry.includeReference((String)null);
        entry.only(null);
        entry.except(null);
        entry.addParam(null, null);
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithEmptyStrings() {
        entry.setLocale("");
        entry.includeReference("");
        entry.addParam("", "");
        assertNotNull(entry);
    }

    @Test
    public void testReuseEntryAfterConfiguration() {
        entry.setLocale("en-US").only(new String[]{"field1"});
        entry.setLocale("fr-FR").only(new String[]{"field2"});
        Entry result = entry.setLocale("de-DE").only(new String[]{"field3"});
        assertNotNull(result);
    }

    // ==================== INCLUDE REFERENCE VARIATIONS ====================

    @Test
    public void testIncludeReferenceWithManyFields() {
        Entry result = entry.includeReference(new String[]{
            "ref1", "ref2", "ref3", "ref4", "ref5",
            "ref6", "ref7", "ref8", "ref9", "ref10"
        });
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceChained() {
        Entry result = entry
            .includeReference("author")
            .includeReference("category")
            .includeReference("tags")
            .includeReference("related_content")
            .includeReference("comments");
        
        assertNotNull(result);
    }

    // ==================== ONLY/EXCEPT VARIATIONS ====================

    @Test
    public void testOnlyWithManyFields() {
        String[] fields = new String[20];
        for (int i = 0; i < 20; i++) {
            fields[i] = "field" + i;
        }
        Entry result = entry.only(fields);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithManyFields() {
        String[] fields = new String[15];
        for (int i = 0; i < 15; i++) {
            fields[i] = "exclude_field" + i;
        }
        Entry result = entry.except(fields);
        assertNotNull(result);
    }

    // ==================== COMPLEX SCENARIOS ====================

    @Test
    public void testCompleteEntryConfiguration() {
        entry
            .setLocale("en-US")
            .includeReference(new String[]{"author", "category", "tags"})
            .only(new String[]{"title", "description", "image", "url"})
            .except(new String[]{"metadata", "internal_data"})
            .addParam("include_dimension", "true")
            .addParam("include_fallback", "true")
            .addParam("version", "2")
            .includeContentType()
            .includeFallback()
            .includeEmbeddedItems();
        
        entry.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertNotNull(entry);
        assertEquals("test_entry_uid", entry.getUid());
    }

    @Test
    public void testReconfigureEntry() {
        entry.setLocale("en-US").only(new String[]{"title"});
        entry.setLocale("fr-FR").except(new String[]{"metadata"});
        Entry result = entry.setLocale("es-ES").includeReference("author");
        assertNotNull(result);
    }

    @Test
    public void testEntryWithAllFeatures() {
        ArrayList<String> onlyRefs = new ArrayList<>();
        onlyRefs.add("name");
        onlyRefs.add("email");
        
        ArrayList<String> exceptRefs = new ArrayList<>();
        exceptRefs.add("password");
        
        Entry result = entry
            .setLocale("en-US")
            .includeReference(new String[]{"author", "category"})
            .onlyWithReferenceUid(onlyRefs, "author")
            .exceptWithReferenceUid(exceptRefs, "author")
            .only(new String[]{"title", "body"})
            .except(new String[]{"draft_notes"})
            .addParam("p1", "v1")
            .addParam("p2", "v2")
            .includeContentType()
            .includeFallback()
            .includeEmbeddedItems();
        
        assertNotNull(result);
    }

    // ==================== SPECIAL CHARACTERS ====================

    @Test
    public void testEntryWithSpecialCharacters() {
        entry.addParam("special_chars", "value & <test> \"quotes\"");
        entry.setLocale("zh-CN");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithUnicodeLocale() {
        entry.setLocale("日本語");
        assertNotNull(entry);
    }

    // ==================== MULTIPLE CONFIGURATIONS ====================

    @Test
    public void testMultipleAddParams() {
        for (int i = 0; i < 10; i++) {
            entry.addParam("param" + i, "value" + i);
        }
        assertNotNull(entry);
    }

    @Test
    public void testMultipleOnlyOperations() {
        entry.only(new String[]{"field1", "field2"});
        entry.only(new String[]{"field3", "field4"});
        Entry result = entry.only(new String[]{"field5"});
        assertNotNull(result);
    }

    @Test
    public void testMultipleExceptOperations() {
        entry.except(new String[]{"field1"});
        entry.except(new String[]{"field2", "field3"});
        Entry result = entry.except(new String[]{"field4"});
        assertNotNull(result);
    }
}

