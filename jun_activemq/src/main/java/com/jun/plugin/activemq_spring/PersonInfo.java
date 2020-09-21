package com.jun.plugin.activemq_spring;

import java.io.Serializable;

/**
 * 实体bean
 * 
 * 作者: zhoubang 
 * 日期：2015年9月28日 上午10:11:33
 */
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 789949814642878355L;

    private String userName;
    private String address;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PersonInfo [userName=" + userName + ", address=" + address
                + ", pwd=" + pwd + "]";
    }

    
}
