public class JavaDemo08
{
	public static void main(String args[])
	{
		add(10.0f) ;
	}
	public static void add()
	{
		System.out.println("** 1、无参的add方法.") ;
	}
	public static void add(int i)
	{
		System.out.println("** 2、有一个参数的add方法。") ;
	}
	public static void add(int i,int j)
	{
		System.out.println("** 3、有两个参数的add方法。") ;
	}
	public static void add(float i)
	{
		System.out.println("** 4、float参数的add方法。") ;
	}
};