package com.contentstack.sdk;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestQuery {

    private Context mockContext;
    private Stack stack;
    private Query query;
    private ContentType contentType;

    @Before
    public void setUp() throws Exception {
        mockContext = TestUtils.createMockContext();
        stack = Contentstack.stack(mockContext, 
            TestUtils.getTestApiKey(), 
            TestUtils.getTestDeliveryToken(), 
            TestUtils.getTestEnvironment());
        contentType = stack.contentType(TestUtils.getTestContentType());
        query = contentType.query();
        TestUtils.cleanupTestCache();
    }

    @After
    public void tearDown() {
        TestUtils.cleanupTestCache();
        query = null;
        contentType = null;
        stack = null;
        mockContext = null;
    }

    @Test
    public void testQueryCreation() {
        assertNotNull("Query should not be null", query);
    }

    @Test
    public void testWhere() {
        Query result = query.where("title", "Test Title");
        assertNotNull("Query should not be null after where", result);
        assertEquals("Should return same query instance", query, result);
    }

    @Test
    public void testWhereWithNullKey() {
        Query result = query.where(null, "value");
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testWhereWithNullValue() {
        Query result = query.where("key", null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testWhereWithMultipleConditions() {
        query.where("title", "Test")
             .where("status", "published")
             .where("count", 10);
        assertNotNull("Query should support chaining", query);
    }

    @Test
    public void testAddQuery() {
        Query result = query.addQuery("custom_param", "custom_value");
        assertNotNull("Query should not be null after addQuery", result);
        assertEquals("Should return same query instance", query, result);
    }

    @Test
    public void testRemoveQuery() {
        query.addQuery("param", "value");
        Query result = query.removeQuery("param");
        assertNotNull("Query should not be null after removeQuery", result);
    }

    @Test
    public void testRemoveNonExistentQuery() {
        Query result = query.removeQuery("non_existent");
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testAnd() {
        ArrayList<Query> queries = new ArrayList<>();
        Query subQuery1 = contentType.query().where("title", "Test1");
        Query subQuery2 = contentType.query().where("title", "Test2");
        queries.add(subQuery1);
        queries.add(subQuery2);
        
        Query result = query.and(queries);
        assertNotNull("Query should not be null after and", result);
    }

    @Test
    public void testAndWithEmptyList() {
        ArrayList<Query> queries = new ArrayList<>();
        Query result = query.and(queries);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testAndWithNullList() {
        Query result = query.and(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testOr() {
        ArrayList<Query> queries = new ArrayList<>();
        Query subQuery1 = contentType.query().where("title", "Test1");
        Query subQuery2 = contentType.query().where("title", "Test2");
        queries.add(subQuery1);
        queries.add(subQuery2);
        
        Query result = query.or(queries);
        assertNotNull("Query should not be null after or", result);
    }

    @Test
    public void testOrWithEmptyList() {
        ArrayList<Query> queries = new ArrayList<>();
        Query result = query.or(queries);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testLessThan() {
        Query result = query.lessThan("price", 100);
        assertNotNull("Query should not be null after lessThan", result);
    }

    @Test
    public void testLessThanWithNullKey() {
        Query result = query.lessThan(null, 100);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testLessThanOrEqualTo() {
        Query result = query.lessThanOrEqualTo("price", 100);
        assertNotNull("Query should not be null after lessThanOrEqualTo", result);
    }

    @Test
    public void testGreaterThan() {
        Query result = query.greaterThan("price", 50);
        assertNotNull("Query should not be null after greaterThan", result);
    }

    @Test
    public void testGreaterThanOrEqualTo() {
        Query result = query.greaterThanOrEqualTo("price", 50);
        assertNotNull("Query should not be null after greaterThanOrEqualTo", result);
    }

    @Test
    public void testNotEqualTo() {
        Query result = query.notEqualTo("status", "draft");
        assertNotNull("Query should not be null after notEqualTo", result);
    }

    @Test
    public void testContainedIn() {
        Object[] values = {"value1", "value2", "value3"};
        Query result = query.containedIn("field", values);
        assertNotNull("Query should not be null after containedIn", result);
    }

    @Test
    public void testContainedInWithEmptyArray() {
        Object[] values = {};
        Query result = query.containedIn("field", values);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testContainedInWithNullArray() {
        Query result = query.containedIn("field", null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testNotContainedIn() {
        Object[] values = {"value1", "value2"};
        Query result = query.notContainedIn("field", values);
        assertNotNull("Query should not be null after notContainedIn", result);
    }

    @Test
    public void testExists() {
        Query result = query.exists("field");
        assertNotNull("Query should not be null after exists", result);
    }

    @Test
    public void testExistsWithNullKey() {
        Query result = query.exists(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testNotExists() {
        Query result = query.notExists("field");
        assertNotNull("Query should not be null after notExists", result);
    }

    @Test
    public void testNotExistsWithNullKey() {
        Query result = query.notExists(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testIncludeReference() {
        Query result = query.includeReference("reference_field");
        assertNotNull("Query should not be null after includeReference", result);
    }

    @Test
    public void testIncludeReferenceWithArray() {
        String[] references = {"ref1", "ref2", "ref3"};
        Query result = query.includeReference(references);
        assertNotNull("Query should not be null after includeReference", result);
    }

    @Test
    public void testIncludeReferenceWithNullArray() {
        Query result = query.includeReference((String[]) null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testTags() {
        String[] tags = {"tag1", "tag2", "tag3"};
        Query result = query.tags(tags);
        assertNotNull("Query should not be null after tags", result);
    }

    @Test
    public void testTagsWithNullArray() {
        Query result = query.tags(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testAscending() {
        Query result = query.ascending("created_at");
        assertNotNull("Query should not be null after ascending", result);
    }

    @Test
    public void testAscendingWithNullKey() {
        Query result = query.ascending(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testDescending() {
        Query result = query.descending("created_at");
        assertNotNull("Query should not be null after descending", result);
    }

    @Test
    public void testDescendingWithNullKey() {
        Query result = query.descending(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testExceptWithArrayList() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("field1");
        fields.add("field2");
        Query result = query.except(fields);
        assertNotNull("Query should not be null after except", result);
    }

    @Test
    public void testExceptWithArray() {
        String[] fields = {"field1", "field2"};
        Query result = query.except(fields);
        assertNotNull("Query should not be null after except", result);
    }

    @Test
    public void testOnlyWithArray() {
        String[] fields = {"field1", "field2"};
        Query result = query.only(fields);
        assertNotNull("Query should not be null after only", result);
    }

    @Test
    public void testOnlyWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("field1");
        Query result = query.onlyWithReferenceUid(fields, "reference_uid");
        assertNotNull("Query should not be null after onlyWithReferenceUid", result);
    }

    @Test
    public void testExceptWithReferenceUid() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("field1");
        Query result = query.exceptWithReferenceUid(fields, "reference_uid");
        assertNotNull("Query should not be null after exceptWithReferenceUid", result);
    }

    @Test
    public void testCount() {
        Query result = query.count();
        assertNotNull("Query should not be null after count", result);
    }

    @Test
    public void testIncludeCount() {
        Query result = query.includeCount();
        assertNotNull("Query should not be null after includeCount", result);
    }

    @Test
    public void testIncludeContentType() {
        Query result = query.includeContentType();
        assertNotNull("Query should not be null after includeContentType", result);
    }

    @Test
    public void testSkip() {
        Query result = query.skip(10);
        assertNotNull("Query should not be null after skip", result);
    }

    @Test
    public void testSkipWithZero() {
        Query result = query.skip(0);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testSkipWithNegative() {
        Query result = query.skip(-1);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testLimit() {
        Query result = query.limit(20);
        assertNotNull("Query should not be null after limit", result);
    }

    @Test
    public void testLimitWithZero() {
        Query result = query.limit(0);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testLimitWithLargeNumber() {
        Query result = query.limit(1000);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testRegex() {
        Query result = query.regex("title", "^test");
        assertNotNull("Query should not be null after regex", result);
    }

    @Test
    public void testRegexWithModifiers() {
        Query result = query.regex("title", "^test", "i");
        assertNotNull("Query should not be null after regex with modifiers", result);
    }

    @Test
    public void testRegexWithNullKey() {
        Query result = query.regex(null, "pattern");
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testLocale() {
        Query result = query.locale("en-us");
        assertNotNull("Query should not be null after locale", result);
    }

    @Test
    public void testLocaleWithNullValue() {
        Query result = query.locale(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testSearch() {
        Query result = query.search("search_term");
        assertNotNull("Query should not be null after search", result);
    }

    @Test
    public void testSearchWithNullValue() {
        Query result = query.search(null);
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testSetCachePolicy() {
        Query result = query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull("Query should not be null after setCachePolicy", result);
    }

    @Test
    public void testSetCachePolicyWithAllPolicies() {
        CachePolicy[] policies = CachePolicy.values();
        for (CachePolicy policy : policies) {
            Query result = query.setCachePolicy(policy);
            assertNotNull("Query should not be null for policy " + policy, result);
        }
    }

    @Test
    public void testSetHeader() {
        query.setHeader("custom-header", "custom-value");
        assertNotNull("Query should not be null after setHeader", query);
    }

    @Test
    public void testRemoveHeader() {
        query.setHeader("custom-header", "custom-value");
        query.removeHeader("custom-header");
        assertNotNull("Query should not be null after removeHeader", query);
    }

    @Test
    public void testGetContentType() {
        String contentTypeName = query.getContentType();
        assertEquals("Content type name should match", TestUtils.getTestContentType(), contentTypeName);
    }

    @Test
    public void testAddParam() {
        Query result = query.addParam("key", "value");
        assertNotNull("Query should not be null after addParam", result);
    }

    @Test
    public void testAddParamWithNullKey() {
        Query result = query.addParam(null, "value");
        assertNotNull("Query should not be null", result);
    }

    @Test
    public void testIncludeReferenceContentTypUid() {
        Query result = query.includeReferenceContentTypUid();
        assertNotNull("Query should not be null after includeReferenceContentTypUid", result);
    }

    @Test
    public void testWhereIn() {
        Query subQuery = contentType.query().where("status", "published");
        Query result = query.whereIn("related", subQuery);
        assertNotNull("Query should not be null after whereIn", result);
    }

    @Test
    public void testWhereNotIn() {
        Query subQuery = contentType.query().where("status", "draft");
        Query result = query.whereNotIn("related", subQuery);
        assertNotNull("Query should not be null after whereNotIn", result);
    }

    @Test
    public void testIncludeFallback() {
        Query result = query.includeFallback();
        assertNotNull("Query should not be null after includeFallback", result);
    }

    @Test
    public void testIncludeEmbeddedItems() {
        Query result = query.includeEmbeddedItems();
        assertNotNull("Query should not be null after includeEmbeddedItems", result);
    }

    @Test
    public void testIncludeMetadata() {
        Query result = query.includeMetadata();
        assertNotNull("Query should not be null after includeMetadata", result);
    }

    @Test
    public void testCancelRequest() {
        query.cancelRequest();
        // Should not throw exception
        assertNotNull("Query should not be null after cancelRequest", query);
    }

    @Test
    public void testComplexQueryChaining() {
        Query result = query
            .where("title", "Test")
            .greaterThan("price", 10)
            .lessThan("price", 100)
            .includeReference("category")
            .includeCount()
            .skip(10)
            .limit(20)
            .ascending("created_at")
            .locale("en-us");
        
        assertNotNull("Query should support complex chaining", result);
        assertEquals("Should return same query instance", query, result);
    }

    @Test
    public void testQueryWithAllParameters() {
        Query result = query
            .where("field1", "value1")
            .notEqualTo("field2", "value2")
            .greaterThan("field3", 10)
            .lessThan("field4", 100)
            .containedIn("field5", new Object[]{"a", "b"})
            .exists("field6")
            .includeReference("ref1")
            .tags(new String[]{"tag1", "tag2"})
            .ascending("created_at")
            .skip(5)
            .limit(10)
            .includeCount()
            .includeContentType()
            .locale("en-us")
            .search("search_term")
            .setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        assertNotNull("Query with all parameters should not be null", result);
    }

    @Test
    public void testMultipleWhereConditions() {
        query.where("field1", "value1")
             .where("field2", "value2")
             .where("field3", "value3")
             .where("field4", "value4");
        assertNotNull("Query with multiple where conditions should not be null", query);
    }

    @Test
    public void testMultipleIncludeReferences() {
        query.includeReference("ref1")
             .includeReference("ref2")
             .includeReference("ref3");
        assertNotNull("Query with multiple includes should not be null", query);
    }

    @Test
    public void testRegexWithDifferentPatterns() {
        String[] patterns = {"^test", "test$", ".*test.*", "^[a-z]+$"};
        for (String pattern : patterns) {
            Query result = query.regex("field", pattern);
            assertNotNull("Query should not be null for pattern " + pattern, result);
        }
    }

    @Test
    public void testLocaleWithDifferentCodes() {
        String[] locales = {"en-us", "fr-fr", "de-de", "es-es", "ja-jp"};
        for (String locale : locales) {
            Query result = query.locale(locale);
            assertNotNull("Query should not be null for locale " + locale, result);
        }
    }
}

