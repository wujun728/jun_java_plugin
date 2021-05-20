import java.util.* ;
public class Demo06
{
	public static void main(String args[])
	{
		Map<java.lang.String,java.lang.String> m = new HashMap<java.lang.String,java.lang.String>() ;
		m.put("张三","123456") ;
		m.put("李四","654321") ;
		m.put("王五","890762") ;

		// 已经准备好了一个电话本，在内存中保留下来
		// 要查找张三的电话
		System.out.println(m.get("赵六")) ;
	}
};