package com.jun.web.biz.mail.html;


import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
  
public class MyAuthenticator extends Authenticator{  
    /*在使用Authenticator这个抽象类时，我们必须采用继承该抽象类的方式，并且该继承类必须具 
     * 有返回PasswordAuthentication对象（用于存储认证时要用到的用户名、密码）getPasswordAuthentication() 
     * 方法。并且要在Session中进行注册，使Session能够了解在认证时该使用哪个类。  
     * */  
    String username=null;  
    String password=null;  
    public MyAuthenticator(){  
          
    }  
    public MyAuthenticator(String username,String password){  
        this.username=username;  
        this.password=password;  
    }  
    public PasswordAuthentication getPasswordAuthentication(){  
          
        return new PasswordAuthentication(username, password);  
    }  
      
      
}  