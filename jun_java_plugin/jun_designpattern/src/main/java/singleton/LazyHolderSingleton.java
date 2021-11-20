package singleton;

public class LazyHolderSingleton {
	
	
	private LazyHolderSingleton() {
		
	}
	
	public static LazyHolderSingleton getLazyHolderSingleton() {
		return SingletonHolder.lazyHolderSingleton;
	}
	
	public void test() {
		System.out.println("--------------jinjin test lazyHolderSingleton-------------");
	}
	
	private static class SingletonHolder {
		private static LazyHolderSingleton lazyHolderSingleton = new LazyHolderSingleton();	
	}
}
