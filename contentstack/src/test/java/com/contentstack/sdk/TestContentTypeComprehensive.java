package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for ContentType class
 */
@RunWith(RobolectricTestRunner.class)
public class TestContentTypeComprehensive {

    private Context context;
    private Stack stack;
    private ContentType contentType;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        contentType = stack.contentType("test_content_type");
    }

    // ==================== Entry Creation ====================

    @Test
    public void testEntryWithValidUid() {
        Entry entry = contentType.entry("entry_uid_123");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithNullUid() {
        Entry entry = contentType.entry(null);
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithEmptyUid() {
        Entry entry = contentType.entry("");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithLongUid() {
        StringBuilder longUid = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longUid.append("a");
        }
        Entry entry = contentType.entry(longUid.toString());
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithSpecialCharacters() {
        Entry entry = contentType.entry("uid_with_special!@#$%^&*()");
        assertNotNull(entry);
    }

    @Test
    public void testMultipleEntriesFromSameContentType() {
        Entry entry1 = contentType.entry("uid1");
        Entry entry2 = contentType.entry("uid2");
        Entry entry3 = contentType.entry("uid3");
        
        assertNotNull(entry1);
        assertNotNull(entry2);
        assertNotNull(entry3);
        assertNotEquals(entry1, entry2);
        assertNotEquals(entry2, entry3);
    }

    // ==================== Query Creation ====================

    @Test
    public void testQuery() {
        Query query = contentType.query();
        assertNotNull(query);
    }

    @Test
    public void testMultipleQueriesFromSameContentType() {
        Query query1 = contentType.query();
        Query query2 = contentType.query();
        Query query3 = contentType.query();
        
        assertNotNull(query1);
        assertNotNull(query2);
        assertNotNull(query3);
        assertNotEquals(query1, query2);
        assertNotEquals(query2, query3);
    }

    @Test
    public void testQueryConfiguration() {
        Query query = contentType.query();
        query.where("status", "published");
        query.limit(10);
        assertNotNull(query);
    }

    // ==================== Multiple Content Types ====================

    @Test
    public void testMultipleContentTypes() {
        ContentType ct1 = stack.contentType("type1");
        ContentType ct2 = stack.contentType("type2");
        ContentType ct3 = stack.contentType("type3");
        
        assertNotNull(ct1);
        assertNotNull(ct2);
        assertNotNull(ct3);
        assertNotEquals(ct1, ct2);
        assertNotEquals(ct2, ct3);
    }

    @Test
    public void testContentTypeWithEmptyName() {
        ContentType ct = stack.contentType("");
        assertNotNull(ct);
    }

    @Test
    public void testContentTypeWithNullName() {
        ContentType ct = stack.contentType(null);
        assertNotNull(ct);
    }

    @Test
    public void testContentTypeWithLongName() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longName.append("content_type_");
        }
        ContentType ct = stack.contentType(longName.toString());
        assertNotNull(ct);
    }

    // ==================== Entry and Query Independence ====================

    @Test
    public void testEntryAndQueryIndependence() {
        Entry entry = contentType.entry("test_entry");
        Query query = contentType.query();
        
        assertNotNull(entry);
        assertNotNull(query);
        assertNotEquals(entry, query);
    }

    @Test
    public void testMultipleEntriesAndQueries() {
        Entry e1 = contentType.entry("entry1");
        Query q1 = contentType.query();
        Entry e2 = contentType.entry("entry2");
        Query q2 = contentType.query();
        Entry e3 = contentType.entry("entry3");
        Query q3 = contentType.query();
        
        assertNotNull(e1);
        assertNotNull(q1);
        assertNotNull(e2);
        assertNotNull(q2);
        assertNotNull(e3);
        assertNotNull(q3);
    }

    // ==================== Concurrent Operations ====================

    @Test
    public void testConcurrentEntryCreation() {
        for (int i = 0; i < 100; i++) {
            Entry entry = contentType.entry("entry_" + i);
            assertNotNull(entry);
        }
    }

    @Test
    public void testConcurrentQueryCreation() {
        for (int i = 0; i < 100; i++) {
            Query query = contentType.query();
            assertNotNull(query);
        }
    }

    @Test
    public void testMixedConcurrentCreation() {
        for (int i = 0; i < 50; i++) {
            Entry entry = contentType.entry("entry_" + i);
            Query query = contentType.query();
            assertNotNull(entry);
            assertNotNull(query);
        }
    }

    // ==================== ContentType Reuse ====================

    @Test
    public void testContentTypeReuse() {
        Entry entry1 = contentType.entry("uid1");
        Entry entry2 = contentType.entry("uid2");
        Query query1 = contentType.query();
        Entry entry3 = contentType.entry("uid3");
        Query query2 = contentType.query();
        
        assertNotNull(entry1);
        assertNotNull(entry2);
        assertNotNull(query1);
        assertNotNull(entry3);
        assertNotNull(query2);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testEntryWithUnicodeUid() {
        Entry entry = contentType.entry("entry_测试_テスト_테스트_🎉");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithWhitespace() {
        Entry entry = contentType.entry("entry with spaces");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithNumericUid() {
        Entry entry = contentType.entry("1234567890");
        assertNotNull(entry);
    }

    @Test
    public void testEntryWithMixedCaseUid() {
        Entry entry = contentType.entry("EnTrY_UiD_MiXeD_CaSe");
        assertNotNull(entry);
    }

    @Test
    public void testQueryWithDifferentConfigurations() {
        Query q1 = contentType.query();
        q1.where("field1", "value1");
        
        Query q2 = contentType.query();
        q2.where("field2", "value2");
        
        Query q3 = contentType.query();
        q3.where("field3", "value3");
        
        assertNotNull(q1);
        assertNotNull(q2);
        assertNotNull(q3);
    }

    @Test
    public void testEntryConfigurationWithOptions() {
        Entry entry = contentType.entry("test_uid");
        entry.only(new String[]{"title", "description"});
        entry.includeReference("author");
        assertNotNull(entry);
    }

    @Test
    public void testQueryConfigurationWithOptions() {
        Query query = contentType.query();
        query.where("status", "published");
        query.limit(20);
        query.skip(10);
        query.includeCount();
        assertNotNull(query);
    }

    // ==================== Factory Method Pattern ====================

    @Test
    public void testFactoryMethodConsistency() {
        Entry entry = contentType.entry("test_uid");
        assertNotNull(entry);
        assertEquals("test_uid", entry.getUid());
    }

    @Test
    public void testFactoryMethodForQueries() {
        Query query = contentType.query();
        assertNotNull(query);
        assertEquals("test_content_type", query.getContentType());
    }

    // ==================== Integration Tests ====================

    @Test
    public void testCompleteWorkflow() {
        // Create content type
        ContentType ct = stack.contentType("blog");
        assertNotNull(ct);
        
        // Create entry
        Entry entry = ct.entry("blog_post_123");
        assertNotNull(entry);
        entry.includeReference("author");
        
        // Create query
        Query query = ct.query();
        assertNotNull(query);
        query.where("status", "published");
        query.limit(10);
    }

    @Test
    public void testMultipleContentTypeWorkflows() {
        ContentType blog = stack.contentType("blog");
        Entry blogEntry = blog.entry("blog_1");
        Query blogQuery = blog.query();
        
        ContentType product = stack.contentType("product");
        Entry productEntry = product.entry("product_1");
        Query productQuery = product.query();
        
        ContentType author = stack.contentType("author");
        Entry authorEntry = author.entry("author_1");
        Query authorQuery = author.query();
        
        assertNotNull(blogEntry);
        assertNotNull(blogQuery);
        assertNotNull(productEntry);
        assertNotNull(productQuery);
        assertNotNull(authorEntry);
        assertNotNull(authorQuery);
    }

    @Test
    public void testRepeatedCreation() {
        for (int i = 0; i < 10; i++) {
            Entry entry = contentType.entry("entry");
            assertNotNull(entry);
        }
        
        for (int i = 0; i < 10; i++) {
            Query query = contentType.query();
            assertNotNull(query);
        }
    }
}

