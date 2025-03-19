package io.github.wujun728.encryptbody.annotation.decrypt;

import io.github.wujun728.encryptbody.enums.RSAKeyType;

import java.lang.annotation.*;

/**
 * @author licoy.cn
 * @version 2018/9/7
 * @see DecryptBody
 */
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RSADecryptBody {

    /**
     * 自定义私钥
     *
     * @return 私钥
     */
    String key() default "";

    /**
     * 类型
     *
     * @return 公钥
     */
    RSAKeyType type() default RSAKeyType.PUBLIC;

}
