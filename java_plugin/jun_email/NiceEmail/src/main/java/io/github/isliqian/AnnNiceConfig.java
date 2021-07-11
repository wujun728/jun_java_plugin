package io.github.isliqian;

import java.lang.annotation.*;


/**
 * Created by LiQian_Nice on 2018/3/18
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AnnNiceConfig {
    /**
     * 发送类型，QQ or 163
     * @return
     */
    String type();

    /**
     * 用户名
     * @return
     */
    String username();

    /**
     * 密码
     * @return
     */
    String password();






}
