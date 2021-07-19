package com.contentstack.sdk;

import java.util.HashMap;

/**
 * To retrieve information related to network call failure.
 *
 * @author Contentstack.com, Inc
 */
public class Error {

    String errorMessage = null;
    int errorCode = 0;
    HashMap<String, Object> errorHashMap = new HashMap<>();

    /**
     * Returns error in string format.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  String errorString = error.getErrorMessage();
     *  </pre>
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    protected void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns error code.
     *
     * @return int value.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  int errorCode = error.getErrorCode();
     *  </pre>
     */
    public int getErrorCode() {
        return errorCode;
    }

    protected void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns error in {@linkplain HashMap} format where error is key and its respective information as HashMap&#39;s value.
     *
     * <br><br><b>Example :</b><br>
     * <pre class="prettyprint">
     *  HashMap&#60;String, Object&#62; errorHashMap = error.getErrors();
     * </pre>
     */
    public HashMap<String, Object> getErrors() {
        return errorHashMap;
    }

    protected void setErrors(HashMap<String, Object> errorHashMap) {
        this.errorHashMap = errorHashMap;
    }

}
