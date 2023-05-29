package prototype;

public class ConcretePrototype1 implements Prototype {

	@Override
	public Prototype clonePrototype() {
		return new ConcretePrototype1();
	}

}
