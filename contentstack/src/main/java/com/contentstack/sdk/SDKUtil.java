package com.contentstack.sdk;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.jar.Attributes;
import java.util.stream.StreamSupport;

/**
 * @author contentstack.com
 */
public class SDKUtil {

    private static final String CLEAR_CACHE = "StartContentStackClearingCache";

    public SDKUtil() {
    }

    public static void showLog(String tag, String message) {
        if (SDKConstant.debug) {
            Log.i(tag, message);
        }
    }

    protected static void clearCache(Context context) {
        Intent alarmIntent = new Intent(CLEAR_CACHE);
        alarmIntent.setPackage(context.getPackageName());
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= 23) flag = PendingIntent.FLAG_IMMUTABLE | flag;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, flag);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_DAY, pendingIntent);
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
            showLog("appUtils", e.getLocalizedMessage());
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
     * @return SHA-256 value
     */
    public String getSHAFromString(String value) {
        String output;
        output = value.toString().trim();
        if (value.length() > 0) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
                digest.reset();
                digest.update(output.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                // deepcode ignore ApiMigration: <please specify a reason of ignoring this>
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
                showLog("appUtils", e.getLocalizedMessage());
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
     *                                                                                                                      Util.parseDate(dateString, TimeZone.getDefault());
     *                                                                                                                    </pre>
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
                Log.e("CSAppUtils", e.getLocalizedMessage());
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
     *                                                                                                                      BuiltUtil.parseDate(dateString, "yyyy-MM-dd'T'HH:mm:ssZ", TimeZone.getTimeZone("GMT"));
     *                                                                                                                    </pre>
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

        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hourOfDay), Integer.parseInt(min), Integer.parseInt(sec));

        month = null;
        day = null;
        year = null;
        hourOfDay = null;
        min = null;
        sec = null;
        dateObject = null;

        return cal;
    }

    public static void jsonToHTML(JSONArray entryArray, String[] keyPath, Option option) throws JSONException {
        for (int i = 0; i < entryArray.length(); i++) {
            Object jsonObj = entryArray.get(i);
            jsonToHTML((JSONObject) jsonObj, keyPath, option);
        }
    }

    public static void jsonToHTML(JSONObject entry, String[] keyPath, Option renderOption) throws JSONException {
        MetaToEmbedCallback converter = metadata -> {
            boolean available = entry.has("_embedded_items");
            if (available) {
                JSONObject jsonArray = entry.optJSONObject("_embedded_items");
                return findEmbeddedItems(jsonArray, metadata);
            }
            return Optional.empty();
        };
        ContentCallback callback = content -> {
            if (content instanceof JSONArray) {
                JSONArray contentArray = (JSONArray) content;
                return enumerateContents(contentArray, renderOption, converter);
            } else if (content instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) content;
                return enumerateContent(jsonObject, renderOption, converter);
            }
            return null;
        };
        for (String path: keyPath) {
            findContent(entry, path, callback);
        }
    }

    private static void findContent(JSONObject entryObj, String path, ContentCallback contentCallback) throws JSONException {
        String[] arrayString = path.split("\\.");
        getContent(arrayString, entryObj, contentCallback);
    }

    private static void getContent(String[] arrayString, JSONObject entryObj, ContentCallback contentCallback) throws JSONException {
        if (arrayString != null && arrayString.length != 0) {
            String path = arrayString[0];
            if (arrayString.length == 1) {
                Object varContent = entryObj.opt(path);
                if (varContent instanceof String || varContent instanceof JSONArray || varContent instanceof JSONObject) {
                    entryObj.put(path, contentCallback.contentObject(varContent));
                }
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(arrayString));
                list.remove(path);
                String[] newArrayString = list.toArray(new String[0]);
                if (entryObj.opt(path) instanceof JSONObject) {
                    getContent(newArrayString, entryObj.optJSONObject(path), contentCallback);
                } else if (entryObj.opt(path) instanceof JSONArray) {
                    JSONArray jsonArray = entryObj.optJSONArray(path);
                    for (int idx = 0; idx < jsonArray.length(); idx++) {
                        getContent(newArrayString, jsonArray.optJSONObject(idx), contentCallback);
                    }
                }
            }
        }
    }

    private static Optional<JSONObject> findEmbeddedItems(JSONObject jsonObject, Metadata metadata) {
        Set<String> allKeys = (Set<String>) jsonObject.keys();
        for (String key: allKeys) {
            JSONArray jsonArray = jsonObject.optJSONArray(key);

            Optional<JSONObject> filteredContent = Optional.empty();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if (jsonObject.optString("uid").equalsIgnoreCase(metadata.getItemUid())) {
                        filteredContent = Optional.of(obj);
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (filteredContent.isPresent()) {
                return filteredContent;
            }
        }
        return Optional.empty();
    }

    private static Object enumerateContents(JSONArray contentArray, Option renderObject, MetaToEmbedCallback item) {
        JSONArray jsonArrayRTEContent = new JSONArray();
        for (int i = 0; i < contentArray.length(); i++) {
            Object RTE = null;
            try {
                RTE = contentArray.get(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONObject jsonObject = (JSONObject) RTE;
            String renderContent = enumerateContent(jsonObject, renderObject, item);
            jsonArrayRTEContent.put(renderContent);
        }
        return jsonArrayRTEContent;
    }
    private static String enumerateContent(JSONObject jsonObject, Option renderObject, MetaToEmbedCallback item) {
        if (jsonObject.length() > 0 && jsonObject.has("type") && jsonObject.has("children")) {
            if (jsonObject.opt("type").equals("doc")) {
                return doRawProcessing(jsonObject.optJSONArray("children"), renderObject, item);
            }
        }
        return "";
    }
    private static String doRawProcessing(JSONArray children, Option renderObject, MetaToEmbedCallback embedItem) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < children.length(); i++) {
            Object item = null;
            try {
                item = children.get(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            JSONObject child;
            if (item instanceof JSONObject) {
                child = (JSONObject) item;
                stringBuilder.append(extractKeys(child, renderObject, embedItem));
            }
        }
        return stringBuilder.toString();
    }

    private static final String ASSET = "asset";
    private static String extractKeys(JSONObject jsonNode, Option renderObject, MetaToEmbedCallback embedItem) {
        if (!jsonNode.has("type") && jsonNode.has("text")) {
            return NodeToHTML.textNodeToHTML(jsonNode, renderObject);
        } else if (jsonNode.has("type")) {
            String nodeType = jsonNode.optString("type");
            if (nodeType.equalsIgnoreCase("reference")) {
                JSONObject attrObj = jsonNode.optJSONObject("attrs");
                String attrType = attrObj.optString("type");
                Metadata metadata;

                if (attrType.equalsIgnoreCase(ASSET)) {
                    String text = attrObj.optString("text");
                    String uid = attrObj.optString("asset-uid");
                    String style = attrObj.optString("display-type");
                    metadata = new Metadata(text, attrType, uid, ASSET, style, "", new Attributes());
                } else {
                    String text = attrObj.optString("text");
                    String uid = attrObj.optString("entry-uid");
                    String contentType = attrObj.optString("content-type-uid");
                    String style = attrObj.optString("display-type");
                    metadata = new Metadata(text, attrType, uid, contentType, style, "", new Attributes());
                }
                Optional<JSONObject> filteredContent = embedItem.toEmbed(metadata);
                if (filteredContent.isPresent()) {
                    JSONObject contentToPass = filteredContent.get();
                    return getStringOption(renderObject, metadata, contentToPass);
                } else {
                    if (attrType.equalsIgnoreCase(ASSET)) {
                        return renderObject.renderNode("img", jsonNode, nodeJsonArray -> doRawProcessing(nodeJsonArray, renderObject, embedItem));
                    }
                }
            } else {
                return renderObject.renderNode(nodeType, jsonNode, nodeJsonArray -> doRawProcessing(nodeJsonArray, renderObject, embedItem));
            }
        }
        return "";
    }
    private static String getStringOption(Option option, Metadata metadata, JSONObject contentToPass) {
        String stringOption = option.renderOptions(contentToPass, metadata);
        if (stringOption == null) {
            DefaultOption defaultOptions = new DefaultOption();
            stringOption = defaultOptions.renderOptions(contentToPass, metadata);
        }
        return stringOption;
    }
}

interface MetaToEmbedCallback {
    Optional<JSONObject> toEmbed(Metadata metadata);
}
interface ContentCallback {

    /**
     * The function contentObject takes an object as input and returns an object as output.
     *
     * @param content The content parameter is an object that represents the content to be passed to
     *                the contentObject function.
     * @return The contentObject is being returned.
     */
    Object contentObject(Object content);
}