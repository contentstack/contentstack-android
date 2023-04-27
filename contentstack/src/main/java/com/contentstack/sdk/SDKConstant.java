package com.contentstack.sdk;

import java.util.ArrayList;

/**
 * The Contentstack Sdk contain constants.
 *
 * @author contentstack.com, Inc
 */
public class SDKConstant {

    /**
     * The constant debug.
     */
    public static final boolean debug = false;
    /**
     * The constant IS_NETWORK_AVAILABLE.
     */
    public static boolean IS_NETWORK_AVAILABLE = true;
    /**
     * The constant PROTOCOL.
     */
    public static String PROTOCOL = "https://";
    /**
     * The constant SDK_VERSION.
     */
    public static String SDK_VERSION = "3.12.0";
    /**
     * The constant NO_NETWORK_CONNECTION.
     */
    public final static int NO_NETWORK_CONNECTION = 408;
    /**
     * The constant TimeOutDuration.
     */
    public final static int TimeOutDuration = 30000; // timeout in millisecond
    /**
     * The constant NumRetry.
     */
    public final static int NumRetry = 0;
    /**
     * The constant BackOFMultiplier.
     */
    public final static int BackOFMultiplier = 0;

    /**
     * The constant cancelledCallController.
     */
//Implemented for single network call cancellation. for class-level network call cancellation.
    public static ArrayList<String> cancelledCallController = new ArrayList<String>();

    /**
     * Directory path to store offline data.
     * used to saved cached network calls with response.
     */
    public static String cacheFolderName;

    /**
     * The enum Request method.
     */
    public static enum RequestMethod {

        /**
         * Get request method.
         */
        GET,
        /**
         * Post request method.
         */
        POST,
        /**
         * Put request method.
         */
        PUT,
        /**
         * Delete request method.
         */
        DELETE
    }

    /**
     * The enum Call controller.
     */
    public static enum callController {
        /**
         * Query call controller.
         */
        QUERY,
        /**
         * Entry call controller.
         */
        ENTRY,
        /**
         * Stack call controller.
         */
        STACK,
        /**
         * Asset call controller.
         */
        ASSET,
        /**
         * Sync call controller.
         */
        SYNC,
        /**
         * Content types call controller.
         */
        CONTENT_TYPES,
        /**
         * Asset library call controller.
         */
        ASSET_LIBRARY;
    }


    /**
     * The constant PLEASE_PROVIDE_VALID_JSON.
     */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final static String PLEASE_PROVIDE_VALID_JSON = "Please provide valid JSON.";
    /**
     * The constant ERROR_MESSAGE_STACK_CONTEXT_IS_NULL.
     */
    public final static String ERROR_MESSAGE_STACK_CONTEXT_IS_NULL = "Context can not be null.";
    /**
     * The constant ERROR_MESSAGE_STACK_API_KEY_IS_NULL.
     */
    public final static String ERROR_MESSAGE_STACK_API_KEY_IS_NULL = "Stack api key can not be null.";
    /**
     * The constant PLEASE_SET_CONTENT_TYPE_NAME.
     */
    public final static String PLEASE_SET_CONTENT_TYPE_NAME = "Please set contentType name.";
    /**
     * The constant PLEASE_SET_ENTRY_UID.
     */
    public final static String PLEASE_SET_ENTRY_UID = "Please set entry uid.";
    /**
     * The constant ERROR_MESSAGE_STACK_ACCESS_TOKEN_IS_NULL.
     */
    public final static String ERROR_MESSAGE_STACK_ACCESS_TOKEN_IS_NULL = "Access token can not be null.";
    /**
     * The constant ERROR_MESSAGE_STACK_ENVIRONMENT_IS_NULL.
     */
    public final static String ERROR_MESSAGE_STACK_ENVIRONMENT_IS_NULL = "Environment can not be null.";
    /**
     * The constant CONNECTION_ERROR.
     */
    public final static String CONNECTION_ERROR = "Connection error";
    /**
     * The constant AUTHENTICATION_NOT_PRESENT.
     */
    public final static String AUTHENTICATION_NOT_PRESENT = "Authentication Not present.";
    /**
     * The constant PARSING_ERROR.
     */
    public final static String PARSING_ERROR = "Parsing Error.";
    /**
     * The constant TRY_AGAIN.
     */
    public final static String TRY_AGAIN = "Server interaction went wrong, Please try again.";
    /**
     * The constant ERROR_MESSAGE_DEFAULT.
     */
    public final static String ERROR_MESSAGE_DEFAULT = "Oops! Something went wrong. Please try again.";
    /**
     * The constant NOT_AVAILABLE.
     */
    public final static String NOT_AVAILABLE = "Network not available.";
    /**
     * The constant STACK_FIRST.
     */
    public final static String STACK_FIRST = "You must called Contentstack.stack() first";
    /**
     * The constant PROVIDE_VALID_PARAMS.
     */
    public final static String PROVIDE_VALID_PARAMS = "Please provide valid params.";
    /**
     * The constant ENTRY_IS_NOT_PRESENT_IN_CACHE.
     */
    public final static String ENTRY_IS_NOT_PRESENT_IN_CACHE = "ENTRY is not present in cache";
    /**
     * The constant NETWORK_CALL_RESPONSE.
     */
    public final static String NETWORK_CALL_RESPONSE = "Error while saving network call response.";
}
