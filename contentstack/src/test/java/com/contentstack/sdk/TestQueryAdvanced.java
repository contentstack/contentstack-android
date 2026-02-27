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
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Advanced Query tests targeting uncovered paths
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryAdvanced {

    private Context context;
    private Stack stack;
    private Query query;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        ContentType contentType = stack.contentType("test_content_type");
        query = contentType.query();
    }

    // ==================== Advanced Where Operations ====================

    @Test
    public void testWhereWithNullKey() {
        Query result = query.where(null, "value");
        assertNotNull(result);
    }

    @Test
    public void testWhereWithNullValue() {
        Query result = query.where("key", null);
        assertNotNull(result);
    }

    @Test
    public void testWhereWithEmptyKey() {
        Query result = query.where("", "value");
        assertNotNull(result);
    }

    @Test
    public void testWhereWithComplexValue() throws Exception {
        JSONObject complexValue = new JSONObject();
        complexValue.put("nested", "value");
        Query result = query.where("field", complexValue);
        assertNotNull(result);
    }

    @Test
    public void testWhereWithArrayValue() throws Exception {
        JSONArray arrayValue = new JSONArray();
        arrayValue.put("item1");
        arrayValue.put("item2");
        Query result = query.where("field", arrayValue);
        assertNotNull(result);
    }

    // ==================== AddQuery Tests ====================

    @Test
    public void testAddQueryWithValidParams() {
        Query result = query.addQuery("key", "value");
        assertNotNull(result);
    }

    @Test
    public void testAddQueryWithNullKey() {
        Query result = query.addQuery(null, "value");
        assertNotNull(result);
    }

    @Test
    public void testAddQueryWithNullValue() {
        Query result = query.addQuery("key", null);
        assertNotNull(result);
    }

    @Test
    public void testAddQueryMultiple() {
        query.addQuery("key1", "value1");
        query.addQuery("key2", "value2");
        query.addQuery("key3", "value3");
        assertNotNull(query);
    }

    // ==================== RemoveQuery Tests ====================

    @Test
    public void testRemoveQueryWithValidKey() {
        query.addQuery("test_key", "test_value");
        Query result = query.removeQuery("test_key");
        assertNotNull(result);
    }

    @Test
    public void testRemoveQueryWithNullKey() {
        Query result = query.removeQuery(null);
        assertNotNull(result);
    }

    @Test
    public void testRemoveQueryWithEmptyKey() {
        Query result = query.removeQuery("");
        assertNotNull(result);
    }

    @Test
    public void testRemoveQueryNonExistent() {
        Query result = query.removeQuery("non_existent_key");
        assertNotNull(result);
    }

    // ==================== Comparison Operators ====================

    @Test
    public void testLessThanWithNull() {
        Query result = query.lessThan(null, null);
        assertNotNull(result);
    }

    @Test
    public void testLessThanWithZero() {
        Query result = query.lessThan("field", 0);
        assertNotNull(result);
    }

    @Test
    public void testLessThanWithNegative() {
        Query result = query.lessThan("field", -100);
        assertNotNull(result);
    }

    @Test
    public void testLessThanOrEqualToWithNull() {
        Query result = query.lessThanOrEqualTo(null, null);
        assertNotNull(result);
    }

    @Test
    public void testLessThanOrEqualToWithZero() {
        Query result = query.lessThanOrEqualTo("field", 0);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanWithNull() {
        Query result = query.greaterThan(null, null);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanWithZero() {
        Query result = query.greaterThan("field", 0);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanOrEqualToWithNull() {
        Query result = query.greaterThanOrEqualTo(null, null);
        assertNotNull(result);
    }

    @Test
    public void testGreaterThanOrEqualToWithZero() {
        Query result = query.greaterThanOrEqualTo("field", 0);
        assertNotNull(result);
    }

    @Test
    public void testNotEqualToWithNull() {
        Query result = query.notEqualTo(null, null);
        assertNotNull(result);
    }

    @Test
    public void testNotEqualToWithValue() {
        Query result = query.notEqualTo("field", "value");
        assertNotNull(result);
    }

    // ==================== Exists/NotExists Tests ====================

    @Test
    public void testExistsWithNull() {
        Query result = query.exists(null);
        assertNotNull(result);
    }

    @Test
    public void testExistsWithEmpty() {
        Query result = query.exists("");
        assertNotNull(result);
    }

    @Test
    public void testNotExistsWithNull() {
        Query result = query.notExists(null);
        assertNotNull(result);
    }

    @Test
    public void testNotExistsWithEmpty() {
        Query result = query.notExists("");
        assertNotNull(result);
    }

    @Test
    public void testExistsMultipleFields() {
        query.exists("field1");
        query.exists("field2");
        query.exists("field3");
        assertNotNull(query);
    }

    // ==================== Array Operators ====================

    @Test
    public void testContainedInWithNullKey() {
        String[] values = {"val1", "val2"};
        Query result = query.containedIn(null, values);
        assertNotNull(result);
    }

    @Test
    public void testContainedInWithSingleValue() {
        String[] values = {"single"};
        Query result = query.containedIn("field", values);
        assertNotNull(result);
    }

    @Test
    public void testContainedInWithManyValues() {
        String[] values = new String[100];
        for (int i = 0; i < 100; i++) {
            values[i] = "value" + i;
        }
        Query result = query.containedIn("field", values);
        assertNotNull(result);
    }

    @Test
    public void testNotContainedInWithNullKey() {
        String[] values = {"val1", "val2"};
        Query result = query.notContainedIn(null, values);
        assertNotNull(result);
    }

    @Test
    public void testNotContainedInWithSingleValue() {
        String[] values = {"single"};
        Query result = query.notContainedIn("field", values);
        assertNotNull(result);
    }

    // ==================== Regex Tests ====================

    @Test
    public void testRegexWithNullKey() {
        Query result = query.regex(null, "pattern", null);
        assertNotNull(result);
    }

    @Test
    public void testRegexWithNullPattern() {
        Query result = query.regex("field", null, null);
        assertNotNull(result);
    }

    @Test
    public void testRegexWithEmptyPattern() {
        Query result = query.regex("field", "", null);
        assertNotNull(result);
    }

    @Test
    public void testRegexWithModifiers() {
        query.regex("field1", "pattern1", "i");
        query.regex("field2", "pattern2", "m");
        query.regex("field3", "pattern3", "im");
        assertNotNull(query);
    }

    @Test
    public void testRegexWithComplexPatterns() {
        query.regex("email", "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$", null);
        query.regex("phone", "^\\d{3}-\\d{3}-\\d{4}$", null);
        query.regex("url", "^https?://.*", "i");
        assertNotNull(query);
    }

    // ==================== Search Tests ====================

    @Test
    public void testSearchWithNull() {
        Query result = query.search(null);
        assertNotNull(result);
    }

    @Test
    public void testSearchWithEmpty() {
        Query result = query.search("");
        assertNotNull(result);
    }

    @Test
    public void testSearchWithLongString() {
        StringBuilder longSearch = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longSearch.append("word ");
        }
        Query result = query.search(longSearch.toString());
        assertNotNull(result);
    }

    @Test
    public void testSearchWithSpecialCharacters() {
        query.search("search with !@#$%^&*()");
        query.search("search with \"quotes\"");
        query.search("search with 'apostrophes'");
        assertNotNull(query);
    }

    // ==================== Tags Tests ====================

    @Test
    public void testTagsWithSingleTag() {
        String[] tags = {"tag1"};
        Query result = query.tags(tags);
        assertNotNull(result);
    }

    @Test
    public void testTagsWithManyTags() {
        String[] tags = new String[50];
        for (int i = 0; i < 50; i++) {
            tags[i] = "tag" + i;
        }
        Query result = query.tags(tags);
        assertNotNull(result);
    }

    @Test
    public void testTagsWithSpecialCharacters() {
        String[] tags = {"tag-with-dash", "tag_with_underscore", "tag with space"};
        Query result = query.tags(tags);
        assertNotNull(result);
    }

    // ==================== Sort Tests ====================

    @Test
    public void testAscendingWithNull() {
        Query result = query.ascending(null);
        assertNotNull(result);
    }

    @Test
    public void testAscendingWithEmpty() {
        Query result = query.ascending("");
        assertNotNull(result);
    }

    @Test
    public void testDescendingWithNull() {
        Query result = query.descending(null);
        assertNotNull(result);
    }

    @Test
    public void testDescendingWithEmpty() {
        Query result = query.descending("");
        assertNotNull(result);
    }

    @Test
    public void testSortByMultipleFields() {
        query.ascending("field1");
        query.descending("field2");
        query.ascending("field3");
        assertNotNull(query);
    }

    // ==================== Skip and Limit Edge Cases ====================

    @Test
    public void testSkipWithMaxValue() {
        Query result = query.skip(Integer.MAX_VALUE);
        assertNotNull(result);
    }

    @Test
    public void testLimitWithMaxValue() {
        Query result = query.limit(Integer.MAX_VALUE);
        assertNotNull(result);
    }

    @Test
    public void testSkipAndLimitCombinations() {
        query.skip(0).limit(10);
        query.skip(10).limit(20);
        query.skip(100).limit(5);
        assertNotNull(query);
    }

    // ==================== Locale and Language ====================

    @Test
    public void testLocaleWithNull() {
        Query result = query.locale(null);
        assertNotNull(result);
    }

    @Test
    public void testLocaleWithEmpty() {
        Query result = query.locale("");
        assertNotNull(result);
    }

    @Test
    public void testLocaleWithInvalidCode() {
        Query result = query.locale("invalid");
        assertNotNull(result);
    }

    @Test
    public void testLanguageWithNull() {
        Query result = query.language(null);
        assertNotNull(result);
    }

    @Test
    public void testLanguageWithMultipleEnums() {
        Language[] languages = {
            Language.ENGLISH_UNITED_STATES,
            Language.SPANISH_SPAIN,
            Language.FRENCH_FRANCE
        };
        
        for (Language lang : languages) {
            Query q = stack.contentType("test").query();
            q.language(lang);
            assertNotNull(q);
        }
    }

    // ==================== Reference Operations ====================

    @Test
    public void testIncludeReferenceWithEmptyString() {
        Query result = query.includeReference("");
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceMultipleTimes() {
        query.includeReference("ref1");
        query.includeReference("ref2");
        query.includeReference("ref3");
        assertNotNull(query);
    }

    @Test
    public void testIncludeReferenceArrayWithDuplicates() {
        String[] refs = {"author", "author", "category", "category"};
        Query result = query.includeReference(refs);
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithReferenceUidEmptyList() {
        ArrayList<String> fields = new ArrayList<>();
        Query result = query.onlyWithReferenceUid(fields, "ref");
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithReferenceUidNullRef() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("field1");
        Query result = query.onlyWithReferenceUid(fields, null);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithReferenceUidEmptyList() {
        ArrayList<String> fields = new ArrayList<>();
        Query result = query.exceptWithReferenceUid(fields, "ref");
        assertNotNull(result);
    }

    // ==================== Include Options ====================

    @Test
    public void testIncludeCountMultipleTimes() {
        query.includeCount();
        query.includeCount();
        query.includeCount();
        assertNotNull(query);
    }

    @Test
    public void testIncludeContentTypeMultipleTimes() {
        query.includeContentType();
        query.includeContentType();
        assertNotNull(query);
    }

    @Test
    public void testIncludeReferenceContentTypUidMultipleTimes() {
        query.includeReferenceContentTypUid();
        query.includeReferenceContentTypUid();
        assertNotNull(query);
    }

    @Test
    public void testIncludeFallbackMultipleTimes() {
        query.includeFallback();
        query.includeFallback();
        assertNotNull(query);
    }

    @Test
    public void testIncludeEmbeddedItemsMultipleTimes() {
        query.includeEmbeddedItems();
        query.includeEmbeddedItems();
        assertNotNull(query);
    }

    @Test
    public void testIncludeMetadataMultipleTimes() {
        query.includeMetadata();
        query.includeMetadata();
        assertNotNull(query);
    }

    // ==================== Logical Operators ====================

    @Test
    public void testOrWithEmptyList() {
        ArrayList<Query> queries = new ArrayList<>();
        Query result = query.or(queries);
        assertNotNull(result);
    }

    @Test
    public void testOrWithSingleQuery() {
        Query subQuery = stack.contentType("test").query();
        subQuery.where("field", "value");
        
        ArrayList<Query> queries = new ArrayList<>();
        queries.add(subQuery);
        
        Query result = query.or(queries);
        assertNotNull(result);
    }

    @Test
    public void testOrWithManyQueries() {
        ArrayList<Query> queries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Query subQuery = stack.contentType("test").query();
            subQuery.where("field" + i, "value" + i);
            queries.add(subQuery);
        }
        
        Query result = query.or(queries);
        assertNotNull(result);
    }

    @Test
    public void testAndWithEmptyList() {
        ArrayList<Query> queries = new ArrayList<>();
        Query result = query.and(queries);
        assertNotNull(result);
    }

    @Test
    public void testAndWithSingleQuery() {
        Query subQuery = stack.contentType("test").query();
        subQuery.where("field", "value");
        
        ArrayList<Query> queries = new ArrayList<>();
        queries.add(subQuery);
        
        Query result = query.and(queries);
        assertNotNull(result);
    }

    // ==================== WhereIn/WhereNotIn with Query ====================

    @Test
    public void testWhereInWithNullKey() {
        Query subQuery = stack.contentType("test").query();
        Query result = query.whereIn(null, subQuery);
        assertNotNull(result);
    }

    @Test
    public void testWhereInWithNullQuery() {
        Query result = query.whereIn("field", null);
        assertNotNull(result);
    }

    @Test
    public void testWhereNotInWithNullKey() {
        Query subQuery = stack.contentType("test").query();
        Query result = query.whereNotIn(null, subQuery);
        assertNotNull(result);
    }

    @Test
    public void testWhereNotInWithNullQuery() {
        Query result = query.whereNotIn("field", null);
        assertNotNull(result);
    }

    // ==================== Complex Chaining Scenarios ====================

    @Test
    public void testComplexQueryChaining() {
        Query result = query
            .where("status", "published")
            .greaterThan("views", 1000)
            .lessThan("views", 10000)
            .exists("featured_image")
            .notExists("deleted_at")
            .regex("title", ".*tutorial.*", "i")
            .search("android development")
            .ascending("created_at")
            .skip(20)
            .limit(10)
            .includeCount()
            .includeReference("author")
            .includeFallback()
            .includeEmbeddedItems()
            .locale("en-us");
        
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testMultipleOrConditions() {
        Query q1 = stack.contentType("test").query().where("status", "published");
        Query q2 = stack.contentType("test").query().where("status", "draft");
        Query q3 = stack.contentType("test").query().where("status", "archived");
        
        ArrayList<Query> orQueries = new ArrayList<>();
        orQueries.add(q1);
        orQueries.add(q2);
        orQueries.add(q3);
        
        query.or(orQueries);
        assertNotNull(query);
    }

    @Test
    public void testMultipleAndConditions() {
        Query q1 = stack.contentType("test").query().greaterThan("price", 10);
        Query q2 = stack.contentType("test").query().lessThan("price", 100);
        Query q3 = stack.contentType("test").query().exists("in_stock");
        
        ArrayList<Query> andQueries = new ArrayList<>();
        andQueries.add(q1);
        andQueries.add(q2);
        andQueries.add(q3);
        
        query.and(andQueries);
        assertNotNull(query);
    }

    // ==================== Cancel Request ====================

    @Test
    public void testCancelRequestMultipleTimes() {
        query.cancelRequest();
        query.cancelRequest();
        query.cancelRequest();
        assertNotNull(query);
    }

    @Test
    public void testCancelRequestAfterConfiguration() {
        query.where("field", "value")
             .limit(10)
             .skip(5);
        query.cancelRequest();
        assertNotNull(query);
    }
}
