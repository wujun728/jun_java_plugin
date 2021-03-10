package net.dreamlu.weixin.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * 微信Api接口注解用于拦截器
 *
 * @author Wujun
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@Controller
public @interface WxApi {

    /**
     * Alias for {@link RequestMapping#value}.
     * @return {String[]}
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

//	/**
//	 * 目前不支持多小程序
//	 * @return {ApiType}
//	 */
//	ApiType type() default ApiType.WX;

}
