package book.oo.shape;
/**
 * 自定义图形的基类
 */
public abstract class MyShape {
	/**图形的名字*/
	protected String name; //protected访问控制符表示只有本类和子类能够访问该属性。
	/**
	 * 抽象方法，获取形状的周长
	 */
	public abstract double getGirth();
	/**
	 * 抽象方法，获取形状的面积
	 */
	public abstract double getArea();
	/**
	 * 抽象方法，输出形状
	 */
	public abstract String toString();
	/**
	 * 获取图形的名字
	 * @return 返回图形的名字
	 */	
	public String getName(){
		return this.name;
	}
	/**
	 * 为图形设置名字
	 * @param name	要设置的图形的名字
	 */
	public void setName(String name){
		this.name = name;
	}
}
