package abstractfactory;

public class SchemeInter implements AbstractFactory {

	@Override
	public CpuApi createCpu() {
		return new InterCpu(1156);
	}

	@Override
	public MainBoardApi createMainBoard() {
		return new InterMainBoard(1156);
	}

}
