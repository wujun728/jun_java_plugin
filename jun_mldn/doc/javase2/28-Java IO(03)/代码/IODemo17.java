import java.io.* ;
public class IODemo17
{
	public static void main(String args[]) throws Exception
	{
		InputStream in = null ;
		// 数据等待键盘的输入
		in = System.in ;
		byte b[] = new byte[7] ;
		// 读的时候是等待用户的输入
		int len = in.read(b) ;
		in.close() ;
		System.out.println("输入的内容为："+new String(b,0,len)) ;
	}
};