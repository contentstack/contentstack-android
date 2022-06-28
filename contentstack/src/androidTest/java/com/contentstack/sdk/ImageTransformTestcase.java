package com.contentstack.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.*;
import org.junit.runner.JUnitCore;

import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;


public class ImageTransformTestcase {

    static final String TAG = ImageTransformTestcase.class.getSimpleName();
    private static CountDownLatch latch;
    private static Stack stack;
    private final LinkedHashMap<String, Object> imageParams = new LinkedHashMap<>();
    private final String IMAGE_URL = "https://images.contentstack.io/v3/assets/download";


    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);

        latch = new CountDownLatch(1);
    }

    @Before
    public void setUp() {
        latch = new CountDownLatch(1);
    }


    @Test
    public void test_00_fetchAllImageTransformation() {

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
        if (image_url.contains("?")) {
            String[] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String[] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size() > 0) {
                for (int i = 0; i < imageParams.size(); i++) {
                    String keyValueParis = getAllPairs[i];
                    Log.i(TAG, "pairs:--> " + keyValueParis);
                    ++counter;
                }
            }
        } else {
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }

        if (counter == imageParams.size()) {
            latch.countDown();
            Log.i(TAG, "Testcases Passed");
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }
    }


    @Test
    public void test_01_fetchAllImageTransformation() {

        imageParams.put("auto", "webp");
        imageParams.put("quality", 200);
        imageParams.put("width", 100);
        imageParams.put("height", 50);
        imageParams.put("format", "png");
        imageParams.put("crop", "3:5");

        String image_url = stack.ImageTransform(IMAGE_URL, imageParams);
        int counter = 0;
        /* check url contains "?" */
        if (image_url.contains("?")) {
            String[] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String[] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size() > 0) {
                for (int i = 0; i < imageParams.size(); i++) {
                    String keyValueParis = getAllPairs[i];
                    Log.i(TAG, "pairs:--> " + keyValueParis);
                    ++counter;
                }
            }
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }

        if (counter == imageParams.size()) {
            latch.countDown();
            Log.i(TAG, "Testcases Passed");
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }
    }


    @Test
    public void test_02_fetchAllImageTransformation() {
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
        if (!image_url.equalsIgnoreCase("") && image_url.contains("?")) {
            String[] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String[] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size() > 0) {
                for (int i = 0; i < imageParams.size(); i++) {
                    String keyValueParis = getAllPairs[i];
                    Log.i(TAG, "pairs:--> " + keyValueParis);
                    ++counter;
                }
            }
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }

        if (counter == imageParams.size()) {
            latch.countDown();
            Log.i(TAG, "Testcases Passed");
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }
    }


    @Test
    public void test_03_fetchAllImageTransformation() {


        imageParams.put("trim", "20,20,20,20");
        imageParams.put("disable", "upscale");
        imageParams.put("canvas", "3:5");
        imageParams.put("orient", "l");

        String image_url = stack.ImageTransform(IMAGE_URL, imageParams);
        int counter = 0;
        /* check url contains "?" */
        if (!image_url.equalsIgnoreCase("") && image_url.contains("?")) {
            String[] imgKeys = image_url.split("\\?");
            String rightUrl = imgKeys[1];
            String[] getAllPairs = rightUrl.split("\\&");
            counter = 0;
            if (imageParams.size() > 0) {
                for (int i = 0; i < imageParams.size(); i++) {
                    String keyValueParis = getAllPairs[i];
                    Log.i(TAG, "pairs:--> " + keyValueParis);
                    ++counter;
                }
            }
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }

        if (counter == imageParams.size()) {
            latch.countDown();
            Log.i(TAG, "Testcases Passed");
        } else {
            Log.i(TAG, "Testcases Failed");
            try {
                latch.await();
            } catch (Exception e) {
                Log.i(TAG, "---------------||" + e.toString());
            }
        }
    }
}
