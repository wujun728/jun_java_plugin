import java.io.* ;
public class IODemo08
{
	public static void main(String args[])
	{
		// 1、表示要操作lxh.txt文件
		File f = new File("f:\\lxh.txt") ;
		OutputStream out = null ;
		// 2、通过子类实例化
		// 使用FileOutputStream子类
		try
		{
			out = new FileOutputStream(f) ;
		}
		catch (Exception e)
		{
		}
		// 将字符串转化为byte数组
		String str = "HELLO MLDN ..." ;
		byte b[] = str.getBytes() ;
		// 3、将byte数组写入到文件之中，写的是byte数组中的内容
		try
		{
			out.write(b) ;
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