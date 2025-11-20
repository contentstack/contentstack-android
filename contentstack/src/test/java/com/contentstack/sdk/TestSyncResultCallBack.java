package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for SyncResultCallBack base behavior.
 */
public class TestSyncResultCallBack {

    /**
     * Simple concrete implementation for testing.
     */
    private static class TestCallback extends SyncResultCallBack {

        SyncStack lastSyncStack;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(SyncStack syncStack, Error error) {
            onCompletionCalled = true;
            lastSyncStack = syncStack;
            lastError = error;
        }

        @Override
        void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithSyncStackAndNullError() {
        TestCallback callback = new TestCallback();
        // Assuming SyncStack has a public no-arg constructor
        SyncStack syncStack = new SyncStack();

        callback.onRequestFinish(syncStack);

        assertTrue(callback.onCompletionCalled);
        assertEquals(syncStack, callback.lastSyncStack);
        assertNull(callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithErrorAndNullSyncStack() {
        TestCallback callback = new TestCallback();
        Error error = new Error(); // SDK Error with no-arg ctor

        // ✅ use top-level ResponseType, not ResultCallBack.ResponseType
        callback.onRequestFail(ResponseType.NETWORK, error);

        assertTrue(callback.onCompletionCalled);
        assertNull(callback.lastSyncStack);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysCanBeOverridden() {
        TestCallback callback = new TestCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
        assertFalse(callback.onCompletionCalled);
        assertNull(callback.lastSyncStack);
        assertNull(callback.lastError);
    }
}
