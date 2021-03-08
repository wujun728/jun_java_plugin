package TwoPhaseTermination.Sample;

public class CountupThread extends Thread {
    // 计数器的值
    private long counter = 0;

    // 已经送出终止请求则为true
    private volatile boolean shutdownRequested = false;

    //  终止请求
    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // 判断终求请求是否已经送出
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    //动作
    public final void run() {
        try {
            while (!shutdownRequested) {
                doWork();
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    //  作业
    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500);
    }

    // 终止处理
    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }
}
