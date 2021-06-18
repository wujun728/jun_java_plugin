class Person{
	private String name ;
	private int age ;
	public Person(){
		System.out.println("******************") ;
	}
	public void setName(String name){
		this.name = name ;
	}
	public void setAge(int age){
		this.age = age ;
	}
	public String getName(){
		return this.name ;
	}
	public int getAge(){
		return this.age ;
	}
};
class Student extends Person {
	private String school ;	// 扩充的属性
	public Student(){
		super() ;	// 隐藏了此语句
		System.out.println("===================") ;
	}
	public void setSchool(String school){
		this.school = school ;
	}
	public String getSchool(){
		return this.school ;
	}
};
public class ExtDemo07{
	public static void main(String args[]){
		Student stu = new Student() ;	//  学生
	}
};