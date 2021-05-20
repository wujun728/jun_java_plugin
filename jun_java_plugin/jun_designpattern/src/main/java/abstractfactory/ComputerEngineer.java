package abstractfactory;

public class ComputerEngineer {
	private CpuApi cpu;
	
	private MainBoardApi mainBoard;
	
	public void makeComputer(AbstractFactory scheme) {
		prepareHardware(scheme);
	}

	private void prepareHardware(AbstractFactory scheme) {
		cpu = scheme.createCpu();
		mainBoard = scheme.createMainBoard();
		
		cpu.calculate();
		mainBoard.installCpu();
	}
}
