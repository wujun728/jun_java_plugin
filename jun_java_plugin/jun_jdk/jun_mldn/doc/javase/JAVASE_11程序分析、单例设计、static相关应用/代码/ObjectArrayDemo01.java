class Person{
	private String name ;
	private int age;
	public Person(String name,int age){
		this.name = name ;
		this.age = age ;
	}
	public String getInfo(){
		return "姓名：" + this.name + "，年龄：" + this.age ;
	}
};

public class ObjectArrayDemo01{
	public static void main(String args[]){
		Person p[] = new Person[3] ;	// 3个长度的对象数组
		for(int x=0;x<p.length;x++){
			System.out.print(p[x] + "、") ;
		}
		System.out.println() ;
		p[0] = new Person("张三",30) ;
		p[1] = new Person("李四",31) ;
		p[2] = new Person("王五",32) ;
		for(int x=0;x<p.length;x++){
			System.out.println(p[x].getInfo()) ;
		}
	}
};