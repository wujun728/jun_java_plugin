package com.us.drools.bean;

import java.io.Serializable;

/**
 * Created by yangyibo on 16/12/8.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}