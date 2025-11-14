package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for ResponseType enum
 */
@RunWith(RobolectricTestRunner.class)
public class TestResponseTypeEnum {

    @Test
    public void testResponseTypeValues() {
        ResponseType[] types = ResponseType.values();
        assertNotNull(types);
        assertTrue(types.length > 0);
    }

    @Test
    public void testNetworkResponseType() {
        ResponseType type = ResponseType.NETWORK;
        assertNotNull(type);
        assertEquals("NETWORK", type.name());
    }

    @Test
    public void testCacheResponseType() {
        ResponseType type = ResponseType.CACHE;
        assertNotNull(type);
        assertEquals("CACHE", type.name());
    }

    @Test
    public void testResponseTypeValueOf() {
        ResponseType network = ResponseType.valueOf("NETWORK");
        assertEquals(ResponseType.NETWORK, network);
        
        ResponseType cache = ResponseType.valueOf("CACHE");
        assertEquals(ResponseType.CACHE, cache);
    }

    @Test
    public void testResponseTypeEquality() {
        ResponseType type1 = ResponseType.NETWORK;
        ResponseType type2 = ResponseType.NETWORK;
        assertEquals(type1, type2);
    }

    @Test
    public void testResponseTypeInequality() {
        assertNotEquals(ResponseType.NETWORK, ResponseType.CACHE);
    }

    @Test
    public void testResponseTypeToString() {
        assertEquals("NETWORK", ResponseType.NETWORK.toString());
        assertEquals("CACHE", ResponseType.CACHE.toString());
    }

    @Test
    public void testResponseTypeOrdinals() {
        ResponseType[] types = ResponseType.values();
        for (int i = 0; i < types.length; i++) {
            assertEquals(i, types[i].ordinal());
        }
    }

    @Test
    public void testResponseTypeInSwitch() {
        ResponseType type = ResponseType.NETWORK;
        boolean found = false;
        
        switch (type) {
            case NETWORK:
                found = true;
                break;
            case CACHE:
                break;
        }
        
        assertTrue(found);
    }

    @Test
    public void testResponseTypeHashCode() {
        int hash1 = ResponseType.NETWORK.hashCode();
        int hash2 = ResponseType.NETWORK.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    public void testResponseTypeCompareTo() {
        ResponseType type = ResponseType.NETWORK;
        assertEquals(0, type.compareTo(ResponseType.NETWORK));
    }

    @Test
    public void testResponseTypeInCollection() {
        java.util.Set<ResponseType> set = new java.util.HashSet<>();
        set.add(ResponseType.NETWORK);
        set.add(ResponseType.CACHE);
        set.add(ResponseType.NETWORK); // Duplicate
        
        assertEquals(2, set.size());
    }

    @Test
    public void testResponseTypeInMap() {
        java.util.Map<ResponseType, String> map = new java.util.HashMap<>();
        map.put(ResponseType.NETWORK, "From Network");
        map.put(ResponseType.CACHE, "From Cache");
        
        assertEquals(2, map.size());
    }

    @Test
    public void testAllResponseTypesAccessible() {
        for (ResponseType type : ResponseType.values()) {
            ResponseType fromName = ResponseType.valueOf(type.name());
            assertEquals(type, fromName);
        }
    }

    @Test
    public void testResponseTypeDeclaringClass() {
        assertEquals(ResponseType.class, ResponseType.NETWORK.getDeclaringClass());
    }

    @Test
    public void testResponseTypeIdentity() {
        ResponseType type1 = ResponseType.NETWORK;
        ResponseType type2 = ResponseType.valueOf("NETWORK");
        assertTrue(type1 == type2);
    }

    @Test
    public void testMultipleValueOfCalls() {
        for (int i = 0; i < 10; i++) {
            ResponseType type = ResponseType.valueOf("NETWORK");
            assertEquals(ResponseType.NETWORK, type);
        }
    }

    @Test
    public void testResponseTypeNotNull() {
        for (ResponseType type : ResponseType.values()) {
            assertNotNull(type);
            assertNotNull(type.name());
            assertNotNull(type.toString());
        }
    }

    @Test
    public void testResponseTypeUniqueness() {
        ResponseType[] types = ResponseType.values();
        for (int i = 0; i < types.length; i++) {
            for (int j = i + 1; j < types.length; j++) {
                assertNotEquals(types[i], types[j]);
            }
        }
    }
}

