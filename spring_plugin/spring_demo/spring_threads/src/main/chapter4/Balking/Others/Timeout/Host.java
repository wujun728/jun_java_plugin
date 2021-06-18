package Balking.Others.Timeout;

public class Host {
    private final long timeout; // timeout值
    private boolean ready = false; //如果可以执行的话为true

    public Host(long timeout) {
        this.timeout = timeout;
    }

    // 更改状态
    public synchronized void setExecutable(boolean on) {
        ready = on;
        notifyAll();
    }                                                           

    // 评断状态后执行
    public synchronized void execute() throws InterruptedException, TimeoutException {
        long start = System.currentTimeMillis(); //开始时刻
        while (!ready) {
            long now = System.currentTimeMillis(); //现在时刻
            long rest = timeout - (now - start); //现在等待的时间
            if (rest <= 0) {
                throw new TimeoutException("now - start = " + (now - start) + ", timeout = " + timeout);
            }
            wait(rest);
        }
        doExecute();
    }

    // 实际的处理动作
    private void doExecute() {
        System.out.println(Thread.currentThread().getName() + " calls doExecute");
    }
}
