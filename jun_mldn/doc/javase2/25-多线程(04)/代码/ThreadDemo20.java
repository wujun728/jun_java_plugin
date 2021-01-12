class Demo implements Runnable
{
	boolean flag = true ;
	public void run()
	{
		int i = 0 ;
		while(flag)
		{
			System.out.println("运行 i = "+i++) ;
		}
	}
};
public class ThreadDemo20
{
	public static void main(String args[])
	{
		// 希望while循环运行100毫秒之后程序要停止
		Demo d = new Demo() ;
		Thread t = new Thread(d) ;
		t.start() ;
		try
		{
			Thread.sleep(1000) ;
		}
		catch (Exception e)
		{
		}
		// 修改标志位，使线程停止
		d.flag = false ;
	}
};