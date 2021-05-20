package book.string;
/**
 * 使用StringBuffer类操作字符串
 */
public class UsingStringBuffer {
	/**
	 * 查找匹配字符串
	 */
	public static void testFindStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// indexOf返回子字符串在字符串中的最先出现的位置，如果不存在，返回负数。
		System.out.println("sb.indexOf(\"is\") = " + sb.indexOf("is"));
		// 可以给indexOf方法设置参数，指定匹配的起始位置
		System.out.println("sb.indexOf(\"is\", 4) = " + sb.indexOf("is", 4));
		// lastIndexOf返回子字符串在字符串中的最后出现的位置，如果不存在，返回负数。
		System.out
				.println("sb.lastIndexOf(\"is\") = " + sb.lastIndexOf("is"));
		// 可以给lastIndexOf方法设置参数，指定匹配的结束位置
		System.out.println("sb.lastIndexOf(\"is\", 1) = "
				+ sb.lastIndexOf("is", 1));
	}
	/**
	 * 截取字符串
	 */
	public static void testSubStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// substring方法截取字符串，可以指定截取的起始位置和终止位置，
		// 默认终止位置为字符串末尾
		System.out.println("sb.substring(2) = " + sb.substring(2));
		System.out.println("sb.substring(2, 9) = " + sb.substring(2, 9));
	}
	/**
	 * 获取字符串中某个位置上的字符
	 */
	public static void testCharAt() {
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		// charAt方法返回字符串中某个位置上的字符
		System.out.println("sb.charAt(2) = " + sb.charAt(2));
	}
	/**
	 * 添加各种类型的数据到字符串尾部
	 */
	public static void testAppend(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//添加字符或者字符数组到StringBuffer末尾
		sb.append('I');
		sb.append(new char[]{' ', 'a','m'});
		System.out.println("Append char: " + sb.toString());
		//添加字符串到StringBuffer末尾
		sb.append(" in 、Beijing.");
		System.out.println("Append String: " + sb.toString());
		//添加基本数字
		sb.append(15);
		sb.append(1.23f);
		sb.append(3.4d);
		sb.append(899L);
		System.out.println("Append number: " + sb.toString());
		//添加布尔值
		sb.append(false);
		sb.append(true);
		System.out.println("Append boolean: " + sb.toString());
	}
	/**
	 * 删除字符串中的数据 
	 */
	public static void testDelete(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//删除指定位置的字符
		sb.deleteCharAt(1);
		sb.deleteCharAt(2);
		System.out.println("Delete char: " + sb.toString());
		//删除指定范围的字符串
		sb.delete(1, 5);
		System.out.println("sb.delete(0, 5) =  " + sb.toString());
	}
	/**
	 * 向字符串中插入各种类型的数据
	 */
	public static void testInsert(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//能够在指定位置插入字符、字符数组、字符串以及各种数字和布尔值
		sb.insert(2, 'W');
		sb.insert(3, new char[]{'A', 'B', 'C'});
		sb.insert(8, "abc");
		sb.insert(2, 3);
		sb.insert(3, 2.3f);
		sb.insert(6, 3.75d);
		sb.insert(5, 9843L);
		sb.insert(2, true);
		System.out.println("testInsert: " + sb.toString());
	}
	/**
	 * 替换字符串中的某些字符
	 */
	public static void testReplace(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//将字符串中某段字符替换成另一个字符串
		sb.replace(3, 8, "replace");
		System.out.println("testReplace: " + sb.toString());
	}
	/**
	 * 将字符串倒序
	 */
	public static void reverseStr(){
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		//reverse方法将字符串到序
		System.out.println(sb.reverse());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("This is a StringBuffer!");
		System.out.println("原始StringBuffer: " + sb.toString());
		
		UsingStringBuffer.testFindStr();
		UsingStringBuffer.testSubStr();
		UsingStringBuffer.testCharAt();
		UsingStringBuffer.testAppend();
		UsingStringBuffer.testDelete();
		UsingStringBuffer.testInsert();
		UsingStringBuffer.testReplace();
		UsingStringBuffer.reverseStr();
	}

}
