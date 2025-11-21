package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for LanguageCode enum.
 */
@RunWith(RobolectricTestRunner.class)
public class TestLanguageCode {

    // ========== ENUM VALUES TESTS ==========

    @Test
    public void testEnumExists() {
        LanguageCode[] codes = LanguageCode.values();
        assertNotNull(codes);
        assertTrue(codes.length > 0);
    }

    @Test
    public void testEnumHas136Languages() {
        LanguageCode[] codes = LanguageCode.values();
        assertEquals(136, codes.length);
    }

    // ========== SPECIFIC LANGUAGE CODE TESTS ==========

    @Test
    public void testEnglishUSExists() {
        LanguageCode code = LanguageCode.en_us;
        assertNotNull(code);
        assertEquals("en_us", code.name());
    }

    @Test
    public void testEnglishGBExists() {
        LanguageCode code = LanguageCode.en_gb;
        assertNotNull(code);
        assertEquals("en_gb", code.name());
    }

    @Test
    public void testChineseSimplifiedExists() {
        LanguageCode code = LanguageCode.zh_cn;
        assertNotNull(code);
        assertEquals("zh_cn", code.name());
    }

    @Test
    public void testFrenchFranceExists() {
        LanguageCode code = LanguageCode.fr_fr;
        assertNotNull(code);
        assertEquals("fr_fr", code.name());
    }

    @Test
    public void testGermanGermanyExists() {
        LanguageCode code = LanguageCode.de_de;
        assertNotNull(code);
        assertEquals("de_de", code.name());
    }

    @Test
    public void testSpanishSpainExists() {
        LanguageCode code = LanguageCode.es_es;
        assertNotNull(code);
        assertEquals("es_es", code.name());
    }

    @Test
    public void testJapaneseExists() {
        LanguageCode code = LanguageCode.ja_jp;
        assertNotNull(code);
        assertEquals("ja_jp", code.name());
    }

    @Test
    public void testKoreanExists() {
        LanguageCode code = LanguageCode.ko_kr;
        assertNotNull(code);
        assertEquals("ko_kr", code.name());
    }

    @Test
    public void testArabicSaudiArabiaExists() {
        LanguageCode code = LanguageCode.ar_sa;
        assertNotNull(code);
        assertEquals("ar_sa", code.name());
    }

    @Test
    public void testHindiIndiaExists() {
        LanguageCode code = LanguageCode.hi_in;
        assertNotNull(code);
        assertEquals("hi_in", code.name());
    }

    // ========== VALUE OF TESTS ==========

    @Test
    public void testValueOfEnglishUS() {
        LanguageCode code = LanguageCode.valueOf("en_us");
        assertEquals(LanguageCode.en_us, code);
    }

    @Test
    public void testValueOfChineseCN() {
        LanguageCode code = LanguageCode.valueOf("zh_cn");
        assertEquals(LanguageCode.zh_cn, code);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfInvalidCode() {
        LanguageCode.valueOf("invalid_code");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfEmptyString() {
        LanguageCode.valueOf("");
    }

    @Test(expected = NullPointerException.class)
    public void testValueOfNull() {
        LanguageCode.valueOf(null);
    }

    // ========== ORDINAL TESTS ==========

    @Test
    public void testFirstLanguageCodeOrdinal() {
        assertEquals(0, LanguageCode.af_za.ordinal());
    }

    @Test
    public void testLastLanguageCodeOrdinal() {
        LanguageCode[] codes = LanguageCode.values();
        assertEquals(135, LanguageCode.vi_vn.ordinal());
        assertEquals(codes.length - 1, LanguageCode.vi_vn.ordinal());
    }

    // ========== NAME TESTS ==========

    @Test
    public void testNameReturnsEnumName() {
        assertEquals("en_us", LanguageCode.en_us.name());
        assertEquals("fr_fr", LanguageCode.fr_fr.name());
        assertEquals("zh_cn", LanguageCode.zh_cn.name());
    }

    // ========== COMPARISON TESTS ==========

    @Test
    public void testEnumEquality() {
        LanguageCode code1 = LanguageCode.en_us;
        LanguageCode code2 = LanguageCode.en_us;
        
        assertEquals(code1, code2);
        assertSame(code1, code2);
    }

    @Test
    public void testEnumInequality() {
        LanguageCode code1 = LanguageCode.en_us;
        LanguageCode code2 = LanguageCode.en_gb;
        
        assertNotEquals(code1, code2);
        assertNotSame(code1, code2);
    }

    // ========== ALL LANGUAGE CODE EXISTENCE TESTS ==========

    @Test
    public void testAllEnglishVariantsExist() {
        assertNotNull(LanguageCode.en_au);
        assertNotNull(LanguageCode.en_bz);
        assertNotNull(LanguageCode.en_ca);
        assertNotNull(LanguageCode.en_cb);
        assertNotNull(LanguageCode.en_ie);
        assertNotNull(LanguageCode.en_jm);
        assertNotNull(LanguageCode.en_nz);
        assertNotNull(LanguageCode.en_ph);
        assertNotNull(LanguageCode.en_za);
        assertNotNull(LanguageCode.en_tt);
        assertNotNull(LanguageCode.en_gb);
        assertNotNull(LanguageCode.en_us);
        assertNotNull(LanguageCode.en_zw);
    }

    @Test
    public void testAllArabicVariantsExist() {
        assertNotNull(LanguageCode.ar_dz);
        assertNotNull(LanguageCode.ar_bh);
        assertNotNull(LanguageCode.ar_eg);
        assertNotNull(LanguageCode.ar_iq);
        assertNotNull(LanguageCode.ar_jo);
        assertNotNull(LanguageCode.ar_kw);
        assertNotNull(LanguageCode.ar_lb);
        assertNotNull(LanguageCode.ar_ly);
        assertNotNull(LanguageCode.ar_ma);
        assertNotNull(LanguageCode.ar_om);
        assertNotNull(LanguageCode.ar_qa);
        assertNotNull(LanguageCode.ar_sa);
        assertNotNull(LanguageCode.ar_sy);
        assertNotNull(LanguageCode.ar_tn);
        assertNotNull(LanguageCode.ar_ae);
        assertNotNull(LanguageCode.ar_ye);
    }

    @Test
    public void testAllChineseVariantsExist() {
        assertNotNull(LanguageCode.zh_cn);
        assertNotNull(LanguageCode.zh_hk);
        assertNotNull(LanguageCode.zh_mo);
        assertNotNull(LanguageCode.zh_sg);
        assertNotNull(LanguageCode.zh_tw);
        assertNotNull(LanguageCode.zh_chs);
        assertNotNull(LanguageCode.zh_cht);
    }

    @Test
    public void testAllSpanishVariantsExist() {
        assertNotNull(LanguageCode.es_ar);
        assertNotNull(LanguageCode.es_bo);
        assertNotNull(LanguageCode.es_cl);
        assertNotNull(LanguageCode.es_co);
        assertNotNull(LanguageCode.es_cr);
        assertNotNull(LanguageCode.es_do);
        assertNotNull(LanguageCode.es_ec);
        assertNotNull(LanguageCode.es_sv);
        assertNotNull(LanguageCode.es_gt);
        assertNotNull(LanguageCode.es_hn);
        assertNotNull(LanguageCode.es_mx);
        assertNotNull(LanguageCode.es_ni);
        assertNotNull(LanguageCode.es_pa);
        assertNotNull(LanguageCode.es_py);
        assertNotNull(LanguageCode.es_pe);
        assertNotNull(LanguageCode.es_pr);
        assertNotNull(LanguageCode.es_es);
        assertNotNull(LanguageCode.es_uy);
        assertNotNull(LanguageCode.es_ve);
    }

    @Test
    public void testAllFrenchVariantsExist() {
        assertNotNull(LanguageCode.fr_be);
        assertNotNull(LanguageCode.fr_ca);
        assertNotNull(LanguageCode.fr_fr);
        assertNotNull(LanguageCode.fr_lu);
        assertNotNull(LanguageCode.fr_mc);
        assertNotNull(LanguageCode.fr_ch);
    }

    @Test
    public void testAllGermanVariantsExist() {
        assertNotNull(LanguageCode.de_at);
        assertNotNull(LanguageCode.de_de);
        assertNotNull(LanguageCode.de_li);
        assertNotNull(LanguageCode.de_lu);
        assertNotNull(LanguageCode.de_ch);
    }

    // ========== SWITCH STATEMENT TESTS ==========

    @Test
    public void testSwitchStatement() {
        String result = getLanguageDescription(LanguageCode.en_us);
        assertEquals("English (United States)", result);
        
        result = getLanguageDescription(LanguageCode.fr_fr);
        assertEquals("French (France)", result);
        
        result = getLanguageDescription(LanguageCode.af_za);
        assertEquals("Unknown", result);
    }

    private String getLanguageDescription(LanguageCode code) {
        switch (code) {
            case en_us:
                return "English (United States)";
            case en_gb:
                return "English (United Kingdom)";
            case fr_fr:
                return "French (France)";
            case de_de:
                return "German (Germany)";
            default:
                return "Unknown";
        }
    }

    // ========== ITERATION TESTS ==========

    @Test
    public void testIterateAllLanguageCodes() {
        int count = 0;
        for (LanguageCode code : LanguageCode.values()) {
            assertNotNull(code);
            assertNotNull(code.name());
            count++;
        }
        assertEquals(136, count);
    }

    // ========== STRING CONVERSION TESTS ==========

    @Test
    public void testToString() {
        assertEquals("en_us", LanguageCode.en_us.toString());
        assertEquals("fr_fr", LanguageCode.fr_fr.toString());
        assertEquals("zh_cn", LanguageCode.zh_cn.toString());
    }

    // ========== ENUM COLLECTION TESTS ==========

    @Test
    public void testCanBeUsedInArrays() {
        LanguageCode[] supportedLanguages = {
            LanguageCode.en_us,
            LanguageCode.en_gb,
            LanguageCode.fr_fr,
            LanguageCode.de_de,
            LanguageCode.es_es
        };
        
        assertEquals(5, supportedLanguages.length);
        assertEquals(LanguageCode.en_us, supportedLanguages[0]);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testFirstAndLastCodes() {
        LanguageCode[] codes = LanguageCode.values();
        assertEquals(LanguageCode.af_za, codes[0]);
        assertEquals(LanguageCode.vi_vn, codes[codes.length - 1]);
    }

    @Test
    public void testAllCodesHaveUnderscoreFormat() {
        for (LanguageCode code : LanguageCode.values()) {
            String name = code.name();
            assertTrue("Code " + name + " should contain underscore", name.contains("_"));
        }
    }

    @Test
    public void testAllCodesAreLowercase() {
        for (LanguageCode code : LanguageCode.values()) {
            String name = code.name();
            assertEquals("Code should be lowercase", name, name.toLowerCase());
        }
    }
}

