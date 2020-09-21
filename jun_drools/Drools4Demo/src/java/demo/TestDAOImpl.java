package demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestDAOImpl implements TestDAO {

	private static Map<Integer, Test> tests = new HashMap<Integer, Test>();

	static {
		int ids[] = { 1, 2, 3, 4, 5 };
		String names[] = { "Test1", "Test2", "Test3", "Test4", "Test5" };
		String descriptions[] = { "Description for Test1",
				"Description for Test2", "Description for Test3",
				"Description for Test4", "Description for Test5" };
		for (int i = 0; i < ids.length; i++) {
			Test test = new Test();
			Integer id = new Integer(ids[i]);
			test.setId(id);
			test.setName(names[i]);
			test.setDescription(descriptions[i]);
			tests.put(id, test);
		}
	}

	public Collection findAll() {
		return tests.values();
	}

	public Test findByKey(Integer id) {
		return (Test) tests.get(id);
	}

}
