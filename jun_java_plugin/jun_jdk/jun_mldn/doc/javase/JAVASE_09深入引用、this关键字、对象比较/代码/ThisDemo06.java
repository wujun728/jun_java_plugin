class Person{
	public void print(){
		System.out.println(this) ;
	}
};
public class ThisDemo06{
	public static void main(String args[]){
		Person per1 = new Person() ;
		Person per2 = new Person() ;
		System.out.println(per1) ;
		per1.print() ;
		System.out.println(per2) ;
		per2.print() ;
	}
};