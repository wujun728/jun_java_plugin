import java.io.* ;
public class IODemo10
{
	public static void main(String args[])
	{
		// 1、表示要操作lxh.txt文件
		File f = new File("f:\\lxh.txt") ;
		Writer out = null ;
		// 2、通过子类实例化
		try
		{
			out = new FileWriter(f) ;
		}
		catch (Exception e)
		{
		}
		String str = "HELLO MLDN ..." ;
		// 3、将字符串写入到文件之中
		try
		{
			out.write(str) ;
			// 表示清空缓存
			out.flush() ;
		}
		catch (Exception e)
		{
		}
		// 4、关闭文件操作
		/*
		try
		{
			out.close() ;
		}
		catch (Exception e)
		{
		}
		*/
	}
};