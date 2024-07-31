package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Asset class to fetch files details on Contentstack server.
 *
 * @author contentstack.com
 */
public class Asset {

    private final static String TAG = Asset.class.getSimpleName();
    protected String assetUid = null;
    protected String contentType = null;
    protected String fileSize = null;
    protected String fileName = null;
    protected String uploadUrl = null;
    protected JSONObject json = null;
    protected String[] tagsArray = null;
    JSONObject urlQueries = new JSONObject();
    protected ArrayMap<String, Object> headerGroupApp;
    protected ArrayMap<String, Object> headerGroupLocal;
    protected Stack stackInstance;
    private CachePolicy cachePolicyForCall = CachePolicy.IGNORE_CACHE;
    private final long MAX_CACHE_TIME_FOR_CALL = 0;

    protected Asset() {
        this.headerGroupLocal = new ArrayMap<>();
        this.headerGroupApp = new ArrayMap<>();
    }

    protected Asset(String assetUid) {
        this.assetUid = assetUid;
        this.headerGroupLocal = new ArrayMap<>();
        this.headerGroupApp = new ArrayMap<>();
    }

    protected void setStackInstance(Stack stack) {
        this.stackInstance = stack;
        this.headerGroupApp = stack.localHeader;
    }

    /**
     * Creates new instance of {@link Asset} from valid {@link JSONObject}.
     * If JSON object is not appropriate then it will return null.
     *
     * @param jsonObject json object of particular file attached in the built object.<br>
     *                   <p>
     *                   {@link Asset} can be generate using of data filled {@link Entry}
     *                   and {@link #configure(JSONObject)}.<br>
     *                   <br><br><b>Example :</b><br>
     *                   <br>1. Single Attachment :-<br>
     *                   <pre
     *                   Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken",  config);
     *                   Asset assetObject = stack.asset("assetUid");<br>
     *                   assetObject.configure(entryObject.getJSONObject(attached_image_field_uid));</pre>
     *                   <br>2. Multiple Attachment :
     *                   <p>
     *                   <pre
     *                   JSONArray array = entryObject.getJSONArray(Attach_Image_Field_Uid);<br>
     *                   for (int i = 0; i < array.length(); i++) {<br>
     *                   Asset assetObject = stack.asset("assetUid");<br>
     *                   assetObject.configure(entryObject.getJSONObject(attached_image_field_uid));
     *                   }<br>
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
     * To set headers for Contentstack rest calls.
     * <br>
     * Scope is limited to this object only.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     *              <br><br><b>Example :</b><br>
     *              <pre
     *              Asset assetObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).asset("assetUid");
     *              assetObject.setHeader("custom_header_key", "custom_header_value");
     *              </pre>
     */
    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            removeHeader(key);
            headerGroupLocal.put(key, value);
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
     *            <pre
     *            Asset assetObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).asset("assetUid");
     *            assetObject.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (headerGroupLocal != null) {
            if (!TextUtils.isEmpty(key)) {
                headerGroupLocal.remove(key);
            }
        }
    }

    /**
     * To set uid of media file which is uploaded on Contentstack server.
     *
     * @param assetUid upload uid.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre
     *                 Asset assetObject = Contentstack.stack(context, "apiKey", "deliveryToken",   config).asset("assetUid");*
     *                 assetObject.setUid("upload_uid");
     *                 </pre>
     */
    protected void setUid(String assetUid) {
        if (!TextUtils.isEmpty(assetUid)) {
            this.assetUid = assetUid;
        }
    }

    /**
     * Returns media file upload uid. You will get uploaded uid after uploading media file on Contentstack server.
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
     * Returns upload url by which you can download media file uploaded on Contentstack server.
     * You will get uploaded url after uploading media file on Contentstack server.
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
            return CSUtil.parseDate(value, null);
        } catch (Exception e) {
            SDKUtil.showLog(TAG, e.getLocalizedMessage());
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
            return CSUtil.parseDate(value, null);
        } catch (Exception e) {
            SDKUtil.showLog(TAG, e.getLocalizedMessage());
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
            return CSUtil.parseDate(value, null);
        } catch (Exception e) {
            SDKUtil.showLog(TAG, e.getLocalizedMessage());
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
     *                    <pre
     *                    Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken",  "stag", false);
     *                    Asset assetObject = stack.asset("assetUid");<br>
     *                    assetObject.setCachePolicy(NETWORK_ELSE_CACHE);
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
     *                 <pre
     *                 Asset asset = stack.asset("assetUid");
     *                 asset.fetch(new FetchResultCallback() {
     *                 &#64;Override
     *                 public void onCompletion(ResponseType responseType, Error error) {
     *                 if(error == null){
     *                 //Success Block.
     *                 }else {
     *                 //Fail Block.
     *                 }
     *                 }
     *                 });
     *                 </pre>
     */
    public void fetch(FetchResultCallback callback) {
        try {
            String urlEndpoint = "/" + stackInstance.VERSION + "/assets/" + assetUid;
            ArrayMap<String, Object> headers = getHeader(headerGroupLocal);
            if (headers.containsKey("environment")) {
                urlQueries.put("environment", headers.get("environment"));
            }
            String mainStringForMD5 = urlEndpoint + new JSONObject().toString() + headers.toString();
            String shaValue = new SDKUtil().getSHAFromString(mainStringForMD5.trim());
            File cacheFile = new File(SDKConstant.cacheFolderName + File.separator + shaValue);

            switch (cachePolicyForCall) {
                case IGNORE_CACHE:
                case NETWORK_ONLY:
                    fetchFromNetwork(urlEndpoint, urlQueries, headers, cacheFile.getPath(), callback);
                    break;
                case CACHE_ONLY:
                    fetchFromCache(cacheFile, callback);
                    break;
                case CACHE_ELSE_NETWORK:
                    if (cacheFile.exists()) {
                        boolean needToSendCall = false;
                        needToSendCall = new SDKUtil().getResponseTimeFromCacheFile(cacheFile, (int) MAX_CACHE_TIME_FOR_CALL);
                        if (needToSendCall) {
                            fetchFromNetwork(urlEndpoint, urlQueries, headers, cacheFile.getPath(), callback);
                        } else {
                            setCacheModel(cacheFile, callback);
                        }
                    } else {
                        fetchFromNetwork(urlEndpoint, urlQueries, headers, cacheFile.getPath(), callback);
                    }
                    break;
                case CACHE_THEN_NETWORK:
                    if (cacheFile.exists()) {
                        setCacheModel(cacheFile, callback);
                    }
                    fetchFromNetwork(urlEndpoint, urlQueries, headers, cacheFile.getPath(), callback);
                    break;

                case NETWORK_ELSE_CACHE:
                    if (SDKConstant.IS_NETWORK_AVAILABLE) {
                        fetchFromNetwork(urlEndpoint, urlQueries, headers, cacheFile.getPath(), callback);
                    } else {
                        fetchFromCache(cacheFile, callback);
                    }
                    break;
            }

        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(SDKConstant.PLEASE_PROVIDE_VALID_JSON);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }

    private void fetchFromNetwork(String URL, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, FetchResultCallback callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, stackInstance, SDKController.GET_ASSETS, URL, headers, urlParams, new JSONObject(), cacheFilePath, SDKConstant.callController.ASSET.toString(), false, SDKConstant.RequestMethod.GET, callback);
        }
    }


    private void fetchFromCache(File cacheFile, FetchResultCallback callback) {
        Error error = null;
        if (cacheFile.exists()) {
            boolean needToSendCall = false;
            needToSendCall = new SDKUtil().getResponseTimeFromCacheFile(cacheFile, (int) MAX_CACHE_TIME_FOR_CALL);
            if (needToSendCall) {
                error = new Error();
                error.setErrorMessage(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE);
            } else {
                setCacheModel(cacheFile, callback);
            }
        } else {
            error = new Error();
            error.setErrorMessage(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE);
        }

        if (callback != null && error != null) {
            callback.onRequestFail(ResponseType.CACHE, error);
        }
    }

    //Asset modeling from cache.
    private void setCacheModel(File cacheFile, FetchResultCallback callback) {
        AssetModel model = new AssetModel(SDKUtil.getJsonFromCacheFile(cacheFile), false, true);
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
     * Asset assetObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).asset("assetUid");
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
            Iterator<String> iterator = urlQueriesJSON.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    Object value = urlQueriesJSON.opt(key);
                    hashMap.put(key, value);
                } catch (Exception e) {
                    SDKUtil.showLog(TAG, "setQueryJson" + e.getLocalizedMessage());
                }
            }
            return hashMap;
        }
        return null;
    }

    private ArrayMap<String, Object> getHeader(ArrayMap<String, Object> localHeader) {
        ArrayMap<String, Object> mainHeader = headerGroupApp;
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
            return headerGroupApp;
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
     * final Asset asset = stack.asset("assetUid");
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
                Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
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
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return this;
    }


    /**
     * Retrieve the published content of the fallback locale if an entry is not localized in specified locale
     *
     * @return {@link Asset} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "environment");
     *     final Asset asset = stack.asset("asset_uid");
     *     asset.includeFallback();
     * </pre>
     */
    public Asset includeFallback() {
        try {
            urlQueries.put("include_fallback", true);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return this;
    }

}
