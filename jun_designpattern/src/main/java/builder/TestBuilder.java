package builder;

public class TestBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Builder aBuilder = new ABuilder();
		Builder bBuilder = new BBuilder();
		
		Director director = new Director(aBuilder);
		Director director2 = new Director(bBuilder);
		
		Product product = director.assemble("jj", "mm", "gg");
		Product product2 = director2.assemble("jj", "mm", "gg");
		
		System.out.println(product);
		System.out.println(product2);
	}

}
