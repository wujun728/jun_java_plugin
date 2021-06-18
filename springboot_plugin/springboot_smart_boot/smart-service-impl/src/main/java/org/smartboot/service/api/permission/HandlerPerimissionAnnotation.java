package org.smartboot.service.api.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API接口权限
 * 
 * @author Wujun
 * @version Permission.java, v 0.1 2016年6月16日 上午10:47:51 Seer Exp.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerPerimissionAnnotation {
	Permission[] value();
}
