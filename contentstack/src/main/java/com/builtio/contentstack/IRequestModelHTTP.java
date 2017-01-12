package com.builtio.contentstack;

import org.json.JSONObject;


/**
 * 
 * @author built.io, Inc
 *
 */
public interface IRequestModelHTTP {

	public void sendRequest();

    public void onRequestFailed(JSONObject error, int statusCode, ResultCallBack callBackObject);

    public void onRequestFinished(CSHttpConnection request);

}
