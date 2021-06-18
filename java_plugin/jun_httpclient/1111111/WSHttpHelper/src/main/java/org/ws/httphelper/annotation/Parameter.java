package org.ws.httphelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>描述：</b> 请求参数<br/>
 * <b>作者：</b> gz <br/>
 * <b>创建时间：</b> 2015-10-12 下午01:53:01 <br/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Parameter {
    /**
     * <b>描述：</b> 参数字段<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String name();

    /**
     * <b>描述：</b> 参数描述<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String description() default "";

    /**
     * <b>描述：</b> 默认值<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String defaultValue() default "";

    /**
     * <b>描述：</b> 参数类型<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public Type type() default Type.STRING;

    /**
     * <b>描述：</b> 是否必须，默认false<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public boolean required() default false;

    /**
     * <b>描述：</b> 参数示例<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String example() default "";

    /**
     * <b>描述：</b> 参数简单验证正则表达式<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String validateRegex() default "";

    /**
     * <b>描述：</b> 类型<br/>
     * <b>作者：</b> gz <br/>
     * <b>创建时间：</b> 2015-10-12 下午01:50:21 <br/>
     */
    enum Type {
        STRING,
        LIST,
        INT,
        FILE
    }
}
