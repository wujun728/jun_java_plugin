package facade;

public class Facade {
	
	private Facade() {
		
	}
	
	public static void assemble() {
		new InterCpu().assemble();
		new AsusMainBoard().assemble();
		new SamsungMemory().assemble();
	}
}
