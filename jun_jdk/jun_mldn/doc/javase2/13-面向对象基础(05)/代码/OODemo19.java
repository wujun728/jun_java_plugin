/*
由运行参数输入Person类中的姓名和年龄，并打印输出
*/
class Person 
{
	String name ;
	int age ;
	String shout()
	{
		return "姓名："+name+"，年龄："+age ;
	}
};
public class OODemo19
{
	public static void main(String args[])
	{
		// 如果发现没有参数，则表示程序出错，退出程序
		if(args.length!=2)
		{
			// 没有两个参数
			// 程序退出
			System.out.println("参数输入有错误，可按以下格式输入：") ;
			System.out.println("java 类名称 名字 年龄") ;
			System.exit(1) ;
		}
		// 输入参数第一个是名字，第二个是年龄
		String name1 = args[0] ;
		int age1 = Integer.parseInt(args[1]) ;
		Person p = new Person() ;
		p.name = name1 ;
		p.age = age1 ;
		System.out.println(p.shout()) ;
	}
};