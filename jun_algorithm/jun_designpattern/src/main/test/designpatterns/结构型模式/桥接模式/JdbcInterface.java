package designpatterns.结构型模式.桥接模式;

/**
 * JDBC连接的统一接口，具体的又各大数据库厂家进行实现连接
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午2:23:59
 */
public interface JdbcInterface {
    
    /**
     * 连接数据库
     * 
     * 作者: zhoubang 
     * 日期：2015年10月28日 下午2:30:51
     */
    public void connect();
}