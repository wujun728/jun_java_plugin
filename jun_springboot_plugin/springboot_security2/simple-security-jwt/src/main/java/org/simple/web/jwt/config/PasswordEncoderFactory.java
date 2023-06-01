package org.simple.web.jwt.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：simple-security-all
 * 类名称：PasswordEncoderFactory
 * 类描述：PasswordEncoderFactory
 * 创建时间：2018/9/13
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
public class PasswordEncoderFactory {

    public static final String PWD_ENCODER_PLAINTEXT = "plain";
    public static final String PWD_ENCODER_BCRYPT = "bcrypt";
    public static final String PWD_ENCODER_SCRYPT = "scrypt";
    public static final String PWD_ENCODER_PBKDF2 = "pbkdf2";
    public static final String PWD_ENCODER_MD5 = "md5";
    public static final String PWD_ENCODER_SHA = "sha";
    public static final String PWD_ENCODER_SHA_256 = "sha-256";
    public static final String PWD_ENCODER_SHA_512 = "sha-512";
    public static final String PWD_ENCODER_STANDARD = "standard";

    private static final Map<String, PasswordEncoder> PASSWORD_ENCODERS = new HashMap<>();

    static {
        PASSWORD_ENCODERS.put(PWD_ENCODER_BCRYPT, new BCryptPasswordEncoder());
        PASSWORD_ENCODERS.put(PWD_ENCODER_SCRYPT, new SCryptPasswordEncoder());
        PASSWORD_ENCODERS.put(PWD_ENCODER_PBKDF2, new Pbkdf2PasswordEncoder());
        PASSWORD_ENCODERS.put(PWD_ENCODER_PLAINTEXT, NoOpPasswordEncoder.getInstance());
        PASSWORD_ENCODERS.put(PWD_ENCODER_MD5, new MessageDigestPasswordEncoder("MD5"));
        PASSWORD_ENCODERS.put(PWD_ENCODER_SHA, new MessageDigestPasswordEncoder("SHA-1"));
        PASSWORD_ENCODERS.put(PWD_ENCODER_SHA_256, new MessageDigestPasswordEncoder("SHA-256"));
        PASSWORD_ENCODERS.put(PWD_ENCODER_SHA_512, new MessageDigestPasswordEncoder("SHA-512"));
        PASSWORD_ENCODERS.put(PWD_ENCODER_STANDARD, new StandardPasswordEncoder());
    }

    private PasswordEncoderFactory() {
    }

    public static PasswordEncoder getInstance(String encoderId) {
        return new DelegatingPasswordEncoder(encoderId, PASSWORD_ENCODERS);
    }

}
