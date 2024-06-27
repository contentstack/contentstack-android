package com.contentstack.sdk;

import android.content.Context;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.*;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntryTestCase {

    private final static String TAG = EntryTestCase.class.getSimpleName();
    private static String entryUID;
    private static final String CONTENT_TYPE_UID = BuildConfig.contentTypeUID;
    private static CountDownLatch latch;
    private static Stack stack;
    private static String variantUID = BuildConfig.variantUID;
    private static String variantEntryUID = BuildConfig.variantEntryUID;
    private static String[] variantsUID = BuildConfig.variantsUID;


    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, BuildConfig.APIKey, BuildConfig.deliveryToken, BuildConfig.environment, config);

        latch = new CountDownLatch(1);
        Log.d(TAG, "test started...");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
        Log.d(TAG, "When all the test cases of class finishes...");
        Log.d(TAG, "Total testcase: " + latch.getCount());
    }

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        latch = new CountDownLatch(1);
    }


    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        Log.d(TAG, "Runs after every testcase completes.");
        latch.countDown();
    }


    @Test
    public void test_01_findAllEntries() {
        final Query query = stack.contentType(CONTENT_TYPE_UID).query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    entryUID = queryresult.getResultObjects().get(15).getUid();
                }
            }
        });
    }

    @Test
    public void test_02_only_fetch() {
        final Entry entry = stack.contentType(CONTENT_TYPE_UID).entry(entryUID);
        entry.only(new String[]{"price"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    assertEquals(786, entry.toJSON().opt("price"));
                }
            }
        });
    }

    @Test
    public void test_03_except_fetch() {
        final Entry entry = stack.contentType(CONTENT_TYPE_UID).entry(entryUID);
        entry.except(new String[]{"title"});
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    Log.e(TAG, entry.toJSON().optString("title"));
                } else {
                    Log.e(TAG, error.getErrorMessage());
                }
            }
        });
    }

    @Test
    public void test_04_includeReference_fetch() {
        final Entry entry = stack.contentType(CONTENT_TYPE_UID).entry(entryUID);
        entry.includeReference("category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    JSONArray categoryArray = entry.getJSONArray("category");

                    try {
                        for (int index = 0; index < categoryArray.length(); index++) {
                            JSONObject array = (JSONObject) categoryArray.get(index);
                            assertTrue(array.toString().contains("_content_type_uid"));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }

                }
            }
        });
    }

    @Test
    public void test_05_includeReferenceOnly_fetch() {
        final Entry entry = stack.contentType(CONTENT_TYPE_UID).entry(entryUID);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("title");
        strings.add("orange");
        strings.add("mango");
        entry.onlyWithReferenceUid(strings, "category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    assertEquals("laptop", entry.toJSON().optString("title"));
                }
            }
        });

    }


    @Test
    public void test_06_includeReferenceExcept_fetch() throws InterruptedException {
        final Entry entry = stack.contentType(CONTENT_TYPE_UID).entry(entryUID);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("color");
        strings.add("price_in_usd");
        entry.exceptWithReferenceUid(strings, "category");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();

    }


    @Test
    public void test_07_getMarkdown_fetch() throws InterruptedException {

        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }
            }
        });
        latch.await();
    }


    @Test
    public void test_08_get() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }
            }
        });
        latch.await();
    }


    @Test
    public void test_09_getParam() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.addParam("include_dimensions", "true");
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    latch.countDown();
                } else {
                    latch.countDown();
                }
            }
        });
        latch.await();
    }


    @Test
    public void test_10_IncludeReferenceContentTypeUID() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.includeReferenceContentTypeUID();
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    JSONObject jsonResult = entry.toJSON();
                    try {
                        JSONArray cartList = (JSONArray) jsonResult.get("cart");
                        Object whatTYPE = cartList.get(0);
                        if (whatTYPE instanceof JSONObject) {
                            assertTrue(true);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                    latch.countDown();
                } else {
                    latch.countDown();
                }
            }
        });
        latch.await();

    }


    @Test
    public void test_11_Locale() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    String checkResp = entry.getLocale();
                    Log.e(TAG, checkResp);
                    latch.countDown();
                } else {
                    latch.countDown();
                }

            }
        });
        latch.await();
    }

    @Test
    public void test_12_entry_except() throws InterruptedException {
        final Entry entry = stack.contentType("user").entry(entryUID);
        String[] allValues = {"color", "price_in_usd"};
        entry.except(allValues);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    String checkResp = entry.getLocale();
                    Log.d(TAG, checkResp);
                    latch.countDown();
                } else {
                    latch.countDown();
                }
            }
        });
        latch.await();
    }

    @Test
    public void test_13_entry_include_embedded_items_unit_test() throws InterruptedException {

        final Entry entry = stack.contentType("user").entry(entryUID);
        entry.includeEmbeddedItems().fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                if (error == null) {
                    String checkResp = entry.getLocale();
                    Log.d(TAG, checkResp);
                }
                boolean hasEmbeddedItemKey = entry.otherPostJSON.has("include_embedded_items[]");
                Assert.assertTrue(hasEmbeddedItemKey);
                latch.countDown();
            }
        });
        latch.await();
    }

    @Test
    public void VariantsTestSingleUid(){
        final Entry entry = stack.contentType("author").entry(variantEntryUID).variants(variantUID);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                assertEquals(variantUID, entry.getHeaders().get("x-cs-variant-uid"));
                System.out.println(entry.toJSON());
            }
        });
    }
    @Test
    public void VariantsTestArray(){
        final Entry entry = stack.contentType("author").entry(variantEntryUID).variants(variantsUID);
        entry.fetch(new EntryResultCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, Error error) {
                System.out.println(entry.toJSON());
            }
        });
    }

}