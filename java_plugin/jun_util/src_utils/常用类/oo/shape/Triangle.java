package book.oo.shape;

/**
 * 三角形
 */
public class Triangle extends MyShape{

	//边a的长度
	private double sideA;
	//边b的长度
	private double sideB;
	//边c的长度
	private double sideC;
	//边长错误
	public static final String SIDEERR = "三角形的边长不能够小于0！";
	//不能构成三角形错误
	public static final String SHAPEERR = "三角形的二边之和必须大于第三边！";
	/**
	 *默认构造函数
	 */
	public Triangle(){
		init();
	}
	/**
	 * 用三条边构造一个三角形
	 * @param a	边a的边长
	 * @param b 边b的边长
	 * @param c 边c的边长
	 */
	public Triangle(double a, double b, double c){
		//如果给定的三条边能够组成三角形，便用给定的边长构成三角形
		if (isTrianglelegal(a, b, c)){
			this.sideA = a;
			this.sideB = b;
			this.sideC = c;
		} else {
			//否则，用默认值构造三角形
			init();
		}
	}
	/**
	 * 缺省的三角形
	 */
	private void init(){
		this.sideA = 3;
		this.sideB = 4;
		this.sideC = 5;
	}
	/**
	 * 判断三条边是否能够组成一个三角形
	 * @param a 边a的边长
	 * @param b 边b的边长
	 * @param c 边c的边长
	 * @return 布尔类型，如果能够组成三角形，便返回true，否则返回false
	 */
	private boolean isTrianglelegal(double a, double b, double c){
		//三条边的长度必须大于0
		if ((a <= 0) || (a <= 0) || (a <= 0)){
			System.err.println(SIDEERR);
			return false;
		} else if ((a + b < c) || (a + c < b) || (b + c < a)){
			//两边之和必须大于第三边
			System.err.println(SHAPEERR);
			return false;
		}
		return true;
	}
	public double getGirth() {
		return this.sideA + this.sideB + this.sideC;
	}
	public double getArea() {
		//根据海伦公式计算三角形的面积
		double s = (this.sideA + this.sideB + this.sideC) / 2;
		//利用Math.sqrt()方法进行开方运算
		return Math.sqrt(s * (s - this.sideA) * (s - this.sideB) * (s - this.sideC));
	}
	public String toString() {
		return "三角形的名字是：" + this.name + ", 它的三条边的边长分别是：" 
				+ this.sideA + ", " + this.sideB + ", " + this.sideC;
	}
	public double getSideA() {
		return sideA;
	}
	public void setSideA(double sideA) {
		//在设置边长的时候，依然要检测采用新的边长后，是否能够组成三角形。
		if (this.isTrianglelegal(sideA, this.sideB, this.sideC)){
			this.sideA = sideA;
		} 
	}
	public double getSideB() {
		return sideB;
	}
	public void setSideB(double sideB) {
		if (this.isTrianglelegal(this.sideA, sideB, this.sideC)){
			this.sideB = sideB;
		}
	}
	public double getSideC() {
		return sideC;
	}
	public void setSideC(double sideC) {
		if (this.isTrianglelegal(this.sideA, this.sideB, sideC)){
			this.sideC = sideC;
		}
	}
	public static void main(String[] args) {
		Triangle test = new Triangle();
		test.setName("myTriangle");
		System.out.println(test.toString());
		System.out.println("三角形的周长是：" + test.getGirth());
		System.out.println("三角形的面积是：" + test.getArea());
	}
}
