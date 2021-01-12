// 建立一个Person类，把Person类的对象序列化
import java.io.* ;

class Person implements Serializable
{
	private String name ;
	private transient int age ;
	public Person(String name,int age)
	{
		this.name = name ;
		this.age = age ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age ;
	}
};
public class IODemo26
{
	public static void main(String args[]) throws Exception
	{
		Person per = new Person("李兴华",30) ;
		ser(per) ;
		System.out.println(dser()) ;
	}
	// 建立一个方法用于完成对象的序列化
	public static void ser(Person per) throws Exception
	{
		ObjectOutputStream oos = null ;
		oos = new ObjectOutputStream(new FileOutputStream(new File("f:\\lxh.txt"))) ;
		oos.writeObject(per) ;
		oos.close() ;
	}
	// 反序列化
	public static Person dser() throws Exception
	{
		ObjectInputStream ois = null ;
		ois = new ObjectInputStream(new FileInputStream(new File("f:\\lxh.txt"))) ;
		Object obj = ois.readObject() ;
		ois.close() ;
		return (Person)obj ;
	}
};