class Demo{
	String x = "mldn" ;
};
public class RefDemo03{
	public static void main(String args[]){
		Demo d = new Demo() ;	
		d.x = "hello" ;
		fun(d) ;
		System.out.println(d.x) ;
	}
	public static void fun(Demo temp){
		temp.x = "world" ;
	}
};
