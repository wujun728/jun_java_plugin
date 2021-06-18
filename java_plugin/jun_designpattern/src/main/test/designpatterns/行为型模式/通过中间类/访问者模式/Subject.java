package designpatterns.行为型模式.通过中间类.访问者模式;

public interface Subject {
    
    //接受将要访问它的对象
    public void accept(Visitor visitor);

    //获取将要被访问的属性
    public String getSubject();
}