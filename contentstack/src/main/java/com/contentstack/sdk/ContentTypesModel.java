package com.contentstack.sdk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentTypesModel {

    private JSONObject responseJSON = new JSONObject();
    private JSONArray responseJSONArray = new JSONArray();
    private final String TAG = ContentTypesModel.class.getSimpleName();

    public void setJSON(JSONObject responseJSON) {

        if (responseJSON != null) {

            if (responseJSON.has("content_type")) {
                try {
                    this.responseJSON = responseJSON.getJSONObject("content_type");
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

            if (responseJSON.has("content_types")) {
                try {
                    this.responseJSONArray = responseJSON.getJSONArray("content_types");
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }

        }
    }

    public JSONObject getResponse() {
        return responseJSON;
    }

    public JSONArray getResultArray() {
        return responseJSONArray;
    }
}
