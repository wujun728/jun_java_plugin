import java.io.* ;
public class IODemo19
{
	public static void main(String args[])
	{
		BufferedReader buf = null ;
		// 此处只是准备了要从键盘中读取数据
		buf = new BufferedReader(new InputStreamReader(System.in)) ;
		String str = null ;
		try
		{
			System.out.print("请输入内容：") ;
			str = buf.readLine() ;
		}
		catch (Exception e)
		{
		}
		System.out.println("输入的内容为："+str) ;
	}
};