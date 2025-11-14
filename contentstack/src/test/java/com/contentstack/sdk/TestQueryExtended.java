package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Extended tests for Query class to maximize coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryExtended {

    private Context context;
    private Stack stack;
    private ContentType contentType;
    private Query query;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        contentType = stack.contentType("test_content_type");
        query = contentType.query();
    }

    // ==================== ARRAY FIELD TESTS ====================

    @Test
    public void testWhereWithJSONObject() {
        try {
            JSONObject whereObj = new JSONObject();
            whereObj.put("status", "published");
            query.where("metadata", whereObj);
            assertNotNull(query);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testWhereWithJSONArray() {
        try {
            JSONArray whereArray = new JSONArray();
            whereArray.put("value1");
            whereArray.put("value2");
            query.where("tags", whereArray);
            assertNotNull(query);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    // ==================== DATE TESTS ====================

    @Test
    public void testLessThanWithDate() {
        Date date = new Date();
        Query result = query.lessThan("created_at", date);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanWithDate() {
        Date date = new Date();
        Query result = query.greaterThan("updated_at", date);
        assertNotNull(result);
    }

    @Test
    public void testLessThanOrEqualToWithDate() {
        Date date = new Date();
        Query result = query.lessThanOrEqualTo("created_at", date);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanOrEqualToWithDate() {
        Date date = new Date();
        Query result = query.greaterThanOrEqualTo("updated_at", date);
        assertNotNull(result);
    }

    // ==================== MULTIPLE VALUE TESTS ====================

    @Test
    public void testWhereInWithMultipleValues() {
        Object[] values = {"active", "published", "archived", "draft"};
        Query result = query.containedIn("status", values);
        assertNotNull(result);
    }

    @Test
    public void testWhereNotInWithMultipleValues() {
        Object[] values = {"deleted", "spam", "trash"};
        Query result = query.notContainedIn("status", values);
        assertNotNull(result);
    }

    // ==================== ADDITIONAL FEATURES TESTS ====================

    @Test
    public void testIncludeContentTypeMultipleTimes() {
        query.includeContentType();
        Query result = query.includeContentType();
        assertNotNull(result);
    }

    // ==================== CACHE POLICY TESTS ====================

    @Test
    public void testSetCachePolicyIgnoreCache() {
        Query result = query.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(result);
    }

    @Test
    public void testSetCachePolicyNetworkElseCache() {
        Query result = query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(result);
    }

    // ==================== PAGINATION EDGE CASES ====================

    @Test
    public void testLimitWithLargeNumber() {
        Query result = query.limit(1000);
        assertNotNull(result);
    }

    @Test
    public void testSkipWithLargeNumber() {
        Query result = query.skip(10000);
        assertNotNull(result);
    }

    @Test
    public void testLimitAndSkipCombination() {
        Query result = query
            .limit(50)
            .skip(100);
        assertNotNull(result);
    }

    // ==================== MULTIPLE SORTING ====================

    @Test
    public void testAscendingOnMultipleFields() {
        query.ascending("field1");
        query.ascending("field2");
        Query result = query.ascending("field3");
        assertNotNull(result);
    }

    @Test
    public void testDescendingOnMultipleFields() {
        query.descending("field1");
        query.descending("field2");
        Query result = query.descending("field3");
        assertNotNull(result);
    }

    @Test
    public void testMixedSorting() {
        query.ascending("created_at");
        Query result = query.descending("updated_at");
        assertNotNull(result);
    }

    // ==================== REGEX VARIATIONS ====================

    @Test
    public void testRegexCaseInsensitive() {
        Query result = query.regex("title", "test", "i");
        assertNotNull(result);
    }

    @Test
    public void testRegexMultiline() {
        Query result = query.regex("description", "^Test", "m");
        assertNotNull(result);
    }

    @Test
    public void testRegexGlobal() {
        Query result = query.regex("content", "pattern", "g");
        assertNotNull(result);
    }

    @Test
    public void testRegexCombinedModifiers() {
        Query result = query.regex("text", "search", "igm");
        assertNotNull(result);
    }

    // ==================== SEARCH WITH QUERY ====================

    @Test
    public void testSearchWithLongText() {
        String longText = "This is a very long search text that should be handled properly by the query system";
        Query result = query.search(longText);
        assertNotNull(result);
    }

    @Test
    public void testSearchWithSpecialCharacters() {
        Query result = query.search("search & <special> \"characters\"");
        assertNotNull(result);
    }

    // ==================== INCLUDE REFERENCE VARIATIONS ====================

    @Test
    public void testIncludeReferenceWithSingleField() {
        Query result = query.includeReference(new String[]{"author"});
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithMultipleFields() {
        Query result = query.includeReference(new String[]{"author", "category", "tags", "related_posts"});
        assertNotNull(result);
    }

    // ==================== ONLY/EXCEPT VARIATIONS ====================

    @Test
    public void testOnlyWithSingleField() {
        Query result = query.only(new String[]{"title"});
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithManyFields() {
        String[] fields = {"field1", "field2", "field3", "field4", "field5", "field6", "field7", "field8"};
        Query result = query.only(fields);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithSingleField() {
        Query result = query.except(new String[]{"large_field"});
        assertNotNull(result);
    }

    @Test
    public void testExceptWithManyFields() {
        String[] fields = {"meta1", "meta2", "meta3", "meta4", "meta5"};
        Query result = query.except(fields);
        assertNotNull(result);
    }

    // ==================== TAGS VARIATIONS ====================

    @Test
    public void testTagsWithSingleTag() {
        Query result = query.tags(new String[]{"featured"});
        assertNotNull(result);
    }

    @Test
    public void testTagsWithManyTags() {
        String[] tags = {"tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10"};
        Query result = query.tags(tags);
        assertNotNull(result);
    }

    // ==================== COMPLEX QUERY BUILDING ====================

    @Test
    public void testComplexQueryWithAllFeatures() {
        Query result = query
            .where("status", "published")
            .lessThan("price", 100)
            .greaterThan("rating", 4.0)
            .containedIn("category", new Object[]{"electronics", "gadgets"})
            .notContainedIn("brand", new Object[]{"unknown"})
            .exists("image")
            .tags(new String[]{"featured", "sale"})
            .regex("title", ".*phone.*", "i")
            .search("smartphone")
            .includeReference(new String[]{"manufacturer"})
            .only(new String[]{"title", "price", "rating"})
            .ascending("price")
            .limit(20)
            .skip(0)
            .locale("en-us")
            .includeCount()
            .includeContentType();
        
        assertNotNull(result);
        assertSame(query, result);
    }

    @Test
    public void testComplexOrQuery() throws Exception {
        Query q1 = contentType.query().where("status", "published");
        Query q2 = contentType.query().where("status", "featured");
        Query q3 = contentType.query().where("status", "trending");
        
        ArrayList<Query> orQueries = new ArrayList<>();
        orQueries.add(q1);
        orQueries.add(q2);
        orQueries.add(q3);
        
        Query result = query.or(orQueries);
        assertNotNull(result);
    }

    @Test
    public void testComplexAndQuery() throws Exception {
        Query q1 = contentType.query().where("type", "article");
        Query q2 = contentType.query().greaterThan("views", 1000);
        Query q3 = contentType.query().lessThan("age_days", 30);
        
        ArrayList<Query> andQueries = new ArrayList<>();
        andQueries.add(q1);
        andQueries.add(q2);
        andQueries.add(q3);
        
        Query result = query.and(andQueries);
        assertNotNull(result);
    }

    // ==================== NESTED QUERY OPERATIONS ====================

    @Test
    public void testNestedOrWithAnd() throws Exception {
        Query q1 = contentType.query().where("field1", "value1");
        Query q2 = contentType.query().where("field2", "value2");
        
        ArrayList<Query> orQueries = new ArrayList<>();
        orQueries.add(q1);
        orQueries.add(q2);
        
        query.or(orQueries);
        
        Query q3 = contentType.query().where("field3", "value3");
        Query q4 = contentType.query().where("field4", "value4");
        
        ArrayList<Query> andQueries = new ArrayList<>();
        andQueries.add(q3);
        andQueries.add(q4);
        
        Query result = query.and(andQueries);
        assertNotNull(result);
    }

    // ==================== EDGE CASE COMBINATIONS ====================

    @Test
    public void testQueryWithOnlyNulls() {
        query.where(null, null);
        query.lessThan(null, null);
        query.greaterThan(null, null);
        query.ascending(null);
        query.descending(null);
        query.locale(null);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithEmptyStrings() {
        query.where("", "");
        query.regex("", "", "");
        query.search("");
        query.ascending("");
        query.descending("");
        query.locale("");
        assertNotNull(query);
    }

    @Test
    public void testQueryReuseAfterConfiguration() {
        query.where("field1", "value1").limit(10);
        query.where("field2", "value2").limit(20);
        Query result = query.where("field3", "value3").limit(30);
        assertNotNull(result);
    }

    // ==================== INCLUDE REFERENCE WITH ONLY/EXCEPT ====================

    @Test
    public void testIncludeReferenceWithOnly() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        fields.add("description");
        
        Query result = query
            .includeReference(new String[]{"author"})
            .onlyWithReferenceUid(fields, "author");
        
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceWithExcept() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("metadata");
        fields.add("internal_notes");
        
        Query result = query
            .includeReference(new String[]{"author"})
            .exceptWithReferenceUid(fields, "author");
        
        assertNotNull(result);
    }

    // ==================== MULTIPLE REFERENCE FIELDS ====================

    @Test
    public void testMultipleIncludeReferenceOperations() {
        Query result = query
            .includeReference(new String[]{"author"})
            .includeReference(new String[]{"category"})
            .includeReference(new String[]{"tags"})
            .includeReference(new String[]{"related_content"});
        
        assertNotNull(result);
    }

    // ==================== ADD QUERY TESTS ====================

    @Test
    public void testAddQueryWithVariousParams() {
        query.addQuery("param1", "value1");
        query.addQuery("param2", "value2");
        query.addQuery("param3", "value3");
        Query result = query.addQuery("param4", "value4");
        assertNotNull(result);
    }

    @Test
    public void testRemoveQueryMultipleTimes() {
        query.addQuery("test1", "value1");
        query.addQuery("test2", "value2");
        query.removeQuery("test1");
        Query result = query.removeQuery("test2");
        assertNotNull(result);
    }

    // ==================== NUMERIC VALUE TESTS ====================

    @Test
    public void testWhereWithInteger() {
        Query result = query.where("count", 42);
        assertNotNull(result);
    }

    @Test
    public void testWhereWithDouble() {
        Query result = query.where("price", 99.99);
        assertNotNull(result);
    }

    @Test
    public void testWhereWithBoolean() {
        Query result = query.where("is_active", true);
        assertNotNull(result);
    }

    @Test
    public void testLessThanWithNumericValues() {
        query.lessThan("int_field", 100);
        query.lessThan("double_field", 99.99);
        Query result = query.lessThan("long_field", 1000000L);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanWithNumericValues() {
        query.greaterThan("int_field", 0);
        query.greaterThan("double_field", 0.01);
        Query result = query.greaterThan("long_field", 1L);
        assertNotNull(result);
    }

    // ==================== ARRAY OPERATIONS ====================

    @Test
    public void testContainedInWithMixedTypes() {
        Object[] values = {"string", 123, 45.67, true};
        Query result = query.containedIn("mixed_field", values);
        assertNotNull(result);
    }

    @Test
    public void testNotContainedInWithMixedTypes() {
        Object[] values = {"excluded", 999, false};
        Query result = query.notContainedIn("mixed_field", values);
        assertNotNull(result);
    }

    // ==================== EXISTS/NOT EXISTS ====================

    @Test
    public void testExistsOnMultipleFields() {
        query.exists("field1");
        query.exists("field2");
        Query result = query.exists("field3");
        assertNotNull(result);
    }

    @Test
    public void testNotExistsOnMultipleFields() {
        query.notExists("field1");
        query.notExists("field2");
        Query result = query.notExists("field3");
        assertNotNull(result);
    }

    @Test
    public void testExistsAndNotExistsCombination() {
        query.exists("required_field");
        Query result = query.notExists("optional_field");
        assertNotNull(result);
    }

    // ==================== COMPARISON OPERATORS ====================

    @Test
    public void testNotEqualToWithVariousTypes() {
        query.notEqualTo("string_field", "value");
        query.notEqualTo("int_field", 42);
        query.notEqualTo("boolean_field", false);
        Query result = query.notEqualTo("double_field", 3.14);
        assertNotNull(result);
    }

    // ==================== GET CONTENT TYPE ====================

    @Test
    public void testGetContentTypeReturnsCorrectName() {
        String name = query.getContentType();
        assertNotNull(name);
        assertEquals("test_content_type", name);
    }

    // ==================== LOCALE TESTS ====================

    @Test
    public void testLocaleWithDifferentFormats() {
        query.locale("en-US");
        query.locale("fr-FR");
        query.locale("es-ES");
        Query result = query.locale("de-DE");
        assertNotNull(result);
    }

    // ==================== INCLUDE EMBEDDED ITEMS ====================

    @Test
    public void testIncludeEmbeddedItemsMultipleTimes() {
        query.includeEmbeddedItems();
        Query result = query.includeEmbeddedItems();
        assertNotNull(result);
    }

    // ==================== INCLUDE FALLBACK ====================

    @Test
    public void testIncludeFallbackMultipleTimes() {
        query.includeFallback();
        Query result = query.includeFallback();
        assertNotNull(result);
    }

    // ==================== COMPREHENSIVE CHAIN TEST ====================

    @Test
    public void testVeryLongMethodChain() {
        Query result = query
            .where("field1", "value1")
            .where("field2", 123)
            .lessThan("field3", 100)
            .lessThanOrEqualTo("field4", 200)
            .greaterThan("field5", 10)
            .greaterThanOrEqualTo("field6", 20)
            .notEqualTo("field7", "excluded")
            .containedIn("field8", new Object[]{"a", "b", "c"})
            .notContainedIn("field9", new Object[]{"x", "y", "z"})
            .exists("field10")
            .notExists("field11")
            .tags(new String[]{"tag1", "tag2"})
            .regex("field12", "pattern", "i")
            .search("search text")
            .includeReference(new String[]{"ref1", "ref2"})
            .only(new String[]{"f1", "f2", "f3"})
            .except(new String[]{"f4", "f5"})
            .ascending("sort1")
            .descending("sort2")
            .limit(50)
            .skip(25)
            .locale("en-US")
            .includeCount()
            .includeContentType()
            .includeFallback()
            .includeEmbeddedItems();
        
        assertNotNull(result);
        assertSame(query, result);
    }
}

