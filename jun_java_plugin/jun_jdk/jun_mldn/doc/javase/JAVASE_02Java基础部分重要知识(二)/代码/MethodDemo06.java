public class MethodDemo06{
	public static void main(String args[]){
		fun(10) ;
		fun(3) ;
	}
	public static void fun(int x){
		System.out.println("进入方法。") ;
		if(x==3){
			return  ;	// 返回方法的被调用处
		}
		System.out.println("结束方法。") ;
	}
};