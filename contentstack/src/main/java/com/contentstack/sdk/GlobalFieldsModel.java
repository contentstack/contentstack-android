package com.contentstack.sdk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class GlobalFieldsModel {

    private JSONObject responseJSON = new JSONObject();
    private JSONArray responseJSONArray = new JSONArray();
    private final String TAG = GlobalFieldsModel.class.getSimpleName();

    public void setJSON(JSONObject responseJSON) {

        if (responseJSON != null) {

            if (responseJSON.has("global_field")) {
                try {
                    this.responseJSON = responseJSON.getJSONObject("global_field");
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }

            if (responseJSON.has("global_fields")) {
                try {
                    this.responseJSONArray = responseJSON.getJSONArray("global_fields");
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
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
