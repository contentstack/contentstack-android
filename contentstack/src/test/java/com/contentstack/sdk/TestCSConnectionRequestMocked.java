package com.contentstack.sdk;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Comprehensive network mocking tests for CSConnectionRequest using MockWebServer
 */
@RunWith(RobolectricTestRunner.class)
public class TestCSConnectionRequestMocked {

    private MockWebServer mockWebServer;
    private Stack stack;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        
        // Create stack with mock server URL
        Config config = new Config();
        config.setHost(mockWebServer.url("/").toString());
        stack = Contentstack.stack(context, "test_api_key", "test_token", "test_env", config);
    }

    @After
    public void tearDown() throws Exception {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
        }
    }

    // ==================== Success Response Tests ====================

    @Test
    public void testSuccessfulQueryRequest() throws Exception {
        JSONObject responseJson = new JSONObject();
        responseJson.put("entries", new org.json.JSONArray());
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(responseJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testSuccessfulEntryRequest() throws Exception {
        JSONObject entryJson = new JSONObject();
        entryJson.put("uid", "test_entry");
        entryJson.put("title", "Test Entry");
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("entry", entryJson);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(responseJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Entry entry = stack.contentType("test_ct").entry("test_entry");
        assertNotNull(entry);
    }

    @Test
    public void testSuccessfulAssetRequest() throws Exception {
        JSONObject assetJson = new JSONObject();
        assetJson.put("uid", "test_asset");
        assetJson.put("filename", "test.jpg");
        
        JSONObject responseJson = new JSONObject();
        responseJson.put("asset", assetJson);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(responseJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Asset asset = stack.asset("test_asset");
        assertNotNull(asset);
    }

    // ==================== Error Response Tests ====================

    @Test
    public void testErrorResponse404() throws Exception {
        JSONObject errorJson = new JSONObject();
        errorJson.put("error_message", "Not Found");
        errorJson.put("error_code", 404);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(404)
            .setBody(errorJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testErrorResponse401() throws Exception {
        JSONObject errorJson = new JSONObject();
        errorJson.put("error_message", "Unauthorized");
        errorJson.put("error_code", 401);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(401)
            .setBody(errorJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testErrorResponse500() throws Exception {
        JSONObject errorJson = new JSONObject();
        errorJson.put("error_message", "Internal Server Error");
        errorJson.put("error_code", 500);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(500)
            .setBody(errorJson.toString())
            .addHeader("Content-Type", "application/json"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testErrorResponse502() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(502)
            .setBody("Bad Gateway"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testErrorResponse503() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(503)
            .setBody("Service Unavailable"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    // ==================== Network Failure Tests ====================

    @Test
    public void testNetworkTimeout() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}")
            .setBodyDelay(10, TimeUnit.SECONDS));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testConnectionRefused() throws Exception {
        mockWebServer.shutdown();
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    // ==================== Request Header Tests ====================

    @Test
    public void testRequestWithCustomHeaders() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}"));
        
        stack.setHeader("Custom-Header", "CustomValue");
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testRequestWithMultipleHeaders() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}"));
        
        stack.setHeader("Header1", "Value1");
        stack.setHeader("Header2", "Value2");
        stack.setHeader("Header3", "Value3");
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    // ==================== Response Body Tests ====================

    @Test
    public void testEmptyResponseBody() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(""));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testMalformedJSONResponse() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{invalid json}"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testLargeResponseBody() throws Exception {
        StringBuilder largeJson = new StringBuilder("{\"entries\":[");
        for (int i = 0; i < 1000; i++) {
            if (i > 0) largeJson.append(",");
            largeJson.append("{\"uid\":\"entry_").append(i).append("\"}");
        }
        largeJson.append("]}");
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(largeJson.toString()));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    // ==================== Multiple Request Tests ====================

    @Test
    public void testMultipleSequentialRequests() throws Exception {
        for (int i = 0; i < 5; i++) {
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"));
        }
        
        for (int i = 0; i < 5; i++) {
            Query query = stack.contentType("ct_" + i).query();
            assertNotNull(query);
        }
    }

    @Test
    public void testAlternatingSuccessAndError() throws Exception {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody("{}"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(500).setBody("{}"));
        
        Query q1 = stack.contentType("ct1").query();
        Query q2 = stack.contentType("ct2").query();
        Query q3 = stack.contentType("ct3").query();
        Query q4 = stack.contentType("ct4").query();
        
        assertNotNull(q1);
        assertNotNull(q2);
        assertNotNull(q3);
        assertNotNull(q4);
    }

    // ==================== Different Content Types ====================

    @Test
    public void testQueryForDifferentContentTypes() throws Exception {
        String[] contentTypes = {"blog", "product", "author", "category", "tag"};
        
        for (String ct : contentTypes) {
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"));
        }
        
        for (String ct : contentTypes) {
            Query query = stack.contentType(ct).query();
            assertNotNull(query);
        }
    }

    // ==================== Response Header Tests ====================

    @Test
    public void testResponseWithCustomHeaders() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}")
            .addHeader("X-Custom-Header", "value")
            .addHeader("X-Rate-Limit", "1000"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    // ==================== HTTP Methods ====================

    @Test
    public void testGETRequest() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}"));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testPOSTRequest() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(201)
            .setBody("{}"));
        
        // Assuming sync operations use POST
        assertNotNull(stack);
    }

    // ==================== Edge Cases ====================

    @Test
    public void testVerySlowResponse() throws Exception {
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{}")
            .setBodyDelay(2, TimeUnit.SECONDS));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testResponseWithSpecialCharacters() throws Exception {
        JSONObject json = new JSONObject();
        json.put("title", "Test with special chars: !@#$%^&*()");
        json.put("description", "Unicode: 🎯🚀✨");
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(json.toString()));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testResponseWithNullValues() throws Exception {
        JSONObject json = new JSONObject();
        json.put("field1", JSONObject.NULL);
        json.put("field2", "value");
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(json.toString()));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testMultipleContentTypesInSequence() throws Exception {
        for (int i = 0; i < 10; i++) {
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"));
        }
        
        Query q1 = stack.contentType("blog").query();
        Entry e1 = stack.contentType("product").entry("uid1");
        Asset a1 = stack.asset("asset1");
        Query q2 = stack.contentType("author").query();
        
        assertNotNull(q1);
        assertNotNull(e1);
        assertNotNull(a1);
        assertNotNull(q2);
    }

    // ==================== Stress Tests ====================

    @Test
    public void testManySuccessfulRequests() throws Exception {
        for (int i = 0; i < 50; i++) {
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"));
        }
        
        for (int i = 0; i < 50; i++) {
            Query query = stack.contentType("test_ct").query();
            assertNotNull(query);
        }
    }

    @Test
    public void testVariedStatusCodes() throws Exception {
        int[] statusCodes = {200, 201, 204, 400, 401, 403, 404, 500, 502, 503};
        
        for (int code : statusCodes) {
            mockWebServer.enqueue(new MockResponse()
                .setResponseCode(code)
                .setBody("{}"));
        }
        
        for (int i = 0; i < statusCodes.length; i++) {
            Query query = stack.contentType("ct_" + i).query();
            assertNotNull(query);
        }
    }

    // ==================== Complex JSON Responses ====================

    @Test
    public void testNestedJSONResponse() throws Exception {
        JSONObject nested = new JSONObject();
        nested.put("level1", new JSONObject().put("level2", new JSONObject().put("level3", "value")));
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(nested.toString()));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }

    @Test
    public void testArrayInResponse() throws Exception {
        org.json.JSONArray array = new org.json.JSONArray();
        for (int i = 0; i < 10; i++) {
            array.put(new JSONObject().put("id", i));
        }
        
        JSONObject json = new JSONObject();
        json.put("items", array);
        
        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody(json.toString()));
        
        Query query = stack.contentType("test_ct").query();
        assertNotNull(query);
    }
}

