package com.contentstack.sdk;

import com.contentstack.sdk.utilities.CSAppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * @author Contentstack.com, Inc
 */
class EntryModel {

    protected JSONObject jsonObject = null;
    protected String entryUid = null;
    protected String ownerEmailId = null;
    protected String ownerUid = null;
    protected String title = null;
    protected String url = null;
    protected String language = null;
    protected String[] tags = null;
    protected WeakHashMap<String, Object> ownerMap = null;
    protected WeakHashMap<String, Object> _metadata = null;
    private JSONArray tagsArray = null;

    public EntryModel(JSONObject jsonObj, String entryUid, boolean isFromObjectsModel, boolean isFromCache, boolean isFromDeltaResponse) {

        try {
            this.entryUid = entryUid;
            if (isFromObjectsModel) {
                jsonObject = jsonObj;
                this.entryUid = (String) (jsonObject.isNull("uid") ? " " : jsonObject.opt("uid"));
            } else {

                if (isFromCache) {
                    jsonObject = jsonObj.opt("response") == null ? null : jsonObj.optJSONObject("response");
                } else {
                    jsonObject = jsonObj;
                }

                if (isFromDeltaResponse) {
                    this.entryUid = (String) (jsonObject != null && jsonObject.isNull("uid") ? " " : jsonObject.opt("uid"));
                } else {
                    jsonObject = jsonObject != null && jsonObject.opt("entry") == null ? null : jsonObject.optJSONObject("entry");
                }
            }
            if (jsonObject != null && jsonObject.has("uid")) {
                this.entryUid = (String) (jsonObject.isNull("uid") ? " " : jsonObject.opt("uid"));
            }

            if (jsonObject != null && jsonObject.has("title")) {
                this.title = (String) (jsonObject.isNull("title") ? " " : jsonObject.opt("title"));
            }

            if (jsonObject != null && jsonObject.has("url")) {
                this.url = (String) (jsonObject.isNull("url") ? " " : jsonObject.opt("url"));
            }

            if (jsonObject != null && jsonObject.has("locale")) {
                this.language = (String) (jsonObject.isNull("locale") ? " " : jsonObject.opt("locale"));
            }

            if (jsonObject != null && jsonObject.has("_metadata")) {
                JSONObject _metadataJSON = jsonObject.optJSONObject("_metadata");
                Iterator<String> iterator = _metadataJSON.keys();
                _metadata = new WeakHashMap<>();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key.equalsIgnoreCase("uid")) {
                        this.entryUid = _metadataJSON.optString(key);
                    }
                    _metadata.put(key, _metadataJSON.optString(key));
                }
            } else if (jsonObject != null && jsonObject.has("publish_details")) {
                JSONObject publishDetailsObj = jsonObject.optJSONObject("publish_details");
                _metadata = new WeakHashMap<>();
                _metadata.put("publish_details", publishDetailsObj);
            }


            if (jsonObject != null && jsonObject.has("_owner") && (jsonObject.opt("_owner") != null) && (!jsonObject.opt("_owner").toString().equalsIgnoreCase("null"))) {
                JSONObject ownerObject = jsonObject.optJSONObject("_owner");
                if (ownerObject.has("email") && ownerObject.opt("email") != null) {
                    ownerEmailId = (String) ownerObject.opt("email");
                }

                if (ownerObject.has("uid") && ownerObject.opt("uid") != null) {
                    ownerUid = ownerObject.opt("uid").toString();
                }
                JSONObject owner = jsonObject.optJSONObject("_owner");
                Iterator<String> iterator = owner.keys();
                ownerMap = new WeakHashMap<>();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    ownerMap.put(key, owner.optString(key));
                }
            }

            tagsArray = (JSONArray) jsonObject.opt("tags");
            if (tagsArray != null && tagsArray.length() > 0) {
                int count = tagsArray.length();
                tags = new String[count];
                for (int i = 0; i < count; i++) {
                    tags[i] = (String) tagsArray.opt(i);
                }
            }


        } catch (Exception e) {
            CSAppUtils.showLog("EntryModel", e.getLocalizedMessage());
        }

    }
}
