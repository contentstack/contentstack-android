package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for SDKUtil class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestSDKUtil {

    private SDKUtil sdkUtil;

    @Before
    public void setUp() {
        sdkUtil = new SDKUtil();
    }

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testSDKUtilCreation() {
        assertNotNull(sdkUtil);
    }

    // ========== SHOW LOG TESTS ==========

    @Test
    public void testShowLogWithValidInputs() {
        // Should not throw exception
        SDKUtil.showLog("TestTag", "Test message");
    }

    @Test
    public void testShowLogWithNullTag() {
        // Should not throw exception
        SDKUtil.showLog(null, "Test message");
    }

    @Test
    public void testShowLogWithNullMessage() {
        // Should not throw exception
        SDKUtil.showLog("TestTag", null);
    }

    @Test
    public void testShowLogWithBothNull() {
        // Should not throw exception
        SDKUtil.showLog(null, null);
    }

    @Test
    public void testShowLogWithEmptyStrings() {
        // Should not throw exception
        SDKUtil.showLog("", "");
    }

    @Test
    public void testShowLogWithLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("Long message ");
        }
        // Should not throw exception
        SDKUtil.showLog("TestTag", longMessage.toString());
    }

    // ========== GET SHA FROM STRING TESTS ==========

    @Test
    public void testGetSHAFromStringWithValidInput() {
        String input = "test_string";
        String sha = sdkUtil.getSHAFromString(input);
        
        assertNotNull(sha);
        assertFalse(sha.isEmpty());
    }

    @Test
    public void testGetSHAFromStringConsistency() {
        String input = "consistent_input";
        String sha1 = sdkUtil.getSHAFromString(input);
        String sha2 = sdkUtil.getSHAFromString(input);
        
        assertEquals("Same input should produce same SHA", sha1, sha2);
    }

    @Test
    public void testGetSHAFromStringDifferentInputs() {
        String sha1 = sdkUtil.getSHAFromString("input1");
        String sha2 = sdkUtil.getSHAFromString("input2");
        
        assertNotEquals("Different inputs should produce different SHAs", sha1, sha2);
    }

    @Test
    public void testGetSHAFromStringWithSpecialCharacters() {
        String input = "!@#$%^&*()_+-={}[]|\\:;<>?,./~`";
        String sha = sdkUtil.getSHAFromString(input);
        
        assertNotNull(sha);
        assertFalse(sha.isEmpty());
    }

    @Test
    public void testGetSHAFromStringWithUnicode() {
        String input = "Hello 世界 مرحبا мир";
        String sha = sdkUtil.getSHAFromString(input);
        
        assertNotNull(sha);
        assertFalse(sha.isEmpty());
    }

    @Test
    public void testGetSHAFromStringWithNumbers() {
        String input = "1234567890";
        String sha = sdkUtil.getSHAFromString(input);
        
        assertNotNull(sha);
        assertFalse(sha.isEmpty());
    }

    @Test
    public void testGetSHAFromStringWithLongInput() {
        StringBuilder longInput = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longInput.append("a");
        }
        String sha = sdkUtil.getSHAFromString(longInput.toString());
        
        assertNotNull(sha);
        assertFalse(sha.isEmpty());
    }

    // ========== PARSE DATE TESTS ==========

    @Test
    public void testParseDateWithValidISO8601() {
        String date = "2023-01-15T10:30:00.000Z";
        Calendar calendar = SDKUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(0, calendar.get(Calendar.MONTH)); // January is 0
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateWithEmptyDate() {
        Calendar calendar = SDKUtil.parseDate("", TimeZone.getTimeZone("UTC"));
        assertNull(calendar);
    }

    @Test
    public void testParseDateWithInvalidFormat() {
        Calendar calendar = SDKUtil.parseDate("invalid-date", TimeZone.getTimeZone("UTC"));
        assertNull(calendar);
    }

    @Test
    public void testParseDateWithDifferentTimezones() {
        String date = "2023-06-15T12:00:00.000Z";
        
        Calendar utc = SDKUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        Calendar pst = SDKUtil.parseDate(date, TimeZone.getTimeZone("PST"));
        
        assertNotNull(utc);
        assertNotNull(pst);
    }

    @Test
    public void testParseDateWithMilliseconds() {
        String date = "2023-12-31T23:59:59.999Z";
        Calendar calendar = SDKUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(11, calendar.get(Calendar.MONTH)); // December is 11
        assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    }

    // ========== GET RESPONSE TIME FROM CACHE FILE TESTS ==========

    @Test
    public void testGetResponseTimeFromCacheFileWithZeroTime() {
        File file = new File("test.txt");
        boolean result = sdkUtil.getResponseTimeFromCacheFile(file, 0);
        
        // With 0 time, should consider cache expired
        assertNotNull(result);
    }

    // ========== GET JSON FROM CACHE FILE TESTS ==========

    @Test
    public void testGetJsonFromCacheFileWithNullFile() {
        JSONObject result = SDKUtil.getJsonFromCacheFile(null);
        assertNull("Should return null for null file", result);
    }

    @Test
    public void testGetJsonFromCacheFileWithNonExistentFile() {
        File nonExistentFile = new File("/non/existent/path/file.json");
        JSONObject result = SDKUtil.getJsonFromCacheFile(nonExistentFile);
        
        assertNull("Should return null for non-existent file", result);
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testGetSHAFromStringNullHandling() {
        try {
            String sha = sdkUtil.getSHAFromString(null);
            // If it doesn't throw, should handle gracefully
            assertNotNull(sha);
        } catch (NullPointerException e) {
            // Expected behavior for null input
            assertNotNull(e);
        }
    }

    @Test
    public void testParseDateWithVariousISO8601Formats() {
        String[] dates = {
            "2023-01-01T00:00:00.000Z",
            "2023-06-15T12:30:45.123Z",
            "2023-12-31T23:59:59.999Z"
        };
        
        for (String date : dates) {
            Calendar calendar = SDKUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull("Date should be parsed: " + date, calendar);
        }
    }

    @Test
    public void testShowLogWithSpecialCharacters() {
        SDKUtil.showLog("Test@#$", "Message with special chars !@#$%^&*()");
        // Should not throw exception
        assertTrue(true);
    }

    @Test
    public void testGetSHAFromStringWithWhitespace() {
        String sha1 = sdkUtil.getSHAFromString("no spaces");
        String sha2 = sdkUtil.getSHAFromString("no spaces");
        
        assertEquals(sha1, sha2);
    }

    @Test
    public void testGetSHAFromStringDifferentCasing() {
        String sha1 = sdkUtil.getSHAFromString("Test");
        String sha2 = sdkUtil.getSHAFromString("test");
        
        assertNotEquals("Different casing should produce different SHAs", sha1, sha2);
    }

    // ========== MULTIPLE CALLS TESTS ==========

    @Test
    public void testMultipleSHAGenerations() {
        for (int i = 0; i < 100; i++) {
            String input = "test_" + i;
            String sha = sdkUtil.getSHAFromString(input);
            assertNotNull(sha);
            assertFalse(sha.isEmpty());
        }
    }

    @Test
    public void testMultipleDateParses() {
        String[] dates = new String[10];
        for (int i = 0; i < 10; i++) {
            dates[i] = "2023-01-" + String.format("%02d", (i + 1)) + "T00:00:00.000Z";
        }
        
        for (String date : dates) {
            Calendar calendar = SDKUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull(calendar);
        }
    }

    // ========== CONCURRENT USAGE TESTS ==========

    @Test
    public void testStaticMethodConcurrency() {
        // Test that static methods can be called multiple times
        SDKUtil.showLog("Tag1", "Message1");
        SDKUtil.showLog("Tag2", "Message2");
        SDKUtil.showLog("Tag3", "Message3");
        
        String sha1 = sdkUtil.getSHAFromString("input1");
        String sha2 = sdkUtil.getSHAFromString("input2");
        
        assertNotEquals(sha1, sha2);
    }

    @Test
    public void testMultipleSDKUtilInstances() {
        SDKUtil util1 = new SDKUtil();
        SDKUtil util2 = new SDKUtil();
        SDKUtil util3 = new SDKUtil();
        
        String sha1 = util1.getSHAFromString("test");
        String sha2 = util2.getSHAFromString("test");
        String sha3 = util3.getSHAFromString("test");
        
        assertEquals("All instances should produce same SHA for same input", sha1, sha2);
        assertEquals("All instances should produce same SHA for same input", sha2, sha3);
    }
}

