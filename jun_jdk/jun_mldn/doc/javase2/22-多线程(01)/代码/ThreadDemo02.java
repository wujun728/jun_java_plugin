// 编写一个类继承自Thread类
class Demo extends Thread
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
public class ThreadDemo02
{
	public static void main(String args[])
	{
		// 如果此时程序是一个多线程操作，则肯定会交替运行
		Demo d1 = new Demo("线程1") ;
		Demo d2 = new Demo("线程2") ;
		Demo d3 = new Demo("线程3") ;
		d1.start() ;
		d2.start() ;
		d3.start() ;
	}
};