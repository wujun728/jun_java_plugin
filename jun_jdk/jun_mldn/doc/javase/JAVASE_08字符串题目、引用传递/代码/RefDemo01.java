class Demo{
	int x = 10 ;
};
public class RefDemo01{
	public static void main(String args[]){
		Demo d = new Demo() ;	
		d.x = 30 ;
		fun(d) ;
		System.out.println(d.x) ;
	}
	public static void fun(Demo temp){
		temp.x = 100 ;
	}
};
