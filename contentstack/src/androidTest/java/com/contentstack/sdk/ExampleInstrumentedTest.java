package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.BeforeClass;
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
    @BeforeClass
    public static void useAppContext() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment);
        assertEquals("com.contentstack.sdk.test", ctx.getPackageName().toLowerCase());
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
    public void initSDK() {
        stack.sync(null);
        assertNotNull(stack.syncParams);
    }


    @Test
    public void syncSDKWIthAllParams() {
        stack.sync("abcd", new Date(), "en-us", Stack.PublishType.ENTRY_PUBLISHED, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

            }
        });
        assertNotNull(stack.syncParams);
    }
}