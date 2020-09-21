package com.drools.utils;

import com.drools.entity.Message;
import com.drools.entity.People;
import com.drools.ruleengine.RuleEngine;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import java.util.ArrayList;

/**
 * Created by LANCHUNQIAN on 2016/10/13.
 */
public class test {

    public static void main(String[] args) {

        RuleEngine ruleEngine = new RuleEngine();
        ruleEngine.initEngine();
        KieSession kieSession = ruleEngine.getKieSession();

        //初始化全局变量messageGlobal
        kieSession.setGlobal("peopleGlobal",new ArrayList<People>());
        People people1 = new People("Lan", 12, 123);
        People people2 = new People("Chun", 22, 456);
        People people3 = new People("Qian", 32, 789);

        Message message = new Message("Hello World", Message.HELLO);

        //将实体类插入执行规则
        kieSession.insert(message);
        kieSession.insert(people1);
        kieSession.insert(people2);
        kieSession.insert(people3);

        kieSession.fireAllRules();

        System.out.println("\nfinal message" + message.getMessage());
        System.out.println();

        //query的使用
        QueryResults results = kieSession.getQueryResults( "people over the age of x", new Object[] {20, 456});
        for ( QueryResultsRow row : results ) {
            People person = (People) row.get( "people" );
            System.out.println("query people over the age of 20 " +  person);
        }
        System.out.println();

        //输出全局变量
        ArrayList<People> finalPeopleGlobal = (ArrayList<People>)kieSession.getGlobal("peopleGlobal");
        for(int i = 0;i < finalPeopleGlobal.size();i ++){
            System.out.println("Age > 20 " + finalPeopleGlobal.get(i));
        }
    }
}
