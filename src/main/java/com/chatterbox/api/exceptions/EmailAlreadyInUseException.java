package com.chatterbox.api.exceptions;

public class EmailAlreadyInUseException extends Exception{
    public EmailAlreadyInUseException() {
    }

    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
