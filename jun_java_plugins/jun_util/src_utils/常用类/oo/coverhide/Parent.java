package book.oo.coverhide;

public class Parent {
	/** 类变量，Parent的类别 */
	public static String kind = "book.oo.coverhide.Parent";
	/** 类变量，Parent的年龄 */
	public static int age = 50;
	/** 实例变量，Parent的名字 */
	public String name = "Parent";

	/** 静态方法，获取Parent的类别 */
	public static String getKind() {
		//静态方法中操作的只能是类变量
		System.out.println("Parent的getKind()方法被调用了！");
		return kind;
	}
	/** 静态方法，获取Parent的年龄 */
	public static int getAge() {
		System.out.println("Parent的getAge()方法被调用了！");
		return age;
	}
	/** 实例方法，获取Parent的名字 */
	public String getName() {
		//实例方法中操作的可以是实例变量，也可以是类变量
		System.out.println("Parent的getName()方法被调用了！");
		return this.name;
	}
	/** final方法，将Parent的年龄加1*/
	public final int nextAge(){
		return ++age;
	}
}
