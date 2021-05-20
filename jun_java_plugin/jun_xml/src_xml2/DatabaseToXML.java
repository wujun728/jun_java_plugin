import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class DatabaseToXML {

	
	public static void toXML(){
		
		
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		connection=JDBCUtilSingle.getInitJDBCUtil().getConnection();
		String sql="SELECT * FROM  `dict`";
		
		
	     Document document = DocumentHelper.createDocument();      
         Element root = document.addElement("dict");// 创建根节点     
         
		
		try {
			statement=connection.prepareStatement(sql);
			rs=statement.executeQuery();
			while(rs.next()){
				
				Element word = root.addElement("word");  
					Element name = word.addElement("name");
					name.setText(rs.getString("word"));
					Element mean=word.addElement("mean");  
						mean.addCDATA(rs.getString("meaning"));
					Element lx=word.addElement("lx");
						lx.addCDATA(rs.getString("lx"));
						

			}
			XMLWriter writer=new XMLWriter(new FileWriter(new File("dict2.xml"))); 
			writer.write(document);
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		toXML();
	}

}
