package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;
import com.contentstack.sdk.utilities.ContentstackUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Asset class to fetch files details on Conentstack server.
 *
 * @author contentstack.com. Inc
 */
public class Asset {

    private final static String TAG = "Asset";
    /*For SingleUpload variable*/
    protected String assetUid = null;
    protected String contentType = null;
    protected String fileSize = null;
    protected String fileName = null;
    protected String uploadUrl = null;
    protected JSONObject json = null;
    protected String[] tagsArray = null;
    JSONObject urlQueries = new JSONObject();
    protected ArrayMap<String, Object> headerGroup_app;
    protected ArrayMap<String, Object> headerGroup_local;
    protected Stack stackInstance;
    private CachePolicy cachePolicyForCall = CachePolicy.IGNORE_CACHE;

    private final long maxCachetimeForCall = 0; //local cache time interval
    private final long defaultCacheTimeInterval = 0;

    protected Asset() {
        this.headerGroup_local = new ArrayMap<>();
        this.headerGroup_app = new ArrayMap<>();
    }

    protected Asset(String assetUid) {
        this.assetUid = assetUid;
        this.headerGroup_local = new ArrayMap<>();
        this.headerGroup_app = new ArrayMap<>();
    }

    protected void setStackInstance(Stack stack) {
        this.stackInstance = stack;
        this.headerGroup_app = stack.localHeader;
    }

    /**
     * Creates new instance of {@link Asset} from valid {@link JSONObject}.
     * If JSON object is not appropriate then it will return null.
     *
     * @param jsonObject json object of particular file attached in the built object.<br>
     *                   <p>
     *                   {@link Asset} can be generate using of data filled {@link Entry} and {@link #configure(JSONObject)}.<br>
     *
     *                   <br><br><b>Example :</b><br>
     *                   <br>1. Single Attachment :-<br>
     *                   <pre class="prettyprint linenums:1">
     *                      //'blt5d4sample2633b' is a dummy Application API key
     *                      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config);
     *
     *                      Asset assetObject = stack.asset("assetUid");<br>
     *                      assetObject.configure(entryObject.getJSONObject(attached_image_field_uid));</pre>
     *
     *                   <br>2. Multiple Attachment :-<br>
     *
     *                   <pre class="prettyprint linenums:1">
     *                   JSONArray array = entryObject.getJSONArray(Attach_Image_Field_Uid);<br>
     *                      for (int i = 0; i < array.length(); i++) {<br>
     *                    	  Asset assetObject = stack.asset("assetUid");<br>
     *                    	  assetObject.configure(entryObject.getJSONObject(attached_image_field_uid));<br>
     *                      }<br>
     *                   </pre>
     * @return {@link Asset} instance.
     */
    public Asset configure(JSONObject jsonObject) {

        AssetModel model = null;

        model = new AssetModel(jsonObject, true, false);

        this.contentType = model.contentType;
        this.fileSize = model.fileSize;
        this.uploadUrl = model.uploadUrl;
        this.fileName = model.fileName;
        this.json = model.json;
        this.assetUid = model.uploadedUid;
        this.setTags(model.tags);

        model = null;

        return this;
    }


    /**
     * To set headers for Built.io Contentstack rest calls.
     * <br>
     * Scope is limited to this object only.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     *              <br><br><b>Example :</b><br>
     *              <pre class="prettyprint">
     *              //'blt5d4sample2633b' is a dummy Application API key
     *              Asset assetObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).asset("assetUid");
     *
     *              assetObject.setHeader("custom_header_key", "custom_header_value");
     *              </pre>
     */
    public void setHeader(String key, String value) {

        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            removeHeader(key);
            headerGroup_local.put(key, value);
        }
    }

    /**
     * Remove a header for a given key from headers.
     * <br>
     * Scope is limited to this object only.
     *
     * @param key header key.
     *
     *            <br><br><b>Example :</b><br>
     *            <pre class="prettyprint">
     *            //'blt5d4sample2633b' is a dummy Application API key
     *            Asset assetObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).asset("assetUid");
     *
     *            assetObject.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (headerGroup_local != null) {

            if (!TextUtils.isEmpty(key)) {
                if (headerGroup_local.containsKey(key)) {
                    headerGroup_local.remove(key);
                }
            }
        }
    }

    /**
     * To set uid of media file which is uploaded on Built.io Contentstack server.
     *
     * @param assetUid upload uid.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre class="prettyprint">
     *                 //'blt5d4sample2633b' is a dummy Application API key
     *                 Asset assetObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).asset("assetUid");
     *
     *                 assetObject.setUid("upload_uid");
     *                 </pre>
     */
    protected void setUid(String assetUid) {
        if (!TextUtils.isEmpty(assetUid)) {
            this.assetUid = assetUid;
        }
    }

    /**
     * Returns media file upload uid. You will get uploaded uid after uploading media file on Built.io Contentstack server.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String uid = assetObject.getAssetUid();
     * </pre>
     */
    public String getAssetUid() {
        return assetUid;
    }

    /**
     * Returns content type of the uploaded file.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String contentType = assetObject.getFileType();
     * </pre>
     */
    public String getFileType() {
        return contentType;
    }

    /**
     * Returns file queueSize of the uploaded file.
     *
     * <p>
     * <br><b>Note :</b><br> file size will receive in bytes number.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String queueSize = assetObject.getFileSize();
     * </pre>
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * Returns file name of the uploaded file.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String fileName = assetObject.getFileName();
     * </pre>
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns upload url by which you can download media file uploaded on Built.io Contentstack server.
     * You will get uploaded url after uploading media file on Built.io Contentstack server.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String url = assetObject.getUrl();
     * </pre>
     */
    public String getUrl() {
        return uploadUrl;
    }

    /**
     * Returns JSON representation of this {@link Asset} instance data.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  JSONObject json = assetObject.toJSON();
     * </pre>
     */
    public JSONObject toJSON() {
        return json;
    }


    /**
     * Get {@link Calendar} value of creation time of entry.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Calendar createdAt = assetObject.getCreateAt("key");
     * </pre>
     */
    public Calendar getCreateAt() {

        try {
            String value = json.optString("created_at");
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
     * String createdBy_uid = assetObject.getCreatedBy();
     * </pre>
     */
    public String getCreatedBy() {

        return json.optString("created_by");
    }

    /**
     * Get {@link Calendar} value of updating time of entry.
     *
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Calendar updatedAt = assetObject.getUpdateAt("key");
     * </pre>
     */
    public Calendar getUpdateAt() {

        try {
            String value = json.optString("updated_at");
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
     * String updatedBy_uid = assetObject.getUpdatedBy();
     * </pre>
     */
    public String getUpdatedBy() {

        return json.optString("updated_by");
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
            String value = json.optString("deleted_at");
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
     * String deletedBy_uid = assetObject.getDeletedBy();
     * </pre>
     */
    public String getDeletedBy() {
        return json.optString("deleted_by");
    }


    /**
     * Get tags.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String[] tags = assetObject.getURL();
     * </pre>
     */
    public String[] getTags() {
        return tagsArray;
    }

    /**
     * To set cache policy using {@link Query} instance.
     *
     * @param cachePolicy {@link CachePolicy} instance.
     *                    <p>
     *                    {@link Query} object, so you can chain this call.
     *
     *                    <br><br><b>Example :</b><br>
     *                    <pre class="prettyprint">
     *                         //'blt5d4sample2633b' is a dummy Stack API key
     *                         //'blt6d0240b5sample254090d' is dummy access token.
     *                         Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *                         Asset assetObject = stack.asset("bltsampleAsset");<br>
     *
     *                         assetObject.setCachePolicy(NETWORK_ELSE_CACHE);
     *                    </pre>
     */
    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicyForCall = cachePolicy;
    }

    /**
     * Fetch a particular asset using uid.
     *
     * @param callback {@link FetchResultCallback} instance for success and failure result.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre class="prettyprint">
     *                  Asset asset = stack.asset("blt5312f71416d6e2c8");
     *                  asset.fetch(new FetchResultCallback() {
     *                    &#64;Override
     *                    public void onCompletion(ResponseType responseType, Error error) {
     *                          if(error == null){
     *                            //Success Block.
     *                          }else {
     *                            //Fail Block.
     *                          }
     *                    }
     *                  });
     *                 </pre>
     */
    public void fetch(FetchResultCallback callback) {

        try {

            String URL = "/" + stackInstance.VERSION + "/assets/" + assetUid;
            ArrayMap<String, Object> headers = getHeader(headerGroup_local);
            if (headers.containsKey("environment")) {
                urlQueries.put("environment", headers.get("environment"));
            }

            String mainStringForMD5 = URL + new JSONObject().toString() + headers.toString();
            String md5Value = new CSAppUtils().getMD5FromString(mainStringForMD5.trim());
            File cacheFile = new File(CSAppConstants.cacheFolderName + File.separator + md5Value);

            switch (cachePolicyForCall) {

                case IGNORE_CACHE:

                case NETWORK_ONLY:
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), callback);
                    break;

                case CACHE_ONLY:
                    fetchFromCache(cacheFile, callback);
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
                            fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), callback);

                        } else {
                            setCacheModel(cacheFile, callback);
                        }

                    } else {
                        fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), callback);
                    }

                    break;

                case CACHE_THEN_NETWORK:
                    if (cacheFile.exists()) {
                        setCacheModel(cacheFile, callback);
                    }

                    // from network
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), callback);
                    break;

                case NETWORK_ELSE_CACHE:

                    if (CSAppConstants.isNetworkAvailable) {
                        fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), callback);
                    } else {
                        fetchFromCache(cacheFile, callback);
                    }

                    break;
            }

        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }

    private void fetchFromNetwork(String URL, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, FetchResultCallback callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, stackInstance, CSController.FETCHASSETS, URL, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.ASSET.toString(), false, CSAppConstants.RequestMethod.GET, callback);
        }
    }


    private void fetchFromCache(File cacheFile, FetchResultCallback callback) {
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

    //Asset modeling from cache.
    private void setCacheModel(File cacheFile, FetchResultCallback callback) {

        AssetModel model = new AssetModel(CSAppUtils.getJsonFromCacheFile(cacheFile), false, true);

        this.contentType = model.contentType;
        this.fileSize = model.fileSize;
        this.uploadUrl = model.uploadUrl;
        this.fileName = model.fileName;
        this.json = model.json;
        this.assetUid = model.uploadedUid;
        this.setTags(model.tags);

        model = null;

        if (callback != null) {
            callback.onRequestFinish(ResponseType.CACHE);
        }
    }

    /**
     * To set tags for this objects
     *
     * @param tags array of tag.
     * @return {@link Asset} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Application API key
     * Asset assetObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).asset("assetUid");
     * assetObject.setTags(new String[]{"tag1", "tag2"});
     * </pre>
     */
    protected Asset setTags(String[] tags) {
        tagsArray = tags;
        return this;
    }

    private HashMap<String, Object> getUrlParams(JSONObject urlQueriesJSON) {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (urlQueriesJSON != null && urlQueriesJSON.length() > 0) {
            Iterator<String> iter = urlQueriesJSON.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = urlQueriesJSON.opt(key);
                    hashMap.put(key, value);
                } catch (Exception e) {
                    CSAppUtils.showLog(TAG, "----------------setQueryJson" + e.toString());
                }
            }
            return hashMap;
        }
        return null;
    }

    private ArrayMap<String, Object> getHeader(ArrayMap<String, Object> localHeader) {
        ArrayMap<String, Object> mainHeader = headerGroup_app;
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
            return headerGroup_app;
        }
    }

    /**
     * This method adds key and value to an Entry.
     *
     * @param key   The key as string which needs to be added to an Asset
     * @param value The value as string which needs to be added to an Asset
     * @return {@link Asset}
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * final Asset asset = stack.asset("blt5312f71416d6e2c8");
     * asset.addParam("key", "some_value");
     *
     *  asset.fetch(new FetchResultCallback() {
     *    &#64;Override
     *    public void onCompletion(ResponseType responseType, Error error) {
     *          if(error == null){
     *            //Success Block.
     *          }else {
     *            //Fail Block.
     *          }
     *    }
     *  });
     * </pre>
     */
    public Asset addParam(String key, String value) {
        if (key != null && value != null) {
            try {
                urlQueries.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return this;
    }


    /**
     * Include the dimensions (height and width) of the image in the response.
     * Supported image types: JPG, GIF, PNG, WebP, BMP, TIFF, SVG, and PSD
     *
     * @return Asset
     */
    public Asset includeDimension() {
        try {
            urlQueries.put("include_dimension", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * Retrieve the published content of the fallback locale if an entry is not localized in specified locale
     *
     * @return {@link Asset} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     Stack stack = Contentstack.stack(context, "ApiKey", "deliveryToken", "environment");
     *     final Asset asset = stack.asset("asset_uid");
     *     asset.includeFallback();
     * </pre>
     */
    public Asset includeFallback() {
        try {
            urlQueries.put("include_fallback", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

}
