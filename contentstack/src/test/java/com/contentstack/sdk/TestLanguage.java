package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for Language enum.
 * Tests all 136 language constants.
 */
@RunWith(RobolectricTestRunner.class)
public class TestLanguage {

    @Test
    public void testEnumValues() {
        Language[] values = Language.values();
        assertEquals("Should have 136 language values", 136, values.length);
    }

    @Test
    public void testValueOf() {
        Language lang = Language.valueOf("ENGLISH_UNITED_STATES");
        assertEquals(Language.ENGLISH_UNITED_STATES, lang);
    }

    @Test
    public void testAllAfricanLanguages() {
        assertNotNull(Language.AFRIKAANS_SOUTH_AFRICA);
        assertNotNull(Language.SWAHILI_KENYA);
    }

    @Test
    public void testAllArabicLanguages() {
        assertNotNull(Language.ARABIC_ALGERIA);
        assertNotNull(Language.ARABIC_BAHRAIN);
        assertNotNull(Language.ARABIC_EGYPT);
        assertNotNull(Language.ARABIC_IRAQ);
        assertNotNull(Language.ARABIC_JORDAN);
        assertNotNull(Language.ARABIC_KUWAIT);
        assertNotNull(Language.ARABIC_LEBANON);
        assertNotNull(Language.ARABIC_LIBYA);
        assertNotNull(Language.ARABIC_MOROCCO);
        assertNotNull(Language.ARABIC_OMAN);
        assertNotNull(Language.ARABIC_QATAR);
        assertNotNull(Language.ARABIC_SAUDI_ARABIA);
        assertNotNull(Language.ARABIC_SYRIA);
        assertNotNull(Language.ARABIC_TUNISIA);
        assertNotNull(Language.ARABIC_UNITED_ARAB_EMIRATES);
        assertNotNull(Language.ARABIC_YEMEN);
    }

    @Test
    public void testAllChineseLanguages() {
        assertNotNull(Language.CHINESE_CHINA);
        assertNotNull(Language.CHINESE_HONG_KONG_SAR);
        assertNotNull(Language.CHINESE_MACUS_SAR);
        assertNotNull(Language.CHINESE_SINGAPORE);
        assertNotNull(Language.CHINESE_TAIWAN);
        assertNotNull(Language.CHINESE_SIMPLIFIED);
        assertNotNull(Language.CHINESE_TRADITIONAL);
    }

    @Test
    public void testAllEnglishLanguages() {
        assertNotNull(Language.ENGLISH_AUSTRALIA);
        assertNotNull(Language.ENGLISH_BELIZE);
        assertNotNull(Language.ENGLISH_CANADA);
        assertNotNull(Language.ENGLISH_CARIBBEAN);
        assertNotNull(Language.ENGLISH_IRELAND);
        assertNotNull(Language.ENGLISH_JAMAICA);
        assertNotNull(Language.ENGLISH_NEW_ZEALAND);
        assertNotNull(Language.ENGLISH_PHILIPPINES);
        assertNotNull(Language.ENGLISH_SOUTH_AFRICA);
        assertNotNull(Language.ENGLISH_TRINIDAD_AND_TOBAGO);
        assertNotNull(Language.ENGLISH_UNITED_KINGDOM);
        assertNotNull(Language.ENGLISH_UNITED_STATES);
        assertNotNull(Language.ENGLISH_ZIMBABWE);
    }

    @Test
    public void testAllFrenchLanguages() {
        assertNotNull(Language.FRENCH_BELGIUM);
        assertNotNull(Language.FRENCH_CANADA);
        assertNotNull(Language.FRENCH_FRANCE);
        assertNotNull(Language.FRENCH_LUXEMBOURG);
        assertNotNull(Language.FRENCH_MONACO);
        assertNotNull(Language.FRENCH_SWITZERLAND);
    }

    @Test
    public void testAllGermanLanguages() {
        assertNotNull(Language.GERMEN_AUSTRIA);
        assertNotNull(Language.GERMEN_GERMANY);
        assertNotNull(Language.GERMEN_LIENCHTENSTEIN);
        assertNotNull(Language.GERMEN_LUXEMBOURG);
        assertNotNull(Language.GERMEN_SWITZERLAND);
    }

    @Test
    public void testAllSpanishLanguages() {
        assertNotNull(Language.SPANISH_ARGENTINA);
        assertNotNull(Language.SPANISH_BOLIVIA);
        assertNotNull(Language.SPANISH_CHILE);
        assertNotNull(Language.SPANISH_COLOMBIA);
        assertNotNull(Language.SPANISH_COSTA_RICA);
        assertNotNull(Language.SPANISH_DOMINICAN_REPUBLIC);
        assertNotNull(Language.SPANISH_ECUADOR);
        assertNotNull(Language.SPANISH_ELSALVADOR);
        assertNotNull(Language.SPANISH_GUATEMALA);
        assertNotNull(Language.SPANISH_HONDURAS);
        assertNotNull(Language.SPANISH_MEXICO);
        assertNotNull(Language.SPANISH_NICARAGUA);
        assertNotNull(Language.SPANISH_PANAMA);
        assertNotNull(Language.SPANISH_PARAGUAY);
        assertNotNull(Language.SPANISH_PERU);
        assertNotNull(Language.SPANISH_PUERTO_RICO);
        assertNotNull(Language.SPANISH_SPAIN);
        assertNotNull(Language.SPANISH_URUGUAY);
        assertNotNull(Language.SPANISH_VENEZUELA);
    }

    @Test
    public void testAllIndianLanguages() {
        assertNotNull(Language.GUJARATI_INDIA);
        assertNotNull(Language.HINDI_INDIA);
        assertNotNull(Language.KANNADA_INDIA);
        assertNotNull(Language.KONKANI_INDIA);
        assertNotNull(Language.MARATHI_INDIA);
        assertNotNull(Language.PUNJABI_INDIA);
        assertNotNull(Language.SANSKRIT_INDIA);
        assertNotNull(Language.TAMIL_INDIA);
        assertNotNull(Language.TELUGU_INDIA);
    }

    @Test
    public void testAllEuropeanLanguages() {
        assertNotNull(Language.ALBANIAN_ALBANIA);
        assertNotNull(Language.ARMENIAN_ARMENIA);
        assertNotNull(Language.BASQUE_BASQUE);
        assertNotNull(Language.BELARUSIAN_BELARUS);
        assertNotNull(Language.BULGARIAN_BULGARIA);
        assertNotNull(Language.CATALAN_CATALAN);
        assertNotNull(Language.CROATIAN_CROATIA);
        assertNotNull(Language.CZECH_CZECH_REPUBLIC);
        assertNotNull(Language.DANISH_DENMARK);
        assertNotNull(Language.DUTCH_BELGIUM);
        assertNotNull(Language.DUTCH_NETHERLANDS);
        assertNotNull(Language.ESTONIAN_ESTONIA);
        assertNotNull(Language.FINNISH_FINLAND);
        assertNotNull(Language.GALICIAN_GALICIAN);
        assertNotNull(Language.GREEK_GREECE);
        assertNotNull(Language.HUNGARIAN_HUNGARY);
        assertNotNull(Language.ICELANDIC_ICELAND);
        assertNotNull(Language.ITALIAN_ITALY);
        assertNotNull(Language.ITALIAN_SWITZERLAND);
        assertNotNull(Language.LATVIAN_LATVIA);
        assertNotNull(Language.LITHUANIAN_LITHUANIA);
        assertNotNull(Language.MACEDONIAN_FYROM);
        assertNotNull(Language.NORWEGIAN_BOKMAL_NORWAY);
        assertNotNull(Language.NORWEGIAN_NYNORSK_NORWAY);
        assertNotNull(Language.POLISH_POLAND);
        assertNotNull(Language.PORTUGUESE_BRAZIL);
        assertNotNull(Language.PORTUGUESE_PORTUGAL);
        assertNotNull(Language.ROMANIAN_ROMANIA);
        assertNotNull(Language.RUSSIAN_RUSSIA);
        assertNotNull(Language.SLOVAK_SLOVAKIA);
        assertNotNull(Language.SLOVENIAN_SLOVENIAN);
        assertNotNull(Language.SWEDISH_FINLAND);
        assertNotNull(Language.SWEDISH_SWEDEN);
        assertNotNull(Language.UKRAINIAN_UKRAINE);
    }

    @Test
    public void testAllAsianLanguages() {
        assertNotNull(Language.AZERI_CYRILLIC_ARMENIA);
        assertNotNull(Language.AZERI_LATIN_AZERBAIJAN);
        assertNotNull(Language.GEORGIAN_GEORGIA);
        assertNotNull(Language.HEBREW_ISRAEL);
        assertNotNull(Language.INDONESIAN_INDONESIA);
        assertNotNull(Language.JAPANESE_JAPAN);
        assertNotNull(Language.KAZAKH_KAZAKHSTAN);
        assertNotNull(Language.KOREAN_KOREA);
        assertNotNull(Language.KYRGYZ_KAZAKHSTAN);
        assertNotNull(Language.MALAY_BRUNEI);
        assertNotNull(Language.MALAY_MALAYSIA);
        assertNotNull(Language.MONGOLIAN_MONGOLIA);
        assertNotNull(Language.THAI_THAILAND);
        assertNotNull(Language.TURKISH_TURKEY);
        assertNotNull(Language.VIETNAMESE_VIETNAM);
    }

    @Test
    public void testAllMiddleEasternLanguages() {
        assertNotNull(Language.FARSI_IRAN);
        assertNotNull(Language.SYRIAC_SYRIA);
    }

    @Test
    public void testAllSerbianLanguages() {
        assertNotNull(Language.SERBIAN_CYRILLIC_SERBIA);
        assertNotNull(Language.SERBIAN_LATIN_SERBIA);
    }

    @Test
    public void testAllUzbekLanguages() {
        assertNotNull(Language.UZBEK_CYRILLIC_UZBEKISTAN);
        assertNotNull(Language.UZBEK_LATIN_UZEBEKISTAN);
    }

    @Test
    public void testAllOtherLanguages() {
        assertNotNull(Language.DHIVEHI_MALDIVES);
        assertNotNull(Language.FAROESE_FAROE_ISLANDS);
        assertNotNull(Language.TATAR_RUSSIA);
        assertNotNull(Language.URDU_PAKISTAN);
    }

    @Test
    public void testEnumUniqueness() {
        Language[] values = Language.values();
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals("Each language should be unique", values[i], values[j]);
            }
        }
    }

    @Test
    public void testEnumNameConsistency() {
        for (Language lang : Language.values()) {
            String name = lang.name();
            assertNotNull("Language name should not be null", name);
            assertFalse("Language name should not be empty", name.isEmpty());
            assertTrue("Language name should be uppercase", name.equals(name.toUpperCase()));
        }
    }

    @Test
    public void testEnumToString() {
        Language lang = Language.ENGLISH_UNITED_STATES;
        assertEquals("ENGLISH_UNITED_STATES", lang.toString());
    }

    @Test
    public void testEnumOrdinal() {
        Language first = Language.AFRIKAANS_SOUTH_AFRICA;
        assertEquals(0, first.ordinal());
        
        Language last = Language.VIETNAMESE_VIETNAM;
        assertEquals(135, last.ordinal());
    }

    @Test
    public void testCommonLanguages() {
        // Test most commonly used languages
        assertNotNull(Language.ENGLISH_UNITED_STATES);
        assertNotNull(Language.ENGLISH_UNITED_KINGDOM);
        assertNotNull(Language.SPANISH_SPAIN);
        assertNotNull(Language.FRENCH_FRANCE);
        assertNotNull(Language.GERMEN_GERMANY);
        assertNotNull(Language.CHINESE_CHINA);
        assertNotNull(Language.JAPANESE_JAPAN);
        assertNotNull(Language.KOREAN_KOREA);
        assertNotNull(Language.HINDI_INDIA);
        assertNotNull(Language.RUSSIAN_RUSSIA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidValueOf() {
        Language.valueOf("INVALID_LANGUAGE");
    }

    @Test(expected = NullPointerException.class)
    public void testNullValueOf() {
        Language.valueOf(null);
    }
}

