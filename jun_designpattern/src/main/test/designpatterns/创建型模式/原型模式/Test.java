package designpatterns.创建型模式.原型模式;

import java.io.IOException;

/**
 * 测试
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午3:59:36
 */
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        
        //测试浅复制的
        
        //创建一个对象
        Prototype_浅复制 pro = new ConcretePrototype_浅复制("浅复制名称");
        //克隆上面的对象，包括其属性
        Prototype_浅复制 pro2 = (Prototype_浅复制) pro.clone();
        
        System.out.println(pro.getName());//输出prototype
        System.out.println(pro2.getName());//输出prototype
        
        
        
        //========================================================================================================
        
        
        
        //测试深复制的
        
        //创建一个对象
        ConcretePrototype_深复制 pro3 = new ConcretePrototype_深复制("深复制名称","性别男");
        //克隆上面的对象，包括其属性
        Prototype_深复制 pro4 = (Prototype_深复制) pro3.deepClone();
        
        System.out.println(pro4.getName());
        System.out.println(pro4.getObj().getSex());
    }

}
