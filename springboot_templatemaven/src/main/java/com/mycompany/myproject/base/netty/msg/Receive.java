package com.mycompany.myproject.base.netty.msg;

import java.io.Serializable;

public class Receive implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String message;
    private byte[] sss;

    public byte[] getSss() {
        return sss;
    }
    public void setSss(byte[] sss) {
        this.sss = sss;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Receive [id=" + id + ", name=" + name + ", message=" + message + ", sss="
                //+ Arrays.toString(sss)
                + "]";
    }

}

