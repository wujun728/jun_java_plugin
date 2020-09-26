package com.kancy.emailplus.core.cryptor;

/**
 * PasswordCryptor
 *
 * @author kancy
 * @date 2020/2/20 14:37
 */
public interface PasswordCryptor {
    /**
     * 解密
     * @param password
     * @return
     */
    String decrypt(String password);

    /**
     * 加密
     * @param text
     * @return
     */
    String encrypt(String text);
}
