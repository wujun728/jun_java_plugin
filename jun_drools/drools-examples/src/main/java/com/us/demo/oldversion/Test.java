package com.us.demo.oldversion;

import java.util.Collection;
import java.util.Iterator;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.kie.api.runtime.KieContainer;

public class Test {
	public static void main(String []args){
		// 获取knowledgePackage
		KnowledgeBuilder kbuilder=KnowledgeBuilderFactory.newKnowledgeBuilder();
		System.setProperty("drools.dateformat","yyyy-MM-dd HH:mm:ss");
		kbuilder.add(ResourceFactory.newClassPathResource("Test.drl",Test.class), ResourceType.DRL);
		// 判断是否有错
		if(kbuilder.hasErrors()){
			System.out.println("Has Errors!");
			KnowledgeBuilderErrors kbuidlerErrors=kbuilder.getErrors(); for(Iterator
					iter=kbuidlerErrors.iterator();iter.hasNext();){ System.out.println(iter.next());
					}
		}
		Collection<KnowledgePackage> Kpackages=kbuilder.getKnowledgePackages();
				
		//获取knowledgeBase
		KnowledgeBaseConfiguration kbconf=KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kbconf.setProperty("org.drools.sequential","true");
		KnowledgeBase kbase =KnowledgeBaseFactory.newKnowledgeBase(kbconf);
		//将规则加载到kbase
		kbase.addKnowledgePackages(Kpackages);
		//将kbuilder加载到 statefulSession内部
		StatefulKnowledgeSession statefulKSession=kbase.newStatefulKnowledgeSession();
		statefulKSession.insert(new Object());
		statefulKSession.fireAllRules();
		statefulKSession.dispose();

	}

}
