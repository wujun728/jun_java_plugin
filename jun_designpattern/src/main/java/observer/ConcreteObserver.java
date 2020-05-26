package observer;


/**
 * @uml.dependency   supplier="observer.ConcreteSubject"
 */
public class ConcreteObserver implements Observer {
	
	private String observerState;

	@Override
	public void update(Subject subject) {
		observerState = ((ConcreteSubject)subject).getSubjectState();
		System.out.println(observerState);
	}

}
