package com.contentstack.sdk;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class QueryTestCase {

    private static final String TAG = AssetTestCase.class.getSimpleName();
    private static final String contentTypeUID = BuildConfig.contentTypeUID;
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
                            assertEquals(0, queryresult.getResultObjects().size());
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

}