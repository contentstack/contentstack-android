package com.contentstack.sdk;

import android.util.ArrayMap;

import com.contentstack.sdk.utilities.CSAppConstants;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author contentstack.com, Inc
 */
class CSBackgroundTask {

    public CSBackgroundTask(Query queryInstance, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlQueries, JSONObject jsonMain, String cacheFilePath, String requestInfo, CSAppConstants.RequestMethod method, ResultCallBack callback) {

        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(queryInstance);
                csConnectionRequest.setQueryInstance(queryInstance);
                csConnectionRequest.setURLQueries(urlQueries);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callback);

            } else {
                sendErrorForHeader(callback);
            }
        } else {
            sendErrorToUser(callback);
        }
    }

    public CSBackgroundTask(Entry entryInstance, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlQueries, JSONObject jsonMain, String cacheFilePath, String requestInfo, boolean isOffline, CSAppConstants.RequestMethod method, ResultCallBack callBack) {
        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(entryInstance);
                csConnectionRequest.setURLQueries(urlQueries);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callBack);

            } else {
                sendErrorForHeader(callBack);
            }
        } else {
            sendErrorToUser(callBack);
        }
    }

    public CSBackgroundTask(AssetLibrary assetLibrary, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlQueries, JSONObject jsonMain, String cacheFilePath, String requestInfo, boolean isOffline, CSAppConstants.RequestMethod method, ResultCallBack callback) {
        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(assetLibrary);
                csConnectionRequest.setURLQueries(urlQueries);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callback);

            } else {
                sendErrorForHeader(callback);
            }
        } else {
            sendErrorToUser(callback);
        }
    }

    public CSBackgroundTask(Asset asset, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlQueries, JSONObject jsonMain, String cacheFilePath, String requestInfo, boolean isOffline, CSAppConstants.RequestMethod method, ResultCallBack callback) {
        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(asset);
                csConnectionRequest.setURLQueries(urlQueries);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callback);

            } else {
                sendErrorForHeader(callback);
            }
        } else {
            sendErrorToUser(callback);
        }
    }


    public CSBackgroundTask(Stack stack, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlParams, JSONObject jsonMain, String cacheFilePath, String requestInfo, boolean b, CSAppConstants.RequestMethod method, ResultCallBack callback) {

        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(stack);
                csConnectionRequest.setStackInstance(stack);
                csConnectionRequest.setURLQueries(urlParams);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callback);

            } else {
                sendErrorForHeader(callback);
            }
        } else {
            sendErrorToUser(callback);
        }
    }


    public CSBackgroundTask(ContentType contentType, Stack stackInstance, String controller, String url, ArrayMap<String, Object> headers, HashMap<String, Object> urlParams, JSONObject jsonMain, String cacheFilePath, String requestInfo, boolean b, CSAppConstants.RequestMethod method, ResultCallBack callback) {

        if (CSAppConstants.isNetworkAvailable) {
            if (headers != null && headers.size() > 0) {

                String URL = stackInstance.URLSCHEMA + stackInstance.URL + url;

                CSConnectionRequest csConnectionRequest = new CSConnectionRequest(contentType);
                csConnectionRequest.setContentTypeInstance(contentType);
                csConnectionRequest.setURLQueries(urlParams);
                csConnectionRequest.setParams(URL, method, controller, jsonMain, headers, cacheFilePath, requestInfo, callback);

            } else {
                sendErrorForHeader(callback);
            }
        } else {
            sendErrorToUser(callback);
        }
    }


    private void sendErrorToUser(ResultCallBack callbackObject) {
        Error error = new Error();
        error.setErrorCode(CSAppConstants.NONETWORKCONNECTION);
        error.setErrorMessage(CSAppConstants.ErrorMessage_NoNetwork);
        if (callbackObject != null) {
            callbackObject.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }

    private void sendErrorForHeader(ResultCallBack callbackObject) {
        Error error = new Error();
        error.setErrorMessage(CSAppConstants.ErrorMessage_CalledDefaultMethod);
        if (callbackObject != null) {
            callbackObject.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }
}
