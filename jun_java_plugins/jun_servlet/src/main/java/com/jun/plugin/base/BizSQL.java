package com.jun.plugin.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jun.plugin.reflect.BeanFactory;


public class BizSQL {


	public static int STATUS_USER = 0; // ��ͨ�û�
	public static int STATUS_ADMIN = 1; // ����Ա

	public static int STATE_FREE = 0; // ��Ա��
	public static int STATE_FREEZE = 1; // ��Ա����

	public static int STATE_NOREGULAR = 0; // ���ð�
	public static int STATE_REGULAR = 1; // ��ʽ��

	public static int STATE_HEAD = 0; // ͷ��
	public static int STATE_SOFT = 1; // ���
	public static int STATE_RESOLVENT = 2; // �������

	public static int STATE_TOOL = 0; // ����
	public static int STATE_MEND = 1; // ����

	// parameter
	public static int STEP = 8; // ��ҳ����

	// user
	public static final String INSERT_USER = "INSERT INTO user (uid,password,name,regqu,reganswer,iid,sex,age,tel,email,qq,regtime,address,http,status,state,resume) VALUES(?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)";
	public static final String UPDATE_USER = "UPDATE (user) SET password=?,name=?,iid=?,sex=?,age=?,tel=?,email=?,qq=?,address=?,http=?,status=?,state=?,resume=? where uid=?";
	public static final String UPDATE_USER_M = "UPDATE (user) SET status=?,state=? where uid=?";
	public static final String SELECT_USER = "SELECT * FROM (user) ";

	// new
	public static final String INSERT_NEW = "INSERT INTO new (uid,title,ntime,fromto,content) VALUES(?,?,now(),?,?)";
	public static final String SELECT_NEW = "SELECT * FROM (new) ";
	public static final String DELETE_NEW = "DELETE FROM new ";
 
	// soft
	public static final String INSERT_SOFT = "INSERT INTO soft (name,edition,price,uptime,uid,iid,sid,environment,filesize,commend,loadnum,regular,introduce,path,resume) VALUES(?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_SOFT = "UPDATE (soft) SET name=?,edition=?,price=?,iid=?,sid=?,environment=?,filesize=?,commend=?,loadnum=?,regular=?,introduce=?,path=?,resume=? where sfid=?";
	public static final String UPDATE_SOFT_LOAD = "UPDATE (soft) SET loadnum=? where sfid=?";
	public static final String SELECT_SOFT = "SELECT * FROM (soft) ";
	public static final String DELETE_SOFT = "DELETE FROM soft ";

	// resolvent 

	// question
	public static final String INSERT_QUESTION = "INSERT INTO question (question,answer) VALUES(?,?)";
	public static final String UPDATE_QUESTION = "UPDATE (question) SET answer=?,question=? where qid=?";
	public static final String SELECT_QUESTION = "SELECT * FROM (question) ";
	public static final String DELETE_QUESTION = "DELETE FROM question ";
  
	
	//sys_fileupload
	public static final String SYS_FILEUPLOAD = "sys_fileupload";
	public static final String SYS_FILEUPLOAD_SELECT = " SELECT * FROM SYS_FILEUPLOAD";
	public static final String SYS_FILEUPLOAD_SELECT_LIMIT = " SELECT * FROM SYS_FILEUPLOAD LIMIT ?,? ";
	public static final String SYS_FILEUPLOAD_DELTE = " DELETE FROM SYS_FILEUPLOAD WHERE ID=? ";
	public static final String SYS_FILEUPLOAD_INSERT = " INSERT INTO SYS_FILEUPLOAD (ID,BIZID,FILENAME,FILETYPE,FILESIZE,DEL_FLAG,FILEPATH,DOWNLOAD_TIME,UPLOAD_USERID,UPLOAD_TIME,DELETE_TIME,DESCRIPTION,EXT_COL1,EXT_COL2,EXT_COL3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SYS_FILEUPLOAD_UPDATE = " UPDATE SYS_FILEUPLOAD SET ID=?,BIZID=?,FILENAME=?,FILETYPE=?,FILESIZE=?,DEL_FLAG=?,FILEPATH=?,DOWNLOAD_TIME=?,UPLOAD_USERID=?,UPLOAD_TIME=?,DELETE_TIME=?,DESCRIPTION=?,EXT_COL1=?,EXT_COL2=?,EXT_COL3=? WHERE ID=? ";
	public static final String SYS_FILEUPLOAD_COUNT = " SELECT COUNT(*) AS C FROM SYS_FILEUPLOAD";
	
	//sys_tables_columns
	public static final String SELECT_SYS_TABLES_COLUMNS = " SELECT * FROM SYS_TABLES_COLUMNS";
	public static final String SELECT_SYS_TABLES_COLUMNS_TABLENAME = " SELECT * FROM SYS_TABLES_COLUMNS WHERE TABLE_NAME LIKE ? ";
	public static final String DELTE_SYS_TABLES_COLUMNS = " DELETE FROM SYS_TABLES_COLUMNS WHERE  ";
	public static final String INSERT_SYS_TABLES_COLUMNS = " INSERT INTO SYS_TABLES_COLUMNS (TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,COLUMN_TYPE,COLUMN_KEY,EXTRA,PRIVILEGES,COLUMN_COMMENT,TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,COLUMN_TYPE,COLUMN_KEY,EXTRA,PRIVILEGES,COLUMN_COMMENT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_SYS_TABLES_COLUMNS = " UPDATE SYS_TABLES_COLUMNS SET TABLE_CATALOG=?,TABLE_SCHEMA=?,TABLE_NAME=?,COLUMN_NAME=?,ORDINAL_POSITION=?,COLUMN_DEFAULT=?,IS_NULLABLE=?,DATA_TYPE=?,CHARACTER_MAXIMUM_LENGTH=?,CHARACTER_OCTET_LENGTH=?,NUMERIC_PRECISION=?,NUMERIC_SCALE=?,DATETIME_PRECISION=?,CHARACTER_SET_NAME=?,COLLATION_NAME=?,COLUMN_TYPE=?,COLUMN_KEY=?,EXTRA=?,PRIVILEGES=?,COLUMN_COMMENT=?,TABLE_CATALOG=?,TABLE_SCHEMA=?,TABLE_NAME=?,COLUMN_NAME=?,ORDINAL_POSITION=?,COLUMN_DEFAULT=?,IS_NULLABLE=?,DATA_TYPE=?,CHARACTER_MAXIMUM_LENGTH=?,CHARACTER_OCTET_LENGTH=?,NUMERIC_PRECISION=?,NUMERIC_SCALE=?,DATETIME_PRECISION=?,CHARACTER_SET_NAME=?,COLLATION_NAME=?,COLUMN_TYPE=?,COLUMN_KEY=?,EXTRA=?,PRIVILEGES=?,COLUMN_COMMENT=? WHERE  ";
	public static final String COUNT_SYS_TABLES_COLUMNS = " SELECT COUNT(*) AS C FROM SYS_TABLES_COLUMNS";
	
	//sys_tables
	public static final String SYS_TABLES = "sys_tables";
	public static final String SYS_TABLES_SELECT = " SELECT * FROM SYS_TABLES";
	public static final String SYS_TABLES_DELTE = " DELETE FROM SYS_TABLES WHERE  ";
	public static final String SYS_TABLES_INSERT = " INSERT INTO SYS_TABLES (TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,TABLE_TYPE,ENGINE,VERSION,ROW_FORMAT,TABLE_ROWS,AVG_ROW_LENGTH,DATA_LENGTH,MAX_DATA_LENGTH,INDEX_LENGTH,DATA_FREE,AUTO_INCREMENT,CREATE_TIME,UPDATE_TIME,CHECK_TIME,TABLE_COLLATION,CHECKSUM,CREATE_OPTIONS,TABLE_COMMENT,TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,TABLE_TYPE,ENGINE,VERSION,ROW_FORMAT,TABLE_ROWS,AVG_ROW_LENGTH,DATA_LENGTH,MAX_DATA_LENGTH,INDEX_LENGTH,DATA_FREE,AUTO_INCREMENT,CREATE_TIME,UPDATE_TIME,CHECK_TIME,TABLE_COLLATION,CHECKSUM,CREATE_OPTIONS,TABLE_COMMENT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SYS_TABLES_UPDATE = " UPDATE SYS_TABLES SET TABLE_CATALOG=?,TABLE_SCHEMA=?,TABLE_NAME=?,TABLE_TYPE=?,ENGINE=?,VERSION=?,ROW_FORMAT=?,TABLE_ROWS=?,AVG_ROW_LENGTH=?,DATA_LENGTH=?,MAX_DATA_LENGTH=?,INDEX_LENGTH=?,DATA_FREE=?,AUTO_INCREMENT=?,CREATE_TIME=?,UPDATE_TIME=?,CHECK_TIME=?,TABLE_COLLATION=?,CHECKSUM=?,CREATE_OPTIONS=?,TABLE_COMMENT=?,TABLE_CATALOG=?,TABLE_SCHEMA=?,TABLE_NAME=?,TABLE_TYPE=?,ENGINE=?,VERSION=?,ROW_FORMAT=?,TABLE_ROWS=?,AVG_ROW_LENGTH=?,DATA_LENGTH=?,MAX_DATA_LENGTH=?,INDEX_LENGTH=?,DATA_FREE=?,AUTO_INCREMENT=?,CREATE_TIME=?,UPDATE_TIME=?,CHECK_TIME=?,TABLE_COLLATION=?,CHECKSUM=?,CREATE_OPTIONS=?,TABLE_COMMENT=? WHERE  ";
	public static final String SYS_TABLES_COUNT = " SELECT COUNT(*) AS C FROM SYS_TABLES";
	
	//获取分页参数
	public static Object[] getSelectLimitParam(Map param){
		return new Object[]{getMapStr(param, "page"),getMapStr(param, "rows")};
	}

	//test
	public static final String TEST = "test";
	public static final String TEST_SELECT = " SELECT * FROM TEST";
	public static final String TEST_SELECT_LIMIT = " SELECT * FROM TEST LIMIT ?,? ";
	public static final String TEST_SELECT_ONE = " SELECT * FROM TEST WHERE ID=? ";
	public static final String TEST_SELECT_STR = " SELECT content FROM TEST WHERE ID=? ";
	public static final String TEST_DELETE = " DELETE FROM TEST WHERE ID=? ";
	public static final String TEST_INSERT = " INSERT INTO TEST (ID,NAME,CONTENT) VALUES (?,?,?)";
	public static final String TEST_UPDATE = " UPDATE TEST SET NAME=?,CONTENT=? WHERE ID=? ";
	public static final String TEST_COUNT = " SELECT COUNT(*) AS C FROM TEST";
	public static Object[] getDeleteParam_test(Map param){ return new Object[]{getMapStr(param, "id")}; } 
	public static Object[] getInsertParam_test(Map param){ return new Object[]{getMapStr(param, "id"),getMapStr(param, "name"),getMapStr(param, "content"),}; } 
	public static Object[] getUpdateParam_test(Map param){ return new Object[]{getMapStr(param, "name"),getMapStr(param, "content"),getMapStr(param, "id")}; } 

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) { 
//		BizSQL.genTableSql("test");
		Map param=new HashMap();
		param.put("method", "test");
		param.put("page", "1");
		param.put("rows", "5");
		param.put("id", "7637265");
//		param.put("name", "test");
//		param.put("content", "test1111");
//		System.err.println(BeanFactory.getInstance(BizService.class).save(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).update(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).delete(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).getTotal(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).getDatagrid(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).queryForList(param));
//		System.err.println(BeanFactory.getInstance(BizService.class).queryForMap(param));
		System.err.println(BeanFactory.getInstance(BizService.class).queryForString(param));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void genTableSql(String tableName) {
		String tablename = "";
		String pk = "" ;
		String pk_method_param = "" ;
		String insert_column_names = "";
		String insert_column_values = "";
		String insert_method_param = "";
		String update_column_name_values = "";
		String update_method_param = "";
		
		Map param=new HashMap();
		param.put("method", tableName);
		List tblist=BeanFactory.getInstance(BizService.class).getList(param);
		
		if(tblist!=null&&!tblist.isEmpty()){
			for(int i=0;i<tblist.size();i++){
				Map tbm=(Map) tblist.get(i);
				String tbname=BizSQL.getMapStr(tbm, "TABLE_NAME");
				String _pk=BizSQL.getMapStr(tbm, "COLUMN_KEY");
				String _column_name=BizSQL.getMapStr(tbm, "COLUMN_NAME");
				pk+=_pk.equals("")?"":_column_name+"=?,";
				pk_method_param+=_pk.equals("")?"":"getMapStr(param, \""+_column_name+"\"),";
				insert_column_names+=_column_name+",";
				insert_column_values+="?,";
				insert_method_param+="getMapStr(param, \""+_column_name+"\"),";
				update_column_name_values+=_pk.equals("")?_column_name+"=?,":"";
				update_method_param+=_pk.equals("")?"getMapStr(param, \""+_column_name+"\"),":"";
				tablename = tbname;//*****
			}
			pk=pk!=null&&pk.length()>1?pk.substring(0, pk.length()-1):"";
			pk_method_param=pk_method_param!=null&&pk_method_param.length()>1?pk_method_param.substring(0, pk_method_param.length()-1):"";
			insert_column_names=insert_column_names.substring(0, insert_column_names.length()-1);
			insert_column_values=insert_column_values.substring(0, insert_column_values.length()-1);
			update_column_name_values=update_column_name_values.substring(0, update_column_name_values.length()-1);
//			update_method_param=update_method_param.substring(0, update_method_param.length()-1);
			
			String COUNT_SQL=" SELECT COUNT(*) AS C FROM "+tablename;
			String SELECT_SQL=" SELECT * FROM "+tablename;
			String SELECT_SQL_LIMIT=" SELECT * FROM "+tablename+" LIMIT ?,? ";
			String SELECT_SQL_ONE=" SELECT * FROM "+tablename+" WHERE "+pk+" ";
			String DELTE_SQL=" DELETE FROM "+tablename+" WHERE "+pk+" ";
			String DELTE_MEHOD_PARAM="public static Object[] getPKParam_"+tablename+"(Map param){ return new Object[]{"+pk_method_param+"}; } ";
			String INSERT_SQL=" INSERT INTO "+tablename+" ("+insert_column_names+") VALUES ("+insert_column_values+")";
			String INSERT_MEHOD_PARAM="public static Object[] getInsertParam_"+tablename+"(Map param){ return new Object[]{"+insert_method_param+"}; } ";
			String UPDATE_SQL=" UPDATE "+tablename+" SET "+update_column_name_values+" WHERE "+pk+" ";
			String UPDATE_MEHOD_PARAM="public static Object[] getUpdateParam_"+tablename+"(Map param){ return new Object[]{"+update_method_param+pk_method_param+"}; } ";
			
			
			System.err.println("COUNT_SQL="+COUNT_SQL);
			System.err.println("SELECT_SQL="+SELECT_SQL);
			System.err.println("SELECT_SQL_LIMIT="+SELECT_SQL_LIMIT);
			System.err.println("SELECT_SQL_ONE="+SELECT_SQL_ONE);
			System.err.println("DELTE_SQL="+DELTE_SQL);
			System.err.println("DELTE_MEHOD_PARAM="+DELTE_MEHOD_PARAM);
			System.err.println("INSERT_SQL="+INSERT_SQL);
			System.err.println("UPDATE_SQL="+UPDATE_SQL);
			System.err.println("UPDATE_MEHOD_PARAM="+UPDATE_MEHOD_PARAM);
			
			
			System.err.println("");
			System.err.println("//"+tablename);
			System.err.println("public static final String "+tablename.toUpperCase()+" = \""+tablename+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_SELECT = \""+SELECT_SQL.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_SELECT_LIMIT = \""+SELECT_SQL_LIMIT.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_SELECT_ONE = \""+SELECT_SQL_ONE.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_DELETE = \""+DELTE_SQL.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_INSERT = \""+INSERT_SQL.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_UPDATE = \""+UPDATE_SQL.toUpperCase()+"\";");
			System.err.println("public static final String "+tablename.toUpperCase()+"_COUNT = \""+COUNT_SQL.toUpperCase()+"\";");
			System.err.println(DELTE_MEHOD_PARAM);
			System.err.println(INSERT_MEHOD_PARAM);
			System.err.println(UPDATE_MEHOD_PARAM);
		}
	}
	
	public static String getMapStr(Object map,String str){
		if(map instanceof Map){
			return String.valueOf(((Map)map).get(str)==null?"":((Map)map).get(str));
		}else{
			System.err.println("getMapStr()输入参数类型有误");
			return null;
		}
	}
	
	/*-- 
	

	 
	delete from sys_tables where  TABLE_NAME in ('sys_fileupload');
	insert into sys_tables
	select * from information_schema.tables _c
	where _c.TABLE_NAME in ('sys_fileupload');

	delete from sys_tables_COLUMNs   where  TABLE_NAME in ('sys_fileupload');
	insert into sys_tables_COLUMNs
	select * from information_schema.COLUMNS _c
	where _c.TABLE_NAME in ('sys_fileupload');

	select *  from sys_fileupload LIMIT 0,10;
	select *  from sys_tables;
	select *  from sys_tables_COLUMNs;
	
	//drop table sys_tables;
	create table sys_tables
	as 
	select * from information_schema.tables _c
	where _c.TABLE_NAME = 'sys_fileupload';

	//drop table sys_tables_COLUMNs;
	create table sys_tables_COLUMNs
	as 
	select * from information_schema.COLUMNS _c
	where _c.TABLE_NAME = 'sys_fileupload';
	
	
	*/	

	 
}
