package designpatterns.结构型模式.桥接模式;

/**
 * 定义一个桥，持有 JdbcInterface 的一个实例
 * 
 */
public abstract class Bridge {
    
    //mysql、oracle的数据库连接的驱动实现
    private JdbcInterface jdbcInterface;

    
    /**
     * 连接数据库
     * 
     */
    public void connect() {
        jdbcInterface.connect();
    }

    
    public JdbcInterface getSource() {
        return jdbcInterface;
    }

    public void setSource(JdbcInterface source) {
        this.jdbcInterface = source;
    }
}