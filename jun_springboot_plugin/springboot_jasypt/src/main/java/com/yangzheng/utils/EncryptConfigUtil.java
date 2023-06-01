package com.yangzheng.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author yangzheng
 * @description
 * @date 2022/6/1 00115:54
 */
public class EncryptConfigUtil {

    public static void main(String[] args) {

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt
        textEncryptor.setPassword("123456");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("");
        System.out.println("username:"+username);
        System.out.println("password:"+password);

    }
}
