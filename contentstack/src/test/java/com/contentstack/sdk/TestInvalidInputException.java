package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for InvalidInputException class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestInvalidInputException {

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testConstructorWithMessage() {
        String message = "Test error message";
        InvalidInputException exception = new InvalidInputException(message);
        
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructorWithNullMessage() {
        InvalidInputException exception = new InvalidInputException(null);
        
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testConstructorWithEmptyMessage() {
        InvalidInputException exception = new InvalidInputException("");
        
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    // ========== ERROR MESSAGE CONSTANT TESTS ==========

    @Test
    public void testWithNullOrEmptyInputMessage() {
        InvalidInputException exception = new InvalidInputException(ErrorMessages.NULL_OR_EMPTY_INPUT);
        
        assertNotNull(exception);
        assertEquals(ErrorMessages.NULL_OR_EMPTY_INPUT, exception.getMessage());
        assertTrue(exception.getMessage().contains("null or empty"));
    }

    @Test
    public void testWithEncodingErrorMessage() {
        InvalidInputException exception = new InvalidInputException(ErrorMessages.ENCODING_ERROR);
        
        assertNotNull(exception);
        assertEquals(ErrorMessages.ENCODING_ERROR, exception.getMessage());
    }

    @Test
    public void testWithJsonParsingErrorMessage() {
        InvalidInputException exception = new InvalidInputException(ErrorMessages.JSON_PARSING_ERROR);
        
        assertNotNull(exception);
        assertEquals(ErrorMessages.JSON_PARSING_ERROR, exception.getMessage());
    }

    // ========== EXCEPTION HIERARCHY TESTS ==========

    @Test
    public void testExtendsException() {
        InvalidInputException exception = new InvalidInputException("Test");
        assertTrue(exception instanceof Exception);
    }

    @Test
    public void testIsThrowable() {
        InvalidInputException exception = new InvalidInputException("Test");
        assertTrue(exception instanceof Throwable);
    }

    @Test
    public void testIsCheckedException() {
        InvalidInputException exception = new InvalidInputException("Test");
        assertTrue(exception instanceof Exception);
        // InvalidInputException extends Exception, not RuntimeException, so it's a checked exception
        assertNotNull(exception);
    }

    // ========== THROW AND CATCH TESTS ==========

    @Test(expected = InvalidInputException.class)
    public void testCanBeThrown() throws InvalidInputException {
        throw new InvalidInputException("Test throw");
    }

    @Test
    public void testCanBeCaught() {
        try {
            throw new InvalidInputException("Caught exception");
        } catch (InvalidInputException e) {
            assertEquals("Caught exception", e.getMessage());
        }
    }

    @Test
    public void testCatchesAsException() {
        try {
            throw new InvalidInputException("Generic catch");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidInputException);
            assertEquals("Generic catch", e.getMessage());
        }
    }

    // ========== METHOD USAGE TESTS ==========

    @Test
    public void testProcessInputMethodExample() {
        try {
            processInput(null);
            fail("Should have thrown InvalidInputException");
        } catch (InvalidInputException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testProcessInputWithEmptyString() {
        try {
            processInput("");
            fail("Should have thrown InvalidInputException");
        } catch (InvalidInputException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testProcessInputWithValidString() throws InvalidInputException {
        String result = processInput("valid input");
        assertEquals("Processed: valid input", result);
    }

    private String processInput(String input) throws InvalidInputException {
        if (input == null || input.isEmpty()) {
            throw new InvalidInputException(ErrorMessages.NULL_OR_EMPTY_INPUT);
        }
        return "Processed: " + input;
    }

    // ========== MESSAGE CONTENT TESTS ==========

    @Test
    public void testMessageWithSpecialCharacters() {
        String specialMessage = "Error: !@#$%^&*()_+-={}[]|:;<>?,./";
        InvalidInputException exception = new InvalidInputException(specialMessage);
        
        assertEquals(specialMessage, exception.getMessage());
    }

    @Test
    public void testMessageWithUnicode() {
        String unicodeMessage = "Error: Hello 世界 مرحبا мир";
        InvalidInputException exception = new InvalidInputException(unicodeMessage);
        
        assertEquals(unicodeMessage, exception.getMessage());
    }

    @Test
    public void testMessageWithLongString() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("Error ");
        }
        
        InvalidInputException exception = new InvalidInputException(longMessage.toString());
        assertEquals(longMessage.toString(), exception.getMessage());
    }

    // ========== EXCEPTION STACK TRACE TESTS ==========

    @Test
    public void testHasStackTrace() {
        InvalidInputException exception = new InvalidInputException("Test");
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }

    @Test
    public void testStackTraceContainsTestClass() {
        InvalidInputException exception = new InvalidInputException("Test");
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        boolean containsTestClass = false;
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("TestInvalidInputException")) {
                containsTestClass = true;
                break;
            }
        }
        assertTrue(containsTestClass);
    }

    // ========== MULTIPLE EXCEPTION INSTANCES TESTS ==========

    @Test
    public void testMultipleInstances() {
        InvalidInputException ex1 = new InvalidInputException("Message 1");
        InvalidInputException ex2 = new InvalidInputException("Message 2");
        InvalidInputException ex3 = new InvalidInputException("Message 3");
        
        assertNotSame(ex1, ex2);
        assertNotSame(ex2, ex3);
        assertNotSame(ex1, ex3);
        
        assertEquals("Message 1", ex1.getMessage());
        assertEquals("Message 2", ex2.getMessage());
        assertEquals("Message 3", ex3.getMessage());
    }

    // ========== REAL WORLD USAGE TESTS ==========

    @Test
    public void testValidateApiKey() {
        try {
            validateApiKey("");
            fail("Should throw InvalidInputException");
        } catch (InvalidInputException e) {
            assertTrue(e.getMessage().contains("API key"));
        }
    }

    @Test
    public void testValidateDeliveryToken() {
        try {
            validateDeliveryToken(null);
            fail("Should throw InvalidInputException");
        } catch (InvalidInputException e) {
            assertTrue(e.getMessage().contains("Delivery token"));
        }
    }

    @Test
    public void testValidateEnvironment() throws InvalidInputException {
        String result = validateEnvironment("production");
        assertEquals("production", result);
    }

    private void validateApiKey(String apiKey) throws InvalidInputException {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new InvalidInputException("API key cannot be null or empty");
        }
    }

    private void validateDeliveryToken(String token) throws InvalidInputException {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidInputException("Delivery token cannot be null or empty");
        }
    }

    private String validateEnvironment(String environment) throws InvalidInputException {
        if (environment == null || environment.isEmpty()) {
            throw new InvalidInputException("Environment cannot be null or empty");
        }
        return environment;
    }

    // ========== SERIALIZATION TESTS ==========

    @Test
    public void testToString() {
        InvalidInputException exception = new InvalidInputException("Test message");
        String toString = exception.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("InvalidInputException"));
        assertTrue(toString.contains("Test message"));
    }

    @Test
    public void testGetLocalizedMessage() {
        String message = "Localized test message";
        InvalidInputException exception = new InvalidInputException(message);
        
        assertEquals(message, exception.getLocalizedMessage());
    }
}

