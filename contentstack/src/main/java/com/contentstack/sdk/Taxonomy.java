package com.contentstack.sdk;

import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


/**
 * 
 * <br>
 * <b>Taxonomy : </b>
 * Taxonomy, currently in the Early Access Phase simplifies
 * the process of organizing content in your system, making
 * it effortless to find and retrieve information.
 * @implSpec To implement the taxonomy use below code
 * <pre>
 *     {@code
 *     Stack stack = Contentstack.stack("API_KEY", "DELIVERY_TOKEN", "ENVIRONMENT");
 *     Taxonomy taxonomy = stack.taxonomy()
 *     }
 * </pre>
 * 
 * 
 */
public class Taxonomy {
    protected ArrayMap<String, Object> headers;
    protected APIService service;
    protected JSONObject query = new JSONObject();
    protected Config config;

    /**
     * Instantiates a new Taxonomy.
     *
     * @param service the service of type {@link APIService}
     * @param config  the config of type {@link Config}
     * @param headers the headers of the {@link LinkedHashMap}
     */
    protected Taxonomy(APIService service, Config config, ArrayMap<String, Object> headers) {
        this.service = service;
        this.headers = headers;
        this.config = config;
    }


    /**
     * Get all entries for a specific taxonomy that satisfy the given conditions provided in the '$in' query.
     * Your query should be as follows:
     * <p>
     * <pre>
     * {"taxonomies.taxonomy_uid" : { "$in" : ["term_uid1" , "term_uid2" ] }}
     * </pre>
     * <p>
     * <b>Example:</b> If you want to retrieve entries with the color taxonomy applied and linked to the term red and/or yellow.
     * <p>
     * <pre>
     * {"taxonomies.color" : { "$in" : ["red" , "yellow" ] }}
     * </pre>
     *
     * @param taxonomy    the key of the taxonomy to query
     * @param listOfItems the list of taxonomy fields
     * @return an instance of the Taxonomy with the specified conditions added to the query
     */
    public Taxonomy in(String taxonomy, List<String> listOfItems) {
        JSONObject innerObj = new JSONObject();
        try {
            JSONArray jsonArray =  new JSONArray(listOfItems);
            innerObj.put("$in", jsonArray);
            this.query.put(taxonomy, innerObj);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return this;
    }


    /**
     * <b>OR Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that satisfy at least one of the given conditions provided in the “$or” query.
     * <p>
     * Your query should be as follows:
     * <p>
     * <pre>
     *
     * { $or: [
     * { "taxonomies.taxonomy_uid_1" : "term_uid1" },
     * { "taxonomies.taxonomy_uid_2" : "term_uid2" }
     * ]}
     *
     * </pre>
     * <b>Example:</b> If you want to retrieve entries with either the color or size taxonomy applied and linked to the terms yellow and small, respectively.
     * <br>
     * <pre>
     *
     * { $or: [
     * { "taxonomies.color" : "yellow" },
     * { "taxonomies.size" : "small" }
     * ]}
     *
     * </pre>
     *
     * @param listOfItems the list of items
     * @return instance {@link Taxonomy}
     */
    public Taxonomy or(List<JSONObject> listOfItems) {
        if(listOfItems != null){
            try {
                JSONArray jsonArray =  new JSONArray(listOfItems);
                this.query.put("$or", jsonArray);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * <b>AND Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that satisfy all the conditions provided in the “$and” query.
     * <p>
     * Your query should be as follows:
     *
     * <pre>
     * {
     * $and: [
     * { "taxonomies.taxonomy_uid_1" : "term_uid1" },
     * { "taxonomies.taxonomy_uid_2" : "term_uid2" }
     * ]
     * }
     * </pre>
     * <b>Example:</b> If you want to retrieve entries with the color and computers taxonomies applied and linked to the terms red and laptop, respectively.
     *
     * <pre>
     * {
     * $and: [
     * { "taxonomies.color" : "red" },
     * { "taxonomies.computers" : "laptop" }
     * ]
     * }
     * </pre>
     *
     * @param listOfItems the list of items to that you want to include in the query string
     * @return instance of the Taxonomy
     */
    public Taxonomy and(List<JSONObject> listOfItems) {
        if(listOfItems != null) {
            try {
                JSONArray jsonArray =  new JSONArray(listOfItems);
                this.query.put("$and", jsonArray);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * <b>Exists Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that if the value of the field, mentioned in the condition, exists.
     * <p>
     * Your query should be as follows:
     * <pre>
     * {"taxonomies.taxonomy_uid" : { "$exists": true }}
     * </pre>
     * Example: If you want to retrieve entries with the color taxonomy applied.
     * <pre>
     * {"taxonomies.color" : { "$exists": true }}
     * </pre>
     *
     * @param taxonomy the taxonomy
     * @param value    the value of the field
     * @return instance of Taxonomy
     */
    public Taxonomy exists( String taxonomy,Boolean value) {
        if(!taxonomy.isEmpty() && value != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("$exists", value);
                this.query.put(taxonomy, json);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * <b>Equal and Below Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that match a specific term and all its descendant terms, requiring only the target term and a specified level.
     * <p>
     * Note: If you don't specify the level, the default behavior is to retrieve terms up to level 10.
     *
     * <pre>{"taxonomies.taxonomy_uid" : { "$eq_below": "term_uid", "level" : 2}}</pre>
     *
     * <b>Example:</b> If you want to retrieve all entries with terms nested under blue, such as navy blue and sky blue, while also matching entries with the target term blue.
     *
     * <pre>{"taxonomies.color" : { "$eq_below": "blue" }}</pre>
     *
     * @param taxonomy the taxonomy
     * @param termsUid the term uid
     * @return instance of Taxonomy
     */
    public Taxonomy equalAndBelow(String taxonomy, String termsUid) {
        if(!taxonomy.isEmpty() && !termsUid.isEmpty()){
            JSONObject param = new JSONObject();
            try {
                param.put("$eq_below", termsUid);
                this.query.put(taxonomy, param);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    /**
     * <b>Below Operator</b>
     * <br>
     * <p>
     * Get all entries for a specific taxonomy that match all of their descendant terms by specifying only the target term and a specific level.
     * <br>
     * <b>Note:</b> If you don't specify the level, the default behavior is to retrieve terms up to level 10.
     * <br>
     * <pre>{"taxonomies.taxonomy_uid" : { "$below": "term_uid", "level" : 2}}</pre>
     *
     * <b>Example:</b> If you want to retrieve all entries containing terms nested under blue, such as navy blue and sky blue, but exclude entries that solely have the target term blue.
     *
     * <pre>{"taxonomies.color" : { "$below": "blue" }}</pre>
     *
     * @param taxonomy the taxonomy
     * @param termsUid the  terms uid
     * @return instance of Taxonomy
     */
    public Taxonomy below (String taxonomy, String termsUid) {
        if(!taxonomy.isEmpty() && !termsUid.isEmpty()) {
            JSONObject param = new JSONObject();
            try {
                param.put("$below", termsUid);
                this.query.put(taxonomy, param);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * <b>Equal and Above Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that match a specific term and all its ancestor terms, requiring only the target term and a specified level.
     *
     * <b>Note:</b> If you don't specify the level, the default behavior is to retrieve terms up to level 10.
     * <p>
     * <pre>{"taxonomies.taxonomy_uid": { "$eq_above": "term_uid", "level": 2 }}</pre>
     * <p>
     * Example: If you want to obtain all entries that include the term led and its parent term tv.
     * <p>
     * <pre>{"taxonomies.appliances": { "$eq_above": "led"}}</pre>
     *
     * @param taxonomy the taxonomy
     * @param termUid  the term uid
     * @return instance of Taxonomy
     */
    public Taxonomy equalAbove( String taxonomy, String termUid) {
        if(!taxonomy.isEmpty() && !termUid.isEmpty()) {
            JSONObject innerMap = new JSONObject();
            try {
                innerMap.put("$eq_above", termUid);
                this.query.put(taxonomy, innerMap);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * <b>Above Operator :</b>
     * <p>
     * Get all entries for a specific taxonomy that match only the parent term(s) of a specified target term, excluding the target term itself. You can also specify a specific level.
     * <p>
     * Note: If you don't specify the level, the default behavior is to retrieve terms up to level 10.
     *
     * <pre>{ "taxonomies.taxonomy_uid": { "$above": "term_uid", "level": 2 }}</pre>
     * <p>
     * Example: If you wish to match entries with the term tv but exclude the target term led.
     *
     * <pre>{"taxonomies.appliances": { "$above": "led" }}</pre>
     *
     * @param taxonomy the taxonomy
     * @param termUid  the term uid
     * @return instance of {@link Taxonomy}
     */
    public Taxonomy above( String taxonomy, String termUid) {
        if(!taxonomy.isEmpty() && !termUid.isEmpty()) {
            JSONObject innerMap = new JSONObject();
            try {
                innerMap.put("$above", termUid);
                this.query.put(taxonomy, innerMap);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }


    /**
     * To verify the payload
     *
     * @return instance of Call<ResponseBody>
     */
    protected Call<ResponseBody> makeRequest() {
        return this.service.getTaxonomy(this.headers, this.query.toString());
    }

    /**
     * Find.
     *
     * @param callback the callback
     */
    public void find(TaxonomyCallback callback) {
        try {
            Response<ResponseBody> response = makeRequest().execute();

            if (response.isSuccessful()) {
                JSONObject responseJSON = new JSONObject(response.body().string());
                callback.onResponse(responseJSON, null);
            } else {
                JSONObject responseJSON = new JSONObject(response.errorBody().string());
                Error error = new Error();
                error.setErrorMessage(responseJSON.optString("error_message"));
                error.setErrorCode(responseJSON.optInt("error_code"));

                callback.onResponse(null, error);
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }


}


