package ThreadPerMessage.A5_4;

public class Service {
    private static Thread worker = null;
    public static synchronized void service() {
        // 如果处理在执行中，可用interrupt取消  
        if (worker != null && worker.isAlive()) {
            worker.interrupt();
            try {
                worker.join();
            } catch (InterruptedException e) {
            }
            worker = null;
        }
        System.out.print("service");
        worker = new Thread() {
            public void run() {
                doService();
            }
        };
        worker.start();
    }
    private static void doService() {
        try {
            for (int i = 0; i < 50; i++) {
                System.out.print(".");
                Thread.sleep(100);
            }
            System.out.println("done.");
        } catch (InterruptedException e) {
            System.out.println("cancelled.");
        }
    }
}
