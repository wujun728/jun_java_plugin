package book.oo.initorder;

public class ClassInitOrderTest {

	public static void main(String[] args) {
		System.out.println("不new对象，访问静态方法时的输出：");
		Child.dispB();
		System.out.println();
		System.out.println("new对象，访问非静态方法时的输出：");
		new Child().display();
		//通知虚拟机进行垃圾回收
		System.gc();
	}

//保留main方法的前2行程序，将后面的注释，得到的输出结果是如下：
//	不new对象，访问静态方法时的输出：
//	Parent的静态初始化块
//	Parent的getNext(int base)被调用
//	Parent的getNext(int base)被调用
//	Child的静态初始化块
//	Child的dispB()被调用
	
//将main方法的前3行程序注释，保留后面的程序，得到的输出结果如下：
//	new对象，访问非静态方法时的输出：
//	Parent的静态初始化块
//	Parent的getNext(int base)被调用
//	Parent的getNext(int base)被调用
//	Child的静态初始化块
//	Parent的初始化块
//	Parent的getNext(int base)被调用
//	Parent的构造方法被调用
//	Child的初始化块
//	Child的构造方法被调用
//	Parent的display方法被调用
//	ix=50; iz=31
//	Parent的dispA()被调用
//	Child的销毁方法被调用
//	Parent的销毁方法被调用
	
//	总结：
//	1、虚拟机在首次加载Java类时，会对静态初始化块、静态成员变量、静态方法进行一次初始化
//	2、只有在调用new方法时才会创建类的实例
//	3、类实例创建过程：按照父子继承关系进行初始化，首先执行父类的初始化块部分，然后是父类的构造方法；再执行
//	   本类继承的子类的初始化块，最后是子类的构造方法
//	4、类实例销毁时候，首先销毁子类部分，再销毁父类部分

}
