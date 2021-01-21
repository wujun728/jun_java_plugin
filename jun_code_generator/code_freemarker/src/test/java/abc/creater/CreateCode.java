package abc.creater;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.jdbc.core.JdbcTemplate;

public class CreateCode {
	
	  public String tableName;
	  public String modelName;
	  public String packageName;
	  public String modelURL;
	  public String modelsql;
	  public String daoURL;
	  public String daoImplURL;
	  public String serviceURL;
	  public String serviceImplURL;
	  public String serviceManagerURL;
	  public String controllerURL;
	  public String utilURL;
	  public String xmlMybatis;
	  public String xmlSpring;
	  public String xmlWeb;
	  public String MySQLDataBaseDrive;
	  public String MySQLDataBaseConnectStr;
	  public String MySQLDataBaseName;
	  public String MySQLDataBasePassword;

	  public String DataBaseDrive;
	  public String DataBaseConnectStr;
	  public String DataBaseName;
	  public String DataBasePassword;

	  public CreateCode(){}

	  public CreateCode(String tableName,String modelName, String packageName, String modelURL,String modelsql, String daoURL, String daoImplURL, String serviceURL, String serviceImplURL, String serviceManagerURL, String controllerURL,String utilURL, String mySQLDataBaseDrive, String mySQLDataBaseConnectStr, 
			  String mySQLDataBaseName, String mySQLDataBasePassword, String dataBaseDrive, String dataBaseConnectStr, String dataBaseName, String dataBasePassword,String xmlMybatis,String xmlSpring,String xmlWeb)
	  {
	    this.tableName = tableName;
	    this.modelName = modelName;
	    this.packageName = packageName;
	    this.modelURL = modelURL;
	    this.modelsql = modelsql;
	    this.daoURL = daoURL;
	    this.daoImplURL = daoImplURL;
	    this.serviceURL = serviceURL;
	    this.serviceImplURL = serviceImplURL;
	    this.serviceManagerURL = serviceManagerURL;
	    this.controllerURL = controllerURL;
	    this.utilURL = utilURL;
	    this.xmlMybatis = xmlMybatis;
	    this.xmlSpring = xmlSpring;
	    this.xmlWeb = xmlWeb;
	    this.MySQLDataBaseDrive = mySQLDataBaseDrive;
	    this.MySQLDataBaseConnectStr = mySQLDataBaseConnectStr;
	    this.MySQLDataBaseName = mySQLDataBaseName;
	    this.MySQLDataBasePassword = mySQLDataBasePassword;
	    this.DataBaseDrive = dataBaseDrive;
	    this.DataBaseConnectStr = dataBaseConnectStr;
	    this.DataBaseName = dataBaseName;
	    this.DataBasePassword = dataBasePassword;
	    writeToFileOnce(modelPage(),utilURL,"Page.java");
	    writeToFileOnce(baseDao(),daoURL,"BaseDao.java");
	    writeToFileOnce(BaseController(),controllerURL,"BaseController.java");
	    writeToFileOnce(modelSql(),modelsql,modelName+".xml");
	    writeToFileOnce(springContextConfig(),xmlSpring,"springContext.xml");
	    writeToFileOnce(springDataSourceConfig(),xmlSpring,"springDataSource.xml");	    
	    writeToFile(this.webConfig(),xmlWeb,"web.xml");
	  }

//	  public List<Table> getAllFields() {
//	    List<Table> res = new SuperBeanHelper(Table.class).convert(getMySQLJDBCTemplate().queryForList("describe " + this.tableName));
//	    return res;
//	  }
//
//	  public List<OracleTable> getOracleAllFields() {
//	    List<OracleTable> res = new SuperBeanHelper(OracleTable.class).convert(getOracleJDBCTemplate().queryForList("select COLUMN_NAME,DATA_TYPE from user_tab_columns where Table_Name='" + this.tableName + "'"));
//	    return res;
//	  }

	  public String getPackage(List<Table> fieldList) {
	    String back = "";
	    for (Table t : fieldList) {
	      if (t.getType().equals("datetime")) {
	        back = "import java.util.Date;";
	        break;
	      }
	    }
	    return back;
	  }

	  public String getOraclePackage(List<OracleTable> fieldList) {
	    String back = "";
	    for (OracleTable t : fieldList) {
	      if (t.getDATA_TYPE().equals("DATE")) {
	        back = "import java.util.Date;";
	        break;
	      }
	    }
	    return back;
	  }	  

//	  public JdbcTemplate getMySQLJDBCTemplate()
//	  {
//	    BasicDataSource bds = new BasicDataSource();
//	    bds.setDriverClassName(this.MySQLDataBaseDrive);
//	    bds.setUrl(this.MySQLDataBaseConnectStr);
//	    bds.setUsername(this.MySQLDataBaseName);
//	    bds.setPassword(this.MySQLDataBasePassword);
//	    JdbcTemplate jdbc = new JdbcTemplate(bds);
//	    return jdbc;
//	  }
//
//	  public JdbcTemplate getOracleJDBCTemplate()
//	  {
//	    BasicDataSource bds = new BasicDataSource();
//	    bds.setDriverClassName(this.DataBaseDrive);
//	    bds.setUrl(this.DataBaseConnectStr);
//	    bds.setUsername(this.DataBaseName);
//	    bds.setPassword(this.DataBasePassword);
//	    JdbcTemplate jdbc = new JdbcTemplate(bds);
//	    return jdbc;
//	  }

	  public void writeToFile(String Text,String dir,String fileName) {
	  File fileDir = null;
	    File file = null;
	    FileWriter fw = null;
	    try {
	      dir=new File("").getAbsolutePath()+"\\"+dir;
	      fileName=dir+fileName;
	      fileDir = new File(dir);
	      file = new File(fileName);
	      if (!fileDir.exists()) fileDir.mkdirs();
	      fw = new FileWriter(file);
          fw.write(Text);
	    }
	    catch (IOException e1) {
	      e1.printStackTrace();
	      try
	      {
	        fw.close();
	      }
	      catch (IOException e2) {
	        e2.printStackTrace();
	      }
	    }
	    finally
	    {
	      try
	      {
	        fw.close();
	      }
	      catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }
	  
	  public void writeToFileOnce(String Text, String dir,String fileName) {
		    File fileDir = null;
		    File file = null;
		    FileWriter fw = null;
		    try {
		      dir=new File("").getAbsolutePath()+"\\"+dir;
		      fileName=dir+fileName;
		      fileDir = new File(dir);
		      file = new File(fileName);
		      if (!fileDir.exists())
		      {
		    	  fileDir.mkdirs();
		      } 
		      if(!file.exists())
		      {
		    	  fw = new FileWriter(file);
		    	  fw.write(Text);
		    	  fw.close();
		      }		      
		    }
		    catch (IOException e1) {
		      e1.printStackTrace();
		      try
		      {
		        fw.close();
		      }
		      catch (IOException e2) {
		        e2.printStackTrace();
		      }
		    }		    
		  }	  

	  public String modelPage() {
		    StringBuffer sb = new StringBuffer();
		    sb.append("package " + this.packageName + ".util;\n");
		    sb.append("public class Page {\n");
		    sb.append("\tpublic static int Length=15;\n");
		    sb.append("\tprivate int currentPage;\n");
		    sb.append("\tprivate int totalPage;\n");
		    sb.append("\tprivate int totalRecord;\n");
		    sb.append("\tpublic int getCurrentPage() {\n");
		    sb.append("\t\treturn currentPage;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic void setCurrentPage(int currentPage) {\n");
		    sb.append("\t\tthis.currentPage = currentPage;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic int getTotalPage() {\n");
		    sb.append("\t\treturn totalPage;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic void setTotalPage(int totalPage) {\n");
		    sb.append("\t\tthis.totalPage = totalPage;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic int getTotalRecord() {\n");
		    sb.append("\t\treturn totalRecord;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic void setTotalRecord(int totalRecord) {\n");
		    sb.append("\t\tthis.totalRecord = totalRecord;\n");
		    sb.append("\t}\n");
		    sb.append("}\n");
		    return sb.toString();
	  }
	  
	  public String mySQLTableToModel()
	  {
	    List<Table> fieldList = getAllFields();
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".model;\n");
	    sb.append(getPackage(fieldList) + "\n");	    
	    sb.append("public class " + this.modelName + "{\n");
	    for (Table t : fieldList) {
	      String type = t.getType().substring(0, 3).equals("int")? "int" : t.getType().substring(0, 4).equals("text")? "String" :t.getType().substring(0, 6).equals("bigint")? "long" :t.getType().substring(0, 7).equals("varchar") ? "String":t.getType().substring(0, 8).equals("datetime") ? "Date" : "String";
	      sb.append("\tprivate " + type + " " + t.getField() + ";\n");
	    }

	    for (Table t : fieldList) {
		      String type = t.getType().substring(0, 3).equals("int")? "int" : t.getType().substring(0, 4).equals("text")? "String" :t.getType().substring(0, 6).equals("bigint")? "long" :t.getType().substring(0, 7).equals("varchar") ? "String":t.getType().substring(0, 8).equals("datetime") ? "Date" : "String";
	      sb.append("\tpublic " + type + " get" + t.getField().substring(0,1).toUpperCase()+t.getField().substring(1) + "(){\n");
	      sb.append("\t   return " + t.getField() + ";\n");
	      sb.append("\t}\n");
	      sb.append("\tpublic void set" + t.getField().substring(0,1).toUpperCase()+t.getField().substring(1) + "(" + type + " " + t.getField() + "){\n");
	      sb.append("\t   this." + t.getField() + "=" + t.getField() + ";\n");
	      sb.append("\t}\n");
	    }
	    sb.append("}");
	    return sb.toString();
	  }

	  private List<Table> getAllFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public String oracleTableToModel() {
	    List<OracleTable> fieldList = getOracleAllFields();
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".model;\n");
	    sb.append(getOraclePackage(fieldList) + "\n");	    
	    sb.append("public class " + this.modelName + "{\n");
	    for (OracleTable t : fieldList) {
	      String type = 
	        t.getDATA_TYPE().equals("DATE") ? "Date" : t.getDATA_TYPE().equals("VARCHAR2") ? "String" : t.getDATA_TYPE().equals("NUMBER") ? "int" : "";
	      sb.append("\tprivate " + type + " " + t.getCOLUMN_NAME() + ";\n");
	    }

	    for (OracleTable t : fieldList) {
	      String type = 
	        t.getDATA_TYPE().equals("DATE") ? "Date" : t.getDATA_TYPE().equals("VARCHAR2") ? "String" : t.getDATA_TYPE().equals("NUMBER") ? "int" : "";
	      sb.append("\tpublic " + type + " get" + t.getCOLUMN_NAME() + "(){\n");
	      sb.append("\t   return " + t.getCOLUMN_NAME() + ";\n");
	      sb.append("\t}\n");
	      sb.append("\tpublic void set" + t.getCOLUMN_NAME() + "(" + type + " " + t.getCOLUMN_NAME() + "){\n");
	      sb.append("\t   this." + t.getCOLUMN_NAME() + "=" + t.getCOLUMN_NAME() + ";\n");
	      sb.append("\t}\n");
	    }
	    sb.append("}");
	    return sb.toString();
	  }
	  
	  private List<OracleTable> getOracleAllFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public String baseDao() {
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("package " + this.packageName + ".dao;\n");
		    sb.append("\timport org.apache.ibatis.session.SqlSession;\n");
		    sb.append("\timport org.springframework.beans.factory.annotation.Autowired;\n");
		    sb.append("\tpublic class BaseDao {\n");
		    sb.append("\t\t@Autowired\n");
		    sb.append("\t\tprotected SqlSession sqlSession;\n");
		    sb.append("\t\tpublic SqlSession getSqlSession() {\n");
		    sb.append("\t\t\treturn sqlSession;\n");
		    sb.append("\t\t}\n");
		    sb.append("\t\tpublic void setSqlSession(SqlSession sqlSession) {\n");
		    sb.append("\t\t\tthis.sqlSession = sqlSession;\n");
		    sb.append("\t\t}\n");
		    sb.append("\t}\n");
		    return sb.toString();
	  }

	  public String tableToDao() {
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".dao;\n");
	    sb.append("import java.util.List;\n");
	    sb.append("import java.util.Map;\n");
	    sb.append("import " + this.packageName + ".model." + this.modelName + ";\n");	    
	    sb.append("public interface " + this.modelName + "Dao{\n");
	    sb.append("\n");
	    sb.append("\tpublic " + this.modelName + " findById(int id);\n");
	    sb.append("\n");
	    sb.append("\tpublic List<" + this.modelName + "> findAllByFilter(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("\tpublic int findAllByFilterCount(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("\tpublic int insert(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\n");
	    sb.append("\tpublic int update(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");;\n");
	    sb.append("\n");
	    sb.append("\tpublic int delete(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("}");	
	    return sb.toString();
	  }

	  public String tableToDaoImpl() {
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".dao.impl;\n");
	    sb.append("import java.util.List;\n");
	    sb.append("import java.util.Map;\n");
	    sb.append("import org.apache.ibatis.session.SqlSession;\n");
	    sb.append("import org.springframework.stereotype.Component;\n");
	    sb.append("import " + this.packageName + ".dao." + this.modelName + "Dao;\n");
	    sb.append("import " + this.packageName + ".model." + this.modelName + ";\n");
	    sb.append("import com.wanxue.dao.BaseDao;\n");	    
	    sb.append("@Component\n");
	    sb.append("public class " + this.modelName + "DaoImpl extends BaseDao implements " + this.modelName + "Dao{\n");
	    sb.append("\n");
	    sb.append("\tprivate String ns=\"ns_"+this.tableName+".\";\n");	   
	    sb.append("\n");
	    sb.append("\tpublic " + this.modelName + " findById(int id) {\n");
	    sb.append("\t\treturn ("+this.modelName+")this.getSqlSession().selectOne(ns+\"findById\",id);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic List<"+this.modelName+"> findAllByFilter(Map<String,Object> map) {\n");
	    sb.append("\t\treturn this.getSqlSession().selectList(ns+\"findAllByFilter\",map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\t public int findAllByFilterCount(Map<String,Object> map) {\n");
	    sb.append("\t\treturn this.getSqlSession().selectOne(ns+\"findAllByFilterCount\",map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic int insert(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ") {\n");
	    sb.append("\t\treturn this.getSqlSession().insert(ns+\"insert\"," + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic int update(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ") {\n");
	    sb.append("\t\treturn this.getSqlSession().update(ns+\"update\"," + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic int delete(Map<String,Object> map) {\n");
	    sb.append("\t\treturn this.getSqlSession().update(ns+\"delete\",map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  public String tableToService() {		 
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".service;\n");
	    sb.append("import java.util.List;\n");
	    sb.append("import java.util.Map;\n");
	    sb.append("import " + this.packageName + ".model." + this.modelName + ";\n");
	    sb.append("public interface " + this.modelName + "Service{\n");
	    sb.append("\n");
	    sb.append("\tpublic " + this.modelName + " findById(int id);\n");
	    sb.append("\n");
	    sb.append("\tpublic List<" + this.modelName + "> findAllByFilter(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("\tpublic int findAllByFilterCount(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("\tpublic int insert(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\n");
	    sb.append("\tpublic int update(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\n");
	    sb.append("\tpublic int delete(Map<String,Object> map);\n");
	    sb.append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  public String tableToServiceImpl() {
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".service.impl;\n");
	    sb.append("import java.util.List;\n");
	    sb.append("import java.util.Map;\n");
	    sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
	    sb.append("import org.springframework.stereotype.Service;\n");
	    sb.append("import " + this.packageName + ".dao." + this.modelName + "Dao;\n");
	    sb.append("import " + this.packageName + ".model." + this.modelName + ";\n");
	    sb.append("import " + this.packageName + ".service." + this.modelName + "Service;\n");	   
	    sb.append("@Service\n");
	    sb.append("public class " + this.modelName + "ServiceImpl implements " + this.modelName + "Service {\n");
	    sb.append("\n");
	    sb.append("\t@Autowired\n");
	    sb.append("\tprivate " + this.modelName + "Dao " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao;\n");
	    sb.append("\n");
	    sb.append("\tpublic " + this.modelName + " findById(int id) {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.findById(id);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic List<"+this.modelName+"> findAllByFilter(Map<String,Object> map) {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.findAllByFilter(map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\t public int findAllByFilterCount(Map<String,Object> map) {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.findAllByFilterCount(map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic int insert(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ") {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.insert(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t}\n");
	    sb.append("\tpublic int update(" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ") {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.update(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\tpublic int delete(Map<String,Object> map) {\n");
	    sb.append("\t\treturn "+ this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1)  + "Dao.delete(map);\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  public String tableToServiceManager() {
	    File file = new File(this.serviceManagerURL + "ServiceManager.java");
	    if (file.exists()) {
	      file.delete();
	    }
	    File dir = new File(this.serviceURL);
	    File[] fileList = dir.listFiles();
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".service.manager;\n");
	    sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
	    sb.append("import org.springframework.stereotype.Service;\n");
	    for (File f : fileList) {
	      if (f.isFile()) {
	        sb.append("import " + this.packageName + ".service." + f.getName().replace(".java", "") + ";\n");
	      }
	    }
	    sb.append("@Service\n");
	    sb.append("public class ServiceManager {\n");
	    for (File f : fileList) {
	      if (f.isFile()) {
	        String className = f.getName().replace(".java", "");
	        sb.append("\t@Autowired\n");
	        sb.append("\tprotected " + className + " " + className.substring(0, 1).toLowerCase() + className.substring(1) + ";\n");
	        sb.append("\n");
	      }
	    }
	    for (File f : fileList) {
	      if (f.isFile()) {
	        String className = f.getName().replace(".java", "");
	        sb.append("\tpublic " + className + " get" + className + "() {\n");
	        sb.append("\t\treturn " + className.substring(0, 1).toLowerCase() + className.substring(1) + ";\n");
	        sb.append("\t}\n");
	        sb.append("\n");
	        sb.append("\tpublic void set" + className + "(" + className + " " + className.substring(0, 1).toLowerCase() + className.substring(1) + ") {\n");
	        sb.append("\t\tthis." + className.substring(0, 1).toLowerCase() + className.substring(1) + " = " + className.substring(0, 1).toLowerCase() + className.substring(1) + ";\n");
	        sb.append("\t}\n");
	        sb.append("\n");
	      }
	    }
	    sb.append("}");
	    return sb.toString();
	  } 
	  
	  public String BaseController() {
		    StringBuffer sb = new StringBuffer();
		    
		    sb.append("package " + this.packageName + ".controller;\n");
		    sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		    sb.append("import com.wanxue.service.manager.ServiceManager;\n");
		    sb.append("public class BaseController {\n");
		    sb.append("\t@Autowired\n");
		    sb.append("\tprotected ServiceManager Service;\n");
		    sb.append("\tpublic ServiceManager getService() {\n");
		    sb.append("\t\treturn Service;\n");
		    sb.append("\t}\n");
		    sb.append("\tpublic void setService(ServiceManager service) {\n");
		    sb.append("\t\tService = service;\n");
		    sb.append("\t}\n");
		    sb.append("}\n");
		    return sb.toString();
	  }
	  
	  public String tableToController() {
	    StringBuffer sb = new StringBuffer();
	    
	    sb.append("package " + this.packageName + ".controller;\n");
	    sb.append("import java.io.IOException;\n");
		sb.append("import java.io.PrintWriter;\n");
		sb.append("import java.util.ArrayList;\n");
		sb.append("import java.util.HashMap;\n");
		sb.append("import java.util.List;\n");
		sb.append("import java.util.Map;\n");
		sb.append("import javax.servlet.http.HttpServletRequest;\n");
		sb.append("import javax.servlet.http.HttpServletResponse;\n");
		sb.append("import net.sf.json.JSONArray;\n");
		sb.append("import net.sf.json.JSONObject;\n");
		sb.append("import org.springframework.stereotype.Controller;\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMethod;\n");	
	    sb.append("import " + this.packageName + ".model." + this.modelName + ";\n");
	    sb.append("import com.wanxue.util.Page;	\n");
	    sb.append("@Controller\n");
	    sb.append("@RequestMapping(\"/" + this.modelName + ".do\")\n");
	    sb.append("public class " + this.modelName + "Controller extends BaseController{\n");
	    sb.append("\t@RequestMapping(value = \"getPageList\", method = RequestMethod.GET)\n");	    
	    sb.append("\tpublic void getPageList(HttpServletRequest request,HttpServletResponse response) throws IOException{\n");
	    sb.append("\t\trequest.setCharacterEncoding(\"utf-8\");\n");
	    sb.append("\t\tresponse.setContentType(\"application/json;charset=utf-8\");\n");
	    sb.append("\t\tPrintWriter out = response.getWriter();	\n");
	    sb.append("\t\tJSONObject jsonBack = new JSONObject();\n");	    
	    sb.append("\t\tint currentPage=Integer.parseInt(request.getParameter(\"currentPage\"));\n");	    
	    sb.append("\t\ttry{\n");
	    sb.append("\t\tMap<String,Object> map1 = new HashMap<String,Object>();\n");
	    sb.append("\t\tint totalRecord=Service.get"+this.modelName+"Service().findAllByFilterCount(map1);\n");
	    sb.append("\t\tint totalPage = totalRecord % Page.Length != 0 ? (totalRecord/ Page.Length + 1) : totalRecord / Page.Length;\n");
	    sb.append("\t\tif (currentPage < 1 || currentPage > totalPage)\n");
	    sb.append("\t\t{\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t\treturn;\n");
	    sb.append("\t\t}\n");
	    sb.append("\t\tPage page = new Page();\n");
	    sb.append("\t\tpage.setCurrentPage(currentPage);\n");
	    sb.append("\t\tpage.setTotalPage(totalPage);\n");
	    sb.append("\t\tpage.setTotalRecord(totalRecord);\n");
	    sb.append("\n");
	    sb.append("\t\tMap<String,Object> map2 = new HashMap<String,Object>();\n");
	    sb.append("\t\tint start=(currentPage-1)*Page.Length;\n");
	    sb.append("\t\tmap2.put(\"start\", start);\n");
	    sb.append("\t\tmap2.put(\"limit\", Page.Length);\n");
	    sb.append("\t\tList<"+this.modelName+"> list=Service.get"+this.modelName+"Service().findAllByFilter(map2);\n");
	    sb.append("\t\tJSONArray jsonItems = new JSONArray();\n");
	    sb.append("\t\tfor ("+this.modelName+" model : list) {\n");
	    sb.append("\t\t\tjsonItems.add(JSONObject.fromObject(model));\n");
	    sb.append("\t\t}\n");
	    sb.append("\t\tjsonBack.put(\"success\", true);\n");
	    sb.append("\t\tjsonBack.put(\"results\", jsonItems);\n");
	    sb.append("\t\tjsonBack.put(\"page\", JSONObject.fromObject(page));\n");
	    sb.append("\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}catch(Exception e){\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t\treturn;\n");
	    sb.append("\t\t}\n");
	    sb.append("\t}\n");	  
	    sb.append("\n");
	    sb.append("\n");
	    sb.append("\t@RequestMapping(value = \"GetById\", method = RequestMethod.GET)\n");
	    sb.append("\tpublic void GetById(HttpServletRequest request,HttpServletResponse response) throws IOException {\n");
	    sb.append("\t\tresponse.setContentType(\"application/json;charset=utf-8\");\n");
	    sb.append("\t\tPrintWriter out = response.getWriter();\n");
	    sb.append("\t\tJSONObject jsonBack = new JSONObject();\n");
	    sb.append("\t\tint id = Integer.parseInt(request.getParameter(\"id\"));\n");
	    sb.append("\t\ttry {\n");
	    sb.append("\t\t" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + " = Service.get"+this.modelName+"Service().findById(id);\n");
	    sb.append("\t\tif(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "==null)\n");
	    sb.append("\t\t{\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}else\n");
	    sb.append("\t\t{\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", true);\n");
	    sb.append("\t\t\tjsonBack.put(\"result\", JSONObject.fromObject(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "));\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t\t} catch (Exception e) {\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\n");
	    sb.append("\t@RequestMapping(value = \"Add\", method = RequestMethod.GET)\n");
	    sb.append("\tpublic void Add(HttpServletRequest request,HttpServletResponse response) throws IOException {\n");
	    sb.append("\t\tresponse.setContentType(\"application/json;charset=utf-8\");\n");
	    sb.append("\t\tPrintWriter out = response.getWriter();\n");
	    sb.append("\t\tJSONObject jsonBack = new JSONObject();\n");
	    List<Table> fieldList = getAllFields();
	    for (Table t : fieldList) 
	    {
		     if(!t.getField().equals("id"))
		     {		    	 
		    	 if(t.getField().equals("state"))
		    	 {
		    		 sb.append("\t\tString "+t.getField()+" = request.getParameter(\""+t.getField()+"\");\n");
		    	 }else
		    	 {
		    		 sb.append("\t\tString "+t.getField()+" = request.getParameter(\""+t.getField()+"\");\n");
		    	 }
		     }		     
		}
	    sb.append("\t\ttry {\n");
	    sb.append("\t\t\t" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "= new " + this.modelName + "();\n");
	    for (Table t : fieldList) 
	    {
		     if(!t.getField().equals("id"))
		     {		    	 
		    	 if(t.getField().equals("state"))
		    	 {
		    		 sb.append("\t\t\t" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ".set" + t.getField().substring(0,1).toUpperCase()+t.getField().substring(1) + "(1);\n");
		    	 }else
		    	 {
		    		 sb.append("\t\t\t" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ".set" + t.getField().substring(0,1).toUpperCase()+t.getField().substring(1) + "("+t.getField()+");\n");
		    	 }
		     }		     
		}
	    sb.append("\t\t\tint result=Service.get"+this.modelName+"Service().insert(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", true);\n");
	    sb.append("\t\tout.print(jsonBack);\n");
	    sb.append("\t\t} catch (Exception e) {\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t}\n");	 
	    sb.append("\n");
	    sb.append("\n");
	    sb.append("\t@RequestMapping(value = \"edit\", method = RequestMethod.GET)\n");
	    sb.append("\tpublic void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {\n");
	    sb.append("\t\tresponse.setContentType(\"application/json;charset=utf-8\");\n");
	    sb.append("\t\tPrintWriter out = response.getWriter();\n");
	    sb.append("\t\tJSONObject jsonBack = new JSONObject();\n");
	    sb.append("\t\tint id = Integer.parseInt(request.getParameter(\"id\"));\n");
	    for (Table t : fieldList) 
	    {
		     if(!t.getField().equals("id")&& !t.getField().equals("state"))
		     {
		    	 sb.append("\t\tString "+t.getField()+" = request.getParameter(\""+t.getField()+"\");\n");		    	
		     }		     
		}
	    sb.append("\t\ttry {\n");
	    sb.append("\t\t\t" + this.modelName + " " + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "= new " + this.modelName + "();\n");
	    sb.append("\t\t\t" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ".setId(id);\n");
	    for (Table t : fieldList) 
	    {
		     if(!t.getField().equals("id")&& !t.getField().equals("state"))
		     {
		    	 sb.append("\t\t\t" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ".set" + t.getField().substring(0,1).toUpperCase()+t.getField().substring(1) + "("+t.getField()+");\n");
		     }		     
		}
	    sb.append("\t\t\tint result=Service.get"+this.modelName+"Service().update(" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + ");\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", true);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t} catch (Exception e) {\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t}\n");
	    sb.append("\n");
	    sb.append("\n");
	    sb.append("\t@RequestMapping(value = \"delete\", method = RequestMethod.GET)\n");
	    sb.append("\tpublic void delete(HttpServletRequest request,HttpServletResponse response) throws IOException {\n");
	    sb.append("\t\tresponse.setContentType(\"application/json;charset=utf-8\");\n");
	    sb.append("\t\tPrintWriter out = response.getWriter();\n");
	    sb.append("\t\tJSONObject jsonBack = new JSONObject();\n");
	    sb.append("\t\tString ids = request.getParameter(\"ids\");\n");
	    sb.append("\t\tString[] idArr=ids.split(\",\");\n");
	    sb.append("\t\tList<String> list=new ArrayList<String>();\n");
	    sb.append("\t\tfor(String s:idArr)\n");
	    sb.append("\t\t{\n");
	    sb.append("\t\t\tlist.add(s);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t\ttry {\n");
	    sb.append("\t\t\tMap<String,Object> map = new HashMap<String,Object>();\n");
	    sb.append("\t\t\tmap.put(\"ids\", list);\n");
	    sb.append("\t\t\tint result=Service.get"+this.modelName+"Service().delete(map);\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", true);\n");
	    sb.append("\t\t\tjsonBack.put(\"result\", result);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t} catch (Exception e) {\n");
	    sb.append("\t\t\tjsonBack.put(\"success\", false);\n");
	    sb.append("\t\t\tout.print(jsonBack);\n");
	    sb.append("\t\t}\n");
	    sb.append("\t}\n");
	    sb.append("}\n");
	    return sb.toString();
	  }	  
	  
	  public String modelSql() {
		    StringBuffer sb = new StringBuffer();		
		    List<Table> fieldList = getAllFields();		    	    
		    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		    sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		    sb.append("\n");
		    sb.append("<mapper namespace=\"ns_" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "\">\n");
		    sb.append("\t<resultMap id=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "Map\" type=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "\">\n");
		    for (Table t : fieldList) 
		    {
		    	 sb.append("\t\t<result property=\""+t.getField()+"\" column=\""+t.getField()+"\" />\n");     
			}
		    sb.append("\t</resultMap>  \n");
		    sb.append("\n");
		    sb.append("\t<sql id=\"find_xxx\">\n");
		    sb.append("\t\t\t<![CDATA[\n");
		    String fields="";
		    for (Table t : fieldList) 
		    {
		    	fields+=t.getField()+",";
			}
		    fields=fields.length()!=0?fields.substring(0,fields.length()-1):fields;
		    sb.append("\t\t\t\tselect "+fields+"\n");
		    sb.append("\t\t\t]]>\n");
		    sb.append("\t</sql>\n");
		    sb.append("\t\n");
		    sb.append("\t<sql id=\"from_xxx\">\n");
		    sb.append("\t\t\t<![CDATA[\n");
		    sb.append("\t\t\t\tfrom "+this.tableName+"\n");
		    sb.append("\t\t\t]]>\n");
		    sb.append("\t</sql> \n");  
		    sb.append("\t\n");
		    sb.append("\t<sql id=\"where_xxx\">\n");
		    sb.append("\t\t\t<![CDATA[\n");
		    sb.append("\t\t\t\twhere state!=0\n");
		    sb.append("\t\t\t]]>\n");
		    sb.append("\t</sql> \n");
		    sb.append("\t\n");
		    sb.append("\t<select id=\"findById\" parameterType=\"int\" resultMap=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "Map\">  \n");
		    sb.append("\t\t<include refid=\"find_xxx\"/>\n");
		    sb.append("\t\t<include refid=\"from_xxx\"/>\n");
		    sb.append("\t\twhere id=#{id}\n");
		    sb.append("\t</select>\n");     
		    sb.append("\t \n");
		    sb.append("\t<select id=\"findAllByFilter\" parameterType=\"Map\" resultMap=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "Map\">\n");  
		    sb.append("\t\t<include refid=\"find_xxx\"/>\n");
		    sb.append("\t\t<include refid=\"from_xxx\"/>\n");
		    sb.append("\t\t<include refid=\"where_xxx\"/>\n");
		    sb.append("\t\t<if test=\"start != null and limit != null\">\n");
		    sb.append("\t\t\tlimit #{start},#{limit}\n");
		    sb.append("\t\t</if>\n");
		    sb.append("\t</select> \n");
		    sb.append("\t\n");
		    sb.append("\t<select id=\"findAllByFilterCount\" parameterType=\"Map\" resultType=\"int\">\n");  
		    sb.append("\t\tselect count(id)\n");
		    sb.append("\t\t<include refid=\"from_xxx\"/>\n");
		    sb.append("\t\t<include refid=\"where_xxx\"/> \n");       
		    sb.append("\t</select>\n");
		    sb.append("\t\n");
		    sb.append("\t<insert id=\"insert\" parameterType=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "\">\n");
		    sb.append("\t\tinsert into "+this.tableName+"("+fields+")\n");
		    String fieldsValue="";
		    for (Table t : fieldList) 
		    {
		    	fieldsValue+="#{"+t.getField()+"},";
			}
		    fieldsValue=fieldsValue.length()!=0?fieldsValue.substring(0,fieldsValue.length()-1):fieldsValue;
		    sb.append("\t\tvalues("+fieldsValue+");\n");
		    sb.append("\t\t<selectKey keyProperty=\"id\" resultType=\"int\" order=\"AFTER\">\n");
		    sb.append("\t\t\tselect last_insert_id() as id;\n");
		    sb.append("\t\t</selectKey>\n");
		    sb.append("\t</insert> \n");
		    sb.append("\t\n");
		    sb.append("\t<update id=\"update\" parameterType=\"" + this.modelName.substring(0,1).toLowerCase()+this.modelName.substring(1) + "\">\n");
		    sb.append("\t\tupdate "+this.tableName+"\n");
		    sb.append("\t\t<set>\n");
		    for (Table t : fieldList) 
		    {
		    	if(!t.getField().equals("id"))
		    	{
		    		sb.append("\t\t\t<if test=\""+t.getField()+" != null\">\n");
				    sb.append("\t\t\t\t"+t.getField()+" = #{"+t.getField()+"},\n");
				    sb.append("\t\t\t</if>\n");
		    	}
			}      	
		    sb.append("\t\t</set>\n");       
		    sb.append("\t\tWHERE id = #{id}\n");
		    sb.append("\t</update>\n");
		    sb.append("\t\n");
		    sb.append("\t<update id=\"delete\" parameterType=\"Map\">\n");
		    sb.append("\t\tupdate "+this.tableName+"\n");
		    sb.append("\t\tset state=0   \n");      
		    sb.append("\t\twhere id in\n");
		    sb.append("\t\t<foreach collection=\"ids\" item=\"id\"  open=\"(\" separator=\",\" close=\")\">  \n");
		    sb.append("\t\t\t#{id} \n");
		    sb.append("\t\t</foreach>\n");
		    sb.append("\t</update>\n");
		    sb.append("\t\n");
		    sb.append("</mapper>\n"); 
		    return sb.toString();
	  }
	  
	  
	  public String springContextConfig() {
		    StringBuffer sb = new StringBuffer();	
		    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		    sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
		    sb.append("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		    sb.append("\txmlns:context=\"http://www.springframework.org/schema/context\"\n");
		    sb.append("\txmlns:mvc=\"http://www.springframework.org/schema/mvc\"\n");
		    sb.append("\txmlns:p=\"http://www.springframework.org/schema/p\"\n");
		    sb.append("\txmlns:aop=\"http://www.springframework.org/schema/aop\" \n"); 
		    sb.append("\txmlns:tx=\"http://www.springframework.org/schema/tx\" \n");
		    sb.append("\txsi:schemaLocation=\"http://www.springframework.org/schema/beans\n"); 
		    sb.append("\t\thttp://www.springframework.org/schema/beans/spring-beans-3.1.xsd \n");  
		    sb.append("\t\thttp://www.springframework.org/schema/context\n");
		    sb.append("\t\thttp://www.springframework.org/schema/context/spring-context-3.1.xsd \n");  
		    sb.append("\t\thttp://www.springframework.org/schema/mvc \n");
		    sb.append("\t\thttp://www.springframework.org/schema/mvc/spring-mvc-3.1.xsd\n");
		    sb.append("\t\thttp://www.springframework.org/schema/tx  \n");
		    sb.append("\t\thttp://www.springframework.org/schema/tx/spring-tx.xsd  \n");
		    sb.append("\t\thttp://www.springframework.org/schema/aop  \n");
		    sb.append("\t\thttp://www.springframework.org/schema/aop/spring-aop.xsd\"> \n");
		    sb.append("\n");   
		    sb.append("\t<bean id=\"viewResolver\" class=\"org.springframework.web.servlet.view.InternalResourceViewResolver\">\n");
		    sb.append("\t\t<property name=\"prefix\" value=\"/WEB-INF/Views/\" />\n");
		    sb.append("\t\t<property name=\"suffix\" value=\".jsp\" />\n");
		    sb.append("\t</bean> \n");
		    sb.append("\n");
		    sb.append("\t<context:annotation-config />\n");
		    sb.append("\t<context:component-scan base-package=\"com.*\" />\n");
		    sb.append("\t<mvc:annotation-driven />\n");
		    sb.append("\n");
		    sb.append("\t<bean id=\"handlerMapping\" class=\"org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping\" />  \n");
		    sb.append("\t<bean id=\"handlerAdapter\" class=\"org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter\" />\n");
		    sb.append("\n");
		    sb.append("\t<!-- 事务管理器 -->	\n");
		    sb.append("\t<!-- \n");
		    sb.append("\t<bean id=\"transactionManager\"  class=\"org.springframework.orm.hibernate3.HibernateTransactionManager\">\n");
		    sb.append("\t\t<property name=\"sessionFactory\">\n");
		    sb.append("\t\t<ref local=\"sessionFactory\"/>\n");
		    sb.append("\t</property>\n");
		    sb.append("\t</bean>\n");
		    sb.append("\t<tx:annotation-driven transaction-manager=\"transactionManager\" proxy-target-class=\"true\"/>\n");
		    sb.append("\t-->\n");
		    sb.append("\t<!-- 配置事务特性 -->\n");
		    sb.append("\t<!-- \n");
		    sb.append("\t<tx:advice id=\"txAdvice\" transaction-manager=\"transactionManager\">\n");
		    sb.append("\t\t<tx:attributes>\n");
		    sb.append("\t\t\t<tx:method name=\"*\" propagation=\"REQUIRED\" rollback-for=\"Exception\"/>\n");
		    sb.append("\t\t</tx:attributes>\n");
		    sb.append("\t</tx:advice>\n");
		    sb.append("\t-->\n");
		    sb.append("\t<!-- 配置那些类的方法进行事务管理，当前com.wanxue.service.impl包中的类中所有方法需要，还需要参考tx:advice的设置 -->\n");
		    sb.append("\t<!-- \n");
		    sb.append("\t<aop:config proxy-target-class=\"true\">\n");
		    sb.append("\t<aop:pointcut id=\"allManagerMethod\" expression=\"execution(* com.wanxue.service.impl.*.*(..))\"/>\n");
		    sb.append("\t<aop:advisor advice-ref=\"txAdvice\" pointcut-ref=\"allManagerMethod\" order=\"0\"/>\n");
		    sb.append("\t</aop:config> \n");  
		    sb.append("\t --> \n");
		    sb.append("\n");
		    sb.append("</beans>");
		    return sb.toString();
	  }
	  
	  public String springDataSourceConfig() {
		    StringBuffer sb = new StringBuffer();
		    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		    sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"\n");
		    sb.append("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n");
		    sb.append("\txmlns:context=\"http://www.springframework.org/schema/context\"\n");
		    sb.append("\txmlns:mvc=\"http://www.springframework.org/schema/mvc\"\n");
		    sb.append("\txsi:schemaLocation=\"http://www.springframework.org/schema/beans \n");   
		    sb.append("\t\thttp://www.springframework.org/schema/beans/spring-beans-3.1.xsd\n");   
		    sb.append("\t\thttp://www.springframework.org/schema/context\n");   
		    sb.append("\t\thttp://www.springframework.org/schema/context/spring-context-3.1.xsd \n");  
		    sb.append("\t\thttp://www.springframework.org/schema/mvc  \n");  
		    sb.append("\t\thttp://www.springframework.org/schema/mvc/spring-mvc-3.1.xsd\">\n");
		    sb.append("\n");
		    sb.append("\t<bean id=\"dataSource\" class=\"org.apache.commons.dbcp.BasicDataSource\" destroy-method=\"close\">\n");
		    sb.append("\t\t<property name=\"driverClassName\" value=\"com.mysql.jdbc.Driver\"/>\n");
		    sb.append("\t\t<property name=\"url\" value=\"jdbc:mysql://localhost:3306/test\"/>\n");
		    sb.append("\t\t<property name=\"username\" value=\"root\"/>\n");
		    sb.append("\t\t<property name=\"password\" value=\"dfzc\"/>\n");
		    sb.append("\t</bean>\n");
		    sb.append("\t<bean id=\"sessionFactory\" class=\"org.mybatis.spring.SqlSessionFactoryBean\">\n");
		    sb.append("\t\t<property name=\"dataSource\" ref=\"dataSource\" />  \n");   
		    sb.append("\t\t<property name=\"configLocation\" value=\"classpath:META-INF/mybatis/mybatis.xml\" /> \n");
		    sb.append("\t\t<property name=\"mapperLocations\">\n");
		    sb.append("\t\t\t<list> \n");  
		    sb.append("\t\t\t\t<value>classpath:com/wanxue/sql/*.xml</value> \n"); 
		    sb.append("\t\t\t</list>\n"); 
		    sb.append("\t\t</property>\n");
		    sb.append("\t</bean>\n");
		    sb.append("\t<bean id=\"sqlSession\" class=\"org.mybatis.spring.SqlSessionTemplate\">\n"); 
		    sb.append("\t\t<constructor-arg index=\"0\" ref=\"sessionFactory\" />\n"); 
		    sb.append("\t</bean> \n");   
		    sb.append("</beans>\n");   
		    return sb.toString();
	  }
	  
	  public String mybatisConfig() {
		    StringBuffer sb = new StringBuffer();	
		    File dir = new File(this.modelURL);
		    File[] fileList = dir.listFiles();	    
		   
		    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); 
		    sb.append("<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n"); 
		    sb.append("<configuration>\n"); 
		    sb.append("<typeAliases>\n"); 
		    for (File f : fileList) {
		    	 String className = f.getName().replace(".java", "");
			     if (f.isFile()) {
			    	  sb.append("<typeAlias type=\"com.wanxue.model."+className+"\" alias=\""+className.substring(0,1).toLowerCase()+className.substring(1)+"\" />\n"); 
			     }
			}		    
		    sb.append("</typeAliases>\n"); 
		    sb.append("</configuration>\n"); 
		    return sb.toString();
	  }
	  
	  public String webConfig() {
		    StringBuffer sb = new StringBuffer();		    
		    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		    sb.append("<web-app version=\"2.5\"\n");
		    sb.append("\txmlns=\"http://java.sun.com/xml/ns/javaee\"\n");
		    sb.append("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		    sb.append("\txsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee\n");
		    sb.append("\thttp://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\">\n");
		    sb.append("\n");
		    sb.append("\t<listener>\n");
		    sb.append("\t\t<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>\n");
		    sb.append("\t</listener>\n");
		    sb.append("\t<context-param>\n");
		    sb.append("\t\t<param-name>contextConfigLocation</param-name>\n");
		    sb.append("\t\t<param-value>\n");
		    sb.append("\t\t\tclasspath*:META-INF/spring/springContext.xml,\n");
		    sb.append("\t\t\tclasspath*:META-INF/spring/springDataSource.xml\n");
		    sb.append("\t\t</param-value>\n");
		    sb.append("\t</context-param>\n");
		    sb.append("\n");
		    sb.append("\t<session-config>\n");
		    sb.append("\t\t<session-timeout>30</session-timeout>\n");
		    sb.append("\t</session-config>\n");
		    sb.append("\n");
		    sb.append("\t<context-param>\n");
		    sb.append("\t\t<param-name>webAppRootKey</param-name>\n");
		    sb.append("\t\t<param-value>Project.root</param-value>\n");
		    sb.append("\t</context-param>\n");
		    sb.append("\n");
		    sb.append("\t<servlet>\n");
		    sb.append("\t\t<servlet-name>dispatcher</servlet-name>\n");
		    sb.append("\t\t<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>\n");
		    sb.append("\t\t<init-param>\n");
		    sb.append("\t\t\t<param-name>contextConfigLocation</param-name>\n");
		    sb.append("\t\t\t<param-value>classpath*:META-INF/spring/springContext.xml</param-value>\n");
		    sb.append("\t\t</init-param>\n");
		    sb.append("\t</servlet>\n");
		    sb.append("\t<servlet-mapping>\n");
		    sb.append("\t\t<servlet-name>dispatcher</servlet-name>\n");
		    sb.append("\t\t<url-pattern>/</url-pattern>\n");
		    sb.append("\t</servlet-mapping>\n");
		    sb.append("\n");
		    sb.append("\t<filter>\n");
		    sb.append("\t\t<filter-name>characterEncodingFilter</filter-name>\n");
		    sb.append("\t\t<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>\n");
		    sb.append("\t\t<init-param>\n");
		    sb.append("\t\t\t<param-name>encoding</param-name>\n");
		    sb.append("\t\t\t<param-value>UTF-8</param-value>\n");
		    sb.append("\t\t</init-param>\n");
		    sb.append("\t\t<init-param>\n");
		    sb.append("\t\t\t<param-name>forceEncoding</param-name>\n");
		    sb.append("\t\t\t<param-value>true</param-value>\n");
		    sb.append("\t\t</init-param>\n");
		    sb.append("\t</filter>\n");
		    sb.append("\n");
		    sb.append("\t<filter-mapping>\n");
		    sb.append("\t\t<filter-name>characterEncodingFilter</filter-name>\n");
		    sb.append("\t\t<url-pattern>/*</url-pattern>\n");
		    sb.append("\t</filter-mapping>\n");
		    sb.append("\n");
		    sb.append("\t<welcome-file-list>\n");
		    sb.append("\t\t<welcome-file>index.jsp</welcome-file>\n");
		    sb.append("\t</welcome-file-list>\n");
		    sb.append("</web-app>\n");
		    return sb.toString();
	  }	  
}

