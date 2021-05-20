package jdbc.viewcblob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

@Repository("viewDao")
public class ViewDaoImpl implements ViewDao{
    
	@Autowired
	private JdbcTemplate jdbcTempalte;
	@Autowired 
	private DefaultLobHandler lobHandler;
	
  final static String sqlCreateViewTable
	  ="create table t_view(id int primary key,description longtext,imgfile blob)";
  final static String sqlInsertViewCBlob
      ="insert into t_view(id,description,imgfile) values(?,?,?)";
  final static String sqlGetImgFile
      ="select imgfile from t_view where id=?";

@Override
	public void createViewTable() {
       this.jdbcTempalte.execute(sqlCreateViewTable);	  		
	}

@Override
public void insertViewCBlob(final View view) throws Exception {
	
	final FileInputStream is=new FileInputStream(view.getImgFile());
	final int size= (int)view.getImgFile().length();
	  
	this.jdbcTempalte.execute(sqlInsertViewCBlob,
		 new AbstractLobCreatingPreparedStatementCallback(this.lobHandler){
			@Override
			  protected void setValues( PreparedStatement pstm,
										LobCreator creator) throws SQLException,DataAccessException
			  {
				pstm.setInt(1,view.getId());
				creator.setClobAsString(pstm,2,view.getDescription());
			    creator.setBlobAsBinaryStream(pstm,3,is,size);							
			  }});		
	}

@Override
public void insertCBlob() throws IOException {
  // straight use PreparedStatement
  String sql="insert into t_view(id,description,imgfile) values(?,?,?)";
  
  final File pic=new File("d:"+File.separator+"MaxJ.jpg");
  final InputStream in=new FileInputStream(pic);
  final File cchar=new File ("d:"+File.separator+"a.txt");
  final FileReader reader=new FileReader(cchar);
 
  this.jdbcTempalte.batchUpdate(sql,new BatchPreparedStatementSetter(){
	@Override
	public int getBatchSize() {
		return 1;
	}
	@Override
	public void setValues(PreparedStatement pstm, int size) throws SQLException {
	  pstm.setInt(1,88);
	  pstm.setClob(2,reader,(int)cchar.length());
	  pstm.setBinaryStream(3,in,(int)pic.length());	
	}});
  }

@SuppressWarnings({ "unchecked", "rawtypes" })
@Override
public File getImgFile(int viewid ) throws IOException {
	final OutputStream os=new FileOutputStream(new File("d:"+File.separator+"wish_bak.jpg"));
	this.jdbcTempalte.query(sqlGetImgFile,new Object[]{viewid},
     new AbstractLobStreamingResultSetExtractor(){
		@Override
		protected void streamData(ResultSet set) throws SQLException,
				IOException, DataAccessException {
			InputStream in=lobHandler.getBlobAsBinaryStream(set, 1);
			FileCopyUtils.copy(in, os);
		}});
	return new File("d:"+File.separator+"wish_bak.jpg");
  }
}
