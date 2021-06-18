package dbutils;


import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;


public class NovelDAO {

 
    @Test
    public void add(Object params[]) throws SQLException {
        //将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into biz_novel(title,content,content_detail,uptime,createtime,url) values(?,?,?,?,?,?)";
//        Object params[] = {"1111","11122", "3333333", "2019-07", new Date(),"url"};
        qr.update(sql, params);
    }
    
    @Test
    public void addPageData(Object params[]) throws SQLException {
    	//将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
    	QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
    	String sql = "insert into biz_novel(title,content,content_detail,uptime,createtime,url) values(?,?,?,?,?,?)";
//        Object params[] = {"1111","11122", "3333333", "2019-07", new Date(),"url"};
    	qr.update(sql, params);
    }
    
    
    @Test
    public void addContentData(Object params[]) throws SQLException {
    	QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
    	StringBuilder sql = new StringBuilder();
    	sql.append(" insert into t_content (id,dataid,name,type,title,content,creator,ctime,createtime,update,keyword,clickhit,images,"
    			+ "html1,html2,html3,content1,content2,content3,remark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
    	qr.update(sql.toString(), params);
    }
     
}