package com.contentstack.sdk.allRegualar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.contentstack.sdk.Asset;
import com.contentstack.sdk.AssetLibrary;
import com.contentstack.sdk.BuildConfig;
import com.contentstack.sdk.CachePolicy;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.EntryResultCallBack;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.FetchAssetsCallback;
import com.contentstack.sdk.FetchResultCallback;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by chinmay on 1/6/16.
 */

@RunWith(AndroidJUnit4.class)
public class AssetTestCase  {

    private static final String TAG = AssetTestCase.class.getSimpleName();
    private Context appContext;
    private Stack stack;


    public AssetTestCase() throws Exception{

        appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost(BuildConfig.base_url);
        stack = Contentstack.stack(appContext,
                BuildConfig.default_api_key,
                BuildConfig.default_access_token,
                BuildConfig.default_env,config);
    }


    @Test
    public void test01_Asset_getAsset(){

        final Entry entry = stack.contentType("multifield").entry("blt1b1cb4f26c4b682e");

        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + entry.toJSON());

                    Asset asset = entry.getAsset("imagefile");

                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.toJSON());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getFileType());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getCreatedBy());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getUpdatedBy());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getFileName());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getFileSize());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getAssetUid());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getUrl());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getCreateAt().getTime());
                    Log.w(TAG, "----------Test--Asset-01--Success---------" + asset.getUpdateAt().getTime());

                } else {
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--01--Error---------" + error.getErrors());
                }

            }
        });

    }

    @Test
    public void test02_Asset_getAssets(){

        final Entry entry = stack.contentType("multifield").entry("blt1b1cb4f26c4b682e");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    Log.w(TAG, "----------Test--ENTRY-02--Success---------" + entry.toJSON());

                    List<Asset> assets = entry.getAssets("file");

                    for (int i = 0; i < assets.size(); i++) {
                        Asset asset = assets.get(i);

                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.toJSON());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getFileType());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getCreatedBy());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getUpdatedBy());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getFileName());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getAssetUid());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getUrl());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getCreateAt().getTime());
                        Log.w(TAG, "----------Test--Asset-02--Success---------" + i +" ---- " + asset.getUpdateAt().getTime());

                    }


                } else {
                    Log.w(TAG, "----------Test--Asset--02--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--02--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--02--Error---------" + error.getErrors());
                }

            }
        });

    }


    @Test
    public void test03_Asset_fetch(){
        final Object result[] = new Object[2];
        final Asset asset = stack.asset("blt5312f71416d6e2c8");
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if(error == null){


                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.toJSON());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileType());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileName());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileSize());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getAssetUid());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUrl());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreateAt().getTime());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdateAt().getTime());

                    assertTrue(asset instanceof Asset);

                }else {
                    result[0] = error;
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrors());
                }

            }
        });


    }



    @Test
    public void test04_AssetLibrary_fetch(){

        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {

                if (error == null) {
                    for (int i = 0; i < assets.size(); i++) {
                        Asset asset = assets.get(i);

                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.toJSON());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getFileType());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getCreatedBy());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getUpdatedBy());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getFileName());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getAssetUid());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getUrl());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getCreateAt().getTime());
                        Log.w(TAG, "----------Test--Asset-04--Success---------" + i +" ---- " + asset.getUpdateAt().getTime());
                    }

                } else {
                    Log.w(TAG, "----------Test--Asset--04--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--04--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--04--Error---------" + error.getErrors());
                }
            }
        });

    }



    @Test
    public void test05_AssetLibrary_includeCount_fetch(){

        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeCount();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {

                if (error == null) {

                    System.out.println("count = [" + assetLibrary.getCount() + "]");

                    for (int i = 0; i < assets.size(); i++) {
                        Asset asset = assets.get(i);

                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.toJSON());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileType());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getCreatedBy());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUpdatedBy());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileName());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getAssetUid());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUrl());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getCreateAt().getTime());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUpdateAt().getTime());
                    }

                } else {
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrors());

                }

            }
        });

    }




    @Test
    public void test06_AssetLibrary_includeRelativeUrl_fetch(){

        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.includeRelativeUrl();
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {

                if (error == null) {

                    for (int i = 0; i < assets.size(); i++) {
                        Asset asset = assets.get(i);

                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.toJSON());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileType());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getCreatedBy());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUpdatedBy());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileName());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getAssetUid());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUrl());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getCreateAt().getTime());
                        Log.w(TAG, "----------Test--Asset-05--Success---------" + i +" ---- " + asset.getUpdateAt().getTime());
                    }

                } else {
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrors());
                }

            }
        });

    }



    @Test
    public void test07_AssetLibrary_setCachePolicy_fetch(){
        final Object result[] = new Object[2];
        final AssetLibrary assetLibrary = stack.assetLibrary();
        assetLibrary.setCachePolicy(CachePolicy.NETWORK_ONLY);
        assetLibrary.fetchAll(new FetchAssetsCallback() {
            @Override
            public void onCompletion(ResponseType responseType, List<Asset> assets, Error error) {

                if (error == null) {

                    int counter = 0;
                    for (int i = 0; i < assets.size(); i++) {
                        Asset asset = assets.get(i);
                        counter = Integer.parseInt(asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.toJSON());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getFileType());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getCreatedBy());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getUpdatedBy());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getFileName());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getFileSize());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getAssetUid());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getUrl());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getCreateAt().getTime());
                        Log.w(TAG, "----------Test--Asset-07--Success---------" + i +" ---- " + asset.getUpdateAt().getTime());

                    }
                    assertEquals(5735, counter);



                } else {
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--05--Error---------" + error.getErrors());
                }

            }
        });

    }



    @Test
    public void test08_Asset_setCachePolicy_fetch(){
        final Asset asset = stack.asset("blt5312f71416d6e2c8");
        asset.setCachePolicy(CachePolicy.NETWORK_ONLY);
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if(error == null){

                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.toJSON());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileType());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileName());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileSize());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getAssetUid());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUrl());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreateAt().getTime());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdateAt().getTime());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getTags().length);

                    assertTrue(asset instanceof Asset);
                }else {
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrors());

                }

            }
        });


    }

    /************************************************************/



    @Test
    public void test11_Asset_addParams(){

        final Asset asset = stack.asset("blt5312f71416d6e2c8");
        asset.addParam("key", "some_value");
        asset.fetch(new FetchResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if(error == null){

                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.toJSON());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileType());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdatedBy());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileName());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getFileSize());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getAssetUid());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUrl());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getCreateAt().getTime());
                    Log.w(TAG, "----------Test--Asset-03--Success---------" + asset.getUpdateAt().getTime());

                    assertTrue(asset instanceof Asset);

                }else {

                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorMessage());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrorCode());
                    Log.w(TAG, "----------Test--Asset--03--Error---------" + error.getErrors());

                }

            }
        });


    }

}
