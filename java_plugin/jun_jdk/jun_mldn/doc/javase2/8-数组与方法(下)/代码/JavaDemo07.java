public class JavaDemo07
{
	public static void main(String args[])
	{
		int k = add(10,30) ;
		// int float String
		System.out.println("两数相加之后为："+k) ;
	}
	// 两个整数相加
	// x和y是在add方法中起作用，所以这种变量称为局部变量
	public static int add(int x,int y)
	{
		int temp = x + y ;
		// 如果有返回值，则要加入return语句
		return temp ;
	}
};