class Person{
	private String name	;	// 表示人的姓名
	private int age ;		// 表示人的年龄
	public Person(){}
	public Person(String n){
		this.setName(n) ;
	}
	public Person(String n,int a){		// 定义了一个构造方法
		this.setName(n) ;	// 调用setName()方法
		this.setAge(a) ;		// 调用setAge()方法
		System.out.println("******************") ;
	}
	public void tell(){	// 定义说话的方法
		System.out.println("姓名：" + this.getName() + "，年龄：" + this.getAge()) ;
	}
	public void setName(String n){
		name = n ;
	}
	public void setAge(int a){
		if(a>=0&&a<=200){
			age = a ;
		}
	}
	public String getName(){
		return name ;
	}
	public int getAge(){
		return age ;
	}
};
public class OODemo13{
	public static void main(String args[]){
		new Person("张三",-30).tell() ;
	}
};