package TwoPhaseTermination.A4;

public class CountupThread extends GracefulThread {
    private long counter = 0;

    
    protected void doWork() throws InterruptedException {
        counter++;
        System.out.println("doWork: counter = " + counter);
        Thread.sleep(500);
    }

   
    protected void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }
}
