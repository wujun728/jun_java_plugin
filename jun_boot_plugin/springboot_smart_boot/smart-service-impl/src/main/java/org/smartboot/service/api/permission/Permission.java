package org.smartboot.service.api.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
	/**
	 * 请求行为
	 * 
	 * @return
	 */
	String act();

	/**
	 * 权限组
	 * 
	 * @return
	 */
	PermissionEnum[] value() default {};

	/**
	 * 扩展权限配置
	 * 
	 * @return
	 */
	String[] ExtendedPermision() default {};

	/**
	 * 权限关系
	 * @return
	 */
	PermissionRelation PermissionRelation() default PermissionRelation.OR;

}
