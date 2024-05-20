package com.jun.plugin.common.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 */
public class JasyptUtil {

    public static void main(String[] args) {

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt
        textEncryptor.setPassword("123456@@");

        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("");
        String password2 = textEncryptor.encrypt("mysqladmin1");
        System.out.println("username:"+username);
        System.out.println("username:"+textEncryptor.decrypt("i9EHZ6vsnfpTeKGvHmH+fA=="));
        System.out.println("password:"+password);
        System.out.println("password2:"+password2);

    }
}
