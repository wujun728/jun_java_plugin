package com.java.plugin.xml.rw.dao;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.java.plugin.xml.rw.domain.Student;
import com.java.plugin.xml.rw.util.Dom4jUtil;

public class StudentDao {
	
	public boolean addStudent(Student stu){
		boolean flag = false;
		try {
			//1.�õ�Document����
			Document document = Dom4jUtil.getDocument();
			//2.����µ�student���
			Element root = document.getRootElement();
			Element studentEle = root.addElement("student").addAttribute("idcard", stu.getIdcard())
				.addAttribute("examid", stu.getExamid());
			//3.��stuEle������ӽ��
			studentEle.addElement("name").setText(stu.getName());
			studentEle.addElement("location").setText(stu.getLocation());
			studentEle.addElement("grade").setText(stu.getGrade()+"");
			
			
			//4.д��
			Dom4jUtil.write2Xml(document);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		
	}

	public Student findStudentByExamid(String examid){
		Student stu = null;
		try {
			Document document = Dom4jUtil.getDocument();
			Node stuNode = document.selectSingleNode("//student[@examid='"+examid+"']");
			if(stuNode!=null){
				Element ele= (Element)stuNode;
				stu = new Student();
				stu.setIdcard(ele.attributeValue("idcard"));
				stu.setExamid(examid);
				stu.setName(ele.elementText("name"));
				stu.setLocation(ele.elementText("location"));
				stu.setGrade(Double.parseDouble(ele.elementText("grade")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stu;
	}

	public void deleteStudentByName(String name){
		Document document;
		try {
			document = Dom4jUtil.getDocument();
			List<Node> list = document.selectNodes("//student/name");
			for(Node node:list){
				if(node instanceof Element){
					Element ele = (Element)node;
					if(ele.getText().equals(name)){
						Element examNode = ele.getParent().getParent();
						examNode.remove(ele.getParent());
						break;
					}
				}
			}
			
			Dom4jUtil.write2Xml(document);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
