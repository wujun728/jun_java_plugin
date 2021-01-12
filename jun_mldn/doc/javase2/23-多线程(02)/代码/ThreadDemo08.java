// 实现Runnable接口
class Demo implements Runnable
{
	public void fun()
	{
		// 得到当前线程的名字
		System.out.println(Thread.currentThread().getName()+" --> 在运行。。。") ;
	}
	public void run()
	{
		for(int i=0;i<10;i++)
		{
			this.fun() ;
		}
	}
};
public class ThreadDemo08
{
	public static void main(String args[])
	{
		Demo d = new Demo() ;
		Thread t1 = new Thread(d,"线程1") ;
		t1.start() ;
		for(int i=0;i<10;i++)
		{
			d.fun() ;
		}
	}
};