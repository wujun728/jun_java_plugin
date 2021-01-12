class Demo implements Runnable
{
	private int ticket = 10 ;
	public void run()
	{
		while(this.ticket>0)
		{
			System.out.println("卖票："+this.ticket--) ;
		}
	}
};
public class ThreadDemo05
{
	public static void main(String args[])
	{
		// 四个售票点应该控制同一个资源：10
		Demo d = new Demo() ;

		Thread t1 = new Thread(d) ;
		Thread t2 = new Thread(d) ;
		Thread t3 = new Thread(d) ;
		Thread t4 = new Thread(d) ;

		t1.start() ;
		t1.start() ;
		t1.start() ;
		t2.start() ;
		t3.start() ;
		t4.start() ;
	}
};