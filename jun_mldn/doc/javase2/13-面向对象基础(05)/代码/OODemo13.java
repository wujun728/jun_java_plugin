// 为了讲解static，程序中的属性暂不封装
class Person
{
	private static String code ;
	String name ;
	int age ;
	String shout() 
	{
		return "地区编码："+code+"，姓名："+name+"，年龄："+age ;
	}
	// 如果需要的话，对于code的内容设置应该通过一个方法进行检查
	// 方法怎么写？
	// 静态方法，由类名称直接调用
	static void setCode(String c)
	{
		// 不要出现this
		code = c ;
	}
	// 能不能通过静态方法为类中的属性赋值
	static void setName(String n)
	{
		name = n ;
	}
};

class OODemo13
{
	public static void main(String args[])
	{
		Person.setCode("110") ;


		Person p1 = new Person() ;
		Person p2 = new Person() ;
		//p1.code = "110" ;
		p1.name = "魔乐" ;
		p1.age = 3 ;
		//p2.code = "120" ;
		p2.name = "李" ;
		p2.age = 30 ;

		Person p3 = new Person() ;
		//p3.code = "130" ;

		// 应该由对象所属的类进行统一的修改

		System.out.println(p1.shout()) ;
		System.out.println(p2.shout()) ;
	}
};