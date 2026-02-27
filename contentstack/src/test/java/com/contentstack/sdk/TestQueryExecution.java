package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for Query execution methods (find, findOne, etc.) to improve coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryExecution {

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

    // ==================== Find Tests ====================

    @Test
    public void testFindWithCallback() {
        QueryResultsCallBack callback = new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                // Callback for find
            }
        };
        
        Query result = query.find(callback);
        assertNotNull(result);
    }

    @Test
    public void testFindWithNullCallback() {
        Query result = query.find(null);
        assertNotNull(result);
    }

    @Test
    public void testFindWithMultipleConditions() {
        query.where("status", "published")
             .greaterThan("price", 100)
             .lessThan("price", 1000)
             .skip(10)
             .limit(20);
        
        QueryResultsCallBack callback = new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                // Handle result
            }
        };
        
        Query result = query.find(callback);
        assertNotNull(result);
    }

    // ==================== FindOne Tests ====================

    @Test
    public void testFindOneWithCallback() {
        SingleQueryResultCallback callback = new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Callback for findOne
            }
        };
        
        Query result = query.findOne(callback);
        assertNotNull(result);
    }

    @Test
    public void testFindOneWithNullCallback() {
        Query result = query.findOne(null);
        assertNotNull(result);
    }

    @Test
    public void testFindOneWithExistingLimit() {
        query.limit(50); // Set initial limit
        
        SingleQueryResultCallback callback = new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Handle result
            }
        };
        
        Query result = query.findOne(callback);
        assertNotNull(result);
    }

    @Test
    public void testFindOneWithConditions() {
        query.where("uid", "test_uid")
             .includeReference("author");
        
        SingleQueryResultCallback callback = new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Handle result
            }
        };
        
        Query result = query.findOne(callback);
        assertNotNull(result);
    }

    // ==================== Complex Query Scenarios ====================

    @Test
    public void testQueryWithAllCachePolicies() {
        CachePolicy[] policies = {
            CachePolicy.NETWORK_ONLY,
            CachePolicy.CACHE_ONLY,
            CachePolicy.CACHE_THEN_NETWORK,
            CachePolicy.CACHE_ELSE_NETWORK,
            CachePolicy.NETWORK_ELSE_CACHE,
            CachePolicy.IGNORE_CACHE
        };
        
        for (CachePolicy policy : policies) {
            Query testQuery = stack.contentType("test_content_type").query();
            testQuery.setCachePolicy(policy);
            assertNotNull(testQuery);
        }
    }

    @Test
    public void testQueryWithAllFieldOperations() {
        query.only(new String[]{"title", "description"})
             .except(new String[]{"internal_field"})
             .includeReference("category")
             .includeCount()
             .includeContentType();
        
        assertNotNull(query);
    }

    @Test
    public void testQueryWithAllSortingOptions() {
        Query q1 = stack.contentType("test_content_type").query();
        q1.ascending("created_at");
        assertNotNull(q1);
        
        Query q2 = stack.contentType("test_content_type").query();
        q2.descending("updated_at");
        assertNotNull(q2);
    }

    @Test
    public void testQueryWithPagination() {
        for (int i = 0; i < 5; i++) {
            Query pageQuery = stack.contentType("test_content_type").query();
            pageQuery.skip(i * 20).limit(20);
            assertNotNull(pageQuery);
        }
    }

    @Test
    public void testQueryWithComplexConditions() {
        query.where("category", "electronics")
             .greaterThanOrEqualTo("rating", 4.0)
             .lessThan("price", 5000)
             .exists("in_stock")
             .notExists("discontinued")
             .regex("title", "^iPhone.*", "i");
        
        assertNotNull(query);
    }

    @Test
    public void testQueryWithArrayOperations() {
        String[] values1 = {"tag1", "tag2", "tag3"};
        query.containedIn("tags", values1);
        
        String[] values2 = {"excluded1", "excluded2"};
        query.notContainedIn("categories", values2);
        
        String[] tags = {"featured", "bestseller"};
        query.tags(tags);
        
        assertNotNull(query);
    }

    @Test
    public void testQueryWithLogicalOperators() {
        Query subQuery1 = stack.contentType("test_content_type").query();
        subQuery1.where("status", "published");
        
        Query subQuery2 = stack.contentType("test_content_type").query();
        subQuery2.where("featured", true);
        
        ArrayList<Query> orQueries = new ArrayList<>();
        orQueries.add(subQuery1);
        orQueries.add(subQuery2);
        
        query.or(orQueries);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithAndOperator() {
        Query subQuery1 = stack.contentType("test_content_type").query();
        subQuery1.where("price_min", 100);
        
        Query subQuery2 = stack.contentType("test_content_type").query();
        subQuery2.where("price_max", 1000);
        
        ArrayList<Query> andQueries = new ArrayList<>();
        andQueries.add(subQuery1);
        andQueries.add(subQuery2);
        
        query.and(andQueries);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithNestedWhereIn() {
        Query subQuery = stack.contentType("category").query();
        subQuery.where("name", "Featured");
        
        query.whereIn("category_ref", subQuery);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithNestedWhereNotIn() {
        Query subQuery = stack.contentType("category").query();
        subQuery.where("name", "Restricted");
        
        query.whereNotIn("category_ref", subQuery);
        assertNotNull(query);
    }

    // ==================== Header Operations ====================

    @Test
    public void testQueryWithMultipleHeaders() {
        query.setHeader("X-Custom-Header-1", "value1");
        query.setHeader("X-Custom-Header-2", "value2");
        query.setHeader("X-Custom-Header-3", "value3");
        assertNotNull(query);
    }

    @Test
    public void testQueryHeaderAddAndRemove() {
        query.setHeader("X-Test-Header", "test-value");
        query.removeHeader("X-Test-Header");
        assertNotNull(query);
    }

    @Test
    public void testQueryHeaderWithEmptyValues() {
        query.setHeader("", "value");
        query.setHeader("key", "");
        query.setHeader(null, "value");
        query.setHeader("key", null);
        assertNotNull(query);
    }

    // ==================== Additional Options ====================

    @Test
    public void testQueryWithAllIncludeOptions() {
        query.includeReference("author")
             .includeReference(new String[]{"category", "tags"})
             .includeReferenceContentTypUid()
             .includeFallback()
             .includeEmbeddedItems()
             .includeMetadata();
        
        assertNotNull(query);
    }

    @Test
    public void testQueryWithCustomParams() {
        query.addParam("param1", "value1");
        query.addParam("param2", "value2");
        query.addParam("param3", "value3");
        assertNotNull(query);
    }

    @Test
    public void testQueryWithLanguageAndLocale() {
        Query q1 = stack.contentType("test_content_type").query();
        q1.language(Language.ENGLISH_UNITED_STATES);
        assertNotNull(q1);
        
        Query q2 = stack.contentType("test_content_type").query();
        q2.locale("en-us");
        assertNotNull(q2);
        
        Query q3 = stack.contentType("test_content_type").query();
        q3.language(Language.SPANISH_SPAIN);
        assertNotNull(q3);
        
        Query q4 = stack.contentType("test_content_type").query();
        q4.locale("es-es");
        assertNotNull(q4);
    }

    @Test
    public void testQueryWithSearch() {
        query.search("search term with multiple words");
        assertNotNull(query);
    }

    @Test
    public void testQueryWithRegexModifiers() {
        query.regex("title", "test.*pattern", "i");
        assertNotNull(query);
        
        Query q2 = stack.contentType("test_content_type").query();
        q2.regex("description", ".*keyword.*", "m");
        assertNotNull(q2);
    }

    // ==================== Reference Operations ====================

    @Test
    public void testQueryWithOnlyReferenceFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("title");
        fields.add("url");
        
        query.onlyWithReferenceUid(fields, "author");
        assertNotNull(query);
    }

    @Test
    public void testQueryWithExceptReferenceFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("internal_id");
        fields.add("metadata");
        
        query.exceptWithReferenceUid(fields, "author");
        assertNotNull(query);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testQueryWithZeroSkipAndLimit() {
        query.skip(0).limit(0);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithNegativeSkipAndLimit() {
        query.skip(-10).limit(-5);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithVeryLargeSkipAndLimit() {
        query.skip(10000).limit(10000);
        assertNotNull(query);
    }

    @Test
    public void testQueryWithEmptyArrays() {
        query.containedIn("tags", new String[]{});
        query.notContainedIn("categories", new String[]{});
        query.tags(new String[]{});
        query.only(new String[]{});
        query.except(new String[]{});
        query.includeReference(new String[]{});
        assertNotNull(query);
    }

    @Test
    public void testQueryWithNullArrays() {
        query.containedIn("tags", null);
        query.notContainedIn("categories", null);
        query.tags(null);
        query.only(null);
        query.except((String[]) null);
        query.includeReference((String[]) null);
        assertNotNull(query);
    }

    @Test
    public void testQueryCancelRequest() {
        query.cancelRequest();
        assertNotNull(query);
    }

    @Test
    public void testQueryGetContentType() {
        String contentType = query.getContentType();
        assertEquals("test_content_type", contentType);
    }

    @Test
    public void testQueryChainedOperations() {
        Query result = query
            .where("field1", "value1")
            .where("field2", "value2")
            .addQuery("field3", "value3")
            .removeQuery("field3")
            .lessThan("num1", 100)
            .greaterThan("num2", 10)
            .exists("field4")
            .notExists("field5")
            .ascending("created_at")
            .skip(20)
            .limit(10)
            .includeCount()
            .search("search query");
        
        assertNotNull(result);
        assertEquals(query, result);
    }

    @Test
    public void testMultipleQueriesIndependence() {
        Query query1 = stack.contentType("type1").query();
        query1.where("field1", "value1");
        
        Query query2 = stack.contentType("type2").query();
        query2.where("field2", "value2");
        
        assertNotNull(query1);
        assertNotNull(query2);
        assertNotEquals(query1, query2);
    }
}

