package com.jun.plugin.freemarker;

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
	
	static String dir = "D:/workspace/workspace_jun_eclipse/jun_base/src_freemarker/com/jun/freemarker/";
	@Test
	public void testFreemarker01() throws Exception{
		
		
		Configuration cfg = new Configuration();
		
		//��ʲô�ط�����ģ���ļ�
		cfg.setDirectoryForTemplateLoading(new File(dir));
		
		//����ģ��
		Template template = cfg.getTemplate("test01.ftl");
		
		//��������
		Map root = new HashMap();
		root.put("strvalue", "�������");
		
		//�������
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test01_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
	
	//���ڿ�ֵ���������
	public void testFreemarker02() throws Exception{
		
//		String dir = "D:\\share\\JavaProjects\\oa\\freemarker\\src\\com\\bjsxt\\freemarker";
		
		Configuration cfg = new Configuration();
		
		//��ʲô�ط�����ģ���ļ�
		cfg.setDirectoryForTemplateLoading(new File(dir));
		
		//�����쳣
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		
		//����ģ��
		Template template = cfg.getTemplate("test02.ftl");
		
		//��������
		Map root = new HashMap();
		//root.put("strvalue", "�������");
		
		//�������
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test02_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
	
	//����������
	public void testFreemarker03() throws Exception{
		
//		String dir = "D:\\share\\JavaProjects\\oa\\freemarker\\src\\com\\bjsxt\\freemarker";
		
		Configuration cfg = new Configuration();
		
		//��ʲô�ط�����ģ���ļ�
		cfg.setDirectoryForTemplateLoading(new File(dir));
		
		//�����쳣
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		
		//����ģ��
		Template template = cfg.getTemplate("test03.ftl");
		
		//��������
		Map root = new HashMap();
		
		List list = new ArrayList();
		for(int i=0; i<10; i++){
			list.add("listvalue"+i);
		}
		root.put("listvalue", list);
		
		//�������
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test03_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
	
	//freemarker �궨��
	public void testFreemarker04() throws Exception{
		
//		String dir = "D:\\share\\JavaProjects\\oa\\freemarker\\src\\com\\bjsxt\\freemarker";
		
		Configuration cfg = new Configuration();
		
		//��ʲô�ط�����ģ���ļ�
		cfg.setDirectoryForTemplateLoading(new File(dir));
		
		//�����쳣
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		
		//����ģ��
		Template template = cfg.getTemplate("test04.ftl");
		
		//��������
		Map root = new HashMap();
		root.put("name", "����");
		
		//�������
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test04_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
	
	//freemarker auto-import���Ե���ʾ
	public void testFreemarker05() throws Exception{
		
//		String dir = "D:\\share\\JavaProjects\\oa\\freemarker\\src\\com\\bjsxt\\freemarker";
		
		Configuration cfg = new Configuration();
		
		//��ʲô�ط�����ģ���ļ�
		cfg.setDirectoryForTemplateLoading(new File(dir));
		
		//�����쳣
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		
		//�Զ���������ԣ��Զ�import������ģ���ļ�
		cfg.addAutoImport("my", "common.ftl");
		
		//����ģ��
		Template template = cfg.getTemplate("test05.ftl");
		
		//��������
		Map root = new HashMap();
		root.put("name", "����");
		
		//�������
		PrintWriter out 
			= new PrintWriter(
				new BufferedWriter(
					new FileWriter(dir+"\\test05_out.txt")
				)
			);
		
		//����ģ�壬�����
		template.process(root, out);
		
	}
}
