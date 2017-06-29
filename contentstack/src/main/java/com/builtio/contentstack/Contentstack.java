package com.builtio.contentstack;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.text.TextUtils;

import com.builtio.contentstack.utilities.CSAppConstants;
import com.builtio.contentstack.utilities.CSAppUtils;
import com.builtio.volley.Request;
import com.builtio.volley.RequestQueue;
import com.builtio.volley.toolbox.Volley;

import java.io.File;

/**
 * Contains all Contentstack API classes and functions.
 *
 * @author  built.io, Inc
 *
 */
public class Contentstack {

    private static final String TAG = "Contentstack";
    protected static RequestQueue requestQueue;
    protected static Context applicationContext = null;
    private Contentstack(){}

    /**
     *
     * Authenticates the stack api key of your stack.
     * This must be called before your stack uses Built.io Contentstack sdk.
     * <br>
     * You can find your stack api key from web.
     *
     * @param context
     * application context.
     *
     * @param stackApiKey
     * application api Key of your application on Built.io Contentstack.
     *
     * @param accessToken
     * access token
     *
     * @param environment
     * environment name
     *
     *
     * @return
     * {@link Stack} instance.
     *
     * @throws Exception
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", "stag");
     * </pre>
     */
    public static Stack stack(Context context, String stackApiKey, String accessToken, String environment) throws Exception{
        if(context != null) {
            if(!TextUtils.isEmpty(stackApiKey)) {
                if(!TextUtils.isEmpty(accessToken)) {
                    if(!TextUtils.isEmpty(environment)) {
                        Config config = new Config();
                        config.setEnvironment(environment);
                        return initializeStack(context, stackApiKey, accessToken, config);
                    }else{
                        throw new Exception(CSAppConstants.ErrorMessage_Stack_Environment_IsNull);
                    }
                }else{
                    throw new Exception(CSAppConstants.ErrorMessage_Stack_AccessToken_IsNull);
                }
            }else {
                throw new Exception(CSAppConstants.ErrorMessage_StackApiKeyIsNull);
            }
        }else{
            throw new Exception(CSAppConstants.ErrorMessage_StackContextIsNull);
        }
    }

    /**
     * Authenticates the stack api key of your stack.
     * This must be called before your stack uses Built.io Contentstack sdk.
     * <br>
     * You can find your stack api key from web.
     *
     * @param context
     * application context.
     *
     * @param stackApiKey
     * application api Key of your application on Built.io Contentstack.
     *
     * @param accessToken
     * access token
     *
     * @param config
     * {@link Config} instance to set environment and other configuration details.
     *
     * @return
     * {@link Stack} instance.
     *
     * @throws Exception
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     * //'blt5d4sample2633b' is a dummy Stack API key
     * //'blt6d0240b5sample254090d' is dummy access token.
     * Config config = new Config();
     * config.setEnvironment("stag");
     *
     * Stack stack = Contentstack.stack(context, "blt5d4sample2633b", "blt6d0240b5sample254090d", config);
     */


    public static Stack stack(Context context, String stackApiKey, String accessToken,
                              String environment, Config config) throws Exception{
        if(context != null) {
            if(!TextUtils.isEmpty(stackApiKey)) {
                if(!TextUtils.isEmpty(accessToken)) {
                    if(!TextUtils.isEmpty(environment)) {

                        if(config != null){
                            config.setEnvironment(environment);
                        }else {
                            config = new Config();
                            config.setEnvironment(environment);
                        }
                        return initializeStack(context, stackApiKey, accessToken, config);
                    }else{
                        throw new Exception(CSAppConstants.ErrorMessage_Stack_Environment_IsNull);
                    }
                }else{
                    throw new Exception(CSAppConstants.ErrorMessage_Stack_AccessToken_IsNull);
                }
            }else {
                throw new Exception(CSAppConstants.ErrorMessage_StackApiKeyIsNull);
            }
        }else{
            throw new Exception(CSAppConstants.ErrorMessage_StackContextIsNull);
        }
    }


    private static Stack initializeStack(Context context, String stackApiKey, String accessToken, Config config){
        Stack stack = new Stack(stackApiKey.trim());
        stack.setHeader("api_key", stackApiKey);
        stack.setHeader("access_token", accessToken);
        applicationContext = context;
        stack.setConfig(config);

        if(context != null) {
            try {

                //cache folder
                File queryCacheFile = context.getDir("ContentstackCache", 0);
                CSAppConstants.cacheFolderName = queryCacheFile.getPath();

                clearCache(context);
            } catch (Exception e) {
                CSAppUtils.showLog(TAG, "-------------------stack-Contentstack-" + e.toString());
            }
        }
        return stack;
    }


    /********************************************************************************************************
     *
     * // Private Functionality
     *
     ********************************************************************************************************/

    protected static RequestQueue getRequestQueue(String protocol) {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext, new OkHttpClass(protocol));
        }
        return requestQueue;
    }

    protected static <T> void addToRequestQueue(String protocol, Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(protocol).add(req);
    }

    /**
     * To start schedule for clearing cache.
     * @param context
     * application context.
     */

    private static void clearCache(Context context) {

        Intent alarmIntent = new Intent("StartContentStackClearingCache");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * To check network availability.
     */
    protected static void isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0) != null || connectivityManager.getNetworkInfo(1).getState() != null) {
            if (connectivityManager.getActiveNetworkInfo() == null) {
                CSAppConstants.isNetworkAvailable = false;
            } else {
                CSAppConstants.isNetworkAvailable = true;
            }
        }else if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            CSAppConstants.isNetworkAvailable = true;
        } else {
            CSAppConstants.isNetworkAvailable = false;
        }
    }
}
