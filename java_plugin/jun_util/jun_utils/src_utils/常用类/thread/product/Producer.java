package book.thread.product;
/**
 * 生产者类，采用线程，模拟生产者的行为
 */
class Producer extends Thread {
	// 生产者存储产品的仓库
	private Warehouse warehouse;
	// 产品的名字
	private static int produceName = 0;
	//是否需要结束线程的标志位
	private boolean running = false;

	public Producer(Warehouse warehouse, String name) {
		super(name);
		this.warehouse = warehouse;
	}

	public void start(){
		this.running = true;
		super.start();
	}

	public void run() {
		Product product;
		try {
			while (running) {
				//生产并存储产品
				product = new Product((++produceName) + "");
				this.warehouse.storageProduct(product);
				sleep(300);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	/**
	 * 停止生产者线程
	 */
	public void stopProducer(){
		synchronized (warehouse){
			this.running = false;
			//通知等待仓库的线程
			warehouse.notifyAll();
		}
	}
	//生产者线程是否在运行
	public boolean isRunning() {
		return running;
	}
	
}
