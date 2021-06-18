package ThreadPerMessage.A5_3;

public class Service {
    private static volatile boolean working = false;
    public static synchronized void service() {
        System.out.print("service");
        if (working) {
            System.out.println(" is balked.");
            return;
        }
        working = true;
        new Thread() {
            public void run() {
                doService();
            }
        }.start();
    }
    private static void doService() {
        try {
            for (int i = 0; i < 50; i++) {
                System.out.print(".");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            System.out.println("done.");
        } finally {
            working = false;
        }
    }
}
