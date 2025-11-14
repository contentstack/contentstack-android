package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for LanguageCode enum
 */
@RunWith(RobolectricTestRunner.class)
public class TestLanguageCodeComprehensive {

    @Test
    public void testAllLanguageCodesNotNull() {
        for (LanguageCode lc : LanguageCode.values()) {
            assertNotNull(lc);
            assertNotNull(lc.name());
        }
    }

    @Test
    public void testLanguageCodeValuesUnique() {
        LanguageCode[] codes = LanguageCode.values();
        for (int i = 0; i < codes.length; i++) {
            for (int j = i + 1; j < codes.length; j++) {
                assertNotEquals(codes[i], codes[j]);
            }
        }
    }

    @Test
    public void testLanguageCodeOrdinals() {
        LanguageCode[] codes = LanguageCode.values();
        for (int i = 0; i < codes.length; i++) {
            assertEquals(i, codes[i].ordinal());
        }
    }

    @Test
    public void testLanguageCodeValueOf() {
        LanguageCode lc = LanguageCode.valueOf("en_us");
        assertEquals(LanguageCode.en_us, lc);
    }

    @Test
    public void testMultipleLanguageCodes() {
        assertNotNull(LanguageCode.en_us);
        assertNotNull(LanguageCode.fr_fr);
        assertNotNull(LanguageCode.de_de);
        assertNotNull(LanguageCode.ja_jp);
        assertNotNull(LanguageCode.zh_cn);
        assertNotNull(LanguageCode.es_es);
        assertNotNull(LanguageCode.it_it);
        assertNotNull(LanguageCode.pt_pt);
        assertNotNull(LanguageCode.ru_ru);
        assertNotNull(LanguageCode.ar_ae);
    }

    @Test
    public void testLanguageCodeComparison() {
        LanguageCode lc1 = LanguageCode.en_us;
        LanguageCode lc2 = LanguageCode.en_us;
        assertEquals(lc1, lc2);
    }

    @Test
    public void testLanguageCodeToString() {
        for (LanguageCode lc : LanguageCode.values()) {
            String str = lc.toString();
            assertNotNull(str);
            assertFalse(str.isEmpty());
        }
    }

    @Test
    public void testSpecificLanguageCodes() {
        assertEquals("en_us", LanguageCode.en_us.name());
        assertEquals("fr_fr", LanguageCode.fr_fr.name());
        assertEquals("de_de", LanguageCode.de_de.name());
    }

    @Test
    public void testLanguageCodeArrayIteration() {
        LanguageCode[] codes = LanguageCode.values();
        assertTrue(codes.length > 0);
        
        for (int i = 0; i < codes.length; i++) {
            assertNotNull(codes[i]);
        }
    }

    @Test
    public void testLanguageCodeInSwitch() {
        LanguageCode lc = LanguageCode.en_us;
        boolean found = false;
        
        switch (lc) {
            case en_us:
                found = true;
                break;
            default:
                break;
        }
        
        assertTrue(found);
    }

    @Test
    public void testLanguageCodeHashCode() {
        LanguageCode lc1 = LanguageCode.en_us;
        LanguageCode lc2 = LanguageCode.en_us;
        assertEquals(lc1.hashCode(), lc2.hashCode());
    }

    @Test
    public void testLanguageCodeEquality() {
        LanguageCode lc = LanguageCode.valueOf("en_us");
        assertTrue(lc == LanguageCode.en_us);
    }

    @Test
    public void testLanguageCodeOrder() {
        LanguageCode[] codes = LanguageCode.values();
        for (int i = 0; i < codes.length - 1; i++) {
            assertTrue(codes[i].ordinal() < codes[i + 1].ordinal());
        }
    }

    @Test
    public void testMultipleValueOfCalls() {
        for (int i = 0; i < 10; i++) {
            LanguageCode lc = LanguageCode.valueOf("en_us");
            assertEquals(LanguageCode.en_us, lc);
        }
    }

    @Test
    public void testLanguageCodeInCollection() {
        java.util.Set<LanguageCode> set = new java.util.HashSet<>();
        set.add(LanguageCode.en_us);
        set.add(LanguageCode.fr_fr);
        set.add(LanguageCode.en_us); // Duplicate
        
        assertEquals(2, set.size());
    }

    @Test
    public void testLanguageCodeInMap() {
        java.util.Map<LanguageCode, String> map = new java.util.HashMap<>();
        map.put(LanguageCode.en_us, "English US");
        map.put(LanguageCode.fr_fr, "French");
        
        assertEquals(2, map.size());
        assertEquals("English US", map.get(LanguageCode.en_us));
    }

    @Test
    public void testAllEnumConstantsAccessible() {
        LanguageCode[] all = LanguageCode.values();
        for (LanguageCode lc : all) {
            LanguageCode fromName = LanguageCode.valueOf(lc.name());
            assertEquals(lc, fromName);
        }
    }

    @Test
    public void testLanguageCodeNotEquals() {
        assertNotEquals(LanguageCode.en_us, LanguageCode.fr_fr);
        assertNotEquals(LanguageCode.de_de, LanguageCode.ja_jp);
    }

    @Test
    public void testLanguageCodeCompareToSelf() {
        LanguageCode lc = LanguageCode.en_us;
        assertEquals(0, lc.compareTo(lc));
    }

    @Test
    public void testLanguageCodeDeclaringClass() {
        LanguageCode lc = LanguageCode.en_us;
        assertEquals(LanguageCode.class, lc.getDeclaringClass());
    }
}

