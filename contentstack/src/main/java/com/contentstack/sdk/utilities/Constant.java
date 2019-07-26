package com.contentstack.sdk.utilities;

/**
 *
 *
 * @author Contentstack, Inc
 *
 */
public class Constant {

    // Contentstack server error response status code.

    public static final int UNAUTHORIZED_ACCESS 						= 105;
    public static final int FORGOT_PASSWORD_USER_NOT_FOUND 				= 107;
    public static final int APPLICATION_NOT_FOUND 						= 109;
    public static final int INVALID_CLASS 								= 118;
    public static final int OBJECT_CREATE_FAILED 						= 119;
    public static final int INVALID_OBJECT_CLASS_UID 					= 120;
    public static final int OBJECT_UPDATE_FAILED 						= 121;
    public static final int OBJECT_DELETE_FAILED						= 122;
    public static final int OBJECT_UPSERT_FAILED						= 123;
    public static final int NOTIFICATION_CREATE_FAILED 					= 124;
    public static final int NOTIFICATION_DELETE_FAILED 					= 125;
    public static final int INVALID_NOTIFICATION						= 126;
    public static final int APPLICATION_USER_CREATE_FAILED				= 127;
    public static final int APPLICATION_USER_UPDATE_FAILED				= 128;
    public static final int APPLICATION_USER_DELETE_FAILED				= 129;
    public static final int APPLICATION_USER_NOT_FOUND					= 130;
    public static final int APPLICATION_USER_LOGGED_IN_FAILED			= 131;
    public static final int APPLICATION_USER_LOGGED_OUT_FAILED			= 132;
    public static final int ACCESS_DENIED								= 133;
    public static final int NOTIFICATION_CREDENTIALS_CREATE_FAILED 		= 134;
    public static final int NOTIFICATION_CREDENTIALS_UPDATE_FAILED 		= 135;
    public static final int INSTALLATION_NOT_CREATED 					= 136;
    public static final int INSTALLATION_NOT_UPDATED					= 137;
    public static final int INSTALLATION_FAILED_SUBSCRIBE 				= 138;
    public static final int INSTALLATION_FAILED_UNSUBSCRIBE 			= 139;
    public static final int CHANNELS_NOT_PROVIDED_FOR_SUB_UNSUB 		= 140;
    public static final int INVALID_PARAMETERS 							= 141;
    public static final int UPLOAD_CREATE_FAILED 						= 142;
    public static final int UPLOAD_UPDATE_FAILED 						= 143;
    public static final int UPLOAD_DELETE_FAILED						= 144;
    public static final int UPLOAD_NOT_FOUND 							= 145;
    public static final int APPLICATION_USER_INVALID_EMAIL				= 148;
    public static final int APPLICATION_USER_RESET_PASSWORD_FAILED		= 149;
    public static final int APPLICATION_SETTING_FAILED 					= 150;
    public static final int APPLICATION_USER_DELETE_ALL 				= 152;
    public static final int CUSTOM_NOTIFICATION_NOT_FOUND 				= 153;
    public static final int CUSTOM_NOTIFICATION_NOT_CREATED 			= 154;
    public static final int CUSTOM_NOTIFICATION_NOT_UPDATED 			= 155;
    public static final int SYSTEM_ROLE_CREATE_FAILED 					= 157;
    public static final int MARKER_CREATE_FAILED 						= 167;
    public static final int MARKER_UPDATE_FAILED 						= 168;
    public static final int MARKER_DELETE_FAILED 						= 169;
    public static final int INVALID_MARKER 								= 170;
    public static final int APPLICATION_USER_UID_RETRIEVE_FAILED 		= 176;
    public static final int UPLOAD_DEFAULT_ACL_UPDATE_FAILED 			= 185;
    public static final int APPLICATION_USER_ACCOUNT_INACTIVE 			= 190;
    public static final int APPLICATION_USER_REGISTER_EMAIL_TAKEN 		= 191;
    public static final int APPLICATION_USER_REGISTER_PASSWORD_INVALID 	= 192;
    public static final int INTERNAL_SERVER_ERROR 						= 194;
    public static final int NOT_FOUND_ERROR 							= 195;
    public static final int ACCOUNT_NAME_BLANK 							= 196;
    public static final int CLASS_UID_BLANK 							= 197;
    public static final int INVALID_API_KEY 							= 198;
    public static final int DELETE_USER_NOT_FOUND 						= 200;
    public static final int REGISTRATION_NOT_CREATED 					= 217;
    public static final int REGISTRATION_NOT_UPDATED 					= 218;
    public static final int REGISTRATION_NOT_FOUND 						= 219;
    public static final int APPLICATION_LIMIT_REACHED 					= 232;

    //////////////////////////////////////////////////////////////////////////////////////

    /**
     * Retrieve error details.
     */
    public static final String KEY_ERROR   = "errors";
}
