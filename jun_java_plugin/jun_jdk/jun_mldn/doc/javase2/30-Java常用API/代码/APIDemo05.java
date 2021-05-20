import java.util.* ;
public class APIDemo05
{
	public static void main(String args[]) throws Exception
	{
		Random r = new Random() ;
		for(int i=0;i<10;i++)
		{
			System.out.println(r.nextInt(100)) ;
		}
	}
};