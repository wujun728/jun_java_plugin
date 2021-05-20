import java.io.* ;

public class IODemo05
{
	public static void main(String args[])
	{
		File f = new File("F:\\testjava\\iodemo") ;
		// 列出此目录下的全部文件
		// 列出之前最好先判断给出的是不是一个目录
		if(f.isDirectory())
		{
			String str[] = f.list() ;
			for(int i=0;i<str.length;i++)
			{
				System.out.println(str[i]) ;
			}
		}
		else
		{
			System.out.println("不是目录。。") ;
		}
	}
};