package com.contentstack.sdk;


/**
 * built.io callback to notify class after network call has been executed.
 *
 * @author Contentstack.com, Inc
 */

public abstract class ResultCallBack {

    abstract void onRequestFail(ResponseType responseType, Error error);

    abstract void always();
}
