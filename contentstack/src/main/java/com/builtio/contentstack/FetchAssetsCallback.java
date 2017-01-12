package com.builtio.contentstack;

import java.util.List;

/**
 * Created by chinmay on 7/10/16.
 */

public abstract class FetchAssetsCallback extends ResultCallBack{

    public abstract void onCompletion(ResponseType responseType, List<Asset> assets, Error error);

    public void onRequestFinish(ResponseType responseType, List<Asset> assets){
        onCompletion(responseType, assets, null);
    }

    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(responseType, null, error);
    }

    @Override
    void always() {

    }
}
