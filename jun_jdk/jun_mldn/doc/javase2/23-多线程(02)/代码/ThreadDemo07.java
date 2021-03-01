// 实现Runnable接口
class Demo implements Runnable
{
	public void fun()
	{
		// 得到当前线程的名字
		System.out.println(Thread.currentThread().getName()+"在运行。。。") ;
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			this.fun() ;
		}
	}
};
public class ThreadDemo07
{
	public static void main(String args[])
	{
		Demo d = new Demo() ;
		Thread t1 = new Thread(d,"线程1") ;
		Thread t2 = new Thread(d,"线程2") ;
		Thread t3 = new Thread(d,"线程3") ;
		/*
		t1.setName("线程1") ;
		t2.setName("线程2") ;
		t3.setName("线程3") ;
		*/
		// 启动线程
		t1.start() ;
		t2.start() ;
		t3.start() ;
	}
};