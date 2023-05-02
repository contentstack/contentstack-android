package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("contentstack.sdk.test", appContext.getPackageName());
    }

    @Test
    public void initSDK() throws Exception {
        Context ctx = ApplicationProvider.getApplicationContext();
        Stack stack = Contentstack.stack(ctx, "", "", "");
        stack.sync(new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

            }
        });
        assertEquals(4, 2 + 2);
    }
}