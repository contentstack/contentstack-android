package com.builtio.contentstack;

/**
 * Created by chinmay on 30/5/16.
 */
public abstract class ContentstackResultCallback extends ResultCallBack{


    public abstract void onCompletion(ResponseType responseType, Error error);

    public void onRequestFinish(ResponseType responseType){
        onCompletion(responseType, null);
    }

    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(responseType, error);
    }

    @Override
    void always() {

    }

}
