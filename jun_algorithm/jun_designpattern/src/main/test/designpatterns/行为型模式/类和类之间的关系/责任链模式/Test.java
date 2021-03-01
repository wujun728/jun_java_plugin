package designpatterns.行为型模式.类和类之间的关系.责任链模式;

public class Test {
    public static void main(String[] args) {
        // 组装责任链
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setSuccessor(handler2);
        // 提交请求
        handler1.handleRequest();
    }
    
    
    /**
     * 
     * 可以看出，客户端创建了两个处理者对象，并指定第一个处理者对象的下家是第二个处理者对象，而第二个处理者对象没有下家。然后客户端将请求传递给第一个处理者对象。
     * 
     * 由于本示例的传递逻辑非常简单：只要有下家，就传给下家处理；如果没有下家，就自行处理。因此，第一个处理者对象接到请求后，会将请求传递给第二个处理者对象。由于第二个处理者对象没有下家，于是自行处理请求
     * 
     * 
     * 
     * 使用场景：
     * 来考虑这样一个功能:申请聚餐费用的管理，详情请查看 【聚餐例子演示责任链模式】
     */
}
