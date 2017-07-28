package com.builtio.contentstack.utilities;

import java.util.ArrayList;

/**
 *
 * @author built.io, Inc
 *
 */
public class CSAppConstants {

    public static final boolean debug = false;
    public static final String SYNC_STORE_DEFAULT_TIMESTAMP  = "2010-01-01T00:00:00.000Z";;
    public static boolean isNetworkAvailable = true;

    public static String URLSCHEMA_HTTP = "http://";
    public static String URLSCHEMA_HTTPS = "https://";

    public static String SDK_VERSION = "3.1.3";

    public final static int NONETWORKCONNECTION = 408;

    public final static int TimeOutDuration = 15000;
    public final static int NumRetry = 0;
    public final static int BackOFMultiplier = 0;

    //Implemented for single network call cancellation. for class-level network call cancellation.
    public static ArrayList<String> cancelledCallController = new ArrayList<String>();

    /**
     * Directory path to store offline data.
	 * used to saved cached network calls with response.
	 */
    public static String cacheFolderName ;

    public static enum RequestMethod {

        GET, POST, PUT, DELETE
    }

    public static enum callController {

        QUERY,
        ENTRY,
        STACK,
        ASSET,
        ASSETLIBRARY;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final static String ErrorMessage_JsonNotProper 			 = "Please provide valid JSON.";
    public final static String ErrorMessage_StackContextIsNull       = "Context can not be null.";
    public final static String ErrorMessage_StackApiKeyIsNull        = "Stack api key can not be null.";
    public final static String ErrorMessage_FormName 				 = "Please set contentType name.";
    public final static String ErrorMessage_EntryUID 				 = "Please set entry uid.";
    public final static String ErrorMessage_Stack_AccessToken_IsNull = "Access token can not be null.";
    public final static String ErrorMessage_Stack_Environment_IsNull = "Environment can not be null.";
    public final static String ErrorMessage_VolleyNoConnectionError  = "Connection error";
    public final static String ErrorMessage_VolleyAuthFailureError 	 = "Authentication Not present.";
    public final static String ErrorMessage_VolleyParseError 		 = "Parsing Error.";
    public final static String ErrorMessage_VolleyServerError 		 = "Server interaction went wrong, Please try again.";
    public final static String ErrorMessage_Default 				 = "Oops! Something went wrong. Please try again.";
    public final static String ErrorMessage_NoNetwork 				 = "Network not available.";
    public final static String ErrorMessage_CalledDefaultMethod      = "You must called Contentstack.stack() first";
    public final static String ErrorMessage_QueryFilterException 	 = "Please provide valid params.";
    public final static String ErrorMessage_EntryNotFoundInCache     = "ENTRY is not present in cache";
    public final static String ErrorMessage_SavingNetworkCallResponseForCache 	= "Error while saving network call response.";
}
