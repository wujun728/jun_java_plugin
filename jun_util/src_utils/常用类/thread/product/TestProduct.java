package book.thread.product;
/**
 * 测试生产者——消费者
 * 支持多个生产者和消费者
 */
public class TestProduct {

	public static void main(String[] args) {
		//建立一个仓库，容量为10
		Warehouse warehouse = new Warehouse(10);
		
		//建立生产者和消费者
		Producer producers1 = new Producer(warehouse, "producer-1");
		Producer producers2 = new Producer(warehouse, "producer-2");
		Producer producers3 = new Producer(warehouse, "producer-3");
		Consumer consumers1 = new Consumer(warehouse, "consumer-1");
		Consumer consumers2 = new Consumer(warehouse, "consumer-2");
		Consumer consumers3 = new Consumer(warehouse, "consumer-3");
		Consumer consumers4 = new Consumer(warehouse, "consumer-4");
		//启动生产者和消费者线程
		producers1.start();
		producers2.start();
		consumers1.start();
		producers3.start();
		consumers2.start();
		consumers3.start();
		consumers4.start();
		//让生产者消费者程序运行1600ms
		try {
			Thread.sleep(1600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//停止生产者和消费者的线程
		producers1.stopProducer();
		consumers1.stopConsumer();
		producers2.stopProducer();
		consumers2.stopConsumer();
		producers3.stopProducer();
		consumers3.stopConsumer();
		consumers4.stopConsumer();
	}
}
