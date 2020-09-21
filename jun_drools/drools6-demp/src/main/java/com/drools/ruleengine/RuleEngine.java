package com.drools.ruleengine;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by LANCHUNQIAN on 2016/12/5.
 */
public class RuleEngine {
    private KieSession kieSession;

    public void initEngine() {
        // 设置时间格式
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");

        //从工厂中获得KieServices实例
        KieServices kieServices = KieServices.Factory.get();
        //从KieServices中获得KieContainer实例，其会加载kmodule.xml文件并load规则文件
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        //建立KieSession到规则文件的通信管道
        kieSession = kieContainer.newKieSession("ksession-rules");
    }

    public KieSession getKieSession(){
        return kieSession;
    }

    public void executeRuleEngine(Object object) {
        if(null == kieSession) {
            return;
        }
        kieSession.insert(object);

        // fire
        kieSession.fireAllRules();
    }
}
