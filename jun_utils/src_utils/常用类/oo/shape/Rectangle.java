package book.oo.shape;

/**
 * 长方形
 */
public class Rectangle extends MyShape {
	//长方形的长
	private double length;
	//长方形的宽
	private double width;
	//边长错误信息
	public static final String SIDEERR = "长方形的长和宽必须大于0！";
	/**
	 *默认构造函数
	 */
	public Rectangle(){
		init();
	}
	/**
	 * 用长和宽构造一个长方形
	 * @param a	长的值
	 * @param b 宽的值
	 */
	public Rectangle(double a, double b){
		if ((a <= 0) || (b <= 0)){
			System.err.println(SIDEERR);
			init();
		} else {
			this.length = a;
			this.width = b;
		}
	}
	/**
	 * 缺省的长方形
	 */
	private void init(){
		this.length = 5;
		this.width = 4;
	}
	
	public double getGirth() {
		return (this.length + this.width) * 2;
	}
	public double getArea() {
		return this.length * this.width;
	}
	public String toString() {
		return "矩形的名字是：" + this.name + "，长为" + this.length + "，宽为" + this.width;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		if (length > 0){
			this.length = length;
		} else {
			System.err.println(SIDEERR);
		}
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		if (width > 0) {
			this.width = width;
		} else {
			System.err.println(SIDEERR);
		}
	}
	public static void main(String[] args) {
		Rectangle test = new Rectangle();
		test.setName("myRectangle");
		System.out.println( test.toString());
		System.out.println("矩形的周长是：" + test.getGirth());
		System.out.println("矩形的面积是：" + test.getArea());
	}
}
