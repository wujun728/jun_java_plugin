package designpatterns.创建型模式.抽象工厂模式;

/**
 * 具体产品类 A2，继承了抽象类 Product
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 上午10:32:22
 */
public class ProductA2 extends Product {

    @Override
    public void dosomething() {
        System.out.println("这是产品A2");
    }

}
