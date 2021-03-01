class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public void fun(Person temp){
		temp.name = "李四" ;
		temp.age = 33 ;
	}
	public String getName(){
		return this.name ;
	}
	public int getAge(){
		return this.age ;
	}
};

public class CompareDemo01{
	public static void main(String args[]){
		Person per = new Person("张三",30) ;
		per.fun(per) ;
		System.out.println(per.getName() + " --> " + per.getAge()) ;
	}
};