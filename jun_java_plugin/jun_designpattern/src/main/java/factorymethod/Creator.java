package factorymethod;

public abstract class Creator {
	protected abstract Product factoryMethod();
	
	public void test() {
		Product product = factoryMethod();
		product.operate();
	}
}
