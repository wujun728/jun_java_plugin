package designpatterns.结构型模式.桥接模式;

/**
 * 实现了JDBC统一接口，这里可以比如成 mysql 的驱动连接的实现
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午2:24:39
 */
public class MysqlDriver implements JdbcInterface {

    @Override
    public void connect() {
        System.out.println("mysql驱动连接mysql数据库...");
    }
}