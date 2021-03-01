package demo.others;

public class DeepCloneDemo {
	public static void main(String[] args) {
		B b = new B(2, new A(1));
		B b1 = (B) b.clone();
		System.out.println(b == b1);
		System.out.println(b.equals(b1));
		System.out.println(b.getClass() == b.getClass());
		System.out.println("改变b的副本b1前：y=" + b.getY() + ",x=" + b.getA().getX());
		b1.setY(5);
		b1.getA().setX(100);
		System.out.println("改变b的副本b1后：y=" + b.getY() + ",x=" + b.getA().getX());
		System.out.println("深克隆成功！！！");
	}
}

class A implements Cloneable {
	private int x;

	// 为了实现深克隆
	public Object clone() {
		A a = null;
		try {
			a = (A) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return a;
	}

	public A(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}

class B implements Cloneable {
	private int y;
	private A a;

	// 覆盖Object中clone方法
	// protected native Object clone() throws CloneNotSupportedException;
	// 注意到protected,这里把权限改为了public
	public Object clone() {
		B b = null;
		try {
			b = (B) super.clone();
			// 实现深克隆，没有这条语句只是克隆了a的引用
			b.a = (A) a.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return b;
	}

	public B(int y, A a) {
		this.y = y;
		this.a = a;
	}

	public int getY() {
		return y;
	}

	public A getA() {
		return a;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setA(A a) {
		this.a = a;
	}
}
