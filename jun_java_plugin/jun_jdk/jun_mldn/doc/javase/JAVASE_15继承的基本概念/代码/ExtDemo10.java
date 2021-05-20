class Person{
	private String name ;
	private int age ;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
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
	public String getInfo(){
		return "姓名：" + this.name + "，年龄：" + this.age ;
	}
};
class Student extends Person {
	private String school ;	// 扩充的属性
	public Student(String name,int age,String school){
		this(name,age) ;	// 明确调用父类中有两个参数的构造方法
		this.school = school ;
	}
	public Student(String name,int age){
		super(name,age) ;
	}
	public String getInfo(){
		return super.getInfo() + "；学校：" + this.school ;
	}
	public void setSchool(String school){
		this.school = school ;
	}
	public String getSchool(){
		return this.school ;
	}
};
public class ExtDemo10{
	public static void main(String args[]){
		Student stu = new Student("张三",30,"清华大学") ;	//  学生
		System.out.println(stu.getInfo()) ;
	}
};