package com.contentstack.sdk;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Objects;

/**
 * Contains all Contentstack SDK Classes And Methods.
 *
 * @author ishaileshmishra
 */
public class Contentstack {

    private static final String TAG = Contentstack.class.getSimpleName();
    /**
     * The constant requestQueue.
     */
    protected static RequestQueue requestQueue;

    private static Context ctx;
    private static Contentstack instance;

    private Contentstack(Context context) {
        throw new IllegalStateException("Private constructor not allowed");
    }

    /**
     * Authenticates the stack api key of your stack.
     * This must be called before your stack uses Contentstack sdk.
     * <br>
     * You can find your stack api key from web.
     *
     * @param context       application context.
     * @param apiKey        The api Key of your stack on Contentstack.
     * @param deliveryToken The Delivery Token for the stack
     * @param environment   environment name
     * @return {@link Stack} instance. <br><br><b>Example :</b><br> <pre class="prettyprint"> Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag"); </pre>
     * @throws Exception the exception
     */
    public static Stack stack(Context context, String apiKey, String deliveryToken, String environment) throws Exception {
        checkIfNull(context, apiKey, deliveryToken, environment);

        if (!TextUtils.isEmpty(apiKey) || !TextUtils.isEmpty(deliveryToken) || !TextUtils.isEmpty(environment)) {
            Config config = new Config();
            config.setEnvironment(environment);
            ctx = context;
            return initializeStack(context, apiKey, deliveryToken, config);
        } else {
            throw new Exception(SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED);
        }
    }

    private static void checkIfNull(Context context, String apiKey, String deliveryToken, String environment) {
        Objects.requireNonNull(context, "can not be null");
        Objects.requireNonNull(apiKey, "can not be null");
        Objects.requireNonNull(deliveryToken, "can not be null");
        Objects.requireNonNull(environment, "can not be null");
    }

    /**
     * Authenticates the stack api key of your stack.
     * This must be called before your stack uses Contentstack sdk.
     * <br>
     * You can find your stack api key from web.
     *
     * @param context       application context.
     * @param apiKey        The api Key of your stack on Contentstack.
     * @param deliveryToken The delivery token for the stack on Contentstack
     * @param environment   the environment
     * @param config        {@link Config} instance to set environment and other configuration details.
     * @return {@link Stack} instance. <br><br><b>Example :</b><br> <pre class="prettyprint"> Config config = new Config(); config.setEnvironment("stag"); Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", config);
     * @throws Exception the exception
     */
    public static Stack stack(Context context, String apiKey, String deliveryToken, String environment, Config config) throws Exception {
        checkIfNull(context, apiKey, deliveryToken, environment);
        Objects.requireNonNull(config, "Config can not be null");
        if (!TextUtils.isEmpty(apiKey) || !TextUtils.isEmpty(deliveryToken) || !TextUtils.isEmpty(environment)) {
            config.setEnvironment(environment);
            ctx = context;
            return initializeStack(context, apiKey, deliveryToken, config);
        } else {
            throw new Exception(SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED);
        }
    }


    private static Stack initializeStack(Context appContext, String apiKey, String deliveryToken, Config config) {
        Stack stack = new Stack(apiKey.trim());
        stack.setHeader("api_key", apiKey);
        stack.setHeader("access_token", deliveryToken);
        if (config.getBranch() != null) {
            stack.setHeader("branch", config.getBranch());
        }
        if (config.getEarlyAccess() != null && config.getEarlyAccess().length > 0) {
            String eaValues = String.join(",", config.earlyAccess).replace("\"", "");
            stack.setHeader("x-header-ea", eaValues);
        }
        stack.setConfig(config);
        initializeCache(appContext);
        return stack;
    }


    private static void initializeCache(Context appContext) {
        try {
            File queryCacheFile = appContext.getDir(SDKConstant.CACHE, 0);
            SDKConstant.cacheFolderName = queryCacheFile.getPath();
            SDKUtil.clearCache(appContext);
        } catch (Exception e) {
            handleCacheInitializationError(e);
        }
    }

    private static void handleCacheInitializationError(Exception e) {
        SDKUtil.showLog(TAG, SDKConstant.CACHE + e.getLocalizedMessage());
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    protected static synchronized Contentstack getInstance(Context context) {
        if (instance == null) {
            instance = new Contentstack(context);
        }
        return instance;
    }

    /**
     * Gets request queue.
     *
     * @return the request queue
     */
    protected RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     */
    protected <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Gets request queue.
     *
     * @param protocol the protocol
     * @return the request queue
     */
    protected static RequestQueue getRequestQueue(String protocol) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }
        return requestQueue;
    }

    /**
     * Add to request queue.
     *
     * @param <T>      the type parameter
     * @param protocol the protocol
     * @param req      the req
     * @param tag      the tag
     */
    protected static <T> void addToRequestQueue(String protocol, Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(protocol).add(req);
    }


}
