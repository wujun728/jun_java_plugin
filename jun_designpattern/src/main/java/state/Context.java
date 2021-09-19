package state;

public class Context {
	private State state;
	
	public Context(State state) {
		this.state = state;
	}
	
	public void someOperate(String sampleParameter) {
		state.handle(sampleParameter);
	}
}
