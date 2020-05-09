package com.qa.exception.handlers;

public class ExceptionHandlers extends RuntimeException {


    public ExceptionHandlers() {
        super();
    }


    public ExceptionHandlers(String message) {
        super(message);
    }

    public ExceptionHandlers(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionHandlers(Throwable cause) {
        super(cause);
    }

    public ExceptionHandlers(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
