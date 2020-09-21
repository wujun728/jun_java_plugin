package org.drools.examples.helloworld;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;

public class ExpertBaseExample {

	/*public static void main(String[] args) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		//UrlResource resource = (UrlResource) ResourceFactory.newUrlResource("http://192.168.20.118:8080/guvnor-webapp/org.drools.guvnor.Guvnor/package/com.hanke.hbrid.template.drools/sp1");
		//kbuilder.add(resource, ResourceType.PKG);
		Resource resource = ResourceFactory.newClassPathResource( "ExpertBase.drl", ExpertBaseExample.class );
		kbuilder.add(resource, ResourceType.DRL);
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);

		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("agroType", "SANDY_SOIL");
		message.put("moisture", 40);
		ksession.insert( message );
		
		ksession.fireAllRules();
	    ksession.dispose();
	    
	    System.err.println(message.get("s1"));
	}*/
	
	public static void main(String[] args) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		
		Resource resource = ResourceFactory.newClassPathResource( "ExpertBase.drl", ExpertBaseExample.class );
		kbuilder.add(resource, ResourceType.DRL);
		Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(pkgs);

		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		ExpertBaseData d1 = new ExpertBaseData();
		InputDataModel inputDataModel = new InputDataModel("SANDY_SOIL", 34.2);
		d1.setInputDataModel(inputDataModel);
		
		ksession.insert( d1 );
		
		//ksession.fireAllRules();
	    ksession.dispose();
	    
	    System.err.println(d1.getId()+"\t"+d1.getOutputDataModel());
	}

}
