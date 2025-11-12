package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for CachePolicy enum.
 */
@RunWith(RobolectricTestRunner.class)
public class TestCachePolicy {

    @Test
    public void testEnumValues() {
        CachePolicy[] values = CachePolicy.values();
        assertEquals("Should have 6 cache policy values", 6, values.length);
    }

    @Test
    public void testCacheOnly() {
        CachePolicy policy = CachePolicy.CACHE_ONLY;
        assertNotNull(policy);
        assertEquals("CACHE_ONLY", policy.name());
        assertEquals(0, policy.ordinal());
    }

    @Test
    public void testNetworkOnly() {
        CachePolicy policy = CachePolicy.NETWORK_ONLY;
        assertNotNull(policy);
        assertEquals("NETWORK_ONLY", policy.name());
        assertEquals(1, policy.ordinal());
    }

    @Test
    public void testCacheElseNetwork() {
        CachePolicy policy = CachePolicy.CACHE_ELSE_NETWORK;
        assertNotNull(policy);
        assertEquals("CACHE_ELSE_NETWORK", policy.name());
        assertEquals(2, policy.ordinal());
    }

    @Test
    public void testNetworkElseCache() {
        CachePolicy policy = CachePolicy.NETWORK_ELSE_CACHE;
        assertNotNull(policy);
        assertEquals("NETWORK_ELSE_CACHE", policy.name());
        assertEquals(3, policy.ordinal());
    }

    @Test
    public void testCacheThenNetwork() {
        CachePolicy policy = CachePolicy.CACHE_THEN_NETWORK;
        assertNotNull(policy);
        assertEquals("CACHE_THEN_NETWORK", policy.name());
        assertEquals(4, policy.ordinal());
    }

    @Test
    public void testIgnoreCache() {
        CachePolicy policy = CachePolicy.IGNORE_CACHE;
        assertNotNull(policy);
        assertEquals("IGNORE_CACHE", policy.name());
        assertEquals(5, policy.ordinal());
    }

    @Test
    public void testValueOf() {
        assertEquals(CachePolicy.CACHE_ONLY, CachePolicy.valueOf("CACHE_ONLY"));
        assertEquals(CachePolicy.NETWORK_ONLY, CachePolicy.valueOf("NETWORK_ONLY"));
        assertEquals(CachePolicy.CACHE_ELSE_NETWORK, CachePolicy.valueOf("CACHE_ELSE_NETWORK"));
        assertEquals(CachePolicy.NETWORK_ELSE_CACHE, CachePolicy.valueOf("NETWORK_ELSE_CACHE"));
        assertEquals(CachePolicy.CACHE_THEN_NETWORK, CachePolicy.valueOf("CACHE_THEN_NETWORK"));
        assertEquals(CachePolicy.IGNORE_CACHE, CachePolicy.valueOf("IGNORE_CACHE"));
    }

    @Test
    public void testEnumToString() {
        assertEquals("CACHE_ONLY", CachePolicy.CACHE_ONLY.toString());
        assertEquals("NETWORK_ONLY", CachePolicy.NETWORK_ONLY.toString());
        assertEquals("CACHE_ELSE_NETWORK", CachePolicy.CACHE_ELSE_NETWORK.toString());
        assertEquals("NETWORK_ELSE_CACHE", CachePolicy.NETWORK_ELSE_CACHE.toString());
        assertEquals("CACHE_THEN_NETWORK", CachePolicy.CACHE_THEN_NETWORK.toString());
        assertEquals("IGNORE_CACHE", CachePolicy.IGNORE_CACHE.toString());
    }

    @Test
    public void testEnumEquality() {
        CachePolicy policy1 = CachePolicy.CACHE_ONLY;
        CachePolicy policy2 = CachePolicy.CACHE_ONLY;
        assertEquals(policy1, policy2);
        assertSame(policy1, policy2);
    }

    @Test
    public void testEnumInequality() {
        assertNotEquals(CachePolicy.CACHE_ONLY, CachePolicy.NETWORK_ONLY);
        assertNotEquals(CachePolicy.CACHE_ELSE_NETWORK, CachePolicy.NETWORK_ELSE_CACHE);
        assertNotEquals(CachePolicy.CACHE_THEN_NETWORK, CachePolicy.IGNORE_CACHE);
    }

    @Test
    public void testSwitchStatement() {
        CachePolicy policy = CachePolicy.CACHE_ELSE_NETWORK;
        String result;

        switch (policy) {
            case CACHE_ONLY:
                result = "Cache Only";
                break;
            case NETWORK_ONLY:
                result = "Network Only";
                break;
            case CACHE_ELSE_NETWORK:
                result = "Cache Else Network";
                break;
            case NETWORK_ELSE_CACHE:
                result = "Network Else Cache";
                break;
            case CACHE_THEN_NETWORK:
                result = "Cache Then Network";
                break;
            case IGNORE_CACHE:
                result = "Ignore Cache";
                break;
            default:
                result = "Unknown";
                break;
        }

        assertEquals("Cache Else Network", result);
    }

    @Test
    public void testAllValuesIteration() {
        int count = 0;
        for (CachePolicy policy : CachePolicy.values()) {
            assertNotNull(policy);
            count++;
        }
        assertEquals(6, count);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidValueOf() {
        CachePolicy.valueOf("INVALID_POLICY");
    }

    @Test(expected = NullPointerException.class)
    public void testNullValueOf() {
        CachePolicy.valueOf(null);
    }

    @Test
    public void testPolicySemantics() {
        // Test that policies have expected semantics
        assertNotNull("CACHE_ONLY should exist", CachePolicy.CACHE_ONLY);
        assertNotNull("NETWORK_ONLY should exist", CachePolicy.NETWORK_ONLY);
        assertNotNull("CACHE_ELSE_NETWORK should exist", CachePolicy.CACHE_ELSE_NETWORK);
        assertNotNull("NETWORK_ELSE_CACHE should exist", CachePolicy.NETWORK_ELSE_CACHE);
        assertNotNull("CACHE_THEN_NETWORK should exist", CachePolicy.CACHE_THEN_NETWORK);
        assertNotNull("IGNORE_CACHE should exist", CachePolicy.IGNORE_CACHE);
    }

    @Test
    public void testEnumComparison() {
        assertTrue(CachePolicy.CACHE_ONLY.compareTo(CachePolicy.NETWORK_ONLY) < 0);
        assertTrue(CachePolicy.IGNORE_CACHE.compareTo(CachePolicy.CACHE_ONLY) > 0);
        assertEquals(0, CachePolicy.CACHE_ELSE_NETWORK.compareTo(CachePolicy.CACHE_ELSE_NETWORK));
    }
}

