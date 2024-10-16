package com.contentstack.sdk;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TaxonomyFindTest {
    private Stack stack;

    @Before
    public void setUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);
    }

    @Test
    public void testTaxonomyIn() throws InterruptedException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        List<String> taxonomyQueryList = new ArrayList<>();
        taxonomyQueryList.add("term_two");
        taxonomy.in("taxonomies.two", taxonomyQueryList).find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyOr() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        List<JSONObject> taxonomyQueryList = new ArrayList<>();
        JSONObject item1 = new JSONObject();
        item1.put("taxonomies.one", "term_one");
        JSONObject item2 = new JSONObject();
        item2.put("taxonomies.two", "term_two");
        taxonomyQueryList.add(item1);
        taxonomyQueryList.add(item2);
        taxonomy.or(taxonomyQueryList).find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyAnd() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        List<JSONObject> taxonomyQueryList = new ArrayList<>();
        JSONObject item1 = new JSONObject();
        item1.put("taxonomies.one", "term_one");
        JSONObject item2 = new JSONObject();
        item2.put("taxonomies.two", "term_two");
        taxonomyQueryList.add(item1);
        taxonomyQueryList.add(item2);
        taxonomy.and(taxonomyQueryList).find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyAbove() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        taxonomy.above("taxonomies.one", "term_one_child").find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyEqualAndAbove() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        taxonomy.equalAndBelow("taxonomies.one", "term_one_child").find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyBelow() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        taxonomy.below("taxonomies.one", "term_one_child").find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }

    @Test
    public void testTaxonomyEqualAndBelow() throws InterruptedException, JSONException {
        // Create a latch to wait for the async operation to complete
        final CountDownLatch latch = new CountDownLatch(1);
        Taxonomy taxonomy = stack.taxonomy();
        taxonomy.equalAndBelow("taxonomies.one", "term_one_child").find(new TaxonomyCallback() {
            @Override
            public void onResponse(JSONObject response, Error error) {
                assertNull("There should be no error", error);
                assertNotNull("Entry should have been fetched", response);
                try {
                    JSONArray entries = (JSONArray)response.get("entries");
                    assertEquals(2, entries.length());
                } catch (Exception e) {
                    assertNull("Error should be null, fail the test", e);
                }
                latch.countDown();
            }
        });
        // Wait for the latch to be unlocked with a timeout (5 seconds)
        latch.await(5, TimeUnit.SECONDS);
        // Optionally fail the test if the latch wasn't counted down (i.e., timeout occurred)
        assertEquals("Query was not completed in time", 0, latch.getCount());
    }
}
