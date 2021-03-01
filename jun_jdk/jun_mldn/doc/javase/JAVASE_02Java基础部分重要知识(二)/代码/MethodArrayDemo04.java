public class MethodArrayDemo04{
	public static void main(String args[]){
 		int t1[] = {1,2,3,4,5,6,7,8,9} ;
		int t2[] = {11,22,33,44,55,66,77,88,99} ;
		// 源数组名称 下标 目标数组 下标 拷贝长度
		System.arraycopy(t2,0,t1,3,3) ;	// 数组拷贝
		print(t1) ;
	}
	public static void print(int[] x){
		for(int i=0;i<x.length;i++){
			System.out.print(x[i] + "、") ;
		}
	}
};