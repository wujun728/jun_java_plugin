package designpatterns.行为型模式.类和类之间的关系.观察者模式;

/**
 * 观察者实现该接口，通知的时候，分别调用不同观察者的update方法
 * 
 * 作者: zhoubang 
 * 日期：2015年10月28日 下午5:03:06
 */
public interface Observer {
    public void update();
}