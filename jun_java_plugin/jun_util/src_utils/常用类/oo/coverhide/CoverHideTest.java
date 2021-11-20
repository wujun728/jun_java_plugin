package book.oo.coverhide;

public class CoverHideTest {

	public static void main(String[] args) {
		// 类变量是指变量属于类，通过类名就可以访问类变量
		// 实例变量是指变量属于类的实例即对象，通过对象可以访问实例变量，但是通过类名不可以访问实例变量
		// 同样静态方法属于一个类，通过类名就可以访问静态方法，而实例方法属于对象，通过对象访问实例方法

		// 所谓隐藏是指，子类能隐藏父类的变量和方法，通过子类对象，不能访问父类被隐藏了的变量和方法，
		// 但是将子类对象转换成父类对象时，可以访问父类中被隐藏的变量和方法
		// 而覆盖则指，子类能覆盖父类的方法，通过子类对象，不能访问父类被覆盖了的方法，
		// 将子类转换成父类对象时，也不能访问父类中被隐藏的方法。
		// 方法可以被隐藏和覆盖，但是变量只可能被隐藏，不会被覆盖

		// 先测试继承时变量的覆盖与隐藏问题
		Child child = new Child();
		System.out.println("child name: " + child.name + "; age: " + child.age
				+ "; kind: " + child.kind);// 此时得到的都是Child类的变量值
		// 将child类型转换成Parent对象，
		Parent parent = child;
		System.out.println("转换成Parent后name: " + parent.name + "; age: "
				+ parent.age + "; kind: " + parent.kind);// 此时得到的都是Parent类的变量值
		/**
		 * 结论：父类的实例变量和类变量能被子类的同名变量隐藏。将子类对象转换成父类对象后，可以访问父类的实例变量和类变量
		 * 在子类中若需要访问父类中被隐藏的实例变量，需要使用super关键字。
		 * 在子类中若需要访问父类中被隐藏的类变量，需要使用父类的名字加"."来访问
		 */
		System.out.println();
		System.out.println("子类访问父类被隐藏的实例变量 name：" + child.getParentName());
		System.out.println("子类访问父类被隐藏的静态变量 kind：" + Child.getParentKind());

		// 再测试继承时方法的覆盖与隐藏问题
		System.out.println();
		child.getName();// 实例方法
		child.getKind();// 静态方法
		parent.getName();// 实例方法
		parent.getKind();// 静态方法
		/**
		 * 结论：父类的静态方法被子类的同名静态方法隐藏；而父类的实例方法被子类的同名实例方法覆盖！
		 * 将子类对象转换成父类对象后，可以访问父类的静态方法，但是不能够访问父类的实例方法。
		 * 
		 * 试图用子类的静态方法隐藏父类中同样标识的实例方法是不合法的，编译器将会报错
		 * 试图用子类的实例方法覆盖父类中同样标识的静态方法也是不合法的，编译器会报错
		 * 静态方法和最终方法(带关键字final的方法)不能被覆盖
		 * 
		 */

	}

	// child name: Child; age: 25; kind: book.oo.coverhide.Child
	// 转换成Parent后name: Parent; age: 50; kind: book.oo.coverhide.Parent
	//
	// 子类访问父类中被隐藏的实例变量 name：Parent
	// 子类访问父类中被隐藏的静态变量 kind：book.oo.coverhide.Parent
	//
	// Child的getName()方法被调用了！
	// Child的getKind()方法被调用了！
	// Child的getName()方法被调用了！
	// Parent的getKind()方法被调用了！

}
