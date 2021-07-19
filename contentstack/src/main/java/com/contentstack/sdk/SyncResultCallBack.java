package com.contentstack.sdk;

/**
 * @author Contentstack.com, Inc callback.
 */
public abstract class SyncResultCallBack extends ResultCallBack {


    public abstract void onCompletion(SyncStack syncStack, Error error);

    void onRequestFinish(SyncStack syncStack) {
        onCompletion(syncStack, null);
    }

    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(null, error);
    }

    @Override
    void always() {

    }

}
