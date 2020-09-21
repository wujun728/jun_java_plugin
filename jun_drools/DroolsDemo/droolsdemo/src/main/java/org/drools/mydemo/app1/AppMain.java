package org.drools.mydemo.app1;

import java.util.ArrayList;
import java.util.List;

import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.util.DroolsUtils;

public class AppMain {

	public static void main(String[] args) {
		ContentElements e1 = new ContentElements();
		e1.setN(11.2);
		e1.setP(8.5);
		
		ExperimentDTO dto = new ExperimentDTO();
		dto.setSoilConstituents(e1);

		List<Object> list = new ArrayList<Object>();
		list.add(e1);
		list.add(dto);
		
		Resource resource = ResourceFactory.newClassPathResource( "app.drl",  AppMain.class);
		StatefulKnowledgeSession statefulKnowledgeSession = DroolsUtils.generateStatefulKnowledgeSessionByDRL(resource);
		
		DroolsUtils.runRules(statefulKnowledgeSession, list);
		System.out.println(dto.getResult());
	}
	

}
