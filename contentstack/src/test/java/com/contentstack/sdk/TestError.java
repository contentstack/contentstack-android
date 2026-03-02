package com.contentstack.sdk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, manifest = Config.NONE)
public class TestError {

    private Error error;

    @Before
    public void setUp() {
        error = new Error();
    }

    @After
    public void tearDown() {
        error = null;
    }

    @Test
    public void testErrorCreation() {
        assertNotNull("Error object should not be null", error);
        assertNull("Default error message should be null", error.getErrorMessage());
        assertEquals("Default error code should be 0", 0, error.getErrorCode());
        assertEquals("Default status code should be 0", 0, error.getStatusCode());
    }

    @Test
    public void testSetErrorMessage() {
        String message = "Test error message";
        error.setErrorMessage(message);
        assertEquals("Error message should be set correctly", message, error.getErrorMessage());
    }

    @Test
    public void testSetErrorMessageWithNull() {
        error.setErrorMessage(null);
        assertNull("Error message should be null", error.getErrorMessage());
    }

    @Test
    public void testSetErrorMessageWithEmpty() {
        error.setErrorMessage("");
        assertEquals("Error message should be empty string", "", error.getErrorMessage());
    }

    @Test
    public void testGetErrorMessage() {
        assertNull("Default error message should be null", error.getErrorMessage());
        error.setErrorMessage("New error");
        assertEquals("Should return set error message", "New error", error.getErrorMessage());
    }

    @Test
    public void testSetErrorCode() {
        int errorCode = 404;
        error.setErrorCode(errorCode);
        assertEquals("Error code should be set correctly", errorCode, error.getErrorCode());
    }

    @Test
    public void testSetErrorCodeWithZero() {
        error.setErrorCode(0);
        assertEquals("Error code should be 0", 0, error.getErrorCode());
    }

    @Test
    public void testSetErrorCodeWithNegative() {
        error.setErrorCode(-1);
        assertEquals("Error code should be -1", -1, error.getErrorCode());
    }

    @Test
    public void testGetErrorCode() {
        assertEquals("Default error code should be 0", 0, error.getErrorCode());
        error.setErrorCode(500);
        assertEquals("Should return set error code", 500, error.getErrorCode());
    }

    @Test
    public void testSetStatusCode() {
        int statusCode = 200;
        error.setStatusCode(statusCode);
        assertEquals("Status code should be set correctly", statusCode, error.getStatusCode());
    }

    @Test
    public void testSetStatusCodeWithVariousValues() {
        int[] statusCodes = {200, 201, 400, 401, 404, 500, 503};
        for (int code : statusCodes) {
            error.setStatusCode(code);
            assertEquals("Status code should be " + code, code, error.getStatusCode());
        }
    }

    @Test
    public void testGetStatusCode() {
        assertEquals("Default status code should be 0", 0, error.getStatusCode());
        error.setStatusCode(404);
        assertEquals("Should return set status code", 404, error.getStatusCode());
    }

    @Test
    public void testSetErrors() {
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("field1", "Error on field1");
        errors.put("field2", "Error on field2");
        
        error.setErrors(errors);
        assertEquals("Errors map should be set correctly", errors, error.getErrors());
        assertEquals("Should contain 2 errors", 2, error.getErrors().size());
    }

    @Test
    public void testSetErrorsWithEmptyHashMap() {
        HashMap<String, Object> errors = new HashMap<>();
        error.setErrors(errors);
        assertEquals("Errors map should be empty", 0, error.getErrors().size());
    }

    @Test
    public void testSetErrorsWithNull() {
        error.setErrors(null);
        assertNull("Errors should be null", error.getErrors());
    }

    @Test
    public void testGetErrors() {
        assertNotNull("Default errors should not be null", error.getErrors());
        assertEquals("Default errors should be empty", 0, error.getErrors().size());
        
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("test", "value");
        error.setErrors(errors);
        assertEquals("Should return set errors", errors, error.getErrors());
    }

    @Test
    public void testGetErrorsWithVariousTypes() {
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("string_error", "String error message");
        errors.put("integer_error", 123);
        errors.put("boolean_error", true);
        errors.put("object_error", new Object());
        
        error.setErrors(errors);
        assertEquals("Should contain 4 errors", 4, error.getErrors().size());
        assertTrue("Should contain string_error", error.getErrors().containsKey("string_error"));
        assertTrue("Should contain integer_error", error.getErrors().containsKey("integer_error"));
    }

    @Test
    public void testCompleteErrorObject() {
        String message = "Complete error occurred";
        int errorCode = 422;
        int statusCode = 422;
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("validation", "Validation failed");
        
        error.setErrorMessage(message);
        error.setErrorCode(errorCode);
        error.setStatusCode(statusCode);
        error.setErrors(errors);
        
        assertEquals("Error message should match", message, error.getErrorMessage());
        assertEquals("Error code should match", errorCode, error.getErrorCode());
        assertEquals("Status code should match", statusCode, error.getStatusCode());
        assertEquals("Errors should match", errors, error.getErrors());
    }

    @Test
    public void testErrorWithLongMessage() {
        String longMessage = "This is a very long error message that contains a lot of text. " +
                "It might be a detailed error description that explains what went wrong in the application. " +
                "Error messages can sometimes be quite lengthy when they need to provide comprehensive information " +
                "about the issue that occurred.";
        error.setErrorMessage(longMessage);
        assertEquals("Long error message should be set correctly", longMessage, error.getErrorMessage());
    }

    @Test
    public void testErrorWithSpecialCharacters() {
        String specialMessage = "Error: Invalid character found! @#$%^&*()_+-=[]{}|;':\",./<>?";
        error.setErrorMessage(specialMessage);
        assertEquals("Special characters should be preserved", specialMessage, error.getErrorMessage());
    }

    @Test
    public void testErrorWithUnicodeCharacters() {
        String unicodeMessage = "Error occurred: 错误 エラー خطأ ошибка";
        error.setErrorMessage(unicodeMessage);
        assertEquals("Unicode characters should be preserved", unicodeMessage, error.getErrorMessage());
    }

    @Test
    public void testMultipleErrorInstances() {
        Error error1 = new Error();
        Error error2 = new Error();
        
        error1.setErrorMessage("Error 1");
        error1.setErrorCode(400);
        
        error2.setErrorMessage("Error 2");
        error2.setErrorCode(500);
        
        assertEquals("Error1 message", "Error 1", error1.getErrorMessage());
        assertEquals("Error2 message", "Error 2", error2.getErrorMessage());
        assertEquals("Error1 code", 400, error1.getErrorCode());
        assertEquals("Error2 code", 500, error2.getErrorCode());
        assertNotEquals("Messages should be different", error1.getErrorMessage(), error2.getErrorMessage());
    }

    @Test
    public void testErrorCodesForCommonHTTPErrors() {
        int[] commonCodes = {400, 401, 403, 404, 405, 422, 500, 502, 503, 504};
        for (int code : commonCodes) {
            error.setErrorCode(code);
            error.setStatusCode(code);
            assertEquals("Error code should be " + code, code, error.getErrorCode());
            assertEquals("Status code should be " + code, code, error.getStatusCode());
        }
    }

    @Test
    public void testErrorsHashMapModification() {
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("initial", "Initial error");
        error.setErrors(errors);
        
        // Modify the original hashmap
        errors.put("additional", "Additional error");
        
        // The error object's hashmap might or might not be affected depending on implementation
        // This test verifies the behavior
        HashMap<String, Object> retrievedErrors = error.getErrors();
        assertNotNull("Retrieved errors should not be null", retrievedErrors);
    }

    @Test
    public void testResetError() {
        // Set initial values
        error.setErrorMessage("Initial error");
        error.setErrorCode(400);
        error.setStatusCode(400);
        HashMap<String, Object> errors = new HashMap<>();
        errors.put("field", "error");
        error.setErrors(errors);
        
        // Reset to new values
        error.setErrorMessage("New error");
        error.setErrorCode(500);
        error.setStatusCode(500);
        error.setErrors(new HashMap<>());
        
        assertEquals("Error message should be updated", "New error", error.getErrorMessage());
        assertEquals("Error code should be updated", 500, error.getErrorCode());
        assertEquals("Status code should be updated", 500, error.getStatusCode());
        assertEquals("Errors should be empty", 0, error.getErrors().size());
    }

    @Test
    public void testNetworkErrorScenario() {
        error.setErrorMessage("Network connection failed");
        error.setErrorCode(SDKConstant.NO_NETWORK_CONNECTION);
        error.setStatusCode(408);
        
        assertEquals("Network error message", "Network connection failed", error.getErrorMessage());
        assertEquals("Network error code", SDKConstant.NO_NETWORK_CONNECTION, error.getErrorCode());
        assertEquals("Network status code", 408, error.getStatusCode());
    }

    @Test
    public void testValidationErrorScenario() {
        HashMap<String, Object> validationErrors = new HashMap<>();
        validationErrors.put("email", "Invalid email format");
        validationErrors.put("password", "Password too short");
        
        error.setErrorMessage("Validation failed");
        error.setErrorCode(422);
        error.setStatusCode(422);
        error.setErrors(validationErrors);
        
        assertEquals("Validation error message", "Validation failed", error.getErrorMessage());
        assertEquals("Should have 2 validation errors", 2, error.getErrors().size());
        assertTrue("Should contain email error", error.getErrors().containsKey("email"));
        assertTrue("Should contain password error", error.getErrors().containsKey("password"));
    }
}

