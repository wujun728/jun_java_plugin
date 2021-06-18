public class ThreadDemo13 extends Object {
	public static void main(String[] args) {
			Thread t = Thread.currentThread();
			System.out.println("Point A: t.isInterrupted()=" + t.isInterrupted());
			t.interrupt();
			System.out.println("Point B: t.isInterrupted()=" + t.isInterrupted());
			System.out.println("Point C: t.isInterrupted()=" + t.isInterrupted());

			try {
					Thread.sleep(2000);
					System.out.println("was NOT interrupted");
			} catch ( InterruptedException x ) {
					System.out.println("was interrupted");
			}
			// 在这里因为sleep抛出了异常，所以它清除了中断标志
			System.out.println("Point D: t.isInterrupted()=" + t.isInterrupted());
	}
}
