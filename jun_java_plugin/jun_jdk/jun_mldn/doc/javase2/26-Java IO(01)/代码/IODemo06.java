import java.io.* ;

public class IODemo06
{
	public static void main(String args[])
	{
		loop("d:\\") ;
	}
	public static void loop(String dir)
	{
		File f = new File(dir) ;
		String str[] = null ;
		if(f.isDirectory())
		{
			str = f.list() ;
			for(int i=0;i<str.length;i++)
			{
				loop(dir+"\\"+str[i]) ;
			}
		}
		else
		{
			System.out.println(dir) ;
		}
		
	}
};