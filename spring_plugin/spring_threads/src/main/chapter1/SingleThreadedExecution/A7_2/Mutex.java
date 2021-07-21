package SingleThreadedExecution.A7_2;

public final class Mutex {
    private long locks = 0;
    private Thread owner = null;
    public synchronized void lock() {
        Thread me = Thread.currentThread();
        while (locks > 0 && owner != me) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // locks == 0 || owner == me
        owner = me;
        locks++;
    }
    public synchronized void unlock() {
        Thread me = Thread.currentThread();
        if (locks == 0 || owner != me) {
            return;
        }
        // locks > 0 && owner == me
        locks--;
        if (locks == 0) {
            owner = null;
            notifyAll();
        }
    }
}
