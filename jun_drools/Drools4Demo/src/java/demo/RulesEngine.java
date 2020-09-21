package demo;

import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.event.DebugWorkingMemoryEventListener;
import org.drools.rule.Package;

public class RulesEngine {

	private RuleBase rules;

	private boolean debug = false;

	public RulesEngine(String rulesFile) throws RulesEngineException {
		super();
		try {
			// Read in the rules source file
			Reader source = new InputStreamReader(RulesEngine.class
					.getResourceAsStream("/" + rulesFile));
			// Use package builder to build up a rule package
			PackageBuilder builder = new PackageBuilder();
			// This will parse and compile in one step
			builder.addPackageFromDrl(source);
			// Get the compiled package
			Package pkg = builder.getPackage();
			// Add the package to a rulebase (deploy the rule package).
			rules = RuleBaseFactory.newRuleBase();
			rules.addPackage(pkg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RulesEngineException(
					"Could not load/compile rules file: " + rulesFile, e);
		}
	}

	public RulesEngine(String rulesFile, boolean debug)
			throws RulesEngineException {
		this(rulesFile);
		this.debug = debug;
	}

	public void executeRules(WorkingEnvironmentCallback callback) {
		WorkingMemory workingMemory = rules.newStatefulSession();
		if (debug) {
			workingMemory
					.addEventListener(new DebugWorkingMemoryEventListener());

		}
		callback.initEnvironment(workingMemory);
		workingMemory.fireAllRules();
	}

}
