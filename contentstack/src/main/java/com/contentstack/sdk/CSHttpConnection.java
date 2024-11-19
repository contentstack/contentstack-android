package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A class that defines a query that is used to query for {@link Entry} instance.
 *
 * @author contentstack.com, Inc
 */
class CSHttpConnection implements IURLRequestHTTP {

    private static final String TAG = "CSHttpConnection";
    private final String urlPath;
    private String controller;
    private ArrayMap<String, Object> headers;
    private String info;
    private JSONObject requestJSON;
    private final IRequestModelHTTP connectionRequest;
    private ResultCallBack callBackObject;
    private SDKConstant.RequestMethod requestMethod;
    private JSONObject responseJSON;
    private Config config;

    public HashMap<String, Object> getFormParams() {
        return formParams;
    }

    public void setFormParams(HashMap<String, Object> formParams) {
        this.formParams = formParams;
    }

    private HashMap<String, Object> formParams;
    private JSONUTF8Request jsonObjectRequest;
    private boolean treatDuplicateKeysAsArrayItems;

    public CSHttpConnection(String urlToCall, IRequestModelHTTP csConnectionRequest) {
        this.urlPath = urlToCall;
        this.connectionRequest = csConnectionRequest;
    }

    @Override
    public void setController(String controller) {
        this.controller = controller;
    }

    @Override
    public String getController() {
        return controller;
    }

    @Override
    public void setHeaders(ArrayMap headers) {
        this.headers = headers;
    }

    @Override
    public ArrayMap getHeaders() {
        return headers;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getInfo() {
        return info;
    }

    public void setFormParamsPOST(JSONObject requestJSON) {
        this.requestJSON = null;
        this.requestJSON = requestJSON;
    }

    @Override
    public void setCallBackObject(ResultCallBack callBackObject) {
        this.callBackObject = callBackObject;
    }

    @Override
    public ResultCallBack getCallBackObject() {
        return callBackObject;
    }

    @Override
    public void setTreatDuplicateKeysAsArrayItems(boolean treatDuplicateKeysAsArrayItems) {
        this.treatDuplicateKeysAsArrayItems = treatDuplicateKeysAsArrayItems;
    }

    @Override
    public boolean getTreatDuplicateKeysAsArrayItems() {
        return treatDuplicateKeysAsArrayItems;
    }

    @Override
    public void setRequestMethod(SDKConstant.RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public SDKConstant.RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public JSONObject getResponse() {
        return responseJSON;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String setFormParamsGET(HashMap<String, java.lang.Object> params) {
        if (params != null && params.size() > 0) {
            String urlParams = null;

            urlParams = info.equalsIgnoreCase(SDKConstant.callController.QUERY.name()) || info.equalsIgnoreCase(SDKConstant.callController.ENTRY.name()) ? getParams(params) : null;
            if (TextUtils.isEmpty(urlParams)) {
                for (Map.Entry<String, Object> e : params.entrySet()) {

                    if (urlParams == null) {
                        urlParams = "?" + e.getKey() + "=" + e.getValue();
                    } else {
                        urlParams += "&" + e.getKey() + "=" + e.getValue();
                    }
                }
            }
            return urlParams;
        }
        return null;
    }


    private String getParams(HashMap<String, Object> params) {
        String urlParams = "?";
        for (Map.Entry<String, Object> e : params.entrySet()) {

            String key = e.getKey();
            Object value = e.getValue();

            try {

                if (key.equalsIgnoreCase("include[]")) {
                    key = URLEncoder.encode(key, "UTF-8");
                    JSONArray array = (JSONArray) value;

                    for (int i = 0; i < array.length(); i++) {
                        urlParams += urlParams.equals("?") ? key + "=" + array.opt(i) : "&" + key + "=" + array.opt(i);
                    }

                } else if (key.equalsIgnoreCase("only[BASE][]")) {
                    key = URLEncoder.encode(key, "UTF-8");
                    JSONArray array = (JSONArray) value;

                    for (int i = 0; i < array.length(); i++) {
                        urlParams += urlParams.equals("?") ? key + "=" + array.opt(i) : "&" + key + "=" + array.opt(i);
                    }
                } else if (key.equalsIgnoreCase("except[BASE][]")) {
                    key = URLEncoder.encode(key, "UTF-8");
                    JSONArray array = (JSONArray) value;

                    for (int i = 0; i < array.length(); i++) {
                        urlParams += urlParams.equals("?") ? key + "=" + array.opt(i) : "&" + key + "=" + array.opt(i);
                    }
                } else if (key.equalsIgnoreCase("only")) {
                    JSONObject onlyJSON = (JSONObject) value;

                    Iterator<String> iter = onlyJSON.keys();
                    while (iter.hasNext()) {
                        String innerKey = iter.next();
                        JSONArray array = (JSONArray) onlyJSON.optJSONArray(innerKey);
                        innerKey = URLEncoder.encode("only[" + innerKey + "][]", "UTF-8");
                        for (int i = 0; i < array.length(); i++) {
                            urlParams += urlParams.equals("?") ? innerKey + "=" + array.opt(i) : "&" + innerKey + "=" + array.opt(i);
                        }
                    }

                } else if (key.equalsIgnoreCase("except")) {
                    JSONObject onlyJSON = (JSONObject) value;

                    Iterator<String> iter = onlyJSON.keys();
                    while (iter.hasNext()) {
                        String innerKey = iter.next();
                        JSONArray array = (JSONArray) onlyJSON.optJSONArray(innerKey);
                        innerKey = URLEncoder.encode("except[" + innerKey + "][]", "UTF-8");
                        for (int i = 0; i < array.length(); i++) {
                            urlParams += urlParams.equals("?") ? innerKey + "=" + array.opt(i) : "&" + innerKey + "=" + array.opt(i);
                        }
                    }

                } else if (key.equalsIgnoreCase("query")) {
                    JSONObject queryJSON = (JSONObject) value;

                    urlParams += urlParams.equals("?") ? key + "=" + URLEncoder.encode(queryJSON.toString(), "UTF-8") : "&" + key + "=" + URLEncoder.encode(queryJSON.toString(), "UTF-8");

                } else {
                    urlParams += urlParams.equals("?") ? key + "=" + value : "&" + key + "=" + value;
                }

            } catch (Exception e1) {
                SDKUtil.showLog(TAG, "-getQueryParam--||" + e1.toString());
            }
        }

        return urlParams;
    }
    @Override
    public void send() {
        String url = null;
        int requestId = getRequestId(requestMethod);
        final HashMap<String, String> headers = new HashMap<>();
        if (requestMethod == SDKConstant.RequestMethod.GET) {
            String params = setFormParamsGET(formParams);
            if (params != null) {
                url = urlPath + params;
            } else {
                url = urlPath;
            }
        } else {
            url = urlPath;
        }

        for (Map.Entry<String, Object> entry : this.headers.entrySet()) {
            String key = entry.getKey();
            headers.put(key, (String) entry.getValue());
        }
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", defaultUserAgent());
        headers.put("X-User-Agent", "contentstack-delivery-android/" + SDKConstant.SDK_VERSION);
        jsonObjectRequest = new JSONUTF8Request(requestId, url, requestJSON, response -> {
            this.responseJSON = response;
            if (this.responseJSON != null) {
                // Handle Live Preview if applicable
//                if (this.config.livePreviewEntry != null) {
//                    try {
//                        handleJSONArray();
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
                connectionRequest.onRequestFinished(CSHttpConnection.this);
            }
        }, this::generateBuiltError) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(SDKConstant.TimeOutDuration, SDKConstant.NumRetry, SDKConstant.BackOFMultiplier));
        jsonObjectRequest.setShouldCache(false);
        Contentstack.addToRequestQueue(SDKConstant.PROTOCOL, jsonObjectRequest, info);
    }
//    void handleJSONArray() throws JSONException {
//        if (responseJSON.has("entries")) {
//            Object entriesObj = responseJSON.get("entries");
//            if (entriesObj instanceof JSONArray) {
//                JSONArray finalEntries = (JSONArray) entriesObj;
//                int length = finalEntries.length();
//                for (int idx = 0; idx < length; idx++) {
//                    Object obj = finalEntries.get(idx);
//                    if (obj instanceof JSONObject) {
//                        handleJSONObject(finalEntries, (JSONObject) obj, idx);
//                    }
//                }
//            }
//        }
//
//        if (responseJSON.has("entry")) {
//            Object entryObj = responseJSON.get("entry");
//            if (entryObj instanceof JSONObject) {
//                JSONObject entry = (JSONObject) entryObj;
//                if (entry.has("uid")) {
//                    Object entryUid = entry.get("uid");
//                    Object previewUid = config.livePreviewEntry.get("uid");
//
//                    if (entryUid != null && previewUid != null &&
//                            entryUid.toString().equals(previewUid.toString())) {
//                        JSONObject newResponse = new JSONObject();
//                        newResponse.put("entry", config.livePreviewEntry);
//                        responseJSON = newResponse;
//                    }
//                }
//            }
//        }
//    }
//
//    void handleJSONObject(JSONArray arrayEntry, JSONObject jsonObj, int idx) throws JSONException {
//        if (jsonObj != null && jsonObj.has("uid")) {
//            Object objUid = jsonObj.get("uid");
//            Object previewUid = config.livePreviewEntry.get("uid");
//
//            if (objUid != null && previewUid != null &&
//                    objUid.toString().equals(previewUid.toString())) {
//                // Create temporary array with modified content
//                JSONArray tempArray = new JSONArray();
//                int length = arrayEntry.length();
//                for (int i = 0; i < length; i++) {
//                    if (i == idx) {
//                        tempArray.put(config.livePreviewEntry);
//                    } else {
//                        tempArray.put(arrayEntry.get(i));
//                    }
//                }
//
//                // Create new response object
//                JSONObject newResponse = new JSONObject();
//                newResponse.put("entries", tempArray);
//                responseJSON = newResponse;
//            }
//        }
//    }
    private boolean isValidJSONObjectKey(JSONObject obj, String key) {
        try {
            return obj != null && obj.has(key) && obj.get(key) != null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    void handleJSONArray() throws JSONException {
    JSONArray finalEntries = responseJSON.optJSONArray("entries");
    if (finalEntries != null) {
        for (int idx = 0; idx < finalEntries.length(); idx++) {
            JSONObject objJSON = finalEntries.optJSONObject(idx);
            handleJSONObject(finalEntries, objJSON, idx);
        }
    }

    JSONObject entry = responseJSON.optJSONObject("entry");
    if (isValidJSONObjectKey(entry, "uid")) {
        String entryUid = entry.optString("uid");
        String previewUid = config.livePreviewEntry.optString("uid");

        if (entryUid.equals(previewUid)) {
            responseJSON = new JSONObject().put("entry", config.livePreviewEntry);
        }
    }
}

    void handleJSONObject(JSONArray arrayEntry, JSONObject jsonObj, int idx) throws JSONException {
        if (isValidJSONObjectKey(jsonObj, "uid")) {
            String objUid = jsonObj.optString("uid");
            String previewUid = config.livePreviewEntry.optString("uid");

            if (objUid.equals(previewUid)) {
                arrayEntry.put(idx, config.livePreviewEntry);
            }
        }
    }

    private String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : ("Android" + System.getProperty("java.version"));
    }

    private int getRequestId(SDKConstant.RequestMethod requestMethod) {
        switch (requestMethod) {
            case GET:
                return 0;
            case POST:
                return 1;
            case PUT:
                return 2;
            case DELETE:
                return 3;
            default:
                return 1;
        }
    }

    private void generateBuiltError(VolleyError error) {
        try {
            int statusCode = 0;
            responseJSON = new JSONObject();
            responseJSON.put("error_message", SDKConstant.ERROR_MESSAGE_DEFAULT);

            if (error != null) {

                try {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        statusCode = error.networkResponse.statusCode;
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        responseJSON = responseBody != null ? new JSONObject(responseBody) : new JSONObject();

                    } else {
                        if (error.toString().equalsIgnoreCase("NoConnectionError")) {

                            responseJSON.put("error_message", SDKConstant.CONNECTION_ERROR);

                        } else if (error.toString().equalsIgnoreCase("AuthFailureError")) {

                            responseJSON.put("error_message", SDKConstant.AUTHENTICATION_NOT_PRESENT);

                        } else if (error.toString().equalsIgnoreCase("NetworkError")) {

                            responseJSON.put("error_message", SDKConstant.NOT_AVAILABLE);

                        } else if (error.toString().equalsIgnoreCase("ParseError")) {

                            responseJSON.put("error_message", SDKConstant.PARSING_ERROR);

                        } else if (error.toString().equalsIgnoreCase("ServerError")) {

                            responseJSON.put("error_message", SDKConstant.TRY_AGAIN);

                        } else if (error.toString().equalsIgnoreCase("TimeoutError")) {

                            responseJSON.put("error_message", SDKConstant.TRY_AGAIN);

                        } else {
                            if (error.getMessage() != null) {
                                responseJSON.put("error_message", error.getMessage());
                            }
                        }

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("errors", error.toString());
                        responseJSON.put("errors", jsonObject);

                    }
                    connectionRequest.onRequestFailed(responseJSON, statusCode, callBackObject);

                } catch (Exception e) {
                    connectionRequest.onRequestFailed(responseJSON, 0, callBackObject);
                }
            } else {
                connectionRequest.onRequestFailed(responseJSON, 0, callBackObject);
            }
        } catch (Exception exception) {
            SDKUtil.showLog(TAG, exception.toString());
        }
    }

}
