package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestGlobalFieldsResultCallback {

    private static class TestGlobalFieldsCallback extends GlobalFieldsResultCallback {

        GlobalFieldsModel lastModel;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
            onCompletionCalled = true;
            lastModel = globalFieldsModel;
            lastError = error;
        }

        @Override
        void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithModelAndNullError() {
        TestGlobalFieldsCallback callback = new TestGlobalFieldsCallback();

        // we don't need a real GlobalFieldsModel, just a non-null reference;
        // but if it's hard to construct, we can pass null and test behavior.
        GlobalFieldsModel model = null;

        callback.onRequestFinish(model);

        assertTrue(callback.onCompletionCalled);
        assertEquals(model, callback.lastModel);
        assertNull(callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithErrorAndNullModel() {
        TestGlobalFieldsCallback callback = new TestGlobalFieldsCallback();

        Error error = new Error(); // your SDK Error (no-arg ctor)

        callback.onRequestFail(ResponseType.NETWORK, error); // use any valid ResponseType

        assertTrue(callback.onCompletionCalled);
        assertNull(callback.lastModel);
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysOverrideIsCallable() {
        TestGlobalFieldsCallback callback = new TestGlobalFieldsCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
    }
}
