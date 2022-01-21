package com.contentstack.sdk;

import android.util.ArrayMap;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;

import org.json.JSONArray;
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
    private String urlPath;
    private String controller;
    private ArrayMap<String, Object> headers;
    private String info;
    private JSONObject requestJSON;
    private IRequestModelHTTP connectionRequest;
    private ResultCallBack callBackObject;
    private CSAppConstants.RequestMethod requestMethod;
    private JSONObject responseJSON;

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
    public void setRequestMethod(CSAppConstants.RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public CSAppConstants.RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public JSONObject getResponse() {
        return responseJSON;
    }


    public String setFormParamsGET(HashMap<String, java.lang.Object> params) {
        if (params != null && params.size() > 0) {
            String urlParams = null;

            urlParams = info.equalsIgnoreCase(CSAppConstants.callController.QUERY.name()) || info.equalsIgnoreCase(CSAppConstants.callController.ENTRY.name()) ? getParams(params) : null;
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
                CSAppUtils.showLog(TAG, "--------------------getQueryParam--||" + e1.toString());
            }
        }

        return urlParams;
    }


    @Override
    public void send() {
        String url = null;
        String protocol = CSAppConstants.URLSCHEMA_HTTPS;
        int requestId = getRequestId(requestMethod);
        final HashMap<String, String> headers = new HashMap<>();
        int count = this.headers.size();

        if (requestMethod == CSAppConstants.RequestMethod.GET) {
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
        headers.put("X-User-Agent", "contentstack-android/" + CSAppConstants.SDK_VERSION);


        jsonObjectRequest = new JSONUTF8Request(requestId, url, requestJSON, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responseJSON = response;
                if (responseJSON != null) {
                    connectionRequest.onRequestFinished(CSHttpConnection.this);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                generateBuiltError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(CSAppConstants.TimeOutDuration, CSAppConstants.NumRetry, CSAppConstants.BackOFMultiplier));
        jsonObjectRequest.setShouldCache(false);
        Contentstack.addToRequestQueue(protocol, jsonObjectRequest, info);

    }


    private void httpRequest(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        responseJSON = response;
                        if (responseJSON != null) {
                            connectionRequest.onRequestFinished(CSHttpConnection.this);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        generateBuiltError(error);

                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(CSAppConstants.TimeOutDuration, CSAppConstants.NumRetry, CSAppConstants.BackOFMultiplier));
        jsonObjectRequest.setShouldCache(false);
        Contentstack.addToRequestQueue("https://", jsonObjectRequest, info);

    }

    private String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : ("Android" + System.getProperty("java.version"));
    }

    private int getRequestId(CSAppConstants.RequestMethod requestMethod) {
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
            responseJSON.put("error_message", CSAppConstants.ErrorMessage_Default);

            if (error != null) {

                try {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        statusCode = error.networkResponse.statusCode;
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        responseJSON = responseBody != null ? new JSONObject(responseBody) : new JSONObject();

                    } else {
                        if (error.toString().equalsIgnoreCase("NoConnectionError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_VolleyNoConnectionError);

                        } else if (error.toString().equalsIgnoreCase("AuthFailureError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_VolleyAuthFailureError);

                        } else if (error.toString().equalsIgnoreCase("NetworkError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_NoNetwork);

                        } else if (error.toString().equalsIgnoreCase("ParseError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_VolleyParseError);

                        } else if (error.toString().equalsIgnoreCase("ServerError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_VolleyServerError);

                        } else if (error.toString().equalsIgnoreCase("TimeoutError")) {

                            responseJSON.put("error_message", CSAppConstants.ErrorMessage_VolleyServerError);

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
            CSAppUtils.showLog(TAG, exception.toString());
        }
    }

}
