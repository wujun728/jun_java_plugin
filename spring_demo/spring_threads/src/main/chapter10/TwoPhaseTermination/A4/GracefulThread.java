package TwoPhaseTermination.A4;

public class GracefulThread extends Thread {
    // 已经送出终止请求则为true
    private volatile boolean shutdownRequested = false;

    // 终止请求
    public final void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    // 判断终止请求是否已经送出
    public final boolean isShutdownRequested() {
        return shutdownRequested;
    }

    // 动作
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

    // 作业
    protected void doWork() throws InterruptedException {
    }

    // 终止处理
    protected void doShutdown() {
    }
}
