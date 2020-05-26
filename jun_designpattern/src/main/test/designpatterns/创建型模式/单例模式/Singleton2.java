package designpatterns.创建型模式.单例模式;

public class Singleton2 {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static Singleton2 instance = null;

    /* 私有构造方法，防止被实例化 */
    private Singleton2() {
    }

    /* 静态工程方法，创建实例 */
    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return instance;
    }
    
    
    /**
     * 虽然对 Singleton.java 中的 getInstance() 方法加上了synchronized关键字，但是synchronized关键字锁住的是这个对象，这样的用法，在性能上会有所下降，因为每次调用getInstance()，都要对对象上锁。
     * 事实上，只有在第一次创建对象的时候需要加锁，之后就不需要了，所以，这个地方需要改进。
     * 
     * 改动后的代码，请点击查看 Singleton3.java
     * 
     * 
     */
}
