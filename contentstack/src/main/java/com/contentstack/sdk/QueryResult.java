package com.contentstack.sdk;

import com.contentstack.sdk.utilities.CSAppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Helper class for parsing the result of {@link Entry}
 *
 * @author Contentstack.com, Inc
 */
public class QueryResult {


    private static final String TAG = "QueryResult";
    protected JSONObject receiveJson;
    protected JSONArray schemaArray;
    protected int count;
    protected JSONObject contentObject;
    protected List<Entry> resultObjects;

    /**
     * Returns {@link Entry} objects list.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * List&#60;Entry&#62; list = queryResultObject.getResultObjects();<br>
     * </pre>
     */
    public List<Entry> getResultObjects() {
        return resultObjects;
    }

    /**
     * Returns count of objects available.<br>
     *
     * <b>Note : </b>
     * To retrieve this data, {@link Query#includeCount()} or {@link Query#count()} should be added in {@link Query} while querying.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * int count = queryResultObject.getCount();<br>
     * </pre>
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns class&#39;s schema if call to fetch schema executed successfully.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * JSONArray itemsArray = queryResultObject.getSchema();<br>
     * </pre>
     */
    public JSONArray getSchema() {
        return schemaArray;
    }


    /**
     * Returns class&#39;s content type if call to fetch contentType executed successfully.
     *
     * @return JSONObject contentObject
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * JSONObject contentObject = queryResultObject.getContentType();<br>
     * </pre>
     */
    public JSONObject getContentType() {
        return contentObject;
    }


    protected void setJSON(JSONObject jsonobject, List<Entry> objectList) {
        receiveJson = jsonobject;
        resultObjects = objectList;

        try {
            if (receiveJson != null) {
                if (receiveJson.has("schema")) {

                    JSONArray jsonarray = new JSONArray();
                    jsonarray = receiveJson.getJSONArray("schema");
                    if (jsonarray != null) {
                        schemaArray = jsonarray;
                    }
                }

                if (receiveJson.has("content_type")) {

                    JSONObject jsonObject = receiveJson.getJSONObject("content_type");
                    if (jsonObject != null) {
                        contentObject = jsonObject;
                    }
                }

                if (receiveJson.has("count")) {
                    count = receiveJson.optInt("count");
                }

                if (count <= 0) {
                    if (receiveJson.has("entries")) {
                        count = receiveJson.optInt("entries");
                    }
                }
            }

        } catch (Exception e) {
            //TODO ERROR HANDLE
            CSAppUtils.showLog(TAG, "----------------------QueryResult--setJSON--" + e.toString());
        }
    }
}
