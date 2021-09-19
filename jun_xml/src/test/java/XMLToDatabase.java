import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;


public class XMLToDatabase {

	
	
	
		
		
	
	public static void toDatabase(){
		
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		connection=JDBCUtilSingle.getInitJDBCUtil().getConnection();
		String sql="INSERT INTO `xmlanddb`.`dict` (`id`, `word`, `meaning`, `lx`) VALUES (NULL, ?, ?, ?);";
		try {
			statement=connection.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Document document=Dom4j.load("dict.xml");
		Element root=Dom4j.getRootElement(document);
		List words = root.elements("word"); //word节点列表      
		String wordStr="",meaningStr="",lxStr="";
		for (Iterator i = words.iterator(); i.hasNext();) {  
			
				    Element word= (Element) i.next(); //单个word节点
				    
				    for(Iterator k=word.elementIterator();k.hasNext();){  //遍历 name mean lx节点     
		                Element element = (Element) k.next();     
		                if(element.getName().equals("name")){
		                	wordStr=element.getText();
		                	
		                }
		                if(element.getName().equals("mean")){
		                	meaningStr=element.getText();
		                }
		                if(element.getName().equals("lx")){
		                	lxStr=element.getText();
		                }      
		            } 
				 
					try {
					
						statement.setString(1,wordStr);
						statement.setString(2,meaningStr);
						statement.setString(3,lxStr);
						statement.executeUpdate();
					    
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
		   
		}      
		JDBCUtilSingle.getInitJDBCUtil().closeConnection(rs, statement, connection);
	}
	
	
	public static void main(String[] args){
		toDatabase();
	}
	
	
	
	
}
