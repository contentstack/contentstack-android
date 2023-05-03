package com.contentstack.sdk;

import static org.junit.Assert.assertEquals;

import android.util.ArrayMap;
import android.util.Log;

import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void defaultTest() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void stack() {
        Stack stack = new Stack("");
        ArrayMap<String, String> headers = new ArrayMap<>();
        headers.put("api_key", "");
        headers.put("access_token", "");
        headers.put("environment", "");
        stack.setHeaders(headers);
        stack.sync(new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                Log.d("TAG", syncStack.getItems().get(0).toString());
            }
        });
        assertEquals(4, 2 + 2);
    }

}