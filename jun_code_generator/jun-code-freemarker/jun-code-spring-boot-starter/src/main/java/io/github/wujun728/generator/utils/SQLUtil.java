package io.github.wujun728.generator.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import freemarker.template.TemplateException;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.generator.CodeUtil;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.generator.util.FreemarkerUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLUtil {

    public static void main(String[] args) throws TemplateException, IOException, SQLException {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        DataSource dataSource = DataSourcePool.init("main",url,username,password);
        //Db.init(main,dataSource);
        //Db.getDbTemplate();
        ClassInfo classInfo = CodeUtil.getClassInfo(dataSource,"api_sql");
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("classInfo",classInfo);
        StaticLog.info(SQLUtil.updateSQL(map));
//        StaticLog.info(SQLGenUtil.updateSQLParams(map));
        StaticLog.info(SQLUtil.insertSQL(map));
//        StaticLog.info(SQLGenUtil.insertSQLParams(map));
        StaticLog.info(SQLUtil.pageListSQL(map));
        StaticLog.info(SQLUtil.pageListCountSQL(map));
        StaticLog.info(SQLUtil.loadSQL(map));
//        StaticLog.info(SQLGenUtil.loadSQLParams(map));
        StaticLog.info(SQLUtil.deleteSQL(map));
//        StaticLog.info(SQLGenUtil.deleteSQLParams(map));
//        StaticLog.info(SQLGenUtil.printSQLParams(map));
//        StaticLog.info(SQLGenUtil.deleteSQLParams(map));

    }

    public static String updateSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String updateSQL = " UPDATE ${classInfo.tableName}\r\n" + "        <set>\r\n"
                + "            <#list classInfo.fieldList as fieldItem >\r\n"
                + "                <#if fieldItem.columnName != \"id\" && fieldItem.columnName != \"AddTime\" && fieldItem.columnName != \"UpdateTime\" >\r\n"
                + "                    <if test=\"${fieldItem.fieldName}!=null and  ${fieldItem.fieldName}!='' \">${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"}<#if fieldItem_has_next>,</#if>${r\"</if>\"}\r\n"
                + "                </#if>\r\n"
                + "            </#list>\r\n"
                + "        </set>\r\n"
                + "        WHERE "
                + "            <#list classInfo.pkfieldList as fieldItem >\r\n"
                + "                 <#if fieldItem.isPrimaryKey != false  > "
                + "                     ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>   "
                + "                </#if> "
//                + "                <#if fieldItem.isPrimaryKey == false  > "
//                + "                    <if test=\"${fieldItem.fieldName} != null and '' != ${fieldItem.fieldName}\">  ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>  ${r\"</if>\"} "
//                + "                </#if> "
                + "            </#list>\r\n";
        return FreemarkerUtil.genTemplateStr(data, "update", updateSQL);
    }
    public static String updateSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
//				+ "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.nullable?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
//				+ "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + " ] ";
        String params = FreemarkerUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }


    public static Map<String, Object> buildSqlParams(HashMap<String, Object> data, String sql,
                                                     String params, String cuudFlag) throws IOException, TemplateException {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String className = FreemarkerUtil.genTemplateStr(data, "${classInfo.className?uncap_first}");
        String path = "/api/"+FreemarkerUtil.genTemplateStr(data, "${classInfo.className?uncap_first}")+"/"+cuudFlag;
        sqlParam.put("id", IdUtil.fastSimpleUUID());
        sqlParam.put("refs", "main");
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
                + "                        <if test=\"${fieldItem.fieldName} != null and '' != ${fieldItem.fieldName}\">\r\n"
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
        return FreemarkerUtil.genTemplateStr(data, "insert", insertSQL);
    }

    public static String insertSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "                <#list classInfo.fieldList as fieldItem >\r\n"
                + "                    <#if fieldItem.columnName != \"id\" >\r\n"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.nullable?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "                    </#if>\r\n"
                + "                </#list>\r\n"
                + "            </#if>\r\n"
                + " ] ";
        String params = FreemarkerUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }

    public static String pageListSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String pageListSQL = " SELECT  <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>   \r\n"
                + "            <#list classInfo.fieldList as fieldItem >   \r\n"
                + "                `${fieldItem.columnName}` as `${fieldItem.fieldName}` <#if fieldItem_has_next>,</#if>  \r\n"
                + "            </#list>\r\n" + "        </#if>    \r\n"
                + "        FROM ${classInfo.tableName}  wheree 1=1  \r\n"
                + "        <if test=\"dataScope != null\">  ${r\"${dataScope}\"} </if>   \r\n"
                + "         <if test=\"pageNumber != null and pageSize != null \"> "
                + "        LIMIT ${r\"#{pageNumber}\"}, ${r\"#{pageSize}\"}  </if> ";
        return FreemarkerUtil.genTemplateStr(data, "pageListSQL", pageListSQL);
    }


    public static String pageListCountSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String pageListCountSQL = " SELECT count(1)\r\n"
                + "        FROM ${classInfo.tableName} ";
        return FreemarkerUtil.genTemplateStr(data, "pageListCountSQL", pageListCountSQL);
    }



    public static String loadSQL(HashMap<String, Object> data) throws IOException, TemplateException {
        String loadSQL = " SELECT  <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>\r\n"
                + "            <#list classInfo.fieldList as fieldItem >\r\n"
                + "                `${fieldItem.columnName}` as `${fieldItem.fieldName}` <#if fieldItem_has_next>,</#if>\r\n"
                + "            </#list>\r\n"
                + "        </#if>  \r\n"
                + "        FROM ${classInfo.tableName}\r\n"
                + "        WHERE "
                + "            <#list classInfo.pkfieldList as fieldItem >\r\n"
                + "                <#if fieldItem.isPrimaryKey != false  > "
                + "                     `${fieldItem.columnName}` = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>   "
                + "                </#if> "
                + "                <#if fieldItem.isPrimaryKey == false  > "
                + "                    <if test=\"null != ${fieldItem.fieldName} and '' != ${fieldItem.fieldName}\">  ${fieldItem.columnName} = ${r\"#{\"}${fieldItem.fieldName}${r\"}\"} <#if fieldItem_has_next>AND</#if>  ${r\"</if>\"} "
                + "                </#if> "
                + "            </#list>\r\n";
        return FreemarkerUtil.genTemplateStr(data, "load", loadSQL);
    }
    public static String loadSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "<#list classInfo.pkfieldList as fieldItem >"
                + " <#if fieldItem.isPrimaryKey != false  >"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.nullable?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "</#if>"
                + "</#list>"
                + " ] ";
        String params = FreemarkerUtil.genTemplateStr(data,  paramsSQLTemplate);
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
        String deleteSQL = FreemarkerUtil.genTemplateStr(data, deleteSQLTemplate);
        return deleteSQL;
    }

    public static String deleteSQLParams(HashMap<String, Object> data) throws IOException, TemplateException {
        String paramsSQLTemplate = " [ \r\n"
                + "<#list classInfo.pkfieldList as fieldItem >"
                + " <#if fieldItem.isPrimaryKey != false  >"
                + "  {'name':'${fieldItem.fieldName}','type':'${fieldItem.columnType}','notNull':${fieldItem.nullable?string('true', 'false')},'maxLength':${fieldItem.columnSize?c}}<#if fieldItem_has_next>,</#if> \r\n"
                + "</#if>"
                + "</#list>"
                + " ] ";
        String params = FreemarkerUtil.genTemplateStr(data,  paramsSQLTemplate);
        return params;
    }


    public static String printSQLParams(HashMap<String, Object> data)
            throws IOException, TemplateException, SQLException {
        String deleteSQLTemplate = " Map<String, Object> sqlParam = new HashMap<String, Object>();\r\n"
                + "<#list classInfo.pkfieldList as fieldItem >" + "<#if fieldItem.isPrimaryKey != false  >"
                + "  sqlParam.put(\"${fieldItem.fieldName}\", \"${fieldItem.fieldName}-value\");\r\n" + "</#if>"
                + "</#list>";
        FreemarkerUtil.genTemplateStr(data, "delete", deleteSQLTemplate);
        deleteSQLTemplate = " Map<String, Object> sqlParam = new HashMap<String, Object>();\r\n"
                + "<#list classInfo.fieldList as fieldItem >" + "<#if fieldItem.isPrimaryKey == false  >"
                + "  sqlParam.put(\"${fieldItem.fieldName}\", \"${fieldItem.fieldName}-value\");\r\n" + "</#if>"
                + "</#list>";
        FreemarkerUtil.genTemplateStr(data, "delete", deleteSQLTemplate);
        return null;
    }


}
