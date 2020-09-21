package cn.rjzjh.commons.util.constant;

/***
 * 数学方面转换的类型
 * 
 * @author Administrator
 * 
 */
public enum MathConvertType {
	trunc("取整"), round("四舍五入"), ceil("有值进1");
	private String desc;

	private MathConvertType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
