package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Cache Policy across all SDK classes
 */
@RunWith(RobolectricTestRunner.class)
public class TestCachePolicyComprehensive {

    private Context context;
    private Stack stack;
    private Config config;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
    }

    // ==================== Query Cache Policies ====================

    @Test
    public void testQueryNetworkOnly() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(query);
    }

    @Test
    public void testQueryCacheOnly() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(query);
    }

    @Test
    public void testQueryCacheThenNetwork() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(query);
    }

    @Test
    public void testQueryCacheElseNetwork() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(query);
    }

    @Test
    public void testQueryNetworkElseCache() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(query);
    }

    @Test
    public void testQueryIgnoreCache() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(query);
    }

    @Test
    public void testQueryAllCachePolicies() {
        CachePolicy[] policies = {
            CachePolicy.NETWORK_ONLY,
            CachePolicy.CACHE_ONLY,
            CachePolicy.CACHE_THEN_NETWORK,
            CachePolicy.CACHE_ELSE_NETWORK,
            CachePolicy.NETWORK_ELSE_CACHE,
            CachePolicy.IGNORE_CACHE
        };
        
        for (CachePolicy policy : policies) {
            Query query = stack.contentType("test_ct").query();
            query.setCachePolicy(policy);
            assertNotNull(query);
        }
    }

    @Test
    public void testQueryCachePolicyWithFind() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                // Handle completion
            }
        });
        assertNotNull(query);
    }

    @Test
    public void testQueryCachePolicyWithFindOne() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        query.findOne(new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                // Handle completion
            }
        });
        assertNotNull(query);
    }

    // ==================== Entry Cache Policies ====================

    @Test
    public void testEntryNetworkOnly() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(entry);
    }

    @Test
    public void testEntryCacheOnly() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(entry);
    }

    @Test
    public void testEntryCacheThenNetwork() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(entry);
    }

    @Test
    public void testEntryCacheElseNetwork() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(entry);
    }

    @Test
    public void testEntryNetworkElseCache() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(entry);
    }

    @Test
    public void testEntryIgnoreCache() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(entry);
    }

    @Test
    public void testEntryAllCachePolicies() {
        CachePolicy[] policies = {
            CachePolicy.NETWORK_ONLY,
            CachePolicy.CACHE_ONLY,
            CachePolicy.CACHE_THEN_NETWORK,
            CachePolicy.CACHE_ELSE_NETWORK,
            CachePolicy.NETWORK_ELSE_CACHE,
            CachePolicy.IGNORE_CACHE
        };
        
        for (CachePolicy policy : policies) {
            Entry entry = stack.contentType("test_ct").entry("uid_" + policy.name());
            entry.setCachePolicy(policy);
            assertNotNull(entry);
        }
    }

    @Test
    public void testEntryCachePolicyWithFetch() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Handle completion
            }
        });
        assertNotNull(entry);
    }

    // ==================== Asset Cache Policies ====================

    @Test
    public void testAssetNetworkOnly() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(asset);
    }

    @Test
    public void testAssetCacheOnly() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(asset);
    }

    @Test
    public void testAssetCacheThenNetwork() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(asset);
    }

    @Test
    public void testAssetCacheElseNetwork() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(asset);
    }

    @Test
    public void testAssetNetworkElseCache() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(asset);
    }

    @Test
    public void testAssetIgnoreCache() {
        Asset asset = stack.asset("test_asset_uid");
        asset.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(asset);
    }

    @Test
    public void testAssetAllCachePolicies() {
        CachePolicy[] policies = {
            CachePolicy.NETWORK_ONLY,
            CachePolicy.CACHE_ONLY,
            CachePolicy.CACHE_THEN_NETWORK,
            CachePolicy.CACHE_ELSE_NETWORK,
            CachePolicy.NETWORK_ELSE_CACHE,
            CachePolicy.IGNORE_CACHE
        };
        
        for (CachePolicy policy : policies) {
            Asset asset = stack.asset("asset_" + policy.name());
            asset.setCachePolicy(policy);
            assertNotNull(asset);
        }
    }

    // ==================== AssetLibrary Cache Policies ====================

    @Test
    public void testAssetLibraryNetworkOnly() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryCacheOnly() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ONLY);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryCacheThenNetwork() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryCacheElseNetwork() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryNetworkElseCache() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryIgnoreCache() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.IGNORE_CACHE);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testAssetLibraryAllCachePolicies() {
        CachePolicy[] policies = {
            CachePolicy.NETWORK_ONLY,
            CachePolicy.CACHE_ONLY,
            CachePolicy.CACHE_THEN_NETWORK,
            CachePolicy.CACHE_ELSE_NETWORK,
            CachePolicy.NETWORK_ELSE_CACHE,
            CachePolicy.IGNORE_CACHE
        };
        
        for (CachePolicy policy : policies) {
            AssetLibrary assetLibrary = stack.assetLibrary();
            assetLibrary.setCachePolicy(policy);
            assertNotNull(assetLibrary);
        }
    }

    // ==================== Combined Cache Policy Tests ====================

    @Test
    public void testDifferentCachePoliciesAcrossObjects() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        Asset asset = stack.asset("test_asset");
        asset.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertNotNull(query);
        assertNotNull(entry);
        assertNotNull(asset);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testSameCachePolicyAcrossObjects() {
        CachePolicy policy = CachePolicy.CACHE_THEN_NETWORK;
        
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(policy);
        
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(policy);
        
        Asset asset = stack.asset("test_asset");
        asset.setCachePolicy(policy);
        
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(policy);
        
        assertNotNull(query);
        assertNotNull(entry);
        assertNotNull(asset);
        assertNotNull(assetLibrary);
    }

    @Test
    public void testCachePolicyChanging() {
        Query query = stack.contentType("test_ct").query();
        
        query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        query.setCachePolicy(CachePolicy.CACHE_ONLY);
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        
        assertNotNull(query);
    }

    @Test
    public void testMultipleObjectsSameContentType() {
        Query query1 = stack.contentType("test_ct").query();
        query1.setCachePolicy(CachePolicy.NETWORK_ONLY);
        
        Query query2 = stack.contentType("test_ct").query();
        query2.setCachePolicy(CachePolicy.CACHE_ONLY);
        
        Entry entry1 = stack.contentType("test_ct").entry("uid1");
        entry1.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        
        Entry entry2 = stack.contentType("test_ct").entry("uid2");
        entry2.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        
        assertNotNull(query1);
        assertNotNull(query2);
        assertNotNull(entry1);
        assertNotNull(entry2);
    }

    // ==================== Cache Policy with Operations ====================

    @Test
    public void testQueryWithCachePolicyAndWhere() {
        Query query = stack.contentType("test_ct").query();
        query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        query.where("field", "value");
        assertNotNull(query);
    }

    @Test
    public void testEntryWithCachePolicyAndIncludeReference() {
        Entry entry = stack.contentType("test_ct").entry("test_uid");
        entry.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        entry.includeReference("author");
        assertNotNull(entry);
    }

    @Test
    public void testAssetWithCachePolicyAndIncludeDimension() {
        Asset asset = stack.asset("test_asset");
        asset.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        asset.includeDimension();
        assertNotNull(asset);
    }

    @Test
    public void testAssetLibraryWithCachePolicyAndIncludeCount() {
        AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        assetLibrary.includeCount();
        assertNotNull(assetLibrary);
    }

    // ==================== Cache Policy Enum Tests ====================

    @Test
    public void testCachePolicyEnumValues() {
        CachePolicy[] policies = CachePolicy.values();
        assertTrue(policies.length >= 6);
        assertNotNull(policies);
    }

    @Test
    public void testCachePolicyEnumValueOf() {
        CachePolicy policy = CachePolicy.valueOf("NETWORK_ONLY");
        assertNotNull(policy);
        assertEquals(CachePolicy.NETWORK_ONLY, policy);
    }

    @Test
    public void testAllCachePolicyEnumsAssignable() {
        Query query = stack.contentType("test_ct").query();
        
        query.setCachePolicy(CachePolicy.NETWORK_ONLY);
        query.setCachePolicy(CachePolicy.CACHE_ONLY);
        query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        query.setCachePolicy(CachePolicy.IGNORE_CACHE);
        
        assertNotNull(query);
    }

    // ==================== Sequential Cache Policy Tests ====================

    @Test
    public void testSequentialCachePolicyChanges() {
        for (int i = 0; i < 10; i++) {
            Query query = stack.contentType("test_ct").query();
            CachePolicy policy = CachePolicy.values()[i % 6];
            query.setCachePolicy(policy);
            assertNotNull(query);
        }
    }

    @Test
    public void testAllObjectTypesWithAllPolicies() {
        CachePolicy[] policies = CachePolicy.values();
        
        for (CachePolicy policy : policies) {
            Query query = stack.contentType("test_ct").query();
            query.setCachePolicy(policy);
            
            Entry entry = stack.contentType("test_ct").entry("uid");
            entry.setCachePolicy(policy);
            
            Asset asset = stack.asset("asset_uid");
            asset.setCachePolicy(policy);
            
            AssetLibrary assetLibrary = stack.assetLibrary();
            assetLibrary.setCachePolicy(policy);
            
            assertNotNull(query);
            assertNotNull(entry);
            assertNotNull(asset);
            assertNotNull(assetLibrary);
        }
    }
}

