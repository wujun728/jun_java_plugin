package ProducerConsumer.A6;

import ProducerConsumer.Sample.EaterThread;
import ProducerConsumer.Sample.MakerThread;
import ProducerConsumer.Sample.Table;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Table table = new Table(3);     
        Thread[] threads = {
            new MakerThread("MakerThread-1", table, 31415),
            new MakerThread("MakerThread-2", table, 92653),
            new MakerThread("MakerThread-3", table, 58979),
            new EaterThread("EaterThread-1", table, 32384),
            new EaterThread("EaterThread-2", table, 62643),
            new EaterThread("EaterThread-3", table, 38327),
        };

        
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        
       	try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        System.out.println("***** interrupt *****");

       
        for (int i = 0; i < threads.length; i++) {
            threads[i].interrupt();
        }
    }
}
