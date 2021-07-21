package ThreadPerMessage.A7_1;

public class Blackhole {
    public static void enter(Object obj) {
        System.out.println("Step 1");
        magic(obj);
        System.out.println("Step 2");
        synchronized (obj) {
            System.out.println("Step 3 (never reached here)");  //  不会到这里来
        }
    }
    public static void magic(final Object obj) {
        // thread会取得obj的lock，变成无穷循环的执行绪
        // 将thread的名称当作Guard条件来使用
        Thread thread = new Thread() {      // inner class
            public void run() {
                synchronized (obj) { // 在此取得obj的锁定
                    synchronized (this) {
                        this.setName("Locked"); // Guard条件的变化
                        this.notifyAll();       // 通知已经取得obj的锁定
                    }
                    while (true) {
                        // 无穷循环
                    }
                }
            }
        };
        synchronized (thread) {
            thread.setName("");
            thread.start(); // 线程的启动
            // Guarded Suspension模式
            while (thread.getName().equals("")) {
                try {
                    thread.wait(); //  等待新的线程取得obj的锁定
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
