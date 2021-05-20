package org.lxh.demo ;
interface Fruit
{
	// 生长
	public void grow() ;
	// 采摘
	public void pick() ;
}
class Apple implements Fruit
{
	public void grow()
	{
		System.out.println("苹果在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘苹果。。。") ;
	}
};
class Orange implements Fruit
{
	public void grow()
	{
		System.out.println("橘子在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘橘子。。。") ;
	}
};
class Cherry implements Fruit
{
	public void grow()
	{
		System.out.println("樱桃在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘樱桃。。。") ;
	}
};

class Banana implements Fruit
{
	public void grow()
	{
		System.out.println("香蕉在生长。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘香蕉。。。") ;
	}
};

class Factory
{
	public static Fruit getFruitInstance(String type)
	{
		Fruit f = null ;
		// 通过Class类完成
		try
		{
			f = (Fruit)Class.forName("org.lxh.demo."+type).newInstance() ;
		}
		catch (Exception e)
		{
		}
		return f ;
	}
};
public class FactoryDemo04
{
	public static void main(String args[])
	{
		if(args.length==0)
		{
			System.out.println("必须输入一个要使用的名称") ;
			System.exit(1) ;
		}
		Fruit f = Factory.getFruitInstance(args[0]) ;
		if(f!=null)
		{
			f.grow() ;
			f.pick() ;
		}
		else
		{
			System.out.println("没有发现这个类型。") ;
		}
	}
};