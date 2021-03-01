package builder;

public interface Builder {
	void head(String head);
	void body(String body);
	void foot(String foot);
	
	Product getProduct();
}
