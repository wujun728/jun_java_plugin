package com.us.demo.money;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.spi.Activation;
import org.drools.core.spi.AgendaGroup;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Test {
	public static void main(String []args){
		KieServices kieServices=KieServices.Factory.get();
		KieContainer kieContainer=kieServices.getKieClasspathContainer();
		KieSession kieSession=kieContainer.newKieSession("ksession-money");
		List list =new ArrayList<Object>();
		kieSession.setGlobal("list", list);
		
		Money money1=new Money(1);
		Money money2=new Money(8);
		//Money money3=new Money(101);
		
		kieSession.insert(money1);
		kieSession.insert(money2);
		//kieSession.insert(money3);
		//相当于一个栈结构 先进入的先执行
		kieSession.fireAllRules();
//		kieSession.fire
		
//		System.out.println("money1"+money1.toString());
//		for(int i=0;i<list.size();i++){
//			System.out.println("List"+i+" Number:"+(Money)(list.get(i)));
//		}
		//kieSession.insert(money2);
		//kieSession.fireAllRules();
		//fire 射出 映射执行
		kieSession.dispose();
//		System.out.println("-------dispose----------");
//		for(int i=0;i<list.size();i++){
//			System.out.println("List"+i+" Number:"+(Money)(list.get(i)));
//		}

//		[Number:11 ]  [Desc:number>0]
//				[Number:101 ]  [Desc:number>10]
//				List0 Number:[Number:101 ]  [Desc:number>10]
//				List1 Number:[Number:101 ]  [Desc:number>10]
//				-------dispose----------
//				List0 Number:[Number:101 ]  [Desc:number>10]
//				List1 Number:[Number:101 ]  [Desc:number>10]
		//结果是这样，是因为 working memory 内的数据并不是去原地址去取的，应该是它拷贝了一份值，然后进行了比较
		//Facts 内的其实是 原地址数据的一个拷贝。但是改变数据的时候，其实改变的是
		//Activation<ModedAssertion<T>>
		//AgendaGroup
	}

}
