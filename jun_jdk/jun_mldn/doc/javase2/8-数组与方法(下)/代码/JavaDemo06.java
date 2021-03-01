public class JavaDemo06
{
	public static void main(String args[])
	{
		printTab() ;
	}
	// 定义一个方法，此方法用于打印Hello World
	// public static必须加上，因为是由主方法直接去调用
	// void：表示此方法不需要任何返回值
	// print：表示方法的名称，可以任意编写
	// 方法名称编写时：第一个单词首字母小写，之后每个单词的首字母大写
	public static void printTab()
	{
		System.out.println("************************") ;
		System.out.println("*      Hello World     *") ;
		System.out.println("*      www.mldn.cn     *") ;
		System.out.println("************************") ;	
	}
};