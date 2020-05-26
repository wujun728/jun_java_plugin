package flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyeightFactory {
	private Map<String, Flyweight> fsMap = new HashMap<String, Flyweight>();
	
	public Flyweight getFlyweight(String key) {
		Flyweight f = fsMap.get(key);
		
		if (f == null) {
			f = new ConcreteFlyweight(key);
			
			fsMap.put(key, f);
		}
		return f;
	}
}
