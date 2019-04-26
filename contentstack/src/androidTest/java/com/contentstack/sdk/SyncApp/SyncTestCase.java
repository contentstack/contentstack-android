package com.contentstack.sdk.SyncApp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.contentstack.sdk.BuildConfig;
import com.contentstack.sdk.Config;
import com.contentstack.sdk.ContentType;
import com.contentstack.sdk.ContentTypesCallback;
import com.contentstack.sdk.ContentTypesModel;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.Language;
import com.contentstack.sdk.Stack;
import com.contentstack.sdk.SyncResultCallBack;
import com.contentstack.sdk.SyncStack;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Shailesh Mishra on 29-Mar-2018.
 */

@RunWith(AndroidJUnit4.class)
public class SyncTestCase {

    private Stack stack;
    private static final String prod_api_key = "blt477ba55f9a67bcdf";
    private static final String prod_delivery_Token = "cs7731f03a2feef7713546fde5";
    private static final String environment = "web";

    private int itemsSize = 0;
    private int counter = 0;
    private String dateISO = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    public SyncTestCase() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();

        config.setHost(BuildConfig.base_url);
        stack = Contentstack.stack(appContext, prod_api_key, prod_delivery_Token, environment, config);
    }


    @Test
    public void test00_SyncInit() {

        stack.sync(new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

                if (error == null) {
                    itemsSize += syncStack.getItems().size();
                    counter = syncStack.getCount();
                }

            }});

        assertEquals(itemsSize, counter);
    }




    @Test
    public void test03_sync_with_syncToken() {
        //bltbb61f31a70a572e6c9506a
        stack.syncToken("bltbb61f31a70a572e6c9506a", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

                if (error == null) {
                    itemsSize = syncStack.getItems().size();
                    counter = syncStack.getCount();
                }
            }
        });

        assertEquals( itemsSize, counter);
    }


    @Test
    public void testPaginationToken() {
        stack.syncPaginationToken("blt7f35951d259183fba680e1", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

                if (error == null){
                    itemsSize = syncStack.getItems().size();
                    counter = syncStack.getCount();
                }

                assertEquals( 25,  itemsSize);

            }
        });

    }



    @Test
    public void test02__syncWithDate() throws ParseException {

        final Date start_date = sdf.parse("2018-10-07");
        stack.syncFromDate(start_date, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                if (error == null) {

                    itemsSize = syncStack.getItems().size();
                    counter = syncStack.getCount();
                    for (JSONObject jsonObject1 : syncStack.getItems()) {
                        if (jsonObject1.has("event_at")) {

                            dateISO = jsonObject1.optString("event_at");
                            String serverDate = returnDateFromISOString(dateISO);
                            Date dateServer = null;
                            try {
                                dateServer = sdf.parse(serverDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            int caparator = dateServer.compareTo(start_date);
                            assertEquals(1, caparator);
                        }
                    }
                }

                assertEquals(123, counter);
            }});
    }





    @Test
    public void test04_sync_with_contentType() {

        stack.syncContentType("session", new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                if (error == null) {
                    counter = syncStack.getItems().size();
                    itemsSize = syncStack.getCount();
                }
            }
        });

        assertEquals(counter, itemsSize);
    }



    @Test
    public void test05_sync_with_locale() {

        stack.syncLocale(Language.ENGLISH_UNITED_STATES, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

                if (error == null) {
                    counter = syncStack.getCount();
                    ArrayList<JSONObject> items = syncStack.getItems();
                    String dataObject = null;
                    for (JSONObject object: items){
                        if (object.has("data"))
                            dataObject = object.optJSONObject("data").optString("locale");

                        if (!dataObject.isEmpty()) {
                            assertEquals("en-us", dataObject);
                        }
                    }
                }
            }
        });

    }




    @Test
    public void testPublishType() {

        stack.syncPublishType(Stack.PublishType.entry_published, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {
                if (error == null) {
                    // Success block
                    counter = syncStack.getItems().size();
                    itemsSize = syncStack.getCount();
                }else {
                    // Error block
                }
            }
        });

        assertEquals(counter, itemsSize);
    }





    @Test
    public void test06_sync_with_all() throws ParseException {

        Date start_date = sdf.parse("2018-10-10");
        stack.sync( "session", start_date, Language.ENGLISH_UNITED_STATES, Stack.PublishType.entry_published, new SyncResultCallBack() {
            @Override
            public void onCompletion(SyncStack syncStack, Error error) {

                if (error == null) {
                    itemsSize = syncStack.getItems().size();
                    counter = syncStack.getCount();
                }

                assertEquals(counter, itemsSize);
            }

        });
    }


    @Test
    public void getAllContentTypes() {

        stack.getContentTypes( new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                Log.e("getAllContentTypes reponse: ", contentTypesModel.getResponse().toString());
            }
        });

    }



    @Test
    public void getSingleContentType() {

        ContentType contentType = stack.contentType("schema");
        contentType.fetch(new ContentTypesCallback() {
            @Override
            public void onCompletion(ContentTypesModel contentTypesModel, Error error) {
                if (error==null){
                    Log.e("single content:", contentTypesModel.getResponse().toString());
                }else {
                    Log.e("Error", error.getErrorMessage());
                }

                assertEquals(100, itemsSize);
            }
        });

    }



    public String convertUTCToISO(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        dateFormat.setTimeZone(tz);
        Log.e("Date to ISO------>",dateFormat.format(date));
        return dateFormat.format(date);
    }



    public String returnDateFromISOString(String isoDateString) {
        String[] dateFormate = isoDateString.split("T");
        return dateFormate[0];
    }

}
