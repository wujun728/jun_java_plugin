package dom4j;

import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;

public class BuildXml {
	public static void main(String[] args) {
		BuildXml xml = new BuildXml();
		String fileName = "src/student.xml";
		String[] studentName = { "", "ľ", "", "", "" };
		String[] courseName = { "", "", "", "ֻ" };
		xml.buildXml
    (fileName, studentName, courseName);
	}
	public void buildXml(String fileName, 
      String[] studentName,
			String[] courseName) {
		// doc
		Document doc = 
      DocumentHelper.createDocument();
		// xmlĵRecord
		Element recordElement = 
      doc.addElement("Record");
    
		// ΪRecordһHeadڵ
		Element headElement = 
      recordElement.addElement("Head");
		// ΪRecordһbodyڵ
		Element bodyElement = 
      recordElement.addElement("Body");
    
		// ΪHeadڵһЩӽڵ
		Element codeEl = 
      headElement.addElement("Code");
		codeEl.setText("SD1101");
		Element examEl = 
      headElement.addElement("Exam");
		examEl.setText("");
		// ñķӽڵ
		addParamList
    (bodyElement, courseName, studentName); 
		// ʽxmlĵ
		try {
			FileWriter fileWriter = 
        new FileWriter(fileName);
			// ˴xmlļĸʽΪ
			OutputFormat xmlFormat = 
        OutputFormat.createPrettyPrint();
			// ļʽ
			xmlFormat.setEncoding("gbk");
			// дļ,ļ,ʽ
			XMLWriter xmlWriter = 
        new XMLWriter(fileWriter, xmlFormat);
			// docĵдļ
			xmlWriter.write(doc);
			// ر
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addParamList(
      Element bodyEl, String[] courseName,
			String[] studentName) {
		/** жֿγ̾Ͳٸ */
		for (int i = 0; i < courseName.length;
    i++) {
			Element courseList = 
        bodyEl.addElement("CourseList");
			Element sheehEl = 
        courseList.addElement("CourseCode");
			sheehEl.setText(courseName[i]);
			/** ÿѧѡȫγ */
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



