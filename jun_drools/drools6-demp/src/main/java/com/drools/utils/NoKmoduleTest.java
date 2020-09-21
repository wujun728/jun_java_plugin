package com.drools.utils;

import com.drools.entity.People;
import org.drools.io.ResourceFactory;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by LANCHUNQIAN on 2016/12/5.
 */
public class NoKmoduleTest {
    public static void main(String[] args) {
        try{
            ObjectDataCompiler converter = new ObjectDataCompiler(); //赋值 给模板属性
            Collection<People> cfl = new ArrayList<People>();
            cfl.add(new People("张三",10,12345));
            cfl.add(new People("李四",20,234567));
            InputStream dis = ResourceFactory.newClassPathResource("E:\\Projects\\Drools\\drools6-demo\\src\\main\\resources\\org\\drools\\rules.drl", NoKmoduleTest.class).getInputStream();
            String drl = converter.compile(cfl, dis);

            KieHelper helper = new KieHelper();
            helper.addContent(drl, ResourceType.DRL);
            KieSession ksession = helper.build().newKieSession();

            People p = new People();
            p.setAge(20);
            ksession.insert(p);

            int i = ksession.fireAllRules();
            System.out.println(p.getName() + "    " + i + "次");
            ksession.dispose();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
