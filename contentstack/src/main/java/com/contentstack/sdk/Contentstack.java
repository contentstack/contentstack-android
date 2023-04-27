package com.contentstack.sdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * Contains all Contentstack SDK classes and functions.
 *
 * @author ishaileshmishra
 */
public class Contentstack {

    private static final String TAG = Contentstack.class.getSimpleName();
    protected static RequestQueue requestQueue;
    private static Contentstack instance;
    protected static Context context = null;

    private Contentstack() {
        throw new IllegalStateException("Private constructor not allowed");
    }

    public Contentstack(Context applicationContext) {
        context = applicationContext;
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
     * @return {@link Stack} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", "stag");
     * </pre>
     */
    public static Stack stack(Context context, String apiKey, String deliveryToken, String environment) throws Exception {
        if (context != null) {
            if (!TextUtils.isEmpty(apiKey)) {
                if (!TextUtils.isEmpty(deliveryToken)) {
                    if (!TextUtils.isEmpty(environment)) {
                        Config config = new Config();
                        config.setEnvironment(environment);
                        return initializeStack(context, apiKey, deliveryToken, config);
                    } else {
                        throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_ENVIRONMENT_IS_NULL);
                    }
                } else {
                    throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_ACCESS_TOKEN_IS_NULL);
                }
            } else {
                throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_API_KEY_IS_NULL);
            }
        } else {
            throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_CONTEXT_IS_NULL);
        }
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
     * @param config        {@link Config} instance to set environment and other configuration details.
     * @return {@link Stack} instance.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * Config config = new Config();
     * config.setEnvironment("stag");
     * Stack stack = Contentstack.stack(context, "apiKey", "deliveryToken", config);
     */
    public static Stack stack(Context context, String apiKey, String deliveryToken, String environment, Config config) throws Exception {
        if (context != null) {
            if (!TextUtils.isEmpty(apiKey)) {
                if (!TextUtils.isEmpty(deliveryToken)) {
                    if (!TextUtils.isEmpty(environment)) {

                        if (config != null) {
                            config.setEnvironment(environment);
                        } else {
                            config = new Config();
                            config.setEnvironment(environment);
                        }
                        return initializeStack(context, apiKey, deliveryToken, config);
                    } else {
                        throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_ENVIRONMENT_IS_NULL);
                    }
                } else {
                    throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_ACCESS_TOKEN_IS_NULL);
                }
            } else {
                throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_API_KEY_IS_NULL);
            }
        } else {
            throw new Exception(SDKConstant.ERROR_MESSAGE_STACK_CONTEXT_IS_NULL);
        }
    }


    private static Stack initializeStack(Context appContext, String apiKey, String deliveryToken, Config config) {
        Stack stack = new Stack(apiKey.trim());
        stack.setHeader("api_key", apiKey);
        stack.setHeader("access_token", deliveryToken);
        context = appContext;
        stack.setConfig(config);
        if (context != null) {
            try {
                File queryCacheFile = context.getDir("ContentstackCache", 0);
                SDKConstant.cacheFolderName = queryCacheFile.getPath();
                clearCache(context);
            } catch (Exception e) {
                SDKUtil.showLog(TAG, "Contentstack-" + e.getLocalizedMessage());
            }
        }
        return stack;
    }


    /********************************************************************************************************
     *
     * // Private Functionality
     *
     ********************************************************************************************************/


    public static synchronized Contentstack getInstance(Context context) {
        if (instance == null) {
            instance = new Contentstack(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    protected static RequestQueue getRequestQueue(String protocol) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    protected static <T> void addToRequestQueue(String protocol, Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(protocol).add(req);
    }

    /**
     * To start schedule for clearing cache.
     *
     * @param context application context.
     */

    private static void clearCache(Context context) {
        Intent alarmIntent = new Intent("StartContentStackClearingCache");
        alarmIntent.setPackage(context.getPackageName());
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= 23) flag = PendingIntent.FLAG_IMMUTABLE | flag;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, flag);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * To check network availability.
     */
    protected static void isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0) != null || connectivityManager.getNetworkInfo(1).getState() != null) {
            SDKConstant.IS_NETWORK_AVAILABLE = connectivityManager.getActiveNetworkInfo() != null;
        } else
            SDKConstant.IS_NETWORK_AVAILABLE = connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
    }
}
