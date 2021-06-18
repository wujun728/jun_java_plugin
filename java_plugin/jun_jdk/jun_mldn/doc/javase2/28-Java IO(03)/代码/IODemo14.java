import java.io.* ;
public class IODemo14
{
	public static void main(String args[]) throws Exception 
	{
		// 通过子类完成不同的功能
		OutputStream out = null ;
		// System.out是PrintStream，是OutputStream子类
		out = System.out ;
		// 现在的out对象具备了向屏幕上打印内容的能力
		String str = "HELLO MLDN --> LXH" ;
		out.write(str.getBytes()) ;
		out.close() ;
	}
};