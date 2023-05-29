package adapter;

public class DoubleSidedAdapter implements Target, Adaptee {
	
	private Target target;
	private Adaptee adaptee;
	
	public DoubleSidedAdapter(Target target, Adaptee adaptee) {
		this.target = target;
		this.adaptee = adaptee;
	}

	public void specialTest() {
		target.test();
		System.out.println("**********************it's a DoubleSidedAdapter from target, specialTest!");
	}

	public void test() {
		adaptee.specialTest();
		System.out.println("**********************it's a DoubleSidedAdapter from adaptee, test()!");
	}

}
