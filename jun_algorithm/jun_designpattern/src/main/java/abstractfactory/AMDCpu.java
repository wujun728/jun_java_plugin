package abstractfactory;

public class AMDCpu implements CpuApi {
	private int pins;
	
	public AMDCpu(int pins) {
		this.pins = pins;
	}

	public void calculate() {
		System.out.println("calculate from AMDCpu! pins = " + pins);
	}
}
