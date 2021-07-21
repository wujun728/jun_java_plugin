package WorkerThread.A3_1;

public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);   // Worker Thread��������
        channel.startWorkers();
        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }
}
