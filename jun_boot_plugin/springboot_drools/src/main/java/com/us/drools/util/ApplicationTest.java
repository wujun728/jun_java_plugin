package com.us.drools.util;

/**
 * Created by yangyibo on 16/12/8.
 */

import com.us.drools.bean.Message;
import com.us.drools.bean.ResourceWrapper;
import com.us.drools.util.DroolsUtils;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;



public class ApplicationTest {
    private static final String RULESFILE_NAME = "rules.drl";

    /**
     * 规则文件内容（可以从数据库中加载）
     */
    private static final String rules = "package com.us.drools; import com.us.drools.bean.Message; rule \"Hello World \" when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";

    public static void main(String[] args) throws Exception {

        KieServices kieServices = KieServices.Factory.get();

        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com", "us", "1.0.0");

        // 创建初始化的kjar
//        InternalKieModule kJar = DroolsUtils.initKieJar(kieServices, releaseId);
        InternalKieModule  kJar = DroolsUtils.createKieJar(kieServices, releaseId,
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

//        //新增一个规则文件
//          kJar = DroolsUtils.createKieJar(kieServices, releaseId,
//                new ResourceWrapper(ResourceFactory.newByteArrayResource(rules.getBytes()), RULESFILE_NAME));
//        repository.addKieModule(kJar);
//        kieContainer.updateToVersion(releaseId);
//
//        //同一个fact再次过滤规则：命中
//          session = kieContainer.newKieSession();
//        try {
//            session.insert(message);
//            session.fireAllRules();
//        } catch (Exception e) {
//        } finally {
//            session.dispose();
//        }
//        System.out.println("-----senond fire end-------");

    }

}