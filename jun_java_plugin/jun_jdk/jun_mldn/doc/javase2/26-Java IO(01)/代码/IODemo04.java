import java.io.* ;

public class IODemo04
{
	public static void main(String args[]) throws Exception
	{
		File f = new File("f:\\mldn.txt") ;
		if(f.exists())
		{
			f.delete() ;
		}
		else
		{
			f.createNewFile() ;
		}
	}
};