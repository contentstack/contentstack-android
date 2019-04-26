package com.contentstack.sdk.allRegualar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.contentstack.sdk.BuildConfig;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.EntryResultCallBack;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Shailesh Mishra.
 *
 * Contentstack pvt Ltd
 *
 */

@RunWith(AndroidJUnit4.class)
public class EntryTestCase  {

    private static final String TAG = "TESTCASE";

    CountDownLatch latch;
    Stack stack;
    Context appContext;
    String[] uidArray;



    public EntryTestCase() throws Exception {

        appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost(BuildConfig.base_url);

        stack = Contentstack.stack(appContext,
                BuildConfig.default_api_key,
                BuildConfig.default_access_token,
                BuildConfig.default_env,config);

        uidArray = new String[]{"blte88d9bec040e7c7c", "bltdf783472903c3e21"};
        latch = new CountDownLatch(1);
    }




    @Test
    public void test_00_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        assertEquals(entry.getString("title"), "laptop");
    }



    @Test
    public void test_01_only_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");
        entry.only(new String[]{"price"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        boolean isContains = false;
        if (entry.toJSON().has("title")) {
            isContains = true;
        }
        assertFalse(isContains);
    }




    @Test
    public void test_02_except_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");
        entry.except(new String[]{"title"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        boolean isContains = false;
        if (entry.toJSON().has("title")) {
            isContains = true;
        }
        assertFalse(isContains);
    }




    @Test
    public void test_03_includeReference_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");
        entry.includeReference("category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        JSONArray categoryArray = (JSONArray) entry.getJSONArray("category");
        try {
            assertTrue(categoryArray.get(0) instanceof JSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test_04_includeReferenceOnly_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("title");

        entry.onlyWithReferenceUid(strings, "category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();

        try {
            JSONArray categoryArray = (JSONArray) entry.getJSONArray("category");
            JSONObject jsonObject = categoryArray.getJSONObject(0);
            boolean isContains = false;
            if (jsonObject.has("title")) {
                isContains = true;
            }
            assertTrue(isContains);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test_05_includeReferenceExcept_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("product").entry("blt7801c5d40cbbe979");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("title");

        entry.exceptWithReferenceUid(strings, "category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        try {
            JSONArray categoryArray = (JSONArray) entry.getJSONArray("category");
            JSONObject jsonObject = categoryArray.getJSONObject(0);
            boolean isContains = false;
            if (jsonObject.has("title")) {
                isContains = true;
            }
            assertFalse(isContains);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void test_06_getMarkdown_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        String htmlString = entry.getHtmlText("markdown");
        //assertNotNull(htmlString);
    }



    @Test
    public void test_07_getMarkdown_fetch() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        ArrayList<String> htmlString = entry.getMultipleHtmlText("markdown_multiple");
        assertEquals(2, htmlString.size());

    }



    @Test
    public void test_08_get() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        String title = (String) entry.get("title");
        assertNotNull(title);
    }

    /************************************************************/



    @Test
    public void test_11_addParam() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry("blt3b0aaebf6f1c3762");
        entry.addParam("key", "some_value");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {

                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
        String title = (String) entry.get("title");
        assertNotNull(title);
    }
}
