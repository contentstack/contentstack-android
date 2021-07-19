package com.contentstack.sdk;


import android.text.TextUtils;
import android.util.ArrayMap;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * AssetLibrary class to fetch all files details on Conentstack server.
 *
 * @author contentstack.com. Inc
 */
public class AssetLibrary implements INotifyClass {

    private final static String TAG = "AssetLibrary";
    private Stack stackInstance;
    private ArrayMap<String, Object> stackHeader;
    private ArrayMap<String, Object> localHeader;
    private JSONObject urlQueries;
    private FetchAssetsCallback assetsCallback;
    private int count;
    private static CachePolicy cachePolicyForCall = CachePolicy.IGNORE_CACHE;

    private long maxCachetimeForCall = 0; //local cache time interval
    private long defaultCacheTimeInterval = 0;

    /**
     * Sorting order enum for {@link AssetLibrary}.
     *
     * @author built.io, Inc
     */
    public enum ORDERBY {
        ASCENDING,
        DESCENDING
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
     *              AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *
     *              assetLibObject.setHeader("custom_header_key", "custom_header_value");
     *              </pre>
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
     *            <pre class="prettyprint">
     *            //'blt5d4sample2633b' is a dummy Application API key
     *            AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *
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
     * //'blt5d4sample2633b' is a dummy Application API key
     * AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *
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
            throwException("sort", CSAppConstants.ErrorMessage_QueryFilterException, e);
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
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'bltdtsample_accessToken767vv' is dummy access token.
     *    AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *
     *    assetLibObject.includeCount();
     * </pre>
     */
    public AssetLibrary includeCount() {
        try {
            urlQueries.put("include_count", "true");
        } catch (Exception e) {
            throwException("includeCount", CSAppConstants.ErrorMessage_QueryFilterException, e);
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
     *    //'blt5d4sample2633b' is a dummy Stack API key
     *    //'bltdtsample_accessToken767vv' is dummy access token.
     *    AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *
     *    assetLibObject.includeRelativeUrl();
     * </pre>
     */
    public AssetLibrary includeRelativeUrl() {
        try {
            urlQueries.put("relative_urls", "true");
        } catch (Exception e) {
            throwException("relative_urls", CSAppConstants.ErrorMessage_QueryFilterException, e);
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
     * @return {@link Query} object, so you can chain this call.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *      //'blt5d4sample2633b' is a dummy Stack API key
     *      //'blt6d0240b5sample254090d' is dummy access token.
     *      Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *      {@link AssetLibrary} assetLibObject = stack.assetLibrary();<br>
     *
     *      assetLibObject.setCachePolicy(NETWORK_ELSE_CACHE);
     * </pre>
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
     *                       <pre class="prettyprint">
     *                          //'blt5d4sample2633b' is a dummy Stack API key
     *                          //'bltdtsample_accessToken767vv' is dummy access token.
     *                          AssetLibrary assetLibObject = Contentstack.stack(context, "blt5d4sample2633b", "bltdtsample_accessToken767vv",  config).assetLibrary();
     *                         assetLibObject.fetchAll(new FetchAssetsCallback() {
     *                         public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
     *                            if (error == null) {
     *                               //Success Block.
     *                            } else {
     *                               //Error Block.
     *                            }
     *                         }
     *                        });
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
            String md5Value = new CSAppUtils().getMD5FromString(mainStringForMD5.trim());
            File cacheFile = new File(CSAppConstants.cacheFolderName + File.separator + md5Value);
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
                        if (maxCachetimeForCall > 0) {
                            needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) maxCachetimeForCall);
                        } else {
                            needToSendCall = new CSAppUtils().getResponseTimeFromCacheFile(cacheFile, (int) defaultCacheTimeInterval);
                        }
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

                    // from network
                    fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    break;

                case NETWORK_ELSE_CACHE:

                    if (CSAppConstants.isNetworkAvailable) {
                        fetchFromNetwork(URL, urlQueries, headers, cacheFile.getPath(), assetsCallback);
                    } else {
                        fetchFromCache(cacheFile, assetsCallback);
                    }

                    break;
            }

        } catch (Exception e) {
            CSAppUtils.showLog(TAG, "---------------------------------------" + TAG + "---fetchAll---||" + e.toString());
        }
    }

    private void fetchFromNetwork(String URL, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, FetchAssetsCallback callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);

            new CSBackgroundTask(this, stackInstance, CSController.FETCHALLASSETS, URL, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.ASSETLIBRARY.toString(), false, CSAppConstants.RequestMethod.GET, assetsCallback);
        }
    }

    //fetch from cache.
    private void fetchFromCache(File cacheFile, FetchAssetsCallback callback) {
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
    private void setCacheModel(File cacheFile, FetchAssetsCallback callback) {

        AssetsModel assetsModel = new AssetsModel(CSAppUtils.getJsonFromCacheFile(cacheFile), true);
        List<Object> objectList = assetsModel.objects;
        assetsModel = null;
        count = objectList.size();
        List<Asset> assets = new ArrayList<Asset>();
        if (objectList != null && objectList.size() > 0) {
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
                    CSAppUtils.showLog(TAG, "----------------setQueryJson" + e.toString());
                }
            }

            return hashMap;
        }

        return null;
    }

    private void throwException(String tag, String messageString, Exception e) {
        Error error = new Error();
        error.setErrorMessage(messageString);
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
     *     Stack stack = Contentstack.stack(context, "ApiKey", "deliveryToken",  environment_name);
     *     AssetLibrary assetLibObject = stack.assetLibrary();
     *     assetLibObject.includeFallback();
     * </pre>
     */
    public AssetLibrary includeFallback() {
        try {
            urlQueries.put("include_fallback", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

}
