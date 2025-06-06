package com.contentstack.sdk;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GlobalFieldTestCase {
    private final String TAG = GlobalFieldTestCase.class.getSimpleName();
//    private static String globalFieldUid = BuildConfig.GlobalFieldUID;
    private static Stack stack;
    private static CountDownLatch latch;


    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);
    }

    @Before
    public void setUp() {
        latch = new CountDownLatch(1);
    }

    @Test
    public void test_fetchGlobalField() throws Exception {
      GlobalField globalField = stack.globalField("specific_gf_uid");
        globalField.findAll(new GlobalFieldsResultCallback() {
            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                if (error == null) {
                    JSONArray result = globalFieldsModel.getResultArray();
                    System.out.println("✅ Global Fields Response: " + result);
                } else {
                    System.out.println("❌ Error: " + error.getErrorMessage());
                }
                latch.countDown(); // Signal that response arrived
            }
        });
    }


    @Test
    public void test_fetchGlobalField_withInvalidUid() {
        GlobalField globalField = new GlobalField(""); // empty UID
        globalField.setStackInstance(stack);

        globalField.fetch(new GlobalFieldsResultCallback() {

            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                if(error != null){
                    System.out.println("❌ Error: " + error.getErrorMessage());
                }
            }
        });
    }

    @Test
    public void test_findAllGlobalFields_success() throws InterruptedException {
        GlobalField globalField = stack.globalField();
        globalField.findAll(new GlobalFieldsResultCallback() {

            @Override
            public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
                if(error == null){
                    Assertions.assertNotNull(globalFieldsModel.getResultArray());
                } else {
                    System.out.println("❌ Error: " + error.getErrorMessage());
                }
            }
        });

    }
}
