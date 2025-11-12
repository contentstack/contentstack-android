package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for CSUtil class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestCSUtil {

    // ========== PARSE DATE WITH TIMEZONE TESTS ==========

    @Test
    public void testParseDateWithValidISO8601() {
        String date = "2023-01-15T10:30:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(0, calendar.get(Calendar.MONTH)); // January is 0
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateWithDifferentTimezones() {
        String date = "2023-06-15T12:00:00.000Z";
        
        Calendar utc = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        Calendar pst = CSUtil.parseDate(date, TimeZone.getTimeZone("PST"));
        
        assertNotNull(utc);
        assertNotNull(pst);
    }

    @Test
    public void testParseDateWithMilliseconds() {
        String date = "2023-12-31T23:59:59.999Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(11, calendar.get(Calendar.MONTH)); // December is 11
        assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateWithInvalidFormat() {
        Calendar calendar = CSUtil.parseDate("invalid-date", TimeZone.getTimeZone("UTC"));
        assertNull(calendar);
    }

    @Test
    public void testParseDateWithVariousISO8601Formats() {
        String[] dates = {
            "2023-01-01T00:00:00.000Z",
            "2023-06-15T12:30:45.123Z",
            "2023-12-31T23:59:59.999Z"
        };
        
        for (String date : dates) {
            Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull("Date should be parsed: " + date, calendar);
        }
    }

    // ========== PARSE DATE WITH FORMAT TESTS ==========

    @Test
    public void testParseDateWithCustomFormat() throws ParseException {
        String date = "2023-01-15 10:30:00";
        String format = "yyyy-MM-dd HH:mm:ss";
        
        Calendar calendar = CSUtil.parseDate(date, format, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(0, calendar.get(Calendar.MONTH));
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateWithShortFormat() throws ParseException {
        String date = "2023-01-15";
        String format = "yyyy-MM-dd";
        
        Calendar calendar = CSUtil.parseDate(date, format, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(0, calendar.get(Calendar.MONTH));
        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected = ParseException.class)
    public void testParseDateWithMismatchedFormat() throws ParseException {
        String date = "2023-01-15";
        String format = "yyyy/MM/dd"; // Different format
        
        CSUtil.parseDate(date, format, TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testParseDateWithDifferentFormats() throws ParseException {
        String[][] testCases = {
            {"2023-01-15", "yyyy-MM-dd"},
            {"15/01/2023", "dd/MM/yyyy"},
            {"Jan 15, 2023", "MMM dd, yyyy"},
            {"2023-01-15 10:30", "yyyy-MM-dd HH:mm"}
        };
        
        for (String[] testCase : testCases) {
            Calendar calendar = CSUtil.parseDate(testCase[0], testCase[1], TimeZone.getTimeZone("UTC"));
            assertNotNull("Date should be parsed: " + testCase[0] + " with format " + testCase[1], calendar);
        }
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testParseDateLeapYear() {
        String date = "2024-02-29T00:00:00.000Z"; // Leap year
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2024, calendar.get(Calendar.YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH)); // February is 1
        assertEquals(29, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateEndOfYear() {
        String date = "2023-12-31T23:59:59.999Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(11, calendar.get(Calendar.MONTH));
        assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseDateStartOfYear() {
        String date = "2023-01-01T00:00:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(calendar);
        assertEquals(2023, calendar.get(Calendar.YEAR));
        assertEquals(0, calendar.get(Calendar.MONTH));
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    // ========== TIMEZONE TESTS ==========

    @Test
    public void testParseDateWithDifferentTimezonesPST() {
        String date = "2023-06-15T12:00:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("PST"));
        
        assertNotNull(calendar);
    }

    @Test
    public void testParseDateWithDifferentTimezonesEST() {
        String date = "2023-06-15T12:00:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("EST"));
        
        assertNotNull(calendar);
    }

    @Test
    public void testParseDateWithDifferentTimezonesIST() {
        String date = "2023-06-15T12:00:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("IST"));
        
        assertNotNull(calendar);
    }

    // ========== MULTIPLE CALLS TESTS ==========

    @Test
    public void testMultipleDateParses() {
        String[] dates = new String[10];
        for (int i = 0; i < 10; i++) {
            dates[i] = "2023-01-" + String.format("%02d", (i + 1)) + "T00:00:00.000Z";
        }
        
        for (String date : dates) {
            Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull(calendar);
        }
    }

    @Test
    public void testParseDate100Times() {
        for (int i = 1; i <= 100; i++) {
            int month = ((i - 1) % 12) + 1;
            String date = "2023-" + String.format("%02d", month) + "-01T00:00:00.000Z";
            Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull(calendar);
        }
    }

    // ========== STATIC METHOD TESTS ==========

    @Test
    public void testStaticMethodAccess() {
        String date = "2023-01-15T10:30:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        assertNotNull(calendar);
    }

    @Test
    public void testConcurrentStaticCalls() {
        String date1 = "2023-01-15T10:30:00.000Z";
        String date2 = "2023-06-20T14:45:00.000Z";
        
        Calendar cal1 = CSUtil.parseDate(date1, TimeZone.getTimeZone("UTC"));
        Calendar cal2 = CSUtil.parseDate(date2, TimeZone.getTimeZone("UTC"));
        
        assertNotNull(cal1);
        assertNotNull(cal2);
        assertNotEquals(cal1.getTimeInMillis(), cal2.getTimeInMillis());
    }

    // ========== YEAR RANGE TESTS ==========

    @Test
    public void testParseDatePastYear() {
        String date = "1900-01-01T00:00:00.000Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        assertNotNull(calendar);
    }

    @Test
    public void testParseDateFutureYear() {
        String date = "2099-12-31T23:59:59.999Z";
        Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
        assertNotNull(calendar);
    }

    // ========== SPECIAL DATE FORMATS TESTS ==========

    @Test
    public void testParseDateWithMillisecondsVariations() {
        String[] dates = {
            "2023-01-15T10:30:00.000Z",
            "2023-01-15T10:30:00.100Z",
            "2023-01-15T10:30:00.999Z"
        };
        
        for (String date : dates) {
            Calendar calendar = CSUtil.parseDate(date, TimeZone.getTimeZone("UTC"));
            assertNotNull(calendar);
        }
    }
}

