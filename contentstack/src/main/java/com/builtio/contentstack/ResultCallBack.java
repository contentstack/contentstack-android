package com.builtio.contentstack;


/**
 * built.io callback to notify class after network call has been executed.
 * 
 * @author built.io, Inc
 *
 */
public abstract class ResultCallBack {

    abstract void onRequestFail(ResponseType responseType, Error error);
	abstract void always();
}
