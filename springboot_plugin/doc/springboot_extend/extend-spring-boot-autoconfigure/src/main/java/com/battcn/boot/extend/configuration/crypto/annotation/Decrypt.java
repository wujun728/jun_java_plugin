package com.battcn.boot.extend.configuration.crypto.annotation;


import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解密
 *
 * @author Levin
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {


    /**
     * 如果选择的 RSA 加/解密算法，那么 key 为必填项
     *
     * @return CryptoType
     */
    CryptoType type() default CryptoType.AES;

    /**
     * 可选，如果未配置则采用全局的key
     *
     * @return String
     */
    String key() default "";

    /**
     * 描述信息
     *
     * @return String
     */
    String description() default "";

}
