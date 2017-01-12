package com.builtio.contentstack;

import com.builtio.contentstack.utilities.CSAppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Helper class for parsing the result of {@link Entry}
 *
 * @author built.io, Inc
 *
 */
public class QueryResult {


    private static final String TAG = "QueryResult";
    protected JSONObject receiveJson;
    protected JSONArray schemaArray;
    protected int count;
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
     * 			To retrieve this data, {@link Query#includeCount()} or {@link Query#count()} should be added in {@link Query} while querying.
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
     * JSONArray schemaArray = queryResultObject.getSchema();<br>
     * </pre>
     */
    public JSONArray getSchema() {
        return schemaArray;
    }



    protected void setJSON(JSONObject jsonobject, List<Entry> objectList) {
        receiveJson = jsonobject;
        resultObjects = objectList;

        try{
            if(receiveJson != null){
                if(receiveJson.has("schema")){

                    JSONArray jsonarray = new JSONArray();
                    jsonarray  = receiveJson.getJSONArray("schema");
                    if(jsonarray != null){
                        schemaArray = jsonarray;
                    }
                }

                if(receiveJson.has("count")){
                    count = receiveJson.optInt("count");
                }

                if(count <= 0){
                    if(receiveJson.has("entries")){
                        count = receiveJson.optInt("entries");
                    }
                }
            }

        }catch(Exception e){
           //TODO ERROR HANDLE
            CSAppUtils.showLog(TAG, "----------------------QueryResult--setJSON--"+e.toString());
        }
    }
}
