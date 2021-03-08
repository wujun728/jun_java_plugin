package ProducerConsumer.A8;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);     // 建立一个只能放3个蛋糕的桌子?
        new MakerThread("MakerThread-1", table, 31415).start();
        new MakerThread("MakerThread-2", table, 92653).start();
        new MakerThread("MakerThread-3", table, 58979).start();
        new EaterThread("EaterThread-1", table, 32384).start();
        new EaterThread("EaterThread-2", table, 62643).start();
        new EaterThread("EaterThread-3", table, 38327).start();
        new LazyThread("LazyThread-1", table).start();
        new LazyThread("LazyThread-2", table).start();
        new LazyThread("LazyThread-3", table).start();
        new LazyThread("LazyThread-4", table).start();
        new LazyThread("LazyThread-5", table).start();
        new LazyThread("LazyThread-6", table).start();
        new LazyThread("LazyThread-7", table).start();
    }
}
