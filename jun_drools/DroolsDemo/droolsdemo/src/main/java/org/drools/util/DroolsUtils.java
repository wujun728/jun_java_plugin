package org.drools.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ReaderResource;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsUtils {

	private static boolean debug = true;

	public static StatefulKnowledgeSession generateStatefulKnowledgeSessionByDRL(
			String drlFilePath) {
		Resource resource = ResourceFactory.newClassPathResource(
				"com/hanke/hbird/template/drools/fertilizationRules.drl",
				DroolsUtils.class);
		return generateStatefulKnowledgeSessionByDRL(resource);
	}

	public static StatefulKnowledgeSession generateStatefulKnowledgeSessionByDRL(
			Resource resource) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(resource, ResourceType.DRL);
		if (kbuilder.hasErrors()) {
			throw new RuntimeException(kbuilder.getErrors().toString());
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase.newStatefulKnowledgeSession();
	}

	public static StatefulKnowledgeSession generateStatefulKnowledgeSessionByDRL(
			File drlFile) {
		Resource resource = null;
		try {
			resource = new ReaderResource(new FileReader(drlFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (resource == null) {
			return null;
		}
		return generateStatefulKnowledgeSessionByDRL(resource);
	}

	public static void runRules(StatefulKnowledgeSession ksession, Object object) {
		if (object == null)
			return;

		if (debug) {
			ksession.addEventListener(new DebugAgendaEventListener());
			ksession.addEventListener(new DebugWorkingMemoryEventListener());
		}

		if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>) (object);
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				ksession.insert(it.next());
			}
		} else if (object.getClass().isArray()) {
			Object[] objs = (Object[]) object;
			for (Object obj : objs) {
				ksession.insert(obj);
			}
		} else {
			ksession.insert(object);
		}

		ksession.fireAllRules();
		ksession.dispose();
	}

}
