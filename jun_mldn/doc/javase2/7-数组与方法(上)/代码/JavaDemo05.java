public class JavaDemo05
{
	public static void main(String args[])
	{
		int a[][] = {{1,3},{2,3,4,5},{2,3,4}} ;
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a[i].length;j++)
			{
				System.out.print(a[i][j]+"\t") ;
			}
			System.out.println("") ;
		}
	}
};