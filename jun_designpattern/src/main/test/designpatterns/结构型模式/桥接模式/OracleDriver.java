package designpatterns.结构型模式.桥接模式;

/**
 * 实现了JDBC统一接口，这里可以比如成 oracle 的驱动连接的实现
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午2:25:26
 */
public class OracleDriver implements JdbcInterface {

    @Override
    public void connect() {
        System.out.println("Oracle驱动连接Oracle数据库...");
    }
}