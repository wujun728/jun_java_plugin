package adapter;

public class TestAdapter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Target target = new TargetImp();
		Target target2 = new Adapter(new AdapteeImp());
		target.test();
		target2.test();
		
		System.out.println("-----------双向适配器------------");
		
		Target target3 = new TargetImp();
		Adaptee adaptee = new AdapteeImp();
		Target target4 = new DoubleSidedAdapter(target3, adaptee);
		Adaptee adaptee2 = new DoubleSidedAdapter(target3, adaptee);
		
		target4.test();
		adaptee2.specialTest();
	}

}
