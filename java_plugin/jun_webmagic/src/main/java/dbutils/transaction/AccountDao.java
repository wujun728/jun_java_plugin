package dbutils.transaction;


import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import dbutils.DBUtils;

/*account娴嬭瘯琛�
create table account(
    id int primary key auto_increment,
    name varchar(40),
    money float
)character set utf8 collate utf8_general_ci;

insert into account(name,money) values('A',1000);
insert into account(name,money) values('B',1000);
insert into account(name,money) values('C',1000);

*/

/**
* @ClassName: AccountDao
* @Description: 閽堝Account瀵硅薄鐨凜RUD
* @author: 瀛ゅ偛鑻嶇嫾
* @date: 2014-10-6 涓嬪崍4:00:42
*
*/ 
public class AccountDao {

    private Connection conn = null;
    
    public AccountDao(Connection conn){
        this.conn = conn;
    }
    
    public AccountDao(){
        
    }
    
    /**
    * @Method: update
    * @Description:鏇存柊
    * @Anthor:瀛ゅ偛鑻嶇嫾
    *
    * @param account
    * @throws SQLException
    */ 
    public void update(Account account) throws SQLException{
        
        QueryRunner qr = new QueryRunner();
        String sql = "update account set name=?,money=? where id=?";
        Object params[] = {account.getName(),account.getMoney(),account.getId()};
        //浣跨敤service灞備紶閫掕繃鏉ョ殑Connection瀵硅薄鎿嶄綔鏁版嵁搴�
        qr.update(conn,sql, params);
        
    }
    
    /**
    * @Method: find
    * @Description:鏌ユ壘
    * @Anthor:瀛ゅ偛鑻嶇嫾
    *
    * @param id
    * @return
    * @throws SQLException
    */ 
    public Account find(int id) throws SQLException{
        QueryRunner qr = new QueryRunner();
        String sql = "select * from account where id=?";
        //浣跨敤service灞備紶閫掕繃鏉ョ殑Connection瀵硅薄鎿嶄綔鏁版嵁搴�
        return (Account) qr.query(conn,sql, id, new BeanHandler(Account.class));
    }
    
    /**
     * @Method: transfer
     * @Description:杩欎釜鏂规硶鏄敤鏉ュ鐞嗕袱涓敤鎴蜂箣闂寸殑杞处涓氬姟
     * 鍦ㄥ紑鍙戜腑锛孌AO灞傜殑鑱岃矗搴旇鍙秹鍙婂埌CRUD锛�
     * 鑰岃繖涓猼ransfer鏂规硶鏄鐞嗕袱涓敤鎴蜂箣闂寸殑杞处涓氬姟鐨勶紝宸茬粡娑夊強鍒板叿浣撶殑涓氬姟鎿嶄綔锛屽簲璇ュ湪涓氬姟灞備腑鍋氾紝涓嶅簲璇ュ嚭鐜板湪DAO灞傜殑
     * 鎵�浠ュ湪寮�鍙戜腑DAO灞傚嚭鐜拌繖鏍风殑涓氬姟澶勭悊鏂规硶鏄畬鍏ㄩ敊璇殑
     * @Anthor:瀛ゅ偛鑻嶇嫾
     *
     * @param sourceName
     * @param targetName
     * @param money
     * @throws SQLException
     */ 
     public void transfer(String sourceName,String targetName,float money) throws SQLException{
         Connection conn = null;
         try{
             conn = DBUtils.getConnection();
             //寮�鍚簨鍔�
             conn.setAutoCommit(false);
             /**
              * 鍦ㄥ垱寤篞ueryRunner瀵硅薄鏃讹紝涓嶄紶閫掓暟鎹簮缁欏畠锛屾槸涓轰簡淇濊瘉杩欎袱鏉QL鍦ㄥ悓涓�涓簨鍔′腑杩涜锛�
              * 鎴戜滑鎵嬪姩鑾峰彇鏁版嵁搴撹繛鎺ワ紝鐒跺悗璁╄繖涓ゆ潯SQL浣跨敤鍚屼竴涓暟鎹簱杩炴帴鎵ц
              */
             QueryRunner runner = new QueryRunner();
             String sql1 = "update account set money=money-100 where name=?";
             String sql2 = "update account set money=money+100 where name=?";
             Object[] paramArr1 = {sourceName};
             Object[] paramArr2 = {targetName};
             runner.update(conn,sql1,paramArr1);
             //妯℃嫙绋嬪簭鍑虹幇寮傚父璁╀簨鍔″洖婊�
             int x = 1/0;
             runner.update(conn,sql2,paramArr2);
             //sql姝ｅ父鎵ц涔嬪悗灏辨彁浜や簨鍔�
             conn.commit();
         }catch (Exception e) {
             e.printStackTrace();
             if(conn!=null){
                 //鍑虹幇寮傚父涔嬪悗灏卞洖婊氫簨鍔�
                 conn.rollback();
             }
         }finally{
             //鍏抽棴鏁版嵁搴撹繛鎺�
             conn.close();
         }
     }
}