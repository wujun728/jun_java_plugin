package com.kancy.emailplus.core;

import com.kancy.emailplus.core.cryptor.PasswordCryptor;
import com.kancy.emailplus.core.cryptor.SimplePasswordCryptor;
import org.junit.Assert;
import org.junit.Test;

/**
 * CryptorTest
 *
 * @author Wujun
 * @date 2020/2/20 15:07
 */
public class CryptorTest {

    @Test
    public void testSimplePasswordCryptor(){
        PasswordCryptor passwordCryptor = new SimplePasswordCryptor();
        String text = "secretData";
        String encrypt = passwordCryptor.encrypt(text);
        System.out.println(encrypt);
        String decrypt = passwordCryptor.decrypt(encrypt);
        System.out.println(decrypt);
        Assert.assertEquals(text, decrypt);
    }

    @Test
    public void encryptPassword(){

        PasswordCryptor passwordCryptor = new SimplePasswordCryptor();
        String encrypt = passwordCryptor.encrypt("secretData");
        String decrypt = passwordCryptor.decrypt(encrypt);
        Assert.assertEquals("secretData", decrypt);

        System.out.println(passwordCryptor.encrypt("Kancy793272861!!"));
        System.out.println(passwordCryptor.encrypt("jtds2018!!"));
        System.out.println(passwordCryptor.encrypt("tdd941026!"));
        System.out.println(passwordCryptor.encrypt("hlmefmhbmgopbehi"));
    }

}
