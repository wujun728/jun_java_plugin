class Outer{
	private String name = "HELLO WORLD!!!" ;
	public void fun(final int temp){
		class Inner{
			public void print(){
				System.out.println("temp = " + temp) ;
				System.out.println("name = " + name) ;
			}
		}
		new Inner().print() ;
	}
};
public class InnerClassDemo06{
	public static void main(String args[]){
		new Outer().fun(30) ;
	}
};