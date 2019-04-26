package com.contentstack.sdk.cachePolicy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedHashMap;
import java.util.List;
import android.support.test.runner.AndroidJUnit4;

import com.contentstack.sdk.Asset;
import com.contentstack.sdk.CachePolicy;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.FetchAssetsCallback;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;

import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;

/**
 * Created by Shailesh Mishra on 1/6/16.
 */

@RunWith(AndroidJUnit4.class)
public class AssetCachePolicy  {

    private static final String TAG = AssetCachePolicy.class.getSimpleName();
    private CountDownLatch startSignal;
    private Stack stack;
    private String responseTypeResult = "";
    private static final String DEFAULT_API_KEY = "blt903007d63561dea2";
    private static final String DEFAULT_ACCESS_TOKEN = "blte31e27332a44557e";
    private static final String DEFAULT_ENV = "gal_dev";
    private LinkedHashMap imageParams = new LinkedHashMap();
    private final String IMAGE_URL = "https://images.contentstack.io/v3/assets/blt903007d63561dea2/blt638399801b6bd23c/59afa6406c11eb860ddf04aa/download";


    public AssetCachePolicy() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost("cdn-contentstack.com");
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





    /*============================================================================================*/


    @Test
    public void test01_ImageOptimization(){

        imageParams.put("auto", "webp");
        imageParams.put("quality", 200);
        imageParams.put("width", 100);
        imageParams.put("height", 50);
        imageParams.put("format", "png");
        imageParams.put("crop", "3:5");
        imageParams.put("trim", "20,20,20,20");
        imageParams.put("disable", "upscale");
        imageParams.put("pad", "10,10,10,10");
        imageParams.put("bg-color", "#FFFFFF");
        imageParams.put("dpr", 20);
        imageParams.put("canvas", "3:5");
        imageParams.put("orient", "l");

        String image_url = stack.ImageTransform(IMAGE_URL, imageParams);
        int counter = 0;
        /* check url contains "?" */
        if (!image_url.equalsIgnoreCase("") && image_url.contains("?")){
            String [] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String [] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size()>0){
                for (int i=0; i<imageParams.size(); i++){
                    String keyValueParis = getAllPairs[i];
                    Log.e(TAG, "pairs:--> "+keyValueParis);
                    ++counter;
                }
            }
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }

        if (counter==imageParams.size()){
            startSignal.countDown();
            Log.e(TAG, "Testcases Passed");
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }
    }






    @Test
    public void test02_ImageOptimization(){

        imageParams.put("auto", "webp");
        imageParams.put("quality", 200);
        imageParams.put("width", 100);
        imageParams.put("height", 50);
        imageParams.put("format", "png");
        imageParams.put("crop", "3:5");
        imageParams.put("trim", "20,20,20,20");
        imageParams.put("disable", "upscale");

        String image_url = stack.ImageTransform(IMAGE_URL, imageParams);
        int counter = 0;
        /* check url contains "?" */
        if (!image_url.equalsIgnoreCase("") && image_url.contains("?")){
            String [] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String [] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size()>0){
                for (int i=0; i<imageParams.size(); i++){
                    String keyValueParis = getAllPairs[i];
                    Log.e(TAG, "pairs:--> "+keyValueParis);
                    ++counter;
                }
            }
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }

        if (counter==imageParams.size()){
            startSignal.countDown();
            Log.e(TAG, "Testcases Passed");
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }
    }



    @Test
    public void test03_ImageOptimization(){

        imageParams.put("pad", "10,10,10,10");
        imageParams.put("bg-color", "#FFFFFF");
        imageParams.put("dpr", 20);
        imageParams.put("canvas", "3:5");
        imageParams.put("orient", "l");

        String image_url = stack.ImageTransform(IMAGE_URL, imageParams);
        int counter = 0;
        /* check url contains "?" */
        if (!image_url.equalsIgnoreCase("") && image_url.contains("?")){
            String [] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String [] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size()>0){
                for (int i=0; i<imageParams.size(); i++){
                    String keyValueParis = getAllPairs[i];
                    Log.e(TAG, "pairs:--> "+keyValueParis);
                    ++counter;
                }
            }
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }

        if (counter==imageParams.size()){
            startSignal.countDown();
            Log.e(TAG, "Testcases Passed");
        }else {
            Log.e(TAG, "Testcases Failed");
            try{
                startSignal.await();
            }catch(Exception e){
                System.out.println("---------------||"+e.toString());
            }
        }
    }

}
