package com.contentstack.sdk.utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper class of utilities.
 *
 * @author contentstack.com, Inc
 */
public class ContentstackUtil {

    /**
     * Converts the given date to user&#39;s timezone.
     *
     * @param date date in ISO format.
     * @return {@link Calendar} object.
     * @throws ParseException <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *  BuiltUtil.parseDate(dateString, TimeZone.getDefault());
     *  </pre>
     */
    public static Calendar parseDate(String date, TimeZone timeZone) throws ParseException {
        ArrayList<String> knownPatterns = new ArrayList<String>();
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ssZ");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ss'Z'");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm.ss'Z'");
        knownPatterns.add("yyyy-MM-dd'T'HH:mmZ");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm'Z'");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm'Z'");
        knownPatterns.add("yyyy-MM-dd'T'HH:mm:ss");
        knownPatterns.add("yyyy-MM-dd' 'HH:mm:ss");
        knownPatterns.add("yyyy-MM-dd");
        knownPatterns.add("HH:mm:ssZ");
        knownPatterns.add("HH:mm:ss'Z'");

        for (String formatString : knownPatterns) {
            try {

                return parseDate(date, formatString, timeZone);

            } catch (ParseException e) {
            }
        }

        return null;
    }

    /**
     * Converts the given date to the user&#39;s timezone.
     *
     * @param date       date in string format.
     * @param dateFormat date format.
     * @return {@link Calendar} object.
     * @throws ParseException <br><br><b>Example :</b><br>
     *                        <pre class="prettyprint">
     *                          BuiltUtil.parseDate(dateString, "yyyy-MM-dd'T'HH:mm:ssZ", TimeZone.getTimeZone("GMT"));
     *                        </pre>
     */
    @SuppressLint("SimpleDateFormat")
    public static Calendar parseDate(String date, String dateFormat, TimeZone timeZone) throws ParseException {
        Date dateObject = null;
        String month = "";
        String day = "";
        String year = "";
        String hourOfDay = "";
        String min = "";
        String sec = "";
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        dateObject = dateFormatter.parse(date);

        month = new SimpleDateFormat("MM").format(dateObject);
        day = new SimpleDateFormat("dd").format(dateObject);
        year = new SimpleDateFormat("yyyy").format(dateObject);
        hourOfDay = new SimpleDateFormat("HH").format(dateObject);
        min = new SimpleDateFormat("mm").format(dateObject);
        sec = new SimpleDateFormat("ss").format(dateObject);

        if (timeZone != null) {
            cal.setTimeZone(timeZone);
        } else {
            cal.setTimeZone(TimeZone.getDefault());
        }

        cal.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day), Integer.valueOf(hourOfDay), Integer.valueOf(min), Integer.valueOf(sec));

        month = null;
        day = null;
        year = null;
        hourOfDay = null;
        min = null;
        sec = null;
        dateObject = null;

        return cal;
    }

    /**
     * Type to compare dates.
     *
     * @author built.io, Inc
     */
    public static enum DateComapareType {

        WEEK, DAY, HOURS, MINUTES, SECONDS

    }

    ;
}
