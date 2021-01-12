import org.lxh.* ;
import cn.mldn.* ;

public class OODemo04
{
	public static void main(String args[])
	{
		// 只要是重名类，就加入完整的包定义
		cn.mldn.DemoB db = new cn.mldn.DemoB() ;
		db.printA() ;
	}
};