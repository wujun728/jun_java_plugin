package com.kulv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IDCheckUtils {


	private final static String BIRTH_DATE_FORMAT = "yyyyMMdd"; // 身份证号码中的出生日期的格式
	private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L); // 身份证的最小出生日期,1900年1月1日
	private final static int NEW_CARD_NUMBER_LENGTH = 18;
	private final static int OLD_CARD_NUMBER_LENGTH = 15;
	private final static char[] VERIFY_CODE = { '1', '0', 'X', '9', '8', '7',
			'6', '5', '4', '3', '2' }; // 18位身份证中最后一位校验码
	private final static int[] VERIFY_CODE_WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1,
			6, 3, 7, 9, 10, 5, 8, 4, 2 };// 18位身份证中，各个数字的生成校验码时的权值

	/**
	 * 验证身份证号码合法性
	 * @param cardNum 身份证 号码
	 * @return true or false
	 */
	public static boolean validCard(String cardNum) {

		if (cardNum == null || cardNum.equals("")) {
			return false;
		}

		if (OLD_CARD_NUMBER_LENGTH == cardNum.length()) {
			cardNum = contertToNewCardNumber(cardNum);
		}

		boolean result = true;
		result = result && (null != cardNum); // 身份证号不能为空

		result = result && NEW_CARD_NUMBER_LENGTH == cardNum.length(); // 身份证号长度是18(新证)

		// 身份证号的前17位必须是阿拉伯数字
		for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
			char ch = cardNum.charAt(i);
			result = result && ch >= '0' && ch <= '9';
		}
		
		// 身份证号的第18位校验正确
		result = result && (calculateVerifyCode(cardNum) == cardNum.charAt(NEW_CARD_NUMBER_LENGTH - 1));

		// 出生日期不能晚于当前时间，并且不能早于1900年
		
		
		String birthdayPart = cardNum.substring(6, 14);
		
		Date birthDate = null;
		try {
			birthDate = createBirthDateParser().parse(birthdayPart);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
		
		result = result && null != birthDate;
		result = result && birthDate.before(new Date());
		result = result && birthDate.after(MINIMAL_BIRTH_DATE);
		

	    String realBirthdayPart = createBirthDateParser().format(birthDate);
	    result = result && (birthdayPart.equals(realBirthdayPart));
		
		return  Boolean.valueOf(result);

	}

	/**
	 * 把15位身份证号码转换到18位身份证号码<br>
	 * 15位身份证号码与18位身份证号码的区别为：<br>
	 * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
	 * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
	 * 
	 * @param cardNumber
	 * @return
	 */
	private static String contertToNewCardNumber(String oldCardNumber) {
		StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
		buf.append(oldCardNumber.substring(0, 6));
		buf.append("19");
		buf.append(oldCardNumber.substring(6));
		buf.append(calculateVerifyCode(buf));
		return buf.toString();
	}

	/**
	 * 校验码（第十八位数）：
	 * 
	 * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2; 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9
	 * 8 7 6 5 4 3 2
	 * 
	 * @param cardNumber
	 * @return
	 */
	private static char calculateVerifyCode(CharSequence cardNumber) {
		int sum = 0;
		for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
			char ch = cardNumber.charAt(i);
			sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
		}
		return VERIFY_CODE[sum % 11];
	}
	
	
	private static SimpleDateFormat createBirthDateParser() {
		return new SimpleDateFormat(BIRTH_DATE_FORMAT);
	}
	
}
