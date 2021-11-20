package prototype;

public class Client {
	private Prototype prototype;
	
	public Client(Prototype prototype) {
		this.prototype = prototype;
	}
	
	public void someOprate() {
		Prototype prototype2 = prototype.clonePrototype();
		//prototype2.clonePrototype();
	}
}
