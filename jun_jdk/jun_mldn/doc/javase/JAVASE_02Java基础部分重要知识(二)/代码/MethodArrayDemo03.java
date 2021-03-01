public class MethodArrayDemo03{
	public static void main(String args[]){
 		int temp[] = fun() ;	// 声明数组
		java.util.Arrays.sort(temp) ;	// 进行排序操作
		print(temp) ;
	}
	public static int[] fun(){
		int x[] = {23,1,5,3,24,3,56,4,3,1} ;
		return x ;	// 返回数组
	}
	public static void print(int[] x){
		for(int i=0;i<x.length;i++){
			System.out.print(x[i] + "、") ;
		}
	}
};