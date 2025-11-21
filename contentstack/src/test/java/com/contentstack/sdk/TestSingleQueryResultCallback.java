package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSingleQueryResultCallback {

    private static class TestSingleQueryCallback extends SingleQueryResultCallback {

        ResponseType lastResponseType;
        Entry lastEntry;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(ResponseType responseType, Entry entry, Error error) {
            onCompletionCalled = true;
            lastResponseType = responseType;
            lastEntry = entry;
            lastError = error;
        }

        @Override
        void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithEntryAndNullError() {
        TestSingleQueryCallback callback = new TestSingleQueryCallback();

        // Use any valid ResponseType constant from your SDK
        ResponseType responseType = ResponseType.NETWORK; // change if needed

        // We can't construct Entry, but we only need to verify it's non-null.
        // So we'll just pass null here and assert behavior around that.
        // To still meaningfully test the path, we just check that:
        // - onCompletion is called
        // - responseType is passed correctly
        // - error is null
        callback.onRequestFinish(responseType, null);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        // we passed null, so this should be null
        assertNull(callback.lastEntry);
        assertNull(callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithErrorAndNullEntry() {
        TestSingleQueryCallback callback = new TestSingleQueryCallback();

        ResponseType responseType = ResponseType.NETWORK; // change if needed
        Error error = new Error(); // your SDK Error with no-arg ctor

        callback.onRequestFail(responseType, error);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertNull(callback.lastEntry);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysOverrideIsCallable() {
        TestSingleQueryCallback callback = new TestSingleQueryCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
    }
}
