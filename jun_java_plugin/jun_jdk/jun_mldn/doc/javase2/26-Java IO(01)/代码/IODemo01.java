import java.io.* ;

public class IODemo01
{
	public static void main(String args[])
	{
		File f = new File("f:\\mldn.txt") ;
		try
		{
			f.createNewFile() ;
		}
		catch (Exception e)
		{
			System.out.println(e) ;
		}
	}
};