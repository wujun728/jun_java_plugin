package iterator;

public class ConcreteIterator implements Iterator {

	private ConcreteAggregate aggregate;
	
	private int index = -1;
	
	public ConcreteIterator(ConcreteAggregate aggregate) {
		this.aggregate = aggregate;
	}
	
	@Override
	public void first() {
		index = 0;
	}

	@Override
	public void next() {
		if (index < this.aggregate.size()) {
			index++;
		}
	}

	@Override
	public boolean isDone() {
		if (index == this.aggregate.size()) {
			return true;
		}
		return false;
	}

	@Override
	public Object currentItem() {
		return aggregate.get(index);
	}

}
