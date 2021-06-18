package TwoPhaseTermination.Others.Hook;

public class Main {
    public static void main(String[] args) {
        System.out.println("main:BEGIN");

        // 设定shutdown hook
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                    System.out.println("*****");
                    System.out.println(Thread.currentThread().getName() + ": SHUTDOWN HOOK!");
                    System.out.println("*****");
                }
            }
        );

        System.out.println("main:SLEEP...");

        // 约3秒后强制结束程序
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println("main:EXIT");

        // 在这里强制结束
        System.exit(0);

        // 不会执行到这里
        System.out.println("main:END");
    }
}
