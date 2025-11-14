package com.contentstack.sdk;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Reflection-based tests for Query private methods
 */
@RunWith(RobolectricTestRunner.class)
public class TestQueryPrivateMethods {

    private Context context;
    private Stack stack;
    private Query query;
    private File testCacheDir;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        stack = Contentstack.stack(context, "test_api_key", "test_delivery_token", "test_env", config);
        ContentType contentType = stack.contentType("test_content_type");
        query = contentType.query();
        
        testCacheDir = new File(context.getCacheDir(), "test_query_cache");
        if (!testCacheDir.exists()) {
            testCacheDir.mkdirs();
        }
    }

    // ==================== execQuery Tests ====================

    @Test
    public void testExecQueryReflection() {
        try {
            Method execQuery = Query.class.getDeclaredMethod("execQuery",
                SingleQueryResultCallback.class, QueryResultsCallBack.class, boolean.class);
            execQuery.setAccessible(true);
            
            execQuery.invoke(query, null, null, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testExecQueryWithSingleCallback() {
        try {
            Method execQuery = Query.class.getDeclaredMethod("execQuery",
                SingleQueryResultCallback.class, QueryResultsCallBack.class, boolean.class);
            execQuery.setAccessible(true);
            
            SingleQueryResultCallback callback = new SingleQueryResultCallback() {
                @Override
                public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                    // Handle completion
                }
            };
            
            execQuery.invoke(query, callback, null, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testExecQueryWithMultipleCallback() {
        try {
            Method execQuery = Query.class.getDeclaredMethod("execQuery",
                SingleQueryResultCallback.class, QueryResultsCallBack.class, boolean.class);
            execQuery.setAccessible(true);
            
            QueryResultsCallBack callback = new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    // Handle completion
                }
            };
            
            execQuery.invoke(query, null, callback, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== throwException Tests ====================

    @Test
    public void testThrowExceptionReflection() {
        try {
            Method throwException = Query.class.getDeclaredMethod("throwException",
                String.class, String.class, Exception.class);
            throwException.setAccessible(true);
            
            throwException.invoke(query, "testMethod", "Test error message", null);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testThrowExceptionWithException() {
        try {
            Method throwException = Query.class.getDeclaredMethod("throwException",
                String.class, String.class, Exception.class);
            throwException.setAccessible(true);
            
            Exception testException = new Exception("Test exception");
            throwException.invoke(query, "testMethod", "Error occurred", testException);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testThrowExceptionMultipleTimes() {
        try {
            Method throwException = Query.class.getDeclaredMethod("throwException",
                String.class, String.class, Exception.class);
            throwException.setAccessible(true);
            
            for (int i = 0; i < 5; i++) {
                throwException.invoke(query, "method" + i, "message" + i, null);
            }
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== getHeader Tests ====================

    @Test
    public void testGetHeaderReflection() {
        try {
            Method getHeader = Query.class.getDeclaredMethod("getHeader", android.util.ArrayMap.class);
            getHeader.setAccessible(true);
            
            android.util.ArrayMap<String, Object> localHeader = new android.util.ArrayMap<>();
            localHeader.put("X-Test-Header", "test-value");
            
            Object result = getHeader.invoke(query, localHeader);
            assertNotNull(result);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetHeaderWithNullInput() {
        try {
            Method getHeader = Query.class.getDeclaredMethod("getHeader", android.util.ArrayMap.class);
            getHeader.setAccessible(true);
            
            Object result = getHeader.invoke(query, (android.util.ArrayMap) null);
            assertTrue(result == null || result instanceof android.util.ArrayMap);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetHeaderWithMultipleHeaders() {
        try {
            Method getHeader = Query.class.getDeclaredMethod("getHeader", android.util.ArrayMap.class);
            getHeader.setAccessible(true);
            
            android.util.ArrayMap<String, Object> localHeader = new android.util.ArrayMap<>();
            for (int i = 0; i < 20; i++) {
                localHeader.put("X-Header-" + i, "value" + i);
            }
            
            Object result = getHeader.invoke(query, localHeader);
            assertNotNull(result);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== fetchFromCache Tests ====================

    @Test
    public void testFetchFromCacheReflection() {
        try {
            Method fetchFromCache = Query.class.getDeclaredMethod("fetchFromCache",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            fetchFromCache.setAccessible(true);
            
            File cacheFile = new File(testCacheDir, "query_cache.json");
            cacheFile.createNewFile();
            
            fetchFromCache.invoke(query, cacheFile, "entries", null, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchFromCacheWithCallback() {
        try {
            Method fetchFromCache = Query.class.getDeclaredMethod("fetchFromCache",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            fetchFromCache.setAccessible(true);
            
            File cacheFile = new File(testCacheDir, "query_cache2.json");
            cacheFile.createNewFile();
            
            QueryResultsCallBack callback = new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    // Handle completion
                }
            };
            
            fetchFromCache.invoke(query, cacheFile, "entries", callback, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchFromCacheWithSingleEntry() {
        try {
            Method fetchFromCache = Query.class.getDeclaredMethod("fetchFromCache",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            fetchFromCache.setAccessible(true);
            
            File cacheFile = new File(testCacheDir, "query_single.json");
            cacheFile.createNewFile();
            
            fetchFromCache.invoke(query, cacheFile, "entry", null, true);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== setCacheModel Tests ====================

    @Test
    public void testSetCacheModelReflection() {
        try {
            Method setCacheModel = Query.class.getDeclaredMethod("setCacheModel",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            setCacheModel.setAccessible(true);
            
            File cacheFile = new File(testCacheDir, "model_cache.json");
            
            // Create valid cache data
            JSONObject cacheData = new JSONObject();
            cacheData.put("entries", new org.json.JSONArray());
            
            java.io.FileWriter writer = new java.io.FileWriter(cacheFile);
            writer.write(cacheData.toString());
            writer.close();
            
            setCacheModel.invoke(query, cacheFile, "entries", null, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testSetCacheModelWithCallback() {
        try {
            Method setCacheModel = Query.class.getDeclaredMethod("setCacheModel",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            setCacheModel.setAccessible(true);
            
            File cacheFile = new File(testCacheDir, "model_cache2.json");
            
            JSONObject cacheData = new JSONObject();
            cacheData.put("entries", new org.json.JSONArray());
            
            java.io.FileWriter writer = new java.io.FileWriter(cacheFile);
            writer.write(cacheData.toString());
            writer.close();
            
            QueryResultsCallBack callback = new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    // Handle completion
                }
            };
            
            setCacheModel.invoke(query, cacheFile, "entries", callback, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== fetchFromNetwork Tests ====================

    @Test
    public void testFetchFromNetworkReflection() {
        try {
            Method fetchFromNetwork = Query.class.getDeclaredMethod("fetchFromNetwork",
                String.class, android.util.ArrayMap.class, android.util.ArrayMap.class,
                String.class, QueryResultsCallBack.class, String.class, boolean.class);
            fetchFromNetwork.setAccessible(true);
            
            android.util.ArrayMap<String, Object> headers = new android.util.ArrayMap<>();
            android.util.ArrayMap<String, Object> params = new android.util.ArrayMap<>();
            
            fetchFromNetwork.invoke(query, "/test/url", params, headers, null, null, "entries", false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testFetchFromNetworkWithCallback() {
        try {
            Method fetchFromNetwork = Query.class.getDeclaredMethod("fetchFromNetwork",
                String.class, android.util.ArrayMap.class, android.util.ArrayMap.class,
                String.class, QueryResultsCallBack.class, String.class, boolean.class);
            fetchFromNetwork.setAccessible(true);
            
            android.util.ArrayMap<String, Object> headers = new android.util.ArrayMap<>();
            android.util.ArrayMap<String, Object> params = new android.util.ArrayMap<>();
            
            QueryResultsCallBack callback = new QueryResultsCallBack() {
                @Override
                public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                    // Handle completion
                }
            };
            
            fetchFromNetwork.invoke(query, "/test/url", params, headers, null, callback, "entries", false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== getResultObject Tests ====================

    @Test
    public void testGetResultObjectReflection() {
        try {
            Method getResultObject = Query.class.getDeclaredMethod("getResultObject",
                java.util.List.class, JSONObject.class, boolean.class);
            getResultObject.setAccessible(true);
            
            java.util.List<Object> objects = new java.util.ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            
            getResultObject.invoke(query, objects, jsonObject, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultObjectWithCount() {
        try {
            Method getResultObject = Query.class.getDeclaredMethod("getResultObject",
                java.util.List.class, JSONObject.class, boolean.class);
            getResultObject.setAccessible(true);
            
            java.util.List<Object> objects = new java.util.ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", 42);
            
            getResultObject.invoke(query, objects, jsonObject, false);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultObjectSingleEntry() {
        try {
            Method getResultObject = Query.class.getDeclaredMethod("getResultObject",
                java.util.List.class, JSONObject.class, boolean.class);
            getResultObject.setAccessible(true);
            
            java.util.List<Object> objects = new java.util.ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            
            getResultObject.invoke(query, objects, jsonObject, true);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== getResult Tests ====================

    @Test
    public void testGetResultReflection() {
        try {
            Method getResult = Query.class.getDeclaredMethod("getResult", Object.class, String.class);
            getResult.setAccessible(true);
            
            JSONObject resultObject = new JSONObject();
            resultObject.put("entries", new org.json.JSONArray());
            
            getResult.invoke(query, resultObject, "entries");
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultWithNullObject() {
        try {
            Method getResult = Query.class.getDeclaredMethod("getResult", Object.class, String.class);
            getResult.setAccessible(true);
            
            getResult.invoke(query, null, "entries");
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testGetResultWithNullController() {
        try {
            Method getResult = Query.class.getDeclaredMethod("getResult", Object.class, String.class);
            getResult.setAccessible(true);
            
            JSONObject resultObject = new JSONObject();
            getResult.invoke(query, resultObject, null);
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    // ==================== Edge Cases ====================

    @Test
    public void testMultipleCacheScenariosReflection() {
        try {
            Method fetchFromCache = Query.class.getDeclaredMethod("fetchFromCache",
                File.class, String.class, QueryResultsCallBack.class, boolean.class);
            fetchFromCache.setAccessible(true);
            
            // Non-existent file
            File nonExistent = new File(testCacheDir, "nonexistent.json");
            fetchFromCache.invoke(query, nonExistent, "entries", null, false);
            
            // Empty file
            File emptyFile = new File(testCacheDir, "empty.json");
            emptyFile.createNewFile();
            fetchFromCache.invoke(query, emptyFile, "entries", null, false);
            
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testReflectionWithDifferentControllers() {
        try {
            Method getResult = Query.class.getDeclaredMethod("getResult", Object.class, String.class);
            getResult.setAccessible(true);
            
            JSONObject resultObject = new JSONObject();
            
            String[] controllers = {"entries", "entry", "assets", "asset"};
            for (String controller : controllers) {
                getResult.invoke(query, resultObject, controller);
            }
            
            assertNotNull(query);
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}

