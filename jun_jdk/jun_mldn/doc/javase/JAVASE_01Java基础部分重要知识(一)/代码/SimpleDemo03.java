public class SimpleDemo03{	// 声明一个类，类名称的命名规范：所有单词的首字母大写
	public static void main(String args[]){	// 主方法
		char c1 = 'a' ;	// 使用”'“括起来表示字符
		int x1 = c1 ;	// 将char变为int型
		x1++ ;			// 自增
		char c2 = (char)x1 ;	// 将int --> char，进行强制转换
		System.out.println(c2) ;
	}
};