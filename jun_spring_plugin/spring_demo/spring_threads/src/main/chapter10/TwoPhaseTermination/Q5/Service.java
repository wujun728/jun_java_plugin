package TwoPhaseTermination.Q5;

public class Service {
    // 开始执行服务
    public static void service() {
        System.out.print("service");
        for (int i = 0; i < 50; i++) {
            System.out.print(".");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("done.");
    }

    //终止服务
    public static void cancel() {
        // 尚未制作
    }
}
