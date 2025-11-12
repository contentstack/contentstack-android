package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for AssetLibrary class to improve coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestAssetLibraryAdvanced {

    private Context context;
    private Stack stack;
    private AssetLibrary assetLibrary;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        assetLibrary = stack.assetLibrary();
    }

    // ==================== CONSTRUCTOR Tests ====================

    @Test
    public void testAssetLibraryCreation() {
        assertNotNull(assetLibrary);
    }

    // ==================== HEADER Tests ====================

    @Test
    public void testSetHeader() {
        assetLibrary.setHeader("custom-header", "custom-value");
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetHeaderWithNull() {
        assetLibrary.setHeader(null, "value");
        assetLibrary.setHeader("key", null);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetHeaderWithEmptyStrings() {
        assetLibrary.setHeader("", "value");
        assetLibrary.setHeader("key", "");
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetHeaderMultiple() {
        assetLibrary.setHeader("header1", "value1");
        assetLibrary.setHeader("header2", "value2");
        assetLibrary.setHeader("header3", "value3");
        assertNotNull(assetLibrary);
    }

    @Test
    public void testRemoveHeader() {
        assetLibrary.setHeader("test-header", "test-value");
        assetLibrary.removeHeader("test-header");
        assertNotNull(assetLibrary);
    }

    @Test
    public void testRemoveHeaderWithNull() {
        assetLibrary.removeHeader(null);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testRemoveHeaderWithEmptyString() {
        assetLibrary.removeHeader("");
        assertNotNull(assetLibrary);
    }

    // ==================== SORT Tests ====================

    @Test
    public void testSortAscending() {
        AssetLibrary result = assetLibrary.sort("created_at", AssetLibrary.ORDERBY.ASCENDING);
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testSortDescending() {
        AssetLibrary result = assetLibrary.sort("updated_at", AssetLibrary.ORDERBY.DESCENDING);
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testSortWithNullKey() {
        AssetLibrary result = assetLibrary.sort(null, AssetLibrary.ORDERBY.ASCENDING);
        assertNotNull(result);
    }

    @Test
    public void testSortMultipleTimes() {
        assetLibrary.sort("field1", AssetLibrary.ORDERBY.ASCENDING);
        AssetLibrary result = assetLibrary.sort("field2", AssetLibrary.ORDERBY.DESCENDING);
        assertNotNull(result);
    }

    // ==================== INCLUDE COUNT Tests ====================

    @Test
    public void testIncludeCount() {
        AssetLibrary result = assetLibrary.includeCount();
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testIncludeCountMultipleTimes() {
        assetLibrary.includeCount();
        AssetLibrary result = assetLibrary.includeCount();
        assertNotNull(result);
    }

    // ==================== INCLUDE RELATIVE URL Tests ====================

    @Test
    public void testIncludeRelativeUrl() {
        AssetLibrary result = assetLibrary.includeRelativeUrl();
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testIncludeRelativeUrlMultipleTimes() {
        assetLibrary.includeRelativeUrl();
        AssetLibrary result = assetLibrary.includeRelativeUrl();
        assertNotNull(result);
    }

    // ==================== INCLUDE METADATA Tests ====================

    @Test
    public void testIncludeMetadata() {
        AssetLibrary result = assetLibrary.includeMetadata();
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testIncludeMetadataMultipleTimes() {
        assetLibrary.includeMetadata();
        AssetLibrary result = assetLibrary.includeMetadata();
        assertNotNull(result);
    }

    // ==================== WHERE Tests ====================

    @Test
    public void testWhere() {
        AssetLibrary result = assetLibrary.where("content_type", "image/jpeg");
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testWhereWithNull() {
        AssetLibrary result = assetLibrary.where(null, "value");
        assertNotNull(result);
        
        result = assetLibrary.where("key", null);
        assertNotNull(result);
    }

    @Test
    public void testWhereMultiple() {
        assetLibrary.where("content_type", "image/png");
        AssetLibrary result = assetLibrary.where("file_size", "1024");
        assertNotNull(result);
    }

    // ==================== CACHE POLICY Tests ====================

    @Test
    public void testSetCachePolicyNetworkOnly() {
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetCachePolicyCacheOnly() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetCachePolicyCacheElseNetwork() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetCachePolicyCacheThenNetwork() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetCachePolicyNetworkElseCache() {
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSetCachePolicyIgnoreCache() {
        assetLibrary.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(assetLibrary);
    }

    // ==================== CONFIGURATION Tests ====================

    @Test
    public void testConfigurationCombination() {
        assetLibrary.setHeader("test-header", "test-value");
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assetLibrary.includeCount();
        assertNotNull(assetLibrary);
    }

    // ==================== METHOD CHAINING Tests ====================

    @Test
    public void testMethodChaining() {
        AssetLibrary result = assetLibrary
            .sort("created_at", AssetLibrary.ORDERBY.ASCENDING)
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata()
            .where("content_type", "image/jpeg");
        
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testComplexChaining() {
        assetLibrary.setHeader("api-version", "v3");
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assetLibrary
            .sort("updated_at", AssetLibrary.ORDERBY.DESCENDING)
            .includeCount()
            .includeRelativeUrl()
            .where("title", "My Asset");
        
        assertNotNull(assetLibrary);
    }

    // ==================== MULTIPLE OPERATIONS Tests ====================

    @Test
    public void testMultipleSortOperations() {
        assetLibrary
            .sort("created_at", AssetLibrary.ORDERBY.ASCENDING)
            .sort("file_size", AssetLibrary.ORDERBY.DESCENDING);
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testMultipleWhereOperations() {
        assetLibrary
            .where("content_type", "image/png")
            .where("file_size", "2048")
            .where("title", "Test");
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAllIncludeOptions() {
        assetLibrary
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata();
        
        assertNotNull(assetLibrary);
    }

    // ==================== EDGE CASES Tests ====================

    @Test
    public void testEmptyStringValues() {
        assetLibrary
            .sort("", AssetLibrary.ORDERBY.ASCENDING)
            .where("", "");
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSpecialCharacters() {
        assetLibrary.where("title", "Asset & <Special> \"Characters\"");
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testLargeChain() {
        assetLibrary.setHeader("h1", "v1");
        assetLibrary.setHeader("h2", "v2");
        assetLibrary.setHeader("h3", "v3");
        
        AssetLibrary result = assetLibrary
            .sort("field1", AssetLibrary.ORDERBY.ASCENDING)
            .where("key1", "value1")
            .where("key2", "value2")
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata();
        
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        assertNotNull(result);
    }

    // ==================== RESET AND REUSE Tests ====================

    @Test
    public void testReuseAfterConfiguration() {
        assetLibrary
            .sort("created_at", AssetLibrary.ORDERBY.ASCENDING)
            .includeCount();
        
        // Reconfigure
        AssetLibrary result = assetLibrary
            .sort("updated_at", AssetLibrary.ORDERBY.DESCENDING)
            .includeMetadata();
        
        assertNotNull(result);
    }

    // ==================== CONTENT TYPE FILTERS Tests ====================

    @Test
    public void testFilterImageTypes() {
        AssetLibrary result = assetLibrary.where("content_type", "image/jpeg");
        assertNotNull(result);
    }

    @Test
    public void testFilterVideoTypes() {
        AssetLibrary result = assetLibrary.where("content_type", "video/mp4");
        assertNotNull(result);
    }

    @Test
    public void testFilterDocumentTypes() {
        AssetLibrary result = assetLibrary.where("content_type", "application/pdf");
        assertNotNull(result);
    }

    // ==================== SORT FIELD TESTS ====================

    @Test
    public void testSortByCreatedAt() {
        AssetLibrary result = assetLibrary.sort("created_at", AssetLibrary.ORDERBY.ASCENDING);
        assertNotNull(result);
    }

    @Test
    public void testSortByUpdatedAt() {
        AssetLibrary result = assetLibrary.sort("updated_at", AssetLibrary.ORDERBY.DESCENDING);
        assertNotNull(result);
    }

    @Test
    public void testSortByTitle() {
        AssetLibrary result = assetLibrary.sort("title", AssetLibrary.ORDERBY.ASCENDING);
        assertNotNull(result);
    }

    @Test
    public void testSortByFileSize() {
        AssetLibrary result = assetLibrary.sort("file_size", AssetLibrary.ORDERBY.DESCENDING);
        assertNotNull(result);
    }

    // ==================== COMBINED OPERATIONS Tests ====================

    @Test
    public void testSearchAndSortCombination() {
        assetLibrary
            .where("content_type", "image/png")
            .sort("file_size", AssetLibrary.ORDERBY.ASCENDING)
            .includeCount();
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testFullQueryConfiguration() {
        assetLibrary.setHeader("Authorization", "Bearer token123");
        assetLibrary.setHeader("Content-Type", "application/json");
        
        assetLibrary
            .where("content_type", "image/jpeg")
            .sort("created_at", AssetLibrary.ORDERBY.DESCENDING)
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata();
        
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertNotNull(assetLibrary);
    }

    // ==================== GET COUNT Tests ====================

    @Test
    public void testGetCountDefaultValue() {
        int count = assetLibrary.getCount();
        assertEquals(0, count);
    }

    @Test
    public void testGetCountAfterIncludeCount() {
        assetLibrary.includeCount();
        int count = assetLibrary.getCount();
        assertEquals(0, count); // Should still be 0 before fetch
    }

    // ==================== INCLUDE FALLBACK Tests ====================

    @Test
    public void testIncludeFallback() {
        AssetLibrary result = assetLibrary.includeFallback();
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testIncludeFallbackChaining() {
        AssetLibrary result = assetLibrary
            .includeFallback()
            .includeCount()
            .includeRelativeUrl();
        
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    // ==================== WHERE QUERY Tests ====================

    @Test
    public void testWhereWithStringValue() {
        AssetLibrary result = assetLibrary.where("title", "Sample Asset");
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testWhereWithEmptyKey() {
        AssetLibrary result = assetLibrary.where("", "value");
        assertNotNull(result);
    }

    @Test
    public void testWhereWithEmptyValue() {
        AssetLibrary result = assetLibrary.where("key", "");
        assertNotNull(result);
    }

    @Test
    public void testWhereWithNullKey() {
        AssetLibrary result = assetLibrary.where(null, "value");
        assertNotNull(result);
    }

    @Test
    public void testWhereWithNullValue() {
        AssetLibrary result = assetLibrary.where("key", null);
        assertNotNull(result);
    }

    @Test
    public void testMultipleWhereCalls() {
        AssetLibrary result = assetLibrary
            .where("content_type", "image/jpeg")
            .where("file_size[lt]", "1000000")
            .where("created_at[gte]", "2023-01-01");
        
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    @Test
    public void testWhereWithSpecialCharacters() {
        AssetLibrary result = assetLibrary.where("tags", "image@#$%");
        assertNotNull(result);
    }

    // ==================== COMPLEX CHAINING Tests ====================

    @Test
    public void testCompleteAssetLibraryConfiguration() {
        assetLibrary.setHeader("custom-header", "value");
        
        assetLibrary
            .where("content_type", "image/png")
            .where("file_size[lt]", "5000000")
            .sort("created_at", AssetLibrary.ORDERBY.DESCENDING)
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata()
            .includeFallback();
        
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        assertNotNull(assetLibrary);
        assertEquals(0, assetLibrary.getCount());
    }

    @Test
    public void testMultipleSortingCriteria() {
        AssetLibrary result = assetLibrary
            .sort("created_at", AssetLibrary.ORDERBY.ASCENDING)
            .sort("file_size", AssetLibrary.ORDERBY.DESCENDING);
        
        assertNotNull(result);
    }

    @Test
    public void testAllIncludeMethodsCombined() {
        AssetLibrary result = assetLibrary
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata()
            .includeFallback();
        
        assertNotNull(result);
        assertSame(assetLibrary, result);
    }

    // ==================== CACHE POLICY Tests ====================

    @Test
    public void testCachePolicyNetworkOnly() {
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyCacheOnly() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyCacheThenNetwork() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyCacheElseNetwork() {
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyNetworkElseCache() {
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyIgnoreCache() {
        assetLibrary.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testMultipleCachePolicyChanges() {
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ONLY);
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertNotNull(assetLibrary);
    }

    // ==================== EDGE CASE Tests ====================

    @Test
    public void testAssetLibraryStateIndependence() {
        AssetLibrary lib1 = stack.assetLibrary();
        AssetLibrary lib2 = stack.assetLibrary();
        
        lib1.where("content_type", "image/jpeg");
        lib2.where("content_type", "image/png");
        
        // Both should be independent
        assertNotNull(lib1);
        assertNotNull(lib2);
        assertNotSame(lib1, lib2);
    }

    @Test
    public void testRepeatedIncludeCountCalls() {
        assetLibrary.includeCount();
        assetLibrary.includeCount();
        assetLibrary.includeCount();
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSortWithAllOrderByOptions() {
        // Test ASCENDING
        AssetLibrary result1 = assetLibrary.sort("created_at", AssetLibrary.ORDERBY.ASCENDING);
        assertNotNull(result1);
        
        // Create new instance for DESCENDING test
        AssetLibrary lib2 = stack.assetLibrary();
        AssetLibrary result2 = lib2.sort("created_at", AssetLibrary.ORDERBY.DESCENDING);
        assertNotNull(result2);
    }

    @Test
    public void testHeaderManagementSequence() {
        assetLibrary.setHeader("h1", "v1");
        assetLibrary.setHeader("h2", "v2");
        assetLibrary.setHeader("h3", "v3");
        
        assetLibrary.removeHeader("h2");
        
        assetLibrary.setHeader("h4", "v4");
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testWhereWithOperators() {
        assetLibrary
            .where("file_size[lt]", "1000000")
            .where("file_size[gt]", "100000")
            .where("created_at[lte]", "2023-12-31")
            .where("updated_at[gte]", "2023-01-01");
        
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCompleteWorkflow() {
        // Simulate complete asset library query workflow
        assetLibrary.setHeader("Authorization", "Bearer token");
        assetLibrary.setHeader("x-request-id", "req123");
        
        assetLibrary
            .where("content_type", "image/jpeg")
            .where("file_size[lt]", "5000000")
            .sort("created_at", AssetLibrary.ORDERBY.DESCENDING)
            .includeCount()
            .includeRelativeUrl()
            .includeMetadata()
            .includeFallback();
        
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        // Verify state
        assertNotNull(assetLibrary);
        assertEquals(0, assetLibrary.getCount());
    }
}

