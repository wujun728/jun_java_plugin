class Outer{
	private String name = "HELLO WORLD!!!" ;
	public void fun(){
		new Inner(this).print() ;	// ¥Ú”°–≈œ¢
	};
	public String getName(){
		return this.name ;
	}
};
class Inner{
	private Outer out ;
	public Inner(Outer out){
		this.out = out ;
	}
	public void print(){
		System.out.println("name = " + this.out.getName()) ;
	}
};
public class InnerClassDemo02{
	public static void main(String args[]){
		new Outer().fun() ;
	}
};