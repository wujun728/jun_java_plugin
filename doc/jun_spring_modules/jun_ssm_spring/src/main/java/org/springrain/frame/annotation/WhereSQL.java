
/**
 * 
 */
package org.springrain.frame.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO 用于queryBean的geter 方法,主要是用于查询条件的映射<br/>
 * 例如:@WhereSQL(sql="id=:Auditlog_id"),表示getId()返回值直接作为:Auditlog_id的命名参数的值
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.annotation.WhereSQL
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WhereSQL  {
	/**
	 * 设置where条件
	 * @return
	 */
	String sql() default "";  
}
