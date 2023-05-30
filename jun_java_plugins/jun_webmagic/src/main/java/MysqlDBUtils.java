

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by cfq on 2017/4/30.
 */
public class MysqlDBUtils {
    private static Connection getConn() {
        String confile = "druid.properties";//配置文件名称
        Properties properties = new Properties();
        InputStream inputStream = null;
        DruidDataSource dataSource = null;
        Connection connection = null;

        confile = MysqlDBUtils.class.getResource("/").getPath() + confile;//获取配置文件路径

        File file = new File(confile);
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            properties.load(inputStream);//加载配置文件


            //通过DruidDataSourceFactory获取javax.sql.DataSource
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            connection = dataSource.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void selectTest() {

        //以下为一个查询的例子，和正常的jdbc操作??
        String sql = "select * from sys_user";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) getConn().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println("");
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/*
	 * public static int insert(GankModel gankModel) { Connection conn = getConn();
	 * int i = 0; String sql = "insert into gankinfo (title,content) values(?,?)";
	 * PreparedStatement pstmt; try { pstmt = (PreparedStatement)
	 * conn.prepareStatement(sql); pstmt.setString(1, gankModel.getTitle());
	 * pstmt.setString(2, gankModel.getContent()); i = pstmt.executeUpdate();
	 * pstmt.close(); conn.close(); } catch (SQLException e) { e.printStackTrace();
	 * } return i; }
	 */

    public static void main(String[] args) {
        MysqlDBUtils.selectTest();

    }
}
