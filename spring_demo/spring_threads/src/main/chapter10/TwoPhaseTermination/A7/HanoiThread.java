package TwoPhaseTermination.A7;

public class HanoiThread extends Thread {
    // 已经送出终止请求则为true
    private volatile boolean shutdownRequested = false;
    //送出终止请求的时刻
    private volatile long requestedTimeMillis = 0;

    //  终止请求
    public void shutdownRequest() {
        requestedTimeMillis = System.currentTimeMillis();
        shutdownRequested = true;
        interrupt();
    }

    //  判断终止请求是否已经送出
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    // 动作
    public void run() {
        try {
            for (int level = 0; !shutdownRequested; level++) {
                System.out.println("==== Level " + level + " ====");
                doWork(level, 'A', 'B', 'C');
                System.out.println("");
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    // 作业
    private void doWork(int level, char posA, char posB, char posC) throws InterruptedException {
        if (level > 0) {
            if (shutdownRequested) {
                throw new InterruptedException();
            }
            doWork(level - 1, posA, posC, posB);
            System.out.print(posA + "->" + posB + " ");
            doWork(level - 1, posC, posB, posA);
        }
    }

    // 终止处理
    private void doShutdown() {
        long time = System.currentTimeMillis() - requestedTimeMillis;
        System.out.println("doShutdown: Latency = " + time + " msec.");
    }
}
