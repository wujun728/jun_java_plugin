package singleton;

public class TestSingleton {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LazySingleton.getLazySingleton().test();
		HungrySingleton.getHungrySingleton().test();
		
		DoubleCheckSingleton.getDoubleCheckSingleton().test();
		LazyHolderSingleton.getLazyHolderSingleton().test();
		
		EnumSingleton.uniqueInstance.go();
		
		System.out.println(EnumSingleton.uniqueInstance);
	}

}
