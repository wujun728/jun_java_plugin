package singleton;

public class HungrySingleton {
	private static HungrySingleton hungrySingleton = new HungrySingleton();
	
	private HungrySingleton() {
		
	}
	
	public static synchronized HungrySingleton getHungrySingleton() {
		return hungrySingleton;
	}
	
	public void test() {
		System.out.println("--------------jinjin test hungrySingleton-------------");
	}
}
