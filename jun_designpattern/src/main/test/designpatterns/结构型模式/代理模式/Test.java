package designpatterns.结构型模式.代理模式;

public class Test {

    public static void main(String[] args) {
        
        //创建代理对象
        Sourceable source = new Proxy();
        
        //通过代理对象，调用被代理对象的方法。
        source.method();
    }

}
