package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;


public class TestCred {

    public static Stack stack() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        return Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
    }
}
