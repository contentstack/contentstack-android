package com.contentstack.sdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;
import org.junit.Test;
import org.junit.runner.JUnitCore;


public class TicketsTestcase extends JUnitCore {

    private final String STACK_API_KEY = "blt4c0468fe43dc5bdd";
    private final String ACCESS_TOKEN = "csbb1543164d7a0684b5a0f87f";
    private final String ENV = "staging";
    private Stack stack;

    public TicketsTestcase() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        Config config = new Config();
        config.setHost("cdn.contentstack.io");
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

        ContentType  contentType = stack.contentType("schema");
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
