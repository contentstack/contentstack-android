package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for FetchResultCallback.
 */
public class TestFetchResultCallback {

    private static class TestCallback extends FetchResultCallback {

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
        public void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithNullError() {
        TestCallback callback = new TestCallback();
        ResponseType responseType = ResponseType.NETWORK; // or CACHE, etc.

        callback.onRequestFinish(responseType);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertNull(callback.lastError);           // success => null error
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithError() {
        TestCallback callback = new TestCallback();
        ResponseType responseType = ResponseType.NETWORK;
        Error error = new Error();               // SDK Error has no-arg ctor

        callback.onRequestFail(responseType, error);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysOverrideCallable() {
        TestCallback callback = new TestCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
        // onCompletion should not be touched here
        assertFalse(callback.onCompletionCalled);
        assertNull(callback.lastResponseType);
        assertNull(callback.lastError);
    }
}
