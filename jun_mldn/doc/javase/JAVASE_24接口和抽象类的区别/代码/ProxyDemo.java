interface Give{
	public void giveMoney() ;
}
class RealGive implements Give{
	public void giveMoney(){
		System.out.println("把钱还给我。。。。。") ;
	}
};
class ProxyGive implements Give{	// 代理公司
	private Give give = null ;
	public ProxyGive(Give give){
		this.give = give ;
	}
	public void before(){
		System.out.println("准备：小刀、绳索、钢筋、钢据、手枪、毒品") ;
	}
	public void giveMoney(){
		this.before() ;
		this.give.giveMoney() ;	// 代表真正的讨债者完成讨债的操作
		this.after() ;
	}
	public void after(){
		System.out.println("销毁所有罪证") ;
	}
};
public class ProxyDemo{
	public static void main(String args[]){
		Give give = new ProxyGive(new RealGive()) ;
		give.giveMoney() ;
	}
};