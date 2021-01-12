import java.util.* ;
public class Demo09
{
	public static void main(String args[])
	{
		Vector v = new Vector() ;
		v.add("A") ;
		v.add("A") ;
		v.add("A") ;
		v.add("A") ;
		v.add("A") ;
		v.add("A") ;
		Enumeration e = v.elements() ;
		while(e.hasMoreElements())
		{
			System.out.println(e.nextElement()) ;
		}
	}
};