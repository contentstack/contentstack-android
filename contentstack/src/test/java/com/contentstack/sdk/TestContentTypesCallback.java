package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for ContentTypesCallback.
 */
public class TestContentTypesCallback {

    /**
     * Simple concrete implementation for testing.
     */
    private static class TestCallback extends ContentTypesCallback {

        ContentTypesModel lastContentTypesModel;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
            onCompletionCalled = true;
            lastContentTypesModel = contentTypesModel;
            lastError = error;
        }

        @Override
        public void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithModelAndNullError() {
        TestCallback callback = new TestCallback();
        // Assuming ContentTypesModel has a public no-arg constructor
        ContentTypesModel model = new ContentTypesModel();

        callback.onRequestFinish(model);

        assertTrue(callback.onCompletionCalled);
        assertEquals(model, callback.lastContentTypesModel);
        assertNull(callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithErrorAndNullModel() {
        TestCallback callback = new TestCallback();
        Error error = new Error(); // SDK Error with no-arg ctor

        callback.onRequestFail(ResponseType.NETWORK, error);

        assertTrue(callback.onCompletionCalled);
        assertNull(callback.lastContentTypesModel);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysCanBeOverridden() {
        TestCallback callback = new TestCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
        assertFalse(callback.onCompletionCalled);
        assertNull(callback.lastContentTypesModel);
        assertNull(callback.lastError);
    }
}
