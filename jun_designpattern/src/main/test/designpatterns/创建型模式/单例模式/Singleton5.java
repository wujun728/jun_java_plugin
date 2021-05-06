package designpatterns.创建型模式.单例模式;

public class Singleton5 {
    private static Singleton5 instance = null;

    private Singleton5() {
    }

    /**
     * 单独为创建对象方法加上synchronized关键字
     * 
     * 作者: zhoubang 
     * 日期：2015年10月27日 上午11:24:08
     */
    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Singleton5();
        }
    }

    /**
     * 获取实例对象的方法 与 创建实例对象的方法分开
     * 
     * 作者: zhoubang 
     * 日期：2015年10月27日 上午11:24:30
     * @return
     */
    public static Singleton5 getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    
    /**
     * 
     * 考虑性能的话，整个程序只需创建一次实例，所以性能也不会有什么影响。
     * 
     * 
     * 补充：为单例对象的属性进行同步更新，详细代码请点击查看 Singleton6.java
     * 
     */
}
