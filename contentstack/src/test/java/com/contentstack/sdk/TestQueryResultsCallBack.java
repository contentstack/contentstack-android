package com.contentstack.sdk;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestQueryResultsCallBack {

    private static class TestQueryResultsCallback extends QueryResultsCallBack {

        ResponseType lastResponseType;
        QueryResult lastQueryResult;
        Error lastError;
        boolean onCompletionCalled = false;
        boolean alwaysCalled = false;

        @Override
        public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
            onCompletionCalled = true;
            lastResponseType = responseType;
            lastQueryResult = queryresult;
            lastError = error;
        }

        @Override
        void always() {
            alwaysCalled = true;
        }
    }

    @Test
    public void testOnRequestFinishCallsOnCompletionWithQueryResultAndNullError() {
        TestQueryResultsCallback callback = new TestQueryResultsCallback();

        // Use any valid ResponseType constant from your SDK
        ResponseType responseType = ResponseType.NETWORK; // change if needed

        // We don't need a real QueryResult instance here; we just check behavior
        callback.onRequestFinish(responseType, null);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertNull(callback.lastQueryResult);  // we passed null
        assertNull(callback.lastError);        // onRequestFinish must send null error
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testOnRequestFailCallsOnCompletionWithErrorAndNullQueryResult() {
        TestQueryResultsCallback callback = new TestQueryResultsCallback();

        ResponseType responseType = ResponseType.NETWORK; // change if needed
        Error error = new Error();                        // SDK Error with no-arg ctor

        callback.onRequestFail(responseType, error);

        assertTrue(callback.onCompletionCalled);
        assertEquals(responseType, callback.lastResponseType);
        assertNull(callback.lastQueryResult);             // must be null on failure
        assertEquals(error, callback.lastError);
        assertFalse(callback.alwaysCalled);
    }

    @Test
    public void testAlwaysOverrideIsCallable() {
        TestQueryResultsCallback callback = new TestQueryResultsCallback();

        callback.always();

        assertTrue(callback.alwaysCalled);
    }
}
