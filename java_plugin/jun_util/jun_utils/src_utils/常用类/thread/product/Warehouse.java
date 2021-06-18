package book.thread.product;
/**
 * 产品的仓库类
 * 内部采用数组来表示循环队列，以存放产品。
 */
public class Warehouse {
	//仓库的容量
	private static int CAPACITY = 11;
	//仓库里的产品
	private Product[] products;
	
	//[front, rear)区间的产品是未被消费的
	//当前仓库中第一个未被消费的产品的下标
	private int front = 0;
	//仓库中最后一个未被消费的产品的下标加1
	private int rear = 0;

	public Warehouse(){
		this.products = new Product[CAPACITY];
	}
	public Warehouse(int capacity) {
		this();
		if (capacity > 0){
			CAPACITY = capacity + 1;
			this.products = new Product[CAPACITY];
		}
	}
	/**
	 * 从仓库获取一个产品
	*/
	public Product getProduct() throws InterruptedException {
		synchronized (this) {
			//标志消费者线程是否还在运行
			boolean consumerRunning = true;
			//获取当前线程
			Thread currentThread = Thread.currentThread();
			if (currentThread instanceof Consumer){
				consumerRunning = ((Consumer)currentThread).isRunning();
			} else {
				//非消费者不能获取产品
				return null;
			}
			//如果仓库中没有产品，而且消费者线程还在运行，则消费者线程继续等待。
			while ((front == rear) && consumerRunning){
				wait();
				consumerRunning = ((Consumer)currentThread).isRunning();
			}
			//如果消费者线程已经没有运行了，则退出该方法，取消获取产品
			if (!consumerRunning){
				return null;
			}
			//取当前未被消费的第一个产品
			Product product = products[front];
			System.out.println("Consumer[" + currentThread.getName()
					+ "] getProduct: " + product);
			//将当前未被消费产品的下标后移一位，如果到了数组末尾，则移动到首部。
			front = (front + 1 + CAPACITY) % CAPACITY;
			System.out.println("仓库中还没有被消费的产品数量："
					+ (rear + CAPACITY - front) % CAPACITY);
			//通知其他等待线程
			notify();
			return product;
		}
	}
	/**
	 * 向仓库存储一个产品
	 */
	public void storageProduct(Product product) throws InterruptedException {
		synchronized (this) {
			//标志生产者线程是否在运行
			boolean producerRunning = true;
			//获取当前线程
			Thread currentThread = Thread.currentThread();
			if (currentThread instanceof Producer){
				producerRunning = ((Producer)currentThread).isRunning();
			} else {
				//不是生产者不能存储产品
				return;
			}
			//如果最后一个未被消费产品与第一个未被消费的产品的下标紧挨着，
			//则说明没有存储空间，如果没有存储空间而且生产者线程还在运行，则等待仓库释放产品。
			while (((rear + 1) % CAPACITY == front) && producerRunning) {
				wait();
				producerRunning = ((Producer)currentThread).isRunning();
			}
			//如果生产者线程已经停止了，则停止产品的存储。
			if (!producerRunning){
				return;
			}
			//保存参数产品到仓库
			products[rear] = product;
			System.out.println("Producer[" + Thread.currentThread().getName()
					+ "] storageProduct: " + product);
			//将rear下标循环后移一位
			rear = (rear + 1) % CAPACITY;
			System.out.println("仓库中还没有被消费的产品数量："
					+ (rear + CAPACITY - front) % CAPACITY);
			notify();
		}
	}
}
