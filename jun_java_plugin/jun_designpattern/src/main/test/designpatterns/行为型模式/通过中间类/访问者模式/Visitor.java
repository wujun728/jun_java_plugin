package designpatterns.行为型模式.通过中间类.访问者模式;

/**
 * 存放要访问的对象
 * 
 * 作者: zhoubang 
 * 日期：2015年10月29日 上午9:51:15
 */
public interface Visitor {
    
    public void visit(Subject sub);
}