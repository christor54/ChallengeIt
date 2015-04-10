package com.challengeit.android.MyExceptions;

/**
 * Created by christophe on 2/24/2015.
 */
public class NoPlayerException extends Exception{

    public NoPlayerException() {
        super();
    }

    public NoPlayerException(String message) {
        super(message);
    }

    public NoPlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlayerException(Throwable cause) {
        super(cause);
    }

    protected NoPlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
