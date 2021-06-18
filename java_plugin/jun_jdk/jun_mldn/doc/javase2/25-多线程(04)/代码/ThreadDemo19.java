class Person
{
	// 两种情况：	李兴华	 作者
	//				MLDN		网站
	private String name = "李兴华" ;
	private String content = "作者" ;
	private boolean flag = false ;
	// 加入一个设置的同步方法
	public synchronized void set(String name,String content)
	{
		// 如果为绿灯则表示可以设置，否则等待：true
		// 值是假，则表示要等待不能去设置
		if(flag)
		{
			try
			{
				wait() ;
			}
			catch (Exception e)
			{
			}
		}
		this.name = name ;
		try
		{
			Thread.sleep(100) ;
		}
		catch (Exception e)
		{
		}
		this.content = content ;
		// 设置完成之后，要改变灯
		flag = true ;
		notifyAll() ;
	}
	public synchronized String get()
	{
		if(!flag)
		{
			try
			{
				wait() ;
			}
			catch (Exception e)
			{
			}
		}
		String str = this.name+" --> "+this.content ;
		// 如果为红灯，则表示可以取，否则等待：false
		flag = false ;
		notifyAll() ;
		return str ;
	}
};
class Pro implements Runnable
{
	private Person per = null ;
	public Pro(Person per)
	{
		this.per = per ;
	}
	public void run()
	{
		for(int i=0;i<100;i++)
		{
			if(i%2==0)
			{
				per.set("MLDN","网站") ;
			}
			else
			{
				per.set("李兴华","作者") ;
			}
		}
	}
};

class Cust implements Runnable
{
	private Person per = null ;
	public Cust(Person per)
	{
		this.per = per ;
	}
	public void run()
	{
		for(int i=0;i<100;i++)
		{
			System.out.println(per.get()) ;
		}
	}
} ;

public class ThreadDemo19
{
	public static void main(String args[])
	{
		Person per = new Person() ;
		Pro p = new Pro(per)  ;
		Cust c = new Cust(per) ;

		new Thread(p).start() ;
		new Thread(c).start() ;
	}
};
