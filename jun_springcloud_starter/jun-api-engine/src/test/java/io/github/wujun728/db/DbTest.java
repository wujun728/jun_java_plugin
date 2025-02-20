package io.github.wujun728.db;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.db.utils.SqlUtils;
import io.github.wujun728.sql.entity.ApiSql;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static io.github.wujun728.db.utils.DataSourcePool.main;


public class DbTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        //Db.init(main,dataSource);

    }

    //@Before
    @BeforeClass
    public static void init() {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(dataSource);
    }

    @Test
    public void testqueryStr() throws Exception {
        String sqlId = Db.use(main).queryForString("select sql_text from api_sql  limit 1 ",null);
        StaticLog.info(JSONUtil.toJsonStr(sqlId));

    }
//    @Test
//    public void testquerySqlXml() throws Exception {
//        String sql = " SELECT t.update_time, t.table_id, t.table_name, t.table_comment, t.sub_table_name, t.sub_table_fk_name, t.class_name, t.tpl_category, t.package_name, t.module_name, t.business_name, t.function_name, t.function_author, t.gen_type, t.gen_path, t.options, t.remark,\n" +
//                "\t\t\t   c.column_id, c.column_name, c.column_comment, c.column_type, c.java_type, c.java_field, c.is_pk, c.is_increment, c.is_required, c.is_insert, c.is_edit, c.is_list, c.is_query, c.query_type, c.html_type, c.dict_type, c.sort\n" +
//                "\t\tFROM gen_table t\n" +
//                "\t\t\t LEFT JOIN gen_table_column c ON t.table_id = c.table_id\n" +
//                "\t\twhere t.table_name = #{tableName} order by c.sort ";
//        List<Map<String, Object>> result = Db.use(main).querySqlXml(sql,MapUtil.of("tableName","biz_test"));
//        StaticLog.info(JSONUtil.toJsonStr(result));
//        List<Record> records  = RecordUtil.mappingList(result);
//        StaticLog.info(JSONUtil.toJsonStr(records));
//    }

    @Test
    public void testfindByColumnValueRecords() throws Exception {
        List<Record> result = Db.use(main).findByColumnValueRecords("api_sql","group","default");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }
     
    @Test
    public void testFindById() throws Exception {
        Record result = Db.use(main).findById("biz_test", "11");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testFindBySql() throws Exception {
        List<Record> result = Db.use(main).find(" select * from api_sql ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }
    @Test
    public void testFindBySql2() throws Exception {
        List<Record> result = Db.use(main).find(" select * from api_sql where id = ? ",new Object[]{1});
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testfindById() throws Exception {
        Record result1 = Db.use(main).findById("api_sql", "id,sql_id","1",1);
        Record result = Db.use(main).findById("api_sql", "id,sql_id", "2","getBizTests");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testDeleteById() throws Exception {
        Boolean result = Db.use(main).deleteById("biz_test", "1111");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteByIds() throws Exception {
        Boolean result = Db.use(main).deleteById("api_sql", "sql_id","getBizTests111");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testFind2() throws Exception {
        List<Record> result = Db.use(main).find(" select * from api_sql where 1=1 and sql_id = ?", "getBizTests");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testPaginate() throws Exception {
        Page<Record> result = Db.use(main).paginate(Integer.valueOf(1), Integer.valueOf(10), " select * ", " from api_sql where 1=1 ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }
    @Test
    public void testPaginate2() throws Exception {
        Page<Record> result = Db.use(main).paginate(Integer.valueOf(1), Integer.valueOf(10), " select * ", " from api_sql where 1=1  ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }


    @Test
    public void testSave() throws Exception {
        Record record = new Record();
        record.set("sql_id", "queryTest"+RandomUtil.randomInt());
        record.set("id", RandomUtil.randomInt());
        record.set("sql_text", "select * from biz_test");
        record.set("group", "default");
        boolean result = Db.use(main).save("api_sql", record);
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testUpdate() throws Exception {
        Record record = new Record();
        record.set("sql_id", "queryTest-153736241");
        record.set("id", 1243333560);
        record.set("sql_text", "select * from biz_test");
        record.set("group", "default");
        boolean result = Db.use(main).update("api_sql", record);
        StaticLog.info(String.valueOf(result));
    }


    @Test
    public void testQuery() throws Exception {
        List result = Db.use(main).find(" select * from api_sql where sql_id = ?", "queryTest");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testQuery2() throws Exception {
        List result = Db.use(main).queryList(" select * from api_sql where sql_id ='queryTest' ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testUpdate2() throws Exception {
        int result = Db.use(main).execute("  update api_sql set datasource_id=datasource_id||'1' where sql_id ='queryTest'  ");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteById2() throws Exception {
        boolean result = Db.use(main).deleteById("api_sql", "sql_id","queryTest-1242227800");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDeleteById3() throws Exception {
        boolean result = Db.use(main).deleteById("api_sql", "id,sql_id", "-1946801918","queryTest-1242227800");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDelete() throws Exception {
        int result = Db.use(main).execute("delete from api_sql where sql_id = ? ", new Object[]{"paras"});
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void testDelete2() throws Exception {
        int result = Db.use(main).execute("delete from api_sql where sql_id = 'paras' ");
        StaticLog.info(String.valueOf(result));
    }

    @Test
    public void findEntityList() throws Exception {
        List<ApiSql> result = Db.use(main).findBeanList(ApiSql.class," select * from api_sql ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

//    @Test
//    public void testSaveBackId() throws Exception {
//        ApiSql sql = new ApiSql();
//        sql.setSqlId("test1"+RandomUtil.randomNumbers(9));
//        sql.setSqlText("select * from test1");
//        sql.setGroup("test11");
//        Integer result = Db.use(main).saveBeanBackPrimaryKey(sql);
//        StaticLog.info(String.valueOf(result));
//    }

//    @Test
//    public void testSave2() throws Exception {
//        ApiSqlMybatis sql = new ApiSqlMybatis();
//        sql.setSqlId("test1"+RandomUtil.randomNumbers(9));
//        sql.setSqlTextq("select * from test1");
//        sql.setGroup("test11");
//        Integer result = Db.use(main).saveBean(sql);
//        StaticLog.info(String.valueOf(result));
//    }

//    @Test
//    public void testUpdate3() throws Exception {
//        ApiSqlMybatis sql = new ApiSqlMybatis();
//        sql.setSqlId("test1449740241");
//        sql.setSqlTextq("select * from test1");
//        sql.setGroup("test11");
//        sql.setId(1243333565);
//        Integer result = Db.use(main).updateBean(sql);
//        StaticLog.info(String.valueOf(result));
//    }


//    @Test
//    public void testDelete3() throws Exception {
//        ApiSqlMybatis sql = new ApiSqlMybatis();
//        sql.setSqlId("test1449740241");
////        sql.setSqlText("select * from test1");
////        sql.setGroup("test11");
//        sql.setId(1243333565);
//        Integer result = Db.use(main).deleteBean(sql);
//        StaticLog.info(String.valueOf(result));
//    }


    @Test
    public void testGetById() throws Exception {
        ApiSql result = (ApiSql) Db.use(main).findBeanById(ApiSql.class,"id,sql_id",1243333563,"test1622823114");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }
    @Test
    public void testGetByParams() throws Exception {
//        ApiSql result = (ApiSql) Db.use(main).findBeanById(ApiSql.class,1243333563/*,"test1622823114"*/);
//        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testQueryAll() throws Exception {
        List result = Db.use(main).find(SqlUtils.getSelect(ApiSql.class).getSql());
        StaticLog.info(JSONUtil.toJsonStr(result));
    }

    @Test
    public void testCount() throws Exception {
        Integer result = Db.use(main).count(" select count(1) from api_sql ");
        StaticLog.info(String.valueOf(result));
    }
    @Test
    public void testCoun11t() throws Exception {
        List<ApiSql> apiSqls = Db.use(main).findBeanList(ApiSql.class," select * from api_sql ");
        System.out.println(JSONUtil.toJsonStr(apiSqls));
    }


//    @Test
//    public void testQueryPage() throws Exception {
//        Page result = Db.use(main).findBeanPages(ApiSql.class, 1, 10);
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }
//    @Test
//    public void testQueryPage2() throws Exception {
//        Page result = Db.use(main).findBeanPages(ApiSql.class, 1, 10, Maps.newHashMap());
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }
//    @Test
//    public void testQueryPage2111() throws Exception {
////        Db.use(main).findBeanPages()
//    }
}
