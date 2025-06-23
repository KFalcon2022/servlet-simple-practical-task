package com.walking.servletpractice.exception;

public class ParsingException extends RuntimeException {
    public ParsingException() {
    }

    public ParsingException(String value, Throwable cause) {
        super("Cannot parse value: '%s'".formatted(value), cause);
    }
}
