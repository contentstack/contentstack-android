package com.contentstack.sdk;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.contentstack.sdk.utilities.CSAppConstants;
import com.contentstack.sdk.utilities.CSAppUtils;
import com.contentstack.sdk.utilities.CSController;

import org.jetbrains.annotations.NotNull;
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
import java.util.Objects;
import java.util.TimeZone;

/**
 * To fetch stack level information of your application from Contentstack server.
 * <p>
 * Created by Shailesh Mishra.
 * Contentstack pvt Ltd
 */
public class Stack implements INotifyClass {

    private static final String TAG = "Stack";
    private String stackApiKey = null;
    protected ArrayMap<String, Object> localHeader = null;
    private String imageTransformationUrl;
    private LinkedHashMap<String, Object> imageParams = new LinkedHashMap<>();
    protected String PROTOCOL = "https://";
    protected String URL = "cdn.contentstack.io";
    protected String VERSION = "v3";
    protected String SYNC_KEY = "sync";
    protected Config config;
    protected ArrayMap<String, Object> headerGroup_app;
    private JSONObject syncParams = null;
    protected String skip = null;
    protected String limit = null;
    protected String localeCode;
    private SyncResultCallBack syncCallBack;


    private Stack() throws IllegalAccessException {
        throw new IllegalAccessException("Can not initialise using constructor");
    }

    protected Stack(String stackApiKey) {
        this.stackApiKey = stackApiKey;
        this.localHeader = new ArrayMap<String, Object>();

    }

    protected void setConfig(Config config) {
        this.config = config;
        PROTOCOL = config.PROTOCOL;
        URL = config.URL;
        VERSION = config.VERSION;

        if (!TextUtils.isEmpty(config.environment)) {
            setHeader("environment", config.environment);
        }

        if (!config.region.name().isEmpty()) {
            String region = config.region.name().toLowerCase();
            if (!region.equalsIgnoreCase("us")) {
                if (URL.equalsIgnoreCase("cdn.contentstack.io")) {
                    URL = "cdn.contentstack.com";
                }
                if (region.equalsIgnoreCase("azure_na")) {
                    URL = "azure-na-cdn.contentstack.com";
                } else if (region.equalsIgnoreCase("azure_eu")) {
                    URL = "azure-eu-cdn.contentstack.com";
                } else {
                    URL = region + "-" + URL;
                }
            }
        }

    }

    /**
     * @param syncCallBack returns callback for sync result.
     *                     <p>
     *                     <p>
     *                     The Sync request performs a complete sync of your app data.
     *                     It returns all the published entries and assets of the specified stack in response.
     *                     The response also contains a sync token, which you need to store,
     *                     since this token is used to get subsequent delta updates later.
     *                     </pre>
     */

    public void sync(SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        this.requestSync(syncCallBack);

    }

    /**
     * Represents a {@link ContentType}.<br>
     * Create {@link ContentType} instance.
     *
     * @param contentTypeName contentType name.
     * @return {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");
     *
     * ContentType contentType = stack.contentType("blog");
     * </pre>
     */
    public ContentType contentType(String contentTypeName) {
        ContentType contentType = new ContentType(contentTypeName);
        contentType.setStackInstance(this);

        return contentType;
    }


    /**
     * Create {@link Asset} instance.
     *
     * @return {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");
     * Asset asset = stack.asset("assetUid");
     * </pre>
     */
    public Asset asset(String uid) {
        Asset asset = new Asset(uid);
        asset.setStackInstance(this);

        return asset;
    }

    /**
     * Create {@link Asset} instance.
     *
     * @return {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");
     * Asset asset = stack.asset();
     * </pre>
     */
    protected Asset asset() {
        Asset asset = new Asset();
        asset.setStackInstance(this);

        return asset;
    }

    /**
     * Create {@link AssetLibrary} instance.
     *
     * @return {@link ContentType} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");
     * AssetLibrary assetLib = stack.assetLibrary();
     * </pre>
     */
    public AssetLibrary assetLibrary() {
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
    public String getApplicationKey() {
        return stackApiKey;
    }

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
    public String getAccessToken() {
        return localHeader != null ? (String) localHeader.get("access_token") : null;
    }

    /**
     * Remove header key.
     *
     * @param key custom_header_key
     *
     *            <br><br><b>Example :</b><br>
     *            <pre
     *            Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");<br>
     *            stack.removeHeader("custom_header_key");
     *            </pre>
     */
    public void removeHeader(String key) {
        if (!TextUtils.isEmpty(key)) {
            localHeader.remove(key);
        }
    }

    /**
     * To set headers for Contentstack rest calls.
     * <br>
     * Scope is limited to this object and followed classes.
     *
     * @param key   header name.
     * @param value header value against given header name.
     *
     *              <br><br><b>Example :</b><br>
     *              <pre
     *              Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");<br>
     *              stack.setHeader("custom_key", "custom_value");
     *              </pre>
     */
    public void setHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            localHeader.put(key, value);
        }
    }


    /**
     * @param image_url  on which we want to manipulate.
     * @param parameters It is an second parameter in which we want to place different manipulation key and value in array form
     * @return String
     * <p>
     * ImageTransform function is define for image manipulation with different
     * parameters in second parameter in array form
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");<br>
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

        for (Map.Entry<String, Object> param : imageParams.entrySet()) {
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
                Log.e(TAG, e.getLocalizedMessage());
            }
        }

        return imageTransformationUrl;
    }


    /**
     * @param params   query parameters
     * @param callback ContentTypesCallback
     *                 This call returns comprehensive information of all the content types available in a particular stack in your account.
     *
     *                 <br><br><b>Example :</b><br>
     *                 <pre
     *                 JSONObject params = new JSONObject();
     *                 params.put("include_snippet_schema", true);
     *                 params.put("limit", 3);
     *                 stack.getContentTypes(params, new ContentTypesCallback() {
     * @Override public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
     * if (error == null){
     * // do your stuff.
     * }
     * }
     * });
     * </pre>
     */
    public void getContentTypes(JSONObject params, final ContentTypesCallback callback) {

        try {
            String URL = "/" + this.VERSION + "/content_types";
            ArrayMap<String, Object> headers = getHeader(localHeader);
            if (params == null) {
                params = new JSONObject();
            }
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                // loop to get the dynamic key
                String key = (String) keys.next();
                // get the value of the dynamic key
                Object value = params.opt(key);
                // do something here with the value...
                params.put(key, value);
            }

            if (headers.containsKey("environment")) {
                params.put("environment", headers.get("environment"));
                params.put("include_count", true);
            }

            fetchContentTypes(URL, params, headers, null, callback);

        } catch (Exception e) {

            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }


    private void fetchContentTypes(String urlString, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, ContentTypesCallback callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, this, CSController.FETCHCONTENTTYPES, urlString, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.CONTENTTYPES.toString(), false, CSAppConstants.RequestMethod.GET, callback);
        }
    }

    /**
     * @param paginationToken If the response is paginated, use the pagination token under this parameter.
     * @param syncCallBack    returns callback for sync result
     *                        <p>
     *                        If the result of the initial sync (or subsequent sync) contains more than 100 records,
     *                        the response would be paginated. It provides pagination token in the response. However,
     *                        you do not have to use the pagination token manually to get the next batch,
     *                        the SDK does that automatically until the sync is complete.
     *                        Pagination token can be used in case you want to fetch only selected batches.
     *                        It is especially useful if the sync process is interrupted midway (due to network issues, etc.).
     *                        In such cases, this token can be used to restart the sync process from where it was interrupted.
     *
     *                        <code>
     *                        stack.syncPaginationToken(pagination_token, new SyncResultCallBack()) {}
     *                        </code>
     *
     *                        </pre>
     */
    public void syncPaginationToken(String paginationToken, SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("pagination_token", paginationToken);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        this.requestSync(syncCallBack);
    }

    /**
     * @param syncToken    Use the sync token that you received in the previous/initial sync under this parameter.
     * @param syncCallBack returns callback for sync result
     *                     <p>
     *                     You can use the sync token (that you receive after initial sync) to get the updated content next time.
     *                     The sync token fetches only the content that was added after your last sync,
     *                     and the details of the content that was deleted or updated.
     *                     <br><br><b>Example :</b><br>
     *                     <pre class="prettyprint">
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  stack.syncToken(sync_token, new SyncResultCallBack() ){ }
     *
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 </pre>
     */
    public void syncToken(String syncToken, SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("sync_token", syncToken);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        this.requestSync(syncCallBack);
    }

    /**
     * @param fromDate     Enter the start date for initial sync.
     * @param syncCallBack Returns callback for sync result.
     *                     <p>
     *                     You can also initialize sync with entries published after a specific date. To do this, use syncWithDate
     *                     and specify the start date as its value.
     *
     *                     <br><br><b>Example :</b><br>
     *                     <pre class="prettyprint">
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                stack.syncFromDate(start_date, new SyncResultCallBack()) { }
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  </pre>
     */
    public void syncFromDate(Date fromDate, SyncResultCallBack syncCallBack) {
        String startFromDate = convertUTCToISO(fromDate);
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("start_from", startFromDate);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        this.requestSync(syncCallBack);
    }

    /**
     * @param contentType  Provide uid of your content_type
     * @param syncCallBack Returns callback for sync result.
     *                     <p>
     *                     You can also initialize sync with entries of only specific content_type.
     *                     To do this, use syncContentType and specify the content type uid as its value.
     *                     However, if you do this, the subsequent syncs will only include the entries of the specified content_type.
     *                     <p>
     */
    public void syncContentType(String contentType, SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("content_type_uid", contentType);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
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
     * @param language     Select the required locale from the Language class.
     * @param syncCallBack Returns callback for sync result.
     *                     You can also initialize sync with entries of only specific locales.
     *                     To do this, use syncLocale and specify the locale code as its value.
     *                     However, if you do this, the subsequent syncs will only include the entries of the specified locales.
     *                     </pre>
     */
    public void syncLocale(Language language, SyncResultCallBack syncCallBack) {
        String localeCode = getLanguageCode(language);
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("locale", localeCode);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }

        this.requestSync(syncCallBack);
    }

    /**
     * @param localeCode   Provide locale code
     * @param syncCallBack Returns callback for sync result.
     *                     You can also initialize sync with entries of only specific locales.
     *                     To do this, use syncLocale and specify the locale code as its value.
     *                     However, if you do this, the subsequent syncs will only include the entries of the specified locales.
     *                     </pre>
     */
    public void syncLocale(@NotNull String localeCode, SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("locale", localeCode);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }

        this.requestSync(syncCallBack);
    }

    /**
     * @param type         - Use the type parameter to get a specific type of content
     *                     like  ( asset_published, entry_published, asset_unpublished, asset_deleted, entry_unpublished, entry_deleted, content_type_deleted.)
     * @param syncCallBack returns callback for sync result.
     *                     <p>
     *                     Use the type parameter to get a specific type of content. You can pass one of the following values:
     *                     asset_published, entry_published, asset_unpublished, asset_deleted, entry_unpublished, entry_deleted,  content_type_deleted.
     *                     If you do not specify any value, it will bring all published entries and published assets.
     *                     <p>
     *                     <code>
     *                     stackInstance.syncPublishType(Stack.PublishType.entry_published, new SyncResultCallBack()) { }
     *                     </code>
     *
     *                     </pre>
     */

    public void syncPublishType(PublishType type, SyncResultCallBack syncCallBack) {
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("type", type);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }

        this.requestSync(syncCallBack);
    }


    private String getLanguageCode(Language language) {
        String localeCode = null;
        if (language != null) {
            Language languageName = Language.valueOf(language.name());
            int localeValue = languageName.ordinal();
            LanguageCode[] languageCodeValues = LanguageCode.values();
            localeCode = languageCodeValues[localeValue].name();
            localeCode = localeCode.replace("_", "-");
        }
        return localeCode;
    }

    /**
     * @param contentType  the content type
     * @param fromDate     the from date
     * @param language     the language code
     * @param type         the publish type
     * @param syncCallBack You can also initialize sync with entries that satisfy multiple parameters.
     *                     To do this, use syncWith and specify the parameters.
     *                     However, if you do this, the subsequent syncs will only include the entries of the specified parameters
     *                     </pre>
     */

    public void sync(String contentType, Date fromDate, Language language, PublishType type, SyncResultCallBack syncCallBack) {
        String startFromDate = convertUTCToISO(fromDate);
        this.localeCode = getLanguageCode(language);
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("start_from", startFromDate);
            syncParams.put("content_type_uid", contentType);
            syncParams.put("type", type);
            syncParams.put("locale", this.localeCode);
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        this.requestSync(syncCallBack);
    }

    public void sync(String contentType, Date fromDate, String locale, PublishType type, SyncResultCallBack syncCallBack) {
        String startFromDate = convertUTCToISO(fromDate);
        try {
            syncParams = new JSONObject();
            syncParams.put("init", true);
            syncParams.put("start_from", startFromDate);
            syncParams.put("content_type_uid", contentType);
            syncParams.put("type", type);
            syncParams.put("locale", locale);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getLocalizedMessage()));
        }
        this.requestSync(syncCallBack);
    }


    /**
     * The enum Publish type.
     */
    public enum PublishType {
        ENTRY_PUBLISHED,
        ENTRY_UNPUBLISHED,
        ENTRY_DELETED,
        ASSET_PUBLISHED,
        ASSET_UNPUBLISHED,
        ASSET_DELETED,
        CONTENT_TYPE_DELETED
    }


    private void requestSync(final SyncResultCallBack callback) {
        try {
            String URL = "/" + this.VERSION + "/stacks/" + SYNC_KEY;
            ArrayMap<String, Object> headers = getHeader(localHeader);
            JSONObject urlQueries = new JSONObject();
            if (headers.containsKey("environment")) {
                syncParams.put("environment", headers.get("environment"));
            }
            urlQueries = syncParams;
            fetchFromNetwork(URL, urlQueries, headers, null, new SyncResultCallBack() {
                @Override
                public void onCompletion(SyncStack syncStack, Error error) {
                    if (error == null) {
                        String paginationToken = syncStack.getPaginationToken();
                        if (paginationToken != null) {
                            syncPaginationToken(paginationToken, callback);
                        }
                    }
                    callback.onCompletion(syncStack, error);
                }
            });
        } catch (Exception e) {
            Error error = new Error();
            error.setErrorMessage(CSAppConstants.ErrorMessage_JsonNotProper);
            callback.onRequestFail(ResponseType.UNKNOWN, error);
        }
    }


    private void fetchFromNetwork(String urlString, JSONObject urlQueries, ArrayMap<String, Object> headers, String cacheFilePath, SyncResultCallBack callback) {
        if (callback != null) {
            HashMap<String, Object> urlParams = getUrlParams(urlQueries);
            new CSBackgroundTask(this, this, CSController.FETCHSYNC, urlString, headers, urlParams, new JSONObject(), cacheFilePath, CSAppConstants.callController.SYNC.toString(), false, CSAppConstants.RequestMethod.GET, callback);
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
                    CSAppUtils.showLog(TAG, e.getLocalizedMessage());
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


    @Override
    public void getResult(Object object, String controller) {
    }


    @Override
    public void getResultObject(List<Object> object, JSONObject jsonObject, boolean isSingleEntry) {
        SyncStack syncStackObject = new SyncStack();
        syncStackObject.setJSON(jsonObject);
        if (syncCallBack != null) {
            syncCallBack.onRequestFinish(syncStackObject);
        }
    }


}
