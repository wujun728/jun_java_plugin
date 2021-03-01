public class MethodDemo08{
	public static void main(String args[]){
		int sum = 0 ;
		sum = fun(100) ;
		System.out.println("¼ÆËã½á¹û£º" + sum) ;
	}
	public static int fun(int temp){
		if(temp==1){
			return 1 ;
		}else{
			return temp + fun(temp-1) ;
		}
	}
};