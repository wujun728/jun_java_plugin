package visitor;

public class TestVisitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectStructure os = new ObjectStructure();
		os.addElement(new ConcreteElementA());
		os.addElement(new ConcreteElementB());
		
		os.handleElements(new ConcreteVisitor1());
	}

}
