package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * Created by contentstack.com, Inc.
 */
class AssetModel {

    String uploadedUid;
    String contentType;
    String fileSize;
    String fileName;
    String uploadUrl;
    String[] tags;
    JSONObject json;
    int totalCount = 0;
    int count = 0;

    protected WeakHashMap<String, Object> _metadata = null;

    public AssetModel(JSONObject responseJSON, boolean isArray, boolean isFromCache) {

        if (isFromCache) {
            json = responseJSON.opt("response") == null ? responseJSON : responseJSON.optJSONObject("response");
        } else {
            json = responseJSON;
        }

        if (isArray) {
            json = responseJSON;
        } else {
            json = responseJSON.optJSONObject("asset");
        }

        uploadedUid = (String) json.opt("uid");
        contentType = (String) json.opt("content_type");
        fileSize = (String) json.opt("file_size");
        fileName = (String) json.opt("filename");
        uploadUrl = (String) json.opt("url");

        if (json.opt("tags") instanceof JSONArray) {
            if ((json.has("tags")) && (json.opt("tags") != null) && (!(json.opt("tags").equals("")))) {

                JSONArray tagsArray = (JSONArray) json.opt("tags");
                if (tagsArray.length() > 0) {
                    int count = tagsArray.length();
                    tags = new String[count];
                    for (int i = 0; i < count; i++) {
                        tags[i] = (String) tagsArray.opt(i);
                    }
                }
            }
        }

        if (json != null && json.has("_metadata")) {
            JSONObject _metadataJSON = json.optJSONObject("_metadata");
            Iterator<String> iterator = _metadataJSON.keys();
            _metadata = new WeakHashMap<>();
            while (iterator.hasNext()) {
                String key = iterator.next();
                _metadata.put(key, _metadataJSON.optString(key));
            }
        }

        if (responseJSON.has("count")) {
            count = responseJSON.optInt("count");
        }

        if (responseJSON.has("objects")) {
            totalCount = responseJSON.optInt("objects");
        }
    }

}
