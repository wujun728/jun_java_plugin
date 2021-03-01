package designpatterns.结构型模式.代理模式;

/**
 * 代理类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 上午11:25:16
 */
public class Proxy implements Sourceable {

    //拥有 被代理类 的实例
    private Source source;

    public Proxy() {
        super();
        this.source = new Source();
    }

    @Override
    public void method() {
        //被代理方法执行之前，执行其他的操作
        before();
        
        source.method();
        
        //被代理方法执行之后，执行其他的操作
        atfer();
    }
    
    private void atfer() {
        System.out.println("被代理类方法执行之前，执行的方法...");
    }

    private void before() {
        System.out.println("被代理类方法执行之后，执行的方法...");
    }

}
