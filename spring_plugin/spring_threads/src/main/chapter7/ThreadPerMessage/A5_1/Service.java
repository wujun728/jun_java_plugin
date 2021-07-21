package ThreadPerMessage.A5_1;

public class Service {
    public static void service() {
        new Thread() {
            public void run() {
                doService();
            }
        }.start();
    }
    private static void doService() {
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
}
