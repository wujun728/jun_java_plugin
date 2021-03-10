package com.us.drools.util;

/**
 * Created by yangyibo on 16/12/8.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.us.drools.bean.ResourceWrapper;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.internal.io.ResourceFactory;

/**
 * 动态生成kjar工具类
 * @author Wujun
 * @version id: DroolsUtils, v 0.1 16/10/26 下午1:58 caicongyang1 Exp $$
 */
public class DroolsUtils {

    /**
     * 默认规则文件所在路径
     */
    private static final String RULES_PATH = "rules";

    /**
     * 获取规定目录下的规则文件
     *
     * @return
     * @throws IOException
     */
    private static List<File> getRuleFiles() throws IOException {
        List<File> list = new ArrayList<File>();
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File rootDir = new File(filePath);
        File[] files = rootDir.listFiles();
        for (File itemFile : files) {
            if (itemFile.isDirectory() && itemFile.getName().equals(RULES_PATH)) {
                for (File f : itemFile.listFiles()) {
                    if (f.getName().endsWith(".drl")) {
                        list.add(f);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 初始化一个kjar：把原有的drl包含进新建的kjar中
     *
     * @param ks
     * @param releaseId
     * @return
     * @throws IOException
     */
    public static InternalKieModule initKieJar(KieServices ks, ReleaseId releaseId) throws IOException {
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
        kfs.writePomXML(getPom(releaseId));
        for (File file : getRuleFiles()) {
            kfs.write("src/main/resources/" + file.getName(),
                    ResourceFactory.newClassPathResource(RULES_PATH + File.separator + file.getName(), "UTF-8"));
        }
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        if (!kieBuilder.buildAll().getResults().getMessages().isEmpty()) {
            throw new IllegalStateException("Error creating KieBuilder.");
        }
        return (InternalKieModule) kieBuilder.getKieModule();
    }

    public static InternalKieModule createKieJar(KieServices ks, ReleaseId releaseId, ResourceWrapper resourceWrapper) {
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
        kfs.writePomXML(getPom(releaseId));
        kfs.write("src/main/resources/" + resourceWrapper.getTargetResourceName(), resourceWrapper.getResource());
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        if (!kieBuilder.getResults().getMessages().isEmpty()) {
            System.out.println(kieBuilder.getResults().getMessages());
            throw new IllegalStateException("Error creating KieBuilder.");
        }
        return (InternalKieModule) kieBuilder.getKieModule();
    }

    /**
     * 创建默认的kbase和stateful的kiesession
     *
     * @param ks
     * @param isdefault
     * @return
     */
    public static KieFileSystem createKieFileSystemWithKProject(KieServices ks, boolean isdefault) {
        KieModuleModel kproj = ks.newKieModuleModel();
        KieBaseModel kieBaseModel1 = kproj.newKieBaseModel("KBase").setDefault(isdefault)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY).setEventProcessingMode(EventProcessingOption.STREAM);
        // Configure the KieSession.
        kieBaseModel1.newKieSessionModel("KSession").setDefault(isdefault)
                .setType(KieSessionModel.KieSessionType.STATEFUL);
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(kproj.toXML());
        return kfs;
    }

    /**
     * 创建kjar的pom
     *
     * @param releaseId
     * @param dependencies
     * @return
     */
    public static String getPom(ReleaseId releaseId, ReleaseId... dependencies) {
        String pom = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
                + "  <modelVersion>4.0.0</modelVersion>\n" + "\n" + "  <groupId>" + releaseId.getGroupId()
                + "</groupId>\n" + "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n" + "  <version>"
                + releaseId.getVersion() + "</version>\n" + "\n";
        if (dependencies != null && dependencies.length > 0) {
            pom += "<dependencies>\n";
            for (ReleaseId dep : dependencies) {
                pom += "<dependency>\n";
                pom += "  <groupId>" + dep.getGroupId() + "</groupId>\n";
                pom += "  <artifactId>" + dep.getArtifactId() + "</artifactId>\n";
                pom += "  <version>" + dep.getVersion() + "</version>\n";
                pom += "</dependency>\n";
            }
            pom += "</dependencies>\n";
        }
        pom += "</project>";
        return pom;
    }
}