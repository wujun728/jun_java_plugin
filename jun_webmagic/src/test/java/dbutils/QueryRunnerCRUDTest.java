package dbutils;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;


/**
*ResultSetHandler鎺ュ彛鐨勫疄鐜扮被
ArrayHandler锛氭妸缁撴灉闆嗕腑鐨勭涓�琛屾暟鎹浆鎴愬璞℃暟缁勩��
ArrayListHandler锛氭妸缁撴灉闆嗕腑鐨勬瘡涓�琛屾暟鎹兘杞垚涓�涓暟缁勶紝鍐嶅瓨鏀惧埌List涓��
BeanHandler锛氬皢缁撴灉闆嗕腑鐨勭涓�琛屾暟鎹皝瑁呭埌涓�涓搴旂殑JavaBean瀹炰緥涓��
BeanListHandler锛氬皢缁撴灉闆嗕腑鐨勬瘡涓�琛屾暟鎹兘灏佽鍒颁竴涓搴旂殑JavaBean瀹炰緥涓紝瀛樻斁鍒癓ist閲屻��
ColumnListHandler锛氬皢缁撴灉闆嗕腑鏌愪竴鍒楃殑鏁版嵁瀛樻斁鍒癓ist涓��
KeyedHandler(name)锛氬皢缁撴灉闆嗕腑鐨勬瘡涓�琛屾暟鎹兘灏佽鍒颁竴涓狹ap閲岋紝鍐嶆妸杩欎簺map鍐嶅瓨鍒颁竴涓猰ap閲岋紝鍏秌ey涓烘寚瀹氱殑key銆�
MapHandler锛氬皢缁撴灉闆嗕腑鐨勭涓�琛屾暟鎹皝瑁呭埌涓�涓狹ap閲岋紝key鏄垪鍚嶏紝value灏辨槸瀵瑰簲鐨勫�笺��
MapListHandler锛氬皢缁撴灉闆嗕腑鐨勬瘡涓�琛屾暟鎹兘灏佽鍒颁竴涓狹ap閲岋紝鐒跺悗鍐嶅瓨鏀惧埌List
*/ 

public class QueryRunnerCRUDTest {

    /*
     *娴嬭瘯琛�
     create table users(
         id int primary key auto_increment, 
         name varchar(40),
         password varchar(40), 
         email varchar(60), 
         birthday date 
     );
     */
    
    @Test
    public void add() throws SQLException {
        //灏嗘暟鎹簮浼犻�掔粰QueryRunner锛孮ueryRunner鍐呴儴閫氳繃鏁版嵁婧愯幏鍙栨暟鎹簱杩炴帴
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into users(name,password,email,birthday) values(?,?,?,?)";
        Object params[] = {"1111","11122", "gacl@sina.com", new Date()};
        //Object params[] = {"鐧借檸绁炵殗","123", "gacl@sina.com", "1988-05-07"};
        qr.update(sql, params);
    }
    
    @Test
    public void delete() throws SQLException {

        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "delete from users where id=?";
        qr.update(sql, 1);

    }

    @Test
    public void update() throws SQLException {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "update users set name=? where id=?";
        Object params[] = { "ddd", 5};
        qr.update(sql, params);
    }

    @Test
    public void find() throws SQLException {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users where id=?";
        Object params[] = {2};
//        User user = (User) qr.query(sql, params, new BeanHandler(User.class));
//        System.out.println(user.getBirthday());
    }

    @Test
    public void getAll() throws SQLException {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "select * from users";
//        List list = (List) qr.query(sql, new BeanListHandler(User.class));
//        System.out.println(list.size());
    }

    /**
    * @Method: testBatch
    * @Description:鎵瑰鐞�
    * @Anthor:瀛ゅ偛鑻嶇嫾
    *
    * @throws SQLException
    */ 
    @Test
    public void testBatch() throws SQLException {
        QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into users(name,password,email,birthday) values(?,?,?,?)";
        Object params[][] = new Object[10][];
        for (int i = 0; i < 10; i++) {
            params[i] = new Object[] { "aa" + i, "123", "aa@sina.com",
                    new Date() };
        }
        qr.batch(sql, params);
    }
    
    //鐢╠butils瀹屾垚澶ф暟鎹紙涓嶅缓璁敤锛�
    /***************************************************************************
     create table testclob
     (
         id int primary key auto_increment,
         resume text
     );
     **************************************************************************/
    @Test
    public void testclob() throws SQLException, IOException{
        QueryRunner runner = new QueryRunner(DBUtils.getDataSource());
        String sql = "insert into testclob(resume) values(?)";  //clob
        //杩欑鏂瑰紡鑾峰彇鐨勮矾寰勶紝鍏朵腑鐨勭┖鏍间細琚娇鐢ㄢ��%20鈥濅唬鏇�
        String path  = QueryRunnerCRUDTest.class.getClassLoader().getResource("data.xml").getPath();
        //灏嗏��%20鈥濇浛鎹㈠洖绌烘牸
        path = path.replaceAll("%20", " ");
        FileReader in = new FileReader(path);
        char[] buffer = new char[(int) new File(path).length()];
        in.read(buffer);
        SerialClob clob = new SerialClob(buffer);
        Object params[] = {clob};
        runner.update(sql, params);
    }
}