package com.contentstack.sdk;

import android.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * To fetch stack level information of your application from Contentstack server.
 *
 *  Created by Shailesh Mishra.
 *  Contentstack pvt Ltd
 */
public class Stack implements INotifyClass {

    private static final String TAG = "Stack";
    private String stackApiKey = null;
    protected ArrayMap<String, Object> localHeader = null;
    private String imageTransformationUrl;
    private LinkedHashMap<String, Object> imageParams = new LinkedHashMap<>();
    protected String URLSCHEMA     = "https://";
    protected String URL           = "cdn.contentstack.io";
    protected String VERSION       = "v3";
    protected String SYNC_KEY      = "sync";
    protected Config config;
    protected ArrayMap<String, Object> headerGroup_app;
    private JSONObject syncParams = null;
    protected String skip = null;
    protected String limit = null;
    protected String sync_token = null;
    protected String pagination_token = null;
    protected String contentType;
    protected String localeCode ;
    protected Types types;
    protected String start_from_date;
    private  SyncResultCallBack syncCallBack;


    public enum Types
    {
        entry_published,
        entry_unpublished ,
        entry_deleted ,
        asset_published ,
        asset_unpublished ,
        asset_deleted ,
        content_type_deleted
    }



    private Stack(){}


    protected Stack(String stackApiKey) {
        this.stackApiKey = stackApiKey;
        this.localHeader = new ArrayMap<String, Object>();

    }

    protected void setConfig(Config config){
        this.config        = config;
        URLSCHEMA          = config.URLSCHEMA;
        URL                = config.URL;
        VERSION            = config.VERSION;

        if(!TextUtils.isEmpty(config.environment)){
            setHeader("environment", config.environment);
        }

    }

    /**
     * Represents a {@link ContentType}.<br>
     * Create {@link ContentType} instance.
     *
     * @param contentTypeName
     *                  contentType name.
     *
     * @return
     *         {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *
     * ContentType contentType = stack.contentType("blog");
     * </pre>
     */
    public ContentType contentType(String contentTypeName){
        ContentType contentType = new ContentType(contentTypeName);
        contentType.setStackInstance(this);

        return contentType;
    }

    /**
     * Create {@link Asset} instance.
     *
     *
     * @return
     *         {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *
     * Asset asset = stack.asset("assetUid");
     * </pre>
     */
    public Asset asset(String uid){
        Asset asset = new Asset(uid);
        asset.setStackInstance(this);

        return asset;
    }

    /**
     * Create {@link Asset} instance.
     *
     *
     * @return
     *         {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *
     * Asset asset = stack.asset();
     * </pre>
     */
    protected Asset asset(){
        Asset asset = new Asset();
        asset.setStackInstance(this);

        return asset;
    }

    /**
     * Create {@link AssetLibrary} instance.
     *
     *
     * @return
     *         {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);
     *
     * AssetLibrary assetLib = stack.assetLibrary();
     * </pre>
     */
    public AssetLibrary assetLibrary(){
        AssetLibrary library = new AssetLibrary();
        library.setStackInstance(this);

        return library;
    }

    /**
     * Get stack application key
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String stackApiKey = stack.getApplicationKey();
     * </pre>
     * </p>
     */
    public String getApplicationKey(){ return stackApiKey;}

    /**
     * Get stack access token
     *
     * <p>
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * String accessToken = stack.getAccessToken();
     * </pre>
     * </p>
     */
    public String getAccessToken(){ return localHeader != null ? (String)localHeader.get("access_token") : null;}

    /**
     * Remove header key.
     *
     * @param key
     *              custom_header_key
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);<br>
     *  stack.removeHeader("custom_header_key");
     * </pre>
     */
    public void removeHeader(String key){
        if(!TextUtils.isEmpty(key)){
            localHeader.remove(key);
        }
    }

    /**
     * To set headers for Built.io Contentstack rest calls.
     * <br>
     * Scope is limited to this object and followed classes.
     * @param key
     * 				header name.
     * @param value
     * 				header value against given header name.
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *  //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);<br>
     *  stack.setHeader("custom_key", "custom_value");
     *  </pre>
     */
    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            localHeader.put(key, value);
        }
    }



    /**
     * @param image_url
     * on which we want to manipulate.
     * @param parameters
     * It is an second parameter in which we want to place different manipulation key and value in array form
     * @return String
     *
     * ImageTransform function is define for image manipulation with different
     * parameters in second parameter in array form
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *  //'blt5d4sample2633b' is a dummy Stack API key
     *  //'blt6d0240b5sample254090d' is dummy access token.
     *  Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag", false);<br>
     *  // resize the image by specifying width and height
     *  LinkedHashMap imageParams = new LinkedHashMap();
     *  imageParams.put("width", 100);
     *  imageParams.put("height",100);
     *  imageUrl = Stack.ImageTransform(image_url, parameters);
     *  stack.ImageTransform(image_url, parameters);
     *
     *
     *
     *  </pre>
     *
     */
    public String ImageTransform(String image_url, LinkedHashMap<String, Object> parameters) {
        imageTransformationUrl = image_url;
        imageParams = parameters;
        return getImageUrl();
    }




    private String getImageUrl() {

        if (imageParams == null || imageParams.size() == 0) {
            return imageTransformationUrl;
        }

        for(Map.Entry<String,Object> param:imageParams.entrySet()){
            try {

                String paramKey = param.getKey();
                String paramValue = param.getValue().toString();

                final String encodedKey = URLEncoder.encode(paramKey, "UTF-8");
                final String encodedValue = URLEncoder.encode(paramValue, "UTF-8");
                if (!imageTransformationUrl.contains("?")) {
                    imageTransformationUrl += "?" + encodedKey + "=" + encodedValue;
                } else {
                    imageTransformationUrl += "&" + encodedKey + "=" + encodedValue;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return imageTransformationUrl;
    }




    /**
     * The Initial Sync request performs a complete sync of your app data.
     * It returns all the published entries and assets of the specified stack in response.
     * The response also contains a sync token, which you need to store,
     * since this token is used to get subsequent delta updates later.
     *
     *<br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *
     *  stack.syncInit(new SyncResultCallBack() {
     *            @Override
     *             public void onCompletion(SyncStack syncStack, Error error) {
     *
     *                 if (error == null) {
     *                     // implementation
     *                 }
     *
     *             }});
     *
     * </pre>
     */

    public void syncInit(SyncResultCallBack syncCallBack){

        if (syncParams == null){
            syncParams = new JSONObject();
        }
        try {
            syncParams.put("init", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);

    }


    /**
     * @param from_date from @{@link Date}
     * @param @{@link SyncResultCallBack} returns syncCallBack
     *
     * You can also initialize sync with entries that satisfy multiple parameters.
     * To do this, use syncWith and specify the parameters.
     * However, if you do this, the subsequent syncs will only include the entries of the specified parameters
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *
     *   final Date start_date = sdf.parse("2018-10-07");
     *   stack.syncWithDate(start_date, new SyncResultCallBack() {
     *         @Override
     *         public void onCompletion(SyncStack syncStack, Error error) {
     *
     *             }
     *          });
     *
     *  </pre>
     */
    public void syncWithDate(Date from_date, SyncResultCallBack syncCallBack){
        start_from_date = convertUTCToISO(from_date);
        if (syncParams == null){
            syncParams = new JSONObject();
        }

        try {
            syncParams.put("init", true);
            syncParams.put("start_from", start_from_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.requestSync(syncCallBack);
    }




    private String convertUTCToISO(Date date) {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(tz);
        return dateFormat.format(date);
    }



    /**
     *
     * If the result of the initial sync (or subsequent sync) contains more than 100 records,
     * the response would be paginated. It provides pagination token in the response. However,
     * you don’t have to use the pagination token manually to get the next batch;
     * the SDK does that automatically until the sync is complete.
     *
     * @param pagination_token this is the parameter need to pass as pagination_token.
     * @param @{@link SyncResultCallBack} returns syncCallBack.
     *
     * Pagination token can be used in case you want to fetch only selected batches.
     * It is especially useful if the sync process is interrupted midway (due to network issues, etc.).
     * In such cases, this token can be used to restart the sync process from where it was interrupted.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *
     * String pagination_token = "blt4738747389474";
     * stack.syncWithPaginationToken(pagination_token, new SyncResultCallBack() {
     *    @Override
     *    public void onCompletion(SyncStack syncStack, Error error) {
     *
     *    }
     * });
     *
     * </pre>
     */
    public void syncWithPaginationToken(String pagination_token, SyncResultCallBack syncCallBack){
        this.pagination_token = pagination_token;
        if (syncParams == null){
            syncParams = new JSONObject();
        }

        try {
            syncParams.put("init", true);
            syncParams.put("pagination_token", pagination_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);
    }




    /**
     * @param sync_token @{@link String}
     * @param @{@link SyncResultCallBack} returns syncCallBack
     *
     * You can use the sync token (that you receive after initial sync) to get the updated content next time.
     * The sync token fetches only the content that was added after your last sync,
     * and the details of the content that was deleted or updated.
     *
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *
     *   String sync_token = "blt28937206743728463";
     *   stack.syncWithSyncToken(sync_token, new SyncResultCallBack() {
     *    @Override
     *    public void onCompletion(SyncStack syncStack,Error error) {
     *
     *    }
     * });
     *
     * </pre>
     */
    public void syncWithSyncToken(String sync_token, SyncResultCallBack syncCallBack){

        this.sync_token = sync_token;
        if (syncParams == null){
            syncParams = new JSONObject();
        }
        try {
            syncParams.put("init", true);
            syncParams.put("sync_token", sync_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);
    }





    /**
     *
     * @param content_type {@link String}
     * @param @{@link SyncResultCallBack} returns syncCallBack
     *
     * You can also initialize sync with entries of only specific content types.
     * To do this, use syncWithContentType and specify the content type UID as its value.
     * However, if you do this, the subsequent syncs will only include the entries of the specified content types.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *
     * stack.syncWithContentType(Stack.Types.type, new SyncResultCallBack() {
     *    @Override
     *    public void onCompletion(SyncStack syncStack,Error error) {
     *
     *    }
     * });
     *
     *  </pre>
     *
     */
    public void syncWithContentType(String content_type, SyncResultCallBack syncCallBack){

        this.contentType = content_type;
        if (syncParams == null){
            syncParams = new JSONObject();
        }
        try {
            syncParams.put("init", true);
            syncParams.put("content_type_uid", contentType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);
    }





    /**
     * @param language @{@link Language}
     * @param @{@link SyncResultCallBack} returns syncCallBack
     *
     * You can also initialize sync with entries of only specific locales.
     * To do this, use syncWithLocale and specify the locale code as its value.
     * However, if you do this, the subsequent syncs will only include the entries of the specified locales.
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *
     * stackInstance.syncWithLocale(Language, new SyncResultCallBack() {
     *    @Override
     *    public void onCompletion(SyncStack syncStack,Error error) {
     *
     *    }
     * });
     *
     * </pre>
     */
    public void syncWithLocale(Language language, SyncResultCallBack syncCallBack) {
        this.localeCode = getLanguageCode(language);

        if (syncParams == null) {
            syncParams = new JSONObject();
        }
        try {
            syncParams.put("init", true);
            syncParams.put("locale", localeCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        this.requestSync(syncCallBack);
    }



    private String getLanguageCode(Language language){

        String localeCode = null;
        if  (language != null) {

            Language languageName = Language.valueOf(language.name());
            int localeValue = languageName.ordinal();
            LanguageCode[] languageCodeValues = LanguageCode.values();
            localeCode = languageCodeValues[localeValue].name();
            localeCode = localeCode.replace("_", "-");
        }

        return localeCode;
    }





    /**
     *
     * @param type @{@link Types} first parameter accepts types for following
     *
     *         entry_published,
     *         entry_published,
     *         entry_unpublished ,
     *         entry_deleted ,
     *         asset_published ,
     *         asset_unpublished ,
     *         asset_deleted ,
     *         content_type_deleted
     *
     * @param @{@link SyncResultCallBack} second parameter takes SyncResultCallBack
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *
     *   stackInstance.syncWithLocale(Types.entry_published, new SyncResultCallBack() {
     *        @Override
     *      public void onCompletion(SyncStack syncStack,Error error) {
     *
     *        }
     *      });
     *
     *  </pre>
     */
    public void syncWithType(Types type, SyncResultCallBack syncCallBack){
        this.types = type;
        if (syncParams == null){ syncParams = new JSONObject(); }

        try {
            syncParams.put("init", true);
            syncParams.put("type", types);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);
    }



    /**
     *
     * @param from_date @{@link Date} first parameter take date
     * @param contentType {@link String} second parameter takes content_type
     * @param type @{@link Types} third parameter takes Types of content
     * @param language @{@link Language} four parameters takes Language
     * @param syncCallBack @{@link SyncResultCallBack} fifth parameter is SyncResultCallback
     *
     * You can also initialize sync with entries that satisfy multiple parameters.
     * To do this, use syncWith and specify the parameters.
     * However, if you do this, the subsequent syncs will only include the entries of the specified parameters
     *
     *  <br><br><b>Example :</b><br>
     *  <pre class="prettyprint">
     *
     *  stackInstance.syncWith(date,"content_type", Stack.Types.type, Language, new SyncResultCallBack() {
     *    @Override
     *    public void onCompletion(SyncStack syncStack,Error error) {
     *
     *    }
     *  });
     *
     *
     * </pre>
     */
    public void sync(Date from_date, String contentType, Types type, Language language, SyncResultCallBack syncCallBack){
        start_from_date = convertUTCToISO(from_date);
        this.contentType = contentType;
        this.types = type;
        this.localeCode = getLanguageCode(language);

        if (syncParams == null){
            syncParams = new JSONObject();
        }
        try {
            syncParams.put("init", true);
            syncParams.put("start_from",   this.start_from_date);
            syncParams.put("content_type_uid", this.contentType);
            syncParams.put("type", types);
            syncParams.put("locale", this.localeCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.requestSync(syncCallBack);
    }



    private void requestSync(final SyncResultCallBack callback){

        try {

            String URL = "/" + this.VERSION + "/stacks/" + SYNC_KEY;
            ArrayMap<String, Object> headers = getHeader(localHeader);

            JSONObject urlQueries = new JSONObject();
            if (headers.containsKey("environment")) {
                syncParams.put("environment", headers.get("environment"));
            }
            //syncParams.put("web_ui_api_key", "607a456d7f3afc20cd9fcb1f");
            syncParams.put("sync_cdn_api_key", "607a456d7f3afc20cd9fcb1f");
            urlQueries = syncParams;

            fetchFromNetwork(URL, urlQueries, headers, null, new SyncResultCallBack() {
                @Override
                public void onCompletion(SyncStack syncStack, Error error) {

                    if (error == null){
                        String paginationToken = syncStack.getPaginationToken();
                        if (paginationToken != null){
                            syncWithPaginationToken(paginationToken, callback);
                        }
                    }

                    callback.onCompletion(syncStack, error);
                }
            });


        }catch (Exception e){

            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }

    }



    private void fetchFromNetwork(String urlString, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, SyncResultCallBack callback) {

        if(callback != null) {

            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, this, CSController.FETCHSYNC, urlString, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.SYNC.toString(), false, CSAppConstants.RequestMethod.GET, callback);
        }
    }


    private HashMap<String, Object> getUrlParams(JSONObject urlQueriesJSON) {

        HashMap<String, Object> hashMap = new HashMap<>();

        if(urlQueriesJSON != null && urlQueriesJSON.length() > 0){
            Iterator<String> iter = urlQueriesJSON.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                try {
                    Object value = urlQueriesJSON.opt(key);
                    hashMap.put(key, value);
                } catch (Exception e) {
                    CSAppUtils.showLog(TAG, "------setQueryJson"+e.toString());
                }
            }

            return hashMap;
        }

        return null;
    }



    private ArrayMap<String,Object> getHeader(ArrayMap<String, Object> localHeader) {

        ArrayMap<String, Object> mainHeader = headerGroup_app;
        ArrayMap<String, Object> classHeaders = new ArrayMap<>();

        if(localHeader != null && localHeader.size() > 0){
            if(mainHeader != null && mainHeader.size() > 0) {
                for (Map.Entry<String, Object> entry : localHeader.entrySet()) {
                    String key = entry.getKey();
                    classHeaders.put(key, entry.getValue());
                }

                for (Map.Entry<String, Object> entry : mainHeader.entrySet()) {
                    String key = entry.getKey();
                    if(!classHeaders.containsKey(key)) {
                        classHeaders.put(key, entry.getValue());
                    }
                }

                return classHeaders;

            }else{
                return localHeader;
            }

        }else{
            return headerGroup_app;
        }

    }



    @Override
    public void getResult(Object object, String controller) { }


    @Override
    public void getResultObject(List<Object> object, JSONObject jsonObject, boolean isSingleEntry) {
        SyncStack syncStackObject = new SyncStack();
        syncStackObject.setJSON(jsonObject);
        if (syncCallBack != null) {
            syncCallBack.onRequestFinish(syncStackObject);
        }
    }


}
