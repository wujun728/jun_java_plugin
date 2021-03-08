package org.typroject.tyboot.core.foundation.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * <pre>
 *  Tyrest
 *  File: Sequence.java
 *
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 *
 *  Description:
 *  TODO
 *
 *  Notes:
 *  $Id: Sequence.java  Tyrest\magintrursh $
 *
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class Sequence {



	public static final String DATAFORMAT_STR = "yyyyMMddHHmmssSSS";


	/**
	 * 20位：17位时间戳加三位随机数
	 *
	 * @return
	 */
	public synchronized static  String generatorOrderSn() {
		return generatorSequence(3, DATAFORMAT_STR);
	}


	/**
	 * 生成账单号，20位,
	 *
	 * @return
	 */
	public synchronized static  String generatorBillNo() {
		return generatorSequence(3, DATAFORMAT_STR);
	}


	/**
	 * 生成userId  32位,
	 *
	 * @return
	 */
	public synchronized static  String generatorUserId() {
		return generatorSequence(15, DATAFORMAT_STR);
	}



	/**
	 *
	 * 生成交易流水号25位 17位时间标识 年月日时分秒毫秒 再加 八位 随机数
	 */
	public synchronized static String generatorSerialNo() {
		return generatorSequence(8, DATAFORMAT_STR);
	}

	/**
	 * 按時間和隨機數生成指定的編號，
	 *
	 * @param randomCount
	 *            隨機數位數
	 * @param dataformatStr
	 *            時間格式
	 * @return
	 */
	public static String generatorSequence(int randomCount, String dataformatStr) {
		return  new SimpleDateFormat(dataformatStr).format(new Date())+generatorRandomStrOfNum(randomCount);
	}

	public static String generatorRandomStrOfNum(int randomCount) {
		String randomStr = "";
		for (int i = 0; i < randomCount; i++) {
			randomStr += new Double(Math.random() * 10).intValue() + "";
		}
		return  randomStr;
	}


	/**
	 * 生成4位验证码
	 * @return
	 */
	public static String generatorSmsVerifyCode4() {
		String randomStr = "";
		for (int i = 0; i < 4; i++) {
			randomStr += new Double(Math.random() * 10).intValue() + "";
		}
		return  randomStr;
	}

	/**
	 * 生成6位验证码
	 * @return
	 */
	public static String generatorSmsVerifyCode6() {
		String randomStr = "";
		for (int i = 0; i < 6; i++) {
			randomStr += new Double(Math.random() * 10).intValue() + "";
		}
		return  randomStr;
	}


}
