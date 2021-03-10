package TwoPhaseTermination.A7;


public class Main {
    public static void main(String[] args) {
        System.out.println("main: BEGIN");
        try {
            // 启动线程
            HanoiThread t = new HanoiThread();
            t.start();

            // 稍微空出的一端时间
            Thread.sleep(10000);

            // 对线程送出终至请求
            System.out.println("main: shutdownRequest");
            t.shutdownRequest();

            System.out.println("main: join");

            // 等待线程结束
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main: END");
    }
}
