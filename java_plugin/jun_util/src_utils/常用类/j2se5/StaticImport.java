package book.j2se5;

// 注意静态导入的写法: import static
import static java.lang.Math.max;//导入了Math的max方法
import static java.lang.Math.min;//导入了Math的min方法
import static java.lang.Integer.*;//导入了Integer的所有静态方法和属性
/**
 * 静态导入。在使用其他类的公共静态方法或者属性时，无需指定类名。
 * 只需要将类的静态方法和属性放在import声明中即可。
 */
public class StaticImport {
	public static void main(String[] args) {
		// 通过静态导入使用Math的静态方法
		System.out.println(max(5,10));
		System.out.println(min(5,10));
		// 通过静态导入使用Integer的静态方法
		System.out.println(parseInt("55544"));
		System.out.println(toBinaryString(2354));
		// 通过静态导入使用Integer的静态属性
		System.out.println(MAX_VALUE);
		System.out.println(MIN_VALUE);
	}
}