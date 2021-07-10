package com.contentstack.sdk;

import android.util.ArrayMap;
import android.text.TextUtils;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A class that defines a query that is used to query for {@link Entry} instance.
 *
 * @author Contentstack
 */
public class Query implements INotifyClass {

    private static final String TAG = "Query";
    protected JSONObject mainJSON = null;
    private String formName = null;
    protected ContentType contentTypeInstance = null;
    private JSONObject urlQueries = null;
    private ArrayMap<String, Object> localHeader = null;
    protected ArrayMap<String, Object> formHeader = null;

    private JSONObject queryValueJSON = null;
    private JSONObject queryValue = null;
    private JSONArray objectUidForInclude = null;
    private JSONArray objectUidForExcept = null;
    private JSONArray objectUidForOnly = null;
    private boolean isJsonProper = true;
    // cache policy
    private long maxCachetimeForCall = 86400000; //local cache time interval
    private long defaultCacheTimeInterval = 0;
    private CachePolicy cachePolicyForCall = null;
    private QueryResultsCallBack queryResultCallback;
    private SingleQueryResultCallback singleQueryResultCallback;
    private String errorString;
    private HashMap<String, Object> errorHashMap;
    private JSONObject onlyJsonObject;
    private JSONObject exceptJsonObject;

    protected Query(String formName) {
        this.formName = formName;
        this.localHeader = new ArrayMap<>();
        this.urlQueries = new JSONObject();
        this.queryValue = new JSONObject();
        this.queryValueJSON = new JSONObject();
        this.mainJSON = new JSONObject();
    }

    protected void setContentTypeInstance(ContentType contentTypeInstance) {
        this.contentTypeInstance = contentTypeInstance;
    }


    /**
     * To set headers for Built.io Contentstack rest calls.
     * <br>
     * Scope is limited to this object and followed classes.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     *              <br><br><b>Example :</b><br>
     *              <pre class="prettyprint">
     *               //'blt5d4sample2633b' is a dummy Stack API key
     *               //'blt6d0240b5sample254090d' is dummy access token.
     *               Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *               Query csQuery = stack.contentType("contentType_name").query();<br>
     *               csQuery.setHeader("custom_key", "custom_value");
     *               </pre>
     */
    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            localHeader.put(key, value);
        }
    }

    /**
     * Remove header key.
     *
     * @param key custom_header_key
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *             //'blt5d4sample2633b' is a dummy Stack API key
     *             //'blt6d0240b5sample254090d' is dummy access token.
     *             Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *             Query csQuery = stack.contentType("contentType_name").query();<br>
     *             csQuery.removeHeader("custom_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    public String getContentType() {
        return contentTypeInstance.contentTypeName;
    }

    /**
     * Add a constraint to fetch all entries that contains given value against specified  key.
     *
     * @param key   field uid.
     * @param value field value which get &#39;included&#39; from the response.
     * @return {@link Query} object, so you can chain this call.
     *
     * <p>
     * <b>Note :</b> for group field provide key in a &#34;key.groupFieldUid&#34; format.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Query csQuery = stack.contentType("contentType_name").query();
     *
     *    csQuery.where("uid", "bltf4fbsample851db");
     * </pre>
     */
    public Query where(String key, Object value) {
        try {
            if (key != null && value != null) {
                queryValueJSON.put(key, value);
            } else {
                throwException("where", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("where", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }

        return this;
    }

    /**
     * Add a custom query against specified key.
     *
     * @param key   key.
     * @param value value.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Query csQuery = stack.contentType("contentType_name").query();
     *
     *    csQuery.addQuery("query_param_key", "query_param_value");
     * </pre>
     */
    public Query addQuery(String key, String value) {
        try {
            if (key != null && value != null) {

                urlQueries.put(key, value);
            } else {
                throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Remove provided query key from custom query if exist.
     *
     * @param key Query name to remove.
     * @return {@linkplain Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      projectQuery.removeQuery("Query_Key");
     * </pre>
     */
    public Query removeQuery(String key) {
        try {
            if (urlQueries.has(key)) {
                urlQueries.remove(key);
            }
        } catch (Exception e) {
            throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Combines all the queries together using AND operator
     *
     * @param queryObjects list of {@link Query} instances on which AND query executes.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example ;</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Query csQuery = stack.contentType("contentType_name").query();
     *
     *    Query query = projectClass.query();
     *    query.where('username','something');
     *
     *    Query subQuery = projectClass.query();
     *    subQuery.where('email_address','something@email.com');
     *
     *    ArrayList&#60;Query&#62; array = new ArrayList&#60;Query&#62;();<br>
     *    array.add(query);
     *    array.add(subQuery);<br>
     *    projectQuery.and(array);
     * </pre>
     */
    public Query and(ArrayList<Query> queryObjects) {
        if (queryObjects != null && queryObjects.size() > 0) {
            try {
                JSONArray orValueJson = new JSONArray();
                int count = queryObjects.size();

                for (int i = 0; i < count; i++) {
                    orValueJson.put(queryObjects.get(i).queryValueJSON);
                }
                queryValueJSON.put("$and", orValueJson);

            } catch (Exception e) {
                throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint to fetch all entries which satisfy <b> any </b> queries.
     *
     * @param queryObjects list of {@link Query} instances on which OR query executes.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     Query query = projectClass.query();
     *     query.where('username','something');
     *
     *     Query subQuery = projectClass.query();
     *     subQuery.where('email_address','something@email.com');
     *
     *     ArrayList&#60;Query&#62; array = new ArrayList&#60;Query&#62;();
     *     array.add(query);
     *     array.add(subQuery);<br>
     *     csQuery.or(array);
     * </pre>
     */
    public Query or(ArrayList<Query> queryObjects) {
        if (queryObjects != null && queryObjects.size() > 0) {
            try {
                JSONArray orValueJson = new JSONArray();
                int count = queryObjects.size();

                for (int i = 0; i < count; i++) {
                    orValueJson.put(queryObjects.get(i).queryValueJSON);
                }

                queryValueJSON.put("$or", orValueJson);

            } catch (Exception e) {
                throwException("or", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("or", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint to the query that requires a particular key entry to be less than the provided value.
     *
     * @param key   the key to be constrained.
     * @param value the value that provides an upper bound.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.lessThan("due_date", "2013-06-25T00:00:00+05:30");
     * </pre>
     */
    public Query lessThan(String key, Object value) {
        if (key != null && value != null) {
            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$lt", value);
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$lt", value);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("lessThan", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("lessThan", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint to the query that requires a particular key entry to be less than or equal to the provided value.
     *
     * @param key   The key to be constrained
     * @param value The value that must be equalled.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.lessThanOrEqualTo("due_date", "2013-06-25T00:00:00+05:30");
     * </pre>
     */
    public Query lessThanOrEqualTo(String key, Object value) {

        if (key != null && value != null) {

            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$lte", value);
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$lte", value);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("lessThanOrEqualTo", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("lessThanOrEqualTo", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Add a constraint to the query that requires a particular key entry to be greater than the provided value.
     *
     * @param key   The key to be constrained.
     * @param value The value that provides an lower bound.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.greaterThan("due_date", "2013-06-25T00:00:00+05:30");
     * </pre>
     */
    public Query greaterThan(String key, Object value) {

        if (key != null && value != null) {
            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$gt", value);
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$gt", value);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("greaterThan", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("greaterThan", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint to the query that requires a particular key entry to be greater than or equal to the provided value.
     *
     * @param key   The key to be constrained.
     * @param value The value that provides an lower bound.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.greaterThanOrEqualTo("due_date", "2013-06-25T00:00:00+05:30");
     * </pre>
     */
    public Query greaterThanOrEqualTo(String key, Object value) {

        if (key != null && value != null) {
            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$gte", value);
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$gte", value);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("greaterThanOrEqualTo", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("greaterThanOrEqualTo", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;

    }

    /**
     * Add a constraint to the query that requires a particular key&#39;s entry to be not equal to the provided value.
     *
     * @param key   The key to be constrained.
     * @param value The object that must not be equaled.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example ;</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.notEqualTo("due_date", "2013-06-25T00:00:00+05:30");
     * </pre>
     */
    public Query notEqualTo(String key, Object value) {

        if (key != null && value != null) {

            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$ne", value);
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$ne", value);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("notEqualTo", null, e);
            }

        } else {
            throwException("notEqualTo", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;

    }

    /**
     * Add a constraint to the query that requires a particular key&#39;s entry to be contained in the provided array.
     *
     * @param key    The key to be constrained.
     * @param values The possible values for the key&#39;s object.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.containedIn("severity", new Object[]{"Show Stopper", "Critical"});
     * </pre>
     */
    public Query containedIn(String key, Object[] values) {

        if (key != null && values != null) {
            try {
                JSONArray valuesArray = new JSONArray();
                int length = values.length;
                for (int i = 0; i < length; i++) {
                    valuesArray.put(values[i]);
                }
                if (queryValueJSON.isNull(key)) {
                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }
                    queryValue.put("$in", valuesArray);
                    queryValueJSON.put(key, queryValue);
                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$in", valuesArray);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("containedIn", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("containedIn", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint to the query that requires a particular key entry&#39;s value not be contained in the provided array.
     *
     * @param key    The key to be constrained.
     * @param values The list of values the key object should not be.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.notContainedIn("severity", new Object[]{"Show Stopper", "Critical"});
     * </pre>
     */
    public Query notContainedIn(String key, Object[] values) {

        if (key != null && values != null) {
            try {
                JSONArray valuesArray = new JSONArray();
                int length = values.length;
                for (int i = 0; i < length; i++) {
                    valuesArray.put(values[i]);
                }
                if (queryValueJSON.isNull(key)) {
                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }
                    queryValue.put("$nin", valuesArray);
                    queryValueJSON.put(key, queryValue);
                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$nin", valuesArray);
                    queryValueJSON.put(key, queryValue);

                }
            } catch (Exception e) {
                throwException("containedIn", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("containedIn", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;

    }

    /**
     * Add a constraint that requires, a specified key exists in response.
     *
     * @param key The key to be constrained.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.exists("status");
     * </pre>
     */
    public Query exists(String key) {

        if (key != null) {
            try {

                if (queryValueJSON.isNull(key)) {
                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }
                    queryValue.put("$exists", true);
                    queryValueJSON.put(key, queryValue);
                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$exists", true);
                    queryValueJSON.put(key, queryValue);
                }
            } catch (Exception e) {
                throwException("exists", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("exists", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint that requires, a specified key does not exists in response.
     *
     * @param key The key to be constrained.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.notExists("status");
     * </pre>
     */
    public Query notExists(String key) {

        if (key != null) {
            try {

                if (queryValueJSON.isNull(key)) {
                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }
                    queryValue.put("$exists", false);
                    queryValueJSON.put(key, queryValue);
                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$exists", false);
                    queryValueJSON.put(key, queryValue);
                }
            } catch (Exception e) {
                throwException("notExists", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("notExists", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * Add a constraint that requires a particular reference key details.
     *
     * @param key key that to be constrained.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.includeReference("for_bug");
     * </pre>
     */
    public Query includeReference(String key) {
        if (objectUidForInclude == null) {
            objectUidForInclude = new JSONArray();
        }

        objectUidForInclude.put(key);

        return this;
    }

    /**
     * Add a constraint that requires a particular reference key details.
     *
     * @param keys array keys that to be constrained.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.includeReference(new String[]{"for_bug"});
     * </pre>
     */
    public Query includeReference(String[] keys) {
        if (keys != null && keys.length > 0) {

            if (objectUidForInclude == null) {
                objectUidForInclude = new JSONArray();
            }

            int count = keys.length;
            for (int i = 0; i < count; i++) {
                objectUidForInclude.put(keys[i]);
            }
        } else {
            throwException("includeReference", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Include tags with which to search entries.
     *
     * @param tags Comma separated array of tags with which to search entries.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.tags(new String[]{"tag1","tag2"});
     * </pre>
     */
    public Query tags(String[] tags) {
        try {
            if (tags != null) {

                String tagsvalue = null;
                int count = tags.length;
                for (int i = 0; i < count; i++) {
                    tagsvalue = tagsvalue + "," + tags[i];
                }
                urlQueries.put("tags", tagsvalue);
            } else {
                throwException("tags", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("tags", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Sort the results in ascending order with the given key.
     * <br>
     * Sort the returned entries in ascending order of the provided key.
     *
     * @param key The key to order by.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.ascending("name");
     * </pre>
     */
    public Query ascending(String key) {
        if (key != null) {
            try {
                urlQueries.put("asc", key);
            } catch (Exception e) {
                throwException("ascending", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("ascending", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Sort the results in descending order with the given key.
     * <br>
     * Sort the returned entries in descending order of the provided key.
     *
     * @param key The key to order by.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *
     *     csQuery.descending("name");
     * </pre>
     */
    public Query descending(String key) {
        if (key != null) {
            try {
                urlQueries.put("desc", key);
            } catch (Exception e) {
                throwException("descending", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("descending", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Specifies list of field uids that would be &#39;excluded&#39; from the response.
     *
     * @param fieldUid field uid  which get &#39;excluded&#39; from the response.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *     ArrayList&#60;String&#62; array = new ArrayList&#60;String&#62;();
     *     array.add("name");
     *     array.add("description");<br>
     *
     *     csQuery.except(array);
     * </pre>
     */
    public Query except(ArrayList<String> fieldUid) {
        try {
            if (fieldUid != null && fieldUid.size() > 0) {
                if (objectUidForExcept == null) {
                    objectUidForExcept = new JSONArray();
                }

                int count = fieldUid.size();
                for (int i = 0; i < count; i++) {
                    objectUidForExcept.put(fieldUid.get(i));
                }
            } else {
                throwException("except", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("except", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Specifies list of field uids that would be &#39;excluded&#39; from the response.
     *
     * @param fieldUids field uid  which get &#39;excluded&#39; from the response.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *     csQuery.except(new String[]{"name", "description"});
     * </pre>
     */
    public Query except(String[] fieldUids) {
        try {
            if (fieldUids != null && fieldUids.length > 0) {
                if (objectUidForExcept == null) {
                    objectUidForExcept = new JSONArray();
                }

                int count = fieldUids.length;
                for (int i = 0; i < count; i++) {
                    objectUidForExcept.put(fieldUids[i]);
                }
            } else {
                throwException("except", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("except", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Specifies an array of &#39;only&#39; keys in BASE object that would be &#39;included&#39; in the response.
     *
     * @param fieldUid Array of the &#39;only&#39; reference keys to be included in response.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     csQuery.only(new String[]{"name"});
     * </pre>
     */
    public Query only(String[] fieldUid) {
        try {
            if (fieldUid != null && fieldUid.length > 0) {
                if (objectUidForOnly == null) {
                    objectUidForOnly = new JSONArray();
                }

                int count = fieldUid.length;
                for (int i = 0; i < count; i++) {
                    objectUidForOnly.put(fieldUid[i]);
                }
            } else {
                throwException("only", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("only", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Specifies an array of &#39;only&#39; keys that would be &#39;included&#39; in the response.
     *
     * @param fieldUid          Array of the &#39;only&#39; reference keys to be included in response.
     * @param referenceFieldUid Key who has reference to some other class object.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     ArrayList&#60;String&#62; array = new ArrayList&#60;String&#62;();
     *     array.add("description");
     *     array.add("name");<br>
     *     csQuery.onlyWithReferenceUid(array, "for_bug");
     * </pre>
     */
    public Query onlyWithReferenceUid(ArrayList<String> fieldUid, String referenceFieldUid) {
        try {
            if (fieldUid != null && referenceFieldUid != null) {
                if (onlyJsonObject == null) {
                    onlyJsonObject = new JSONObject();
                }
                JSONArray fieldValueArray = new JSONArray();
                int count = fieldUid.size();
                for (int i = 0; i < count; i++) {
                    fieldValueArray.put(fieldUid.get(i));
                }

                onlyJsonObject.put(referenceFieldUid, fieldValueArray);

                if (objectUidForInclude == null) {
                    objectUidForInclude = new JSONArray();
                }
                objectUidForInclude.put(referenceFieldUid);

            } else {
                throwException("onlyWithReferenceUid", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("onlyWithReferenceUid", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Specifies an array of &#39;except&#39; keys that would be &#39;excluded&#39; in the response.
     *
     * @param fieldUid          Array of the &#39;except&#39; reference keys to be excluded in response.
     * @param referenceFieldUid Key who has reference to some other class object.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     ArrayList&#60;String&#62; array = new ArrayList&#60;String&#62;();
     *     array.add("description");
     *     array.add("name");<br>
     *     csQuery.exceptWithReferenceUid(array, "for_bug");
     * </pre>
     */
    public Query exceptWithReferenceUid(ArrayList<String> fieldUid, String referenceFieldUid) {
        try {
            if (fieldUid != null && referenceFieldUid != null) {
                if (exceptJsonObject == null) {
                    exceptJsonObject = new JSONObject();
                }
                JSONArray fieldValueArray = new JSONArray();
                int count = fieldUid.size();
                for (int i = 0; i < count; i++) {
                    fieldValueArray.put(fieldUid.get(i));
                }

                exceptJsonObject.put(referenceFieldUid, fieldValueArray);

                if (objectUidForInclude == null) {
                    objectUidForInclude = new JSONArray();
                }
                objectUidForInclude.put(referenceFieldUid);

            } else {
                throwException("exceptWithReferenceUid", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("exceptWithReferenceUid", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Retrieve only count of entries in result.
     *
     * @return {@link Query} object, so you can chain this call.
     *
     * <b>Note :- </b>
     * <li>Call {@link QueryResult#getCount()} method in the success to get count of objects.</li>
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *     csQuery.count();
     * </pre>
     */
    public Query count() {
        try {
            urlQueries.put("count", "true");
        } catch (Exception e) {
            throwException("count", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Retrieve count and data of objects in result.
     *
     * @return {@link Query} object, so you can chain this call.
     *
     * <b>Note :- </b>
     * <li>Call {@link QueryResult#getCount()} method in the success to get count of  objects.</li>
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     csQuery.includeCount();
     * </pre>
     */
    public Query includeCount() {
        try {
            urlQueries.put("include_count", "true");
        } catch (Exception e) {
            throwException("includeCount", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }


    /**
     * Include Content Type of all returned objects along with objects themselves.
     *
     * @return {@link Query} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     csQuery.includeContentType();
     * </pre>
     */
    public Query includeContentType() {
        try {
            if (urlQueries.has("include_schema")) {
                urlQueries.remove("include_schema");
            }
            urlQueries.put("include_content_type", true);
            urlQueries.put("include_global_field_schema", true);
        } catch (Exception e) {
            throwException("include_content_type", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }


    /**
     * Include object owner&#39;s profile in the objects data.
     *
     * @return {@linkplain Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *     csQuery.includeOwner();
     * </pre>
     */
    public Query includeOwner() {
        try {
            urlQueries.put("include_owner", true);
        } catch (Exception e) {
            throwException("includeUser", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Fetches all the objects before specified uid.
     *
     * @param uid uid before which objects should be returned.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *     //'blt0ae4df463e93f6f5' is dummy uid
     *     csQuery.beforeUid("blt0ae4df463e93f6f5");
     * </pre>
     */
    private Query beforeUid(String uid) {
        if (uid != null) {
            try {
                urlQueries.put("before_uid", uid);
            } catch (Exception e) {
                throwException("beforeUid", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("beforeUid", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Fetches all the objects after specified uid.
     *
     * @param uid uid after which objects should be returned.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      //'blt0ae4df463e93f6f5' is dummy uid
     *      csQuery.afterUid("blt0ae4df463e93f6f5");
     * </pre>
     */
    private Query afterUid(String uid) {
        if (uid != null) {
            try {
                urlQueries.put("after_uid", uid);
            } catch (Exception e) {
                throwException("afterUid", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("afterUid", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * The number of objects to skip before returning any.
     *
     * @param number No of objects to skip from returned objects.
     * @return {@link Query} object, so you can chain this call.
     *
     * <p>
     * <b> Note: </b>The skip parameter can be used for pagination, &#34;skip&#34; specifies the number of objects to skip in the response.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.skip(2);
     * </pre>
     */
    public Query skip(int number) {
        try {
            urlQueries.put("skip", number);
        } catch (Exception e) {
            throwException("skip", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * A limit on the number of objects to return.
     *
     * @param number No of objects to limit.
     * @return {@link Query} object, so you can chain this call.
     * <p>
     * <b> Note:</b> The limit parameter can be used for pagination, &#34;limit&#34; specifies the number of objects to limit to in the response.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.limit(2);
     * </pre>
     */
    public Query limit(int number) {
        try {
            urlQueries.put("limit", number);
        } catch (Exception e) {
            throwException("limit", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }

    /**
     * Add a regular expression constraint for finding string values that match the provided regular expression.
     * This may be slow for large data sets.
     *
     * @param key   The key to be constrained.
     * @param regex The regular expression pattern to match.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.regex("name", "^browser");
     * </pre>
     */
    public Query regex(String key, String regex) {
        if (key != null && regex != null) {

            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$regex", regex);

                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$regex", regex);

                    queryValueJSON.put(key, queryValue);

                }

            } catch (Exception e) {
                throwException("matches", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("matches", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }


    /**
     * Add a regular expression constraint for finding string values that match the provided regular expression.
     * This may be slow for large data sets.
     *
     * @param key       The key to be constrained.
     * @param regex     The regular expression pattern to match.
     * @param modifiers Any of the following supported Regular expression modifiers.
     *                  <li>use <b> i </b> for case-insensitive matching.</li>
     *                  <li>use <b> m </b> for making dot match newlines.</li>
     *                  <li>use <b> x </b> for ignoring whitespace in regex</li>
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.regex("name", "^browser", "i");
     * </pre>
     */
    public Query regex(String key, String regex, String modifiers) {
        if (key != null && regex != null) {

            try {
                if (queryValueJSON.isNull(key)) {

                    if (queryValue.length() > 0) {
                        queryValue = new JSONObject();
                    }

                    queryValue.put("$regex", regex);

                    if (modifiers != null) {
                        queryValue.put("$options", modifiers);
                    }
                    queryValueJSON.put(key, queryValue);

                } else if (queryValueJSON.has(key)) {

                    queryValue.put("$regex", regex);

                    if (modifiers != null) {
                        queryValue.put("$options", modifiers);
                    }
                    queryValueJSON.put(key, queryValue);

                }

            } catch (Exception e) {
                throwException("matches", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("matches", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }

    /**
     * Set {@link Language} instance.
     *
     * @param language {@link Language} value
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.language(Language.ENGLISH_UNITED_STATES);
     * </pre>
     */
    @Deprecated
    public Query language(Language language) {
        if (language != null) {
            try {
                Language languageName = Language.valueOf(language.name());
                int localeValue = languageName.ordinal();
                LanguageCode[] languageCodeValues = LanguageCode.values();
                String localeCode = languageCodeValues[localeValue].name();
                localeCode = localeCode.replace("_", "-");

                if (urlQueries != null) {
                    urlQueries.put("locale", localeCode);
                }

            } catch (Exception e) {
                throwException("language", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("language", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }


    /**
     * @param locale String value
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *      csQuery.locale("en-hi");
     * </pre>
     */
    public Query locale(String locale) {

        if (locale != null) {
            try {
                if (urlQueries != null) {
                    urlQueries.put("locale", locale);
                }

            } catch (Exception e) {
                throwException("locale", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("locale", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }


    /**
     * This method provides only the entries matching the specified value.
     *
     * @param value value used to match or compare
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.search("header");
     * </pre>
     */
    public Query search(String value) {
        if (value != null) {
            try {

                if (urlQueries.isNull(value)) {
                    urlQueries.put("typeahead", value);
                }
            } catch (Exception e) {
                throwException("value", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("value", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }

        return this;
    }

    /**
     * To set cache policy using {@link Query} instance.
     *
     * @param cachePolicy {@link CachePolicy} instance.
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.setCachePolicy(NETWORK_ELSE_CACHE);
     * </pre>
     */
    public Query setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicyForCall = cachePolicy;

        return this;
    }

    /**
     * Execute a Query and Caches its result (Optional)
     *
     * @param callback {@link QueryResultsCallBack} object to notify the application when the request has completed.
     * @return {@linkplain Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.find(new QueryResultsCallBack() {<br>
     *          &#64;Override
     *          public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {<br>
     *
     *          }
     *      });<br>
     * </pre>
     */
    public Query find(QueryResultsCallBack callback) {
        Error error = null;
        try {
            if (isJsonProper) {

                if (!TextUtils.isEmpty(formName)) {

                    execQuery(null, callback, false);
                } else {
                    //TODO ERROR HANDLE
                    throwException("find", CSAppConstants.ErrorMessage_FormName, null);
                    error = new Error();
                    error.setErrorMessage(errorString);
                    error.setErrors(errorHashMap);
                }
            } else {
                //TODO ERROR HANDLE
                error = new Error();
                error.setErrorMessage(errorString);
                error.setErrors(errorHashMap);
            }
        } catch (Exception e) {
            //TODO ERROR HANDLE
            throwException("find", CSAppConstants.ErrorMessage_JsonNotProper, null);
            error = new Error();
            error.setErrorMessage(errorString);
            error.setErrors(errorHashMap);
        }

        if (error != null && callback != null) {
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }

        return this;
    }

    /**
     * Execute a Query and Caches its result (Optional)
     *
     * @param callBack {@link QueryResultsCallBack} object to notify the application when the request has completed.
     * @return {@linkplain Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *
     *      csQuery.findOne(new QueryResultsCallBack() {<br>
     *          &#64;Override
     *          public void onCompletion(ResponseType responseType, ENTRY entry, Error error) {<br>
     *
     *          }
     *      });<br>
     * </pre>
     */
    public Query findOne(SingleQueryResultCallback callBack) {
        Error error = null;
        try {
            if (isJsonProper) {
                if (!TextUtils.isEmpty(formName)) {

                    int limit = -1;
                    if (urlQueries != null && urlQueries.has("limit")) {
                        limit = (int) urlQueries.get("limit");
                    }
                    urlQueries.put("limit", 1);

                    execQuery(callBack, null, false);

                    if (limit != -1) {
                        urlQueries.put("limit", limit);
                    }

                } else {
                    //TODO ERROR HANDLE
                    throwException("find", CSAppConstants.ErrorMessage_FormName, null);
                    error = new Error();
                    error.setErrorMessage(errorString);
                    error.setErrors(errorHashMap);
                }
            } else {
                //TODO ERROR HANDLE
                error = new Error();
                error.setErrorMessage(errorString);
                error.setErrors(errorHashMap);
            }
        } catch (Exception e) {
            //TODO ERROR HANDLE
            throwException("find", CSAppConstants.ErrorMessage_JsonNotProper, null);
            error = new Error();
            error.setErrorMessage(errorString);
            error.setErrors(errorHashMap);
        }

        if (error != null && callBack != null) {
            callBack.onRequestFail(ResponseType.UNKNOWN, error);
        }

        return this;
    }

    /**
     * To cancel all {@link Query} network requests.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      csQuery.cancelRequest();
     * </pre>
     */
    public void cancelRequest() {
        CSAppConstants.cancelledCallController.add(CSAppConstants.callController.QUERY.toString());

        if (Contentstack.requestQueue != null) {
            Contentstack.requestQueue.cancelAll(CSAppConstants.callController.QUERY.toString());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void throwException(String queryName, String messageString, Exception e) {
        isJsonProper = false;
        errorString = messageString;
        errorHashMap = new HashMap<String, Object>();
        if (e != null) {
            errorHashMap.put(queryName, e.toString());
        }
    }

    protected void setQueryJson(QueryResultsCallBack callback) {
        try {

            if (queryValueJSON != null && queryValueJSON.length() > 0) {
                urlQueries.put("query", queryValueJSON);
            }

            if (objectUidForExcept != null && objectUidForExcept.length() > 0) {
                //JSONObject exceptValueJson = new JSONObject();
                //exceptValueJson.put("BASE", objectUidForExcept);
                urlQueries.put("except[BASE][]", objectUidForExcept);
                objectUidForExcept = null;

            }

            if (objectUidForOnly != null && objectUidForOnly.length() > 0) {
                //JSONObject onlyValueJson = new JSONObject();
                //onlyValueJson.put("BASE", objectUidForOnly);
                urlQueries.put("only[BASE][]", objectUidForOnly);
                objectUidForOnly = null;

            }

            if (onlyJsonObject != null && onlyJsonObject.length() > 0) {
                urlQueries.put("only", onlyJsonObject);
                onlyJsonObject = null;
            }

            if (exceptJsonObject != null && exceptJsonObject.length() > 0) {
                urlQueries.put("except", exceptJsonObject);
                exceptJsonObject = null;
            }

            if (objectUidForInclude != null && objectUidForInclude.length() > 0) {
                urlQueries.put("include[]", objectUidForInclude);
                objectUidForInclude = null;
            }

        } catch (Exception e) {
            //TODO ERROR HANDLER
            throwException("find", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
    }

    protected void execQuery(SingleQueryResultCallback callBack, QueryResultsCallBack callback, boolean isFromLocal) {
        try {

            String URL = "/" + contentTypeInstance.stackInstance.VERSION + "/content_types/" + formName + "/entries";

            queryResultCallback = callback;
            singleQueryResultCallback = callBack;

            setQueryJson(callback);

            //TODO Delta is not yet added
            ArrayMap<String, Object> headers = getHeader(localHeader);

            if (headers.size() < 1) {
                //TODO HANDLE HEADER NOT PRESENT ERROR
                throwException("find", CSAppConstants.ErrorMessage_CalledDefaultMethod, null);

            } else {

                if (headers.containsKey("environment")) {
                    urlQueries.put("environment", headers.get("environment"));
                }

                mainJSON.put("query", urlQueries);
                mainJSON.put("_method", CSAppConstants.RequestMethod.GET.toString());

                String mainStringForMD5 = URL + mainJSON.toString() + headers.toString();
                String md5Value = new CSAppUtils().getMD5FromString(mainStringForMD5.trim());

                File cacheFile = new File(CSAppConstants.cacheFolderName + File.separator + md5Value);

                CachePolicy cachePolicy = CachePolicy.NETWORK_ONLY;//contentTypeInstance.stackInstance.globalCachePolicyForCall;


                if (cachePolicyForCall != null) {
                    cachePolicy = cachePolicyForCall;
                }

                switch (cachePolicy) {
                    case IGNORE_CACHE:

                        fetchFromNetwork(URL, headers, mainJSON, null, callback, callBack);
                        break;

                    case CACHE_ONLY:

                        fetchFromCache(cacheFile, callback, callBack);
                        break;

                    case NETWORK_ONLY:

                        fetchFromNetwork(URL, headers, mainJSON, cacheFile.getPath(), callback, callBack);
                        break;

                    case CACHE_ELSE_NETWORK:

                        if (cacheFile.exists()) {
                            boolean needToSendCall = false;

                            if (maxCachetimeForCall > 0) {
                                needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) maxCachetimeForCall);
                            } else {
                                needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) defaultCacheTimeInterval);
                            }

                            if (needToSendCall) {
                                fetchFromNetwork(URL, headers, mainJSON, cacheFile.getPath(), callback, callBack);

                            } else {
                                setCacheModel(cacheFile, callback, callBack);
                            }

                        } else {
                            fetchFromNetwork(URL, headers, mainJSON, cacheFile.getPath(), callback, callBack);
                        }

                        break;

                    case NETWORK_ELSE_CACHE:

                        if (CSAppConstants.isNetworkAvailable) {
                            fetchFromNetwork(URL, headers, mainJSON, cacheFile.getPath(), callback, callBack);
                        } else {
                            fetchFromCache(cacheFile, callback, callBack);
                        }
                        break;

                    case CACHE_THEN_NETWORK:

                        if (cacheFile.exists()) {
                            setCacheModel(cacheFile, callback, callBack);
                        }

                        // from network
                        fetchFromNetwork(URL, headers, mainJSON, cacheFile.getPath(), callback, callBack);
                        break;

                    default:
                        break;
                }
            }


        } catch (Exception e) {
            //TODO HANDLE EXCEPTION
            throwException("find", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
    }

    //fetch from network.
    private void fetchFromNetwork(String URL, ArrayMap<String, Object> headers, JSONObject jsonMain, String cacheFilePath, ResultCallBack callback, SingleQueryResultCallback resultCallback) {

        HashMap<String, Object> urlParams = getUrlParams(jsonMain);

        if (resultCallback != null) {
            new CSBackgroundTask(this, contentTypeInstance.stackInstance, CSController.SINGLEQUERYOBJECT, URL, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.QUERY.toString(), CSAppConstants.RequestMethod.GET, resultCallback);
        } else {
            new CSBackgroundTask(this, contentTypeInstance.stackInstance, CSController.QUERYOBJECT, URL, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.QUERY.toString(), CSAppConstants.RequestMethod.GET, callback);
        }
    }

    private HashMap<String, Object> getUrlParams(JSONObject jsonMain) {

        JSONObject queryJSON = jsonMain.optJSONObject("query");
        HashMap<String, Object> hashMap = new HashMap<>();

        if (queryJSON != null && queryJSON.length() > 0) {
            Iterator<String> iter = queryJSON.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = queryJSON.opt(key);
                    hashMap.put(key, value);
                } catch (Exception e) {
                    CSAppUtils.showLog(TAG, "----------------setQueryJson" + e.toString());
                }
            }

            return hashMap;
        }

        return null;
    }

    //fetch from cache.
    private void fetchFromCache(File cacheFile, QueryResultsCallBack callback, SingleQueryResultCallback callBack) {
        Error error = null;

        if (cacheFile.exists()) {
            boolean needToSendCall = false;

            if (maxCachetimeForCall > 0) {
                needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) maxCachetimeForCall);
            } else {
                needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) defaultCacheTimeInterval);
            }

            if (needToSendCall) {
                error = new Error();
                error.setErrorMessage(CSAppConstants.ErrorMessage_EntryNotFoundInCache);

            } else {
                setCacheModel(cacheFile, callback, callBack);
            }
        } else {
            error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_EntryNotFoundInCache);
        }

        if (callback != null && error != null) {
            callback.onRequestFail(ResponseType.CACHE, error);
        } else if (callBack != null && error != null) {
            callBack.onRequestFail(ResponseType.CACHE, error);
        }
    }

    //ENTRY modeling from cache.
    private void setCacheModel(File cacheFile, QueryResultsCallBack callback, SingleQueryResultCallback callBack) {
        EntriesModel model = new EntriesModel(CSAppUtils.getJsonFromCacheFile(cacheFile), null, true);
        List<Entry> entries = new ArrayList<Entry>();
        List<java.lang.Object> objects = model.objectList;
        int countObject = objects.size();

        for (int i = 0; i < countObject; i++) {
            Entry entry = null;
            try {
                entry = contentTypeInstance.entry(((EntryModel) objects.get(i)).entryUid);

            } catch (Exception e) {
                entry = new Entry(formName);
                entry.setUid(((EntryModel) objects.get(i)).entryUid);
                CSAppUtils.showLog(TAG, "----------------getResultObject" + e.toString());
            }

            entry.resultJson = ((EntryModel) objects.get(i)).jsonObject;
            entry.ownerEmailId = ((EntryModel) objects.get(i)).ownerEmailId;
            entry.ownerUid = ((EntryModel) objects.get(i)).ownerUid;
            entry.title = ((EntryModel) objects.get(i)).title;
            entry.url = ((EntryModel) objects.get(i)).url;
            if (((EntryModel) objects.get(i)).ownerMap != null) {
                entry.owner = new HashMap<>(((EntryModel) objects.get(i)).ownerMap);
            }
            if (((EntryModel) objects.get(i))._metadata != null) {
                entry._metadata = new HashMap<>(((EntryModel) objects.get(i))._metadata);
            }

            entry.setTags(((EntryModel) objects.get(i)).tags);
            entries.add(entry);
        }

        if (callback != null) {
            QueryResult queryResultObject = new QueryResult();
            queryResultObject.setJSON(model.jsonObject, entries);
            callback.onRequestFinish(ResponseType.CACHE, queryResultObject);
            model = null;
        } else if (callBack != null) {

            Entry entry = contentTypeInstance.entry();
            if (entries != null && entries.size() > 0) {
                entry = entries.get(0);
            }
            callBack.onRequestFinish(ResponseType.CACHE, entry);
        }
    }

    @Override
    public void getResult(Object object, String controller) {
    }

    @Override
    public void getResultObject(List<Object> objects, JSONObject jsonObject, boolean isSingleEntry) {

        List<Entry> objectList = new ArrayList<Entry>();
        int countObject = objects.size();

        for (int i = 0; i < countObject; i++) {
            Entry entry = null;
            try {
                entry = contentTypeInstance.stackInstance.contentType(formName).entry(((EntryModel) objects.get(i)).entryUid);
            } catch (Exception e) {
                entry = new Entry(formName);
            }
            entry.setUid(((EntryModel) objects.get(i)).entryUid);
            entry.resultJson = ((EntryModel) objects.get(i)).jsonObject;
            entry.ownerEmailId = ((EntryModel) objects.get(i)).ownerEmailId;
            entry.ownerUid = ((EntryModel) objects.get(i)).ownerUid;
            entry.title = ((EntryModel) objects.get(i)).title;
            entry.url = ((EntryModel) objects.get(i)).url;

            if (((EntryModel) objects.get(i)).ownerMap != null) {
                entry.owner = new HashMap<>(((EntryModel) objects.get(i)).ownerMap);
            }
            if (((EntryModel) objects.get(i))._metadata != null) {
                entry._metadata = new HashMap<>(((EntryModel) objects.get(i))._metadata);
            }


            entry.setTags(((EntryModel) objects.get(i)).tags);
            objectList.add(entry);
        }

        if (isSingleEntry) {
            Entry entry = contentTypeInstance.entry();
            if (objectList != null && objectList.size() > 0) {
                entry = objectList.get(0);
            }

            if (singleQueryResultCallback != null) {
                singleQueryResultCallback.onRequestFinish(ResponseType.NETWORK, entry);
            }

        } else {
            QueryResult queryResultObject = new QueryResult();
            queryResultObject.setJSON(jsonObject, objectList);

            if (queryResultCallback != null) {
                queryResultCallback.onRequestFinish(ResponseType.NETWORK, queryResultObject);
            }
        }
    }

    private ArrayMap<String, Object> getHeader(ArrayMap<String, Object> localHeader) {
        ArrayMap<String, Object> mainHeader = formHeader;
        ArrayMap<String, Object> classHeaders = new ArrayMap<>();

        if (localHeader != null && localHeader.size() > 0) {
            if (mainHeader != null && mainHeader.size() > 0) {
                for (Map.Entry<String, Object> entry : localHeader.entrySet()) {
                    String key = entry.getKey();
                    classHeaders.put(key, entry.getValue());
                }

                for (Map.Entry<String, Object> entry : mainHeader.entrySet()) {
                    String key = entry.getKey();
                    if (!classHeaders.containsKey(key)) {
                        classHeaders.put(key, entry.getValue());
                    }
                }
                return classHeaders;
            } else {
                return localHeader;
            }

        } else {
            return formHeader;
        }
    }

    /**
     * This method adds key and value to an Entry.
     *
     * @param key   The key as string which needs to be added to the Query
     * @param value The value as string which needs to be added to the Query
     * @return {@link Query}
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *      csQuery.addParam("key", "some_value");
     *      csQuery.findOne(new QueryResultsCallBack() {<br>
     *          &#64;Override
     *          public void onCompletion(ResponseType responseType, ENTRY entry, Error error) {<br>
     *
     *          }
     *      });<br>
     * </pre>
     */
    public Query addParam(String key, String value) {
        try {
            if (key != null && value != null) {

                urlQueries.put(key, value);
            } else {
                throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, null);
            }
        } catch (Exception e) {
            throwException("and", CSAppConstants.ErrorMessage_QueryFilterException, e);
        }
        return this;
    }


    /**
     * This method also includes the content type UIDs of the referenced entries returned in the response
     *
     * @return {@link Query}
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      Query csQuery = stack.contentType("contentType_name").query();<br>
     *      csQuery.addParam("key", "some_value");
     *      csQuery.findOne(new QueryResultsCallBack() {<br>
     *          &#64;Override
     *          public void onCompletion(ResponseType responseType, ENTRY entry, Error error) {<br>
     *
     *          }
     *      });<br>
     * </pre>
     */
    public Query includeReferenceContentTypUid() {
        try {
            urlQueries.put("include_reference_content_type_uid", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Get entries having values based on referenced fields. This query retrieves all entries that satisfy the query conditions made on referenced fields.
     *
     * @param key The key to be constrained
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *     csQuery.whereIn("due_date");
     * </pre>
     */
    public Query whereIn(String key, Query queryObject) {

        if (key != null) {

            try {

                JSONObject inQueryObj = new JSONObject();
                inQueryObj.put("$in_query", queryObject.queryValueJSON.toString());
                queryValueJSON.put(key, inQueryObj);
            } catch (Exception e) {
                throwException("in_query", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("in_query", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }


    /**
     * Get entries having values based on referenced fields. This query works the opposite of $in_query and retrieves all entries that does not satisfy query conditions made on referenced fields.
     *
     * @param key The key to be constrained
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *     csQuery.whereNotIn("due_date");
     * </pre>
     */
    public Query whereNotIn(String key, Query queryObject) {

        if (key != null) {

            try {
                JSONObject inQueryObj = new JSONObject();
                inQueryObj.put("$nin_query", queryObject.queryValueJSON.toString());
                queryValueJSON.put(key, inQueryObj);
            } catch (Exception e) {
                throwException("nin_query", CSAppConstants.ErrorMessage_QueryFilterException, e);
            }
        } else {
            throwException("nin_query", CSAppConstants.ErrorMessage_QueryFilterException, null);
        }
        return this;
    }


    /**
     * Retrieve the published content of the fallback locale if an entry is not localized in specified locale.
     *
     * @return {@link Query} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     Stack stack = Contentstack.stack(context, "ApiKey", "deliveryToken",  environment_name);
     *     Query csQuery = stack.contentType("contentType_name").query();
     *     csQuery.includeFallback();
     * </pre>
     */
    public Query includeFallback() {
        try {
            mainJSON.put("include_fallback", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * includeEmbeddedItems instance of Query
     * Include Embedded Objects (Entries and Assets) along with entry/entries details.<br>
     * Stack stack = Contentstack.stack( "ApiKey", "deliveryToken", "environment");
     * final Query query = stack.contentType("user").query();
     * query = query.includeEmbeddedObjects()
     *
     * @return {@link Query}
     */
    public Query includeEmbeddedItems() {
        try {
            mainJSON.put("include_embedded_items[]", "BASE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

}

