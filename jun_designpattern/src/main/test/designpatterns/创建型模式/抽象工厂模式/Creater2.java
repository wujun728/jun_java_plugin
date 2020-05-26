package designpatterns.创建型模式.抽象工厂模式;

/**
 * 具体工厂类,继承抽象工厂Creater
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:39:37
 */
public class Creater2 extends Creater {

    //创建产品A2
    @Override
    public Product createProductA() {
        return new ProductA2();
    }

  //创建产品B2
    @Override
    public Product createProductB() {
        return new ProductB2();
    }

}
