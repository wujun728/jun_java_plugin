class Person{
	private String name ;
	private int age ;
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
};
public class ExtDemo02{
	public static void main(String args[]){
		Student stu = new Student() ;	//  学生
		stu.setName("张三") ;	// 从Person继承而来
		stu.setAge(30) ;		// 从Person继承而来
		System.out.println("姓名：" + stu.getName()) ;
		System.out.println("年龄：" + stu.getAge()) ;
	}
};