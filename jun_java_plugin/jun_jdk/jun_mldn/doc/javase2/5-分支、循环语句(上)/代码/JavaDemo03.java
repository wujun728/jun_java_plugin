public class JavaDemo03
{
	public static void main(String args[])
	{
		float score = 95 ;
		if(score>=60)
		{
			if(score>=90&&score<=100)
			{
				System.out.println("等级为A等。") ;
			}
			else
			{
				if(score>=80&&score<90)
				{
					System.out.println("等级为B等。") ;
				}
				else
				{
					if(score>=70&&score<80)
					{
						System.out.println("等级为C等。") ;
					}
					else
					{
						System.out.println("等级为D等。") ;
					}
				}
			}
		}
		else
		{
			System.out.println("等级为E等。") ;
		}
	}
};