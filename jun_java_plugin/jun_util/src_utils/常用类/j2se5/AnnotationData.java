package book.j2se5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用注释Annotation.
 * Java 编程的一个最新趋势就是使用元数据，元数据可以用于创建文档，
 * 跟踪代码中的依赖性，甚至执行基本编译时检查。
 * 许多元数据工具（如Xdoclet）将这些功能添加到核心Java语言中，暂时成为Java编程功能的一部分。
 * Annotation是可以添加到代码中的修饰符，
 * 可以用于包声明、类型声明、构造函数、方法、域变量、参数和变量
 */
public class AnnotationData {
	@Deprecated private String name;
	public AnnotationData(String name){
		this.name = name;
	}

	// 方法声明中使用了内置的@Override元数据，表示该方法覆盖了父类的同名同参数方法
	// 如果父类不存在该方法，则编译不会通过。
	@Override public String toString() {
        return super.toString() + this.name;
	}
	@Override public int hashCode() {
        return toString().hashCode();
	}
	/**
	 * 方法中使用了内置的@Deprecated元数据，表示该方法已经不被推荐使用了。
	 * @return
	 */
	@Deprecated public String getName(){
		return name;
	}
	public String getAnnotationDataName(){
		return this.name;
	}
	
	// 下面自定义元数据类型
	// 使用@interface声明Annotation类型
	public @interface MyAnnotation {
		// 在元数据类中可以定义其他类
		public enum Severity {
			CRITICAL, IMPORTANT, TRIVIAL, DOCUMENTATION
		};
		// 定义数据成员不需要定义getter和setter方法，
		// 只需要定义一个以成员名称命名的方法，并指定返回类型为需要的数据类型
		// default关键字为Annotation类型的成员设置缺省值
		Severity severity() default Severity.IMPORTANT;
		String item();
	    String assignedTo();
	    String dateAssigned();
	}
	// 使用自定义Annotation类型，在使用时，
	// 如果Annotation类型在其他的包下，需要跟使用类一样，import它
	@MyAnnotation(
			severity = MyAnnotation.Severity.CRITICAL, 
			item = "Must finish this method carefully", 
			assignedTo = "Programmer A", 
			dateAssigned = "2006/09/10")
	public void doFunction() {
		// do something
	}
	// 下面再定义一个Annotation类型，使用了元数据的元数据
	//  @Target指定Annotation类型可以应用的程序元素，
	//  程序元素的类型由java.lang.annotation.ElementType枚举类定义
	
	//  @Retention和 Java 编译器处理Annotation类型的方式有关，
	//  这些方式由java.lang.annotation.RetentionPolicy 枚举类定义
	//  @Documented指明需要在Javadoc中包含Annotation（缺省是不包含的）
	
	//@Retention(RetentionPolicy.RUNTIME)这个meta-annotation
	// 表示了此类型的annotation将编译成class文件，而且还能被虚拟机读取。
	//而@Target(ElementType.METHOD)表示此类型的annotation只能用于修饰方法声明
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyNewAnnotation{
	}
}
