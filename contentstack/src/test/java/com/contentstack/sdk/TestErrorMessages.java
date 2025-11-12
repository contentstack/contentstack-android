package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for ErrorMessages class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestErrorMessages {

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testPrivateConstructorThrowsException() {
        try {
            java.lang.reflect.Constructor<ErrorMessages> constructor = 
                ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Should have thrown an exception");
        } catch (Exception e) {
            // Expected - constructor throws IllegalStateException
            assertNotNull(e);
            assertTrue(e.getCause() instanceof IllegalStateException);
        }
    }

    @Test
    public void testPrivateConstructorExceptionMessage() {
        try {
            java.lang.reflect.Constructor<ErrorMessages> constructor = 
                ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Should have thrown IllegalStateException");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertEquals("Utility class - do not instantiate", e.getCause().getMessage());
        }
    }

    // ========== CONSTRUCTOR RELATED ERROR MESSAGES TESTS ==========

    @Test
    public void testPrivateConstructorNotAllowedMessage() {
        String message = ErrorMessages.PRIVATE_CONSTRUCTOR_NOT_ALLOWED;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("private constructor"));
    }

    @Test
    public void testUtilityClassInstantiationMessage() {
        String message = ErrorMessages.UTILITY_CLASS_INSTANTIATION;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertEquals("This is a utility class and cannot be instantiated", message);
    }

    @Test
    public void testNodeToHtmlInstantiationMessage() {
        String message = ErrorMessages.NODE_TO_HTML_INSTANTIATION;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("NodeToHTML"));
    }

    // ========== INPUT VALIDATION ERROR MESSAGES TESTS ==========

    @Test
    public void testNullOrEmptyInputMessage() {
        String message = ErrorMessages.NULL_OR_EMPTY_INPUT;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("null or empty"));
    }

    // ========== NETWORK AND PARSING ERROR MESSAGES TESTS ==========

    @Test
    public void testEncodingErrorMessage() {
        String message = ErrorMessages.ENCODING_ERROR;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("encoding"));
    }

    @Test
    public void testJsonParsingErrorMessage() {
        String message = ErrorMessages.JSON_PARSING_ERROR;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("data formatting"));
    }

    // ========== CACHE RELATED ERROR MESSAGES TESTS ==========

    @Test
    public void testCacheInitializationErrorMessage() {
        String message = ErrorMessages.CACHE_INITIALIZATION_ERROR;
        assertNotNull(message);
        assertFalse(message.isEmpty());
        assertTrue(message.contains("cache"));
    }

    // ========== ALL MESSAGES NON-NULL TESTS ==========

    @Test
    public void testAllErrorMessagesAreNonNull() {
        assertNotNull(ErrorMessages.PRIVATE_CONSTRUCTOR_NOT_ALLOWED);
        assertNotNull(ErrorMessages.UTILITY_CLASS_INSTANTIATION);
        assertNotNull(ErrorMessages.NODE_TO_HTML_INSTANTIATION);
        assertNotNull(ErrorMessages.NULL_OR_EMPTY_INPUT);
        assertNotNull(ErrorMessages.ENCODING_ERROR);
        assertNotNull(ErrorMessages.JSON_PARSING_ERROR);
        assertNotNull(ErrorMessages.CACHE_INITIALIZATION_ERROR);
    }

    @Test
    public void testAllErrorMessagesAreNonEmpty() {
        assertFalse(ErrorMessages.PRIVATE_CONSTRUCTOR_NOT_ALLOWED.isEmpty());
        assertFalse(ErrorMessages.UTILITY_CLASS_INSTANTIATION.isEmpty());
        assertFalse(ErrorMessages.NODE_TO_HTML_INSTANTIATION.isEmpty());
        assertFalse(ErrorMessages.NULL_OR_EMPTY_INPUT.isEmpty());
        assertFalse(ErrorMessages.ENCODING_ERROR.isEmpty());
        assertFalse(ErrorMessages.JSON_PARSING_ERROR.isEmpty());
        assertFalse(ErrorMessages.CACHE_INITIALIZATION_ERROR.isEmpty());
    }

    // ========== MESSAGE CONTENT VALIDATION TESTS ==========

    @Test
    public void testConstructorErrorMessagesContainRelevantKeywords() {
        assertTrue(ErrorMessages.PRIVATE_CONSTRUCTOR_NOT_ALLOWED.toLowerCase().contains("constructor"));
        assertTrue(ErrorMessages.UTILITY_CLASS_INSTANTIATION.toLowerCase().contains("utility"));
        assertTrue(ErrorMessages.NODE_TO_HTML_INSTANTIATION.toLowerCase().contains("nodetohtml"));
    }

    @Test
    public void testValidationErrorMessagesContainRelevantKeywords() {
        String message = ErrorMessages.NULL_OR_EMPTY_INPUT.toLowerCase();
        assertTrue(message.contains("null") || message.contains("empty"));
    }

    @Test
    public void testNetworkErrorMessagesContainRelevantKeywords() {
        assertTrue(ErrorMessages.ENCODING_ERROR.toLowerCase().contains("encoding"));
        assertTrue(ErrorMessages.JSON_PARSING_ERROR.toLowerCase().contains("data") || 
                   ErrorMessages.JSON_PARSING_ERROR.toLowerCase().contains("format"));
    }

    @Test
    public void testCacheErrorMessagesContainRelevantKeywords() {
        assertTrue(ErrorMessages.CACHE_INITIALIZATION_ERROR.toLowerCase().contains("cache"));
    }

    // ========== MESSAGE UNIQUENESS TESTS ==========

    @Test
    public void testAllErrorMessagesAreUnique() {
        String[] messages = {
            ErrorMessages.PRIVATE_CONSTRUCTOR_NOT_ALLOWED,
            ErrorMessages.UTILITY_CLASS_INSTANTIATION,
            ErrorMessages.NODE_TO_HTML_INSTANTIATION,
            ErrorMessages.NULL_OR_EMPTY_INPUT,
            ErrorMessages.ENCODING_ERROR,
            ErrorMessages.JSON_PARSING_ERROR,
            ErrorMessages.CACHE_INITIALIZATION_ERROR
        };
        
        for (int i = 0; i < messages.length; i++) {
            for (int j = i + 1; j < messages.length; j++) {
                assertNotEquals("Messages should be unique", messages[i], messages[j]);
            }
        }
    }

    // ========== FINAL MODIFIER TESTS ==========

    @Test
    public void testClassIsFinal() {
        assertTrue(java.lang.reflect.Modifier.isFinal(ErrorMessages.class.getModifiers()));
    }

    @Test
    public void testAllFieldsAreFinal() throws Exception {
        java.lang.reflect.Field[] fields = ErrorMessages.class.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            // Skip checking final for fields starting with "$" (compiler-generated)
            if (!field.getName().startsWith("$")) {
                assertTrue("Field " + field.getName() + " should be final",
                          java.lang.reflect.Modifier.isFinal(field.getModifiers()));
            }
        }
    }

    @Test
    public void testAllFieldsAreStatic() throws Exception {
        java.lang.reflect.Field[] fields = ErrorMessages.class.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            // Skip checking static for fields starting with "$" (compiler-generated)
            if (!field.getName().startsWith("$")) {
                assertTrue("Field " + field.getName() + " should be static",
                          java.lang.reflect.Modifier.isStatic(field.getModifiers()));
            }
        }
    }

    @Test
    public void testAllFieldsArePublic() throws Exception {
        java.lang.reflect.Field[] fields = ErrorMessages.class.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            // Skip checking public for fields starting with "$" (compiler-generated)
            if (!field.getName().startsWith("$")) {
                assertTrue("Field " + field.getName() + " should be public",
                          java.lang.reflect.Modifier.isPublic(field.getModifiers()));
            }
        }
    }
}

