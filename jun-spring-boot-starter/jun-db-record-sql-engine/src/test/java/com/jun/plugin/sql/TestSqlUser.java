package com.jun.plugin.sql;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.druid.pool.DruidDataSource;
import com.jun.plugin.db.DataSourcePool;
import org.junit.Test;

import com.jun.plugin.sql.engine.DynamicSqlEngine;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class TestSqlUser {
    public static void main(String[] args) throws SQLException {

        String jdbcUrl = "jdbc:mysql://localhost:3306/db_qixing_bk" +
                "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&useInformationSchema=true";
        DataSource ds = DataSourcePool.init("ds1",jdbcUrl,"root","",DataSourcePool.mysqlDriver6);

        Map params = new HashMap();
        //params.put("id",10);
        Object obj = SqlEngine.executeSql(ds.getConnection(),"select * from biz_test" +
                "  <if test='id!=null'>  where id = #{id}  </if> ",params,true);
        StaticLog.info(JSONUtil.toJsonStr(obj));
        StaticLog.info("");
    }

    @Test
    public void testSubMap() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = "id &lt;= #{maxId.maxId}";
        
        Map<String, Object> submap = new HashMap<>();
        submap.put("maxId", 10);
        Map<String, Object> map = new HashMap<>();
        map.put("maxId", submap);
        

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println("testSubMap"+sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);

    }
    @Test
    public void testIf() {
    	DynamicSqlEngine engine = new DynamicSqlEngine();
    	String sql = "id &lt;= #{maxId}";
    	Map<String, Object> map = new HashMap<>();
    	map.put("maxId", 10);
    	
    	SqlMeta sqlMeta = engine.parse(sql, map);
    	System.out.println(sqlMeta.getSql());
    	sqlMeta.getJdbcParamValues().forEach(System.out::println);
    	
    }

    @Test
    public void testTrim() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = "<trim prefix='(' suffix=')' suffixesToOverride=',' prefixesToOverride='and' ><foreach collection='list' index='idx' open='(' separator=',' close=')'>#{item.name}== #{idx}</foreach><if test='id!=null'>  and xyz.,</if></trim>";
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(new User(10, "tom"));
        arrayList.add(new User(11, "jerry"));
        map.put("list", arrayList);

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }

    @Test
    public void testWhere() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = "<where><if test='id!=null'>  and id = #{id}</if><if test='id!=null'>  and id = #{id}</if></where>";
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(new User(10, "tom"));
        arrayList.add(new User(11, "jerry"));
        map.put("list", arrayList);

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }

    @Test
    public void testForeach() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("select * from user where name in <foreach collection='list' index='idx' open='(' separator=',' close=')'>#{item.name}== #{idx}</foreach>");
        Map<String, Object> map = new HashMap<>();

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(new User(10, "tom"));
        arrayList.add(new User(11, "jerry"));
        map.put("list", arrayList.toArray());

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }

    @Test
    public void testForeachIF() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("select * from user where name in <foreach collection='list' index='idx' open='(' separator=',' close=')'>#{item.name}== #{idx}<if test='id!=null'>  and id = #{id}</if></foreach>");
        Map<String, Object> map = new HashMap<>();

        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(new User(10, "tom"));
        arrayList.add(new User(11, "jerry"));
        map.put("list", arrayList.toArray());
        map.put("id", 100);

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }

/*
    @Test
    public void testForeachMap() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("<foreach collection='users' open='(' separator=',' close=')'>#{item}</foreach>");
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> users = new HashMap<String, Object>() {
            {
                put("aaa", "a1");
                put("bbb", "b1");
            }
        };

        map.put("users", users);

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }
*/

    @Test
    public void testMultiForeach() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("<foreach collection='list' open='(' separator=',' close=')'>#{item}</foreach><foreach collection='list2' open='{' separator=',' close='}'>#{item}</foreach>");
        Map<String, Object> map = new HashMap<>();

        ArrayList<String> list = new ArrayList<String>() {{
            add("a");
            add("b");
        }};

        map.put("list", list);

        ArrayList<String> list2 = new ArrayList<String>() {{
            add("c");
            add("d");
        }};

        map.put("list2", list2.toArray());

        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);
    }

    @Test
    public void testSet() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("update<set><if test='id !=null'> id = #{id} ,</if><if test='id !=null'> id = #{id} , </if></set>");
        Map<String, Object> map = new HashMap<>();
        map.put("id",10);
        SqlMeta sqlMeta = engine.parse(sql, map);
        System.out.println(sqlMeta.getSql());
        sqlMeta.getJdbcParamValues().forEach(System.out::println);

    }

    @Test
    public void testParseParam() {
        DynamicSqlEngine engine = new DynamicSqlEngine();
        String sql = ("<foreach collection='list' open='(' separator=',' close=')'>#{item.name} #{item} #{id} ${indexName} </foreach><where><if test='id!=null'>  and id = #{mid}</if> ${name}</where>");
        Set<String> set = engine.parseParameter(sql);
        set.stream().forEach(System.out::println);
    }

}
