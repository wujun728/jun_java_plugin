package org.supercall.domainModel;

/**
 * Created by kira on 16/7/26.
 */
public class ResultMessage {
    private String message;
    private Boolean isSuccess;
    private String stackMessage;

    public ResultMessage(Boolean isSuccess, String message, String stackMessage) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.stackMessage = stackMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getStackMessage() {
        return stackMessage;
    }

    public void setStackMessage(String stackMessage) {
        this.stackMessage = stackMessage;
    }
}
