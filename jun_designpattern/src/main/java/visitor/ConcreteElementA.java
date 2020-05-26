package visitor;

public class ConcreteElementA extends Element {

	@Override
	public void accept(Visitor visitor) {
		visitor.visitConcreteElementA(this);
	}

	public void operateA() {
		System.out.println("this is A's operation");
	}
}
