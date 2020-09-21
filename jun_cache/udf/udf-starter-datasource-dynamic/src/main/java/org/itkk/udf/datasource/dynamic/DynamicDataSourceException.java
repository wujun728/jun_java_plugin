package org.itkk.udf.datasource.dynamic;

/**
 * <p>
 * ClassName: DynamicDataSourceException
 * </p>
 * <p>
 * Description: 动态数据源异常
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2016年3月23日
 * </p>
 */
public class DynamicDataSourceException extends RuntimeException {

    /**
     * <p>
     * Field serialVersionUID: 序列号
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param message 异常信息
     */
    public DynamicDataSourceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param cause 堆栈
     */
    public DynamicDataSourceException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param message 异常信息
     * @param cause   堆栈
     */
    public DynamicDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
