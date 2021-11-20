package iterator;

public class TestIterator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] ss = new String[]{"����", "����", "����"};
		
		Aggregate aggregate = new ConcreteAggregate(ss);
		Iterator iterator = aggregate.createIterator();
		
		iterator.first();
		
		while(!iterator.isDone()) {
			Object object = iterator.currentItem();
			System.out.println("this is " + object);
			
			iterator.next();
		}
	}

}
