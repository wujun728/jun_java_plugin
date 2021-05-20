import java.util.* ;
class Person implements Comparable
{
	private String name ;
	private int age ;
	private float score ;
	public Person(String name,int age,float score)
	{
		this.name = name ;
		this.age = age ;
		this.score = score ;
	}
	public String toString()
	{
		return "姓名："+this.name+"，年龄："+this.age+"，成绩："+this.score ;
	}
	// 覆写compareTo方法就是排序规则
	public int compareTo(Object obj)
	{
		Person p = (Person)obj ;
		if(p.score>this.score)
		{
			return 1 ;
		}
		else if(p.score<this.score)
		{
			return -1 ;
		}
		else
		{
			// 如果成绩相等则判断年龄
			if(p.age>this.age)
			{
				return 1 ;
			}
			else if(p.age<this.age)
			{
				return -1 ;
			}
			else
			{
				return 0 ;
			}
		}
	}
};
public class AdvDemo05
{
	public static void main(String args[])
	{
		Person p[] = new Person[5] ;
		p[0] = new Person("张三",20,96) ;
		p[1] = new Person("李四",19,96) ;
		p[2] = new Person("王五",19,97) ;
		p[3] = new Person("赵六",21,78) ;
		p[4] = new Person("孙七",20,80) ;

		Arrays.sort(p) ;
		for(int i=0;i<p.length;i++)
		{
			System.out.println(p[i]) ;
		}
	}
};