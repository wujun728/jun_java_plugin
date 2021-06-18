

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;



public class DatabaseToXMLWithDom {
	
	
	
	public void toXML(){
		
		
		
		/**
		 * 讲数据记录存入到数组中
		 */
		String[] number={""};
		String[] name={""};
		String[] date={""};
		String[] height={""};
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		connection=JDBCUtilSingle.getInitJDBCUtil().getConnection();
		String sql="SELECT * FROM `student` ";
		try {
			statement=connection.prepareStatement(sql);
			rs=statement.executeQuery();
			rs.last(); //讲游标移到结果集的最后一行
			int recordAmount=rs.getRow(); //获取记录数据
			number=new String[recordAmount];
			name=new String[recordAmount];
			date=new String[recordAmount];
			height=new String[recordAmount];
			int k=0;
			rs.beforeFirst(); //讲游标移到第一条记录前
			while(rs.next()){
				number[k]=rs.getString(1);
				name[k]=rs.getString(2);
				date[k]=rs.getString(3);
				height[k]=String.valueOf(rs.getDouble(4));
				k++;
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCUtilSingle.getInitJDBCUtil().closeConnection(rs, statement, connection);
		}
		
		
		
		/**
		 * 将数据导入到XML文件
		 */
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	    factory.setIgnoringElementContentWhitespace(true);  //忽略空白缩进
		DocumentBuilder domParser;
		try {
			domParser = factory.newDocumentBuilder();
			Document document=domParser.newDocument(); //通过调用newDocument() 方法获取实例
			document.setXmlVersion("1.0"); //设置 xml版本号
			Element root=document.createElement("学生信息");
			document.appendChild(root);
			for(int k=0;k<name.length;k++){
				Node student=document.createElement("学生");
				root.appendChild(student);
				
					Node xuehao=document.createElement("学号");
					xuehao.appendChild(document.createTextNode(number[k]));
					
					Node xingming=document.createElement("姓名");
					xingming.appendChild(document.createTextNode(name[k]));
					
					Node chusheng=document.createElement("出生日期");
					chusheng.appendChild(document.createTextNode(date[k]));
					
					Node shenggao=document.createElement("身高");
					shenggao.appendChild(document.createTextNode(height[k]));
					
					student.appendChild(xuehao);
					student.appendChild(xingming);
					student.appendChild(chusheng);
					student.appendChild(shenggao);
					
					
					
					TransformerFactory transFactory=TransformerFactory.newInstance(); //工厂对象获取transFactory实例
					Transformer transformer=transFactory.newTransformer(); //获取Transformer实例
					DOMSource domSource=new DOMSource(document);
					File file=new File("newXML2.xml");
					FileOutputStream out=new FileOutputStream(file);
					StreamResult xmlResult=new StreamResult(out);
					transformer.transform(domSource, xmlResult);
					out.close();
					
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DatabaseToXMLWithDom().toXML();
	}

}
