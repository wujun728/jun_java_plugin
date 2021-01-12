class Demo{
	{	// 构造块
		System.out.println("*********** 构造块 *************") ;
	}
	public Demo(){	// 构造方法
		System.out.println("*********** 构造方法 *************") ;
	}
};
public class CodeDemo02{
	public static void main(String args[]){
		new Demo() ;
		new Demo() ;
		new Demo() ;
	}
};