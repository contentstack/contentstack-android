package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentstackResultCallbackTest {

    // Simple concrete implementation for testing
    private static class TestCallback extends ContentstackResultCallback {

        ResponseType lastResponseType;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(ResponseType responseType, Error error) {
            onCompletionCalled = true;
            lastResponseType = responseType;
            lastError = error;
        }

        @Override
        void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithNullError() {
        TestCallback callback = new TestCallback();

        // Use any valid ResponseType constant that exists in your SDK
        // If NETWORK doesn't exist, replace with SUCCESS or any other valid one.
        ResponseType responseType = ResponseType.NETWORK;

        callback.onRequestFinish(responseType);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertNull(callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithError() {
        TestCallback callback = new TestCallback();
        ResponseType responseType = ResponseType.NETWORK;

        // IMPORTANT: this uses the no-arg constructor of your SDK Error class
        Error error = new Error();

        callback.onRequestFail(responseType, error);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysOverrideIsCallable() {
        TestCallback callback = new TestCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
    }
}
