package com.contentstack.sdk;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.List;


@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssetInstrumentedTest {

    private static String assetUid = BuildConfig.assetUID;
    private static Stack stack;
    private final String TAG = AssetInstrumentedTest.class.getSimpleName();

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
    }


    @Test()
    public void testAGetAllAssetsToSetAssetUid() {
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    assetUid = assets.get(0).getAssetUid();
                }
            }
        });

    }

    @Test
    public void test_B_VerifyAssetUID() {

        final Asset asset = stack.asset(assetUid);
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    // Success Block.
                    Log.d(TAG, "response: " + asset.getAssetUid());
                    assertEquals(assetUid, asset.getAssetUid());
                }
            }
        });
    }

    @Test
    public void test_C_Asset_fetch() {
        final Asset asset = stack.asset(assetUid);
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    assertEquals(BuildConfig.assetUID, asset.getAssetUid());
                    assertEquals("image/jpeg", asset.getFileType());
                    assertEquals("phoenix2.jpg", asset.getFileName());
                    assertEquals("482141", asset.getFileSize());
                } else {
                    assertEquals(105, error.getErrorCode());
                }
            }
        });
    }

    @Test
    public void test_D_AssetLibrary_fetch() {
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    assets.forEach(asset -> {
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.toJSON());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getFileType());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getCreatedBy());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getUpdatedBy());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getFileName());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getFileSize());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getAssetUid());
                        Log.d(TAG, "----Test--Asset-D--Success----" + asset.getUrl());
                    });
                }
            }
        });
    }

    @Test
    public void test_E_AssetLibrary_includeCount_fetch() {
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeCount();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    assertEquals(16, assetLibrary.getCount());
                }
            }
        });
    }

    @Test
    public void test_F_AssetLibrary_includeRelativeUrl_fetch() {
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeRelativeUrl();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    assertTrue(assets.get(0).getUrl().contains("phoenix2.jpg"));
                }
            }
        });
    }

    @Test
    public void test_G_Include_Dimension() {
        final Asset asset = stack.asset(assetUid);
        asset.includeDimension().fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    Log.d(TAG, asset.getAssetUid());
                    assertEquals(assetUid, asset.getAssetUid());
                }
            }
        });
    }


    @Test
    public void test_H_include_fallback() {
        final Asset asset = stack.asset(assetUid);
        asset.includeFallback().fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    Log.d(TAG, asset.getAssetUid());
                    assertEquals(assetUid, asset.getAssetUid());
                }
            }
        });
    }

    @Test
    public void setStackInstance() {
    }

    @Test
    public void configure() {
    }

    @Test
    public void setHeader() {
    }

    @Test
    public void removeHeader() {
    }

    @Test
    public void setUid() {
    }

    @Test
    public void getAssetUid() {
    }

    @Test
    public void getFileType() {
    }

    @Test
    public void getFileSize() {
    }

    @Test
    public void getFileName() {
    }

    @Test
    public void getUrl() {
    }

    @Test
    public void toJSON() {
    }

    @Test
    public void getCreateAt() {
    }

    @Test
    public void getCreatedBy() {
    }

    @Test
    public void getUpdateAt() {
    }

    @Test
    public void getUpdatedBy() {
    }

    @Test
    public void getDeleteAt() {
    }

    @Test
    public void getDeletedBy() {
    }

    @Test
    public void getTags() {
    }

    @Test
    public void setCachePolicy() {
    }

    @Test
    public void fetch() {
    }

    @Test
    public void setTags() {
    }

    @Test
    public void addParam() {
    }

    @Test
    public void includeDimension() {
    }

    @Test
    public void includeFallback() {
    }

}
