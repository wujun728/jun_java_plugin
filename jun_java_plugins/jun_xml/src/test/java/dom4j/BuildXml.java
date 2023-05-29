package dom4j;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class BuildXml {
	public static void main(String[] args) {
		BuildXml xml = new BuildXml();
		String fileName = "src/student.xml";
		String[] studentName = { "张三丰", "花木兰", "郭靖", "任我行", "赵敏" };
		String[] courseName = { "射箭", "骑马", "烹饪", "种花" };
		xml.buildXml
    (fileName, studentName, courseName);
	}
	public void buildXml(String fileName, 
      String[] studentName,
			String[] courseName) {
		// 建立doc对象
		Document doc = 
      DocumentHelper.createDocument();
		// 建立xml文档的Record根对象
		Element recordElement = 
      doc.addElement("Record");
    
		// 为Record根建立一个Head节点
		Element headElement = 
      recordElement.addElement("Head");
		// 为Record根建立一个body节点
		Element bodyElement = 
      recordElement.addElement("Body");
    
		// 为Head节点添加一些子节点
		Element codeEl = 
      headElement.addElement("Code");
		codeEl.setText("SD1101");
		Element examEl = 
      headElement.addElement("Exam");
		examEl.setText("是");
		// 调用本类的方法，增加子节点
		addParamList
    (bodyElement, courseName, studentName); 
		// 格式化输出xml文档，并解决中文问题
		try {
			FileWriter fileWriter = 
        new FileWriter(fileName);
			// 设置了创建xml文件的格式为缩进的
			OutputFormat xmlFormat = 
        OutputFormat.createPrettyPrint();
			// 设置文件编码格式
			xmlFormat.setEncoding("gbk");
			// 创建写文件,输入参数是文件,格式
			XMLWriter xmlWriter = 
        new XMLWriter(fileWriter, xmlFormat);
			// 将doc文档写入文件
			xmlWriter.write(doc);
			// 关闭
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addParamList(
      Element bodyEl, String[] courseName,
			String[] studentName) {
		/** 有多少种课程就产生多少个对象 */
		for (int i = 0; i < courseName.length;
    i++) {
			Element courseList = 
        bodyEl.addElement("CourseList");
			Element sheehEl = 
        courseList.addElement("CourseCode");
			sheehEl.setText(courseName[i]);
			/** 假设每个学生选修全部课程 */
			addItem(studentName, courseList);
		}
	}

	private void addItem(
      String[] studentName, 
      Element courseList) {
		Element studentEl = 
      courseList.addElement("Student");
		for (int i = 0; i < studentName.length;
    i++) {
			Element studentNameEl = 
        studentEl.addElement("StudentName");
			studentNameEl.setText(studentName[i]);

			studentNameEl.
      addAttribute("score", 
          new Random().nextInt(100) + "");
		}
	}
}



