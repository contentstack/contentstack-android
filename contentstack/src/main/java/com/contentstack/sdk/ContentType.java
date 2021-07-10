package com.contentstack.sdk;

import android.util.ArrayMap;
import android.text.TextUtils;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * BuiltClass provides {@link Entry} and {@link Query} instance.<br>
 *
 * @author contentstack.com, Inc
 */
public class ContentType {

    protected String TAG = ContentType.class.getSimpleName();
    protected String contentTypeName = null;
    protected Stack stackInstance = null;
    private ArrayMap<String, Object> localHeader = null;
    private ArrayMap<String, Object> stackHeader = null;

    private ContentType() {
    }

    protected ContentType(String contentTypeName) {
        this.contentTypeName = contentTypeName;
        this.localHeader = new ArrayMap<String, Object>();
    }

    protected void setStackInstance(Stack stack) {
        this.stackInstance = stack;
        this.stackHeader = stack.localHeader;
    }


    /**
     * To set headers for Built.io Contentstack rest calls.
     * <br>
     * Scope is limited to this object and followed classes.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     *              <br><br><b>Example :</b><br>
     *              <pre class="prettyprint">
     *               //'blt5d4sample2633b' is a dummy Stack API key
     *               //'blt6d0240b5sample254090d' is dummy access token.
     *               Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *               ContentType contentType = stack.contentType("form_name");<br>
     *
     *               contentType.setHeader("custom_key", "custom_value");
     *               </pre>
     */
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
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            //'blt5d4sample2633b' is a dummy Stack API key
     *             //'blt6d0240b5sample254090d' is dummy access token.
     *             Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *             ContentType contentType = stack.contentType("form_name");<br>
     *             contentType.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    protected Entry entry() {
        Entry entry = new Entry(contentTypeName);
        entry.formHeader = getHeader(localHeader);
        entry.setContentTypeInstance(this);

        return entry;
    }

    /**
     * Represents a {@link Entry}.
     * Create {@link Entry} instance.
     *
     * @param entryUid Set entry uid.
     * @return {@link Entry} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *  ContentType contentType = stack.contentType("form_name");<br>
     *  // setUid will identify the object, and calling save will update it
     *  ENTRY entry = contentType.entry("bltf4fbbc94e8c851db");
     *  </pre>
     */
    public Entry entry(String entryUid) {
        Entry entry = new Entry(contentTypeName);
        entry.formHeader = getHeader(localHeader);
        entry.setContentTypeInstance(this);
        entry.setUid(entryUid);

        return entry;
    }

    /**
     * Represents a {@link Query}.
     * Create {@link Query} instance.
     *
     * @return {@link Query} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *  ContentType contentType = stack.contentType("form_name");<br>
     *  Query csQuery = contentType.query();
     *  </pre>
     */
    public Query query() {
        Query query = new Query(contentTypeName);
        query.formHeader = getHeader(localHeader);
        query.setContentTypeInstance(this);

        return query;
    }


    /**
     * @param params   query parameters
     * @param callback ContentTypesCallback
     *                 This call returns information of a specific content type. It returns the content type schema, but does not include its entries.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre class="prettyprint">
     *                 ContentType  contentType = stack.contentType("content_type_uid");
     *                 JSONObject params = new JSONObject();
     *                 params.put("include_snippet_schema", true);
     *                 params.put("limit", 3);
     *                 contentType.fetch(new ContentTypesCallback() {
     *                 @Override
     *                 public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
     *                 if (error==null){
     *
     *                 }else {
     *
     *                 }
     *                 }
     *                 });
     *                 </pre>
     */


    public void fetch(JSONObject params, final ContentTypesCallback callback) {

        try {

            String URL = "/" + stackInstance.VERSION + "/content_types/" + contentTypeName;
            ArrayMap<String, Object> headers = getHeader(localHeader);
            if (params == null) {
                params = new JSONObject();
            }

            Iterator keys = params.keys();
            while (keys.hasNext()) {
                // loop to get the dynamic key
                String key = (String) keys.next();
                // get the value of the dynamic key
                Object value = params.opt(key);
                // do something here with the value...
                params.put(key, value);
            }

            if (headers.containsKey("environment")) {
                params.put("environment", headers.get("environment"));
            }

            if (contentTypeName != null && !contentTypeName.isEmpty()) {
                fetchContentTypes(URL, params, headers, null, callback);
            } else {
                Error error = new Error();
                error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
                callback.onRequestFail(ResponseType.UNKNOWN, error);
            }


        } catch (Exception e) {

            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }

    }


    private void fetchContentTypes(String urlString, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, ContentTypesCallback callback) {

        if (callback != null) {

            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, stackInstance, CSController.FETCHCONTENTTYPES, urlString, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.CONTENTTYPES.toString(), false, CSAppConstants.RequestMethod.GET, callback);
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
                    CSAppUtils.showLog(TAG, "------setQueryJson" + e.toString());
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
