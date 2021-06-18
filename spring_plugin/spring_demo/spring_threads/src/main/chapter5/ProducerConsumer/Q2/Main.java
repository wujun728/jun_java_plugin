package ProducerConsumer.Q2;

public class Main {
    public static void main(String[] args) {
        new MakerThread("Alice", new Table(3), 31415).start();
        new EaterThread("Bobby", new Table(3), 92653).start();
    }
}
