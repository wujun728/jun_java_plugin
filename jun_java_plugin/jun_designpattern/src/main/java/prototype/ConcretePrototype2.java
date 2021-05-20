package prototype;

public class ConcretePrototype2 implements Prototype {

	@Override
	public Prototype clonePrototype() {
		return new ConcretePrototype2();
	}

}
