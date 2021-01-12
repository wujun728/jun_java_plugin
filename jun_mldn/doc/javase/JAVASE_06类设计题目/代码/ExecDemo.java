class Student{
	private String name ;
	private int age ;
	private float english ;
	private float computer ;
	private float math ;
	public Student(){}
	public Student(String n,int a,float e,float c,float m){
		this.setName(n) ;
		this.setAge(a) ;
		this.setEnglish(e) ;
		this.setComputer(c) ;
		this.setMath(m) ;
	}
	public float sum(){
		return english + computer + math ;
	}
	public float avg(){
		return this.sum() / 3 ;
	}
	public float max(){
		float max = computer>math?computer:math ;
		max = max>english?max:english ;
		return max ;
	}
	public float min(){
		float min = computer<math?computer:math ;
		min = min<english?min:english ;
		return min ;
	}
	public String getInfo(){
		return	"学生信息： \n" +
				"\t|- 姓名：" + this.getName() + "\n" +
				"\t|- 年龄：" + this.getAge() + "\n" +
				"\t|- 数学成绩：" + this.getMath() + "\n" +
				"\t|- 英语成绩：" + this.getEnglish() + "\n" +
				"\t|- 计算机成绩：" + this.getComputer() ;
	}
	public void setName(String n){
		name = n ;
	}
	public void setAge(int a){
		age = a ;
	}
	public void setEnglish(float e){
		english = e ;
	}
	public void setComputer(float c){
		computer = c ;
	}
	public void setMath(float m){
		math = m ;
	}
	public String getName(){
		return name ;
	}
	public int getAge(){
		return age ;
	}
	public float getEnglish(){
		return english ;
	}
	public float getComputer(){
		return computer ;
	}
	public float getMath(){
		return math ;
	}
}
public class ExecDemo{
	public static void main(String args[]){
		Student stu = new Student("张三",30,89.0f,91.0f,87.0f) ;
		System.out.println("总分：" + stu.sum()) ;
		System.out.println("平均分：" + stu.avg()) ;
		System.out.println("最高分：" + stu.max()) ;
		System.out.println("最低分：" + stu.min()) ;
		System.out.println(stu.getInfo()) ;
	}
};