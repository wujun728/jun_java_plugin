package abstractfactory;

public class AMDMainBoard implements MainBoardApi {
	private int pinHoles;
	
	public AMDMainBoard(int pinHoles) {
		this.pinHoles = pinHoles;
	}

	@Override
	public void installCpu() {
		System.out.println("install AMDMainBoard! pinHoles = " + pinHoles);
	}

}
