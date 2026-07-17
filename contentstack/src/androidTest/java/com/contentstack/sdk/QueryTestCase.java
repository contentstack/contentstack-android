package com.contentstack.sdk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;


public class QueryTestCase {

    private static final String TAG = AssetTestCase.class.getSimpleName();
    private static final String contentTypeUID = BuildConfig.contentTypeUID;
    private static final String variantUID = BuildConfig.variantUID;
    private static final String[] variantsUID = BuildConfig.variantsUID;
    private static final String variantBranch = BuildConfig.variantBranch;
    private static Query query;

    static {
        try {
            query = TestCred.stack().contentType(contentTypeUID).query();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        query = TestCred.stack().contentType(contentTypeUID).query();
    }

    @Test
    public void test_05_fetchEntryNotContainedInField() {
        String[] containArray = new String[]{"Roti Maker", "kids dress"};
        query.notContainedIn("title", containArray);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    int price = entries.get(0).toJSON().optInt("price");
                    assertEquals(45, price);
                }
            }
        });
    }

    @Test
    public void test_10_fetchEntryLessThanEqualField() {
        query.lessThanOrEqualTo("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
//                if (error == null) {
//                    List<Entry> entries = queryresult.getResultObjects();
//                    int price = entries.get(0).toJSON().optInt("price");
//                    assertEquals(0, price);
//                }
            }
        });
    }

    @Test
    public void test_40_WithoutIncludeFallback() throws Exception {
        Query fallbackQuery = TestCred.stack().contentType("categories").query();
        fallbackQuery.locale("hi-in");
        fallbackQuery.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    assertEquals(0, queryresult.getResultObjects().size());
                    fallbackQuery.includeFallback().locale("hi-in");
                    fallbackQuery.find(new QueryResultsCallBack() {
                        @Override
                        public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                            assertEquals(8, queryresult.getResultObjects().size());
                        }
                    });
                }
            }
        });
    }

    @Test
    public void test_40_WithIncludeFallback() throws Exception {
        Query fallbackQuery = TestCred.stack().contentType("categories").query();
        fallbackQuery.locale("hi-in");
        fallbackQuery.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    assertEquals(0, queryresult.getResultObjects().size());
                }
            }
        });
    }


    @Test
    public void test_41_entry_include_embedded_items_unit_test() throws Exception {
        final Query query = TestCred.stack().contentType("user").query();
        query.includeEmbeddedItems().find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
//                if (error == null) {
//                    Entry checkResp = queryresult.getResultObjects().get(0);
//                    Log.d(TAG, checkResp.toString());
//                }
//                boolean hasEmbeddedItemKey = query.mainJSON.has("include_embedded_items[]");
//                Assert.assertTrue(hasEmbeddedItemKey);
            }
        });
    }

    @Test
    public void test_42_variants_single_uid_find() throws Exception {
        Assume.assumeFalse("variantUID not configured", variantUID == null || variantUID.trim().isEmpty());
        final Query q = TestCred.stack().contentType(contentTypeUID).query();
        q.variants(variantUID);
        assertEquals(variantUID.trim(), q.getHeaders().get("x-cs-variant-uid"));
        assertNull(q.getHeaders().get("branch"));

        final CountDownLatch latch = new CountDownLatch(1);
        q.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Log.d(TAG, "variants single find: " + queryresult.getResultObjects().size() + " entries");
                }
                latch.countDown();
            }
        });
        assertTrue("find() callback timed out", latch.await(30, TimeUnit.SECONDS));
    }

    @Test
    public void test_43_variants_array_find() throws Exception {
        Assume.assumeFalse("variantsUID not configured", variantsUID == null || variantsUID.length == 0);
        final Query q = TestCred.stack().contentType(contentTypeUID).query();
        q.variants(variantsUID);
        assertNotNull(q.getHeaders().get("x-cs-variant-uid"));
        assertNull(q.getHeaders().get("branch"));

        final CountDownLatch latch = new CountDownLatch(1);
        q.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Log.d(TAG, "variants array find: " + queryresult.getResultObjects().size() + " entries");
                }
                latch.countDown();
            }
        });
        assertTrue("find() callback timed out", latch.await(30, TimeUnit.SECONDS));
    }

    @Test
    public void test_44_variants_single_uid_with_branch_find() throws Exception {
        Assume.assumeFalse("variantUID not configured", variantUID == null || variantUID.trim().isEmpty());
        Assume.assumeFalse("variantBranch not configured", variantBranch == null || variantBranch.trim().isEmpty());
        final Query q = TestCred.stack().contentType(contentTypeUID).query();
        q.variants(variantUID, variantBranch);
        assertEquals(variantUID.trim(), q.getHeaders().get("x-cs-variant-uid"));
        assertEquals(variantBranch.trim(), q.getHeaders().get("branch"));

        final CountDownLatch latch = new CountDownLatch(1);
        q.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Log.d(TAG, "variants single+branch find: " + queryresult.getResultObjects().size() + " entries");
                }
                latch.countDown();
            }
        });
        assertTrue("find() callback timed out", latch.await(30, TimeUnit.SECONDS));
    }

    @Test
    public void test_45_variants_array_with_branch_find() throws Exception {
        Assume.assumeFalse("variantsUID not configured", variantsUID == null || variantsUID.length == 0);
        Assume.assumeFalse("variantBranch not configured", variantBranch == null || variantBranch.trim().isEmpty());
        final Query q = TestCred.stack().contentType(contentTypeUID).query();
        q.variants(variantsUID, variantBranch);
        assertNotNull(q.getHeaders().get("x-cs-variant-uid"));
        assertEquals(variantBranch.trim(), q.getHeaders().get("branch"));

        final CountDownLatch latch = new CountDownLatch(1);
        q.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Log.d(TAG, "variants array+branch find: " + queryresult.getResultObjects().size() + " entries");
                }
                latch.countDown();
            }
        });
        assertTrue("find() callback timed out", latch.await(30, TimeUnit.SECONDS));
    }

}