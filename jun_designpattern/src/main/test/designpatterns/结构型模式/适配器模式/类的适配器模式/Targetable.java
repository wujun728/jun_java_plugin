package designpatterns.结构型模式.适配器模式.类的适配器模式;

/**
 * 目标接口
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午5:03:48
 */
public interface Targetable {
    
    /* 与原类中的方法相同，如 Source 类中的方法名称 */
    public void method1();

    /* 新类的方法 */
    public void method2();
}
