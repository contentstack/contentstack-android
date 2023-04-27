package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AssetLibrary class to fetch all files details on Conentstack server.
 *
 * @author contentstack.com. Inc
 */
public class AssetLibrary implements INotifyClass {

    private final static String TAG = AssetLibrary.class.getSimpleName();
    private Stack stackInstance;
    private ArrayMap<String, Object> stackHeader;
    private ArrayMap<String, Object> localHeader;
    private JSONObject urlQueries;
    private FetchAssetsCallback assetsCallback;
    private int count;
    private static CachePolicy cachePolicyForCall = CachePolicy.IGNORE_CACHE;
    private long maxCacheTimeForCall = 0;
    private final long defaultCacheTimeInterval = 0;

    /**
     * Sorting order enum for {@link AssetLibrary}.
     *
     * @author Contentstack
     */
    public enum ORDERBY {
        ASCENDING, DESCENDING
    }

    protected AssetLibrary() {
        this.localHeader = new ArrayMap<String, Object>();
        this.urlQueries = new JSONObject();
    }

    protected void setStackInstance(Stack stack) {
        this.stackInstance = stack;
        this.stackHeader = stack.localHeader;
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
     *              <pre class="prettyprint">
     *                                                                                                                                                                                                                  AssetLibrary assetLibObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).assetLibrary();
     *                                                                                                                                                                                                                  assetLibObject.setHeader("custom_header_key", "custom_header_value");
     *                                                                                                                                                                                                                  </pre>
     */
    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            localHeader.put(key, value);
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
     *            AssetLibrary assetLibObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).assetLibrary();
     *            assetLibObject.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    /**
     * Sort assets by fieldUid.
     *
     * @param key     field Uid.
     * @param orderby {@link ORDERBY} value for ascending or descending.
     * @return {@link AssetLibrary} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * AssetLibrary assetLibObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).assetLibrary();
     * assetLibObject.sort("fieldUid", AssetLibrary.ORDERBY.ASCENDING);
     * </pre>
     */
    public AssetLibrary sort(String key, ORDERBY orderby) {
        try {
            switch (orderby) {
                case ASCENDING:
                    urlQueries.put("asc", key);
                    break;

                case DESCENDING:
                    urlQueries.put("desc", key);
                    break;
            }
        } catch (Exception e) {
            throwException("sort", SDKConstant.PROVIDE_VALID_PARAMS, e);
        }

        return this;
    }

    /**
     * Retrieve count and data of assets in result.
     *
     * @return {@link AssetLibrary} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    AssetLibrary assetLibObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).assetLibrary();
     *    assetLibObject.includeCount();
     * </pre>
     */
    public AssetLibrary includeCount() {
        try {
            urlQueries.put("include_count", "true");
        } catch (Exception e) {
            throwException("includeCount", SDKConstant.PROVIDE_VALID_PARAMS, e);
        }
        return this;
    }

    /**
     * Retrieve relative urls objects in result.
     *
     * @return {@link AssetLibrary} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *    AssetLibrary assetLibObject = Contentstack.stack(context, "deliveryToken", "deliveryToken",  config).assetLibrary();
     *    assetLibObject.includeRelativeUrl();
     * </pre>
     */
    public AssetLibrary includeRelativeUrl() {
        try {
            urlQueries.put("relative_urls", "true");
        } catch (Exception e) {
            throwException("relative_urls", SDKConstant.PROVIDE_VALID_PARAMS, e);
        }
        return this;
    }

    /**
     * Get a count of assets in success callback of {@link FetchAssetsCallback}.
     */
    public int getCount() {
        return count;
    }


    /**
     * To set cache policy using {@link Query} instance.
     *
     * @param cachePolicy {@link CachePolicy} instance.
     *
     *                    <br><br><b>Example :</b><br>
     *                    <pre
     *                    Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag", false);
     *                    {@link AssetLibrary} assetLibObject = stack.assetLibrary();<br>
     *                    assetLibObject.setCachePolicy(NETWORK_ELSE_CACHE);
     *                    </pre>
     * @return {@link Query} object, so you can chain this call.
     */
    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicyForCall = cachePolicy;
    }

    /**
     * Fetch a all asset.
     *
     * @param assetsCallback {@link FetchAssetsCallback} instance for success and failure result.
     *
     *                       <br><br><b>Example :</b><br>
     *                       <pre
     *                       AssetLibrary assetLibObject = Contentstack.stack(context, "apiKey", "deliveryToken",  config).assetLibrary();
     *                       assetLibObject.fetchAll(new FetchAssetsCallback() {
     *                       public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
     *                       if (error == null) {
     *                       //Success Block.
     *                       } else {
     *                       //Error Block.
     *                       }
     *                       }
     *                       });
     *                       </pre>
     */
    public void fetchAll(FetchAssetsCallback assetsCallback) {
        try {
            this.assetsCallback = assetsCallback;
            String URL = "/" + stackInstance.VERSION + "/assets";
            ArrayMap<String, Object> headers = getHeader(localHeader);
            if (headers.containsKey("environment")) {
                urlQueries.put("environment", headers.get("environment"));
            }
            String mainStringForMD5 = URL + new JSONObject().toString() + headers.toString();
            String md5Value = new SDKUtil().getMD5FromString(mainStringForMD5.trim());
            File cacheFile = new File(SDKConstant.cacheFolderName + File.separator + md5Value);
            switch (cachePolicyForCall) {
                case IGNORE_CACHE:
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    break;
                case NETWORK_ONLY:
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    break;
                case CACHE_ONLY:
                    fetchFromCache(cacheFile, assetsCallback);
                    break;
                case CACHE_ELSE_NETWORK:
                    if (cacheFile.exists()) {
                        boolean needToSendCall = false;
                        needToSendCall = new SDKUtil().getResponseTimeFromCacheFile(cacheFile, (int) maxCacheTimeForCall);
                        if (needToSendCall) {
                            fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                        } else {
                            setCacheModel(cacheFile, assetsCallback);
                        }
                    } else {
                        fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    }
                    break;
                case CACHE_THEN_NETWORK:
                    if (cacheFile.exists()) {
                        setCacheModel(cacheFile, assetsCallback);
                    }
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    break;
                case NETWORK_ELSE_CACHE:
                    if (SDKConstant.IS_NETWORK_AVAILABLE) {
                        fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    } else {
                        fetchFromCache(cacheFile, assetsCallback);
                    }
                    break;
            }

        } catch (Exception e) {
            SDKUtil.showLog(TAG, e.toString());
        }
    }

    private void fetchFromNetwork(String URL, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, FetchAssetsCallback callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, stackInstance, SDKController.GET_ALL_ASSETS, URL, headers, urlParams, new JSONObject(), cacheFilePath, SDKConstant.callController.ASSET_LIBRARY.toString(), false, SDKConstant.RequestMethod.GET, assetsCallback);
        }
    }

    private void fetchFromCache(File cacheFile, FetchAssetsCallback callback) {
        Error error = null;
        if (cacheFile.exists()) {
            boolean needToSendCall = false;
            needToSendCall = new SDKUtil().getResponseTimeFromCacheFile(cacheFile, (int) maxCacheTimeForCall);
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
    private void setCacheModel(File cacheFile, FetchAssetsCallback callback) {

        AssetsModel assetsModel = new AssetsModel(SDKUtil.getJsonFromCacheFile(cacheFile), true);
        List<Object> objectList = assetsModel.objects;
        assetsModel = null;
        count = objectList.size();
        List<Asset> assets = new ArrayList<Asset>();
        if (objectList.size() > 0) {
            for (Object object : objectList) {
                AssetModel model = (AssetModel) object;
                Asset asset = stackInstance.asset();

                asset.contentType = model.contentType;
                asset.fileSize = model.fileSize;
                asset.uploadUrl = model.uploadUrl;
                asset.fileName = model.fileName;
                asset.json = model.json;
                asset.assetUid = model.uploadedUid;
                asset.setTags(model.tags);
                model = null;
                assets.add(asset);
            }
        }

        if (assetsCallback != null) {
            assetsCallback.onRequestFinish(ResponseType.CACHE, assets);
        }
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
                    SDKUtil.showLog(TAG, e.toString());
                }
            }

            return hashMap;
        }

        return null;
    }

    private void throwException(String tag, String messageString, Exception e) {
        Error error = new Error();
        if (messageString != null) {
            error.setErrorMessage(messageString);
            Log.d(tag, messageString);
        }
        if (e != null) {
            error.setErrorMessage(e.getLocalizedMessage());
            assert messageString != null;
            Log.d(tag, messageString);
        }
    }

    private ArrayMap<String, Object> getHeader(ArrayMap<String, Object> localHeader) {
        ArrayMap<String, Object> mainHeader = stackHeader;
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
            return stackHeader;
        }
    }

    @Override
    public void getResult(Object object, String controller) {
    }

    @Override
    public void getResultObject(List<Object> objects, JSONObject jsonObject, boolean isSingleEntry) {

        if (jsonObject != null && jsonObject.has("count")) {
            count = jsonObject.optInt("count");
        }
        List<Asset> assets = new ArrayList<Asset>();
        if (objects != null && objects.size() > 0) {
            for (Object object : objects) {
                AssetModel model = (AssetModel) object;
                Asset asset = stackInstance.asset();

                asset.contentType = model.contentType;
                asset.fileSize = model.fileSize;
                asset.uploadUrl = model.uploadUrl;
                asset.fileName = model.fileName;
                asset.json = model.json;
                asset.assetUid = model.uploadedUid;
                asset.setTags(model.tags);
                model = null;
                assets.add(asset);
            }
        }

        if (assetsCallback != null) {
            assetsCallback.onRequestFinish(ResponseType.NETWORK, assets);
        }
    }


    /**
     * Retrieve the published content of the fallback locale if an entry is not localized in specified locale
     *
     * @return {@link AssetLibrary} object, so you can chain this call.
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *     Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken",  environment_name);
     *     AssetLibrary assetLibObject = stack.assetLibrary();
     *     assetLibObject.includeFallback();
     * </pre>
     */
    public AssetLibrary includeFallback() {
        try {
            urlQueries.put("include_fallback", true);
        } catch (JSONException e) {
            Log.d("AssetLibrary", Objects.requireNonNull(e.getLocalizedMessage()));
            throwException("AssetLibrary", null, e);
        }
        return this;
    }


    /**
     * Include metadata asset response.
     *
     * @return the asset library
     */
    public AssetLibrary includeMetadata() {
        try {
            urlQueries.put("include_metadata", true);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return this;
    }

}
