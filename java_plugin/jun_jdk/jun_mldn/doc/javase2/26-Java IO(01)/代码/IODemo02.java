import java.io.* ;

public class IODemo02
{
	public static void main(String args[])
	{
		File f = new File("f:\\mldn.txt") ;
		if(f.exists())
		{
			System.out.println("文件已存在。") ;
		}
		else
		{
			System.out.println("文件不存在") ;
		}
	}
};