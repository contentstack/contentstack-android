package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for SDKConstant class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestSDKConstant {

    // ========== STRING CONSTANTS TESTS ==========

    @Test
    public void testProtocolConstant() {
        assertEquals("https://", SDKConstant.PROTOCOL);
        assertNotNull(SDKConstant.PROTOCOL);
    }

    @Test
    public void testSDKVersionConstant() {
        assertNotNull(SDKConstant.SDK_VERSION);
        assertFalse(SDKConstant.SDK_VERSION.isEmpty());
    }

    // ========== ERROR MESSAGE CONSTANTS TESTS ==========

    @Test
    public void testPleaseProvideValidJSONMessage() {
        assertEquals("Please provide valid JSON.", SDKConstant.PLEASE_PROVIDE_VALID_JSON);
        assertNotNull(SDKConstant.PLEASE_PROVIDE_VALID_JSON);
    }

    @Test
    public void testEmptyCredentialsMessage() {
        assertEquals("Empty credentials are not allowed, Please provide a valid one", 
                     SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED);
        assertNotNull(SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED);
    }

    @Test
    public void testPleaseSetContentTypeNameMessage() {
        assertEquals("Please set contentType name.", SDKConstant.PLEASE_SET_CONTENT_TYPE_NAME);
        assertNotNull(SDKConstant.PLEASE_SET_CONTENT_TYPE_NAME);
    }

    @Test
    public void testPleaseSetEntryUidMessage() {
        assertEquals("Please set entry uid.", SDKConstant.PLEASE_SET_ENTRY_UID);
        assertNotNull(SDKConstant.PLEASE_SET_ENTRY_UID);
    }

    @Test
    public void testConnectionErrorMessage() {
        assertEquals("Connection error", SDKConstant.CONNECTION_ERROR);
        assertNotNull(SDKConstant.CONNECTION_ERROR);
    }

    @Test
    public void testAuthenticationNotPresentMessage() {
        assertEquals("Authentication Not present.", SDKConstant.AUTHENTICATION_NOT_PRESENT);
        assertNotNull(SDKConstant.AUTHENTICATION_NOT_PRESENT);
    }

    @Test
    public void testParsingErrorMessage() {
        assertEquals("Parsing Error.", SDKConstant.PARSING_ERROR);
        assertNotNull(SDKConstant.PARSING_ERROR);
    }

    @Test
    public void testTryAgainMessage() {
        assertEquals("Server interaction went wrong, Please try again.", SDKConstant.TRY_AGAIN);
        assertNotNull(SDKConstant.TRY_AGAIN);
    }

    @Test
    public void testErrorMessageDefault() {
        assertEquals("Oops! Something went wrong. Please try again.", SDKConstant.ERROR_MESSAGE_DEFAULT);
        assertNotNull(SDKConstant.ERROR_MESSAGE_DEFAULT);
    }

    @Test
    public void testNotAvailableMessage() {
        assertEquals("Network not available.", SDKConstant.NOT_AVAILABLE);
        assertNotNull(SDKConstant.NOT_AVAILABLE);
    }

    @Test
    public void testStackFirstMessage() {
        assertEquals("You must called Contentstack.stack() first", SDKConstant.STACK_FIRST);
        assertNotNull(SDKConstant.STACK_FIRST);
    }

    @Test
    public void testProvideValidParamsMessage() {
        assertEquals("Please provide valid params.", SDKConstant.PROVIDE_VALID_PARAMS);
        assertNotNull(SDKConstant.PROVIDE_VALID_PARAMS);
    }

    @Test
    public void testEntryNotPresentInCacheMessage() {
        assertEquals("ENTRY is not present in cache", SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE);
        assertNotNull(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE);
    }

    @Test
    public void testNetworkCallResponseMessage() {
        assertEquals("Error while saving network call response.", SDKConstant.NETWORK_CALL_RESPONSE);
        assertNotNull(SDKConstant.NETWORK_CALL_RESPONSE);
    }

    @Test
    public void testPleaseProvideGlobalFieldUidMessage() {
        assertEquals("Please provide global field uid.", SDKConstant.PLEASE_PROVIDE_GLOBAL_FIELD_UID);
        assertNotNull(SDKConstant.PLEASE_PROVIDE_GLOBAL_FIELD_UID);
    }

    // ========== NUMERIC CONSTANTS TESTS ==========

    @Test
    public void testNoNetworkConnectionConstant() {
        assertEquals(408, SDKConstant.NO_NETWORK_CONNECTION);
    }

    @Test
    public void testTimeOutDurationConstant() {
        assertEquals(30000, SDKConstant.TimeOutDuration);
        assertTrue(SDKConstant.TimeOutDuration > 0);
    }

    @Test
    public void testNumRetryConstant() {
        assertEquals(0, SDKConstant.NumRetry);
        assertTrue(SDKConstant.NumRetry >= 0);
    }

    @Test
    public void testBackOFMultiplierConstant() {
        assertEquals(0, SDKConstant.BackOFMultiplier);
        assertTrue(SDKConstant.BackOFMultiplier >= 0);
    }

    // ========== BOOLEAN CONSTANTS TESTS ==========

    @Test
    public void testDebugConstant() {
        assertFalse(SDKConstant.debug);
    }

    @Test
    public void testIsNetworkAvailableConstant() {
        assertTrue(SDKConstant.IS_NETWORK_AVAILABLE);
    }

    // ========== ENUM TESTS ==========

    @Test
    public void testRequestMethodEnumValues() {
        SDKConstant.RequestMethod[] methods = SDKConstant.RequestMethod.values();
        assertEquals(4, methods.length);
        assertNotNull(SDKConstant.RequestMethod.GET);
        assertNotNull(SDKConstant.RequestMethod.POST);
        assertNotNull(SDKConstant.RequestMethod.PUT);
        assertNotNull(SDKConstant.RequestMethod.DELETE);
    }

    @Test
    public void testRequestMethodEnumGET() {
        assertEquals("GET", SDKConstant.RequestMethod.GET.name());
    }

    @Test
    public void testRequestMethodEnumPOST() {
        assertEquals("POST", SDKConstant.RequestMethod.POST.name());
    }

    @Test
    public void testRequestMethodEnumPUT() {
        assertEquals("PUT", SDKConstant.RequestMethod.PUT.name());
    }

    @Test
    public void testRequestMethodEnumDELETE() {
        assertEquals("DELETE", SDKConstant.RequestMethod.DELETE.name());
    }

    @Test
    public void testCallControllerEnumValues() {
        SDKConstant.callController[] controllers = SDKConstant.callController.values();
        assertEquals(8, controllers.length); // Fixed: should be 8, not 7
        assertNotNull(SDKConstant.callController.QUERY);
        assertNotNull(SDKConstant.callController.ENTRY);
        assertNotNull(SDKConstant.callController.STACK);
        assertNotNull(SDKConstant.callController.ASSET);
        assertNotNull(SDKConstant.callController.SYNC);
        assertNotNull(SDKConstant.callController.CONTENT_TYPES);
        assertNotNull(SDKConstant.callController.GLOBAL_FIELDS);
        assertNotNull(SDKConstant.callController.ASSET_LIBRARY);
    }

    @Test
    public void testCallControllerEnumQUERY() {
        assertEquals("QUERY", SDKConstant.callController.QUERY.name());
    }

    @Test
    public void testCallControllerEnumENTRY() {
        assertEquals("ENTRY", SDKConstant.callController.ENTRY.name());
    }

    @Test
    public void testCallControllerEnumSTACK() {
        assertEquals("STACK", SDKConstant.callController.STACK.name());
    }

    @Test
    public void testCallControllerEnumASSET() {
        assertEquals("ASSET", SDKConstant.callController.ASSET.name());
    }

    @Test
    public void testCallControllerEnumSYNC() {
        assertEquals("SYNC", SDKConstant.callController.SYNC.name());
    }

    @Test
    public void testCallControllerEnumCONTENT_TYPES() {
        assertEquals("CONTENT_TYPES", SDKConstant.callController.CONTENT_TYPES.name());
    }

    @Test
    public void testCallControllerEnumGLOBAL_FIELDS() {
        assertEquals("GLOBAL_FIELDS", SDKConstant.callController.GLOBAL_FIELDS.name());
    }

    @Test
    public void testCallControllerEnumASSET_LIBRARY() {
        assertEquals("ASSET_LIBRARY", SDKConstant.callController.ASSET_LIBRARY.name());
    }

    // ========== ARRAYLIST TESTS ==========

    @Test
    public void testCancelledCallControllerInitialization() {
        assertNotNull(SDKConstant.cancelledCallController);
    }

    @Test
    public void testCancelledCallControllerCanAddItems() {
        int initialSize = SDKConstant.cancelledCallController.size();
        SDKConstant.cancelledCallController.add("test_call_id");
        assertTrue(SDKConstant.cancelledCallController.size() >= initialSize);
        SDKConstant.cancelledCallController.remove("test_call_id");
    }

    @Test
    public void testCancelledCallControllerCanRemoveItems() {
        SDKConstant.cancelledCallController.add("test_remove");
        assertTrue(SDKConstant.cancelledCallController.contains("test_remove"));
        SDKConstant.cancelledCallController.remove("test_remove");
        assertFalse(SDKConstant.cancelledCallController.contains("test_remove"));
    }

    // ========== CACHE FOLDER NAME TESTS ==========

    @Test
    public void testCacheFolderNameCanBeSet() {
        String originalValue = SDKConstant.cacheFolderName;
        SDKConstant.cacheFolderName = "test_cache_folder";
        assertEquals("test_cache_folder", SDKConstant.cacheFolderName);
        SDKConstant.cacheFolderName = originalValue; // Restore
    }

    // ========== STRING NON-NULL TESTS ==========

    @Test
    public void testAllErrorMessagesAreNonNull() {
        assertNotNull(SDKConstant.PLEASE_PROVIDE_VALID_JSON);
        assertNotNull(SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED);
        assertNotNull(SDKConstant.PLEASE_SET_CONTENT_TYPE_NAME);
        assertNotNull(SDKConstant.PLEASE_SET_ENTRY_UID);
        assertNotNull(SDKConstant.CONNECTION_ERROR);
        assertNotNull(SDKConstant.AUTHENTICATION_NOT_PRESENT);
        assertNotNull(SDKConstant.PARSING_ERROR);
        assertNotNull(SDKConstant.TRY_AGAIN);
        assertNotNull(SDKConstant.ERROR_MESSAGE_DEFAULT);
        assertNotNull(SDKConstant.NOT_AVAILABLE);
        assertNotNull(SDKConstant.STACK_FIRST);
        assertNotNull(SDKConstant.PROVIDE_VALID_PARAMS);
        assertNotNull(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE);
        assertNotNull(SDKConstant.NETWORK_CALL_RESPONSE);
        assertNotNull(SDKConstant.PLEASE_PROVIDE_GLOBAL_FIELD_UID);
    }

    @Test
    public void testAllErrorMessagesAreNonEmpty() {
        assertFalse(SDKConstant.PLEASE_PROVIDE_VALID_JSON.isEmpty());
        assertFalse(SDKConstant.EMPTY_CREDENTIALS_NOT_ALLOWED.isEmpty());
        assertFalse(SDKConstant.PLEASE_SET_CONTENT_TYPE_NAME.isEmpty());
        assertFalse(SDKConstant.PLEASE_SET_ENTRY_UID.isEmpty());
        assertFalse(SDKConstant.CONNECTION_ERROR.isEmpty());
        assertFalse(SDKConstant.AUTHENTICATION_NOT_PRESENT.isEmpty());
        assertFalse(SDKConstant.PARSING_ERROR.isEmpty());
        assertFalse(SDKConstant.TRY_AGAIN.isEmpty());
        assertFalse(SDKConstant.ERROR_MESSAGE_DEFAULT.isEmpty());
        assertFalse(SDKConstant.NOT_AVAILABLE.isEmpty());
        assertFalse(SDKConstant.STACK_FIRST.isEmpty());
        assertFalse(SDKConstant.PROVIDE_VALID_PARAMS.isEmpty());
        assertFalse(SDKConstant.ENTRY_IS_NOT_PRESENT_IN_CACHE.isEmpty());
        assertFalse(SDKConstant.NETWORK_CALL_RESPONSE.isEmpty());
        assertFalse(SDKConstant.PLEASE_PROVIDE_GLOBAL_FIELD_UID.isEmpty());
    }

    // ========== ENUM valueOf TESTS ==========

    @Test
    public void testRequestMethodValueOf() {
        assertEquals(SDKConstant.RequestMethod.GET, SDKConstant.RequestMethod.valueOf("GET"));
        assertEquals(SDKConstant.RequestMethod.POST, SDKConstant.RequestMethod.valueOf("POST"));
        assertEquals(SDKConstant.RequestMethod.PUT, SDKConstant.RequestMethod.valueOf("PUT"));
        assertEquals(SDKConstant.RequestMethod.DELETE, SDKConstant.RequestMethod.valueOf("DELETE"));
    }

    @Test
    public void testCallControllerValueOf() {
        assertEquals(SDKConstant.callController.QUERY, SDKConstant.callController.valueOf("QUERY"));
        assertEquals(SDKConstant.callController.ENTRY, SDKConstant.callController.valueOf("ENTRY"));
        assertEquals(SDKConstant.callController.STACK, SDKConstant.callController.valueOf("STACK"));
        assertEquals(SDKConstant.callController.ASSET, SDKConstant.callController.valueOf("ASSET"));
        assertEquals(SDKConstant.callController.SYNC, SDKConstant.callController.valueOf("SYNC"));
        assertEquals(SDKConstant.callController.CONTENT_TYPES, SDKConstant.callController.valueOf("CONTENT_TYPES"));
        assertEquals(SDKConstant.callController.GLOBAL_FIELDS, SDKConstant.callController.valueOf("GLOBAL_FIELDS"));
        assertEquals(SDKConstant.callController.ASSET_LIBRARY, SDKConstant.callController.valueOf("ASSET_LIBRARY"));
    }

    // ========== ENUM ORDINAL TESTS ==========

    @Test
    public void testRequestMethodOrdinals() {
        assertEquals(0, SDKConstant.RequestMethod.GET.ordinal());
        assertEquals(1, SDKConstant.RequestMethod.POST.ordinal());
        assertEquals(2, SDKConstant.RequestMethod.PUT.ordinal());
        assertEquals(3, SDKConstant.RequestMethod.DELETE.ordinal());
    }

    @Test
    public void testCallControllerOrdinals() {
        assertEquals(0, SDKConstant.callController.QUERY.ordinal());
        assertEquals(1, SDKConstant.callController.ENTRY.ordinal());
        assertEquals(2, SDKConstant.callController.STACK.ordinal());
        assertEquals(3, SDKConstant.callController.ASSET.ordinal());
        assertEquals(4, SDKConstant.callController.SYNC.ordinal());
        assertEquals(5, SDKConstant.callController.CONTENT_TYPES.ordinal());
        assertEquals(6, SDKConstant.callController.GLOBAL_FIELDS.ordinal());
        assertEquals(7, SDKConstant.callController.ASSET_LIBRARY.ordinal());
    }
}

