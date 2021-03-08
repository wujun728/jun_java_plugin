package net.dreamlu.weixin.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 微信消息控制器
 *
 * @author Wujun
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@RequestMapping
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface WxMsgController {

	/**
	 * Alias for {@link RequestMapping#value}.
	 * @return {String[]}
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};

}
