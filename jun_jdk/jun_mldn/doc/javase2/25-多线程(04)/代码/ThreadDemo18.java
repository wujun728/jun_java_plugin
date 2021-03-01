class Person
{
	// 两种情况：	李兴华	 作者
	//				MLDN		网站
	private String name = "李兴华" ;
	private String content = "作者" ;
	// 加入一个设置的同步方法
	public synchronized void set(String name,String content)
	{
		this.name = name ;
		try
		{
			Thread.sleep(100) ;
		}
		catch (Exception e)
		{
		}
		this.content = content ;
	}
	public synchronized String get()
	{
		return this.name+" --> "+this.content ;
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
};

public class ThreadDemo18
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
