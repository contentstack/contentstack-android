package com.contentstack.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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
public class CSUtil {

    /**
     * Converts the given date to user&#39;s timezone.
     *
     * @param date date in ISO format.
     * @return {@link Calendar} object.
     */
    public static Calendar parseDate(String date, TimeZone timeZone) {
        ArrayList<String> knownPatterns = new ArrayList<>();
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
                Log.e("CSUtils", e.getLocalizedMessage());
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
     *                                                                                                                    Util.parseDate(dateString, "yyyy-MM-dd'T'HH:mm:ssZ", TimeZone.getTimeZone("GMT"));
     *                                                                                                                  </pre>
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

        assert dateObject != null;
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

        cal.set(Integer.parseInt(year),
                Integer.parseInt(month) - 1,
                Integer.parseInt(day),
                Integer.parseInt(hourOfDay),
                Integer.parseInt(min),
                Integer.parseInt(sec));

        month = null;
        day = null;
        year = null;
        hourOfDay = null;
        min = null;
        sec = null;
        dateObject = null;
        return cal;
    }


    protected static void isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0) != null || connectivityManager.getNetworkInfo(1).getState() != null) {
            SDKConstant.IS_NETWORK_AVAILABLE = connectivityManager.getActiveNetworkInfo() != null;
        } else
            SDKConstant.IS_NETWORK_AVAILABLE = connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
    }
}
