package designpatterns.创建型模式.抽象工厂模式;

/**
 * 具体产品类 B2，继承抽象类 Product
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:33:52
 */
public class ProductB2 extends Product {

    @Override
    public void dosomething() {
        System.out.println("这里是产品B2");
    }

}
