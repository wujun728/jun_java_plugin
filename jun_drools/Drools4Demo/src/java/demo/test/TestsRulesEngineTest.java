package demo.test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import junit.framework.TestCase;
import demo.Machine;
import demo.Test;
import demo.TestDAO;
import demo.TestDAOImpl;
import demo.TestsRulesEngine;

public class TestsRulesEngineTest extends TestCase {

	private class MachineResultSetImpl implements MachineResultSet {

		Iterator machines;

		public MachineResultSetImpl() {
			super();
			Machine m1 = new Machine();
			m1.setSerialNumber("1234A");
			m1.setType("Type1");
			m1.setFunctions(Arrays.asList(new String[] { "DDNS Server",
					"DNS Server" }));
			Machine m2 = new Machine();
			m2.setSerialNumber("1234B");
			m2.setType("Type2");
			m2.setFunctions(Arrays.asList(new String[] { "DDNS Server",
					"DNS Server" }));
			Machine m3 = new Machine();
			m3.setSerialNumber("1234C");
			m3.setType("Type2");
			m3
					.setFunctions(Arrays.asList(new String[] { "Gateway",
							"Router" }));
			Machine m4 = new Machine();
			m4.setSerialNumber("1234D");
			m4.setType("Type2");
			m4.setFunctions(Arrays.asList(new String[] { "Gateway" }));
			Machine m5 = new Machine();
			m5.setSerialNumber("1234E");
			m5.setType("Type2");
			m5.setFunctions(Arrays.asList(new String[] { "DNS Server" }));
			machines = Arrays.asList(new Object[] { m1, m2, m3, m4, m5 })
					.iterator();
		}

		public boolean next() {
			return machines.hasNext();
		}

		public Machine getMachine() {
			return (Machine) machines.next();
		}
	}

	private MachineResultSet machineResultSet;

	private TestsRulesEngine testsRulesEngine;

	private TestDAO testDAO;

	protected void setUp() throws Exception {
		super.setUp();
		// Set drools.schema.validating property to false
		// Ideally, this would be set only once as a JVM property for your program
		// Setting this property to false, gets rid of the following message:
		// cvc-elt.1: Cannot find the declaration of element 'rule-set'
        System.setProperty("drools.schema.validating", "false");
		machineResultSet = new MachineResultSetImpl();
		testDAO = new TestDAOImpl();
		testsRulesEngine = new TestsRulesEngine(testDAO);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		machineResultSet = null;
		testDAO = null;
		testsRulesEngine = null;
	}

	public void testTestsRulesEngine() throws Exception {
		while (machineResultSet.next()) {
			Machine machine = machineResultSet.getMachine();
			testsRulesEngine.assignTests(machine);
			Timestamp creationTs = machine.getCreationTs();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(creationTs);
			Timestamp testsDueTime = machine.getTestsDueTime();

			if (machine.getSerialNumber().equals("1234A")) {
				assertEquals(3, machine.getTests().size());
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST1)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST2)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST5)));

				calendar.add(Calendar.DATE, 3);
				assertEquals(calendar.getTime(), testsDueTime);
			} else if (machine.getSerialNumber().equals("1234B")) {
				assertEquals(4, machine.getTests().size());
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST5)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST4)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST3)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST2)));

				calendar.add(Calendar.DATE, 7);
				assertEquals(calendar.getTime(), testsDueTime);
			} else if (machine.getSerialNumber().equals("1234C")) {
				assertEquals(3, machine.getTests().size());
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST3)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST4)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST1)));

				calendar.add(Calendar.DATE, 3);
				assertEquals(calendar.getTime(), testsDueTime);
			} else if (machine.getSerialNumber().equals("1234D")) {
				assertEquals(2, machine.getTests().size());
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST3)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST4)));

				calendar.add(Calendar.DATE, 10);
				assertEquals(calendar.getTime(), testsDueTime);
			} else if (machine.getSerialNumber().equals("1234E")) {
				assertEquals(2, machine.getTests().size());
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST5)));
				assertTrue(machine.getTests().contains(
						testDAO.findByKey(Test.TEST4)));

				calendar.add(Calendar.DATE, 12);
				assertEquals(calendar.getTime(), testsDueTime);
			}
		}
	}

}
