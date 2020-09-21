package demo.order;

import java.util.Arrays;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

public class OrderTest {

	public static void main(String[] args) throws Exception {
		OrderTest ot = new OrderTest();
		ot.executeExample();
	}

	public int executeExample() throws Exception {

		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		kbuilder.add(ResourceFactory.newClassPathResource("discountrule.drl", getClass()), ResourceType.DRL);

		if (kbuilder.hasErrors()) {
			System.err.print(kbuilder.getErrors());
			return -1;
		}

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

		StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();

		Order order = new Order();
		order.setSumprice(1);
		ksession.execute(Arrays.asList(new Object[] { order }));

		System.out.println("DISCOUNT IS: " + order.getDiscountPercent());

		return order.getDiscountPercent();

	}

}
