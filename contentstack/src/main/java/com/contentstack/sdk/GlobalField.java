package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class GlobalField {
    protected String TAG = GlobalField.class.getSimpleName();
    protected String global_field_uid = null;
    protected Stack stackInstance = null;
    private ArrayMap<String, Object> localHeader = null;
    private ArrayMap<String, Object> stackHeader = null;

    JSONObject urlQueries = new JSONObject();

    protected GlobalField() {
        this.localHeader = new ArrayMap<>();
    }

    protected GlobalField (String global_field_uid) {
        this.global_field_uid = global_field_uid;
        this.localHeader = new ArrayMap<>();
    }

    protected void setStackInstance(Stack stack) {
        this.stackInstance = stack;
        this.stackHeader = stack.localHeader;
    }

    /**
     * To set headers for Contentstack rest calls.
     * <br>
     * Scope is limited to this object and followed classes.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     **/

    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            localHeader.put(key, value);
        }
    }

    /**
     * Remove header key.
     *
     * @param key custom_header_key
     *
     *            <br>
     *            <br>
     *            <b>Example :</b><br>
     * 
     *            <pre class="prettyprint">
     **/
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    /**
     *
     *
     * @throws IllegalAccessException
     *                                illegal access exception
     */

    public GlobalField includeBranch() {
        try {
            urlQueries.put("include_branch", true);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return this;
    }

    public GlobalField includeGlobalFieldSchema() {
        try {
            urlQueries.put("include_global_field_schema", true);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return this;
    }

    public void fetch( GlobalFieldsResultCallback callback) {
        try {
            String URL = "/" + stackInstance.VERSION + "/global_fields/" + global_field_uid;
            ArrayMap<String, Object> headers = getHeader(localHeader);

            if (global_field_uid != null && !global_field_uid.isEmpty()) {
                fetchGlobalFields(URL, urlQueries, headers, null, callback);
            } else {
                Error error = new Error();
                error.setErrorMessage(SDKConstant.PLEASE_PROVIDE_VALID_JSON);
                callback.onRequestFail(ResponseType.UNKNOWN, error);
            }
        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(SDKConstant.PLEASE_PROVIDE_GLOBAL_FIELD_UID);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }

    }

    public void findAll(final GlobalFieldsResultCallback callback) {
        try {
            String URL = "/" + stackInstance.VERSION + "/global_fields";
            ArrayMap<String, Object> headers = getHeader(localHeader);
            fetchGlobalFields(URL, urlQueries, headers, null, callback);
        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(SDKConstant.ERROR_MESSAGE_DEFAULT);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }

    private void fetchGlobalFields(String urlString, JSONObject urlQueries, ArrayMap<String, Object> headers,
            String cacheFilePath, GlobalFieldsResultCallback callback) {

        if (callback != null) {

            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, stackInstance, SDKController.GET_GLOBAL_FIELDS, urlString, headers, urlParams,
                    new JSONObject(), cacheFilePath, SDKConstant.callController.GLOBAL_FIELDS.toString(), false,
                    SDKConstant.RequestMethod.GET, callback);
        }
    }

    private HashMap<String, Object> getUrlParams(JSONObject urlQueriesJSON) {

        HashMap<String, Object> hashMap = new HashMap<>();

        if (urlQueriesJSON != null && urlQueriesJSON.length() > 0) {
            Iterator<String> iter = urlQueriesJSON.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = urlQueriesJSON.opt(key);
                    hashMap.put(key, value);
                } catch (Exception e) {
                    SDKUtil.showLog(TAG, "------setQueryJson" + e.toString());
                }
            }

            return hashMap;
        }

        return null;
    }

    private ArrayMap<String, Object> getHeader(ArrayMap<String, Object> localHeader) {
        ArrayMap<String, Object> mainHeader = stackHeader;
        ArrayMap<String, Object> classHeaders = new ArrayMap<>();

        if (localHeader != null && localHeader.size() > 0) {
            if (mainHeader != null && mainHeader.size() > 0) {
                for (Map.Entry<String, Object> entry : localHeader.entrySet()) {
                    String key = entry.getKey();
                    classHeaders.put(key, entry.getValue());
                }

                for (Map.Entry<String, Object> entry : mainHeader.entrySet()) {
                    String key = entry.getKey();
                    if (!classHeaders.containsKey(key)) {
                        classHeaders.put(key, entry.getValue());
                    }
                }

                return classHeaders;

            } else {
                return localHeader;
            }

        } else {
            return stackHeader;
        }
    }
}
