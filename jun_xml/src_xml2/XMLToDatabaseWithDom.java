

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




/**
 * 讲XML文件导入到数据库
 * @author licheng
 *
 */
public class XMLToDatabaseWithDom {
	
	
	
	
	/**
	 * 讲 学生XML文件导入到数据库中
	 */
	public void toDatabase(){
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		connection=JDBCUtilSingle.getInitJDBCUtil().getConnection();
		String sql="INSERT INTO `xmlanddb`.`student` (`number`, `name`, `date`, `height`) VALUES (?,?,?,?);";
		try {	
			statement=connection.prepareStatement(sql);
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		    factory.setIgnoringElementContentWhitespace(true);  //忽略空白缩进
			DocumentBuilder domParser=factory.newDocumentBuilder();
			Document document=domParser.parse(new File("student2.xml")); //通过已经存在的文件创建Document对象
			Element root=document.getDocumentElement();
			NodeList list1=root.getElementsByTagName("学号");
			NodeList list2=root.getElementsByTagName("姓名");
			NodeList list3=root.getElementsByTagName("出生日期");
			NodeList list4=root.getElementsByTagName("身高");
			int size=list1.getLength(); //获取长度
			String[] number=new String[4];
			String[] name=new String[4];
			String[] date=new String[4];
			double[] height=new double[4];
			for(int k=0;k<size;k++){
				Node numberNode=list1.item(k);
				Node nameNode=list2.item(k);
				Node dateNode=list3.item(k);
				Node heightNode=list4.item(k);
				number[k]=numberNode.getTextContent().trim();
				name[k]=nameNode.getTextContent().trim();
				date[k]=dateNode.getTextContent().trim();
				height[k]=Double.parseDouble(heightNode.getTextContent().trim());
				statement.setString(1, number[k]);
				statement.setString(2, name[k]);
				statement.setString(3, date[k]);
				statement.setDouble(4, height[k]);
				statement.executeUpdate();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			JDBCUtilSingle.getInitJDBCUtil().closeConnection(rs, statement, connection);
		}
	}
	
	
	
	/**
	 * 读取数据库中的数据
	 */
	public void getItemFromDatabase(){
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		connection=JDBCUtilSingle.getInitJDBCUtil().getConnection();
		String sql="SELECT * FROM `student` ";
		try {
			statement=connection.prepareStatement(sql);
			rs=statement.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getDouble(4));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static void main(String[] args){
			//new XMLToDatabase().toDatabase();
			new XMLToDatabaseWithDom().getItemFromDatabase();
	}	
}
