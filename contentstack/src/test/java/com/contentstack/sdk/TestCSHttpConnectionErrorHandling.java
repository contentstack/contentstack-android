package com.contentstack.sdk;

import com.android.volley.VolleyError;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import com.contentstack.sdk.CSHttpConnection;
import com.contentstack.sdk.IRequestModelHTTP;
import com.contentstack.sdk.ResultCallBack;
import com.contentstack.sdk.SDKConstant;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class TestCSHttpConnectionErrorHandling {

    private CSHttpConnection connection;
    private IRequestModelHTTP mockRequestModel;

    @Before
    public void setUp() {
        mockRequestModel = new IRequestModelHTTP() {
            @Override
            public void sendRequest() {
            }

            @Override
            public void onRequestFinished(CSHttpConnection request) {
            }

            @Override
            public void onRequestFailed(JSONObject response, int statusCode, ResultCallBack callBackObject) {
            }
        };

        connection = new CSHttpConnection("https://example.com", mockRequestModel);
    }

    private void invokeGenerateBuiltError(VolleyError error) throws Exception {
        Method method = CSHttpConnection.class.getDeclaredMethod("generateBuiltError", VolleyError.class);
        method.setAccessible(true);
        method.invoke(connection, error);
    }

    @Test
    public void testGenerateBuiltErrorWithNullError() throws Exception {
        invokeGenerateBuiltError(null);

        JSONObject response = connection.getResponse();
        assertNotNull(response);
        assertTrue(response.has("error_message"));
        assertEquals(SDKConstant.ERROR_MESSAGE_DEFAULT, response.optString("error_message"));
    }

    @Test
    public void testGenerateBuiltErrorWithCustomMessage() throws Exception {
        VolleyError error = new VolleyError("Custom error message");
        invokeGenerateBuiltError(error);

        JSONObject response = connection.getResponse();
        assertNotNull(response);
        assertTrue(response.has("error_message"));
        assertEquals("Custom error message", response.optString("error_message"));
        assertTrue(response.has("errors"));
    }

    @Test
    public void testGenerateBuiltErrorWithKnownType() throws Exception {
        VolleyError noConnectionError = new VolleyError("NoConnectionError");
        invokeGenerateBuiltError(noConnectionError);

        JSONObject response = connection.getResponse();
        assertNotNull(response);

        VolleyError authFailureError = new VolleyError("AuthFailureError");
        invokeGenerateBuiltError(authFailureError);

        response = connection.getResponse();

        VolleyError networkError = new VolleyError("NetworkError");
        invokeGenerateBuiltError(networkError);

        response = connection.getResponse();
        assertNotNull(response);
        assertEquals("NetworkError", response.optString("error_message"));
    }
}
