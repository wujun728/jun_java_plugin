package com.us.demo.age;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.us.demo.age.person.Person;


public class Test {
	static KieSession getSession(){
		KieServices kieServices=KieServices.Factory.get();
		KieContainer kieContainer=kieServices.getKieClasspathContainer();
		
		return kieContainer.newKieSession("ksession-age");
	}
	
	public static void run(){
		KieSession kieSession=getSession();
		
		Person p1=new Person("Name1", 1);
		Person p2=new Person("Name6", 6);
		Person p3=new Person("Name12", 12);
//		Person p3=new Person("Name3", 13);
		Person p4=new Person("Name27", 27);
		Person p5=new Person("Name75", 75);
		
		// 返回一个 FactHandle对象
		kieSession.insert(p1);
		kieSession.insert(p2);
		kieSession.insert(p3);
		kieSession.insert(p4);
		kieSession.insert(p5);
		
		// 最后压入栈的先执行？ 这个是一个栈式的结构
		int count=kieSession.fireAllRules();
		kieSession.dispose();


		
	}
	public static void main(String[]args){
		run();
	}

}
// bug 1

//SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
//SLF4J: Defaulting to no-operation (NOP) logger implementation
//SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
//Exception in thread "main" java.lang.RuntimeException: Error while creating KieBase[Message [id=1, kieBase=age, level=ERROR, path=age.drl, line=31, column=0
//   text=Rule Compilation error getName cannot be resolved or is not a field], Message [id=2, kieBase=age, level=ERROR, path=age.drl, line=42, column=0
//   text=Rule Compilation error The method setDesc(String) in the type Person is not applicable for the arguments (Person)
//getName cannot be resolved or is not a field]]
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.getKieBase(KieContainerImpl.java:527)
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:687)
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:655)
//	at com.us.demo.age.Test.getSession(Test.java:14)
//	at com.us.demo.age.Test.run(Test.java:18)
//	at com.us.demo.age.Test.main(Test.java:39)


// bug 2
//SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
//SLF4J: Defaulting to no-operation (NOP) logger implementation
//SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
//Exception in thread "main" java.lang.RuntimeException: Error while creating KieBase[Message [id=1, kieBase=age, level=ERROR, path=age.drl, line=42, column=0
//   text=Rule Compilation error getName cannot be resolved or is not a field], Message [id=2, kieBase=age, level=ERROR, path=age.drl, line=31, column=0
//   text=Rule Compilation error getName cannot be resolved or is not a field]]
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.getKieBase(KieContainerImpl.java:527)
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:687)
//	at org.drools.compiler.kie.builder.impl.KieContainerImpl.newKieSession(KieContainerImpl.java:655)
//	at com.us.demo.age.Test.getSession(Test.java:14)
//	at com.us.demo.age.Test.run(Test.java:18)
//	at com.us.demo.age.Test.main(Test.java:39)

// 同优先级只会执行一次
// salicence 1 
//name:Name5 desc:少年
//name:Name4 desc:少年
//name:Name2 desc:少年
//name:Name3 desc:少年
//name:Name1 desc:少年
