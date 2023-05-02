package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.ArrayMap;
import android.util.Log;

public class ExampleUnitTest {
    @Test
    public void defaultTest() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void stack() {
        Stack stack = new Stack("blte00eab6e0747e802");
        ArrayMap<String, String> headers = new ArrayMap<>();
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