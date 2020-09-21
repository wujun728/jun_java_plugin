package demo;

import org.drools.WorkingMemory;

public class TestsRulesEngine {

	private RulesEngine rulesEngine;

	private TestDAO testDAO;

	public TestsRulesEngine(TestDAO testDAO) throws RulesEngineException {
		super();
		rulesEngine = new RulesEngine("testRules2.drl");
		this.testDAO = testDAO;
	}

	public void assignTests(final Machine machine) {
		rulesEngine.executeRules(new WorkingEnvironmentCallback() {
			public void initEnvironment(WorkingMemory workingMemory) {
				// Set globals first before asserting/inserting any knowledge!
				workingMemory.setGlobal("testDAO", testDAO);
				workingMemory.insert(machine);
			};
		});
	}

}
