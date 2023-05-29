package factorymethod;

public class ConcreteCreator extends Creator {

	@Override
	protected Product factoryMethod() {
		return new ProductA();
	}

}
