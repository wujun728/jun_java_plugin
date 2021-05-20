package GuardedSuspension.Q5;

public class TalkThread extends Thread {
    private RequestQueue input;
    private RequestQueue output;
    public TalkThread(RequestQueue input, RequestQueue output, String name) {
        super(name);
        this.input = input;
        this.output = output;
    }
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":BEGIN");
        for (int i = 0; i < 20; i++) {
            // 取得对方的请求
            Request request1 = input.getRequest();
            System.out.println(Thread.currentThread().getName() + " gets  " + request1);

            // 加上一个感叹号(!)传给对方
            Request request2 = new Request(request1.getName() + "!");
            System.out.println(Thread.currentThread().getName() + " puts  " + request2);
            output.putRequest(request2);
        }
        System.out.println(Thread.currentThread().getName() + ":END");
    }
}
