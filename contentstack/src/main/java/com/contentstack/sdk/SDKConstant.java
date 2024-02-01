package com.contentstack.sdk;

import java.util.ArrayList;

public class SDKConstant {

    protected static final String ENVIRONMENT = "environment";
    protected static final String CACHE = "ContentstackCache";
    public static final boolean debug = false;
    public static boolean IS_NETWORK_AVAILABLE = true;
    public static String PROTOCOL = "https://";
    public static String SDK_VERSION = "3.13.0";
    public final static int NO_NETWORK_CONNECTION = 408;
    public final static int TimeOutDuration = 30000; // timeout in millisecond
    public final static int NumRetry = 0;
    public final static int BackOFMultiplier = 0;
    public static ArrayList<String> cancelledCallController = new ArrayList<String>();
    public static String cacheFolderName;

    public static enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public static enum callController {
        QUERY,
        ENTRY,
        STACK,
        ASSET,
        SYNC,
        CONTENT_TYPES,
        ASSET_LIBRARY;
    }


    public final static String PLEASE_PROVIDE_VALID_JSON = "Please provide valid JSON.";
    public final static String EMPTY_CREDENTIALS_NOT_ALLOWED = "Empty credentials are not allowed, Please provide a valid one";
    public final static String PLEASE_SET_CONTENT_TYPE_NAME = "Please set contentType name.";
    public final static String PLEASE_SET_ENTRY_UID = "Please set entry uid.";
    public final static String CONNECTION_ERROR = "Connection error";
    public final static String AUTHENTICATION_NOT_PRESENT = "Authentication Not present.";
    public final static String PARSING_ERROR = "Parsing Error.";
    public final static String TRY_AGAIN = "Server interaction went wrong, Please try again.";
    public final static String ERROR_MESSAGE_DEFAULT = "Oops! Something went wrong. Please try again.";
    public final static String NOT_AVAILABLE = "Network not available.";
    public final static String STACK_FIRST = "You must called Contentstack.stack() first";
    public final static String PROVIDE_VALID_PARAMS = "Please provide valid params.";
    public final static String ENTRY_IS_NOT_PRESENT_IN_CACHE = "ENTRY is not present in cache";
    public final static String NETWORK_CALL_RESPONSE = "Error while saving network call response.";
}
