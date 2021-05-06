package singleton;

public class LazySingleton {
	private static LazySingleton lazySingleton = null;
	
	private LazySingleton() {
		
	}
	
	public static synchronized LazySingleton getLazySingleton() {
		if (lazySingleton == null) {
			return new LazySingleton();
		} else {
			return lazySingleton;
		}
	}
	
	public void test() {
		System.out.println("--------------jinjin test lazySingleton-------------");
	}
}
