package org.ws.httphelper.exception;

public class WSException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WSException(String message) {
        super(message);
    }

    public WSException(String message, Throwable e) {
        super(message, e);
    }

    public WSException(Throwable e) {
        super(e);
    }
}
