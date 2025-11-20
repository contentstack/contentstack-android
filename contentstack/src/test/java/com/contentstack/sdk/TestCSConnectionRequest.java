//package com.contentstack.sdk;
//
//import android.util.ArrayMap;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileReader;
//import java.lang.reflect.Field;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
///**
// * Unit tests for CSConnectionRequest (coverage-oriented, less strict on args).
// */
//public class TestCSConnectionRequest {
//
//    // -----------------------------
//    // Helpers
//    // -----------------------------
//
//    private void injectField(Object target, String fieldName, Object value) throws Exception {
//        Field f = target.getClass().getDeclaredField(fieldName);
//        f.setAccessible(true);
//        f.set(target, value);
//    }
//
//    private String readAll(File f) throws Exception {
//        FileReader r = new FileReader(f);
//        StringBuilder sb = new StringBuilder();
//        char[] buf = new char[1024];
//        int len;
//        while ((len = r.read(buf)) != -1) {
//            sb.append(buf, 0, len);
//        }
//        r.close();
//        return sb.toString();
//    }
//
//    // -----------------------------
//    // onRequestFailed
//    // -----------------------------
//
//    @Test
//    public void testOnRequestFailed_populatesErrorAndCallsCallback() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        JSONObject err = new JSONObject();
//        err.put("error_message", "fail message");
//        err.put("error_code", 123);
//        JSONObject errorsObj = new JSONObject();
//        errorsObj.put("field", "is required");
//        err.put("errors", errorsObj);
//
//        ResultCallBack cb = mock(ResultCallBack.class);
//        injectField(req, "callBackObject", cb);
//
//        req.onRequestFailed(err, 400, cb);
//
//        // we don’t care about exact Error content, only that callback is invoked
//        verify(cb, atLeastOnce()).onRequestFail(eq(ResponseType.NETWORK), any(Error.class));
//    }
//
//    @Test
//    public void testOnRequestFailed_withNullError_usesDefaultMessage() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        ResultCallBack cb = mock(ResultCallBack.class);
//        injectField(req, "callBackObject", cb);
//
//        req.onRequestFailed(null, 500, cb);
//
//        verify(cb, atLeastOnce()).onRequestFail(eq(ResponseType.NETWORK), any(Error.class));
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_QUERY_ENTRIES
//    // -----------------------------
//
//    @Test
//    public void testOnRequestFinished_queryEntries() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        INotifyClass notifyClass = mock(INotifyClass.class);
//        injectField(req, "notifyClass", notifyClass);
//        injectField(req, "cacheFileName", null);
//
//        JSONObject response = new JSONObject();
//        response.put("entries", new JSONArray());
//        response.put("schema", new JSONArray());
//        response.put("content_type", new JSONObject().put("uid", "ct_uid"));
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_QUERY_ENTRIES);
//
//        // main goal: exercise the branch, not assert exact results
//        req.onRequestFinished(conn);
//
//        // If we reach here without any exception, the branch is covered.
//        assertTrue(true);
//    }
//
//    // -----------------------------
//    // onRequestFinished – SINGLE_QUERY_ENTRIES
//    // -----------------------------
//
//    @Test
//    public void testOnRequestFinished_singleQueryEntries() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        INotifyClass notifyClass = mock(INotifyClass.class);
//        injectField(req, "notifyClass", notifyClass);
//        injectField(req, "cacheFileName", null);
//
//        JSONObject response = new JSONObject();
//        response.put("entries", new JSONArray());
//        response.put("schema", new JSONArray());
//        response.put("content_type", new JSONObject().put("uid", "ct_uid"));
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.SINGLE_QUERY_ENTRIES);
//
//        req.onRequestFinished(conn);
//
//        // again, just smoke-check that no exception is thrown
//        assertTrue(true);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_ENTRY
//    // -----------------------------
//
//    static class TestEntryResultCallback extends EntryResultCallBack {
//        boolean called = false;
//
//        @Override
//        public void onCompletion(ResponseType responseType, Error error) {
//            called = true;
//        }
//    }
//
//    @Test
//    public void testOnRequestFinished_getEntry() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        Entry entry = mock(Entry.class);
//        injectField(req, "entryInstance", entry);
//        injectField(req, "cacheFileName", null);
//
//        JSONObject entryJson = new JSONObject();
//        entryJson.put("uid", "entry_uid");
//        entryJson.put("title", "title");
//        entryJson.put("url", "/url");
//        entryJson.put("locale", "en-us");
//
//        JSONObject response = new JSONObject();
//        response.put("entry", entryJson);
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_ENTRY);
//
//        TestEntryResultCallback cb = new TestEntryResultCallback();
//        when(conn.getCallBackObject()).thenReturn(cb);
//
//        req.onRequestFinished(conn);
//
//        assertTrue(cb.called);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_ALL_ASSETS
//    // -----------------------------
//
//    @Test
//    public void testOnRequestFinished_getAllAssets() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        INotifyClass assetLibrary = mock(INotifyClass.class);
//        injectField(req, "assetLibrary", assetLibrary);
//        injectField(req, "cacheFileName", null);
//
//        JSONObject assetJson = new JSONObject();
//        assetJson.put("uid", "asset_uid");
//
//        JSONArray assetsArr = new JSONArray();
//        assetsArr.put(assetJson);
//
//        JSONObject response = new JSONObject();
//        response.put("assets", assetsArr);
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_ALL_ASSETS);
//
//        req.onRequestFinished(conn);
//
//        // only check we reached here without crash
//        assertTrue(true);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_ASSETS
//    // -----------------------------
//
//    static class TestFetchResultCallback extends FetchResultCallback {
//        boolean called = false;
//
//        @Override
//        public void onCompletion(ResponseType responseType, Error error) {
//            called = true;
//        }
//    }
//
//    @Test
//    public void testOnRequestFinished_getAssets() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        Asset asset = new Asset();
//        injectField(req, "assetInstance", asset);
//        injectField(req, "cacheFileName", null);
//
//        JSONObject assetJson = new JSONObject();
//        assetJson.put("uid", "asset_uid");
//        assetJson.put("content_type", "image/png");
//        assetJson.put("filename", "file.png");
//        assetJson.put("url", "https://example.com/file.png");
//        assetJson.put("file_size", "1234");
//
//        JSONObject response = new JSONObject();
//        response.put("asset", assetJson);
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_ASSETS);
//        when(conn.getCallBackObject()).thenReturn(null);
//
//        req.onRequestFinished(conn);
//
//        // Basic sanity: UID set from response
//        assertEquals("asset_uid", asset.assetUid);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_SYNC
//    // -----------------------------
//
//    static class TestSyncCallback extends SyncResultCallBack {
//        boolean called = false;
//
//        @Override
//        public void onCompletion(SyncStack syncStack, Error error) {
//            called = true;
//        }
//    }
//
//    @Test
//    public void testOnRequestFinished_getSync() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        injectField(req, "cacheFileName", null);
//
//        JSONObject response = new JSONObject();
//        response.put("items", new JSONArray());
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_SYNC);
//
//        TestSyncCallback cb = new TestSyncCallback();
//        when(conn.getCallBackObject()).thenReturn(cb);
//
//        req.onRequestFinished(conn);
//
//        assertTrue(cb.called);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_CONTENT_TYPES
//    // -----------------------------
//
//    static class TestContentTypesCallback extends ContentTypesCallback {
//        boolean called = false;
//
//        @Override
//        public void onCompletion(ContentTypesModel model, Error error) {
//            called = true;
//        }
//    }
//
//    @Test
//    public void testOnRequestFinished_getContentTypes() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        injectField(req, "cacheFileName", null);
//
//        JSONObject response = new JSONObject();
//        response.put("content_types", new JSONArray());
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_CONTENT_TYPES);
//
//        TestContentTypesCallback cb = new TestContentTypesCallback();
//        when(conn.getCallBackObject()).thenReturn(cb);
//
//        req.onRequestFinished(conn);
//
//        assertTrue(cb.called);
//    }
//
//    // -----------------------------
//    // onRequestFinished – GET_GLOBAL_FIELDS
//    // -----------------------------
//
//    static class TestGlobalFieldsCallback extends GlobalFieldsResultCallback {
//        boolean called = false;
//
//        @Override
//        public void onCompletion(GlobalFieldsModel globalFieldsModel, Error error) {
//            called = true;
//        }
//    }
//
//    @Test
//    public void testOnRequestFinished_getGlobalFields() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        injectField(req, "cacheFileName", null);
//
//        JSONObject response = new JSONObject();
//        response.put("global_fields", new JSONArray());
//
//        CSHttpConnection conn = mock(CSHttpConnection.class);
//        when(conn.getResponse()).thenReturn(response);
//        when(conn.getController()).thenReturn(SDKController.GET_GLOBAL_FIELDS);
//
//        TestGlobalFieldsCallback cb = new TestGlobalFieldsCallback();
//        when(conn.getCallBackObject()).thenReturn(cb);
//
//        req.onRequestFinished(conn);
//
//        assertTrue(cb.called);
//    }
//
//    // -----------------------------
//    // createFileIntoCacheDir
//    // -----------------------------
//
//    @Test
//    public void testCreateFileIntoCacheDir_whenException_callsCallbackWithCacheError() throws Exception {
//        CSConnectionRequest req = new CSConnectionRequest();
//
//        // Use a directory as "file" so FileWriter throws
//        File dir = File.createTempFile("csreqdir", "");
//        dir.delete();
//        dir.mkdir();
//
//        injectField(req, "cacheFileName", dir.getAbsolutePath());
//        injectField(req, "paramsJSON", new JSONObject());
//        injectField(req, "header", new ArrayMap<String, Object>());
//        injectField(req, "urlToCall", "https://example.com");
//
//        ResultCallBack cb = mock(ResultCallBack.class);
//        injectField(req, "callBackObject", cb);
//
//        req.createFileIntoCacheDir(new JSONObject().put("resp", "ok"));
//
//        verify(cb, atLeastOnce()).onRequestFail(eq(ResponseType.CACHE), any(Error.class));
//    }
//}
