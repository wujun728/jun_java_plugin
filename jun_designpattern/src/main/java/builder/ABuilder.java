package builder;

public class ABuilder implements Builder {
	private Product product; 
	
	public ABuilder() {
		product = new Product();
	}

	@Override
	public void head(String head) {
		product.setHead("a_" + head);
	}

	@Override
	public void body(String body) {
		product.setBody("a_" + body);
	}

	@Override
	public void foot(String foot) {
		product.setFoot("a_" + foot);
	}

	@Override
	public Product getProduct() {
		return product;
	}

}
