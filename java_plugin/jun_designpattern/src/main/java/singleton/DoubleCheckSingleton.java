package singleton;

public class DoubleCheckSingleton {
	private static volatile DoubleCheckSingleton doubleCheckSingleton = null;
	
	private DoubleCheckSingleton() {
		
	}
	
	public static DoubleCheckSingleton getDoubleCheckSingleton() {
		if (doubleCheckSingleton == null) {
			synchronized (DoubleCheckSingleton.class) {
				if (doubleCheckSingleton == null) {
					return new DoubleCheckSingleton();
				}
			}
		}
		return doubleCheckSingleton;
	}
	
	public void test() {
		System.out.println("--------------jinjin test doubleCheckSingleton-------------");
	}
}
