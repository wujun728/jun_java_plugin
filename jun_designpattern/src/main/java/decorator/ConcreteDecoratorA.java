package decorator;

public class ConcreteDecoratorA extends Decorator {
	public ConcreteDecoratorA(Component component) {
		super(component);
	}

	@Override
	public void operation() {
		super.operation();
		System.out.println("这个是装饰者添加的功能。addedState=" + this.getAddedState());
	}
	
}