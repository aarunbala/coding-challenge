package com.challenge.coding.exception;

import java.io.IOException;

public class InvalidInputException extends IOException {

    private String message;

    public InvalidInputException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
