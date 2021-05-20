import java.util.* ;
public class Demo07
{
	public static void main(String args[])
	{
		Map<java.lang.String,java.lang.String> m = new HashMap<java.lang.String,java.lang.String>() ;
		m.put("张三","123456") ;
		m.put("李四","654321") ;
		m.put("王五","890762") ;

		Set all = m.entrySet() ;
		Iterator iter = all.iterator() ;
		while(iter.hasNext())
		{
			Map.Entry me = (Map.Entry)iter.next() ;
			System.out.println(me.getKey()+" --> "+me.getValue()) ;
		}
	}
};