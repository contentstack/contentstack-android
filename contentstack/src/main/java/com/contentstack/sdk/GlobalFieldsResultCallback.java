package com.contentstack.sdk;

public abstract class GlobalFieldsResultCallback extends ResultCallBack{
    /**
     * Triggered after call execution complete.
     *
     * @param globalFieldsModel {@link GlobalFieldsModel} instance if call success else null.
     * @param error        {@link Error} instance if call failed else null.
     */
    public abstract void onCompletion(GlobalFieldsModel globalFieldsModel, Error error);
    void onRequestFinish(GlobalFieldsModel globalFieldsModel) {
        onCompletion(globalFieldsModel, null);
    }
    @Override
    void onRequestFail(ResponseType responseType, Error error) {
        onCompletion(null, error);
    }
    @Override
    void always() {
    }
}
