package com.contentstack.sdk;

import org.json.JSONObject;


/**
 * @author Contentstack.com, Inc
 */
public interface IRequestModelHTTP {

    public void sendRequest();

    public void onRequestFailed(JSONObject error, int statusCode, ResultCallBack callBackObject);

    public void onRequestFinished(CSHttpConnection request);

}
