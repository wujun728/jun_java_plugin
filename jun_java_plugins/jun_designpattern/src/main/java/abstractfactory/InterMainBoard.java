package abstractfactory;

public class InterMainBoard implements MainBoardApi {
	private int pinHoles;
	
	public InterMainBoard(int pinHoles) {
		this.pinHoles = pinHoles;
	}

	@Override
	public void installCpu() {
		System.out.println("install InterMainBoard! pinHoles = " + pinHoles);
	}

}
