package org.lxh.demo ;
import java.io.* ;
import java.util.* ;
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

class WaterMelon implements Fruit
{
	public void grow()
	{
		System.out.println("西瓜在生长。。。") ;
	}
	public void pick()
	{
		System.out.println("摘西瓜。。。") ;
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

class DD implements Fruit
{
	public void grow()
	{
		System.out.println("DD。。。 ") ;
	}
	public void pick()
	{
		System.out.println("摘DD。。。") ;
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
			f = (Fruit)Class.forName(type).newInstance() ;
		}
		catch (Exception e)
		{
			System.out.println(e) ;
		}
		return f ;
	}
};

// 定义一个新类，此类可以从键盘输入数据
class InputData
{
	BufferedReader buf = null ;
	public InputData()
	{
		buf = new BufferedReader(new InputStreamReader(System.in)) ;
	}
	public String getString()
	{
		String str = null ;
		try
		{
			str = buf.readLine() ;
		}
		catch (Exception e)
		{
		}
		return str ;
	}
};

class Init
{
	public static Properties getPropertiesInit()
	{
		Properties p = new Properties() ;
		try
		{
			p.loadFromXML(new FileInputStream("lxh.xml")) ;
		}
		catch (Exception e)
		{
		}
		return p ;
	}
};

public class FactoryDemo06
{
	public static void main(String args[])
	{
		Properties p = new Properties() ;
		p = Init.getPropertiesInit() ;
		System.out.println(p) ;
		
		// 加入一个可以从键盘输入数据的类
		System.out.print("输入要使用的子类代码：") ;
		String code = new InputData().getString() ;
		System.out.println(p.getProperty(code)) ;
		Fruit f = Factory.getFruitInstance(p.getProperty(code)) ;
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