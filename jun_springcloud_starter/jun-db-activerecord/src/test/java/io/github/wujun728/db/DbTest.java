package io.github.wujun728.db;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.SqlUtil;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.rest.entity.ApiSqlMybatis;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static io.github.wujun728.db.Db.main;


public class DbTest {


    //@Before
    @BeforeClass
    public static void init() {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(main,dataSource);
    }

    @Test
    public void testFindById() throws Exception {
        Record result = Db.findById("biz_mail", "11");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testFindBySql() throws Exception {
        List<Record> result = Db.find(" select * from biz_mail ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }
    @Test
    public void testFindBySql2() throws Exception {
        List<Record> result = Db.find(" select * from biz_mail where id = ? ",1);
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testFindByIds() throws Exception {
        Record result1 = Db.findById("api_sql", "id,sql_id", "1",1);
        Record result = Db.findById("api_sql", "id,sql_id", "2","getBizTests");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testDeleteById() throws Exception {
        Boolean result = Db.deleteById("biz_mail", "1111");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteByIds() throws Exception {
        Boolean result = Db.deleteById("api_sql", "id,sql_id", "2","getBizTests111");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testFind2() throws Exception {
        List<Record> result = Db.find(" select * from api_sql where 1=1 and sql_id = ?", "getBizTests");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testPaginate() throws Exception {
        Page<Record> result = Db.paginate(Integer.valueOf(1), Integer.valueOf(10), " select * ", " from api_sql where 1=1 ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }
    @Test
    public void testPaginate2() throws Exception {
        Page<Record> result = Db.paginate(Integer.valueOf(1), Integer.valueOf(10), " select * ", " from api_sql where 1=1  ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }


    @Test
    public void testSave() throws Exception {
        Record record = new Record();
        record.set("sql_id", "queryTest"+RandomUtil.randomInt());
        record.set("id", RandomUtil.randomInt());
        record.set("sql_text", "select * from biz_test");
        record.set("group", "default");
        boolean result = Db.save("api_sql", record);
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testUpdate() throws Exception {
        Record record = new Record();
        record.set("sql_id", "queryTest-153736241");
        record.set("id", 1243333560);
        record.set("sql_text", "select * from biz_test");
        record.set("group", "default");
        boolean result = Db.update("api_sql", record);
        StaticLog.info(String.valueOf(result));
    }


    @Test
    public void testQuery() throws Exception {
        List result = Db.find(" select * from api_sql where sql_id = ?", "queryTest");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testQuery2() throws Exception {
        List result = Db.queryList(" select * from api_sql where sql_id ='queryTest' ");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testUpdate2() throws Exception {
        int result = Db.update("  update api_sql set datasource_id=datasource_id||'1' where sql_id ='queryTest'  ");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteById2() throws Exception {
        boolean result = Db.deleteById("api_sql", "sql_id","queryTest-1242227800");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteById3() throws Exception {
        boolean result = Db.deleteById("api_sql", "id,sql_id", "-1946801918","queryTest-1242227800");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDelete() throws Exception {
        Boolean result = Db.deleteBySql("delete from api_sql where sql_id = ? ", "paras");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDelete2() throws Exception {
        boolean result = Db.deleteBySql("delete from api_sql where sql_id = 'paras' ");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void findEntityList() throws Exception {
        List<ApiSql> result = Db.findList(ApiSql.class," select * from api_sql ",  null);
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testSaveBackId() throws Exception {
        ApiSql sql = new ApiSql();
        sql.setSqlId("test1"+RandomUtil.randomNumbers(9));
        sql.setSqlText("select * from test1");
        sql.setGroup("test11");
        Integer result = Db.saveBackPrimaryKey(sql);
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testSave2() throws Exception {
        ApiSqlMybatis sql = new ApiSqlMybatis();
        sql.setSqlId("test1"+RandomUtil.randomNumbers(9));
        sql.setSqlTextq("select * from test1");
        sql.setGroup("test11");
        Integer result = Db.save(sql);
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testUpdate3() throws Exception {
        ApiSqlMybatis sql = new ApiSqlMybatis();
        sql.setSqlId("test1449740241");
        sql.setSqlTextq("select * from test1");
        sql.setGroup("test11");
        sql.setId(1243333565);
        Integer result = Db.update(sql);
        StaticLog.info(String.valueOf(result));
    }


    @Test
    public void testDelete3() throws Exception {
        ApiSqlMybatis sql = new ApiSqlMybatis();
        sql.setSqlId("test1449740241");
//        sql.setSqlText("select * from test1");
//        sql.setGroup("test11");
        sql.setId(1243333565);
        Integer result = Db.delete(sql);
        StaticLog.info(String.valueOf(result));
    }


    @Test
    public void testGetById() throws Exception {
        ApiSql result = (ApiSql) Db.findById(ApiSql.class,1243333563,"test1622823114");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }
    @Test
    public void testGetByParams() throws Exception {
        ApiSql result = (ApiSql) Db.findById(ApiSql.class,new String[]{"id","sql_id"},1243333563,"test1622823114");
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testQueryAll() throws Exception {
        List result = Db.find(SqlUtil.getSelect(ApiSql.class).getSql());
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }

    @Test
    public void testCount() throws Exception {
        Integer result = Db.count(" select count(1) from api_sql ");
        StaticLog.info(String.valueOf(result));
    }
    @Test
    public void testCoun11t() throws Exception {
        List<ApiSql> apiSqls = Db.use(main).findList(ApiSql.class," select * from api_sql ");
        System.out.println(JSONUtil.toJsonPrettyStr(apiSqls));
    }


    @Test
    public void testQueryPage() throws Exception {
        Page result = Db.use().findPages(ApiSql.class, 1, 10);
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }
    @Test
    public void testQueryPage2() throws Exception {
        Page result = Db.findPages(ApiSql.class, 1, 10, Maps.newHashMap());
        StaticLog.info(JSONUtil.toJsonPrettyStr(result));
    }
}
