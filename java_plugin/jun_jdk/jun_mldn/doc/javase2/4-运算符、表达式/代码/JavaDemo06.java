public class JavaDemo06
{
	public static void main(String args[])
	{
		// 学生上学的年龄在5~7岁之间
		// 如果成绩在90分，或者年龄在7岁，则表示是一个学生
		int age = 6 ;
		int score = 90 ;
		// 5<=age>=7 : age>=5 age<=7
		// boolean flag = age>=5&&age<=7 ;
		boolean flag = age==7 | score==90 ;
		System.out.println(flag) ;
	}
};
