package com.contentstack.sdk;

import android.content.Context;
import android.util.ArrayMap;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for CSHttpConnection class.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestCSHttpConnection {

    private Context context;
    private Stack stack;
    private IRequestModelHTTP mockRequestModel;
    private CSHttpConnection connection;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env");
        
        mockRequestModel = new IRequestModelHTTP() {
            @Override
            public void onRequestFailed(JSONObject error, int statusCode, ResultCallBack callBackObject) {
                // Mock implementation
            }

            @Override
            public void onRequestFinished(CSHttpConnection request) {
                // Mock implementation
            }

            @Override
            public void sendRequest() {
                // Mock implementation
            }
        };
        
        connection = new CSHttpConnection("https://cdn.contentstack.io/v3/content_types", mockRequestModel);
    }

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testConstructorWithValidParams() {
        CSHttpConnection conn = new CSHttpConnection("https://test.com/api", mockRequestModel);
        assertNotNull(conn);
    }

    @Test
    public void testConstructorWithDifferentUrls() {
        CSHttpConnection conn1 = new CSHttpConnection("https://cdn.contentstack.io/v3/entries", mockRequestModel);
        CSHttpConnection conn2 = new CSHttpConnection("https://cdn.contentstack.io/v3/assets", mockRequestModel);
        
        assertNotNull(conn1);
        assertNotNull(conn2);
        assertNotSame(conn1, conn2);
    }

    // ========== CONTROLLER TESTS ==========

    @Test
    public void testSetController() {
        connection.setController("QUERY");
        assertNotNull(connection);
    }

    @Test
    public void testGetController() {
        connection.setController("ENTRY");
        String controller = connection.getController();
        
        assertNotNull(controller);
        assertEquals("ENTRY", controller);
    }

    @Test
    public void testSetControllerWithNull() {
        connection.setController(null);
        assertNull(connection.getController());
    }

    @Test
    public void testSetControllerWithEmptyString() {
        connection.setController("");
        assertEquals("", connection.getController());
    }

    @Test
    public void testSetControllerMultipleTimes() {
        connection.setController("QUERY");
        assertEquals("QUERY", connection.getController());
        
        connection.setController("ENTRY");
        assertEquals("ENTRY", connection.getController());
        
        connection.setController("ASSET");
        assertEquals("ASSET", connection.getController());
    }

    // ========== HEADERS TESTS ==========

    @Test
    public void testSetHeaders() {
        ArrayMap<String, Object> headers = new ArrayMap<>();
        headers.put("api_key", "test_key");
        headers.put("access_token", "test_token");
        
        connection.setHeaders(headers);
        assertNotNull(connection);
    }

    @Test
    public void testGetHeaders() {
        ArrayMap<String, Object> headers = new ArrayMap<>();
        headers.put("api_key", "test_key");
        headers.put("environment", "production");
        
        connection.setHeaders(headers);
        ArrayMap result = connection.getHeaders();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testSetHeadersWithNull() {
        connection.setHeaders(null);
        assertNull(connection.getHeaders());
    }

    @Test
    public void testSetHeadersWithEmptyMap() {
        ArrayMap<String, Object> emptyHeaders = new ArrayMap<>();
        connection.setHeaders(emptyHeaders);
        
        ArrayMap result = connection.getHeaders();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSetHeadersWithMultipleValues() {
        ArrayMap<String, Object> headers = new ArrayMap<>();
        headers.put("api_key", "key1");
        headers.put("access_token", "token1");
        headers.put("environment", "env1");
        headers.put("custom_header", "custom_value");
        
        connection.setHeaders(headers);
        ArrayMap result = connection.getHeaders();
        
        assertNotNull(result);
        assertEquals(4, result.size());
    }

    // ========== INFO TESTS ==========

    @Test
    public void testSetInfo() {
        connection.setInfo("QUERY");
        assertNotNull(connection);
    }

    @Test
    public void testGetInfo() {
        connection.setInfo("ENTRY");
        String info = connection.getInfo();
        
        assertNotNull(info);
        assertEquals("ENTRY", info);
    }

    @Test
    public void testSetInfoWithNull() {
        connection.setInfo(null);
        assertNull(connection.getInfo());
    }

    @Test
    public void testSetInfoWithEmptyString() {
        connection.setInfo("");
        assertEquals("", connection.getInfo());
    }

    // ========== FORM PARAMS TESTS ==========

    @Test
    public void testSetFormParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("include_count", true);
        params.put("limit", 10);
        
        connection.setFormParams(params);
        assertNotNull(connection);
    }

    @Test
    public void testGetFormParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("limit", 10);
        params.put("skip", 0);
        
        connection.setFormParams(params);
        HashMap<String, Object> result = connection.getFormParams();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testSetFormParamsWithNull() {
        connection.setFormParams(null);
        assertNull(connection.getFormParams());
    }

    @Test
    public void testSetFormParamsWithEmptyMap() {
        HashMap<String, Object> emptyParams = new HashMap<>();
        connection.setFormParams(emptyParams);
        
        HashMap<String, Object> result = connection.getFormParams();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ========== FORM PARAMS POST TESTS ==========

    @Test
    public void testSetFormParamsPOST() throws Exception {
        JSONObject json = new JSONObject();
        json.put("key", "value");
        
        connection.setFormParamsPOST(json);
        assertNotNull(connection);
    }

    @Test
    public void testSetFormParamsPOSTWithNull() {
        connection.setFormParamsPOST(null);
        assertNotNull(connection);
    }

    @Test
    public void testSetFormParamsPOSTWithEmptyJSON() throws Exception {
        JSONObject emptyJson = new JSONObject();
        connection.setFormParamsPOST(emptyJson);
        assertNotNull(connection);
    }

    @Test
    public void testSetFormParamsPOSTWithComplexJSON() throws Exception {
        JSONObject json = new JSONObject();
        json.put("string_field", "value");
        json.put("int_field", 42);
        json.put("boolean_field", true);
        
        JSONObject nested = new JSONObject();
        nested.put("nested_key", "nested_value");
        json.put("nested_object", nested);
        
        connection.setFormParamsPOST(json);
        assertNotNull(connection);
    }

    // ========== CALLBACK TESTS ==========

    @Test
    public void testSetCallBackObject() {
        ResultCallBack callback = new ResultCallBack() {
            @Override
            public void onRequestFail(ResponseType responseType, Error error) {
                // Test callback
            }

            @Override
            public void always() {
                // Test callback
            }
        };
        
        connection.setCallBackObject(callback);
        assertNotNull(connection);
    }

    @Test
    public void testGetCallBackObject() {
        ResultCallBack callback = new ResultCallBack() {
            @Override
            public void onRequestFail(ResponseType responseType, Error error) {}
            
            @Override
            public void always() {}
        };
        
        connection.setCallBackObject(callback);
        ResultCallBack result = connection.getCallBackObject();
        
        assertNotNull(result);
        assertSame(callback, result);
    }

    @Test
    public void testSetCallBackObjectWithNull() {
        connection.setCallBackObject(null);
        assertNull(connection.getCallBackObject());
    }

    // ========== REQUEST METHOD TESTS ==========

    @Test
    public void testSetRequestMethod() {
        connection.setRequestMethod(SDKConstant.RequestMethod.GET);
        assertNotNull(connection);
    }

    @Test
    public void testGetRequestMethod() {
        connection.setRequestMethod(SDKConstant.RequestMethod.POST);
        SDKConstant.RequestMethod method = connection.getRequestMethod();
        
        assertNotNull(method);
        assertEquals(SDKConstant.RequestMethod.POST, method);
    }

    @Test
    public void testSetRequestMethodGET() {
        connection.setRequestMethod(SDKConstant.RequestMethod.GET);
        assertEquals(SDKConstant.RequestMethod.GET, connection.getRequestMethod());
    }

    @Test
    public void testSetRequestMethodPOST() {
        connection.setRequestMethod(SDKConstant.RequestMethod.POST);
        assertEquals(SDKConstant.RequestMethod.POST, connection.getRequestMethod());
    }

    @Test
    public void testSetRequestMethodPUT() {
        connection.setRequestMethod(SDKConstant.RequestMethod.PUT);
        assertEquals(SDKConstant.RequestMethod.PUT, connection.getRequestMethod());
    }

    @Test
    public void testSetRequestMethodDELETE() {
        connection.setRequestMethod(SDKConstant.RequestMethod.DELETE);
        assertEquals(SDKConstant.RequestMethod.DELETE, connection.getRequestMethod());
    }

    // ========== TREAT DUPLICATE KEYS TESTS ==========

    @Test
    public void testSetTreatDuplicateKeysAsArrayItems() {
        connection.setTreatDuplicateKeysAsArrayItems(true);
        assertNotNull(connection);
    }

    @Test
    public void testGetTreatDuplicateKeysAsArrayItems() {
        connection.setTreatDuplicateKeysAsArrayItems(true);
        assertTrue(connection.getTreatDuplicateKeysAsArrayItems());
        
        connection.setTreatDuplicateKeysAsArrayItems(false);
        assertFalse(connection.getTreatDuplicateKeysAsArrayItems());
    }

    // ========== RESPONSE TESTS ==========

    @Test
    public void testGetResponseBeforeSend() {
        JSONObject response = connection.getResponse();
        // Response should be null before send
        assertNull(response);
    }

    // ========== COMPLEX CONFIGURATION TESTS ==========

    @Test
    public void testCompleteConfiguration() throws Exception {
        ArrayMap<String, Object> headers = new ArrayMap<>();
        headers.put("api_key", "key");
        headers.put("access_token", "token");
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("limit", 10);
        params.put("skip", 0);
        
        JSONObject json = new JSONObject();
        json.put("query", "test");
        
        ResultCallBack callback = new ResultCallBack() {
            @Override
            public void onRequestFail(ResponseType responseType, Error error) {}
            
            @Override
            public void always() {}
        };
        
        connection.setController("QUERY");
        connection.setHeaders(headers);
        connection.setInfo("QUERY");
        connection.setFormParams(params);
        connection.setFormParamsPOST(json);
        connection.setCallBackObject(callback);
        connection.setRequestMethod(SDKConstant.RequestMethod.GET);
        connection.setTreatDuplicateKeysAsArrayItems(true);
        
        assertNotNull(connection);
        assertEquals("QUERY", connection.getController());
        assertEquals("QUERY", connection.getInfo());
        assertEquals(SDKConstant.RequestMethod.GET, connection.getRequestMethod());
        assertTrue(connection.getTreatDuplicateKeysAsArrayItems());
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testMultipleInstances() {
        CSHttpConnection conn1 = new CSHttpConnection("url1", mockRequestModel);
        CSHttpConnection conn2 = new CSHttpConnection("url2", mockRequestModel);
        CSHttpConnection conn3 = new CSHttpConnection("url3", mockRequestModel);
        
        assertNotNull(conn1);
        assertNotNull(conn2);
        assertNotNull(conn3);
        
        assertNotSame(conn1, conn2);
        assertNotSame(conn2, conn3);
        assertNotSame(conn1, conn3);
    }

    @Test
    public void testSetFormParamsGETWithNull() {
        String result = connection.setFormParamsGET(null);
        assertNull(result);
    }

    @Test
    public void testSetFormParamsGETWithEmptyMap() {
        HashMap<String, Object> emptyParams = new HashMap<>();
        String result = connection.setFormParamsGET(emptyParams);
        assertNull(result);
    }

    @Test
    public void testSetFormParamsGETWithValidParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("limit", "10");
        params.put("skip", "0");
        
        connection.setInfo("OTHER"); // Not QUERY or ENTRY
        String result = connection.setFormParamsGET(params);
        
        assertNotNull(result);
        assertTrue(result.startsWith("?"));
        assertTrue(result.contains("limit=10"));
    }

    @Test
    public void testStateIndependence() {
        CSHttpConnection conn1 = new CSHttpConnection("url1", mockRequestModel);
        CSHttpConnection conn2 = new CSHttpConnection("url2", mockRequestModel);
        
        conn1.setController("QUERY");
        conn2.setController("ENTRY");
        
        assertEquals("QUERY", conn1.getController());
        assertEquals("ENTRY", conn2.getController());
    }
}

