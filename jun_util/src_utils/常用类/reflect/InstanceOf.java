package book.reflect;

/**
 * instance of 用于检测对象的类型。
 * （1）类的对象与类作instance of操作，结果为true
 * （2）子类对象与父类作instance of操作，结果为true。
 *  因此，所有对象与Object作instance of操作，结果都为true。
 * （3）其他情况下，结果都为false。
 */
public class InstanceOf {
	
	// 父类
	static class ClassA {
	}
	// 子类
	static class ClassB extends ClassA{
	}

	public static void main(String[] args) {
		ClassA a = new ClassA();
		ClassB b = new ClassB();
		// 检测对象a，b是否为ClassA类型
		if (a instanceof ClassA){
			System.out.println("Object a is a ClassA Object!");
		} else {
			System.out.println("Object a is not a ClassA Object!");
		}
		if (b instanceof ClassA){
			System.out.println("Object b is a ClassA Object!");
		} else {
			System.out.println("Object b is not a ClassA Object!");
		}
		
		// 检测对象a，b是否为ClassB类型
		if (a instanceof ClassB){
			System.out.println("Object a is a ClassB Object!");
		} else {
			System.out.println("Object a is not a ClassB Object!");
		}
		if (b instanceof ClassB){
			System.out.println("Object b is a ClassB Object!");
		} else {
			System.out.println("Object b is not a ClassB Object!");
		}
	}
}
