// 编写一个类继承自Thread类
class Demo implements Runnable
{
	// 因为多线程需要一个程序的主体
	// 子类要覆写Thread类中的run方法
	private String info ;
	public Demo(String info)
	{
		this.info = info ;
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			System.out.println(this.info+"：i = "+i) ;
		}
	}
};
public class ThreadDemo03
{
	public static void main(String args[])
	{
		// 因为实现的是Runnable接口，所以需要通过Thread类启动多线程
		Demo d1 = new Demo("线程1") ;
		Demo d2 = new Demo("线程2") ;
		Demo d3 = new Demo("线程3") ;
		Thread t1 = new Thread(d1) ;
		Thread t2 = new Thread(d2) ;
		Thread t3 = new Thread(d3) ;
		// 启动多线程
		t1.start() ;
		t2.start() ;
		t3.start() ;
	}
};