public class OODemo08
{
	public static void main(String args[])
	{
		// String类中有一个构造方法 --> String (String)
		// String name1 = new String("李兴华") ;
		String name2 = "李兴华" ;
		String name3 = "李兴华" ;
		// String name3 = name1 ;
		// System.out.println("name1 = "+name1) ;
		// System.out.println("name2 = "+name2) ;
		// System.out.println(name1==name2) ;
		// String类的一个方法：public boolean equals(String str) 
		// System.out.println(name1.equals(name2)) ;
		// System.out.println(name3==name1) ;
		// System.out.println("李兴华".equals(name1)) ;
		System.out.println(name2==name3) ;
	}
};