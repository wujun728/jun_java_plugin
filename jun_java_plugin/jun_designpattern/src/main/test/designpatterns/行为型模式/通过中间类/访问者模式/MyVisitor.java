package designpatterns.行为型模式.通过中间类.访问者模式;

public class MyVisitor implements Visitor {

    @Override
    public void visit(Subject sub) {
        System.out.println("visit the subject：" + sub.getSubject());
    }
}