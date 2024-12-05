package io.github.wujun728.db.test;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;

import javax.sql.DataSource;

import static io.github.wujun728.db.record.Db.main;

public class TestDb {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
        Db.init(main,ds);
        Record record = Db.findById("biz_mail",2);
        Record record2 = Db.findById("api_sql","id,sql_id","getBizTests");
    }


    public static void main1(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        //registerRecord("db_qixing_bk");
        Db.init(main,dataSource);
        //查询数据并返回单条Record
        Record record = Db.findById("biz_mail",new Object[]{2,"getBizTests"});
//        record.set()
        StaticLog.info(JSONUtil.toJsonPrettyStr(record));

//        List data1 = Db.use(main).queryAll(ApiSql.class);
//
//        //查询数据并转为Bean清单
//        List<ApiSql> data2 = Db.queryForBeanList(" SELECT * FROM api_sql ", ApiSql.class,null);
//
//        Page<Record> data3 = Db.paginate(1,10," SELECT * "," FROM api_sql ");
//        StaticLog.info(JSONUtil.toJsonPrettyStr(data3));
    }
}
