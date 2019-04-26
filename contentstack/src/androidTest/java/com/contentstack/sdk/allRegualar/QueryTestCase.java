package com.contentstack.sdk.allRegualar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.contentstack.sdk.BuildConfig;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.ContentType;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.Language;
import com.contentstack.sdk.Query;
import com.contentstack.sdk.QueryResult;
import com.contentstack.sdk.QueryResultsCallBack;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.SingleQueryResultCallback;
import com.contentstack.sdk.Stack;
import com.contentstack.sdk.utilities.CSAppUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * Created by Shailesh Mishra.
 * Contentstack pvt Ltd
 *
 */

@RunWith(AndroidJUnit4.class)
public class QueryTestCase  {

    private static final String TAG = "TESTCASE";
    private Stack stack;
    private Context appContext;
    private String[] containArray;


    public QueryTestCase() throws Exception{

        appContext = InstrumentationRegistry.getTargetContext();

        Config config = new Config();
        config.setHost(BuildConfig.base_url);
        stack = Contentstack.stack(appContext,
                BuildConfig.default_api_key,
                BuildConfig.default_access_token,
                BuildConfig.default_env,config);
        containArray = new String[]{"Roti Maker", "kids dress"};
    }



    @Test
    public void test_00_fetchAllEntries() {

        Query query = stack.contentType("product").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    assertEquals(27, entries.size());
                }
            }
        });

    }




    @Test
    public void test_01_fetchEntriesOfNonExistingContentType() {
        Query query = stack.contentType("department").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    int size = queryresult.getResultObjects().size();
                    assertEquals(27, size);
                }
            }
        });
    }





    @Test
    public void test_02_fetchSingleEntry() {
        Query query = stack.contentType("categories").query();
        query.where("title","Women");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    Entry size = queryresult.getResultObjects().get(0);
                    assertEquals("bltc4342a218a8a66c5", size.getUid());
                }
            }
        });

    }




    @Test
    public void test_03_fetchSingleNonExistingEntry() {
        Query query = stack.contentType("categories").query();
        query.where("uid","blta3b58d6893d8935b");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    assertEquals(0, queryresult.getResultObjects().size());
                }
            }
        });
    }






    @Test
    public void test_04_fetchEntryWithIncludeReference()  {
        Query query = stack.contentType("product").query();

        query.includeReference("category");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    JSONArray categoryArray = (JSONArray) entries.get(0).get("category");
                    try {
                        assertTrue(categoryArray.get(0) instanceof JSONObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });


    }


    @Test
    public void test_05_fetchEntryNotContainedInField()  {


        Query query = stack.contentType("product").query();
        query.notContainedIn("title", containArray);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    if(entries != null) {
                        boolean isContains = false;
                        for (Entry entry : entries) {
                            if (Arrays.asList(containArray).contains(entry.getString("title"))) {
                                isContains = true;
                            }
                        }
                        assertTrue(!isContains);
                    }
                }
            }
        });
    }




    @Test
    public void test_06_fetchEntryContainedInField()  {

        Query query = stack.contentType("product").query();
        query.containedIn("title", containArray);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                ArrayList<Entry> entries = null;
                if (error == null) {
                    entries = (ArrayList<Entry>) queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (!Arrays.asList(containArray).contains(entry.getString("title"))) {
                            isContains = true;
                        }
                    }
                    assertTrue(!isContains);
                }
            }
        });
    }



    @Test
    public void test_07_fetchEntryNotEqualToField()  {

        Query query = stack.contentType("product").query();

        query.notEqualTo("title", "yellow t shirt");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        if(entry.getTitle().trim().equals("yellow t shirt")){
                            count++;
                        }
                    }
                    assertTrue(count == 0);
                }
            }
        });

    }



    @Test
    public void test_08_fetchEntryGreaterThanEqualToField()  {

        Query query = stack.contentType("product").query();

        query.greaterThanOrEqualTo("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        if((Integer)entry.get("price")>=90) {
                            count++;
                        }
                    }
                    assertTrue(count.equals(entries.size()));
                }
            }
        });


    }



    @Test
    public void test_09_fetchEntryGreaterThanField()  {

        Query query = stack.contentType("product").query();

        query.greaterThan("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        if((Integer)entry.get("price")>90) {
                            count++;
                        }
                    }
                    assertTrue(count.equals(entries.size()));

                }
            }
        });


    }




    @Test
    public void test_10_fetchEntryLessThanEqualField()  {

        Query query = stack.contentType("product").query();

        query.lessThanOrEqualTo("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        if((Integer)entry.get("price")<=90) {
                            count++;
                        }
                    }
                    assertTrue(count.equals(entries.size()));
                }
            }
        });


    }




    @Test
    public void test_11_fetchEntryLessThanField()  {

        Query query = stack.contentType("product").query();

        query.lessThan("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        System.out.println(entry.get("price"));
                        if((Integer)entry.get("price")<90) {
                            count++;
                        }
                    }
                    assertTrue(count.equals(entries.size()));
                }
            }
        });


    }



    @Test
    public void test_12_fetchEntriesWithOr()  {

        ContentType ct = stack.contentType("product");
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
                    String resultString = queryresult.getResultObjects().get(4).getString("title");
                    assertEquals("Men Green Tshirt", resultString);
                }
            }
        });

    }






    @Test
    public void test_13_fetchEntriesWithAnd()  {

        ContentType ct = stack.contentType("product");
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

                List<Entry> entries = null;
                Integer count = 0;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    for(Entry entry: entries){
                        int discount = (Integer)entry.get("discount");
                        if((Integer)entry.get("price")<90 || (discount==20 || discount == 45)) {
                            count++;
                        }
                    }
                }

                assertTrue(count.equals(entries.size()));
            }
        });


    }





    @Test
    public void test_14_addQuery()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.addQuery("limit", "8");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }
                assertTrue(entries.size() == 8);
            }
        });


    }




    @Test
    public void test_15_removeQueryFromQuery()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();



        query.addQuery("limit", "8");
        query.removeQuery("limit");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }
                assertTrue(entries.size() == 27);
            }
        });


    }





    @Test
    public void test_18_includeSchema()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeSchema();


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                JSONArray schema = null;
                if (error == null) {
                    schema = queryresult.getSchema();
                }

                assertTrue(schema !=  null);
            }
        });


    }





    @Test
    public void test_19_search()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.search("dress");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                   String result = queryresult.getResultObjects().get(1).getString("title");
                   assertEquals("halloween dress", result);
                }
            }
        });
    }




    @Test
    public void test_20_ascending()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.ascending("title");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    for (Entry entry: entries) {
                        Log.w(TAG, "----------Test--Query-20--Success---------" + entry.getString("title"));
                    }
                }
                assertTrue(entries.size() == 27);
            }
        });


    }





    @Test
    public void test_21_descending()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.descending("title");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    for (Entry entry: entries) {
                        Log.w(TAG, "----------Test--Query-21--Success---------" + entry.getString("title"));
                    }
                }

                assertTrue(entries.size() == 27);
            }
        });


    }




    @Test
    public void test_22_limit()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.limit(3);


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    for (Entry entry: entries) {
                        System.out.println(" entry = [" + entry.getString("title") + "]");
                    }
                }
                assertTrue(entries.size() == 3);
            }
        });


    }




    @Test
    public void test_23_skip()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.skip(3);


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries= queryresult.getResultObjects();
                }
                assertTrue(entries.size() == 24);
            }
        });
    }




    @Test
    public void test_24_only()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.only(new String[]{"price"});
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (entry.toJSON().has("title")) {
                            isContains = true;
                        }
                    }
                    assertFalse(isContains);
                }
            }
        });
    }

    public void test_25_except()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.except(new String[]{"price"});


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = (ArrayList<Entry>) queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (entry.toJSON().has("price")) {
                            isContains = true;
                        }
                    }
                    assertTrue(!isContains);
                }
            }
        });
    }

    public void test_26_count()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.count();


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                int entriesCount = 0;
                if (error == null) {
                    entriesCount = queryresult.getCount();
                }
                assertEquals(27, entriesCount);
            }
        });
    }






    @Test
    public void test_27_regex()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.regex("title", "lap*", "i");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries =  queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (entry.getString("title").equals("laptop")) {
                            isContains = true;
                        }
                    }
                    assertTrue(isContains);
                }
            }
        });
    }





    @Test
    public void test_28_exist()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.exists("title");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (entry.toJSON().has("title")) {
                            isContains = true;
                        }
                    }
                    assertTrue(isContains);
                }
            }
        });


    }






    @Test
    public void test_29_notExist()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.notExists("price1");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                int entriesCount = 0;
                List<Entry> entries = null;
                if (error == null) {
                    entriesCount = queryresult.getCount();
                    entries = queryresult.getResultObjects();
                }

                if(entries != null) {
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        if (entry.toJSON().has("price1")) {
                            isContains = true;
                        }
                    }
                    assertTrue(!isContains);
                }
            }
        });
    }




    /*public void test_30_afterUid()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.afterUid("blt127655bd036aad42");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    result[0] = queryresult.getResultObjects();

                } else {
                    result[0] = error.getErrorCode();

                }
            }
        });

        assertEquals(1, ((List<Entry>)result[0]).size());
    }

    public void test_31_beforeUid()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.beforeUid("blt127655bd036aad42");


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    result[0] = queryresult.getResultObjects();

                } else {
                    result[0] = error.getErrorCode();

                }
            }
        });

        assertEquals(20, ((List<Entry>)result[0]).size());
    }*/





    @Test
    public void test_32_tags()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.tags(new String[]{"pink"});
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }
                assertEquals(1, entries.size());
            }
        });


    }




    @Test
    public void test_33_language()  {

        ContentType ct = stack.contentType("product");
        final Query query = ct.query();
        query.language(Language.ENGLISH_UNITED_KINGDOM);


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                }
                assertEquals(4, entries.size());
            }
        });


    }





    @Test
    public void test_34_includeCount()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeCount();
        query.where("uid","blt3976eac6d3a0cb74");
        final Object result[] = new Object[2];

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    result[0] = queryresult.getResultObjects();
                    result[1] = queryresult.getCount();

                } else {
                    result[0] = error.getErrorCode();

                }
            }
        });

        if((List<Entry>)result[0] != null) {
            assertEquals(1, (int)result[1]);
        }
    }




    @Test
    public void test_35_includeReferenceOnly_fetch()  {

        final Query query = stack.contentType("multifield").query();
        query.where("uid", "blt1b1cb4f26c4b682e");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("title");

        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("title");
        strings1.add("brief_description");
        strings1.add("discount");
        strings1.add("price");
        strings1.add("in_stock");

        query.onlyWithReferenceUid(strings, "package_info.info_category");
        query.exceptWithReferenceUid(strings1, "product_ref");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    String uid = queryresult.getResultObjects().get(0).getString("uid");
                    assertEquals("blt1b1cb4f26c4b682e", uid);
                }
            }
        });
    }





    @Test
    public void test_36_includeReferenceExcept_fetch()  {

        final Query query = stack.contentType("product").query().where("uid", "blt7801c5d40cbbe979");

        ArrayList<String> strings = new ArrayList<>();
        strings.add("title");

        query.exceptWithReferenceUid(strings, "category");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    String result = queryresult.getResultObjects().get(0).getString("title");
                    assertEquals("laptop", result);
                }
            }
        });

    }





    @Test
    public void test_37_findOne() {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.where("in_stock", true);
        query.where("title", "halloween dress");
        final Object result[] = new Object[2];
        query.findOne(new SingleQueryResultCallback() {
            @Override
            public void onCompletion(ResponseType responseType, Entry entry, Error error) {

                if (error == null) {
                    result[0] = entry;
                    assertEquals(entry.getUid(), "bltbb44e49e6148eb30");
                } else {
                    result[0] = error.getErrorCode();

                }
            }
        });

        //assertEquals(entry.getUid(), "bltbb44e49e6148eb30"/*"blte88d9bec040e7c7c"*/);
    }



    //Char count 8209 will passed...
    @Test
    public void test_38_complexFind()  {

        ContentType contentType = stack.contentType("product");
        Query query = contentType.query();
        query.notEqualTo("title", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.************************************Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.*******");
        query.includeSchema();
        query.includeCount();

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    System.out.println("responseType = [" + responseType + "], queryresult = [" + queryresult.getResultObjects().size() + "]");
                }
                assertEquals(27, entries.size());
            }
        });


    }




    @Test
    public void test_39_includeContentType()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeContentType();

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                String stringResult = null;
                if (error == null) {
                    stringResult = (String) queryresult.getResultObjects().get(0).get("title");
                    Log.e(TAG, stringResult);
                }

                assertEquals("Blue Dress", stringResult);
            }
        });
    }




    @Test
    public void test_40_includeSchemaAndContentType() {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeSchema();
        query.includeContentType();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                if (error == null) {
                    String resultString = queryresult.getResultObjects().get(0).getString("title");
                    assertEquals("Blue Dress", resultString);
                }
            }
        });

    }





    @Test
    public void test_40_includeContentTypeAndSchema()  {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeContentType();
        query.includeSchema();


        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                JSONObject contentType = null;
                if (error == null) {
                    contentType = queryresult.getContentType();
                }
                assertTrue(contentType !=  null);
            }
        });


    }




    @Test
    public void test_39_addParams()  {

        ContentType contentType = stack.contentType("product");
        Query query = contentType.query();
        final Object result[] = new Object[] { new Object() };
        query.addParam("key","sample_value");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    result[0] = queryresult.getResultObjects();
                    System.out.println("responseType = [" + responseType + "], queryresult = [" + queryresult.getResultObjects().size() + "]");

                } else {

                }
            }
        });


    }

}
