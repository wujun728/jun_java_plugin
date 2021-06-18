abstract class Person
{
	// 将说话的内容作为一个抽象方法，通过子类去实现
	public void say()
	{
		System.out.println(this.getContent()) ;
	}
	public abstract String getContent() ;
};
// 假设人分为两种 ：一种是工人，一种是学生
// 工人有工资，学生有成绩
// 不管是学生还是工人，肯定都可以说话
// 说话的内容不一样
class Worker extends Person
{
	public String getContent()
	{
		return "工人说话了。。。" ;
	}
};
class Student extends Person
{
	public String getContent()
	{
		return "学生说话了。。。" ;
	}
};
public class OODemo02
{
	public static void main(String args[])
	{
		Person p = null ;
		p = new Student() ;
		p.say() ;
	}
};