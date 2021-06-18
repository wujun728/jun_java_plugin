package com.us.drools.service;

import com.us.drools.bean.Message;
import com.us.drools.bean.ResourceWrapper;
import com.us.drools.bean.Rules;
import com.us.drools.dao.RulesDao;
import com.us.drools.util.DroolsUtils;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 16/12/9.
 */
@Service
public class RulesService {
    @Autowired
    private RulesDao rulesDao;

    //废弃
    public String getRules(Integer id) {

        String rules = "";
        Rules ru = rulesDao.getById(id);
        if (ru != null && ru.getRules() != null) {
            rules = ru.getRules();
        }
        String RULESFILE_NAME = "rules.drl";
        KieServices kieServices = KieServices.Factory.get();

        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com", "us", "1.0.0");

        // 创建初始化的kjar
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                new ResourceWrapper(ResourceFactory.newByteArrayResource(rules.getBytes()), RULESFILE_NAME));
        KieRepository repository = kieServices.getRepository();
        repository.addKieModule(kJar);
        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        KieSession session = kieContainer.newKieSession();
        Message message = new Message();
        message.setStatus("0");
        //同一个fact第一次不命中
        try {
            session.insert(message);
            session.fireAllRules();
        } catch (Exception e) {
        } finally {
            session.dispose();
        }
        System.out.println("-----first fire end-------");
        return "ok";
    }

    public String getRulesWrite(Integer id) {
        String rules = "";
        Rules ru = rulesDao.getById(id);
        if (ru != null && ru.getRules() != null) {
            rules = ru.getRules();
        }

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/rules.drl", rules.getBytes());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();
        KieSession ksession = kieBase.newKieSession();

        Message message = new Message();
        message.setStatus("0");
        ksession.insert(message);
        ksession.fireAllRules();
        return "ok";
    }
}
