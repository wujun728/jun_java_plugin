

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerTest{
	
	static String dir = "src/main/resources/templates";
	
	@Test
	public void testStringFreemarker01() throws Exception{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		Template template = cfg.getTemplate("test01.ftl");
		Map root = new HashMap();
		root.put("strvalue", "test111");
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test01_out.txt")
				)
			);
		
		template.process(root, out);
		
	}
	
	@Test
	public void testMapFreemarker02() throws Exception{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		Template template = cfg.getTemplate("test02.ftl");
		Map root = new HashMap();
		root.put("strvalue", "val-map-123");
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test02_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
	
	@Test
	public void testListFreemarker03() throws Exception{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		Template template = cfg.getTemplate("test03.ftl");
		Map root = new HashMap();
		List list = new ArrayList();
		for(int i=0; i<10; i++){
			list.add("listvalue"+i);
		}
		root.put("listvalue", list);
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test03_out.txt")
				)
			);
		
		template.process(root, out);
	}
	
	@Test
	public void testFreemarker04() throws Exception{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		Template template = cfg.getTemplate("test04.ftl");
		Map root = new HashMap();
		root.put("name", "names");
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test04_out.txt")
				)
			);
		
		template.process(root, out);
	}
	
	@Test
	public void testFreemarker05() throws Exception{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(dir));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		cfg.addAutoImport("my", "common.ftl");
		Template template = cfg.getTemplate("test05.ftl");
		Map root = new HashMap();
		root.put("name", "names123");
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test05_out.txt")
				)
			);
		
		template.process(root, out);
		
	}
}
