class Demo implements Runnable
{
	private int ticket = 10 ;
	// 加入一个同步方法
	public synchronized void fun() 
	{
		// 把需要同步的地方放在同步方法之中
		if(this.ticket>0)
		{
			try
			{
				Thread.sleep(100) ;
			}
			catch (Exception e)
			{
			}
			System.out.println(Thread.currentThread().getName()+" --> 卖票："+this.ticket--) ;
		}
	}
	public void run()
	{
		while(ticket>0)
		{
			this.fun() ;
		}
	}
};
public class ThreadDemo14
{
	public static void main(String args[])
	{
		Demo d = new Demo() ;
		Thread t1 = new Thread(d,"售票点 A") ;
		Thread t2 = new Thread(d,"售票点 B") ;
		Thread t3 = new Thread(d,"售票点 C") ;

		t1.start() ;
		t2.start() ;
		t3.start() ;
	}
};