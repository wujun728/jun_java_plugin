package org.itkk.udf.datasource.dynamic;

/**
 * 描述 : 数据源本地线程
 *
 * @author wangkang
 */
public class DataSourceThreadLocal extends ThreadLocal<String> {
    /*
     * 修改点参考:http://blog.csdn.net/cxh5060/article/details/49275633
     * @see java.lang.ThreadLocal#remove()
     */
    @Override
    public void remove() {
        super.remove();
        this.initialValue();
    }
}
