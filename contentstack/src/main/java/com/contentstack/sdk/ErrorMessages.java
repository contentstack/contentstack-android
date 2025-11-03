package com.contentstack.sdk;

/**
 * Contains all error messages used across the SDK.
 * Centralizing messages here makes it easier to maintain and update them.
 */
public final class ErrorMessages {
    private ErrorMessages() {
        throw new IllegalStateException("Utility class - do not instantiate");
    }

    // Constructor related errors
    public static final String PRIVATE_CONSTRUCTOR_NOT_ALLOWED = 
        "This class does not support private constructors. Use a public constructor to create an instance.";
    public static final String UTILITY_CLASS_INSTANTIATION = 
        "This is a utility class and cannot be instantiated";
    public static final String NODE_TO_HTML_INSTANTIATION = 
        "Failed to create an instance of NodeToHTML, you can directly access the methods of this class";

    // Input validation errors
    public static final String NULL_OR_EMPTY_INPUT = 
        "The input value cannot be null or empty. Provide a valid string to continue.";
    
    // Network and parsing errors
    public static final String ENCODING_ERROR = 
        "The system encountered an encoding issue while processing your request. Try again or contact support.";
    public static final String JSON_PARSING_ERROR = 
        "We couldn't process the response due to a data formatting issue. Try again or contact support if the problem persists.";

    // Cache related errors
    public static final String CACHE_INITIALIZATION_ERROR = 
        "Failed to initialize cache. The application will continue without caching.";
}
