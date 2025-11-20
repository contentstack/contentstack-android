package com.contentstack.sdk;

import android.util.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Taxonomy}
 */
public class TestTaxonomy {

    private Taxonomy taxonomy;
    private FakeAPIService fakeService;
    private Config config;
    private ArrayMap<String, Object> headers;

    // -------- Fake Call implementation ----------

    private static class FakeCall implements Call<ResponseBody> {

        private final Response<ResponseBody> responseToReturn;
        private boolean executed = false;
        private final Timeout timeout = new Timeout(); // okio.Timeout

        FakeCall(Response<ResponseBody> responseToReturn) {
            this.responseToReturn = responseToReturn;
        }

        @Override
        public Response<ResponseBody> execute() throws IOException {
            executed = true;
            return responseToReturn;
        }

        @Override
        public void enqueue(Callback<ResponseBody> callback) {
            throw new UnsupportedOperationException("enqueue not supported in FakeCall");
        }

        @Override
        public boolean isExecuted() {
            return executed;
        }

        @Override
        public void cancel() {
            // no-op
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call<ResponseBody> clone() {
            return new FakeCall(responseToReturn);
        }

        @Override
        public Request request() {
            return new Request.Builder()
                    .url("https://example.com")
                    .build();
        }

        @Override
        public Timeout timeout() {
            return timeout;
        }
    }

    // -------- Fake APIService implementation ----------

    private static class FakeAPIService implements APIService {

        String lastQueryString;
        Map<String, Object> lastHeaders;
        Call<ResponseBody> taxonomyCallToReturn;

        @Override
        public Call<ResponseBody> getTaxonomy(Map<String, Object> headers, String query) {
            this.lastHeaders = headers;
            this.lastQueryString = query;
            return taxonomyCallToReturn;
        }

        @Override
        public Call<ResponseBody> getRequest(String url, LinkedHashMap<String, Object> headers) {
            // Not used in Taxonomy tests, minimal stub
            return new FakeCall(
                    Response.success(
                            ResponseBody.create(
                                    "{\"dummy\":\"ok\"}",
                                    MediaType.parse("application/json"))));
        }

        // If APIService has more abstract methods, stub them similarly as needed.
    }

    // -------- Setup ----------

    @Before
    public void setUp() {
        headers = new ArrayMap<>();
        headers.put("api_key", "test_key");
        headers.put("access_token", "test_token");

        config = new Config();
        fakeService = new FakeAPIService();

        taxonomy = new Taxonomy(fakeService, config, headers);
    }

    // -------- Helper to create successful / error Response ----------

    private Response<ResponseBody> createSuccessResponse(String body) {
        return Response.success(
                ResponseBody.create(body, MediaType.parse("application/json")));
    }

    private Response<ResponseBody> createErrorResponse(int code, String body) {
        okhttp3.Response raw = new okhttp3.Response.Builder()
                .code(code)
                .message("Error")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://example.com").build())
                .build();

        return Response.error(
                ResponseBody.create(body, MediaType.parse("application/json")),
                raw);
    }

    // -------- Tests for query builders ----------

    @Test
    public void testInBuildsCorrectQuery() throws Exception {
        taxonomy.in("taxonomies.color", Arrays.asList("red", "yellow"));

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
            assertNull(error);
            assertNotNull(response);
        });

        assertNotNull(fakeService.lastQueryString);
        JSONObject parsed = new JSONObject(fakeService.lastQueryString);

        assertTrue(parsed.has("taxonomies.color"));
        JSONObject inner = parsed.getJSONObject("taxonomies.color");
        JSONArray inArray = inner.getJSONArray("$in");
        assertEquals(2, inArray.length());
        assertEquals("red", inArray.getString(0));
        assertEquals("yellow", inArray.getString(1));
    }

    @Test
    public void testOrBuildsCorrectQuery() throws Exception {
        JSONObject obj1 = new JSONObject();
        obj1.put("taxonomies.color", "yellow");
        JSONObject obj2 = new JSONObject();
        obj2.put("taxonomies.size", "small");

        taxonomy.or(Arrays.asList(obj1, obj2));

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
            assertNull(error);
            assertNotNull(response);
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("$or"));
        JSONArray orArray = parsed.getJSONArray("$or");
        assertEquals(2, orArray.length());
    }

    @Test
    public void testExistsBuildsCorrectQuery() throws Exception {
        taxonomy.exists("taxonomies.color", true);

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
            assertNull(error);
            assertNotNull(response);
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        JSONObject existsObj = parsed.getJSONObject("taxonomies.color");
        assertTrue(existsObj.getBoolean("$exists"));
    }

    // -------- find() behaviour tests ----------

    @Test
    public void testFindSuccess() {
        try {
            JSONObject responseJson = new JSONObject();
            responseJson.put("entries", new JSONArray());

            fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(responseJson.toString()));

            final boolean[] callbackCalled = { false };

            taxonomy.find((response, error) -> {
                callbackCalled[0] = true;
                assertNull(error);
                assertNotNull(response);
                assertTrue(response.has("entries"));
            });

            assertTrue(callbackCalled[0]);
        } catch (JSONException e) {
            fail("JSONException should not be thrown in testFindSuccess: " + e.getMessage());
        }
    }

    @Test
    public void testFindError() {
        try {
            JSONObject errorJson = new JSONObject();
            errorJson.put("error_message", "Something went wrong");
            errorJson.put("error_code", 123);

            fakeService.taxonomyCallToReturn = new FakeCall(createErrorResponse(400, errorJson.toString()));

            final boolean[] callbackCalled = { false };

            taxonomy.find((response, error) -> {
                callbackCalled[0] = true;
                assertNull(response);
                assertNotNull(error);
                assertEquals("Something went wrong", error.getErrorMessage());
                assertEquals(123, error.getErrorCode());
            });

            assertTrue(callbackCalled[0]);
        } catch (JSONException e) {
            fail("JSONException should not be thrown in testFindError: " + e.getMessage());
        }
    }

    @Test
    public void testAndBuildsCorrectQuery() throws Exception {
        JSONObject obj1 = new JSONObject();
        obj1.put("taxonomies.color", "red");
        JSONObject obj2 = new JSONObject();
        obj2.put("taxonomies.size", "large");

        taxonomy.and(Arrays.asList(obj1, obj2));

        // trigger makeRequest / find so query gets serialized and we can inspect it
        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
            assertNull(error);
            assertNotNull(response);
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("$and"));
        JSONArray andArray = parsed.getJSONArray("$and");
        assertEquals(2, andArray.length());
        assertEquals(obj1.toString(), andArray.getJSONObject(0).toString());
        assertEquals(obj2.toString(), andArray.getJSONObject(1).toString());
    }

    @Test
    public void testEqualAndBelowBuildsCorrectQuery() throws Exception {
        taxonomy.equalAndBelow("taxonomies.color", "blue");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("taxonomies.color"));
        JSONObject node = parsed.getJSONObject("taxonomies.color");
        assertEquals("blue", node.getString("$eq_below"));
    }

    @Test
    public void testBelowBuildsCorrectQuery() throws Exception {
        taxonomy.below("taxonomies.color", "blue");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("taxonomies.color"));
        JSONObject node = parsed.getJSONObject("taxonomies.color");
        assertEquals("blue", node.getString("$below"));
    }

    @Test
    public void testEqualAboveBuildsCorrectQuery() throws Exception {
        taxonomy.equalAbove("taxonomies.appliances", "led");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("taxonomies.appliances"));
        JSONObject node = parsed.getJSONObject("taxonomies.appliances");
        assertEquals("led", node.getString("$eq_above"));
    }

    @Test
    public void testAboveBuildsCorrectQuery() throws Exception {
        taxonomy.above("taxonomies.appliances", "led");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertTrue(parsed.has("taxonomies.appliances"));
        JSONObject node = parsed.getJSONObject("taxonomies.appliances");
        assertEquals("led", node.getString("$above"));
    }

    // ========== NEGATIVE / EDGE CASE TESTS FOR QUERY BUILDERS ==========

    @Test
    public void testAndWithNullListDoesNotModifyQuery() throws Exception {
        // call with null
        taxonomy.and(null);

        // prepare fake response so that find() serializes current query
        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        // when nothing was added, query should be empty object
        assertNotNull(fakeService.lastQueryString);
        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertEquals(0, parsed.length()); // no $and key present
    }

    @Test
    public void testEqualAndBelowWithEmptyParamsDoesNotModifyQuery() throws Exception {
        taxonomy.equalAndBelow("", "");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertEquals(0, parsed.length()); // no key added
    }

    @Test
    public void testBelowWithEmptyParamsDoesNotModifyQuery() throws Exception {
        taxonomy.below("", "");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertEquals(0, parsed.length());
    }

    @Test
    public void testEqualAboveWithEmptyParamsDoesNotModifyQuery() throws Exception {
        taxonomy.equalAbove("", "");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertEquals(0, parsed.length());
    }

    @Test
    public void testAboveWithEmptyParamsDoesNotModifyQuery() throws Exception {
        taxonomy.above("", "");

        JSONObject json = new JSONObject();
        json.put("entries", new JSONArray());
        fakeService.taxonomyCallToReturn = new FakeCall(createSuccessResponse(json.toString()));

        taxonomy.find((response, error) -> {
        });

        JSONObject parsed = new JSONObject(fakeService.lastQueryString);
        assertEquals(0, parsed.length());
    }

}
