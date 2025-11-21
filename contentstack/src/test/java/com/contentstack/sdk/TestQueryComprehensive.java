package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Query class to achieve maximum coverage.
 * Covers all query builder methods, operators, and options.
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryComprehensive {

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

    // ==================== Basic Query Methods ====================

    @Test
    public void testWhere() {
        Query result = query.where("title", "Test Value");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testWhereWithNull() {
        Query result = query.where(null, "value");
        assertNotNull(result);
    }

    @Test
    public void testAddQuery() {
        Query result = query.addQuery("key", "value");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testRemoveQuery() {
        query.addQuery("key", "value");
        Query result = query.removeQuery("key");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testRemoveQueryNull() {
        Query result = query.removeQuery(null);
        assertNotNull(result);
    }

    // ==================== Comparison Operators ====================

    @Test
    public void testLessThan() {
        Query result = query.lessThan("price", 100);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testLessThanWithNull() {
        Query result = query.lessThan(null, 100);
        assertNotNull(result);
    }

    @Test
    public void testLessThanOrEqualTo() {
        Query result = query.lessThanOrEqualTo("price", 100);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testGreaterThan() {
        Query result = query.greaterThan("price", 50);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testGreaterThanOrEqualTo() {
        Query result = query.greaterThanOrEqualTo("price", 50);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testNotEqualTo() {
        Query result = query.notEqualTo("status", "draft");
        assertNotNull(result);
        assertEquals(query, result);
    }

    // ==================== Array Operators ====================

    @Test
    public void testContainedIn() {
        String[] values = {"value1", "value2", "value3"};
        Query result = query.containedIn("tags", values);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testContainedInWithNull() {
        Query result = query.containedIn(null, new String[]{"value"});
        assertNotNull(result);
    }

    @Test
    public void testNotContainedIn() {
        String[] values = {"excluded1", "excluded2"};
        Query result = query.notContainedIn("tags", values);
        assertNotNull(result);
        assertEquals(query, result);
    }

    // ==================== Existence Operators ====================

    @Test
    public void testExists() {
        Query result = query.exists("field_name");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testExistsWithNull() {
        Query result = query.exists(null);
        assertNotNull(result);
    }

    @Test
    public void testNotExists() {
        Query result = query.notExists("field_name");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testNotExistsWithNull() {
        Query result = query.notExists(null);
        assertNotNull(result);
    }

    // ==================== Include References ====================

    @Test
    public void testIncludeReference() {
        Query result = query.includeReference("category");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeReferenceArray() {
        String[] references = {"category", "author", "tags"};
        Query result = query.includeReference(references);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeReferenceWithNull() {
        Query result = query.includeReference((String) null);
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceArrayWithNull() {
        Query result = query.includeReference((String[]) null);
        assertNotNull(result);
    }

    @Test
    public void testIncludeReferenceContentTypUid() {
        Query result = query.includeReferenceContentTypUid();
        assertNotNull(result);
        assertEquals(query, result);
    }

    // ==================== Tags ====================

    @Test
    public void testTags() {
        String[] tags = {"tag1", "tag2", "tag3"};
        Query result = query.tags(tags);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testTagsWithNull() {
        Query result = query.tags(null);
        assertNotNull(result);
    }

    // ==================== Sorting ====================

    @Test
    public void testAscending() {
        Query result = query.ascending("created_at");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testAscendingWithNull() {
        Query result = query.ascending(null);
        assertNotNull(result);
    }

    @Test
    public void testDescending() {
        Query result = query.descending("updated_at");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testDescendingWithNull() {
        Query result = query.descending(null);
        assertNotNull(result);
    }

    // ==================== Field Selection ====================

    @Test
    public void testExceptArrayList() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("field1");
        fields.add("field2");
        Query result = query.except(fields);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testExceptArray() {
        String[] fields = {"field1", "field2"};
        Query result = query.except(fields);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testExceptWithNull() {
        Query result = query.except((ArrayList<String>) null);
        assertNotNull(result);
    }

    @Test
    public void testOnly() {
        String[] fields = {"title", "description"};
        Query result = query.only(fields);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testOnlyWithNull() {
        Query result = query.only(null);
        assertNotNull(result);
    }

    @Test
    public void testOnlyWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        Query result = query.onlyWithReferenceUid(fields, "category");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testOnlyWithReferenceUidNull() {
        Query result = query.onlyWithReferenceUid(null, null);
        assertNotNull(result);
    }

    @Test
    public void testExceptWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("internal_field");
        Query result = query.exceptWithReferenceUid(fields, "category");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testExceptWithReferenceUidNull() {
        Query result = query.exceptWithReferenceUid(null, null);
        assertNotNull(result);
    }

    // ==================== Count ====================

    @Test
    public void testCount() {
        Query result = query.count();
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeCount() {
        Query result = query.includeCount();
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeContentType() {
        Query result = query.includeContentType();
        assertNotNull(result);
        assertEquals(query, result);
    }

    // ==================== Pagination ====================

    @Test
    public void testSkip() {
        Query result = query.skip(10);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testSkipZero() {
        Query result = query.skip(0);
        assertNotNull(result);
    }

    @Test
    public void testSkipNegative() {
        Query result = query.skip(-5);
        assertNotNull(result);
    }

    @Test
    public void testLimit() {
        Query result = query.limit(20);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testLimitZero() {
        Query result = query.limit(0);
        assertNotNull(result);
    }

    @Test
    public void testLimitNegative() {
        Query result = query.limit(-10);
        assertNotNull(result);
    }

    // ==================== Regex ====================

    @Test
    public void testRegex() {
        Query result = query.regex("title", "^test.*");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testRegexWithNull() {
        Query result = query.regex(null, null);
        assertNotNull(result);
    }

    @Test
    public void testRegexWithModifiers() {
        Query result = query.regex("title", "test", "i");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testRegexWithModifiersNull() {
        Query result = query.regex(null, null, null);
        assertNotNull(result);
    }

    // ==================== Language/Locale ====================

    @Test
    public void testLanguage() {
        Query result = query.language(Language.ENGLISH_UNITED_STATES);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testLanguageNull() {
        Query result = query.language(null);
        assertNotNull(result);
    }

    @Test
    public void testLocale() {
        Query result = query.locale("en-us");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testLocaleNull() {
        Query result = query.locale(null);
        assertNotNull(result);
    }

    // ==================== Search ====================

    @Test
    public void testSearch() {
        Query result = query.search("search term");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testSearchNull() {
        Query result = query.search(null);
        assertNotNull(result);
    }

    // ==================== Cache Policy ====================

    @Test
    public void testSetCachePolicyNetworkOnly() {
        Query result = query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testSetCachePolicyCacheOnly() {
        Query result = query.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testSetCachePolicyCacheThenNetwork() {
        Query result = query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testSetCachePolicyNull() {
        Query result = query.setCachePolicy(null);
        assertNotNull(result);
    }

    // ==================== Complex Queries ====================

    @Test
    public void testAndQuery() {
        Query query1 = stack.contentType("test_content_type").query();
        query1.where("price", 100);
        
        Query query2 = stack.contentType("test_content_type").query();
        query2.where("stock", "available");
        
        ArrayList<Query> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        
        Query result = query.and(queries);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testAndQueryWithNull() {
        Query result = query.and(null);
        assertNotNull(result);
    }

    @Test
    public void testAndQueryWithEmpty() {
        Query result = query.and(new ArrayList<Query>());
        assertNotNull(result);
    }

    @Test
    public void testOrQuery() {
        Query query1 = stack.contentType("test_content_type").query();
        query1.where("status", "published");
        
        Query query2 = stack.contentType("test_content_type").query();
        query2.where("status", "draft");
        
        ArrayList<Query> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        
        Query result = query.or(queries);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testOrQueryWithNull() {
        Query result = query.or(null);
        assertNotNull(result);
    }

    @Test
    public void testWhereIn() {
        Query subQuery = stack.contentType("category").query();
        subQuery.where("name", "Electronics");
        
        Query result = query.whereIn("category", subQuery);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testWhereInWithNull() {
        Query result = query.whereIn(null, null);
        assertNotNull(result);
    }

    @Test
    public void testWhereNotIn() {
        Query subQuery = stack.contentType("category").query();
        subQuery.where("name", "Restricted");
        
        Query result = query.whereNotIn("category", subQuery);
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testWhereNotInWithNull() {
        Query result = query.whereNotIn(null, null);
        assertNotNull(result);
    }

    // ==================== Additional Options ====================

    @Test
    public void testAddParam() {
        Query result = query.addParam("custom_key", "custom_value");
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testAddParamNull() {
        Query result = query.addParam(null, null);
        assertNotNull(result);
    }

    @Test
    public void testIncludeFallback() {
        Query result = query.includeFallback();
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeEmbeddedItems() {
        Query result = query.includeEmbeddedItems();
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testIncludeMetadata() {
        Query result = query.includeMetadata();
        assertNotNull(result);
        assertEquals(query, result);
    }

    // ==================== Headers ====================

    @Test
    public void testSetHeader() {
        query.setHeader("custom-header", "custom-value");
        // Verify no exception thrown
        assertNotNull(query);
    }

    @Test
    public void testSetHeaderNull() {
        query.setHeader(null, null);
        // Verify no exception thrown
        assertNotNull(query);
    }

    @Test
    public void testSetHeaderEmptyKey() {
        query.setHeader("", "value");
        assertNotNull(query);
    }

    @Test
    public void testRemoveHeader() {
        query.setHeader("custom-header", "custom-value");
        query.removeHeader("custom-header");
        assertNotNull(query);
    }

    @Test
    public void testRemoveHeaderNull() {
        query.removeHeader(null);
        assertNotNull(query);
    }

    // ==================== Utility Methods ====================

    @Test
    public void testGetContentType() {
        String contentType = query.getContentType();
        assertEquals("test_content_type", contentType);
    }

    @Test
    public void testCancelRequest() {
        query.cancelRequest();
        // Verify no exception thrown
        assertNotNull(query);
    }

    // ==================== Complex Chaining ====================

    @Test
    public void testComplexQueryChaining() {
        Query result = query
            .where("status", "published")
            .lessThan("price", 1000)
            .greaterThan("rating", 4.0)
            .tags(new String[]{"featured", "popular"})
            .ascending("created_at")
            .skip(10)
            .limit(20)
            .includeReference("author")
            .includeCount();
        
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testComplexFieldSelection() {
        Query result = query
            .only(new String[]{"title", "description", "price"})
            .includeReference("category")
            .locale("en-us")
            .includeMetadata();
        
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testComplexComparison() {
        Query result = query
            .greaterThanOrEqualTo("min_age", 18)
            .lessThanOrEqualTo("max_age", 65)
            .notEqualTo("status", "deleted")
            .exists("verified")
            .notExists("banned");
        
        assertNotNull(result);
        assertEquals(query, result);
    }
}

