package com.ddangnmarket.ddangmarkgetbackend.utils.repository;

/**
 * @author SeunghyunYoo
 */
public class IllegalSortArgumentException extends RuntimeException{
    public IllegalSortArgumentException() {
        super();
    }

    public IllegalSortArgumentException(String message) {
        super(message);
    }

    public IllegalSortArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalSortArgumentException(Throwable cause) {
        super(cause);
    }

    protected IllegalSortArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
