package com.kancy.emailplus.core.cryptor;

import com.kancy.emailplus.core.exception.EmailException;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimplePasswordCryptor
 *
 * @author kancy
 * @date 2020/2/20 14:38
 */
public class SimplePasswordCryptor extends AesCipher implements PasswordCryptor {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^ENC\\((.*)\\)$");
    private static final String DEFAULT_KEY_ID = "key_id_email_password";
    private static final String DEFAULT_KEY = "UalUV^u@F$2ZStxE";

    private String keyId;

    public SimplePasswordCryptor() {
        this(DEFAULT_KEY_ID, DEFAULT_KEY);
    }

    public SimplePasswordCryptor(String key) {
        this(DEFAULT_KEY_ID, key);
    }

    public SimplePasswordCryptor(String keyId, String key) {
        this.keyId = keyId;
        addKey(keyId, key.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 解密
     *
     * @param password
     * @return
     */
    @Override
    public String decrypt(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (matcher.find()){
            String encryptPassword = matcher.group(1).trim();
            try {
                byte[] decryptData = super.decodeAndDecrypt(encryptPassword, this.keyId);
                return new String(decryptData, StandardCharsets.UTF_8);
            } catch (Exception ex) {
                throw new EmailException("邮件密码解密失败", ex);
            }
        }
        return password;
    }

    /**
     * 加密
     *
     * @param text
     * @return
     */
    @Override
    public String encrypt(String text) {
        try {
            return String.format("ENC(%s)",
                    super.encryptAndEncode(text.getBytes(StandardCharsets.UTF_8), DEFAULT_KEY_ID));
        } catch (Exception ex) {
            throw new EmailException("邮件密码解密失败", ex);
        }
    }
}
