// Object类是所有类的父类，如果一个类没有明确声明继承自那个类
// 则肯定会继承Object类
class Person// extends Object
{
	public String toString()
	{
		return "Hello MLDN" ;
	}
};
public class OODemo01
{
	public static void main(String args[])
	{
		Person p = new Person() ;
		// 默认情况下打印对象，实际上就相当于调用对象中的toString方法
		System.out.println(p) ;
	}
};