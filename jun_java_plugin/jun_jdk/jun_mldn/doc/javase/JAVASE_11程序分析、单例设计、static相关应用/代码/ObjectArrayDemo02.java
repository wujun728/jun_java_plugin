class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public String getInfo(){
		return "姓名：" + this.name + "，年龄：" + this.age ;
	}
};

public class ObjectArrayDemo02{
	public static void main(String args[]){
		Person p[] = {new Person("张三",30),new Person("李四",31),new Person("王五",32)} ;
		for(int x=0;x<p.length;x++){
			System.out.println(p[x].getInfo()) ;
		}
	}
};