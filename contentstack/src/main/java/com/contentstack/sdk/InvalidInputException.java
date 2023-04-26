package com.contentstack.sdk;


/**
 * This class extends the Exception class, which is the base class for all checked exceptions in Java.
 * The InvalidInputException class takes a String message as input and passes it to the
 * Exception class constructor using the super keyword.
 * <p>
 * You can use this custom exception class to throw an exception when invalid input is detected in your code.
 * <p> <b>For example:</b>
 * <p>
 *
 * <code>
 * public void processInput(String input) throws InvalidInputException {
 * if (input == null || input.isEmpty()) {
 * throw new InvalidInputException("Input cannot be null or empty");
 * }
 * // Process the input here
 * }
 * </code>
 */
public class InvalidInputException extends Exception {
    /**
     * Instantiates a new Invalid input exception.
     *
     * @param message the message
     */
    public InvalidInputException(String message) {
        super(message);
    }
}

