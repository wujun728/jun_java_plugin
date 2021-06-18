package abstractfactory;

public class TestAbstractFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractFactory scheme = new SchemeAMD();
		AbstractFactory scheme2 = new SchemeInter();
		
		ComputerEngineer engineer = new ComputerEngineer();
		engineer.makeComputer(scheme);
		engineer.makeComputer(scheme2);
	}

}
