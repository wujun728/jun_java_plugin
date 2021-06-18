public class JavaDemo02
{
	public static void main(String args[])
	{
		// 声明了一个数组
		int i[] = null ;
		// 使用数组？
		i = new int[10] ;
		// i[0]  ~ i[9]
		for (int x=0;x<10;x++)
		{
			System.out.println("i["+x+"] = "+i[x]) ;
		}
	}
};