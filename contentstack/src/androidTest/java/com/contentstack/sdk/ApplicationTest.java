package com.contentstack.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by Shailesh Mishra.
 *
 * Contentstack pvt Ltd
 *
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ApplicationTest  {

    private static final String TAG = ApplicationTest.class.getSimpleName();
    private Stack stack;
    private String[] uidArray;
    private ArrayList<Entry> entries = null;
    private int count = 0;


    public ApplicationTest() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost(BuildConfig.base_url);

        stack = Contentstack.stack(appContext,
                BuildConfig.default_api_key,
                BuildConfig.default_access_token,
                BuildConfig.default_env,config);

        uidArray = new String[]{"blte88d9bec040e7c7c", "bltdf783472903c3e21"};
    }


    @Test
    public void test_00_fetchAllEntries()  {

        Query query = stack.contentType("product").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                String string = null;
                int counter = 0;
                if (error == null) {
                    counter = queryresult.getResultObjects().size();
                    assertEquals(counter, queryresult.getResultObjects().size());
                    //string = queryresult.getResultObjects().get(2).getString("short_description");
                    //assertEquals("halloween dress", string);
                }

            }
        });

    }




    @Test
    public void test_01_fetchEntriesOfNonExistingContentType() {

        Query query = stack.contentType("products").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                String title = null;
                if (error == null) {
                    title = queryresult.getResultObjects().get(0).getString("title");
                    assertEquals(title, title);
                } else {
                    title = error.getErrorMessage().trim();
                    assertEquals("The Content Type 'products' was not found. Please try again.", title);
                }

            }
        });
    }




    @Test
    public void test_02_fetchSingleEntry() throws InterruptedException,ClassCastException {

        Query query = stack.contentType("categories").query();
        query.where("uid","blta3b58d6893d8935d");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                String title = null;
                if (error == null) {
                    title = queryresult.getResultObjects().get(0).getString("title");
                    assertEquals("Book", title);
                }

            }
        });
    }




    @Test
    public void test_03_fetchSingleNonExistingEntry()throws InterruptedException,ClassCastException {
        Query query = stack.contentType("categories").query();
        query.where("uid","blta3b58d6893d8935b");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    int size = queryresult.getResultObjects().size();
                    assertEquals(0, size);
                }
            }
        });
    }



    @Test
    public void test_04_fetchEntryWithIncludeReference() throws InterruptedException {
        Query query = stack.contentType("product").query();
        query.includeReference("category");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    JSONArray  categoryArray = (JSONArray) queryresult.getResultObjects().get(0).get("category");
                    try {
                        assertTrue(categoryArray.get(0) instanceof JSONObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }



    @Test
    public void test_05_fetchEntryNotContainedInField() throws InterruptedException {


        Query query = stack.contentType("product").query();
        query.notContainedIn("uid", uidArray);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    entries = (ArrayList<Entry>) queryresult.getResultObjects();
                }
            }
        });

        if(entries != null) {
            boolean isContains = false;
            for (Entry entry : entries) {
                if (Arrays.asList(uidArray).contains(entry.getUid())) {
                    isContains = true;
                }
            }
            assertTrue(!isContains);
        }
    }



    @Test
    public void test_06_fetchEntryContainedInField() throws InterruptedException {

        Query query = stack.contentType("product").query();
        query.containedIn("uid", uidArray);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        if(Arrays.asList(uidArray).contains(entry.getUid())){
                            count++;
                        }
                    }

                    assertTrue(count.equals(entries.size()));
                }
            }
        });


    }



    @Test
    public void test_07_fetchEntryNotEqualToField() throws InterruptedException, ParseException {

        Query query = stack.contentType("product").query();
        query.notEqualTo("title", "yellow t shirt");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    queryresult.getResultObjects().size();
                    assertTrue(count == 0);
                }
            }
        });


    }



//    @Test
//    public void test_08_fetchEntryGreaterThanEqualToField() throws InterruptedException, ParseException {
//
//        Query query = stack.contentType("product").query();
//
//        query.greaterThanOrEqualTo("price", 90);
//        query.find(new QueryResultsCallBack() {
//            @Override
//            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
//
//                if (error == null) {
//                    count = queryresult.getCount();
//                    result[0] = queryresult.getResultObjects();
//
//                } else {
//
//                }
//            }
//        });
//
//        assertEquals(count, entries.size());
//    }




    @Test
    public void test_09_fetchEntryGreaterThanField() throws InterruptedException, ParseException {

        Query query = stack.contentType("product").query();
        query.greaterThan("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                int counter = 0;
                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    for (Entry entry: entries) {
                        if((Integer)entry.get("price") >= 90) {
                            counter += (Integer)entry.get("price");
                        }
                    }

                    assertEquals(4991, counter);
                }
            }
        });


    }



    @Test
    public void test_10_fetchEntryLessThanEqualField() throws InterruptedException, ParseException {

        Query query = stack.contentType("product").query();
        query.lessThanOrEqualTo("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry>[] result = new List[0];

                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
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
    public void test_11_fetchEntryLessThanField() throws InterruptedException, ParseException {

        Query query = stack.contentType("product").query();
        query.lessThan("price", 90);
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    List<Entry> entryList = queryresult.getResultObjects();
                    Integer count = 0;
                    for (Entry entry : entryList) {
                        System.out.println(entry.get("price"));
                        if ((Integer) entry.get("price") == 90) {
                            count++;
                        }
                    }
                }
                assertTrue(count==0);
            }
        });


    }



    @Test
    public void test_12_fetchEntriesWithOr() throws InterruptedException, ParseException {

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
                    final List<Entry> entries = queryresult.getResultObjects();
                    if (entries.size()>0){
                        assertTrue(true);
                    }
                    //;Equals(18,  entries.size());
                }

            }
        });



    }



    @Test
    public void test_13_fetchEntriesWithAnd() throws InterruptedException, ParseException {

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

                if (error == null) {
                    List<Entry>[] result = new List[0];
                    List<Entry> entries = queryresult.getResultObjects();
                    Integer count = 0;
                    for(Entry entry: entries){
                        int discount = (Integer)entry.get("discount");
                        if((Integer)entry.get("price")<90 || (discount==20 || discount == 45)) {
                            count++;
                        }
                    }
                    assertTrue(count.equals(entries.size()));
                }
            }
        });


    }




    @Test
    public void test_14_addQueryInQuery() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.addQuery("limit", "8");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    int queryLength = 0;
                    queryLength = queryresult.getResultObjects().size();
                    assertEquals(8, queryLength);
                }


            }
        });

    }



    @Test
    public void test_15_removeQueryFromQuery() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.addQuery("limit", "8");
        query.removeQuery("limit");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    assertEquals(27, entries.size());
                }
            }
        });


    }



    @Test
    public void test_16_setWrongHeader() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.setHeader("site_api_key", "kuch bhi");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {

                    //assertEquals(28, queryresult.getResultObjects().size());
                }
            }
        });


    }




    @Test
    public void test_17_setHeader() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.setHeader("site_api_key", "blt920bb7e90248f607");
        query.setHeader("access_token", "blt0c4300391e033d4a59eb2857");
        query.setHeader("environment", "production");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    queryresult.getResultObjects();
                }
                //assertEquals(105, error.getErrorCode());
            }
        });


    }



//    @Test
//    public void test_18_includeSchema() throws InterruptedException, ParseException {
//
//        ContentType ct = stack.contentType("product");
//        Query query = ct.query();
//        query.includeSchema();
//
//        query.find(new QueryResultsCallBack() {
//            @Override
//            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
//                int length = 0;
//                if (error == null) {
//                     length = queryresult.getSchema().length();
//                }
//                assertEquals(10, queryresult.getSchema().length());
//            }
//        });
//
//
//    }




    @Test
    public void test_19_search() throws InterruptedException, ParseException {
        String head = "laptop";
        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.search("laptop");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                String title = null;
                if (error == null) {
                    title = queryresult.getResultObjects().get(0).getString("title");
                }
                //assertEquals("laptop", title);
            }
        });


    }




    @Test
    public void test_20_ascending() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.ascending("title");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    assertEquals("Blue Dress", queryresult.getResultObjects().get(0).getString("title"));
                }
            }
        });


    }




    /*@Test
    public void test_21_descending() throws InterruptedException, ParseException {
        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.descending("title");
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                int sizeOFContent = 0;
                if (error == null) {
                    sizeOFContent = queryresult.getResultObjects().size();
                }
                assertTrue(sizeOFContent == 27);
            }
        });
    }*/



    @Test
    public void test_22_limit() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.limit(3);

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                int countLimit = 0;
                if (error == null) {
                    countLimit = queryresult.getResultObjects().size();
                    assertTrue(countLimit == 3);
                }


            }
        });


    }




    @Test
    public void test_23_skip() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.skip(3);

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                int skipCounter = 0;
                if (error == null) {
                    skipCounter = queryresult.getResultObjects().size();
                    assertTrue(skipCounter == skipCounter);
                }

            }
        });


    }





    @Test
    public void test_24_only() throws InterruptedException, ParseException {

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
                        System.out.println("to JSON "+entry.toJSON() );
                        if (entry.toJSON().has("title")) {
                            isContains = true;
                        }
                    }
                    assertTrue(isContains == false);
                }
            }
        });


    }



    @Test
    public void test_25_except() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.except(new String[]{"price"});

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    List<Entry> entries = queryresult.getResultObjects();
                    if(entries != null) {
                        boolean isContains = false;
                        for (Entry entry : entries) {
                            System.out.println("to JSON "+entry.toJSON() );
                            if (entry.toJSON().has("price")) {
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
    public void test_26_count() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.count();

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                int coun = 0;
                if (error == null) {
                    coun = queryresult.getCount();
                    assertEquals(27, coun);
                }
            }
        });


    }





    @Test
    public void test_27_regex() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.regex("title", "lap*", "i");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    boolean isContains = false;
                    for (Entry entry : entries) {
                        System.out.println("to JSON "+entry.toJSON() );
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
    public void test_28_exist() throws InterruptedException, ParseException {

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
                        System.out.println("to JSON "+entry.toJSON() );
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
    public void test_29_notExist() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.notExists("price1");

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
                        System.out.println("to JSON "+entry.toJSON() );
                        if (entry.toJSON().has("price1")) {
                            isContains = true;
                        }
                    }
                    assertTrue(!isContains);
                }
            }
        });
    }




    @Test
    public void test_32_tags() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.tags(new String[]{"pink"});

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                if (error == null) {
                    int count = queryresult.getResultObjects().size();
                    assertEquals(1, count);
                }
            }
        });

    }




    @Test
    public void test_33_language() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.language(Language.ENGLISH_UNITED_KINGDOM);

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {

                List<Entry> entries = null;
                int counter = 0;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    counter = entries.size();
                }

                assertEquals(counter, entries.size());
            }
        });
    }




    @Test
    public void test_34_includeCount() throws InterruptedException, ParseException {

        ContentType ct = stack.contentType("product");
        Query query = ct.query();
        query.includeCount();
        query.where("uid", "blt3976eac6d3a0cb74");

        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryresult, Error error) {
                List<Entry> entries = null;
                if (error == null) {
                    entries = queryresult.getResultObjects();
                    if (entries != null) {
                        // assertEquals("blt3976eac6d3a0cb74", entries.get(0).getUid());
                    }
                }
            }
        });
    }
}