package templatemethod;

public abstract class AbstractClass {
	public abstract void doSome1();
	
	public abstract void doSome2();
	
	public void templateMethod() {
		doSome1();
		doSome2();
	}
}
