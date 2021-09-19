package book.string;
/**
 * 判断一个字符串是否能够当Java的标识符
 */
public class JavaIdentifier {

	/**
	 * 判断字符串是否是合法的Java标识符
	 * @param s	待判断的字符串
	 * @return
	 */
	public static boolean isJavaIdentifier(String s){
        //如果字符串为空或者长度为0，返回false
		if ((s == null) || (s.length() == 0)) {
            return false;
        }
        //字符串中每一个字符都必须是Java标识符的一部分
        for (int i=0; i<s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                return false;
            }
        }
        return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\"my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("my_var"));
		System.out.println("\"my_var.1\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("my_var.1"));
		System.out.println("\"$my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("$my_var"));
		System.out.println("\"\u0391var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("\u0391var"));
		System.out.println("\"\1$my_var\" is an identifier? "
				+ JavaIdentifier.isJavaIdentifier("\1$my_var"));
	}
}
