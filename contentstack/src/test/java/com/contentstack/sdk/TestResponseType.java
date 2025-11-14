package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for ResponseType enum.
 */
@RunWith(RobolectricTestRunner.class)
public class TestResponseType {

    // ========== ENUM VALUES TESTS ==========

    @Test
    public void testEnumValues() {
        ResponseType[] types = ResponseType.values();
        assertNotNull(types);
        assertEquals(3, types.length);
    }

    @Test
    public void testNetworkTypeExists() {
        ResponseType type = ResponseType.NETWORK;
        assertNotNull(type);
        assertEquals("NETWORK", type.name());
    }

    @Test
    public void testCacheTypeExists() {
        ResponseType type = ResponseType.CACHE;
        assertNotNull(type);
        assertEquals("CACHE", type.name());
    }

    @Test
    public void testUnknownTypeExists() {
        ResponseType type = ResponseType.UNKNOWN;
        assertNotNull(type);
        assertEquals("UNKNOWN", type.name());
    }

    // ========== VALUE OF TESTS ==========

    @Test
    public void testValueOfNetwork() {
        ResponseType type = ResponseType.valueOf("NETWORK");
        assertEquals(ResponseType.NETWORK, type);
    }

    @Test
    public void testValueOfCache() {
        ResponseType type = ResponseType.valueOf("CACHE");
        assertEquals(ResponseType.CACHE, type);
    }

    @Test
    public void testValueOfUnknown() {
        ResponseType type = ResponseType.valueOf("UNKNOWN");
        assertEquals(ResponseType.UNKNOWN, type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalid() {
        ResponseType.valueOf("INVALID");
    }

    // ========== ORDINAL TESTS ==========

    @Test
    public void testNetworkOrdinal() {
        assertEquals(0, ResponseType.NETWORK.ordinal());
    }

    @Test
    public void testCacheOrdinal() {
        assertEquals(1, ResponseType.CACHE.ordinal());
    }

    @Test
    public void testUnknownOrdinal() {
        assertEquals(2, ResponseType.UNKNOWN.ordinal());
    }

    // ========== EQUALITY TESTS ==========

    @Test
    public void testEnumEquality() {
        ResponseType type1 = ResponseType.NETWORK;
        ResponseType type2 = ResponseType.NETWORK;
        
        assertEquals(type1, type2);
        assertSame(type1, type2);
    }

    @Test
    public void testEnumInequality() {
        ResponseType network = ResponseType.NETWORK;
        ResponseType cache = ResponseType.CACHE;
        
        assertNotEquals(network, cache);
        assertNotSame(network, cache);
    }

    // ========== SWITCH STATEMENT TESTS ==========

    @Test
    public void testSwitchStatement() {
        String result = getTypeDescription(ResponseType.NETWORK);
        assertEquals("Response from network", result);
        
        result = getTypeDescription(ResponseType.CACHE);
        assertEquals("Response from cache", result);
        
        result = getTypeDescription(ResponseType.UNKNOWN);
        assertEquals("Unknown response", result);
    }

    private String getTypeDescription(ResponseType type) {
        switch (type) {
            case NETWORK:
                return "Response from network";
            case CACHE:
                return "Response from cache";
            case UNKNOWN:
                return "Unknown response";
            default:
                return "Unexpected type";
        }
    }

    // ========== ITERATION TESTS ==========

    @Test
    public void testIterateAllTypes() {
        int count = 0;
        for (ResponseType type : ResponseType.values()) {
            assertNotNull(type);
            assertNotNull(type.name());
            count++;
        }
        assertEquals(3, count);
    }

    // ========== TO STRING TESTS ==========

    @Test
    public void testToString() {
        assertEquals("NETWORK", ResponseType.NETWORK.toString());
        assertEquals("CACHE", ResponseType.CACHE.toString());
        assertEquals("UNKNOWN", ResponseType.UNKNOWN.toString());
    }

    // ========== NAME TESTS ==========

    @Test
    public void testName() {
        assertEquals("NETWORK", ResponseType.NETWORK.name());
        assertEquals("CACHE", ResponseType.CACHE.name());
        assertEquals("UNKNOWN", ResponseType.UNKNOWN.name());
    }

    // ========== ARRAY USAGE TESTS ==========

    @Test
    public void testCanBeUsedInArray() {
        ResponseType[] supportedTypes = {
            ResponseType.NETWORK,
            ResponseType.CACHE
        };
        
        assertEquals(2, supportedTypes.length);
        assertEquals(ResponseType.NETWORK, supportedTypes[0]);
        assertEquals(ResponseType.CACHE, supportedTypes[1]);
    }

    // ========== COMPARISON TESTS ==========

    @Test
    public void testCompareTo() {
        assertTrue(ResponseType.NETWORK.compareTo(ResponseType.CACHE) < 0);
        assertTrue(ResponseType.CACHE.compareTo(ResponseType.UNKNOWN) < 0);
        assertTrue(ResponseType.UNKNOWN.compareTo(ResponseType.NETWORK) > 0);
        assertEquals(0, ResponseType.NETWORK.compareTo(ResponseType.NETWORK));
    }

    // ========== ALL TYPES IN ORDER TESTS ==========

    @Test
    public void testAllTypesInOrder() {
        ResponseType[] types = ResponseType.values();
        assertEquals(ResponseType.NETWORK, types[0]);
        assertEquals(ResponseType.CACHE, types[1]);
        assertEquals(ResponseType.UNKNOWN, types[2]);
    }

    // ========== USAGE PATTERN TESTS ==========

    @Test
    public void testUsageInConditional() {
        ResponseType type = ResponseType.NETWORK;
        
        if (type == ResponseType.NETWORK) {
            assertTrue(true); // Expected path
        } else {
            fail("Should be NETWORK");
        }
    }

    @Test
    public void testUsageInMultipleConditionals() {
        ResponseType type = ResponseType.CACHE;
        
        boolean isCache = type == ResponseType.CACHE;
        boolean isNetwork = type == ResponseType.NETWORK;
        boolean isUnknown = type == ResponseType.UNKNOWN;
        
        assertTrue(isCache);
        assertFalse(isNetwork);
        assertFalse(isUnknown);
    }
}
