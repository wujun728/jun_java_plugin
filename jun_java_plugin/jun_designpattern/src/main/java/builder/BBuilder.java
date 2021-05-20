package builder;

public class BBuilder implements Builder {
	private Product product; 
	
	public BBuilder() {
		product = new Product();
	}

	@Override
	public void head(String head) {
		product.setHead("b_" + head);
	}

	@Override
	public void body(String body) {
		product.setBody("b_" + body);
	}

	@Override
	public void foot(String foot) {
		product.setFoot("b_" + foot);
	}

	@Override
	public Product getProduct() {
		return product;
	}
}
