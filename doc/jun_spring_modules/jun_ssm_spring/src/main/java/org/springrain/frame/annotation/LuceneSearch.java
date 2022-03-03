
/**
 * 
 */
package org.springrain.frame.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  标记可以用于Lucene搜索,根据类名创建索引
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.annotation.LuceneSearch
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LuceneSearch  {
	

}
