class Person
{
	// 两种情况：	李兴华	 作者
	//				MLDN		网站
	String name = "李兴华" ;
	String content = "作者" ;
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
				per.name = "MLDN" ;
				try
				{
					Thread.sleep(100) ;
				}
				catch (Exception e)
				{
				}
				per.content = "网站" ;
			}
			else
			{
				per.name = "李兴华" ;
				try
				{
					Thread.sleep(100) ;
				}
				catch (Exception e)
				{
				}
				per.content = "作者" ;
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
			try
			{
				Thread.sleep(100) ;
			}
			catch (Exception e)
			{
			}
			System.out.println(per.name+" --> "+per.content) ;
		}
	}
};

public class ThreadDemo17
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
