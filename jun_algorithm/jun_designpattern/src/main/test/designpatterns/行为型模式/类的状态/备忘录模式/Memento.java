package designpatterns.行为型模式.类的状态.备忘录模式;

/**
 * 备忘录类
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:37:53
 */
public class Memento {

    private String value;

    public Memento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}