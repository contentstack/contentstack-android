package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


@RunWith(AndroidJUnit4ClassRunner.class)
public class ExampleInstrumentedTest {

    static final String apiKey = BuildConfig.APIKey;
    static final String deliveryToken = BuildConfig.deliveryToken;
    static final String environment = BuildConfig.environment;
    static Stack stack = null;


    @Test
    public void useAppContext() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment);
        assertEquals("com.contentstack.sdk.test", ctx.getPackageName().toLowerCase());
    }

    @Test
    public void testConfig() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setBranch("dev");
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment, config);
        assertEquals("com.contentstack.sdk.test", ctx.getPackageName().toLowerCase());
        assertEquals("dev", stack.config.branch);
        assertEquals("azure_na", stack.config.getRegion().toString().toLowerCase());
    }

    @Test
    public void testBranch() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setBranch("dev");
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment, config);
        Query query = stack.contentType("product").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                Log.d("", String.valueOf(queryresult.getResultObjects().stream().count()));
            }
        });

    }

    @Test
    public void testAPINotNull() {
        assertNotNull(apiKey);
    }

    @Test
    public void testDeliveryTokenNotNull() {
        assertNotNull(deliveryToken);
    }

    @Test
    public void testEnvironmentNotNull() {
        assertNotNull(environment);
    }


    @Test
    public void syncLocale() {
        stack.syncLocale("ar-eu", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

            }
        });
        assertNotNull(stack.syncParams);
    }


    @Test
    public void syncSDKWIthAllParams() {
        stack.sync("content_type", new Date(), "en-us", Stack.PublishType.ENTRY_PUBLISHED, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

            }
        });
        assertNotNull(stack.syncParams);
    }
}