package book.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检测用户输入的字符串是否是一个格式合法的电话号码。
 * 中国国内电话号码的格式是：
 * 3位区号加8位号码或者4位区号加7位号码，中间可以有一个-号，或者没有
 */
public class CheckPhoneNO {
	/**电话号码的正则表达式*/
	private static String PATTERN_ONE = "^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$";
	//PATTERN_TWO正则表达式等价于PATTERN_ONE
//	private static String PATTERN_TWO = "^(\\d{3}-?\\d{8})|(\\d{4}-?\\d{7})$";

	/**
	 * 使用String类中利用正则表达式匹配的方法
	 * @param phoneNO	待检验的电话号码
	 * @return
	 */
	public static boolean usingStringRegex(String phoneNO){
		if (phoneNO != null){
			return phoneNO.matches(PATTERN_ONE);
		} else {
			return false;
		}
	}
	/**
	 * 使用Pattern和Matcher匹配
	 * @param phoneNO
	 * @return
	 */
	public static boolean usingPatternRegex(String phoneNO){
		Pattern p = Pattern.compile(PATTERN_ONE);
		//创建一个Matcher
		Matcher m = p.matcher(phoneNO);
		//精确匹配		
		return m.matches();
	}

	public static void main(String[] args) {
		//符合格式的号码
		String phoneNO = "010-88888888";
		System.out.println(phoneNO + " 是一个格式合法的电话号码? "
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "01088888888";
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "0731-6666666";
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		//不符合格式的号码
		phoneNO = "010-7777777777";
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
		phoneNO = "0df0-777dg77";
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingStringRegex(phoneNO));
		System.out.println(phoneNO + " 是一个格式合法的电话号码? " 
				+ CheckPhoneNO.usingPatternRegex(phoneNO));
	}
}
