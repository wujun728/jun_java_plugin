package visitor;

public class ConcreteElementB extends Element {

	@Override
	public void accept(Visitor visitor) {
		visitor.visitConcreteElementB(this);
	}

	public void operateB() {
		System.out.println("this is B's operation");
	}
}
