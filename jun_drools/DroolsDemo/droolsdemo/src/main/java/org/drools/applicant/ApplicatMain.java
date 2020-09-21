package org.drools.applicant;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

public class ApplicatMain {

	public static void main(String[] args) {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		Resource drlFilePath = ResourceFactory.newClassPathResource("licenseApplication.drl", Applicant.class);
		kbuilder.add( drlFilePath, ResourceType.DRL);

		if (kbuilder.hasErrors()) {
			System.err.println(kbuilder.getErrors().toString());
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

		Applicant applicant = new Applicant("Mr John Smith", 16);

		System.out.println(applicant.isValid());

		ksession.execute(applicant);

		System.out.println(applicant.isValid());
		
	}

}
