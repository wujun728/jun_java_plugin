package com.neo.entity;

import java.io.Serializable;

public class UserEntity  extends Entity implements Serializable{
    /** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	private long id;
    private String userName;
    private String passWord;
    
	public UserEntity() {
		super();
	}
    public UserEntity(String userName, String passWord) {
		super();
		this.userName = userName;
		this.passWord = passWord;
	}
	public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
 

}

