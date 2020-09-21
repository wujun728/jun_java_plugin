package com.us.demo.ticket;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Test2 {
	public static void main(String []args){
		KieContainer kc=KieServices.Factory.get().getKieClasspathContainer();
		KieSession ks=kc.newKieSession("ksession-ticket");
		
		final Customer a=new Customer("a","Gold");
		final Customer b=new Customer("b","Platinum");
		final Customer c=new Customer("c","Silver");
		final Customer d=new Customer("d","Silver");
		
		final Ticket t1=new Ticket(a);
		final Ticket t2=new Ticket(b);
		final Ticket t3=new Ticket(c);	
		final Ticket t4=new Ticket(d);
		
		//need add the two object
		ks.insert(a);
		ks.insert(b);
		ks.insert(c);
		ks.insert(d);

		ks.insert(t1);
		ks.insert(t2);
		ks.insert(t3);
		ks.insert(t4);

		ks.fireAllRules();
		
		ks.dispose();
		
	}
	
}
