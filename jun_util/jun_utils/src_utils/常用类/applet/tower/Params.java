package book.applet.tower;

/**
 * 定义了移动汉诺塔的参数
 */
public class Params {
	// 要移动的盘数
	public int num;
	// 移动的起始塔
	public int from;
	// 移动的目的塔
	public int to;
	// 移动的中转塔
	public int inter;
	// 操作步骤码
	public int actionCode;
	
	public Params(int num, int from, int to, int inter, int codePart) {
		this.num = num;
		this.from = from;
		this.to = to;
		this.inter = inter;
		this.actionCode = codePart;
	}
}