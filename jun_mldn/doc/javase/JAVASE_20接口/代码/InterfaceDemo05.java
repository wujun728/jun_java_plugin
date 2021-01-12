interface A{
	public void printA() ;
}
interface B{
	public void printB() ;
}
abstract class C implements A,B{
	public abstract void printC() ;
};
class X extends C{
	public void printA(){}
	public void printB(){}
	public void printC(){}
};
public class InterfaceDemo04{
	public static void main(String args[]){
		B b = new B() ;
		b.print() ;
		b.fun() ;
		b.funA() ;
	}
};