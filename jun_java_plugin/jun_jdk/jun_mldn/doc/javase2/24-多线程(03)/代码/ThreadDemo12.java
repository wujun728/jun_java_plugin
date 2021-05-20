class Demo implements Runnable
{
	public void run()
	{
		System.out.println("1、Demo --> 程序进入休眠状态。") ;
		try
		{
			Thread.sleep(2000) ;
		}
		catch (Exception e)
		{
			System.out.println("2、Demo --> 休眠中断") ;
			return ;
		}
		System.out.println("3、Demo --> 程序正常退出。") ;
	}
};

public class ThreadDemo12
{
	public static void main(String args[])
	{
		Thread t = new Thread(new Demo(),"LXH") ;
		System.out.println(t.getName()+" --> 线程启动。") ;
		t.start() ;
		System.out.println("4、MAIN --> 让线程休眠") ;
		try
		{
			// 程序至少可以运行2000毫秒
			Thread.sleep(10000) ;
		}
		catch (Exception e)
		{
		}
		System.out.println("5、MAIN --> 中断线程。") ;
		t.interrupt() ;
		System.out.println("6、MAIN --> 程序退出。") ;
	}
};