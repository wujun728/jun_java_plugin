package com.jun.plugin.designpatterns.创建型模式.原型模式;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 声明一个克隆自身的接口————深复制
 * 
 * 作者: zhoubang 日期：2015年10月27日 下午3:55:01
 */
public class Prototype_深复制 implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    // 拥有name属性
    private String name;
    private SerializableObject obj;

    // 一个原型类，只需要实现Cloneable接口，覆写clone方法，此处clone方法可以改成任意的名称，因为Cloneable接口是个空接口，你可以任意定义实现类的方法名，如cloneA或者cloneB。
    // 因为此处的重点是super.clone()这句话，super.clone()调用的是Object的clone()方法，而在Object类中，clone()是native的。

    /**
     * 对象深、浅复制的概念： 浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。
     * 深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。
     * 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * 
     */

    // 此方法为“深复制”。
    public Object deepClone() throws IOException, ClassNotFoundException {
        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /* 读出二进制流产生的新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }																							

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public SerializableObject getObj() {
        return obj;
    }

    public void setObj(SerializableObject obj) {
        this.obj = obj;
    }
}


/**
 * 声明内部类，方便测试使用
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午4:21:40
 */
class SerializableObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String sex;//性别名称
    
    //构造方法
    public SerializableObject(String type) {
        setSex(type);
    }
    
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    
}