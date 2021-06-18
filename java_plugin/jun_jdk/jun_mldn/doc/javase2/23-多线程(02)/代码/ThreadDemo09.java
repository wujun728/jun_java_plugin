// 实现Runnable接口
class Demo implements Runnable
{
	public void run()
	{
		System.out.println(Thread.currentThread().getName()+" --> 在运行。。。") ;
	}
};
public class ThreadDemo09
{
	public static void main(String args[])
	{
		Demo d = new Demo() ;
		Thread t1 = new Thread(d,"线程1") ;
		System.out.println("线程启动之前："+t1.isAlive()) ;
		t1.start() ;
		System.out.println("线程启动之后："+t1.isAlive()) ;
		// 加入一个for循环
		for(int i=0;i<10000000;i++)
		{
			// 加入一个延迟
			;
		}
		System.out.println("延迟线程之后："+t1.isAlive()) ;
	}
};