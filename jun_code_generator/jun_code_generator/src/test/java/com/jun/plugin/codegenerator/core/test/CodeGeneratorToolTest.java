package com.jun.plugin.codegenerator.core.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jun.plugin.code.generator.model.ClassInfo;
import com.jun.plugin.code.generator.util.CodeGeneratorTool;
import com.jun.plugin.code.generator.util.FreemarkerTool;

import freemarker.template.TemplateException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeGeneratorToolTest {
	
	@Resource
    private FreemarkerTool freemarkerTool;

    @Test
    public void codeGeneratorToolTest() throws IOException, TemplateException  {

        // table sql
        FileInputStream fileInputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("table.sql").getPath());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        StringBuffer stringBuffer = new StringBuffer();
        String lineTmp = null;
        while ((lineTmp = bufferedReader.readLine()) != null) {
            stringBuffer.append(lineTmp);
        }
        bufferedReader.close();

        String tableSql = stringBuffer.toString();

        // code generate
        ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("classInfo", classInfo);
        
        Map<String, String> result = new HashMap<String, String>();
        String template_path = "template_v1";
        result.put("controller_code", freemarkerTool.processString(template_path+"/crud/controller.ftl", params));

    }

}
