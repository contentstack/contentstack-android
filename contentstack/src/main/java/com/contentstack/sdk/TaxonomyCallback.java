package com.contentstack.sdk;


import org.json.JSONObject;

public interface TaxonomyCallback {
    /**
     * This method is called wen API response gets received
     * @param response the response of type JSON
     * @param error the error of type @{@link Error}
     */
    void onResponse(JSONObject response, Error error);

}
