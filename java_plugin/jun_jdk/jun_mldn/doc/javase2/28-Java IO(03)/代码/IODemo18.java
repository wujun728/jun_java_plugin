import java.io.* ;
public class IODemo18
{
	public static void main(String args[]) throws Exception
	{
		InputStream in = null ;
		// 数据等待键盘的输入
		in = System.in ;
		String str = "" ;
		int c = 0 ;
		while((c=in.read())!=-1)
		{
			str += (char)c;
		}
		in.close() ;
		System.out.println("输入的内容为："+str) ;
	}
};