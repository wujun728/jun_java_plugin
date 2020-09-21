package com.drools.entity;

/**
 * Created by LANCHUNQIAN on 2016/10/13.
 */
public class Message {

    public static final int HELLO = 0;
    public static final int GOODBYE = 1;

    private String message;
    private int status;

    public Message(String message, int status){
        this.message = message;
        this.status = status;
    }

    public Message(){}

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
