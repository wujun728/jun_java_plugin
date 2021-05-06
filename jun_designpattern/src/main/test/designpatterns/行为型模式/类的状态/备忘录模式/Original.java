package designpatterns.行为型模式.类的状态.备忘录模式;

/**
 * 原始类，里面有需要保存的属性value及创建一个备忘录类，用来保存value值
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:36:59
 */
public class Original {

    //需要保存备份的属性
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Original(String value) {
        this.value = value;
    }

    //创建备忘录
    public Memento createMemento() {
        return new Memento(value);
    }

    //还原
    public void restoreMemento(Memento memento) {
        this.value = memento.getValue();
    }
}