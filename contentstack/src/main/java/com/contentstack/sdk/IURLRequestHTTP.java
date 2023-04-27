package com.contentstack.sdk;

import android.util.ArrayMap;

import org.json.JSONObject;

/**
 * @author Contentstack.com, Inc
 */
public interface IURLRequestHTTP {

    public void send();

    public void setHeaders(ArrayMap headers);

    public ArrayMap getHeaders();

    public void setRequestMethod(SDKConstant.RequestMethod requestMethod);

    public SDKConstant.RequestMethod getRequestMethod();

    public JSONObject getResponse();

    public void setInfo(String info);

    public String getInfo();

    public void setController(String controller);

    public String getController();

    public void setCallBackObject(ResultCallBack builtResultCallBackObject);

    public ResultCallBack getCallBackObject();

    public void setTreatDuplicateKeysAsArrayItems(boolean treatDuplicateKeysAsArrayItems);

    public boolean getTreatDuplicateKeysAsArrayItems();


}
