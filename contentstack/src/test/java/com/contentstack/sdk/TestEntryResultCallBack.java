package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for EntryResultCallBack.
 */
public class TestEntryResultCallBack {

    @Test
    public void testOnRequestFinishCallsOnCompletionWithNullError() {
        class TestCallback extends EntryResultCallBack {
            boolean finished = false;
            ResponseType lastResponse;
            Error lastError;

            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                finished = true;
                lastResponse = responseType;
                lastError = error;
            }
        }

        TestCallback callback = new TestCallback();

        ResponseType responseType = ResponseType.NETWORK; // use SDK's ResponseType
        callback.onRequestFinish(responseType);

        assertTrue(callback.finished);
        assertEquals(responseType, callback.lastResponse);
        assertNull(callback.lastError);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithError() {
        class TestCallback extends EntryResultCallBack {
            boolean finished = false;
            ResponseType lastResponse;
            Error lastError;

            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                finished = true;
                lastResponse = responseType;
                lastError = error;
            }
        }

        TestCallback callback = new TestCallback();

        ResponseType responseType = ResponseType.NETWORK;
        Error error = new Error();
        callback.onRequestFail(responseType, error);

        assertTrue(callback.finished);
        assertEquals(responseType, callback.lastResponse);
        assertEquals(error, callback.lastError);
    }

    @Test
    public void testAlwaysCallable() {
        class TestCallback extends EntryResultCallBack {
            boolean alwaysCalled = false;

            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                // do nothing
            }

            @Override
            void always() {
                alwaysCalled = true;
            }
        }

        TestCallback callback = new TestCallback();
        callback.always();
        assertTrue(callback.alwaysCalled);
    }
}
