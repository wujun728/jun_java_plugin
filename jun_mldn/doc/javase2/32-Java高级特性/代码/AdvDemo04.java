import java.util.* ;
public class AdvDemo04
{
	public static void main(String args[])
	{
		int i[] = {1,23,25,1,32,53,0,9,7,6,4,4,6,6,7,8,454} ;
		// 要求对数组排序
		Arrays.sort(i) ;
		for(int x=0;x<i.length;x++)
		{
			System.out.print(i[x]+"\t") ;
		}
	}
};