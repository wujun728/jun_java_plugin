
/**
 * 
 */
package org.springrain.frame.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  用于Entity的注解,不希望记录日志
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.annotation.NotLog
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotLog  {
	

}
