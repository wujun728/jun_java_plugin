package GuardedSuspension.A6;

import java.util.Random;

public class ServerThread extends Thread {
    private Random random;
    private RequestQueue requestQueue;
    public ServerThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
        this.random = new Random(seed);
    }
    public void run() {
        try {
            for (int i = 0; i < 10000; i++) {
                Request request = requestQueue.getRequest();
                System.out.println(Thread.currentThread().getName() + " handles  " + request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}
