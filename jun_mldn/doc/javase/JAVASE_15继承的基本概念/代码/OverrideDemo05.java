class A{
	public String name = "HELLO" ;
};
class B extends A {
	public int name = 1 ;	// 此时属性覆盖了
	public void print(){
		System.out.println("name = " + name) ;
	}
};
public class OverrideDemo05{
	public static void main(String args[]){
		B b = new B() ;
		b.print() ;
	}
};