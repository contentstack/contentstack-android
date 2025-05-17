package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * The type Sync stack.
 */
public class SyncStack {

    private static final String TAG = SyncStack.class.getSimpleName();
    private JSONObject receiveJson;
    private int skip;
    private int limit;
    private int count;
    private String URL;
    private String pagination_token;
    private String syncToken;
    private ArrayList<JSONObject> syncItems;
    private String sequentialToken;


    /**
     * Gets sequential token based on sync response
     *
     * @return sequentialToken
     */
    public String getSequentialToken() {
        return sequentialToken;
    }

    public void setSequentialToken(String sequentialToken) {
        this.sequentialToken = sequentialToken;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return this.URL;
    }

    /**
     * Gets json response.
     *
     * @return the json response
     */
    public JSONObject getJSONResponse() {
        return this.receiveJson;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Gets limit.
     *
     * @return the limit
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Gets skip.
     *
     * @return the skip
     */
    public int getSkip() {
        return this.skip;
    }

    /**
     * Gets pagination token.
     *
     * @return the pagination token
     */
    public String getPaginationToken() {
        return this.pagination_token;
    }

    /**
     * Gets sync token.
     *
     * @return the sync token
     */
    public String getSyncToken() {
        return this.syncToken;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public ArrayList<JSONObject> getItems() {
        return this.syncItems;
    }

    /**
     * Sets json.
     *
     * @param jsonobject the jsonobject
     */
    protected void setJSON(JSONObject jsonobject) {

        if (jsonobject != null) {
            receiveJson = jsonobject;
            try {
                if (receiveJson != null) {

                    URL = "";

                    if (receiveJson.has("items")) {
                        JSONArray jsonarray = receiveJson.getJSONArray("items");
                        if (jsonarray != null) {
                            syncItems = new ArrayList<>();
                            for (int position = 0; position < jsonarray.length(); position++) {
                                syncItems.add(jsonarray.optJSONObject(position));
                            }
                        }
                    }

                    if (receiveJson.has("skip")) {
                        this.skip = receiveJson.optInt("skip");
                    }
                    if (receiveJson.has("total_count")) {
                        this.count = receiveJson.optInt("total_count");
                    }
                    if (receiveJson.has("limit")) {
                        this.limit = receiveJson.optInt("limit");
                    }
                    if (receiveJson.has("pagination_token")) {
                        this.pagination_token = receiveJson.optString("pagination_token");
                    } else {
                        this.syncToken = null;
                    }
                    if (receiveJson.has("sync_token")) {
                        this.syncToken = receiveJson.optString("sync_token");
                    } else {
                        this.syncToken = null;
                    }
                    if (receiveJson.has("last_seq_id")) {
                        this.sequentialToken = receiveJson.optString("last_seq_id");
                    } else {
                        this.sequentialToken = null;
                    }
                }
            } catch (Exception e) {
                SDKUtil.showLog(TAG, e.getLocalizedMessage());
            }

        }
    }


}
