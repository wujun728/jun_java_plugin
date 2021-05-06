package designpatterns.创建型模式.建造者模式;

/**
 * 为创建一个对象的各个部件指定抽象接口
 * 
 * 作者: zhoubang 
 * 日期：2015年10月27日 下午4:35:01
 */
public interface PersonBuilder {

    void buildHead();

    void buildBody();

    void buildFoot();

    Person buildPerson();
}