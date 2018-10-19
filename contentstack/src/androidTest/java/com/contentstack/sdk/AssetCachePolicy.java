package com.contentstack.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;

/**
 * Created by chinmay on 1/6/16.
 */

@RunWith(AndroidJUnit4.class)
public class AssetCachePolicy  {

    private static final String TAG = "TESTCASE";
    private CountDownLatch startSignal;
    private Context appContext;
    private Stack stack;
    private String responseTypeResult = "";
    private static final String DEFAULT_API_KEY = "blt903007d63561dea2";
    private static final String DEFAULT_ACCESS_TOKEN = "blte31e27332a44557e";
    private static final String DEFAULT_ENV = "gal_dev";



    public AssetCachePolicy() throws Exception{

        appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_ACCESS_TOKEN, DEFAULT_ENV, config);
        startSignal = new CountDownLatch(1);
    }




    @Test
    public void test00_getAssetWith_CachePolicy() {

            stack.assetLibrary().setCachePolicy(CachePolicy.NETWORK_ONLY);
            stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
                @Override
                public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                    if (error == null) {
                        responseTypeResult = responseType.toString();
                        Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                        startSignal.countDown();
                    } else {
                        Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                        startSignal.countDown();
                    }
                }
            });

            try{
                startSignal.await();

            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }

            assertEquals("NETWORK", responseTypeResult);

    }



    @Test
    public void test01_getAssetWith_CachePolicy() {

        stack.assetLibrary().setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    responseTypeResult = responseType.toString();
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                    startSignal.countDown();
                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    startSignal.countDown();
                }
            }
        });

        try{
            startSignal.await();

        }catch(Exception e){
            System.out.println("---------------||"+e.toString());
        }

        assertEquals("CACHE", responseTypeResult);

    }




    @Test
    public void test02_getAssetWith_CachePolicy() {

        stack.assetLibrary().setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    responseTypeResult = responseType.toString();
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                    startSignal.countDown();
                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    startSignal.countDown();
                }
            }
        });

        try{
            startSignal.await();

        }catch(Exception e){
            System.out.println("---------------||"+e.toString());
        }

        assertEquals("NETWORK", responseTypeResult);

    }



    @Test
    public void test03_getAssetWith_CachePolicy() {

        stack.assetLibrary().setCachePolicy(CachePolicy.CACHE_ONLY);
        stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    responseTypeResult = responseType.toString();
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                    startSignal.countDown();
                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    startSignal.countDown();
                }
            }
        });

        try{
            startSignal.await();

        }catch(Exception e){
            System.out.println("---------------||"+e.toString());
        }

        assertEquals("CACHE", responseTypeResult);

    }




    @Test
    public void test04_getAssetWith_CachePolicy() {

        stack.assetLibrary().setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
        stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    responseTypeResult = responseType.toString();
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                    startSignal.countDown();
                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    startSignal.countDown();
                }
            }
        });

        try{
            startSignal.await();

        }catch(Exception e){
            System.out.println("---------------||"+e.toString());
        }

        assertEquals("CACHE", responseTypeResult);

    }




    @Test
    public void test05_getAssetWith_CachePolicy() {

        stack.assetLibrary().setCachePolicy(CachePolicy.IGNORE_CACHE);
        stack.assetLibrary().fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {
                if (error == null) {
                    responseTypeResult = responseType.toString();
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + responseType);
                    startSignal.countDown();
                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    startSignal.countDown();
                }
            }
        });

        try{
            startSignal.await();

        }catch(Exception e){
            System.out.println("---------------||"+e.toString());
        }

        assertEquals("NETWORK", responseTypeResult);

    }



}
