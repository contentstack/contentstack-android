package com.contentstack.sdk;

import android.content.Context;
import android.util.Log;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssetTestCase {

    private final String TAG = AssetTestCase.class.getSimpleName();
    private static String assetUid = BuildConfig.assetUID;
    private static Stack stack;
    private static CountDownLatch latch;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
    }

    @Test
    public void test_B_VerifyAssetUID() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Asset asset = stack.asset(assetUid);
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // Success Block.
                Log.d(TAG, "response: " + asset.getAssetUid());
                assertEquals(assetUid, asset.getAssetUid());
                // Unlock the latch to allow the test to proceed
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void test_C_Asset_fetch() throws Exception {
        Config config = new Config();
        Context appContext = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);
        final CountDownLatch latch = new CountDownLatch(1);
        final Asset asset = stack.asset(assetUid);
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                assertEquals(BuildConfig.assetUID, asset.getAssetUid());
                assertEquals("image/jpeg", asset.getFileType());
                assertEquals("image1.jpg", asset.getFileName());
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void test_E_AssetLibrary_includeCount_fetch() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeCount();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                assertEquals(5, assetLibrary.getCount());
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void test_G_Include_Dimension() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Asset asset = stack.asset(assetUid);
        asset.includeDimension();
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                assertEquals(assetUid, asset.getAssetUid());
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }


    @Test
    public void test_H_include_fallback() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Asset asset = stack.asset(assetUid);
        asset.includeFallback();
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                assertEquals(assetUid, asset.getAssetUid());
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void test_AZURE_NA() throws Exception {
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        config.setRegion(Config.ContentstackRegion.AZURE_NA);
        Context appContext = InstrumentationRegistry.getTargetContext();
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
    }

    @Test
    public void test_GCP_NA() throws Exception {
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        config.setRegion(Config.ContentstackRegion.GCP_NA);
        Context appContext = InstrumentationRegistry.getTargetContext();
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
    }

    @Test
    public void test_M_fetch_asset_empty_title() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final AssetLibrary assetLibrary = stack.assetLibrary().where("title","");
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                assertEquals(0, assets.size());
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

}
