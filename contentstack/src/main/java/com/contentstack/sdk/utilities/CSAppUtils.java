package com.contentstack.sdk.utilities;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author contentstack.com, Inc
 */
public class CSAppUtils {

    public CSAppUtils() {
    }

    public static void showLog(String tag, String message) {
        if (CSAppConstants.debug) {
            Log.i(tag, message);
        }
    }


    /**
     * To check if required response within given time window available in cache
     *
     * @param file cache file.
     * @param time time
     * @return true if cache data available which satisfy given time condition.
     */
    public boolean getResponseTimeFromCacheFile(File file, long time) {
        try {
            JSONObject jsonObj = getJsonFromCacheFile(file);
            long responseDate = Long.parseLong(jsonObj.optString("timestamp"));

            Calendar responseCalendar = Calendar.getInstance();

            responseCalendar.add(Calendar.MINUTE, 0);
            responseCalendar.set(Calendar.SECOND, 0);
            responseCalendar.set(Calendar.MILLISECOND, 0);
            responseCalendar.setTimeInMillis(responseDate);
            responseCalendar.getTimeInMillis();


            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(new Date());
            currentCalendar.getTimeInMillis();

            long dateDiff = (currentCalendar.getTimeInMillis() - responseCalendar.getTimeInMillis());
            long dateDiffInMin = dateDiff / (60 * 1000);


            if (dateDiffInMin > (time / 60000)) {
                return true;// need to send call.
            } else {
                return false;// no need to send call.
            }

        } catch (Exception e) {
            showLog("appUtils", "------------getJsonFromFilec catch-|" + e.toString());
            return false;
        }
    }

    /**
     * To retrieve data from cache.
     *
     * @param file cache file.
     * @return cache data in JSON.
     */
    public static JSONObject getJsonFromCacheFile(File file) {

        JSONObject json = null;
        InputStream input = null;
        ByteArrayOutputStream buffer = null;
        try {

            input = new BufferedInputStream(new FileInputStream(file));
            buffer = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int read;
            while ((read = input.read(temp)) > 0) {
                buffer.write(temp, 0, read);
            }
            json = new JSONObject(buffer.toString("UTF-8"));
            buffer.flush();
            buffer.close();
            input.close();
            return json;
        } catch (Exception e) {
            showLog("appUtils", "------------getJsonFromFilec catch-|" + e.toString());
            return null;
        }
    }

    /**
     * To encrypt given value.
     *
     * @param value string
     * @return MD5 value
     */
    public String getMD5FromString(String value) {
        String output;
        output = value.toString().trim();
        if (value.length() > 0) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                digest.reset();
                digest.update(output.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {

                    String hex = Integer.toHexString(0xFF & messageDigest[i]);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }

                return hexString.toString();

            } catch (Exception e) {
                showLog("appUtils", "------------getMD5FromString catch-|" + e.toString());
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * Converts the given date to user&#39;s timezone.
     *
     * @param date date in ISO format.
     * @return {@link Calendar} object.
     * @throws ParseException <br><br><b>Example :</b><br>
     *                        <pre class="prettyprint">
     *                          BuiltUtil.parseDate(dateString, TimeZone.getDefault());
     *                        </pre>
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
     * @author built.io. Inc
     */
    public static enum DateComapareType {

        WEEK, DAY, HOURS, MINUTES, SECONDS

    }

    ;
}
