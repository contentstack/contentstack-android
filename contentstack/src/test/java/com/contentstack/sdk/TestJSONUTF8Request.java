package com.contentstack.sdk;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import org.json.JSONObject;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class TestJSONUTF8Request {

    // Subclass with a public wrapper
    private static class TestJSONUTF8RequestImpl extends JSONUTF8Request {
        TestJSONUTF8RequestImpl() {
            super(
                    0,
                    "http://example.com",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) { }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(com.android.volley.VolleyError error) { }
                    }
            );
        }

        // Public wrapper to access the protected method
        public Response<JSONObject> callParse(NetworkResponse response) {
            return super.parseNetworkResponse(response);
        }
    }

    @Test
    public void testParseNetworkResponse_validJson() throws Exception {
        JSONObject original = new JSONObject();
        original.put("key", "value");

        byte[] data = original.toString().getBytes(Charset.forName("UTF-8"));
        NetworkResponse networkResponse = new NetworkResponse(data);

        TestJSONUTF8RequestImpl request = new TestJSONUTF8RequestImpl();
        Response<JSONObject> response = request.callParse(networkResponse);

        assertNotNull(response);
        assertNull(response.error);
        assertNotNull(response.result);
        assertEquals("value", response.result.getString("key"));
    }

    @Test
    public void testParseNetworkResponse_invalidJson() {
        byte[] data = "not-json".getBytes(Charset.forName("UTF-8"));
        NetworkResponse networkResponse = new NetworkResponse(data);

        TestJSONUTF8RequestImpl request = new TestJSONUTF8RequestImpl();
        Response<JSONObject> response = request.callParse(networkResponse);

        assertNotNull(response);
        assertNotNull(response.error);
        assertTrue(response.error instanceof ParseError);
    }
}
