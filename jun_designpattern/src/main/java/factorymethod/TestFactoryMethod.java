package factorymethod;

public class TestFactoryMethod {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Creator creator = new ConcreteCreator();
		creator.test();
	}

}
