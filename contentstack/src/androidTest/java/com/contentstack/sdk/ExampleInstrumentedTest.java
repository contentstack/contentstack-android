package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        config.setBranch("main");
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment, config);
        assertEquals("com.contentstack.sdk.test", ctx.getPackageName().toLowerCase());
        assertEquals("main", stack.config.branch);
        assertEquals("azure_na", stack.config.getRegion().toString().toLowerCase());
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
    public void testEarlyAccess() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String[] earlyAccess = {"Taxonomy"};
        config.earlyAccess(earlyAccess);
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment, config);
        assertEquals(earlyAccess[0], config.earlyAccess[0]);
        assertNotNull(stack.localHeader.containsKey("x-header-ea"));
        assertEquals("Taxonomy", stack.localHeader.get("x-header-ea"));
    }
    
    @Test
    public void testConfigEarlyAccessMultipleFeature() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String[] earlyAccess = {"Taxonomy", "Teams", "Terms", "LivePreview"};
        config.earlyAccess(earlyAccess);
        stack = Contentstack.stack(ctx, apiKey, deliveryToken, environment, config);
        assertEquals(4, stack.localHeader.keySet().size());
        assertEquals(earlyAccess[1], config.earlyAccess[1]);
        assertTrue(stack.localHeader.containsKey("x-header-ea"));
        assertEquals("Taxonomy,Teams,Terms,LivePreview", stack.localHeader.get("x-header-ea"));
    }
    
}