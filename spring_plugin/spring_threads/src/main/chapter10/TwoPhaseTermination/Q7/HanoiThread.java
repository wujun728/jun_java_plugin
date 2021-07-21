package TwoPhaseTermination.Q7;

public class HanoiThread extends Thread {
    // �w�g�e�X�פ�ШD�h��true
    private volatile boolean shutdownRequested = false;
    // �e�X�פ�ШD���ɨ�
    private volatile long requestedTimeMillis = 0;

    // �פ�ШD
    public void shutdownRequest() {
        requestedTimeMillis = System.currentTimeMillis();
        shutdownRequested = true;
        interrupt();
    }

    // �P�_�פ�ШD�O�_�w�g�e�X
    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    // �ʧ@
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

    // �@�~
    private void doWork(int level, char posA, char posB, char posC) throws InterruptedException {
        if (level > 0) {
            doWork(level - 1, posA, posC, posB);
            System.out.print(posA + "->" + posB + " ");
            doWork(level - 1, posC, posB, posA);
        }
    }

    // �פ�B�z
    private void doShutdown() {
        long time = System.currentTimeMillis() - requestedTimeMillis;
        System.out.println("doShutdown: Latency = " + time + " msec.");
    }
}
