interface National{}
class Person extends Object implements National{
};
public class ObjectDemo03{
	public static void main(String arg[]){
		National na = new Person() ;	// 子类为接口实例化
		Object obj = na ;	// 使用Object接收接口实例
		National temp = (National)obj ;
	}
};