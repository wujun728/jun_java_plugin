package TwoPhaseTermination.A2;

public class CountupThread extends Thread {
    // 计数器的值
    private long counter = 0;


    // 终止请求
    public void shutdownRequest() {
        interrupt();
    }

    // 动作
    public void run() {
        try {
            while (!isInterrupted()) {
                doWork();
            }
        } catch (InterruptedException e) {
        } finally {
            doShutdown();
        }
    }

    // 作业
    private void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        try {
        	Thread.sleep(500);
		} catch (InterruptedException e) {
		}
    }

    // 终止处理
    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }
}
