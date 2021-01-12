class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public boolean compare(Person per){
		if(this==per){	// 地址相等
			return true ;
		}
		if(this.name.equals(per.name)&&this.age==per.age){
			return true ;
		}else{
			return false ;
		}
	}
	public String getName(){
		return this.name ;
	}
	public int getAge(){
		return this.age ;
	}
};

public class CompareDemo03{
	public static void main(String args[]){
		Person per1 = new Person("张三",30) ;
		Person per2 = new Person("张三",30) ;
		if(per1.compare(per1)){
			System.out.println("是同一个人！") ;
		}
	}
};