// 回避一种写法
// 如果程序中非要使用此种类型的代码，则可以使用StringBuffer代替
// StringBuffer与String的本质区别，在于StringBuffer可以改变
public class OODemo12
{
	public static void main(String args[])
	{
		String str = "A" ;
		for(int i=0;i<100;i++)
		{
			str += i ;
		}
		System.out.println(str) ;
	}
};