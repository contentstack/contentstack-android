package com.contentstack.sdk;

import static junit.framework.TestCase.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EntryFindTest {
    private Stack stack;
    private static final String CONTENT_TYPE_UID = BuildConfig.contentTypeUID;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);
    }

    @Test
    public void testFindEntry() throws InterruptedException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                assertEquals("source5", queryResult.getResultObjects().get(0).getTitle());
                // Unlock the latch to allow the test to proceed
                latch.countDown();
            }
        });

        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);

        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindEntryAsc() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        query.ascending("updated_at");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Modify the format to match the date format used.
                try {
                    Date prevDate = sdf.parse(entries.get(0).getUpdatedAt("updated_at"));
                    for (int i = 1; i < entries.size(); i++) {
                        Date currentDate = sdf.parse(entries.get(i).getUpdatedAt("updated_at"));
                        // Check if previous date is smaller than the current date
                        assertTrue("Previous date should be smaller than the current date", prevDate.compareTo(currentDate) <= 0);
                        // Update prevDate for the next iteration
                        prevDate = currentDate;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    fail("Date parsing failed");
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindEntryDesc() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        query.descending("updated_at");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Modify the format to match the date format used.
                try {
                    Date prevDate = sdf.parse(entries.get(0).getUpdatedAt("updated_at"));
                    for (int i = 1; i < entries.size(); i++) {
                        Date currentDate = sdf.parse(entries.get(i).getUpdatedAt("updated_at"));
                        // Check if previous date is smaller than the current date
                        assertTrue("Previous date should be greater than the current date", prevDate.compareTo(currentDate) >= 0);
                        // Update prevDate for the next iteration
                        prevDate = currentDate;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    fail("Date parsing failed");
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindLessThan() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType("numbers_content_type").query();
        int value = 11;
        query.lessThan("num_field", value);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    Integer currNum = (int)entries.get(i).get("num_field");
                    assertTrue("Curr num_field should be less than the value", currNum < value);
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindLessThanOrEqualTo() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType("numbers_content_type").query();
        int value = 11;
        query.lessThanOrEqualTo("num_field", value);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    Integer currNum = (int)entries.get(i).get("num_field");
                    assertTrue("Curr num_field should be less than or equal to the value", currNum <= value);
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindGreaterThan() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType("numbers_content_type").query();
        int value = 11;
        query.greaterThan("num_field", value);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    Integer currNum = (int)entries.get(i).get("num_field");
                    assertTrue("Curr num_field should be greater than the value", currNum > value);
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindGreaterThanOREqualTo() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType("numbers_content_type").query();
        int value = 11;
        query.greaterThanOrEqualTo("num_field", value);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    Integer currNum = (int)entries.get(i).get("num_field");
                    assertTrue("Curr num_field should be greater than or equal to the value", currNum >= value);
                }
                latch.countDown();
            }
        });
        latch.await(15, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindContainedIn() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        String[] values = {"source1"};
        query.containedIn("title", values);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    String title = entries.get(i).getTitle();
                    assertTrue("Title should contain the strings provided", title.contains(values[0]));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindNotContainedIn() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        String[] values = {"source1"};
        query.notContainedIn("title", values);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    String title = entries.get(i).getTitle();
                    assertTrue("Title should not contain the strings provided", !title.contains(values[0]));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindExists() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        String field = "boolean";
        query.exists(field);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    assertTrue("Field value provided should exist", entries.get(i).contains(field));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindNotExists() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        String field = "isspecial";
        query.notExists(field);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    assertTrue("Field value provided should exist", !entries.get(i).contains(field));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindOr() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        String[] values = {"source1"};
        String field = "boolean";
        final Query query1 = stack.contentType(CONTENT_TYPE_UID).query().containedIn("title", values);
        final Query query2 = stack.contentType(CONTENT_TYPE_UID).query().where(field, true);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        ArrayList<Query> queryList = new ArrayList<>();
        queryList.add(query1);
        queryList.add(query2);
        query.or(queryList);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    assertTrue("One of or query should be true", (Boolean) entries.get(i).get(field) || entries.get(i).getTitle().equals(values[0]));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindAnd() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        String[] values = {"source1"};
        String field = "boolean";
        final Query query1 = stack.contentType(CONTENT_TYPE_UID).query().containedIn("title", values);
        final Query query2 = stack.contentType(CONTENT_TYPE_UID).query().where(field, true);
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        ArrayList<Query> queryList = new ArrayList<>();
        queryList.add(query1);
        queryList.add(query2);
        query.and(queryList);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    assertTrue("Both of and queries should be true", (Boolean) entries.get(i).get(field) && entries.get(i).getTitle().equals(values[0]));
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testFindIncludeReference() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        String field = "reference";
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        query.includeReference(field);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error)  {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", queryResult);
                List<Entry> entries = queryResult.getResultObjects();
                for (int i = 0; i < entries.size(); i++) {
                    try {
                        JSONArray ref = (JSONArray)entries.get(i).get(field);
                        // Convert JSONArray to List
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < ref.length(); j++) {
                            JSONObject jsonObject = ref.getJSONObject(j);  // Get the first JSONObject
                            // Title is a mandatory field, so we can test against it being present
                            assertTrue("One of or should be true", jsonObject.has("title"));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }
}
