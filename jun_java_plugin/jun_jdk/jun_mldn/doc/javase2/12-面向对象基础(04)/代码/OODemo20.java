class Person
{
	private String name ;
	private int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public void print()
	{
		System.out.println("姓名："+this.name) ;
		System.out.println("年龄："+this.age) ;
	}
};
public class OODemo20
{
	public static void main(String args[])
	{
		// 对象数组：类名称 对象数组名[] = null ;
		// 对象数组名 = new 类名称[] 
		Person p[] = null ;
		p = new Person[10] ;
		// 对象数组在使用时需要为每一个元素[对象]分别实例化
		p[0] = new Person("张三",30) ;
		p[1] = new Person("李四1",33) ;
		p[2] = new Person("李四2",33) ;
		p[3] = new Person("李四3",33) ;
		p[4] = new Person("李四4",33) ;
		p[5] = new Person("李四5",33) ;
		p[6] = new Person("李四6",33) ;
		p[7] = new Person("李四7",33) ;
		p[8] = new Person("李四8",33) ;
		p[9] = new Person("李四9",33) ;
		for(int i=0;i<p.length;i++)
		{
			p[i].print() ;
			System.out.println("-----------------------------") ;
		}
	}
};