package cn.rjzjh.commons.util.apiext;

import java.math.BigDecimal;
import java.util.Date;

import cn.rjzjh.commons.util.constant.SimpleDateFormatCase;

/**
 * @ClassName: NumberUtil
 * @Description: 计算类型的处理
 * @author 周俊辉
 * @date 2010-10-29 下午01:37:46
 * 
 */
public abstract class NumberUtil {
	/**
	 * 精度转换采用BigDecimal.ROUND_HALF_EVEN转换精度 向“最接近的”数字舍入，如果是5，则采取左边数字奇上偶下法则
	 * 
	 * @param input
	 *            要处理的值
	 * @param scale
	 *            精度
	 * @return
	 * */
	public static BigDecimal handleScale(BigDecimal input, int scale) {
		return input.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 把double值转为BigDecimal
	 * 
	 * @param input
	 *            要处理的值
	 * @param scale
	 *            精度
	 * @return
	 * */
	public static BigDecimal handleScale(double input, int scale) {
		BigDecimal bigDecimal = new BigDecimal(input);
		return handleScale(bigDecimal, scale);
	}

	/**
	 * 把String值转为BigDecimal
	 * 
	 * @param input
	 *            要处理的值
	 * @param scale
	 *            精度
	 * @return
	 * */
	public static BigDecimal handleScale(String input, int scale) {
		BigDecimal bigDecimal = new BigDecimal(input);
		return handleScale(bigDecimal, scale);
	}

	/**
	 * 跟据时间产生yyyyMMddHHmmssSSSS格式的长整形
	 * 
	 * @return long
	 * */
	public synchronized static long proUniqNumByTime() {
		return new Long(SimpleDateFormatCase.yyyyMMddHHmmssSSSS.getInstanc()
				.format(new Date()));
	}

	/****
	 * 两个BigDecimal相加，返回２位小数点精度的BigDecimal
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal bigDecimalAdd(BigDecimal b1, BigDecimal b2) {
		if (b1 == null || b2 == null) {
			return null;
		} else {
			return changeBD(b1.add(b2));
		}
	}

	/****
	 * 两个BigDecimal相减，返回２位小数点精度的BigDecimal
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal bigDecimalPlus(BigDecimal b1, BigDecimal b2) {
		if (b1 == null || b2 == null) {
			return null;
		} else {
			return changeBD(b1.subtract(b2));
		}
	}

	public static BigDecimal handNull(BigDecimal ori) {
		return ori == null ? new BigDecimal(0) : ori;
	}

	/****
	 * 把字符型/double型/BigDecimal型 转成２位小数点精度的BigDecimal
	 * 
	 * @param db
	 * @return
	 */
	public static BigDecimal changeBD(Object db) {
		if (db == null) {
			return null;
		}
		if (db instanceof BigDecimal) {
			return NumberUtil.handleScale((BigDecimal) db, 2);
		}
		Double dbd = null;
		if (db instanceof String) {
			dbd = Double.parseDouble(db.toString());
		} else if (db instanceof Double) {
			dbd = (Double) db;
		}
		if (dbd == null) {
			return null;
		}
		return NumberUtil.handleScale(dbd, 2);
	}

}
