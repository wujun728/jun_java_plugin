package com.jun.plugin.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Example {

	private static String FILE_DIR = "c:\\sample\\freemarker\\";

	public static void main(String[] args) {
		
		File f = new File(FILE_DIR);
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		
		Example t = new Example();
		
		//计算式
		t.process("T101.ftl");

		//输出一个值
		HashMap t2root = new HashMap<String, String>();
		t2root.put("user", "RenSanNing");
		t.process("T102.ftl", t2root);

		//输出一个列表
		Map<String, Object> t3root = new HashMap<String, Object>();
        List<Food> menu = new ArrayList<Food>();
        menu.add(new Food("iText in Action", 98));
        menu.add(new Food("iBATIS in Action", 118));
        menu.add(new Food("Lucene in Action", 69));
        t3root.put("menu", menu);
		t.process("T103.ftl", t3root);

		//逻辑判断（IF,SWITCH)
		Map<String, Object> t4root = new HashMap<String, Object>();
		t4root.put("x", 2);
		t4root.put("y", "medium");
		t.process("T104.ftl", t4root);
		
		//自定义函数
		t.process("T105.ftl");
		
		//定义变量
		t.process("T106.ftl");  

		//定义宏macro
		t.process("T107.ftl");
		
		//include
		t.process("T108.ftl");

		//名字空间
		t.process("T109.ftl");
		
		//自定义指令Directive
		Map<String, Object> t10root = new HashMap<String, Object>();
		t10root.put("systemdate", new SystemDateDirective());
		t10root.put("text_cut", new TextCutDirective());
		t.process("T110.ftl", t10root);
		
	}

	public void process(String template, Map<String, ?> data){
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File("ftl"));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			
			//设置字符集
			cfg.setDefaultEncoding("UTF-8");
			
			//设置尖括号语法和方括号语法,默认是自动检测语法
			//  自动 AUTO_DETECT_TAG_SYNTAX
			//  尖括号 ANGLE_BRACKET_TAG_SYNTAX
			//  方括号 SQUARE_BRACKET_TAG_SYNTAX
			cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
	
			Writer out = new OutputStreamWriter(new FileOutputStream(FILE_DIR + template + ".txt"),"UTF-8");
			Template temp = cfg.getTemplate(template);
			temp.process(data, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void process(String template) {
		process(template, null);
	}

}
