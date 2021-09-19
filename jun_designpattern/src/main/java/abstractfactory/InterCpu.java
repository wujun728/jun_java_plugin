package abstractfactory;

public class InterCpu implements CpuApi {
	private int pins;
	
	public InterCpu(int pins) {
		this.pins = pins;
	}

	@Override
	public void calculate() {
		System.out.println("calculate from InterCpu! pins = " + pins);
	}

}
