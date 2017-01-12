package com.builtio.contentstack;

import org.json.JSONObject;

import java.util.List;

/**
 * To notify class which initiate network call when network call complete.
 * 
 * @author built.io, Inc
 * 
 */
public interface INotifyClass {
	
	public void getResult(Object object, String controller);
	
	public void getResultObject(List<Object> object, JSONObject jsonObject, boolean isSingleEntry);

}
