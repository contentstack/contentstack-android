package com.contentstack.sdk;


import android.util.ArrayMap;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author contentstack.com, Inc
 */
class CSConnectionRequest implements IRequestModelHTTP {

    private static final String TAG = "CSConnectionRequest";

    private String urlToCall;
    private CSAppConstants.RequestMethod method;
    private String controller;
    private JSONObject paramsJSON;
    private ArrayMap<String, Object> header;
    private HashMap<String, Object> urlQueries;
    private String cacheFileName;
    private String requestInfo;
    private ResultCallBack callBackObject;
    private CSHttpConnection connection;
    private JSONObject responseJSON;
    private INotifyClass notifyClass;
    private INotifyClass assetLibrary;

    private Stack stackInstance;
    private Entry entryInstance;
    private Query queryInstance;
    private Asset assetInstance;
    private ContentType contentTypeInstance;
    private JSONObject errorJObject;
    private Error errorObject = new Error();

    public CSConnectionRequest() {
    }

    public CSConnectionRequest(Query queryInstance) {
        notifyClass = queryInstance;
    }

    public CSConnectionRequest(Entry entryInstance) {
        this.entryInstance = entryInstance;
    }

    public CSConnectionRequest(INotifyClass assetLibrary) {
        this.assetLibrary = assetLibrary;
    }

    public CSConnectionRequest(Asset asset) {
        this.assetInstance = asset;
    }

    public CSConnectionRequest(ContentType contentType) {
        this.contentTypeInstance = contentType;
    }

    public void setQueryInstance(Query queryInstance) {
        this.queryInstance = queryInstance;
    }

    public void setURLQueries(HashMap<String, Object> urlQueries) {
        this.urlQueries = urlQueries;
    }

    public void setStackInstance(Stack stackInstance) {
        this.stackInstance = stackInstance;
    }

    public void setContentTypeInstance(ContentType contentTypeInstance) {
        this.contentTypeInstance = contentTypeInstance;
    }

    public void setParams(Object... objects) {
        CSAppUtils.showLog(TAG, "ParallelTasks------|" + objects[0] + " started");

        this.urlToCall = (String) objects[0];
        this.method = (CSAppConstants.RequestMethod) objects[1];
        this.controller = (String) objects[2];
        paramsJSON = (JSONObject) objects[3];
        this.header = (ArrayMap<String, Object>) objects[4];

        if (objects[5] != null) {
            cacheFileName = (String) objects[5];
        }

        if (objects[6] != null) {
            requestInfo = (String) objects[6];
        }

        if (objects[7] != null) {

            callBackObject = (ResultCallBack) objects[7];
        }

        sendRequest();
    }

    @Override
    public void sendRequest() {
        connection = new CSHttpConnection(urlToCall, this);
        connection.setController(controller);
        connection.setHeaders(header);
        connection.setInfo(requestInfo);
        connection.setFormParamsPOST(paramsJSON);
        connection.setCallBackObject(callBackObject);

        if (urlQueries != null && urlQueries.size() > 0) {
            connection.setFormParams(urlQueries);
        }

        connection.setRequestMethod(method);
        connection.send();

    }

    @Override
    public void onRequestFailed(JSONObject error, int statusCode, ResultCallBack callBackObject) {

        String errorMessage = null;
        int errorCode = statusCode;
        HashMap<String, Object> resultHashMap = null;

        try {
            errorJObject = error;

            if (errorJObject != null) {
                errorMessage = (errorJObject).isNull("error_message") ? "" : (errorJObject).optString("error_message");

                if ((!errorJObject.isNull("error_code")) && (!errorJObject.optString("error_code").contains(" "))) {
                    errorCode = (Integer) errorJObject.opt("error_code");
                }

                if (!errorJObject.isNull("errors")) {
                    resultHashMap = new HashMap<String, Object>();
                    if (errorJObject.opt("errors") instanceof JSONObject) {
                        JSONObject errorsJsonObj = errorJObject.optJSONObject("errors");
                        Iterator<String> iterator = errorsJsonObj.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            Object value = errorsJsonObj.opt(key);
                            resultHashMap.put(key, value);
                        }
                    } else {
                        resultHashMap.put("errors", errorJObject.get("errors"));
                    }
                }
            }

        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "------------------catch 210 urlReq---|" + e);
            errorMessage = e.getLocalizedMessage();
        }
        if (errorMessage == null || (!(errorMessage.length() > 0))) {
            errorMessage = CSAppConstants.ErrorMessage_Default;
        }
        errorObject.setErrorCode(errorCode);
        errorObject.setErrorMessage(errorMessage);
        errorObject.setErrors(resultHashMap);

        if (this.callBackObject != null) {
            this.callBackObject.onRequestFail(ResponseType.NETWORK, errorObject);
        }

    }

    @Override
    public void onRequestFinished(CSHttpConnection request) {

        responseJSON = request.getResponse();

        String controller = request.getController();
        if (cacheFileName != null) {
            createFileIntoCacheDir(responseJSON);
        }

        if (controller.equalsIgnoreCase(CSController.QUERYOBJECT)) {

            EntriesModel model = new EntriesModel(responseJSON, null, false);
            notifyClass.getResult(model.formName, null);
            notifyClass.getResultObject(model.objectList, responseJSON, false);
            model = null;

        } else if (controller.equalsIgnoreCase(CSController.SINGLEQUERYOBJECT)) {

            EntriesModel model = new EntriesModel(responseJSON, null, false);
            notifyClass.getResult(model.formName, null);
            notifyClass.getResultObject(model.objectList, responseJSON, true);
            model = null;

        } else if (controller.equalsIgnoreCase(CSController.FETCHENTRY)) {

            EntryModel model = new EntryModel(responseJSON, null, false, false, false);
            entryInstance.resultJson = model.jsonObject;
            entryInstance.ownerEmailId = model.ownerEmailId;
            entryInstance.ownerUid = model.ownerUid;
            entryInstance.title = model.title;
            entryInstance.url = model.url;
            entryInstance.language = model.language;
            if (model.ownerMap != null) {
                entryInstance.owner = new HashMap<>(model.ownerMap);
            }
            if (model._metadata != null) {
                entryInstance._metadata = new HashMap<>(model._metadata);
            }
            entryInstance.uid = model.entryUid;
            entryInstance.setTags(model.tags);
            model = null;

            if (request.getCallBackObject() != null) {
                ((EntryResultCallBack) request.getCallBackObject()).onRequestFinish(ResponseType.NETWORK);
            }

        } else if (controller.equalsIgnoreCase(CSController.FETCHALLASSETS)) {
            AssetsModel assetsModel = new AssetsModel(responseJSON, false);
            List<Object> objectList = assetsModel.objects;
            assetsModel = null;

            assetLibrary.getResultObject(objectList, responseJSON, false);

        } else if (controller.equalsIgnoreCase(CSController.FETCHASSETS)) {
            AssetModel model = new AssetModel(responseJSON, false, false);

            assetInstance.contentType = model.contentType;
            assetInstance.fileSize = model.fileSize;
            assetInstance.uploadUrl = model.uploadUrl;
            assetInstance.fileName = model.fileName;
            assetInstance.json = model.json;
            assetInstance.assetUid = model.uploadedUid;
            assetInstance.setTags(model.tags);

            model = null;
            if (request.getCallBackObject() != null) {
                ((FetchResultCallback) request.getCallBackObject()).onRequestFinish(ResponseType.NETWORK);
            }
        } else if (controller.equalsIgnoreCase(CSController.FETCHSYNC)) {

            SyncStack model = new SyncStack();
            model.setJSON(responseJSON);
            if (request.getCallBackObject() != null) {
                ((SyncResultCallBack) request.getCallBackObject()).onRequestFinish(model);
            }

        } else if (controller.equalsIgnoreCase(CSController.FETCHCONTENTTYPES)) {

            ContentTypesModel model = new ContentTypesModel();
            model.setJSON(responseJSON);
            if (request.getCallBackObject() != null) {
                ((ContentTypesCallback) request.getCallBackObject()).onRequestFinish(model);
            }

        }

    }

    protected void createFileIntoCacheDir(Object jsonObject) {
        try {
            JSONObject jsonObj = new JSONObject();
            JSONObject mainJsonObj = new JSONObject();
            JSONObject headerJson = new JSONObject();


            jsonObj = paramsJSON;

            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            cal.setTime(new Date());
            long gmtTime = cal.getTimeInMillis();

            mainJsonObj.put("url", urlToCall.toString().trim());
            mainJsonObj.put("timestamp", gmtTime);
            mainJsonObj.put("params", jsonObj);
            mainJsonObj.put("response", jsonObject);
            if (requestInfo != null) {
                mainJsonObj.put("classUID", requestInfo);
            }

            for (Map.Entry<String, Object> entry : header.entrySet()) {
                String key = entry.getKey();
                headerJson.put(key, entry.getValue());
            }
            mainJsonObj.put("header", headerJson);

            File cacheFile = new File(cacheFileName);

            if (cacheFile.exists()) {
                cacheFile.delete();
            }
            FileWriter file = new FileWriter(cacheFile);
            file.write(mainJsonObj.toString());
            file.flush();
            file.close();
        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_SavingNetworkCallResponseForCache);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("error", e);
            error.setErrors(hashMap);
            if (callBackObject != null) {
                callBackObject.onRequestFail(ResponseType.CACHE, error);
            }
            CSAppUtils.showLog(TAG, "-----built.io----------createCacheFile-------cach |" + e);
        }
    }

}
