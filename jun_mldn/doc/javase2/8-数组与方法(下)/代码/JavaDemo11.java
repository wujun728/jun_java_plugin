public class JavaDemo11
{
	public static void main(String args[])
	{
		int source[] = {1,2,3,4,5,6} ;
		int dest[] = {11,22,33,44,55,66,77,88,99} ;
		System.out.println("------- dest数组拷贝之前 ---------") ;
		printArray(dest) ;
		// 进行数组的拷贝
		System.arraycopy(source,0,dest,0,source.length) ;
		
		System.out.println("------- dest数组拷贝之后 ---------") ;
		printArray(dest) ;
	}
	public static void printArray(int x[])
	{
		for (int i=0;i<x.length;i++)
		{
			System.out.println("数组["+i+"] = "+x[i]) ;
		}
	}
};