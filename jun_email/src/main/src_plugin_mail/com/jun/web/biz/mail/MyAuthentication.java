package com.jun.web.biz.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentication extends Authenticator {  
	  
    private String username;  
    private String password;  
      
    public MyAuthentication(String username, String password) {  
        this.username = username;  
        this.password = password;  
    }  
      
    @Override  
    protected PasswordAuthentication getPasswordAuthentication() {  
          
        return new PasswordAuthentication(username, password);  
    }  
}  