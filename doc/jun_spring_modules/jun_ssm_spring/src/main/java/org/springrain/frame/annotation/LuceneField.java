
/**
 * 
 */
package org.springrain.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记可以用于Lucene搜索,根据类名创建索引
 * 
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.annotation.LuceneField
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LuceneField {
    // 是否分词,仅支持字符串,主键强制不分词
    boolean stringTokenized() default true;

    // 是否进行lucene排序字段,仅支持数值和日期类型
    boolean numericSort() default true;
    
    //FacetField,暂未实现 facet,这样的场景建议换solr了
    //boolean luceneFacet() default false;

    // 字段是否保存,请谨慎修改
    boolean luceneStored() default true;

    // 字段是否索引,只有索引才能作为查询条件,请谨慎修改
    boolean luceneIndex() default true;
}
