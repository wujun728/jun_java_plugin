package dbutils;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

public class ContentDAO {

	@Test
	public void addContentData(Object params[]) throws SQLException {
		QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
		StringBuilder sql = new StringBuilder();
		sql.append(
				"INSERT INTO t_content ( dataid, name, type, title, content, creator, ctime, createtime,  updatetime, keyword, clickhit, images, html1, html2, html3, content1, content2, content3,"
				+ " remark) VALUES ( ?,?,?  ,?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,? )  " + 
				" ");
//		sql.append(
//				" insert into t_content (dataid,name,type,title,content,creator,ctime,createtime,updatetime,keyword,clickhit,images,"
//						+ "html1,html2,html3,content1,content2,content3,remark) values ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )  ");
		qr.update(sql.toString(),params);
	}

}