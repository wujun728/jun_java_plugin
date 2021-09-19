package proxy;

public class Proxy implements Subject {
	/**
	 * @uml.property  name="realSubject"
	 * @uml.associationEnd  
	 */
	private Subject subject;
	
	public Proxy(Subject subject) {
		this.subject = subject;
	}

	@Override
	public void request() {
		System.out.println("其他的操作1");
		subject.request();
		System.out.println("其他的操作2");
	}

}
