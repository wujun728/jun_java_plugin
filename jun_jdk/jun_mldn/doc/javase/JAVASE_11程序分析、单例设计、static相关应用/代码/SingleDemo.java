class Single{
	private static Single instance = new Single() ;
	private Single(){	// 将构造方法私有化
	}
	public static Single getInstance(){
		return instance ;
	}
	public void print(){
		System.out.println("hello world!!!") ;
	}
};
public class SingleDemo{
	public static void main(String args[]){
		Single s = null ;		// 声明对象
		s = Single.getInstance() ;		// 实例化对象
		s.print() ;
	}
};