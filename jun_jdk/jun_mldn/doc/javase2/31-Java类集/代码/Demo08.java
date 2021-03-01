import java.util.* ;
public class Demo08
{
	public static void main(String args[])
	{
		Map<java.lang.String,java.lang.String> m = new TreeMap<java.lang.String,java.lang.String>() ;
		m.put("CC","123456") ;
		m.put("DD","654321") ;
		m.put("AA","890762") ;

		Set all = m.entrySet() ;
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Map.Entry me = (Map.Entry)iter.next() ;
			System.out.println(me.getKey()+" --> "+me.getValue()) ;
		}
	}
};