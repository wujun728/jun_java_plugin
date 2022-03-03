package org.springrain.weixin.sdk.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识某个字段是否是必填的
 * 
 * Created by springrain on 2017/1/25.
 * @author springrain (http://git.oschina.net/chunanyong/springrain)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {

}