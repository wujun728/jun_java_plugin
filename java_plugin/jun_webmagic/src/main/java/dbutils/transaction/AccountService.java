package dbutils.transaction;


import java.sql.Connection;
import java.sql.SQLException;

import dbutils.DBUtils;

/**
* @ClassName: AccountService
* @Description: 业务逻辑处理层
* @author: 孤傲苍狼
* @date: 2014-10-6 下午5:30:15
*
*/ 
public class AccountService {
    
    /**
    * @Method: transfer
    * @Description:这个方法是用来处理两个用户之间的转账业务
    * @Anthor:孤傲苍狼
    *
    * @param sourceid
    * @param tartgetid
    * @param money
    * @throws SQLException
    */ 
    public void transfer(int sourceid,int tartgetid,float money) throws SQLException{
        Connection conn = null;
        try{
            //获取数据库连接
            conn = DBUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //将获取到的Connection传递给AccountDao，保证dao层使用的是同一个Connection对象操作数据库
            AccountDao dao = new AccountDao(conn);
            Account source = dao.find(sourceid);
            Account target = dao.find(tartgetid);
            
            source.setMoney(source.getMoney()-money);
            target.setMoney(target.getMoney()+money);
            
            dao.update(source);
            //模拟程序出现异常让事务回滚
            int x = 1/0;
            dao.update(target);
            //提交事务
            conn.commit();
        }catch (Exception e) {
            e.printStackTrace();
            //出现异常之后就回滚事务
            conn.rollback();
        }finally{
            conn.close();
        }
    }
}