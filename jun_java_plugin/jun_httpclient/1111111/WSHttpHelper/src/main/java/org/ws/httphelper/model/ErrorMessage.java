package org.ws.httphelper.model;

import java.text.MessageFormat;

/**
 * 参数验证结果
 */
public class ErrorMessage {
    private String message = null;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, Object... var) {
        this.message = MessageFormat.format(message, var);
    }

    @Override
    public String toString() {
        return this.message;
    }
}
