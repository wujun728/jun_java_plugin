package com.zengtengpeng.annotation;

import java.lang.annotation.*;

/**
 * MQ发送消息注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQPublish {
    /**
     * topic name
     * @return
     */
    String name();
}
