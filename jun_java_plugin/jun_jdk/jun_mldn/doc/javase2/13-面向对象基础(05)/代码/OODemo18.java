// 测试运行时的参数
public class OODemo18
{
	public static void main(String args[])
	{
		// 使用此种方法，必须保证字符串是由数字组成的
		String num = "123a" ;
		int temp = Integer.parseInt(num) ;
		System.out.println(temp*2) ;
	}
};