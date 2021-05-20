package designpatterns.createPatterns.abstractfactory;

public class Create2 extends Creator {

	@Override
	public Product Create() {
		return new ProductA2();
	}

}
