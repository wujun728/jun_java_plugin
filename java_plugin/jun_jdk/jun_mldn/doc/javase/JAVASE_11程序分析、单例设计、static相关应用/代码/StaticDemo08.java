class Person{
	private String name ;
	private static int count ; 
	public Person(){
		count++ ;
		this.name = "NONAME - " + count ;	// 自动命名
	}
	public Person(String name){
		this.name = name ;
	}
	public String getInfo(){
		return "姓名：" + this.name ;
	}
};
public class StaticDemo08{
	public static void main(String args[]){
		System.out.println(new Person().getInfo()) ;
		System.out.println(new Person("A").getInfo()) ;
		System.out.println(new Person("B").getInfo()) ;
		System.out.println(new Person().getInfo()) ;
	}
};