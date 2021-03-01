package book.oo;

public class ComplexNumber implements Cloneable{

	/** 复数的实部 */
	private double realPart;

	/** 复数的虚部 */
	private double imaginaryPart;

	/** 默认构造函数 */
	public ComplexNumber() {
		this.realPart = 0.0;
		this.imaginaryPart = 0.0;
	}

	/**
	 * 构造函数
	 * @param a 实部
	 * @param b 虚部
	 */
	public ComplexNumber(double a, double b) {
		this.realPart = a;
		this.imaginaryPart = b;
	}

	/**
	 * 复数的加法运算。 
	 * c = a + b的运算法则是： 
	 * c.实部 = a.实部 + b.实部; c.虚部 = a.虚部 + b.虚部
	 * @param aComNum  加数
	 * @return
	 */
	public ComplexNumber add(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("对象不能够为null！");
			return new ComplexNumber();
		}
		return new ComplexNumber(this.realPart + aComNum.getRealPart(),
				this.imaginaryPart + aComNum.getImaginaryPart());
	}

	/**
	 * 复数的减法运算。 
	 * c = a - b的运算法则是： 
	 * c.实部 = a.实部 - b.实部; c.虚部 = a.虚部 - b.虚部
	 * @param aComNum  减数
	 * @return
	 */
	public ComplexNumber decrease(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("对象不能够为null！");
			return new ComplexNumber();
		}
		return new ComplexNumber(this.realPart - aComNum.getRealPart(),
				this.imaginaryPart - aComNum.getImaginaryPart());
	}

	/**
	 * 复数的乘法运算。 
	 * c = a * b的运算法则是： 
	 * c.实部 = a.实部 * b.实部 - a.虚部 * b.虚部; 
	 * c.虚部 = a.虚部 * b.实部 + a.实部 * b.虚部;
	 * @param aComNum  乘数
	 * @return
	 */
	public ComplexNumber multiply(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("对象不能够为null！");
			return new ComplexNumber();
		}
		double newReal = this.realPart * aComNum.realPart - this.imaginaryPart
				* aComNum.imaginaryPart;
		double newImaginary = this.realPart * aComNum.imaginaryPart
				+ this.imaginaryPart * aComNum.realPart;
		ComplexNumber result = new ComplexNumber(newReal, newImaginary);
		return result;
	}

	/**
	 * 复数的除法运算。 
	 * c = a / b 的运算法则是： 
	 * c.实部 = (a.实部 * b.实部 + a.虚部 * b.虚部) / (b.实部 *b.实部 + b.虚部 * b.虚部); 
	 * c.虚部 = (a.虚部 * b.实部 - a.实部 * b.虚部) / (b.实部 * b.实部 + b.虚部 * b.虚部);
	 * @param aComNum  除数
	 * @return
	 */
	public ComplexNumber divide(ComplexNumber aComNum) {
		if (aComNum == null) {
			System.err.println("对象不能够为null！");
			return new ComplexNumber();
		}
		if ((aComNum.getRealPart() == 0) && (aComNum.getImaginaryPart() == 0)) {
			System.err.println("除数不能够为0！");
			return new ComplexNumber();
		}

		double temp = aComNum.getRealPart() * aComNum.getRealPart()
				+ aComNum.getImaginaryPart() * aComNum.getImaginaryPart();
		double crealpart = (this.realPart * aComNum.getRealPart() + this.imaginaryPart
				* aComNum.getImaginaryPart())
				/ temp;
		double cimaginaryPart = (this.imaginaryPart * aComNum.getRealPart() - this.realPart
				* aComNum.getImaginaryPart())
				/ temp;
		return new ComplexNumber(crealpart, cimaginaryPart);
	}

	/**
	 * 将一个复数显示为字符串
	 */
	public String toString() {
		return this.realPart + " + " + this.imaginaryPart + "i";
	}

	/**
	 * 比较一个对象是否和这个复数对象的值相等
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		// 首先判断a是不是一个复数对象，instanceof关键字是用来判断对象的类型。
		if (obj instanceof ComplexNumber) {
			// 如果a是复数对象，需要将它强制类型转换成复数对象，才能调用复数类提供的方法。
			ComplexNumber b = (ComplexNumber) obj;
			if ((this.realPart == b.getRealPart())
					&& (this.imaginaryPart == b.getImaginaryPart())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获得该复数对象的hashcode
	 */
	public int hashCode() {
		// 如果两个复数对象是equals的，那么它们的hashCode也必须相同。
		// 两个值相等的复数对象通过toString()方法得到的输出字符串是一样的，
		// 于是，可以把得到的字符串的hashCode当作复数对象的hashCode
		return this.toString().hashCode();
	}

	/**
	 * 根据现有对象克隆一个新对象
	 */
	public Object clone() {
		// 如果你要使自定义的类能够被clone,就必须实现Cloneable接口并且重写它的clone()方法．
		// 如果你仅仅重写了clone方法而没有在类的声明中添加实现Cloneable接口，调用clone方法时将会出现
		// CloneNotSupportedException异常，读者可以试试。
		try {
			ComplexNumber newObject = (ComplexNumber) super.clone();
			newObject.setRealPart(this.realPart);
			newObject.setImaginaryPart(this.imaginaryPart);
			return newObject;
		} catch (CloneNotSupportedException e) {
			// //如果没有实现Cloneable接口，抛出异常
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return 返回 imaginaryPart。
	 */
	public double getImaginaryPart() {
		return imaginaryPart;
	}

	/**
	 * @param imaginaryPart  要设置的 imaginaryPart。
	 */
	public void setImaginaryPart(double imaginaryPart) {
		this.imaginaryPart = imaginaryPart;
	}

	/**
	 * @return 返回 realPart。
	 */
	public double getRealPart() {
		return realPart;
	}

	/**
	 * @param realPart  要设置的 realPart。
	 */
	public void setRealPart(double realPart) {
		this.realPart = realPart;
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		ComplexNumber a = new ComplexNumber(2, 4);
		ComplexNumber b = new ComplexNumber(2, 4);
		System.out.println("ComplexNumber a: " + a.toString());
		System.out.println("ComplexNumber b: " + b.toString());

		System.out.println("a.euqals(b) = " + a.equals(b));
		System.out.println("a.hashCode = " + a.hashCode() 
				+ "; b.hashCode = " + b.hashCode());
		System.out.println("a.clone = " + a.clone().toString());

//		System.out.println("(a + b) = " + a.add(b).toString());
//		System.out.println("(a - b) = " + a.decrease(b).toString());
//		System.out.println("(a * b) = " + a.multiply(b).toString());
//		System.out.println("(a / b) = " + a.divide(b).toString());
		
		
//		System.out.println(a.equals(b));
//		ComplexNumber c = new ComplexNumber(1, 2);
//		ComplexNumber d = new ComplexNumber(1.00, 2.0);
//
//		ComplexNumber e = (ComplexNumber) d.clone();
//		System.out.println(e.toString());

	}
}
