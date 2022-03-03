
/**
 * 
 */
package org.springrain.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  用于Entity的注解,当主键值为空时,主要是用于数据库sequence取值
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.annotation.PKSequence
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface PKSequence  {
	/**
	 *sequence取值,默认为空,可以指定sequence取值方式 例如 oracle的 test.nextvalue,db2的 next value for test
	 * @return
	 */
	String name() default "";  

}
