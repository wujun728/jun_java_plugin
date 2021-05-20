class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public String getName(){
		return this.name ;
	}
	public int getAge(){
		return this.age ;
	}
};

public class CompareDemo02{
	public static void main(String args[]){
		Person per1 = new Person("张三",30) ;
		Person per2 = new Person("张三",30) ;
		if(per1.getName().equals(per2.getName())&&per1.getAge()==per2.getAge()){
			System.out.println("是同一个人！") ;
		}
	}
};