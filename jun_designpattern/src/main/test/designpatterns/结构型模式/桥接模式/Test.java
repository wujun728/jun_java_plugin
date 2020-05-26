package designpatterns.结构型模式.桥接模式;

public class Test {
    
    /**
     * 联系JDBC的连接，来理解该方法调用
     */
    public static void main(String[] args) {
        
        //相当于驱动管理器，DriverManager
        Bridge bridge = new MyBridge();

        
        /*mysql驱动的实现*/
        JdbcInterface mysqldriver = new MysqlDriver();
        
        //设置mysql的数据库连接驱动
        bridge.setSource(mysqldriver);
        
        //连接mysql数据库
        bridge.connect();

        
        
        //===============================================================================================================
        
        
        
        /*oracle驱动的实现*/
        JdbcInterface oracledriver = new OracleDriver();
        
        //设置oracle的数据库连接驱动
        bridge.setSource(oracledriver);
        
        //连接oracle数据库
        bridge.connect();
    }

    /**
     * 
     * 这样，就通过对Bridge类的调用，实现了对接口JdbcInterface的实现类MysqlDriver和OracleDriver的调用。
     * 
     * 
     */
}
