package abstractfactory;

public interface AbstractFactory {
	CpuApi createCpu();
	MainBoardApi createMainBoard();
}
