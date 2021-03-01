package builder;

public class Director {
	private Builder builder;
	
	public Director(Builder builder) {
		this.builder = builder;
	}
	
	public Product assemble(String head, String body, String foot) {
		builder.head(head);
		builder.body(body);
		builder.foot(foot);
		return builder.getProduct();
	}
}
