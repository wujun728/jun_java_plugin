class Outer{
	private String name = "HELLO WORLD!!!" ;
	class Inner{
		public void print(){
			System.out.println("name = " + name) ;
		}
	};
};
public class InnerClassDemo03{
	public static void main(String args[]){
		Outer out = new Outer() ;	// 外部类实例
		Outer.Inner in = out.new Inner() ;	// 实例化内部类对象
		in.print() ;
	}
};