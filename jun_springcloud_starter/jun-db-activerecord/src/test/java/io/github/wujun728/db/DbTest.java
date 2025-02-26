package io.github.wujun728.db;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.record.IAtom;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.utils.DataSourcePool;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static io.github.wujun728.db.utils.DataSourcePool.main;


public class DbTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        //String sqlId = Db.queryStr("select sql_text from api_sql  limit 1 ");
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        Db.init(main,dataSource);
        Db.init(main,dataSource);
    }

    @Test
    public void testIAtom() throws Exception {
        Db.tx(() -> {
            try {
                Record r = new Record();
                r.set("id", "ddddd");
                Db.save("base_basket", r);

                r.set("id", "ddddd");
                r.set("remarks", "remarks");
                Db.update("base_basket", "id", r);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        });

        String sqlId = Db.use(main).queryStr("select sql_text from api_sql  limit 1 ");
        StaticLog.info(JSONUtil.toJsonStr(sqlId));

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
        String sqlId = Db.use(main).queryStr("select sql_text from api_sql  limit 1 ");
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

    /*@Test
    public void testfindByColumnValueRecords() throws Exception {
        List<Record> result = Db.use(main).findByColumnValueRecords("api_sql","group","default");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }*/
//    @Test
//    public void testfindByColumnValueBeans() throws Exception {
//        List<ApiSql> result = Db.use(main).findByColumnValueBeans(ApiSql.class,"group,sql_id","default","queryTest45765727");
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }
//    @Test
//    public void testfindByWhereSqlForBean() throws Exception {
//        List<ApiSql> result = Db.use(main).findByWhereSqlForBean(ApiSql.class,"`group`=? and `sql_id`=? ","default","queryTest45765727");
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }
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
    public void testFindByIds() throws Exception {
        Record result1 = Db.use(main).findByIds("api_sql", "id,sql_id","1",1);
        Record result = Db.use(main).findByIds("api_sql", "id,sql_id", "2","getBizTests");
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
        List result = Db.use(main).query(" select * from api_sql where sql_id ='queryTest' ");
        StaticLog.info(JSONUtil.toJsonStr(result));
    }


    @Test
    public void testDeleteById2() throws Exception {
        boolean result = Db.use(main).deleteById("api_sql", "sql_id","queryTest-1242227800");
        StaticLog.info(String.valueOf(result));
    }





//    @Test
//    public void findEntityList() throws Exception {
//        List<ApiSql> result = Db.use(main).findBeanList(ApiSql.class," select * from api_sql ");
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }

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


//    @Test
//    public void testGetById() throws Exception {
//        ApiSql result = (ApiSql) Db.use(main).findBeanByIds(ApiSql.class,"id,sql_id",1243333563,"test1622823114");
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }
    @Test
    public void testGetByParams() throws Exception {
//        ApiSql result = (ApiSql) Db.use(main).findBeanById(ApiSql.class,1243333563/*,"test1622823114"*/);
//        StaticLog.info(JSONUtil.toJsonStr(result));
    }

//    @Test
//    public void testQueryAll() throws Exception {
//        List result = Db.use(main).find(SqlUtil.getSelect(ApiSql.class).getSql());
//        StaticLog.info(JSONUtil.toJsonStr(result));
//    }

    @Test
    public void testCount() throws Exception {
        Integer result = Db.use(main).queryInt(" select count(1) from api_sql ");
        StaticLog.info(String.valueOf(result));
    }
//    @Test
//    public void testCoun11t() throws Exception {
//        List<ApiSql> apiSqls = Db.use(main).findBeanList(ApiSql.class," select * from api_sql ");
//        System.out.println(JSONUtil.toJsonStr(apiSqls));
//    }


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

























   /* @Test
    public void testGetPkNames() throws Exception {
        String result = Db.use(main).getPkNames("c_user");
        StaticLog.info(result);
    }*/



    @Test
    public void testQueryList() throws Exception {
        List<Map<String, Object>> datas = Db.use(main).query(" select * from c_user ");
        System.out.println(JSONUtil.toJsonPrettyStr(datas));

    }

    @Test
    public void testQueryList2() throws Exception {
//        List<Map<String, Object>> datas = Db.use(main).queryList(" select * from c_user  where id_ = ? ",new Object[]{"-8599623109329969322"});
//        System.out.println(JSONUtil.toJsonPrettyStr(datas));
//
//        Map<String, Object> result1 = Db.use(main).queryForMap(" select * from c_user   where id_=? ", new Object[]{-8599623109329969322L});
//        System.out.println(JSONUtil.toJsonPrettyStr(result1));
//
//        int result2 = Db.use(main).queryForInt(" select count(1) from c_user ", null);
//        System.out.println( result2 );
//
//        long result3 = Db.use(main).queryForLong(" select id_ from c_user limit 1 ", null);
//        System.out.println( result3 );
//
//        User result4 = Db.use(main).queryForObject(" select * from c_user  where id_=? ", new Object[]{-8599623109329969322L}, User.class);
//        System.out.println(JSONUtil.toJsonPrettyStr(result4));
//
//        String result5 =  Db.use(main).queryForString(" select mobile_ from c_user  where id_=? ", new Object[]{-8599623109329969322L});
//        System.out.println( result5 );

//        List data1 =  Db.use(main).queryList("select * from c_user ");
//        Page<Map> data111 =  Db.use(main).queryMapPages("select * from c_user ",1,10,null);
//        System.out.println( JSONUtil.toJsonPrettyStr(data111) );
        //Page<User> data1112 =  Db.use(main).queryBeanPage(User.class,1,10,"select * from c_user ");
        //System.out.println( JSONUtil.toJsonPrettyStr(data1112) );
    }


    @Test
    public void testSave11() throws Exception {
        Record record = new Record();
        record.set("id_", RandomUtil.randomInt());
        record.set("mobile_","321432");
        record.set("password_","31532-9999");
//         Db.use(main).save("c_user",record);
//        record.set("id_", RandomUtil.randomInt());
//         Db.use(main).save("c_user","_id",record);
        record.set("id_", RandomUtil.randomInt());
        //Object obj = RecordUtil.recordToBean(record,User.class);
        //Db.use(main).saveBean(obj);
        Db.use(main).update(" INSERT INTO c_user(`id_`,`mobile_`,`password_`) VALUES (?,?,?) ",new Object[]{RandomUtil.randomInt(),"abc-555","abc"});
    }
    @Test
    public void testUpdate11() throws Exception {
        Record record = new Record();
        record.set("id_", RandomUtil.randomInt());
        record.set("mobile_","321432");
        record.set("password_","31532-9999");
        //Db.use(main).update("c_user",record);
        Db.use(main).update("c_user","id_",record);
        //Object obj = RecordUtil.recordToBean(record,User.class);
        // Db.use(main).updateBean(obj);
        Db.use(main).update(" UPDATE c_user SET `mobile_`=?,`password_`=? WHERE `id_`=? ",new Object[]{"abc-555","abc","6"});
    }


    @Test
    public void testDelete11() throws Exception {
        Record record = new Record();
        record.set("id_", RandomUtil.randomInt());
        record.set("mobile_","321432");
        record.set("password_","31532-9999");
//         Db.use(main).delete("c_user",record);
//        Db.use(main).delete("c_user","id_",record);
//        Db.use(main).execute(" delete from c_user where id_ = 1",null);
//        Object obj = RecordUtil.recordToBean(record,User.class);
//        Db.use(main).deleteBean(obj);

        //Db.use(main).deleteById("c_user",66666);
        Db.use(main).deleteById("c_user","id_",66666);
        Db.use(main).deleteById("c_user","id_",6666);
    }




    @Test
    public void testFind11() throws Exception {
//        List<Record>  data1 = Db.use(main).find("select * from c_user");
//        System.out.println(data1.size());
//
//        List<Record>  data2 = Db.use(main).find("select * from c_user where id_=? ",2);
//        System.out.println(data2.size());
//
//        List<Record>  data3 = Db.use(main).findAll("c_user");
//        System.out.println(data3.size());

//        List<User>  data4 = Db.use(main).findBeanList(User.class,"select * from c_user");
//        System.out.println(data4.size());
//
//        List<User>  data5 = Db.use(main).findBeanList(User.class,"select * from c_user where id_=? ",2);
//        System.out.println(data5.size());

//        List<Record>  data6 = Db.use(main).findByColumnValueRecords("c_user","id_",2);
//        System.out.println(data6.size());

//        Record record1 = Db.use(main).findFirst("select * from c_user");
//        System.out.println(record1);

//        Record record11 = Db.use(main).findById("c_user",2);
//        System.out.println(JSONUtil.toJsonPrettyStr(record11));

//        Record record112 = Db.use(main).findById("c_user","id_",2);
//        System.out.println(JSONUtil.toJsonPrettyStr(record112));

        //User record11233 = Db.use(main).findBeanById(User.class,2);
//        User record11233 = Db.use(main).findBeanById(User.class,"id_",2);
//        System.out.println(JSONUtil.toJsonPrettyStr(record11233));

//        Page<User> record1123344 = Db.use(main).findBeanPages(User.class,1,10);

//        Page<User> record112334455 = Db.use(main).findBeanPages(User.class,1,10," select * from c_user where id_<>2 ");
//        System.out.println(JSONUtil.toJsonPrettyStr(record112334455));

    }



//
//    @Test
//    public void testQueryForDate() throws Exception {
//        Date result = Db.use(main).queryForDate("sql", new Object[]{"objects"});
//        Assert.assertEquals(new GregorianCalendar(2025, Calendar.FEBRUARY, 20, 11, 27).getTime(), result);
//    }
//
//    @Test
//    public void testQueryForList() throws Exception {
//        List<String> result = Db.use(main).queryForList("sql", new Object[]{"objects"}, "keyName");
//        Assert.assertEquals(Arrays.<String>asList("String"), result);
//    }
//
//    @Test
//    public void testQueryForList2() throws Exception {
//        List<Map<String, Object>> result = Db.use(main).queryForList("sql", new Object[]{"objects"});
//        Assert.assertEquals(Arrays.<Map<String, Object>>asList(new HashMap<String, Object>() {{
//            put("String", "Map");
//        }}), result);
//    }
//
//    @Test
//    public void testQueryForList3() throws Exception {
//        List<Map<String, Object>> result = Db.use(main).queryForList("sql", new Object[]{"objects"}, 0);
//        Assert.assertEquals(Arrays.<Map<String, Object>>asList(new HashMap<String, Object>() {{
//            put("String", "Map");
//        }}), result);
//    }
//
//    @Test
//    public void testQueryForList4() throws Exception {
//        List<T> result = Db.use(main).queryForList("sql", new Object[]{"objects"}, null);
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testQueryForList5() throws Exception {
//        List<T> result = Db.use(main).queryForList("sql", new Object[]{"objects"}, null);
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testExecute() throws Exception {
//        int result = Db.use(main).execute("sql", new Object[]{"objects"});
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testInsert() throws Exception {
//        long result = Db.use(main).insert("sql", new Object[]{"objects"});
//        Assert.assertEquals(0L, result);
//    }
//
//    @Test
//    public void testBatchExecute() throws Exception {
//        int result = Db.use(main).batchExecute("sql", Arrays.<Object>asList(new Object[]{"objectsList"}));
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testDoInTransaction() throws Exception {
//        int result = Db.use(main).doInTransaction(new BatchSql());
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testReplaceSqlCount() throws Exception {
//        String result = Db.use(main).replaceSqlCount("sql");
//        Assert.assertEquals("replaceMeWithExpectedResult", result);
//    }
//
//    @Test
//    public void testReplaceSqlOrder() throws Exception {
//        String result = Db.use(main).replaceSqlOrder("sql");
//        Assert.assertEquals("replaceMeWithExpectedResult", result);
//    }
//
//    @Test
//    public void testGetForList() throws Exception {
//        List<Map<String, Object>> result = Db.use(main).getForList("sql", Arrays.<String>asList("String"), 0, 0);
//        Assert.assertEquals(Arrays.<Map<String, Object>>asList(new HashMap<String, Object>() {{
//            put("String", "Map");
//        }}), result);
//    }
//
//    @Test
//    public void testGetCharCnt() throws Exception {
//        int result = Db.use(main).getCharCnt("sql");
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testGetTotalRows() throws Exception {
//        long result = Db.use(main).getTotalRows("sql", Arrays.<String>asList("String"));
//        Assert.assertEquals(0L, result);
//    }
//
//    @Test
//    public void testDeleteById() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        Boolean result = Db.use(main).deleteById("tableName", "idValues");
//        Assert.assertEquals(Boolean.TRUE, result);
//    }
//
//    @Test
//    public void testDeleteBySql() throws Exception {
//        Boolean result = Db.use(main).deleteBySql("sql", "paras");
//        Assert.assertEquals(Boolean.TRUE, result);
//    }
//
//    @Test
//    public void testDeleteBySql2() throws Exception {
//        Boolean result = Db.use(main).deleteBySql("sql");
//        Assert.assertEquals(Boolean.TRUE, result);
//    }
//
//    @Test
//    public void testFindBeanList() throws Exception {
//        List<T> result = Db.use(main).findBeanList(null, "sql");
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testFindBeanList2() throws Exception {
//        List result = Db.use(main).findBeanList(Class.forName("io.github.wujun728.db.record.DbPro"), "sql", "params");
//        Assert.assertEquals(Arrays.asList("String"), result);
//    }
//
//    @Test
//    public void testFindMapList() throws Exception {
//        List result = Db.use(main).findMapList(Class.forName("io.github.wujun728.db.record.DbPro"), "sql", "params");
//        Assert.assertEquals(Arrays.asList("String"), result);
//    }
//
//    @Test
//    public void testFindRecordList() throws Exception {
//        List result = Db.use(main).findRecordList("sql", "params");
//        Assert.assertEquals(Arrays.asList("String"), result);
//    }
//
//    @Test
//    public void testFindBeanById() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        T result = Db.use(main).findBeanById(null, "idValue");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testFindBeanByIds() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        T result = Db.use(main).findBeanByIds(Class.forName("io.github.wujun728.db.record.DbPro"), "primaryKeys", "idValue");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testFindBeanPages() throws Exception {
//        Page result = Db.use(main).findBeanPages(Class.forName("io.github.wujun728.db.record.DbPro"), 0, 0);
//        Assert.assertEquals(new Page(Arrays.asList("String"), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testFindBeanPages2() throws Exception {
//        Page result = Db.use(main).findBeanPages(Class.forName("io.github.wujun728.db.record.DbPro"), 0, 0, new HashMap<String, Object>() {{
//            put("String", "params");
//        }});
//        Assert.assertEquals(new Page(Arrays.asList("String"), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testQueryBeanPage() throws Exception {
//        Page result = Db.use(main).queryBeanPage(Class.forName("io.github.wujun728.db.record.DbPro"), 0, 0);
//        Assert.assertEquals(new Page(Arrays.asList("String"), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testQueryBeanPage2() throws Exception {
//        Page result = Db.use(main).queryBeanPage(Class.forName("io.github.wujun728.db.record.DbPro"), 0, 0, new HashMap<String, Object>() {{
//            put("String", "params");
//        }});
//        Assert.assertEquals(new Page(Arrays.asList("String"), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testQueryMapPages() throws Exception {
//        Page<Map> result = Db.use(main).queryMapPages("sql", 0, 0, new Object[]{"params"});
//        Assert.assertEquals(new Page<Map>(Arrays.<Map>asList(new HashMap() {{
//            put("String", "String");
//        }}), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testCount() throws Exception {
//        int result = Db.use(main).count("sql", "params");
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testFindRecordById() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        Record result = Db.use(main).findRecordById("tableName", "id");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testFindByColumnValueRecords() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        List<Record> result = Db.use(main).findByColumnValueRecords("tableName", "columnNames", "columnValues");
//        Assert.assertEquals(Arrays.<Record>asList(new Record()), result);
//    }
//
//    @Test
//    public void testFindByColumnValueBeans() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        List result = Db.use(main).findByColumnValueBeans(Class.forName("io.github.wujun728.db.record.DbPro"), "columnNames", "columnValues");
//        Assert.assertEquals(Arrays.asList("String"), result);
//    }
//
//    @Test
//    public void testFindByWhereSqlForBean() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        List result = Db.use(main).findByWhereSqlForBean(Class.forName("io.github.wujun728.db.record.DbPro"), "whereSql", "columnValues");
//        Assert.assertEquals(Arrays.asList("String"), result);
//    }
//
//    @Test
//    public void testPaginate() throws Exception {
//        Page<Record> result = Db.use(main).paginate(Integer.valueOf(0), Integer.valueOf(0), "select", "from");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testQueryMaps() throws Exception {
//        List<Map<String, Object>> result = Db.use(main).queryMaps("sql", "paras");
//        Assert.assertEquals(Arrays.<Map<String, Object>>asList(new HashMap<String, Object>() {{
//            put("String", "Map");
//        }}), result);
//    }
//
//    @Test
//    public void testQuery() throws Exception {
//        List<T> result = Db.use(main).query("sql", "paras");
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testQuery2() throws Exception {
//        List<T> result = Db.use(main).query("sql");
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testQueryFirst() throws Exception {
//        T result = Db.use(main).queryFirst("sql", "paras");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryFirstMap() throws Exception {
//        T result = Db.use(main).queryFirstMap("sql", "paras");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryFirst2() throws Exception {
//        T result = Db.use(main).queryFirst("sql");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryFirstMap2() throws Exception {
//        T result = Db.use(main).queryFirstMap("sql");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryColumn() throws Exception {
//        T result = Db.use(main).queryColumn("sql", "paras");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryColumn2() throws Exception {
//        T result = Db.use(main).queryColumn("sql");
//        Assert.assertEquals(new T(), result);
//    }
//
//    @Test
//    public void testQueryStr() throws Exception {
//        String result = Db.use(main).queryStr("sql", "paras");
//        Assert.assertEquals("replaceMeWithExpectedResult", result);
//    }
//
//    @Test
//    public void testQueryStr2() throws Exception {
//        String result = Db.use(main).queryStr("sql");
//        Assert.assertEquals("replaceMeWithExpectedResult", result);
//    }
//
//    @Test
//    public void testQueryInt() throws Exception {
//        Integer result = Db.use(main).queryInt("sql", "paras");
//        Assert.assertEquals(Integer.valueOf(0), result);
//    }
//
//    @Test
//    public void testQueryInt2() throws Exception {
//        Integer result = Db.use(main).queryInt("sql");
//        Assert.assertEquals(Integer.valueOf(0), result);
//    }
//
//    @Test
//    public void testQueryLong() throws Exception {
//        Long result = Db.use(main).queryLong("sql", "paras");
//        Assert.assertEquals(Long.valueOf(1), result);
//    }
//
//    @Test
//    public void testQueryLong2() throws Exception {
//        Long result = Db.use(main).queryLong("sql");
//        Assert.assertEquals(Long.valueOf(1), result);
//    }
//
//    @Test
//    public void testQueryDouble() throws Exception {
//        Double result = Db.use(main).queryDouble("sql", "paras");
//        Assert.assertEquals(Double.valueOf(0), result);
//    }
//
//    @Test
//    public void testQueryDouble2() throws Exception {
//        Double result = Db.use(main).queryDouble("sql");
//        Assert.assertEquals(Double.valueOf(0), result);
//    }
//
//    @Test
//    public void testQueryFloat() throws Exception {
//        Float result = Db.use(main).queryFloat("sql", "paras");
//        Assert.assertEquals(Float.valueOf(1.1f), result);
//    }
//
//    @Test
//    public void testQueryFloat2() throws Exception {
//        Float result = Db.use(main).queryFloat("sql");
//        Assert.assertEquals(Float.valueOf(1.1f), result);
//    }
//
//    @Test
//    public void testQueryBigDecimal() throws Exception {
//        BigDecimal result = Db.use(main).queryBigDecimal("sql", "paras");
//        Assert.assertEquals(new BigDecimal(0), result);
//    }
//
//    @Test
//    public void testQueryBigDecimal2() throws Exception {
//        BigDecimal result = Db.use(main).queryBigDecimal("sql");
//        Assert.assertEquals(new BigDecimal(0), result);
//    }
//
//    @Test
//    public void testQueryBigInteger() throws Exception {
//        BigInteger result = Db.use(main).queryBigInteger("sql", "paras");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryBigInteger2() throws Exception {
//        BigInteger result = Db.use(main).queryBigInteger("sql");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryBytes() throws Exception {
//        byte[] result = Db.use(main).queryBytes("sql", "paras");
//        Assert.assertArrayEquals(new byte[]{(byte) 0}, result);
//    }
//
//    @Test
//    public void testQueryBytes2() throws Exception {
//        byte[] result = Db.use(main).queryBytes("sql");
//        Assert.assertArrayEquals(new byte[]{(byte) 0}, result);
//    }
//
//    @Test
//    public void testQueryDate() throws Exception {
//        Date result = Db.use(main).queryDate("sql", "paras");
//        Assert.assertEquals(new GregorianCalendar(2025, Calendar.FEBRUARY, 20, 11, 27).getTime(), result);
//    }
//
//    @Test
//    public void testQueryDate2() throws Exception {
//        Date result = Db.use(main).queryDate("sql");
//        Assert.assertEquals(new GregorianCalendar(2025, Calendar.FEBRUARY, 20, 11, 27).getTime(), result);
//    }
//
//    @Test
//    public void testQueryLocalDateTime() throws Exception {
//        LocalDateTime result = Db.use(main).queryLocalDateTime("sql", "paras");
//        Assert.assertEquals(LocalDateTime.of(2025, Month.FEBRUARY, 20, 11, 27, 3), result);
//    }
//
//    @Test
//    public void testQueryLocalDateTime2() throws Exception {
//        LocalDateTime result = Db.use(main).queryLocalDateTime("sql");
//        Assert.assertEquals(LocalDateTime.of(2025, Month.FEBRUARY, 20, 11, 27, 3), result);
//    }
//
//    @Test
//    public void testQueryTime() throws Exception {
//        Time result = Db.use(main).queryTime("sql", "paras");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryTime2() throws Exception {
//        Time result = Db.use(main).queryTime("sql");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryTimestamp() throws Exception {
//        Timestamp result = Db.use(main).queryTimestamp("sql", "paras");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryTimestamp2() throws Exception {
//        Timestamp result = Db.use(main).queryTimestamp("sql");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryBoolean() throws Exception {
//        Boolean result = Db.use(main).queryBoolean("sql", "paras");
//        Assert.assertEquals(Boolean.TRUE, result);
//    }
//
//    @Test
//    public void testQueryBoolean2() throws Exception {
//        Boolean result = Db.use(main).queryBoolean("sql");
//        Assert.assertEquals(Boolean.TRUE, result);
//    }
//
//    @Test
//    public void testQueryShort() throws Exception {
//        Short result = Db.use(main).queryShort("sql", "paras");
//        Assert.assertEquals(Short.valueOf((short) 0), result);
//    }
//
//    @Test
//    public void testQueryShort2() throws Exception {
//        Short result = Db.use(main).queryShort("sql");
//        Assert.assertEquals(Short.valueOf((short) 0), result);
//    }
//
//    @Test
//    public void testQueryByte() throws Exception {
//        Byte result = Db.use(main).queryByte("sql", "paras");
//        Assert.assertEquals(Byte.valueOf("00110"), result);
//    }
//
//    @Test
//    public void testQueryByte2() throws Exception {
//        Byte result = Db.use(main).queryByte("sql");
//        Assert.assertEquals(Byte.valueOf("00110"), result);
//    }
//
//    @Test
//    public void testQueryNumber() throws Exception {
//        Number result = Db.use(main).queryNumber("sql", "paras");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testQueryNumber2() throws Exception {
//        Number result = Db.use(main).queryNumber("sql");
//        Assert.assertEquals(null, result);
//    }
//
//    @Test
//    public void testFind() throws Exception {
//        List<Record> result = Db.use(main).find("sql", "paras");
//        Assert.assertEquals(Arrays.<Record>asList(new Record()), result);
//    }
//
//    @Test
//    public void testFind2() throws Exception {
//        List<Record> result = Db.use(main).find("sql");
//        Assert.assertEquals(Arrays.<Record>asList(new Record()), result);
//    }
//
//    @Test
//    public void testFindMaps() throws Exception {
//        List<Map> result = Db.use(main).findMaps("sql");
//        Assert.assertEquals(Arrays.<Map>asList(new HashMap() {{
//            put("String", "String");
//        }}), result);
//    }
//
//    @Test
//    public void testFindObjs() throws Exception {
//        List<T> result = Db.use(main).findObjs(null, "sql");
//        Assert.assertEquals(Arrays.<T>asList(new T()), result);
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        when(dialect.forFindAll(anyString())).thenReturn("forFindAllResponse");
//
//        List<Record> result = Db.use(main).findAll("tableName");
//        Assert.assertEquals(Arrays.<Record>asList(new Record()), result);
//    }
//
//    @Test
//    public void testFindFirst() throws Exception {
//        Record result = Db.use(main).findFirst("sql", "paras");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testFindFirst2() throws Exception {
//        Record result = Db.use(main).findFirst("sql");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testFindById() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//        when(dialect.getDefaultPrimaryKey()).thenReturn("getDefaultPrimaryKeyResponse");
//
//        Record result = Db.use(main).findById("tableName", "idValue");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testFindById2() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        Record result = Db.use(main).findById("tableName", "primaryKey", "idValue");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testFindByIds() throws Exception {
//        when(dialect.forDbFindById(anyString(), anyString())).thenReturn("forDbFindByIdResponse");
//
//        Record result = Db.use(main).findByIds("tableName", "primaryKey", "idValues");
//        Assert.assertEquals(new Record(), result);
//    }
//
//    @Test
//    public void testDeleteById2() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//        when(dialect.getDefaultPrimaryKey()).thenReturn("getDefaultPrimaryKeyResponse");
//
//        boolean result = Db.use(main).deleteById("tableName", "idValue");
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDeleteByPrimaryKey() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        boolean result = Db.use(main).deleteByPrimaryKey("tableName", "idValue");
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDeleteById3() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        boolean result = Db.use(main).deleteById("tableName", "primaryKey", "idValue");
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDeleteByIds() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        boolean result = Db.use(main).deleteByIds("tableName", "primaryKey", "idValues");
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        boolean result = Db.use(main).delete("tableName", "primaryKey", new Record());
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDelete2() throws Exception {
//        when(dialect.forDbDeleteById(anyString(), anyString())).thenReturn("forDbDeleteByIdResponse");
//
//        boolean result = Db.use(main).delete("tableName", new Record());
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testDelete3() throws Exception {
//        int result = Db.use(main).delete("sql", "paras");
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testDelete4() throws Exception {
//        int result = Db.use(main).delete("sql");
//        Assert.assertEquals(0, result);
//    }
//
//    @Test
//    public void testPaginate2() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//        when(dialect.forPaginateTotalRow(anyString(), anyString(), any())).thenReturn("forPaginateTotalRowResponse");
//
//        Page<Record> result = Db.use(main).paginate(0, 0, "select", "sqlExceptSelect");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testPaginate3() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//        when(dialect.forPaginateTotalRow(anyString(), anyString(), any())).thenReturn("forPaginateTotalRowResponse");
//
//        Page<Record> result = Db.use(main).paginate(0, 0, true, "select", "sqlExceptSelect", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testDoPaginate() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//        when(dialect.forPaginateTotalRow(anyString(), anyString(), any())).thenReturn("forPaginateTotalRowResponse");
//
//        Page<Record> result = Db.use(main).doPaginate(0, 0, Boolean.TRUE, "select", "sqlExceptSelect", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testDoPaginateByFullSql() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//
//        Page<Record> result = Db.use(main).doPaginateByFullSql(0, 0, Boolean.TRUE, "totalRowSql", null, "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testPaginate4() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//        when(dialect.forPaginateTotalRow(anyString(), anyString(), any())).thenReturn("forPaginateTotalRowResponse");
//
//        Page<Record> result = Db.use(main).paginate(0, 0, "select", "sqlExceptSelect", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testDoPaginateByFullSql2() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//
//        Page<Record> result = Db.use(main).doPaginateByFullSql(0, 0, Boolean.TRUE, "totalRowSql", "findSql", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testPaginateByFullSql() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//
//        Page<Record> result = Db.use(main).paginateByFullSql(0, 0, "totalRowSql", "findSql", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testPaginateByFullSql2() throws Exception {
//        when(dialect.forPaginate(anyInt(), anyInt(), any())).thenReturn("forPaginateResponse");
//
//        Page<Record> result = Db.use(main).paginateByFullSql(0, 0, true, "totalRowSql", "findSql", "paras");
//        Assert.assertEquals(new Page<Record>(Arrays.<Record>asList(new Record()), 0, 0, 0, 0), result);
//    }
//
//    @Test
//    public void testSave() throws Exception {
//        boolean result = Db.use(main).save("tableName", "primaryKey", new Record());
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testSave2() throws Exception {
//        boolean result = Db.use(main).save("tableName", new Record());
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testUpdate3() throws Exception {
//        boolean result = Db.use(main).update("tableName", "primaryKey", new Record());
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testUpdate4() throws Exception {
//        boolean result = Db.use(main).update("tableName", new Record());
//        Assert.assertEquals(true, result);
//    }



}
