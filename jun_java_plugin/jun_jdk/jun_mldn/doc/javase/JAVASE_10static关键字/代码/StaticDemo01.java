class Person{
	private String name ;
	private int age;
	String city = "A城" ;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public String getInfo(){
		return "姓名：" + this.name + "，年龄：" + this.age + "，城市：" + city ;
	}
};
public class StaticDemo01{
	public static void main(String args[]){
		Person per1 = new Person("张三",30) ;
		Person per2 = new Person("李四",31) ;
		Person per3 = new Person("王五",30) ;
		System.out.println(per1.getInfo()) ;
		System.out.println(per2.getInfo()) ;
		System.out.println(per3.getInfo()) ;
		per1.city = "B城" ;
		System.out.println(per1.getInfo()) ;
		System.out.println(per2.getInfo()) ;
		System.out.println(per3.getInfo()) ;
	}
};