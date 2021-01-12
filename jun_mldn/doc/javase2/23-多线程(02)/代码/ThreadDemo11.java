class Demo implements Runnable
{
	public void run()
	{
		for(int i=0;i<100;i++)
		{
			System.out.println(Thread.currentThread().getName()+" --> 运行。i = "+i) ;
		}
	}
};
public class ThreadDemo11
{
	public static void main(String args[])
	{
		Thread t = new Thread(new Demo(),"线程") ;
		t.start() ;
		for(int i=0;i<100;i++)
		{
			if(i==10)
			{
				try
				{
					t.join() ;
				}
				catch (Exception e)
				{
				}
			}
			System.out.println(Thread.currentThread().getName()+" --> 运行。i = "+i) ;
		}
	}
};