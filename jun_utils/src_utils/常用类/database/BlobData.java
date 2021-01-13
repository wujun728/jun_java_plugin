package book.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 存取BLOB数据到数据库。
 */
public class BlobData {
	/**
	 * 写Blob数据到数据库
	 * @param con	
	 * @param infilePath	保存到数据库的文件	
	 */
	public static void writeBlob(Connection con, String infilePath){
		FileInputStream fis = null;
		PreparedStatement psm = null;
		try {
			//打开输入文件
			File file = new File(infilePath);
			fis = new FileInputStream(file);
			psm = con.prepareStatement(
					"insert into student_photo(name, filename, filedata)"
					+ " values('mary', ?, ?)");
			//第一个参数为文件名
			psm.setString(1, file.getName());
			//第二个参数为文件的二进制流
			psm.setBinaryStream(2, fis, fis.available());
			// 执行
			psm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//关闭打开的对像
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			OperateDB.closeStatement(psm);
		}
	}
	/**
	 * 从数据库中读Blob数据
	 * @param con
	 * @param srcFileName	要读取的文件名
	 * @param outFilePath	保存到本地的文件
	 */
	public static void readBlob(Connection con, String srcFileName, 
			String outFilePath){
        Statement sm = null;
        FileOutputStream fos = null;
        InputStream is = null;
		try {
	        sm = con.createStatement();
	        ResultSet rs = sm.executeQuery(
	        		"SELECT * FROM student_photo where name = 'mary'"
	        		+ " and filename = '" + srcFileName + "'");
	        if (rs.next()) {
	            // 从列中读取Blob数据
	            Blob blob = rs.getBlob("filedata");
	    
	            // 获取Blob的字节数，是long类型，表示字节数很多。
	            long blobLength = blob.length();
	            // 可以通过blob的getBytes方法从Blob对象中提取字节数据
	            // 也通过Blob对象的二进制输入流读数据
	            is = blob.getBinaryStream();
				byte[] buffer = new byte[4096];
				int size;
				File file = new File(outFilePath);
				try {
					fos = new FileOutputStream(file);
					while ((size = is.read(buffer)) != -1){
						fos.write(buffer, 0, size);
					}
				} catch (IOException eee) {
					eee.printStackTrace();
				}
	        }
	        rs.close();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	    	OperateDB.closeStatement(sm);
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    }
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		String dbName = "studentdb";
		String userName = "test";
		String password = "test";
		
		Connection con = null;
		try {
			// 获得数据库连接
			con = DBConnector.getMySQLConnection(null, null, null, dbName,
					userName, password);
			// 写Blob数据
			BlobData.writeBlob(con, "C:/temp/mary_photo.jpg");
			// 读Blob数据
			BlobData.readBlob(con, "mary_photo.jpg", "C:/temp/mary_photo_db.jpg");
		} catch (ClassNotFoundException e1) {
			throw e1;
		} catch (SQLException e2) {
			throw e2;
		} finally {
			// 关闭数据库连接
			OperateDB.closeConnection(con);
		}
	}
}
