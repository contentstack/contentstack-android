package com.contentstack.sdk.tickets;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.contentstack.sdk.Config;
import com.contentstack.sdk.ContentType;
import com.contentstack.sdk.ContentTypesCallback;
import com.contentstack.sdk.ContentTypesModel;
import com.contentstack.sdk.Contentstack;
import com.contentstack.sdk.Entry;
import com.contentstack.sdk.Error;
import com.contentstack.sdk.Query;
import com.contentstack.sdk.QueryResult;
import com.contentstack.sdk.QueryResultsCallBack;
import com.contentstack.sdk.ResponseType;
import com.contentstack.sdk.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@MediumTest
public class TicketsTestcase extends JUnitCore {

    private Stack stack;


    @Before
    public void startApp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
        String STACK_API_KEY = "blt4c0468fe43dc5bdd";
        String ACCESS_TOKEN = "csbb1543164d7a0684b5a0f87f";
        String ENV = "staging";
        stack = Contentstack.stack(appContext, STACK_API_KEY, ACCESS_TOKEN, ENV);
    }


    @Test
    public void TicketONE(){

        Query query = stack.contentType("help_center_topic").query();
        query.find(new QueryResultsCallBack() {
            @Override
            public void onCompletion(ResponseType responseType, QueryResult queryResult, Error error) {

                if (error == null) {
                    Log.e("Title Size : ",""+queryResult.getResultObjects().size());
                    for (Entry entry: queryResult.getResultObjects()) {
                        Log.e("Title :",""+entry.getString("title"));
                    }
                } else {
                    System.out.println("Failed: " + error.getErrorMessage() + " " + error.getErrorCode());
                }
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
            }
        });

        //assertEquals(100, itemsSize);
    }
}
