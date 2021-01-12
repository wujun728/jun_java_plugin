class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		name = name ;
		age = age ;
	}
	public void setName(String n){
		name = n ;
	}
	public void setAge(int a){
		age = a ;
	}
	public String getName(){
		return name ;
	}
	public int getAge(){
		return age ;
	}
};
public class ThisDemo01{
	public static void main(String args[]){
		Person per = new Person("ÕÅÈı",30) ;
		System.out.println(per.getName() + " --> " + per.getAge()) ;
	}
};