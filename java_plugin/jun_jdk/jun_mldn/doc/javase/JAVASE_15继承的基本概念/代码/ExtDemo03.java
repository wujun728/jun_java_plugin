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
	private String school ;	// 扩充的属性
	public void setSchool(String school){
		this.school = school ;
	}
	public String getSchool(){
		return this.school ;
	}
};
public class ExtDemo03{
	public static void main(String args[]){
		Student stu = new Student() ;	//  学生
		stu.setName("张三") ;	// 从Person继承而来
		stu.setAge(30) ;		// 从Person继承而来
		stu.setSchool("清华大学") ;		// 自己定义的方法
		System.out.println("姓名：" + stu.getName()) ;
		System.out.println("年龄：" + stu.getAge()) ;
		System.out.println("学校：" + stu.getSchool()) ;
	}
};