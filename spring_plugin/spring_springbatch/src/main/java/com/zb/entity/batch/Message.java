package com.zb.entity.batch;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Message implements Serializable {

    private User user;
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message() {
        // TODO Auto-generated constructor stub
    }

}
