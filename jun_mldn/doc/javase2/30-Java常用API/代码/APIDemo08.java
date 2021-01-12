import java.util.* ;
import java.text.* ;
public class APIDemo08
{
	public static void main(String args[]) throws Exception
	{
		/*
			2007-5-17 16:19:20
			年-月-日 时:分:秒

		*/
		String str = "2007-5-17 16:19:20" ;
		// 1、准备原格式
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

		// 2、准备新格式
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒") ;

		// 3、按sdf1模板摘出时间数
		/*
		public Date parse(String source)
           throws ParseException
		*/
		Date d = sdf1.parse(str) ;
		// 4、将时间数插入到新模板之中
		// public final String format(Date date)
		String newStr = sdf2.format(d) ;
		System.out.println(newStr) ;
	}
};