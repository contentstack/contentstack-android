package com.builtio.contentstack;


/**
 *
 * @author built.io, Inc
 *
 */
public abstract class QueryResultsCallBack extends ResultCallBack {

    public abstract void onCompletion(ResponseType responseType, QueryResult queryresult, Error error);

    void onRequestFinish(ResponseType responseType, QueryResult queryResultObject){
        onCompletion(responseType, queryResultObject, null);
    }

    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(responseType, null, error);
    }

    @Override
    void always() {

    }


}
