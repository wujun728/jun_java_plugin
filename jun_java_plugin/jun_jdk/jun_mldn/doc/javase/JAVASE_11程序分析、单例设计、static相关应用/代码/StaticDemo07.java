class Person{
	private String name ;
	private static int count ; 
	public Person(){
		count++ ;
		System.out.println("产生了" + count + "个实例化对象。") ;
	}
	public String getInfo(){
		return "姓名：" + this.name ;
	}
};
public class StaticDemo07{
	public static void main(String args[]){
		new Person() ;	
		new Person() ;	
		new Person() ;	
		new Person() ;	
		new Person() ;	
	}
};