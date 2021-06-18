package iterator;

public class ConcreteAggregate extends Aggregate {
	
	private String[] ss = null;
	
	public ConcreteAggregate(String[] ss) {
		this.ss = ss;
	}

	@Override
	public Iterator createIterator() {
		return new ConcreteIterator(this);
	}
	
	public Object get(int index) {
		Object object = null;
		if (index < ss.length) {
			object = ss[index];
		}
		return object;
	}
	
	public int size() {
		return ss.length;
	}

}
