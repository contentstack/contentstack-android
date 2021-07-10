package com.contentstack.sdk;

/**
 * @author Contentstack.com, Inc callback.
 */
public abstract class ContentTypesCallback extends ResultCallBack {

    public abstract void onCompletion(ContentTypesModel contentTypesModel, Error error);

    void onRequestFinish(ContentTypesModel contentTypesModel) {
        onCompletion(contentTypesModel, null);
    }

    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(null, error);
    }

    @Override
    public void always() {

    }

}
