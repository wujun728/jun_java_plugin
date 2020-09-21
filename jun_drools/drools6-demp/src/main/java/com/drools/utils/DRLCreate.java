package com.drools.utils;

import com.google.common.io.Resources;
import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LANCHUNQIAN on 2016/10/18.
 */
public class DRLCreate {

    public KieContainer build(KieServices kieServices) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ReleaseId rid = kieServices.newReleaseId("com.example.rulesengine", "model-test", "1.0-SNAPSHOT");
        kieFileSystem.generateAndWritePomXML(rid);

        kieFileSystem.write("src/main/resources/rules.drl", getResource(kieServices, "rules.drl"));

        addRule(kieFileSystem);

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" +
                    kieBuilder.getResults().toString());
        }
        return kieServices.newKieContainer(rid);
    }

    private void addRule(KieFileSystem kieFileSystem) {
        PackageDescrBuilder packageDescrBuilder = DescrFactory.newPackage();
        packageDescrBuilder
                .name("com.example.model")
                .newRule()
                .name("Is of valid age")
                .lhs()
                .pattern("Person").constraint("age < 18").end()
                .pattern().id("$a", false).type("Action").end()
                .end()
                .rhs("$a.showBanner( false );")
                .end();

        String rules = new DrlDumper().dump(packageDescrBuilder.getDescr());
        kieFileSystem.write("src/main/resources/rule-1.drl", rules);
    }

    private Resource getResource(KieServices kieServices, String resourcePath) {
        try {
            InputStream is = Resources.getResource(resourcePath).openStream(); //guava
            return kieServices.getResources()
                    .newInputStreamResource(is)
                    .setResourceType(ResourceType.DRL);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load drools resource file.", e);
        }
    }
}
