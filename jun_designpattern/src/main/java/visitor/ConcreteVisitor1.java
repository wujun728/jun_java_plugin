package visitor;

public class ConcreteVisitor1 implements Visitor {

	@Override
	public void visitConcreteElementA(ConcreteElementA concreteElementA) {
		System.out.println("concreteElementA" + "其他操作");
		concreteElementA.operateA();
	}

	@Override
	public void visitConcreteElementB(ConcreteElementB concreteElementB) {
		System.out.println("concreteElementB" + "其他操作");
		concreteElementB.operateB();
	}

}
