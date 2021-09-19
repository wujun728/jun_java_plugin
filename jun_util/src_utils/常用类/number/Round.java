package book.number;

import java.math.BigDecimal;
/**
 * 对数字进行各种模式的舍入，如四舍五入等
 */
public class Round {
	/**
	 * 利用java.lang.Math类对数字进行四舍五入
	 * @param dou	待舍入的数字
	 * @return
	 */
	public static long getTraRoundMath(double dou) {
		//Math.round方法采用首先将dou加上0.5，然后取下整数。
		//dou = 4.6, 首先dou加0.5成5.1，其下整数为5。四舍五入的结果就是5。
		return Math.round(dou);
	}
	/**
	 * 对数字进行四舍五入
	 * @param dou	待舍入的数字
	 * @return
	 */
	public static long getTraRound(double dou) {
		//四舍五入模式相当于BigDecimal.ROUND_HALF_UP模式
		return Round.getIntRound(dou, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 要求舍入后返回整数类型，
	 * 
	 * @param dou  待舍入的数字
	 * @param roundmode  舍入模式
	 * @return
	 */
	public static long getIntRound(double dou, int roundmode) {
		//	最后取得BigDecimal对象转化成int并返回。
		return Round.getRound(dou, 0, roundmode).longValue();
	}
	/**
	 * 要求舍入后返回BigDecimal类型
	 * 
	 * @param dou  待舍入的数字
	 * @param scale 返回的BigDecimal对象的标度（scale）
	 * @param roundmode  舍入模式
	 * @return
	 */
	public static BigDecimal getRound(double dou, int scale, int roundmode) {
		//创建一个新的BigDecimal对象paramNumber，该对象的值和dou大小一样。
		BigDecimal paramNumber = new BigDecimal(dou);
		//然后调用paramNumber的setScale方法，该方法返回一个 BigDecimal对象temp，
		//返回值的标度为第一个参数指定的值，标度为大小表示小数部分的位数
		//第二个参数指定了paramNumber对象到temp对象的舍入模式，如四舍五入等。
		return paramNumber.setScale(scale, roundmode);
		//实际可以一条语句实现：return new BigDecimal(dou).setScale(0, roundmode);
	}
	public static void main(String[] args) {
		double dou1 = 8.50;
		double dou2 = -9.54;
		System.out.println("待舍入的数字：dou1 = " + dou1 + "; dou2 = " + dou2);
		System.out.println("采用Math类实现四舍五入的结果: \t" + Round.getTraRoundMath(dou1)
				+ "\t" + Round.getTraRoundMath(dou2));
		System.out.println("四舍五入后的结果: \t" + Round.getTraRound(dou1) + "\t"
				+ Round.getTraRound(dou2));

		System.out.println("要求舍入后返回整数：");
		//接近正无穷大的舍入模式
		System.out.println("舍入模式ROUND_CEILING: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_CEILING) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_CEILING));

		//接近零的舍入模式
		System.out.println("舍入模式ROUND_DOWN: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_DOWN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_DOWN));

		//舍入远离零的舍入模式
		System.out.println("舍入模式ROUND_UP : \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_UP) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_UP));

		//接近负无穷大的舍入模式
		System.out.println("舍入模式ROUND_FLOOR: \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_FLOOR) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_FLOOR));

		//向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为ROUND_DOWN舍入模式。
		System.out.println("舍入模式ROUND_HALF_DOWN:\t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_DOWN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_DOWN));

		//向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
		System.out.println("舍入模式ROUND_HALF_EVEN:\t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_EVEN) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_EVEN));

		//向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为ROUND_UP舍入模式。
		System.out.println("舍入模式ROUND_HALF_UP : \t"
				+ Round.getIntRound(dou1, BigDecimal.ROUND_HALF_UP) + "\t"
				+ Round.getIntRound(dou2, BigDecimal.ROUND_HALF_UP));

		System.out.println();
		System.out.println("要求舍入后返回数字的小数部分保留一位：");
		System.out.println("舍入模式ROUND_CEILING: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_CEILING) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_CEILING));
		System.out.println("舍入模式ROUND_DOWN: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_DOWN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_DOWN));
		System.out.println("舍入模式ROUND_UP : \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_UP) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_UP));
		System.out.println("舍入模式ROUND_FLOOR: \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_FLOOR) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_FLOOR));
		System.out.println("舍入模式ROUND_HALF_DOWN:\t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_DOWN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_DOWN));
		System.out.println("舍入模式ROUND_HALF_EVEN:\t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_EVEN) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_EVEN));
		System.out.println("舍入模式ROUND_HALF_UP : \t"
				+ Round.getRound(dou1, 1, BigDecimal.ROUND_HALF_UP) + "\t"
				+ Round.getRound(dou2, 1, BigDecimal.ROUND_HALF_UP));
	}
}