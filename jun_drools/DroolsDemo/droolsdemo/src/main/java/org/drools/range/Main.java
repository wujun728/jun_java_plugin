package org.drools.range;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

public class Main {

	public static void main(String[] args) throws Exception {

		Collection<ParamSet> cfl = new ArrayList<ParamSet>();

		cfl.add(new ParamSet("weight", 10, 99, EnumSet.of(ItemCode.LOCK,
				ItemCode.STOCK)));

		cfl.add(new ParamSet("price", 10, 50, EnumSet.of(ItemCode.BARREL)));

		KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();

		Expander ex = new Expander();

		InputStream dis = Main.class.getClassLoader().getResourceAsStream("org/drools/range/rangeTemp.drl");
		//InputStream dis = new FileInputStream(new File("rangeTemp.drl"));

		ex.expand(kBase, dis, cfl);

		StatefulKnowledgeSession session = kBase.newStatefulKnowledgeSession();

		session.insert(new Item("A", 130, 42, ItemCode.LOCK));

		Item Bitem = new Item("B", 44, 100, ItemCode.STOCK);
		session.insert(Bitem);

		session.insert(new Item("C", 123, 180, ItemCode.BARREL));

		session.insert(new Item("D", 85, 9, ItemCode.LOCK));

		session.fireAllRules();

		System.err.println(Bitem.getOutput());
		
	}

}
