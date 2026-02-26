/* 已移除 - DataSourcePool功能已合并到Db.java，测试已迁移到DbActiveRecordTest.java
package io.github.wujun728.db.test;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;

import javax.sql.DataSource;

import static io.github.wujun728.db.utils.DataSourcePool.main;


public class TestDb {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
        Db.init(main,ds);
        Record record = Db.use().findById("biz_mail",2);
        Record record2 = Db.use().findById("api_sql","id,sql_id","getBizTests");
    }


    public static void main1(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(main,dataSource);
        Record record = Db.use().findById("biz_mail",new Object[]{2,"getBizTests"});
        StaticLog.info(JSONUtil.toJsonPrettyStr(record));
    }
}
*/
