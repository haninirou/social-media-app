package com.example.questionapp.exceptions;

/**
 * Exception thrown when a user is not found
 * Extends RuntimeException for unchecked exception handling
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Default constructor without a message
     * Calls the constructor of the RuntimeException class
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructor with a custom error message
     * @param message The error message to display
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
