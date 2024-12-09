package io.github.wujun728.rest.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import freemarker.template.TemplateException;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.generator.utils.GeneratorUtil;
import io.github.wujun728.db.Db;
import io.github.wujun728.db.Record;
import io.github.wujun728.rest.util.HttpRequestUtil;
import io.github.wujun728.rest.entity.ApiSql;
import io.github.wujun728.rest.service.IRestApiService;
import io.github.wujun728.sql.SqlXmlUtil;
import io.github.wujun728.sql.SqlMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wujun
 * @desc 通用Rest服务接口
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping({"${platform.path:}/rest", "${platform.path:}/public/rest"})
//@Api(value = "实体公共增删改查接口")
public class RestSqlController {

    private static String main = "main";

    @PostConstruct
    public void init(){
        DataSourcePool.add(main, SpringUtil.getBean(DataSource.class));
        //ActiveRecordUtil.initActiveRecordPlugin(main,SpringUtil.getBean(DataSource.class));
    }
    @Resource
    IRestApiService restApiService;

    @RequestMapping(path = {"/run/{path}"}, produces = "application/json")
    public Result apiExecute(@PathVariable String path ,HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> parameters = HttpRequestUtil.getAllParameters(request);
        main = MapUtil.getStr(parameters, "ds");
//        List<Record> records  = Db.use(main).findBySql(ApiSql.class," select * from api_sql ");
//        List<ApiSql> apiSqls = RecordUtil.recordToListBean(records, ApiSql.class);
        List<ApiSql> apiSqls = Db.use(main).findList(ApiSql.class," select * from api_sql ");
        Db.use().findList(ApiSql.class," select * from api_sql ");
        Map<String, ApiSql> apiSqlMap = apiSqls.stream().collect(Collectors.toMap(i->i.getPath(),i->i));
        if(apiSqlMap.containsKey(path)){
            Object obj = restApiService.doSQLProcess(apiSqlMap.get(path), parameters);
            return Result.success(obj);
        }else{
            return Result.error("执行的接口不存在" );
        }
    }



    public static Object executeSQL(DataSource ds, Map<String, Object> sqlParam, String deleteSQL)
            throws SQLException {
        SqlMeta sqlMeta = SqlXmlUtil.getEngine().parse(deleteSQL, sqlParam);
        Object datas = SqlXmlUtil.executeSql(ds.getConnection(), sqlMeta.getSql(), sqlMeta.getJdbcParamValues());
        System.err.println(JSON.toJSONString(datas));
        return datas;
    }

    public static void initDb(String configName, String url, String username, String password) {
        DataSourcePool.add(main, SpringUtil.getBean(DataSource.class));
        //ActiveRecordUtil.initActiveRecordPlugin(configName,url,username,password);
    }

    public static String updateSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String updateSQL = " UPDATE ${classInfo.tableName}\r\n" + "        <set>\r\n"
                + "            <#list classInfo.fieldList as fieldItem >\r\n"
                + "                <#if fieldItem.columnName != \"id\" && fieldItem.columnName != \"AddTime\" && fieldItem.columnName != \"UpdateTime\" >\r\n"
                + "                    <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"}<#if fieldItem_has_next>,</#if>${r\"</if>\"}\r\n"
                + "                </#if>\r\n"
                + "            </#list>\r\n"
                + "        </set>\r\n"
                + "        WHERE "
                + "            <#list classInfo.pkfieldList as fieldItem >\r\n"
                + "                <#if fieldItem.isPrimaryKey != false  > "
                + "                     ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>   "
                + "                </#if> "
                + "                <#if fieldItem.isPrimaryKey == false  > "
                + "                    <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">  ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>  ${r\"</if>\"} "
                + "                </#if> "
                + "            </#list>\r\n";
        return GeneratorUtil.genTemplateStr(data, "update", updateSQL);
    }
    public static String updateSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
//				+ "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.notNull?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
//				+ "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + " ] ";
        String params = GeneratorUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }


    public static Map<String, Object> buildSqlParams(HashMap<String, Object> data, String sql,
                                                     String params, String cuudFlag) throws IOException, TemplateException {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String className = GeneratorUtil.genTemplateStr(data, "${classInfo.className?uncap_first}");
        String path = "/api/"+GeneratorUtil.genTemplateStr(data, "${classInfo.className?uncap_first}")+"/"+cuudFlag;
        sqlParam.put("id", IdUtil.fastSimpleUUID());
        sqlParam.put("refs", main);
        sqlParam.put("path", path);
        sqlParam.put("name", path);
        // sqlParam.put("method", "method-value");
        sqlParam.put("params", params);
//        sqlParam.put("interfaceId", className + "-"+cuudFlag);
        sqlParam.put("bean_name", className + "-"+cuudFlag);
        sqlParam.put("datasource_id", "local");
        sqlParam.put("script_type", "SQL");
        sqlParam.put("script_content", sql);
        sqlParam.put("status", "ENABLE");
        sqlParam.put("group_name", className);
        sqlParam.put("sort", "0");
        sqlParam.put("extend_info", "extendInfo-value");
        sqlParam.put("open_trans", "1");
        sqlParam.put("relutl_type", null);
        sqlParam.put("creator", "admin");
        sqlParam.put("created_time", DateUtil.now());
        sqlParam.put("update_time", DateUtil.now());
        sqlParam.put("last_update", "admin");
        return sqlParam;
    }



    public static String insertSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String insertSQL = " INSERT INTO ${classInfo.tableName}\r\n"
                + "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
                + "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "                        <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">\r\n"
                + "                        ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>\r\n"
                + "                        ${r\"</if>\"}\r\n"
                + "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + "        </trim>\r\n"
                + "        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
                + "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "                    <#--<#if fieldItem.columnName=\"addtime\" || fieldItem.columnName=\"updatetime\" >\r\n"
                + "                    ${r\"<if test ='null != \"}${fieldItem.fieldName}${r\"'>\"}\r\n"
                + "                        NOW()<#if fieldItem_has_next>,</#if>\r\n"
                + "                    ${r\"</if>\"}\r\n"
                + "                    <#else>-->\r\n"
                + "                        <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">\r\n"
                + "                        ${r\"#{\"}${fieldItem.fieldName}${r\"}\"}<#if fieldItem_has_next>,</#if>\r\n"
                + "                        ${r\"</if>\"}\r\n"
                + "                    <#--</#if>-->\r\n"
                + "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + "        </trim> ";
        return GeneratorUtil.genTemplateStr(data, "insert", insertSQL);
    }

    public static String insertSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
                + "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.notNull?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + " ] ";
        String params = GeneratorUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }

    public static String pageListSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String pageListSQL = " SELECT  <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>   \r\n"
                + "            <#list classInfo.fieldList as fieldItem >   \r\n"
                + "                ${fieldItem.columnName} as ${fieldItem.fieldName} <#if fieldItem_has_next>,</#if>  \r\n"
                + "            </#list>\r\n" + "        </#if>    \r\n"
                + "        FROM ${classInfo.tableName}   \r\n"
                + "        LIMIT ${r\"#{pageNumber}\"}, ${r\"#{pageSize}\"} ";
        return GeneratorUtil.genTemplateStr(data, "pageListSQL", pageListSQL);
    }


    public static String pageListCountSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String pageListCountSQL = " SELECT count(1)\r\n"
                + "        FROM ${classInfo.tableName} ";
        return GeneratorUtil.genTemplateStr(data, "pageListCountSQL", pageListCountSQL);
    }



    public static String loadSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String loadSQL = " SELECT  <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "            <#list classInfo.fieldList as fieldItem >\r\n"
                + "                ${fieldItem.columnName} as ${fieldItem.fieldName} <#if fieldItem_has_next>,</#if>\r\n"
                + "            </#list>\r\n"
                + "        </#if>  \r\n"
                + "        FROM ${classInfo.tableName}\r\n"
                + "        WHERE "
                + "            <#list classInfo.pkfieldList as fieldItem >\r\n"
                + "                <#if fieldItem.isPrimaryKey != false  > "
                + "                     ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>   "
                + "                </#if> "
                + "                <#if fieldItem.isPrimaryKey == false  > "
                + "                    <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">  ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>  ${r\"</if>\"} "
                + "                </#if> "
                + "            </#list>\r\n";
        return GeneratorUtil.genTemplateStr(data, "load", loadSQL);
    }
    public static String loadSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "<#list classInfo.pkfieldList as fieldItem >"
                + " <#if fieldItem.isPrimaryKey != false  >"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.notNull?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "</#if>"
                + "</#list>"
                + " ] ";
        String params = GeneratorUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }

    @SuppressWarnings("unused")
    public static String deleteSQL(HashMap<String, Object> data) throws IOException, TemplateException, SQLException {
        String deleteSQLTemplate = " DELETE FROM ${classInfo.tableName} \r\n"
                + "        WHERE "
                + "            <#list classInfo.pkfieldList as fieldItem >\r\n"
                + "                <#if fieldItem.isPrimaryKey != false  > "
                + "                     ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>   "
                + "                </#if> "
                + "                <#if fieldItem.isPrimaryKey == false  > "
                + "                    <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">  ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>  ${r\"</if>\"} "
                + "                </#if> "
                + "            </#list>\r\n";
        String deleteSQL = GeneratorUtil.genTemplateStr(data, deleteSQLTemplate);
        return deleteSQL;
    }

    public static String deleteSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "<#list classInfo.pkfieldList as fieldItem >"
                + " <#if fieldItem.isPrimaryKey != false  >"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.notNull?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "</#if>"
                + "</#list>"
                + " ] ";
        String params = GeneratorUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }


    public static String printSQLParams(HashMap<String, Object> data)
            throws IOException, TemplateException, SQLException {
        String deleteSQLTemplate = " Map<String, Object> sqlParam = new HashMap<String, Object>();\r\n"
                + "<#list classInfo.pkfieldList as fieldItem >" + "<#if fieldItem.isPrimaryKey != false  >"
                + "  sqlParam.put(\"${fieldItem.fieldName}\", \"${fieldItem.fieldName}-value\");\r\n" + "</#if>"
                + "</#list>";
        GeneratorUtil.genTemplateStr(data, "delete", deleteSQLTemplate);
        deleteSQLTemplate = " Map<String, Object> sqlParam = new HashMap<String, Object>();\r\n"
                + "<#list classInfo.fieldList as fieldItem >" + "<#if fieldItem.isPrimaryKey == false  >"
                + "  sqlParam.put(\"${fieldItem.fieldName}\", \"${fieldItem.fieldName}-value\");\r\n" + "</#if>"
                + "</#list>";
        GeneratorUtil.genTemplateStr(data, "delete", deleteSQLTemplate);
        return null;
    }


    public static Record getTableRecord(DataSource ds, String tableName, Map params){
        Record record = new Record();
        Table table = MetaUtil.getTableMeta(ds,tableName);
        table.getColumns().forEach(c->{
            record.set(c.getName(), MapUtil.getStr(params,c.getName()));
        });
        return record;
    }

    public static String getPkNames(DataSource ds, String tableName){
        Table table = MetaUtil.getTableMeta(ds,tableName);
        return String.join(",",table.getPkNames());
    }



}
