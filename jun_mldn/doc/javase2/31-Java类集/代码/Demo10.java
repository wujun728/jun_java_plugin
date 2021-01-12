import java.util.* ;
public class Demo10
{
	public static void main(String args[])
	{
		Stack s = new Stack() ;
		s.push("A") ;
		s.push("B") ;
		s.push("C") ;
		s.push("D") ;
		s.push("E") ;
		// System.out.println(s.empty()) ;
		// 取值
		while(!s.empty())
		{
			System.out.println(s.pop()) ;
		}
		// 如果内容已经全部弹出则再弹会出现错误
		s.pop() ;
	}
};