package book.oo.coverhide;

public class Child extends Parent {

	/** 类变量，Child的类别 */
	public static String kind = "book.oo.coverhide.Child";
	/** 实例变量，Child的年龄*/
	public int age = 25;// age变量在Parent类中是static的。
	/** 实例变量，Child的名字 */
	public String name = "Child";
	/** 静态方法，获取Child的类别 */
	public static String getKind() {
		System.out.println("Child的getKind()方法被调用了！");
		return kind;
	}
	/** 静态方法，获取父类的名字*/
	public static String getParentKind(){
		//通过类名加"."访问父类中被隐藏的类变量
		return Parent.kind;
	}
	/** 实例方法，获取Child的名字 */
	public String getName() {
		System.out.println("Child的getName()方法被调用了！");
		return this.name;
	}
	/** 实例方法，获取父类的名字*/
	public String getParentName(){
		//通过super关键字父类中被隐藏的实例变量
		return super.name;
	}
//	错误！实例方法不能够覆盖父类的静态方法
//	public int getAge(){
//		return this.age;
//	}
//	错误！父类的final方法不能够被覆盖
//	public int nextAge(){
//		return ++age;
//	}
}
