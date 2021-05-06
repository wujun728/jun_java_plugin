package designpatterns.createPatterns.abstractfactory;

public class Create1 extends Creator {

	@Override
	public Product Create() {
		return new ProdcutA1();
	}

}
