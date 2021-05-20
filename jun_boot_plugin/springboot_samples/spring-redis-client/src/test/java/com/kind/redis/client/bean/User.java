package com.kind.redis.client.bean;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 7102873147877263336L;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }
}
