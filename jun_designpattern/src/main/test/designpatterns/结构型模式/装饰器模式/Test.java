package designpatterns.结构型模式.装饰器模式;

public class Test {

    public static void main(String[] args) {
        
        //被装饰类对象
        Sourceable source = new Source();
        
        //将被装饰类对象传递给装饰类，由装饰类发起方法调用，在执行方法的前后加入其他业务逻辑代码
        Sourceable obj = new Decorator(source);
        
        //执行被装饰类的方法
        obj.method();
    }

}
