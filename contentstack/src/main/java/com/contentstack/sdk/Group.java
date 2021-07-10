package com.contentstack.sdk;

import android.text.TextUtils;

import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.ContentstackUtil;
import com.contentstack.txtmark.Configuration;
import com.contentstack.txtmark.Processor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/***
 * Group class is represent group field uid value.
 *
 * @author Contentstack.com, Inc
 *
 */
public class Group {

    private static final String TAG = "Group";
    private JSONObject resultJson;
    private Stack stackInstance;

    protected Group(Stack stack, JSONObject jsonObject) {
        resultJson = jsonObject;
        stackInstance = stack;
    }

    /**
     * Get group representation in json
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * JSONObject json = group.toJSON();
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
     *            Object obj = group.get("key");
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
     * String htmlText = group.getHtmlText("markdownKey");
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
     * ArrayList&#60;String&#62; htmlTexts = group.getMultipleHtmlText("markdownKey");
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
     *            String value = group.getString("key");
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
     *            Boolean value = group.getBoolean("key");
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
     *            JSONArray value = group.getJSONArray("key");
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
     *            JSONObject value = group.getJSONObject("key");
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
     *            JSONObject value = group.getJSONObject("key");
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
     *            int value = group.getInt("key");
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
     *            float value = group.getFloat("key");
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
     *            double value = group.getDouble("key");
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
     *            long value = group.getLong("key");
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
     *            short value = group.getShort("key");
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
     *            Calendar value = group.getDate("key");
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
     * Get an asset from the group
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Asset asset = group.getAsset("key");
     *            </pre>
     */
    public Asset getAsset(String key) {

        JSONObject assetObject = getJSONObject(key);

        return stackInstance.asset().configure(assetObject);
    }

    /**
     * Get an assets from the group. This works with multiple true fields
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            List<Asset> asset = group.getAssets("key");
     *            </pre>
     */
    public List<Asset> getAssets(String key) {
        List<Asset> assets = new ArrayList<>();
        JSONArray assetArray = getJSONArray(key);

        for (int i = 0; i < assetArray.length(); i++) {

            if (assetArray.opt(i) instanceof JSONObject) {
                Asset asset = stackInstance.asset().configure(assetArray.optJSONObject(i));
                assets.add(asset);
            }

        }
        return assets;
    }


    /**
     * Get a group from the group.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Group innerGroup = group.getGroup("key");
     *            </pre>
     */
    public Group getGroup(String key) {

        if (!TextUtils.isEmpty(key) && resultJson.has(key) && resultJson.opt(key) instanceof JSONObject) {
            return new Group(stackInstance, resultJson.optJSONObject(key));
        }
        return null;
    }

    /**
     * Get a list of group from the group.
     *
     * <p>
     * <b>Note :-</b> This will work when group is multiple true.
     *
     * @param key field_uid as key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            Group innerGroup = group.getGroups("key");
     *            </pre>
     */
    public List<Group> getGroups(String key) {

        if (!TextUtils.isEmpty(key) && resultJson.has(key) && resultJson.opt(key) instanceof JSONArray) {
            JSONArray array = resultJson.optJSONArray(key);
            List<Group> groupList = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                if (array.opt(i) instanceof JSONObject) {
                    Group group = new Group(stackInstance, array.optJSONObject(i));
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
     *                           Group   group   = entry.getGroup("fieldUid");
     *                           Entry taskEntry = entry.getAllEntries("for_task", "task");
     *                   }
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
                            entryInstance = stackInstance.contentType(refContentType).entry();
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


}
