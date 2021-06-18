class Outer{
	private String name = "HELLO WORLD!!!" ;
	public void fun(){
		class Inner{
			public void print(){
				System.out.println("name = " + name) ;
			}
		}
		new Inner().print() ;
	}
};
public class InnerClassDemo05{
	public static void main(String args[]){
		new Outer().fun() ;
	}
};