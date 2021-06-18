package org.ws.httphelper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WSRequest {
    /**
     * <b>描述：</b> 名称<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String name();

    /**
     * <b>描述：</b> 请求路径<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String url();

    /**
     * <b>描述：</b> 返回值类型：默认普通文本<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public ResponseType responseType() default ResponseType.TEXT;

    /**
     * <b>描述：</b> 请求方式<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public MethodType method() default MethodType.GET;

    /**
     * <b>描述：</b> 描述<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public String description() default "";

    /**
     * <b>描述：</b> 参数列表<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public Parameter[] parameters() default {};

    /**
     * <b>描述：</b> 头部列表<br/>
     * <b>作者：</b>gz <br/>
     *
     * @return
     */
    public Header[] headers() default {};

    /**
     * 请求编码
     *
     * @return
     */
    public String charset() default "";

    /**
     * 返回值类型，用于自动解析
     *
     * @return
     */
    public Class<?> resultClass() default String.class;

    /**
     * <b>描述：</b> 返回值类型：根据类型自动解析结果<br/>
     * <b>作者：</b> gz <br/>
     * <b>创建时间：</b> 2015-10-13 下午01:26:41 <br/>
     */
    enum ResponseType {
        /**
         * 普通文本：不做处理
         */
        TEXT,// 不做解析
        /**
         * HTML：不做处理
         */
        HTML,// 不做解析
        /**
         * JSON：默认解析为map
         */
        JSON,// 解析为map
        /**
         * XML：默认解析为map
         */
        XML,// 解析为map
        /**
         * byte数组：不做解析
         */
        BYTE_ARRAY// 返回byte[]
    }

    /**
     * <b>描述：</b> 请求方式<br/>
     * <b>作者：</b> gz <br/>
     * <b>创建时间：</b> 2015-10-13 下午01:26:57 <br/>
     */
    enum MethodType {
        GET,
        POST,
        DELETE
    }
}
