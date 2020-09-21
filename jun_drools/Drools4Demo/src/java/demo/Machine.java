package demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Machine {

	private String type;

	private List functions = new ArrayList();

	private String serialNumber;

	private Collection tests = new HashSet();

	private Timestamp creationTs;

	private Timestamp testsDueTime;

	public Machine() {
		super();
		this.creationTs = new Timestamp(System.currentTimeMillis());
	}

	public List getFunctions() {
		return functions;
	}

	public void setFunctions(List functions) {
		this.functions = functions;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Collection getTests() {
		return tests;
	}

	public void setTests(Collection tests) {
		this.tests = tests;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getTestsDueTime() {
		return testsDueTime;
	}

	public void setTestsDueTime(Timestamp testsDueTime) {
		this.testsDueTime = testsDueTime;
	}

	public Timestamp getCreationTs() {
		return creationTs;
	}

}
