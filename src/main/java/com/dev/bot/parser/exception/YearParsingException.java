package com.dev.bot.parser.exception;

public class YearParsingException extends Exception {
    public YearParsingException() {
    }

    public YearParsingException(String message) {
        super(message);
    }

    public YearParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
