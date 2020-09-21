package com.drools.utils;

import com.drools.entity.People;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LANCHUNQIAN on 2016/12/5.
 */
public class TemplateTest {
    public static void main(String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("TemplatesKS");
        People p = new People();
        p.setAge(42);
        kieSession.insert(p);
        List<String> list = new ArrayList<String>();
        kieSession.setGlobal("list",list);
        int i = kieSession.fireAllRules();
        System.out.println(list + "  " + i + "次");
        kieSession.dispose();
    }
}
