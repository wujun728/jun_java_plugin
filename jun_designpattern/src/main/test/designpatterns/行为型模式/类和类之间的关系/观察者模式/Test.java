package designpatterns.行为型模式.类和类之间的关系.观察者模式;

public class Test {

    public static void main(String[] args) {
        
        Subject sub = new MySubject();
        
        //添加观察者
        sub.add(new Observer1());
        sub.add(new Observer2());

        //自身操作。通知所有观察者
        sub.operation();
    }

}
