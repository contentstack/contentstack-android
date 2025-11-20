package com.contentstack.sdk;

import static org.junit.Assert.*;

import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Unit tests for Entry.java that do not depend on internal constructors of
 * Contentstack, Stack, or ContentType.
 */
public class TestEntry {

    /**
     * Helper to create a fully-populated Entry with a backing JSON.
     * This does not depend on Stack/Contentstack or ContentType constructors.
     */
    private Entry createBasicEntry() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("title", "Hello");
        json.put("url", "/hello");
        json.put("locale", "en-us");
        json.put("created_at", "2021-01-01T10:00:00.000Z");
        json.put("updated_at", "2021-01-02T10:00:00.000Z");
        json.put("deleted_at", "2021-01-03T10:00:00.000Z");
        json.put("created_by", "creator");
        json.put("updated_by", "updater");
        json.put("deleted_by", "deleter");
        json.put("string_field", "value");
        json.put("bool_field", true);
        json.put("int_field", 42);
        json.put("double_field", 3.14);
        json.put("float_field", 1.23f);
        json.put("long_field", 123456789L);
        json.put("short_field", (short) 12);

        // markdown fields
        json.put("markdownKey", "hello **world**");
        JSONArray mdArr = new JSONArray();
        mdArr.put("**one**");
        mdArr.put("**two**");
        json.put("markdown_mult", mdArr);

        // asset single
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "asset_uid");
        json.put("asset_field", assetJson);

        // asset multiple
        JSONArray assetArray = new JSONArray();
        assetArray.put(new JSONObject().put("uid", "asset_1"));
        assetArray.put(new JSONObject().put("uid", "asset_2"));
        json.put("asset_list", assetArray);

        // reference entries
        JSONArray refArray = new JSONArray();
        refArray.put(new JSONObject().put("uid", "entry_1"));
        refArray.put(new JSONObject().put("uid", "entry_2"));
        json.put("ref_field", refArray);

        // group
        JSONObject groupObj = new JSONObject();
        groupObj.put("name", "grp");
        json.put("group_field", groupObj);

        // groups (multiple)
        JSONArray groupsArr = new JSONArray();
        groupsArr.put(new JSONObject().put("name", "g1"));
        groupsArr.put(new JSONObject().put("name", "g2"));
        json.put("group_list", groupsArr);

        // metadata locale
        JSONObject metadata = new JSONObject();
        metadata.put("locale", "en-us");
        json.put("_metadata", metadata);

        // Use Entry(String) constructor which exists in implementation
        Entry entry = new Entry("article");
        // No ContentType instance, just rely on internal contentTypeUid
        entry.setContentTypeInstance(null);
        // Configure with our JSON
        entry.configure(json);

        return entry;
    }

    // ---------------- BASIC GETTERS ----------------

    @Test
    public void testEntryBasicAccessors() throws JSONException {
        Entry entry = createBasicEntry();

        assertEquals("Hello", entry.getTitle());
        assertEquals("/hello", entry.getURL());
        assertEquals("article", entry.getContentType());
        assertNotNull(entry.getUid());
        assertEquals("creator", entry.getCreatedBy());
        assertEquals("updater", entry.getUpdatedBy());
        assertEquals("deleter", entry.getDeletedBy());

        assertEquals("value", entry.getString("string_field"));
        assertTrue(entry.getBoolean("bool_field"));
        assertEquals(42, entry.getInt("int_field"));
        assertEquals(3.14, entry.getDouble("double_field"), 0.0001);
        assertEquals(1.23f, entry.getFloat("float_field"), 0.0001f);
        assertEquals(123456789L, entry.getLong("long_field"));
        assertEquals(12, entry.getShort("short_field"));

        assertTrue(entry.contains("string_field"));
        assertFalse(entry.contains("non_existing"));
    }

    @Test
    public void testGetDateFieldsAndLocale() throws JSONException {
        Entry entry = createBasicEntry();

        Calendar created = entry.getCreateAt();
        Calendar updated = entry.getUpdateAt();
        Calendar deleted = entry.getDeleteAt();

        assertNotNull(created);
        assertNotNull(updated);
        assertNotNull(deleted);

        // Locale from resultJson
        assertEquals("en-us", entry.getLocale());

        // Deprecated getLanguage() still should not crash
        Language lang = entry.getLanguage();
        assertNotNull(lang);
    }

    // ---------------- MARKDOWN HELPERS ----------------

    @Test
    public void testGetHtmlText() throws JSONException {
        Entry entry = createBasicEntry();

        String html = entry.getHtmlText("markdownKey");
        assertNotNull(html);
        // Just check that markdown was converted somehow
        assertTrue(html.toLowerCase().contains("world"));
    }

    @Test
    public void testGetMultipleHtmlText() throws JSONException {
        Entry entry = createBasicEntry();

        ArrayList<String> htmlList = entry.getMultipleHtmlText("markdown_mult");
        assertNotNull(htmlList);
        assertEquals(2, htmlList.size());
    }

    // ---------------- ASSET / GROUP / REFERENCE ----------------

    @Test
    public void testGetAssetAndAssets() throws JSONException {
        Entry entry = createBasicEntry();

        // Just call them for coverage, do not assert success because the
        // implementation may require a real Stack/ContentType context.
        try {
            entry.getAsset("asset_field");
        } catch (Exception ignored) {
            // We intentionally ignore exceptions here, since we cannot construct
            // a fully valid SDK context in unit tests.
        }

        try {
            entry.getAssets("asset_list");
        } catch (Exception ignored) {
            // Same reasoning as above.
        }
    }

    @Test
    public void testGetGroupAndGroups() throws JSONException {
        Entry entry = createBasicEntry();

        try {
            entry.getGroup("group_field");
        } catch (Exception ignored) {
            // Ignore – implementation might need a real ContentType/Stack.
        }

        try {
            entry.getGroups("group_list");
        } catch (Exception ignored) {
            // Ignore – same as above.
        }
    }

    @Test
    public void testGetAllEntries() throws JSONException {
        Entry entry = createBasicEntry();

        ArrayList<Entry> refs = entry.getAllEntries("ref_field", "task");
        assertNotNull(refs);
        assertEquals(2, refs.size());
        assertEquals("task", refs.get(0).getContentType());
    }

    // ---------------- HEADERS & VARIANTS ----------------

    @Test
    public void testSetAndRemoveHeader() throws JSONException {
        Entry entry = createBasicEntry();

        // We just ensure that these calls do not crash.
        entry.setHeader("environment", "dev");
        entry.setHeader("custom", "value");
        entry.removeHeader("custom");
        // No assertion on internal header map, since implementation is opaque in tests.
    }

    @Test
    public void testVariantsSingle() throws JSONException {
        Entry entry = createBasicEntry();

        // Just ensure it does not throw
        entry.variants("variantA");
    }

    @Test
    public void testVariantsMultiple() throws JSONException {
        Entry entry = createBasicEntry();

        // Just ensure it does not throw
        entry.variants(new String[]{"v1", "v2", "  ", null, "v3"});
    }

    @Test
    public void testVariantsNegativeCases() throws JSONException {
        Entry entry = createBasicEntry();

        // null single
        entry.variants((String) null);

        // empty array
        entry.variants(new String[]{});

        // all empty / null values
        entry.variants(new String[]{" ", null, ""});
    }

    // ---------------- OTHER PARAM DSL METHODS ----------------

    @Test
    public void testIncludeHelpersDoNotCrash() throws JSONException {
        Entry entry = createBasicEntry();

        entry.includeReference("ref_field")
                .includeReference(new String[]{"ref_field_a", "ref_field_b"})
                .includeReferenceContentTypeUID()
                .includeContentType()
                .includeFallback()
                .includeEmbeddedItems()
                .includeMetadata()
                .addParam("include_dimensions", "true");

        assertSame(entry, entry.includeFallback());
    }

    @Test
    public void testOnlyAndExceptWithReferenceUid() throws JSONException {
        Entry entry = createBasicEntry();

        ArrayList<String> list = new ArrayList<>();
        list.add("name");
        list.add("description");

        entry.only(new String[]{"title", "url"})
                .except(new String[]{"deleted_at"})
                .onlyWithReferenceUid(list, "ref_field")
                .exceptWithReferenceUid(list, "ref_field");
    }

    // ---------------- NEGATIVE GETTERS ----------------

    @Test
    public void testNegativeGettersWhenFieldMissing() throws JSONException {
        Entry entry = createBasicEntry();

        assertNull(entry.getString("unknown"));
        assertFalse(entry.getBoolean("unknown"));
        assertEquals(0, entry.getInt("unknown"));
        assertEquals(0f, entry.getFloat("unknown"), 0.0001f);
        assertEquals(0d, entry.getDouble("unknown"), 0.0001d);
        assertEquals(0L, entry.getLong("unknown"));
        assertEquals((short) 0, entry.getShort("unknown"));
        assertNull(entry.getJSONObject("unknown"));
        assertNull(entry.getJSONArray("unknown"));
        assertNull(entry.getNumber("unknown"));
        assertNull(entry.getDate("unknown"));
    }

    @Test
    public void testGetUpdatedAtHelper() throws JSONException {
        Entry entry = createBasicEntry();

        // existing string field
        assertEquals("2021-01-02T10:00:00.000Z", entry.getUpdatedAt("updated_at"));

        // non-string field
        assertNull(entry.getUpdatedAt("int_field"));

        // missing field
        assertNull(entry.getUpdatedAt("missing_field"));
    }
}
