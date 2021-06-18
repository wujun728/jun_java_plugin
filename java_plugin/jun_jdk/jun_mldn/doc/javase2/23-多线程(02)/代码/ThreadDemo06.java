// 实现Runnable接口
class Demo implements Runnable
{
	public void fun()
	{
		System.out.println(Thread.currentThread()) ;
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			this.fun() ;
		}
	}
};
public class ThreadDemo06
{
	public static void main(String args[])
	{
		Demo d = new Demo() ;
		Thread t1 = new Thread(d) ;
		Thread t2 = new Thread(d) ;
		Thread t3 = new Thread(d) ;

		// 启动线程
		t1.start() ;
		t2.start() ;
		t3.start() ;
	}
};