package com.contentstack.sdk;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

import androidx.test.core.app.ApplicationProvider;


public class QueryTestCase {

    private static final String TAG = AssetTestCase.class.getSimpleName();
    private static Stack stack;
    private static Query query;
    private static final String contentTypeUID = BuildConfig.contentTypeUID;

    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        Context appContext = ApplicationProvider.getApplicationContext();
        Config config = new Config();
        String DEFAULT_API_KEY = BuildConfig.APIKey;
        String DEFAULT_DELIVERY_TOKEN = BuildConfig.deliveryToken;
        String DEFAULT_ENV = BuildConfig.environment;
        String DEFAULT_HOST = BuildConfig.host;
        config.setHost(DEFAULT_HOST);
        stack = Contentstack.stack(appContext, DEFAULT_API_KEY, DEFAULT_DELIVERY_TOKEN, DEFAULT_ENV, config);
        query = stack.contentType(contentTypeUID).query();
    }


    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp() {
        query = stack.contentType(contentTypeUID).query();
    }


    @Test
    public void test_01_fetchAllEntries() {
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }

    @Test()
    public void test_03_fetchSingleNonExistingEntry() {
        Query query = stack.contentType("categories").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }

    @Test
    public void test_04_fetchEntryWithIncludeReference() {
        query.includeReference("category");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
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
    public void test_07_fetchEntryNotEqualToField() {
        query.notEqualTo("title", "yellow t shirt");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    entries.forEach(entry -> Log.i(TAG, entry.getString("title")));
                }
            }
        });
    }


    @Test
    public void test_08_fetchEntryGreaterThanEqualToField() {
        query.greaterThanOrEqualTo("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    entries.forEach(entry -> {
                        //og.i(TAG,entry.getString("price"));
                    });
                }
            }
        });
    }


    @Test
    public void test_09_fetchEntryGreaterThanField() {
        query.greaterThan("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    entries.forEach(entry -> {
                        //Log.i(TAG,entry.getString("price");
                    });
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
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    int price = entries.get(0).toJSON().optInt("price");
                    assertEquals(45, price);
                }
            }
        });
    }


    @Test
    public void test_11_fetchEntryLessThanField() {
        query.lessThan("price", "90");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> resp = queryresult.getResultObjects();
                    resp.forEach(entry -> {
                        Log.i(TAG, "Is price less than 90..? " + entry.get("price"));
                    });
                }
            }
        });
    }


    @Test
    public void test_12_fetchEntriesWithOr() {

        ContentType ct = stack.contentType(contentTypeUID);
        Query orQuery = ct.query();

        Query query = ct.query();
        query.lessThan("price", 90);

        Query subQuery = ct.query();
        subQuery.containedIn("discount", new Integer[]{20, 45});

        ArrayList<Query> array = new ArrayList<Query>();
        array.add(query);
        array.add(subQuery);

        orQuery.or(array);

        orQuery.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_13_fetchEntriesWithAnd() {

        ContentType ct = stack.contentType(contentTypeUID);
        Query orQuery = ct.query();

        Query query = ct.query();
        query.lessThan("price", 90);

        Query subQuery = ct.query();
        subQuery.containedIn("discount", new Integer[]{20, 45});

        ArrayList<Query> array = new ArrayList<Query>();
        array.add(query);
        array.add(subQuery);

        orQuery.and(array);
        orQuery.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_14_addQuery() {
        query.addQuery("limit", "8");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_15_removeQueryFromQuery() {
        query.addQuery("limit", "8");
        query.removeQuery("limit");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> listOfEntries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_16_includeSchema() {
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    JSONObject contentTypeObj = queryresult.getContentType();
                }
            }
        });
    }


    @Test
    public void test_17_search() {
        query.search("dress");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    for (Entry entry : entries) {
                        JSONObject jsonObject = entry.toJSON();
                        Iterator<String> iter = jsonObject.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = jsonObject.opt(key);
                                if (value instanceof String && ((String) value).contains("dress"))
                                    Log.i(TAG, value.toString());
                            } catch (Exception e) {
                                Log.i(TAG, "----------------setQueryJson" + e.toString());
                            }
                        }
                    }
                }
            }
        });
    }


    @Test
    public void test_18_ascending() {
        query.ascending("title");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    for (Entry entry : entries) {
                        Log.i(TAG, entry.getString("title"));
                    }
                }
            }
        });
    }


    @Test
    public void test_19_descending() {
        query.descending("title");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    for (Entry entry : entries) {
                        Log.i(TAG, entry.getString("title"));
                    }
                }
            }
        });
    }


    @Test
    public void test_20_limit() {
        query.limit(3);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    for (Entry entry : entries) {
                        Log.i(TAG, " entry = [" + entry.getString("title") + "]");
                    }
                }
            }
        });
    }


    @Test
    public void test_21_skip() {
        query.skip(3);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_22_only() {
        query.only(new String[]{"price"});
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_23_except() {
        query.locale("en-eu");
        query.except(new String[]{"price", "chutiya"});
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_24_count() {
        query.count();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    int count = queryresult.getCount();
                }
            }
        });
    }


    @Test
    public void test_25_regex() {
        query.regex("title", "lap*", "i");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_26_exist() {
        query.exists("title");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_27_notExist() {
        query.notExists("price1");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    int entryCount = queryresult.getCount();
                }
            }
        });


    }


    @Test
    public void test_28_tags() {
        query.tags(new String[]{"pink"});
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });


    }


    @Test
    public void test_29_language() {
        query.language(Language.ENGLISH_UNITED_KINGDOM);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });


    }


    @Test
    public void test_33_findOne() {
        query.includeCount();
        query.where("in_stock", true);
        query.findOne(new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {
                if (error == null) {
                    System.out.println("entry: " + entry);
                }
            }
        });
    }


    @Test
    public void test_34_complexFind() {
        query.notEqualTo("title", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*******");
        query.includeCount();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                }
            }
        });
    }


    @Test
    public void test_35_includeSchema() {
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                JSONArray result;
                if (error == null) {
                    result = queryresult.getSchema();
                }
            }
        });
    }


    @Test
    public void test_36_includeContentType() {
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    JSONObject entries = queryresult.getContentType();
                }
            }
        });
    }


    @Test
    public void test_38_include_content_type() {
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                JSONObject result;
                if (error == null) {
                    result = queryresult.getContentType();
                }
            }
        });
    }


    @Test
    public void test_39_include_content_type() {
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    JSONObject entries = queryresult.getContentType();
                }
            }
        });
    }


    @Test
    public void test_40_WithoutIncludeFallback() {
        Query fallbackQuery = stack.contentType("categories").query();
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
    public void test_40_WithIncludeFallback() {
        Query fallbackQuery = stack.contentType("categories").query();
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
    public void test_41_entry_include_embedded_items_unit_test() {

        final Query query = stack.contentType("user").query();
        query.includeEmbeddedItems().find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Entry checkResp = queryresult.getResultObjects().get(0);
                    Log.d(TAG, checkResp.toString());
                }
                //boolean hasEmbeddedItemKey = query.mainJSON.has("include_embedded_items[]");
                //Assert.assertTrue(hasEmbeddedItemKey);
            }
        });
    }


    @Test
    public void query_include_embedded_items_unit_test() throws Exception {
        final Query query = stack.contentType("testembededobjects").query();
        query.includeEmbeddedItems().find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Entry checkResp = queryresult.getResultObjects().get(0);
                    Log.d(TAG, queryresult.getResultObjects().toString());
                }
                // boolean hasEmbeddedItemKey = query.mainJSON.has("include_embedded_items[]");
                // Assert.assertTrue(hasEmbeddedItemKey);
            }
        });
    }


}