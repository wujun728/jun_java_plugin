package org.drools.mydemo.employee;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class EmployeeTest {

	public static void main(String[] args) throws Exception {

		// load up the knowledge base

		KnowledgeBase kbase = readKnowledgeBase();

		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
				.newFileLogger(ksession, "test");

		// go !

		Employ emp = new Employ();
		emp.setBonus(1000.0);
		emp.setEduInfo("master");

		emp.setResume("tech");

		emp.setAnnualExam("good");

		emp.setAwardPunish("award");

		ksession.insert(emp);

		ksession.startProcess("salary");

		ksession.fireAllRules();

		System.out.println("Basic Salary: " + emp.getBasicSalary());

		System.out.println("Duty Salary: " + emp.getDutySalary());

		System.out.println("Bonus : " + emp.getBonus());

		System.out.println("rate : " + emp.getPercent());

		System.out.printf("Total Salary: %.0f", emp.getTotalSalary());

		logger.close();
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {

		KnowledgeBuilder kbuilder =

		KnowledgeBuilderFactory.newKnowledgeBuilder();

		kbuilder.add(ResourceFactory.newClassPathResource("EduInfoRule.drl",
				EmployeeTest.class), ResourceType.DRL);

		kbuilder.add(ResourceFactory.newClassPathResource("ResumeRule.drl",
				EmployeeTest.class), ResourceType.DRL);

		kbuilder.add(ResourceFactory.newClassPathResource("BonusRule.drl",
				EmployeeTest.class), ResourceType.DRL);

		kbuilder.add(ResourceFactory.newClassPathResource("AwardPunish.drl",
				EmployeeTest.class), ResourceType.DRL);

		kbuilder.add(ResourceFactory.newClassPathResource("TotalRule.drl",
				EmployeeTest.class), ResourceType.DRL);

		kbuilder.add(ResourceFactory.newClassPathResource("salary.rf",
				EmployeeTest.class), ResourceType.DRF);

		if (kbuilder.hasErrors()) {
			throw new IllegalArgumentException(kbuilder.getErrors().toString());
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		return kbase;

	}

}
