package book.string;

import java.security.MessageDigest;

/**
 * 对密码进行加密和验证的程序
 */
public class Password {

	/**	十六进制下数字到字符的映射数组	*/
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 把inputString加密。
	 * @param inputString	待加密的字符串
	 * @return
	 */
	public static String createPassword(String inputString){
		return encodeByMD5(inputString);
	}
	/**
	 * 验证输入的密码是否正确
	 * @param password		真正的密码（加密后的真密码）
	 * @param inputString	输入的字符串
	 * @return
	 */
	public static boolean authenticatePassword(String password, String inputString){
		if (password.equals(encodeByMD5(inputString))){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对字符串进行MD5编码
	 * @param originString
	 * @return
	 */
	private static String encodeByMD5(String originString) {
		if (originString != null){
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString	.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b  字节数组
	 * @return 十六进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将一个字节转化成16进制形式的字符串
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}


	public static void main(String[] args) {
		String password = Password.createPassword("888888");
		System.out.println("对888888用MD5摘要后的字符串：" + password);
		String inputString = "8888";
		System.out.println("8888与密码匹配？" + Password.authenticatePassword(password, inputString));
		inputString = "888888";
		System.out.println("888888与密码匹配？" + Password.authenticatePassword(password, inputString));
	}
}