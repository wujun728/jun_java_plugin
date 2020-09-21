package com.us.demo.helloword;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.drools.core.base.RuleNameMatchesAgendaFilter;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class HelloWorldExample {
	public static void main(final String []args){
		KieServices kieServices=KieServices.Factory.get();
		KieContainer kc=kieServices.getKieClasspathContainer();
		execute(kc);

	}
	public static void execute(KieContainer kc){
		KieSession ksession=kc.newKieSession("ksession-helloworld");
		ksession.setGlobal("list", new ArrayList<Object>());
		ksession.addEventListener(new DebugAgendaEventListener());
		ksession.addEventListener(new DebugRuleRuntimeEventListener());
//		ksession.addEventListener(listener);
		//PropertyChangeSupport
//		RuleNameStartsWithAgendaFilter;
//		RuleNameMatchesAgendaFilter
		final Message message=new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		ksession.insert(message);
		
		ksession.fireAllRules();
		ksession.dispose();
		
	}
	//Message 类 用来加载和记录消息
	public static class Message{
		public static final int HELLO=0;
		public static final int GOODBYE=1;
		
		public String message;
		public int status;
		public Message(){
			
		}
		public String getMessage(){
			return this.message;
		}
		public void setMessage(String message){
			this.message=message;
		}
		public int getStatus(){
			return this.status;
		}
		public void setStatus(int status){
			this.status=status;
		}
		public static Message doSomething(Message message){
			return message;
		}
		public boolean isSomething(String msg,List<Object> list){
			list.add(this);
			return this.message.equals(msg);
		}
	}

}


