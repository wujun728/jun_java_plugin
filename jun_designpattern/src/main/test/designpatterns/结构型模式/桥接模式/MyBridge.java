package designpatterns.结构型模式.桥接模式;

/**
 * 
 * 相当于驱动管理器，DriverManager
 */
public class MyBridge extends Bridge {
    
    public void connect() {
        getSource().connect();
    }
}