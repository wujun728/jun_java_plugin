package dbutils.transaction;


import java.sql.Connection;

/**
* @ClassName: ConnectionContext
* @Description:数据库连接上下文
* @author: 孤傲苍狼
* @date: 2014-10-7 上午8:36:01
*
*/ 
public class ConnectionContext {

    /**
     * 构造方法私有化，将ConnectionContext设计成单例
     */
    private ConnectionContext(){
        
    }
    //创建ConnectionContext实例对象
    private static ConnectionContext connectionContext = new ConnectionContext();
    
    /**
    * @Method: getInstance
    * @Description:获取ConnectionContext实例对象
    * @Anthor:孤傲苍狼
    *
    * @return
    */ 
    public static ConnectionContext getInstance(){
        return connectionContext;
    }
    
    /**
    * @Field: connectionThreadLocal
    *         使用ThreadLocal存储数据库连接对象
    */ 
    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
    
    /**
    * @Method: bind
    * @Description:利用ThreadLocal把获取数据库连接对象Connection和当前线程绑定
    * @Anthor:孤傲苍狼
    *
    * @param connection
    */ 
    public void bind(Connection connection){
        connectionThreadLocal.set(connection);
    }
    
    /**
    * @Method: getConnection
    * @Description:从当前线程中取出Connection对象
    * @Anthor:孤傲苍狼
    *
    * @return
    */ 
    public Connection getConnection(){
        return connectionThreadLocal.get();
    }
    
    /**
    * @Method: remove
    * @Description: 解除当前线程上绑定Connection
    * @Anthor:孤傲苍狼
    *
    */ 
    public void remove(){
        connectionThreadLocal.remove();
    }
}