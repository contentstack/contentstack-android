package com.builtio.contentstack;

import android.support.annotation.IntegerRes;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.builtio.contentstack.utilities.CSAppConstants;
import com.builtio.contentstack.utilities.CSAppUtils;
import com.builtio.contentstack.utilities.CSController;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * To fetch stack level information of your application from Built.io Contentstack server.
 *
 * @author built.io, Inc
 *
 */
public class Stack {

    private static final String TAG = "Stack";
    private String stackApiKey = null;
    protected ArrayMap<String, Object> localHeader = null;
    private String imageTransformationUrl;
    private LinkedHashMap<String, Object> imageParams = new LinkedHashMap<>();
    //TODO CONSTANTS
    protected String URLSCHEMA     = "https://";
    protected String URL           = "cdn.contentstack.io";
    protected String VERSION       = "v3";

    protected Config config;

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
    public String getAccessToken(){ return localHeader != null ? (String)localHeader.get("access_token") : null;};

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

                String paramKey = param.getKey().toString();
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

}
