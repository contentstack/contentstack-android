package com.contentstack.sdk;

import android.util.ArrayMap;
import android.text.TextUtils;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;
import com.contentstack.sdk.utilities.ContentstackUtil;
import com.contentstack.txtmark.Configuration;
import com.contentstack.txtmark.Processor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*** Entry is used to create, update and delete contentType&#39;s entries on the Contentstack.
 *
 * @author Contentstack.com, Inc
 *
 */
public class Entry {

    private static final String TAG = "Entry";

    private String contentTypeName = null;
    private ArrayMap<String, Object> localHeader = null;
    protected ArrayMap<String, Object> formHeader = null;
    private ContentType contentTypeInstance = null;
    private String[] tags = null;

    protected String uid = null;
    protected JSONObject resultJson = null;
    protected String ownerEmailId = null;
    protected String ownerUid = null;
    protected HashMap<String, Object> owner = null;
    protected HashMap<String, Object> _metadata = null;
    protected String title = null;
    protected String url = null;
    protected String language = null;
    private JSONArray referenceArray;
    protected JSONObject otherPostJSON;
    private JSONArray objectUidForOnly;
    private JSONArray objectUidForExcept;
    private JSONObject onlyJsonObject;
    private JSONObject exceptJsonObject;
    private CachePolicy cachePolicyForCall = CachePolicy.NETWORK_ONLY;

    private long maxCachetimeForCall = 0; //local cache time interval
    private long defaultCacheTimeInterval = 0;
    protected boolean isDelete = false;

    private Entry() {
    }

    protected Entry(String contentTypeName) {
        this.contentTypeName = contentTypeName;
        this.localHeader = new ArrayMap<>();
        this.otherPostJSON = new JSONObject();
    }

    protected void setContentTypeInstance(ContentType contentTypeInstance) {
        this.contentTypeInstance = contentTypeInstance;
    }

    public Entry configure(JSONObject jsonObject) {
        EntryModel model = new EntryModel(jsonObject, null, true, false, false);
        this.resultJson = model.jsonObject;
        this.ownerEmailId = model.ownerEmailId;
        this.ownerUid = model.ownerUid;
        this.title = model.title;
        this.url = model.url;
        this.language = model.language;
        if (model.ownerMap != null) {
            this.owner = new HashMap<>(model.ownerMap);
        }
        if (model._metadata != null) {
            this._metadata = new HashMap<>(model._metadata);
        }

        this.uid = model.entryUid;
        this.setTags(model.tags);
        model = null;

        return this;
    }

    /**
     * Set headers.
     *
     * @param key   custom_header_key
     * @param value custom_header_value
     *
     *              <br><br><b>Example :</b><br>
     *              <pre class="prettyprint">
     *              //'blt5d4sample2633b' is a dummy Stack API key
     *              //'blt6d0240b5sample254090d' is dummy access token.
     *              Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *              Entry entry = stack.contentType("form_name").entry("entry_uid");
     *              entry.setHeader("custom_header_key", "custom_header_value");
     *              </pre>
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
     *            //'blt5d4sample2633b' is a dummy Stack API key
     *            //'blt6d0240b5sample254090d' is dummy access token.
     *            Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *            Entry entry = stack.contentType("form_name").entry("entry_uid");
     *            entry.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    /**
     * Get title string
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String title = entry.getTitle();
     * </pre>
     * </p>
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get url string
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String url = entry.getURL();
     * </pre>
     * </p>
     */
    public String getURL() {
        return url;
    }

    /**
     * Get tags.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String[] tags = entry.getURL();
     * </pre>
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Get contentType name.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String contentType = entry.getFileType();
     * </pre>
     */
    public String getContentType() {
        return contentTypeName;
    }

    /**
     * Get uid.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String uid = entry.getUid();
     * </pre>
     */
    public String getUid() {
        return uid;
    }

    /**
     * Get metadata of entry.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * HashMap<String, Object> metaData = entry.getMetadata();
     * </pre>
     */
    private HashMap<String, Object> getMetadata() {
        return _metadata;
    }

    /**
     * Get {@link Language} instance
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Language local = entry.getLanguage();
     * </pre>
     * </p>
     */
    @Deprecated
    public Language getLanguage() {
        String localeCode = null;

        if (_metadata != null && _metadata.size() > 0 && _metadata.containsKey("locale")) {
            localeCode = (String) _metadata.get("locale");
        } else if (resultJson.has("locale")) {
            localeCode = (String) resultJson.optString("locale");
        }

        if (localeCode != null) {
            localeCode = localeCode.replace("-", "_");
            LanguageCode codeValue = LanguageCode.valueOf(localeCode);
            int localeValue = codeValue.ordinal();
            Language[] language = Language.values();

            return language[localeValue];
        }
        return null;
    }


    /**
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String local = entry.getLocale();
     * </pre>
     * </p>
     */
    public String getLocale() {

        if (resultJson.has("locale")) {
            return (String) resultJson.optString("locale");
        }

        return this.language;
    }


    /**
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Entry entry = entry.setLocale("en-hi");
     * </pre>
     * </p>
     */
    public Entry setLocale(String locale) {

        if (locale != null) {
            try {
                otherPostJSON.put("locale", locale);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return this;
    }


    public HashMap<String, Object> getOwner() {
        return owner;
    }

    /**
     * Get entry representation in json
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * JSONObject json = entry.toJSON();
     * </pre>
     * </p>
     */
    public JSONObject toJSON() {
        return resultJson;
    }

    /**
     * Get object value for key.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Object obj = entry.get("key");
     *            </pre>
     */
    public Object get(String key) {
        try {
            if (resultJson != null && key != null) {
                return resultJson.get(key);
            } else {
                return null;
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------get|" + e);
            return null;
        }
    }

    /**
     * Get html text for markdown data type
     *
     * @param markdownKey field_uid as key.
     * @return html text in string format.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String htmlText = entry.getHtmlText("markdownKey");
     * </pre>
     */
    public String getHtmlText(String markdownKey) {
        try {
            return Processor.process(getString(markdownKey), Configuration.builder().forceExtentedProfile().build());
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getHtmlText|" + e);
            return null;
        }
    }

    /**
     * Get html text for markdown data type which is multiple true
     *
     * @param markdownKey field_uid as key.
     * @return html text in string format.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * ArrayList&#60;String&#62; htmlTexts = entry.getMultipleHtmlText("markdownKey");
     * </pre>
     */
    public ArrayList<String> getMultipleHtmlText(String markdownKey) {
        try {
            ArrayList<String> multipleHtmlStrings = new ArrayList<>();
            JSONArray jsonArray = getJSONArray(markdownKey);
            for (int i = 0; i < jsonArray.length(); i++) {
                multipleHtmlStrings.add(Processor.process(jsonArray.getString(i), Configuration.builder().forceExtentedProfile().build()));
            }
            return multipleHtmlStrings;
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getHtmlText|" + e);
            return null;
        }
    }

    /**
     * Get string value for key.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            String value = entry.getString("key");
     *            </pre>
     */
    public String getString(String key) {
        Object value = get(key);
        if (value != null) {
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    /**
     * Get boolean value for key.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Boolean value = entry.getBoolean("key");
     *            </pre>
     */
    public Boolean getBoolean(String key) {
        Object value = get(key);
        if (value != null) {
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
        }
        return false;
    }

    /**
     * Get {@link JSONArray} value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            JSONArray value = entry.getJSONArray("key");
     *            </pre>
     */
    public JSONArray getJSONArray(String key) {
        Object value = get(key);
        if (value != null) {
            if (value instanceof JSONArray) {
                return (JSONArray) value;
            }
        }
        return null;
    }

    /**
     * Get {@link JSONObject} value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            JSONObject value = entry.getJSONObject("key");
     *            </pre>
     */
    public JSONObject getJSONObject(String key) {
        Object value = get(key);
        if (value != null) {
            if (value instanceof JSONObject) {
                return (JSONObject) value;
            }
        }
        return null;
    }

    /**
     * Get {@link JSONObject} value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            JSONObject value = entry.getJSONObject("key");
     *            </pre>
     */
    public Number getNumber(String key) {
        Object value = get(key);
        if (value != null) {
            if (value instanceof Number) {
                return (Number) value;
            }
        }
        return null;
    }

    /**
     * Get integer value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            int value = entry.getInt("key");
     *            </pre>
     */
    public int getInt(String key) {
        Number value = getNumber(key);
        if (value != null) {
            return value.intValue();
        }
        return 0;
    }

    /**
     * Get integer value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            float value = entry.getFloat("key");
     *            </pre>
     */
    public float getFloat(String key) {
        Number value = getNumber(key);
        if (value != null) {
            return value.floatValue();
        }
        return (float) 0;
    }

    /**
     * Get double value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            double value = entry.getDouble("key");
     *            </pre>
     */
    public double getDouble(String key) {
        Number value = getNumber(key);
        if (value != null) {
            return value.doubleValue();
        }
        return (double) 0;
    }

    /**
     * Get long value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            long value = entry.getLong("key");
     *            </pre>
     */
    public long getLong(String key) {
        Number value = getNumber(key);
        if (value != null) {
            return value.longValue();
        }
        return (long) 0;
    }

    /**
     * Get short value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            short value = entry.getShort("key");
     *            </pre>
     */
    public short getShort(String key) {
        Number value = getNumber(key);
        if (value != null) {
            return value.shortValue();
        }
        return (short) 0;
    }

    /**
     * Get {@link Calendar} value for key
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Calendar value = entry.getDate("key");
     *            </pre>
     */
    public Calendar getDate(String key) {

        try {
            String value = getString(key);
            return ContentstackUtil.parseDate(value, null);
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getDate|" + e);
        }
        return null;
    }

    /**
     * Get {@link Calendar} value of creation time of entry.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Calendar createdAt = entry.getCreateAt("key");
     * </pre>
     */
    public Calendar getCreateAt() {

        try {
            String value = getString("created_at");
            return ContentstackUtil.parseDate(value, null);
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getCreateAtDate|" + e);
        }
        return null;
    }

    /**
     * Get uid who created this entry.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String createdBy_uid = entry.getCreatedBy();
     * </pre>
     */
    public String getCreatedBy() {

        return getString("created_by");
    }

    /**
     * Get {@link Calendar} value of updating time of entry.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Calendar updatedAt = entry.getUpdateAt("key");
     * </pre>
     */
    public Calendar getUpdateAt() {

        try {
            String value = getString("updated_at");
            return ContentstackUtil.parseDate(value, null);
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getUpdateAtDate|" + e);
        }
        return null;
    }

    /**
     * Get uid who updated this entry.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String updatedBy_uid = entry.getUpdatedBy();
     * </pre>
     */
    public String getUpdatedBy() {

        return getString("updated_by");
    }

    /**
     * Get {@link Calendar} value of deletion time of entry.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Calendar updatedAt = entry.getUpdateAt("key");
     * </pre>
     */
    public Calendar getDeleteAt() {

        try {
            String value = getString("deleted_at");
            return ContentstackUtil.parseDate(value, null);
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------getDeleteAt|" + e);
        }
        return null;
    }

    /**
     * Get uid who deleted this entry.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String deletedBy_uid = entry.getDeletedBy();
     * </pre>
     */
    public String getDeletedBy() {

        return getString("deleted_by");
    }

    /**
     * Get an asset from the entry
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Asset asset = entry.getAsset("key");
     *            </pre>
     */
    public Asset getAsset(String key) {

        JSONObject assetObject = getJSONObject(key);
        Asset asset = contentTypeInstance.stackInstance.asset().configure(assetObject);

        return asset;
    }

    /**
     * Get an assets from the entry. This works with multiple true fields
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            List<Asset> asset = entry.getAssets("key");
     *            </pre>
     */
    public List<Asset> getAssets(String key) {
        List<Asset> assets = new ArrayList<>();
        JSONArray assetArray = getJSONArray(key);

        for (int i = 0; i < assetArray.length(); i++) {

            if (assetArray.opt(i) instanceof JSONObject) {
                Asset asset = contentTypeInstance.stackInstance.asset().configure(assetArray.optJSONObject(i));
                assets.add(asset);
            }
        }
        return assets;
    }

    /**
     * Get a group from entry.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Group innerGroup = entry.getGroup("key");
     *            </pre>
     */
    public Group getGroup(String key) {

        if (!TextUtils.isEmpty(key) && resultJson.has(key) && resultJson.opt(key) instanceof JSONObject) {
            return new Group(contentTypeInstance.stackInstance, resultJson.optJSONObject(key));
        }
        return null;
    }

    /**
     * Get a list of group from entry.
     *
     * <p>
     * <b>Note :-</b> This will work when group is multiple true.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Group innerGroup = entry.getGroups("key");
     *            </pre>
     */
    public List<Group> getGroups(String key) {

        if (!TextUtils.isEmpty(key) && resultJson.has(key) && resultJson.opt(key) instanceof JSONArray) {
            JSONArray array = resultJson.optJSONArray(key);
            List<Group> groupList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                if (array.opt(i) instanceof JSONObject) {
                    Group group = new Group(contentTypeInstance.stackInstance, array.optJSONObject(i));
                    groupList.add(group);
                }
            }

            return groupList;
        }
        return null;
    }

    /**
     * Get value for the given reference key.
     *
     * @param refKey         key of a reference field.
     * @param refContentType class uid.
     * @return {@link ArrayList} of {@link Entry} instances.
     * Also specified contentType value will be set as class uid for all {@link Entry} instance.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *  Query csQuery = stack.contentType("contentType_name").query();
     *
     * csQuery.includeReference("for_bug");
     *
     * csQuery.find(new QueryResultsCallBack() {<br>
     *          &#64;Override
     *          public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {<br>
     *
     *              if(error == null){
     *                  List&#60;Entry&#62; list = builtqueryresult.getResultObjects();
     *                   for (int i = 0; i < list.queueSize(); i++) {
     *                           Entry   entry   = list.get(i);
     *                           Entry taskEntry = entry.getAllEntries("for_task", "task");
     *                  }
     *              }
     *
     *          }
     *      });<br>
     *
     * </pre>
     */
    public ArrayList<Entry> getAllEntries(String refKey, String refContentType) {
        try {
            if (resultJson != null) {

                if (resultJson.get(refKey) instanceof JSONArray) {

                    int count = ((JSONArray) resultJson.get(refKey)).length();
                    ArrayList<Entry> builtObjectList = new ArrayList<Entry>();
                    for (int i = 0; i < count; i++) {

                        EntryModel model = new EntryModel(((JSONArray) resultJson.get(refKey)).getJSONObject(i), null, false, false, true);
                        Entry entryInstance = null;
                        try {
                            entryInstance = contentTypeInstance.stackInstance.contentType(refContentType).entry();
                        } catch (Exception e) {
                            entryInstance = new Entry(refContentType);
                            CSAppUtils.showLog("BuiltObject", "----------------getAllEntries" + e.toString());
                        }
                        entryInstance.setUid(model.entryUid);
                        entryInstance.ownerEmailId = model.ownerEmailId;
                        entryInstance.ownerUid = model.ownerUid;
                        if (model.ownerMap != null) {
                            entryInstance.owner = new HashMap<>(model.ownerMap);
                        }
                        entryInstance.resultJson = model.jsonObject;
                        entryInstance.setTags(model.tags);

                        builtObjectList.add(entryInstance);
                        model = null;
                    }

                    return builtObjectList;

                }
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "-----------------get|" + e);
            return null;
        }

        return null;
    }

    /**
     * Specifies list of field uids that would be &#39;excluded&#39; from the response.
     *
     * @param fieldUid field uid  which get &#39;excluded&#39; from the response.
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *     entry.except(new String[]{"name", "description"});
     * </pre>
     */
    public Entry except(String[] fieldUid) {
        try {
            if (fieldUid != null && fieldUid.length > 0) {

                if (objectUidForExcept == null) {
                    objectUidForExcept = new JSONArray();
                }

                int count = fieldUid.length;
                for (int i = 0; i < count; i++) {
                    objectUidForExcept.put(fieldUid[i]);
                }
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--except-catch|" + e);
        }
        return this;
    }

    /**
     * Add a constraint that requires a particular reference key details.
     *
     * @param referenceField key that to be constrained.
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *    entry.includeReference("referenceUid");
     * </pre>
     */
    public Entry includeReference(String referenceField) {
        try {
            if (!TextUtils.isEmpty(referenceField)) {
                if (referenceArray == null) {
                    referenceArray = new JSONArray();
                }

                referenceArray.put(referenceField);

                otherPostJSON.put("include[]", referenceArray);
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--include Reference-catch|" + e);
        }

        return this;
    }

    /**
     * Add a constraint that requires a particular reference key details.
     *
     * @param referenceFields array key that to be constrained.
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *    entry.includeReference(new String[]{"referenceUid_A", "referenceUid_B"});
     * </pre>
     */
    public Entry includeReference(String[] referenceFields) {
        try {
            if (referenceFields != null && referenceFields.length > 0) {
                if (referenceArray == null) {
                    referenceArray = new JSONArray();
                }
                for (int i = 0; i < referenceFields.length; i++) {
                    referenceArray.put(referenceFields[i]);
                }

                otherPostJSON.put("include[]", referenceArray);
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--include Reference-catch|" + e);
        }

        return this;
    }

    /**
     * Specifies an array of &#39;only&#39; keys in BASE object that would be &#39;included&#39; in the response.
     *
     * @param fieldUid Array of the &#39;only&#39; reference keys to be included in response.
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *    entry.only(new String[]{"name", "description"});
     * </pre>
     */
    public Entry only(String[] fieldUid) {
        try {
            if (fieldUid != null && fieldUid.length > 0) {
                if (objectUidForOnly == null) {
                    objectUidForOnly = new JSONArray();
                }

                int count = fieldUid.length;
                for (int i = 0; i < count; i++) {
                    objectUidForOnly.put(fieldUid[i]);
                }
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--include Reference-catch|" + e);
        }

        return this;
    }

    /**
     * Specifies an array of &#39;only&#39; keys that would be &#39;included&#39; in the response.
     *
     * @param fieldUid          Array of the &#39;only&#39; reference keys to be included in response.
     * @param referenceFieldUid Key who has reference to some other class object..
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *    ArrayList&#60;String&#62; array = new ArrayList&#60;String&#62;();
     *    array.add("description");
     *    array.add("name");<br>
     *    entry.onlyWithReferenceUid(array, "referenceUid");
     * </pre>
     */
    public Entry onlyWithReferenceUid(ArrayList<String> fieldUid, String referenceFieldUid) {
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

                includeReference(referenceFieldUid);

            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--onlyWithReferenceUid-catch|" + e);
        }
        return this;
    }

    /**
     * Specifies an array of &#39;except&#39; keys that would be &#39;excluded&#39; in the response.
     *
     * @param fieldUid          Array of the &#39;except&#39; reference keys to be excluded in response.
     * @param referenceFieldUid Key who has reference to some other class object.
     * @return {@link Entry} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *    ArrayList&#60;String&#62; array = new ArrayList&#60;String&#62;();
     *    array.add("description");
     *    array.add("name");<br>
     *    entry.onlyWithReferenceUid(array, "referenceUid");
     * </pre>
     */
    public Entry exceptWithReferenceUid(ArrayList<String> fieldUid, String referenceFieldUid) {
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

                includeReference(referenceFieldUid);
            }
        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "--exceptWithReferenceUid-catch|" + e);
        }
        return this;
    }


    protected void setTags(String[] tags) {
        this.tags = tags;
    }

    protected void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Fetches the latest version of the entries from Built.io content stack
     *
     * @param callBack {@link EntryResultCallBack} object to notify the application when the request has completed.
     *                 <p>
     *                 {@link Entry} object, so you can chain this call.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre class="prettyprint">
     *                    //'blt5d4sample2633b' is a dummy Stack API key
     *                    //'blt6d0240b5sample254090d' is dummy access token.
     *                    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *                    Entry entry = stack.contentType("form_name").entry("entry_uid");<br>
     *                    entry.fetch(new BuiltResultCallBack() {<br>
     *                           &#64;Override
     *                           public void onCompletion(ResponseType responseType, BuiltError builtError) {
     *
     *                           }<br>
     *                    });<br>
     */
    public void fetch(EntryResultCallBack callBack) {
        try {
            if (!TextUtils.isEmpty(uid)) {

                String URL = "/" + contentTypeInstance.stackInstance.VERSION + "/content_types/" + contentTypeName + "/entries/" + uid;

                ArrayMap<String, Object> headers = getHeader(localHeader);
                HashMap<String, String> headerAll = new HashMap<String, String>();
                JSONObject urlQueries = new JSONObject();

                if (headers != null && headers.size() > 0) {
                    for (Map.Entry<String, Object> entry : headers.entrySet()) {
                        headerAll.put(entry.getKey(), (String) entry.getValue());
                    }

                    if (headers.containsKey("environment")) {
                        urlQueries.put("environment", headers.get("environment"));
                    }
                }

                String mainStringForMD5 = URL + new JSONObject().toString() + headerAll.toString();
                String md5Value = new CSAppUtils().getMD5FromString(mainStringForMD5.trim());

                File cacheFile = new File(CSAppConstants.cacheFolderName + File.separator + md5Value);


                switch (cachePolicyForCall) {

                    case IGNORE_CACHE:
                        fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);
                        break;

                    case NETWORK_ONLY:
                        fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);
                        break;

                    case CACHE_ONLY:
                        fetchFromCache(cacheFile, callBack);
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
                                fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);

                            } else {
                                setCacheModel(cacheFile, callBack);
                            }

                        } else {
                            fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);
                        }

                        break;

                    case CACHE_THEN_NETWORK:
                        if (cacheFile.exists()) {
                            setCacheModel(cacheFile, callBack);
                        }

                        // from network
                        fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);
                        break;

                    case NETWORK_ELSE_CACHE:

                        if (CSAppConstants.isNetworkAvailable) {
                            fetchFromNetwork(URL, urlQueries, cacheFile.getPath(), callBack);
                        } else {
                            fetchFromCache(cacheFile, callBack);
                        }

                        break;

                }

            } else {
                throwException(CSAppConstants.ErrorMessage_EntryUID, null, callBack);
            }
        } catch (Exception e) {
            throwException(null, e, callBack);
        }
    }

    private void fetchFromNetwork(String URL, JSONObject urlQueries, String cacheFilePath, EntryResultCallBack callBack) {
        try {

            JSONObject mainJson = new JSONObject();

            setIncludeJSON(urlQueries, callBack);
            mainJson.put("query", urlQueries);

            mainJson.put("_method", CSAppConstants.RequestMethod.GET.toString());

            HashMap<String, Object> urlParams = getUrlParams(mainJson);

            new CSBackgroundTask(this, contentTypeInstance.stackInstance, CSController.FETCHENTRY, URL, getHeader(localHeader), urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.ENTRY.toString(), false, CSAppConstants.RequestMethod.GET, callBack);

        } catch (Exception e) {
            throwException(null, e, callBack);
        }
    }

    //fetch from cache.
    private void fetchFromCache(File cacheFile, EntryResultCallBack callback) {
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
                setCacheModel(cacheFile, callback);
            }
        } else {
            error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_EntryNotFoundInCache);
        }

        if (callback != null && error != null) {
            callback.onRequestFail(ResponseType.CACHE, error);
        }
    }

    //Entry modeling from cache.
    private void setCacheModel(File cacheFile, EntryResultCallBack callback) {

        EntryModel model = new EntryModel(CSAppUtils.getJsonFromCacheFile(cacheFile), null, false, true, false);
        this.resultJson = model.jsonObject;
        this.ownerEmailId = model.ownerEmailId;
        this.ownerUid = model.ownerUid;
        this.title = model.title;
        this.url = model.url;
        if (model.ownerMap != null) {
            this.owner = new HashMap<>(model.ownerMap);
        }
        if (model._metadata != null) {
            this._metadata = new HashMap<>(model._metadata);
        }

        this.uid = model.entryUid;
        setTags(model.tags);
        model = null;

        if (callback != null) {
            callback.onRequestFinish(ResponseType.CACHE);
        }
    }

    /**
     * To cancel all {@link Entry} network requests.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     entry.cancelRequest();
     * </pre>
     */

    public void cancelRequest() {
        CSAppConstants.cancelledCallController.add(CSAppConstants.callController.ENTRY.toString());

        if (Contentstack.requestQueue != null) {
            Contentstack.requestQueue.cancelAll(CSAppConstants.callController.ENTRY.toString());
        }
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
     *      Entry entry = stack.contentType("form_name").entry();<br>
     *      entry.setCachePolicy(NETWORK_ELSE_CACHE);
     * </pre>
     */
    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicyForCall = cachePolicy;
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

    private void setIncludeJSON(JSONObject mainJson, ResultCallBack callBack) {
        try {
            Iterator<String> iterator = otherPostJSON.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = otherPostJSON.get(key);
                mainJson.put(key, value);
            }

            if (objectUidForOnly != null && objectUidForOnly.length() > 0) {
                mainJson.put("only[BASE][]", objectUidForOnly);
                objectUidForOnly = null;

            }

            if (objectUidForExcept != null && objectUidForExcept.length() > 0) {
                mainJson.put("except[BASE][]", objectUidForExcept);
                objectUidForExcept = null;
            }

            if (exceptJsonObject != null && exceptJsonObject.length() > 0) {
                mainJson.put("except", exceptJsonObject);
                exceptJsonObject = null;
            }

            if (onlyJsonObject != null && onlyJsonObject.length() > 0) {
                mainJson.put("only", onlyJsonObject);
                onlyJsonObject = null;
            }
        } catch (Exception e) {
            throwException(null, e, (EntryResultCallBack) callBack);
        }
    }

    private void throwException(String errorMsg, Exception e, EntryResultCallBack callBack) {

        Error error = new Error();
        if (errorMsg != null) {
            error.setErrorMessage(errorMsg);
        } else {
            error.setErrorMessage(e.toString());
        }

        callBack.onRequestFail(ResponseType.UNKNOWN, error);

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
     * @param key   The key as string which needs to be added to an Entry
     * @param value The value as string which needs to be added to an Entry
     * @return {@link Entry}
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'blt6d0240b5sample254090d' is dummy access token.
     *    {@code
     *
     *    Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *    final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762"); <br>
     *    entry.addParam("include_dimensions", "true"); <br>
     *    entry.fetch(new BuiltResultCallBack() {<br>
     *           &#64;Override
     *           public void onCompletion(ResponseType responseType, BuiltError builtError) {
     *
     *           }<br>
     *    });<br>
     *
     *      }
     *   </pre>
     */

    public Entry addParam(String key, String value) {

        if (key != null && value != null) {
            try {
                otherPostJSON.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return this;
    }


    /**
     * This method also includes the content type UIDs of the referenced entries returned in the response
     *
     * @return {@link Entry}
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * {@code
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     * final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762"); <br>
     * entry.includeReferenceContentTypeUID; <br>
     * entry.fetch(new BuiltResultCallBack() {
     * <br>&#64;
     * Override
     * public void onCompletion(ResponseType responseType, BuiltError builtError) {
     *  }<br>
     * });<br>
     *  }
     * </pre>
     */
    public Entry includeReferenceContentTypeUID() {
        try {
            otherPostJSON.put("include_reference_content_type_uid", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Include Content Type of all returned objects along with objects themselves.
     *
     * @return {@link Entry} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     //'blt5d4sample2633b' is a dummy Stack API key
     *     //'blt6d0240b5sample254090d' is dummy access token.
     *     Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *     final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762");
     *     entry.includeContentType();
     * </pre>
     */
    public Entry includeContentType() {
        try {
            if (otherPostJSON.has("include_schema")) {
                otherPostJSON.remove("include_schema");
            }
            otherPostJSON.put("include_content_type", true);
            otherPostJSON.put("include_global_field_schema", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Retrieve the published content of the fallback locale if an entry is not localized in specified locale.
     *
     * @return {@link Entry} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     Stack stack = Contentstack.stack(context, "ApiKey", "deliveryToken", "environment");
     *     final Entry entry = stack.contentType("user").entry("entryUid");
     *     entry.includeFallback();
     * </pre>
     */
    public Entry includeFallback() {
        try {
            otherPostJSON.put("include_fallback", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * includeEmbeddedItems instance of Entry
     * Include Embedded Objects (Entries and Assets) along with entry/entries details.<br>
     * Stack stack = Contentstack.stack( "ApiKey", "deliveryToken", "environment");
     * final Entry entry = stack.contentType("user").entry("entry_uid");
     * entry = entry.includeEmbeddedObjects()
     *
     * @return {@link Entry}
     */
    public Entry includeEmbeddedItems() {
        try {
            otherPostJSON.put("include_embedded_items[]", "BASE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}
