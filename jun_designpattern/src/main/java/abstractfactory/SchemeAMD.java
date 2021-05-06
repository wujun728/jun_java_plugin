package abstractfactory;

public class SchemeAMD implements AbstractFactory {

	@Override
	public CpuApi createCpu() {
		return new InterCpu(939);
	}

	@Override
	public MainBoardApi createMainBoard() {
		return new InterMainBoard(939);
	}

}
