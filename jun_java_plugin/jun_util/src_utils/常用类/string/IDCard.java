package book.string;

/**
 * 公民身份号码是特征组合码,由十七位数字本体码和一位数字校验码组成.排列顺序从左至右依次为:
 * 六位数字地址码,八位数字出生日期码,三位数字顺序码和一位数字校验码。
 * 1、地址码：表示编码对象常住户口所在县（市、旗、区）的行政区划代码，按 GB/T 2260 的规定执行。 
 * 2、出生日期码：表示编码对象出生的年、月、日，按 * GB/T 7408 的规定执行。年、月、日代码之间不用分隔符。 
 * 例：某人出生日期为 1966年10月26日，其出生日期码为 19661026。
 * 3、顺序码：表示在同一地址码所标识的区域范围内，
 * 对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数千分配给女性。 
 * 4、校验码：校验码采用ISO 7064：1983，MOD 11-2 校验码系统。 
 * （1）十七位数字本体码加权求和公式 
 * S = Sum(Ai * Wi), i = * 0, ... , 16 ，先对前17位数字的权求和 
 * Ai:表示第i位置上的身份证号码数字值 
 * Wi:表示第i位置上的加权因子 
 * Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1
 * （2）计算模 Y = mod(S, 11) 
 * （3）通过模得到对应的校验码 
 * Y: 0 1 2 3 4 5 6 7 8 9 10 
 * 校验码: 1 0 X 9 8 7 6 5 4 3 2
 */
public class IDCard {
	// 加权因子
	private static final int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6,
			3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// 校验码
	private static final int[] checkDigit = new int[] { 1, 0, 'X', 9, 8, 7, 6,
			5, 4, 3, 2 };

	public IDCard() {
	}
	/**
	 * 验证身份证是否符合格式
	 * @param idcard
	 * @return
	 */
	public boolean Verify(String idcard) {
		if (idcard.length() == 15) {
			idcard = this.update2eighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		//获取输入身份证上的最后一位，它是校验码
		String checkDigit = idcard.substring(17, 18);
		//比较获取的校验码与本方法生成的校验码是否相等
		if (checkDigit.equals(this.getCheckDigit(idcard))) {
			return true;
		}
		return false;
	}

	/**
	 * 计算18位身份证的校验码
	 * @param eighteenCardID	18位身份证
	 * @return
	 */
	private String getCheckDigit(String eighteenCardID) {
		int remaining = 0;
		if (eighteenCardID.length() == 18) {
			eighteenCardID = eighteenCardID.substring(0, 17);
		}

		if (eighteenCardID.length() == 17) {
			int sum = 0;
			int[] a = new int[17];
			//先对前17位数字的权求和
			for (int i = 0; i < 17; i++) {
				String k = eighteenCardID.substring(i, i + 1);
				a[i] = Integer.parseInt(k);
			}
			for (int i = 0; i < 17; i++) {
				sum = sum + weight[i] * a[i];
			}
			//再与11取模
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(checkDigit[remaining]);
	}

	/**
	 * 将15位身份证升级成18位身份证号码
	 * @param fifteenCardID
	 * @return
	 */
	private String update2eighteen(String fifteenCardID) {
		//15位身份证上的生日中的年份没有19，要加上
		String eighteenCardID = fifteenCardID.substring(0, 6);
		eighteenCardID = eighteenCardID + "19";
		eighteenCardID = eighteenCardID + fifteenCardID.substring(6, 15);
		eighteenCardID = eighteenCardID + this.getCheckDigit(eighteenCardID);
		return eighteenCardID;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IDCard test = new IDCard();
		String idCardStr = "110105194912310025";
		System.out.println("身份证" + idCardStr + "验证合格？ " 
				+ test.Verify(idCardStr));
		idCardStr = "440524188001010014";
		System.out.println("身份证" + idCardStr + "验证合格？ "  
				+ test.Verify(idCardStr));
	}
}
