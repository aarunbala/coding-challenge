package com.challenge.coding.exception;

import java.io.IOException;

public class InvalidCSVFileException extends IOException {

    String message;

    public InvalidCSVFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
