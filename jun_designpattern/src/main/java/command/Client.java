package command;

public class Client {
	public void assemble() {
		Receiver receiver = new Receiver();
		Command command = new ConcreteCommand(receiver);
		
		Invoker invoker = new Invoker();
		invoker.setCommand(command);
	}
}
