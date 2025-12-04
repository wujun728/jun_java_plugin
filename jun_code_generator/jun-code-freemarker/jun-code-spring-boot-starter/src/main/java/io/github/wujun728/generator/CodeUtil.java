package io.github.wujun728.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.generator.entity.ClassInfo;
import io.github.wujun728.generator.entity.FieldInfo;
import io.github.wujun728.generator.util.FreemarkerUtil;
import io.github.wujun728.generator.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CodeUtil {

    static Map<String, Object> params = new HashMap<String, Object>();
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
//        genCode(ds,"pj_customer",CodeUtil.UI_LAYUI_LIST);
//        genCode(ds,"pj_customer",CodeUtil.UI_LAYUI_EDIT2);
        genCodeFile(ds,"paas_function","D:/test1122",CodeUtil.GROUP_MYBATIS_PLUG_NO1);
    }


    public static String authorName = "authorName";
    private static String AUTHOR_NAME = "wujun";
    public static String packageName = "packageName";
    public static String namePreRemove = "S_,B_,I_,DT_,TS_,M_,F_,PK_I_N,PK_I_S,PAAS1_";
    private static String PACKAGE_NAME = "io.github.wujun728.biz";

    public static String mybatis_plus_single_v3 = "/mybatis-plus-single-v5-paas";
    public static String MYBATIS_PLUG_SINGLE_VO_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Vo.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_PARAM_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Param.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_RESP_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Resp.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_CONTROLLER_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Controller.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_DTO_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Dto.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_EDIT_HTML = mybatis_plus_single_v3+"/edit.html.ftl";
    public static String MYBATIS_PLUG_SINGLE_LIST_HTML = mybatis_plus_single_v3+"/list.html.ftl";
    public static String MYBATIS_PLUG_SINGLE_ENTITY_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Entity.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_MAPPER_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Mapper.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_SERVICE_JAVA = mybatis_plus_single_v3+"/${classInfo.className}Service.java.ftl";
    public static String MYBATIS_PLUG_SINGLE_SERVICE_IMPL_JAVA = mybatis_plus_single_v3+"/${classInfo.className}ServiceImpl.java.ftl";

    public static String BEETLSQL_BEETLCONTROLLER = "/beetlsql/${classInfo.className}Controller.java.ftl";
    public static String BEETLSQL_BEETLENTITY = "/beetlsql/${classInfo.className}.java.ftl";
    public static String BEETLSQL_BEETLMD = "/beetlsql/${classInfo.className}.md.ftl";

    public static String Db_RECORD_CONTROLLER = "/db-record/${classInfo.className}Controller.java.ftl";
    public static String Db_RECORD_ENTITY = "/db-record/${classInfo.className}.java.ftl";


    public static String JDBCTEMPLATE_JTDAO = "/jdbctemplate/I${classInfo.className}DAO.java.ftl";
    public static String JDBCTEMPLATE_JTDAOIMPL = "/jdbctemplate/${classInfo.className}DaoImpl.java.ftl";



    public static String JPA_ENTITY = "/jpa/${classInfo.className}.java.ftl";
    public static String JPA_JPACONTROLLER = "/jpa/${classInfo.className}Controller.java.ftl";
    public static String JPA_REPOSITORY = "/jpa/${classInfo.className}Repository.java.ftl";


    public static String MYBATISPLUS_PLUSCONTROLLER = "/mybatis-plus/${classInfo.className}Controller.java.ftl";
    public static String MYBATISPLUS_PLUSENTITY = "/mybatis-plus/${classInfo.className}.java.ftl";
    public static String MYBATISPLUS_PLUSMAPPER = "/mybatis-plus/${classInfo.className}Mapper.java.ftl";
    public static String MYBATISPLUS_PLUSSERVICE = "/mybatis-plus/${classInfo.className}Service.java.ftl";

    public static String MYBATIS_CONTROLLER = "/mybatis/${classInfo.className}Controller.java.ftl";
    public static String MYBATIS_MAPPER = "/mybatis/${classInfo.className}Mapper.java.ftl";
    public static String MYBATIS_MAPPER2 = "/mybatis/${classInfo.className}Mapper2.java.ftl";
    public static String MYBATIS_MODEL = "/mybatis/${classInfo.className}.java.ftl";
    public static String MYBATIS_MYBATIS_XML = "/mybatis/${classInfo.className}.xml.ftl";
    public static String MYBATIS_SERVICE = "/mybatis/${classInfo.className}Service.java.ftl";
    public static String MYBATIS_SERVICE_IMPL = "/mybatis/${classInfo.className}ServiceImpl.java.ftl";

    public static String UI_BOOTSTRAP_UI = "/ui/${classInfo.className}bootstrap.html.ftl";
    public static String UI_ELEMENT_UI = "/ui/${classInfo.className}element.vuel.ftl";
    public static String UI_LAYUI_EDIT = "/ui/${classInfo.className}layui-edit.html.ftl";

    public static String UI_LAYUI_EDIT2 = "/ui/${classInfo.className}layui-edit2.html.ftl";
    public static String UI_LAYUI_LIST = "/ui/${classInfo.className}layui-list.html.ftl";
    public static String UI_SWAGGER_UI = "/ui/${classInfo.className}swagger.json.ftl";



    public static String UTIL_BEANUTIL = "/util/${classInfo.className}beanutil.java.ftl";
    public static String UTIL_JSON = "/util/${classInfo.className}json.json.ftl";
    public static String UTIL_SQL = "/util/${classInfo.className}sql.sql.ftl";
    public static String UTIL_SWAGGER_YML = "/util/${classInfo.className}swagger.yml.ftl";
    public static String UTIL_XML = "/util/${classInfo.className}xml.xml.ftl";


    public  static List<String> GROUP_MYBATIS_PLUG_NO1 = Lists.newArrayList(MYBATIS_PLUG_SINGLE_RESP_JAVA,MYBATIS_PLUG_SINGLE_PARAM_JAVA,MYBATIS_PLUG_SINGLE_VO_JAVA,MYBATIS_PLUG_SINGLE_CONTROLLER_JAVA,MYBATIS_PLUG_SINGLE_DTO_JAVA,
            MYBATIS_PLUG_SINGLE_ENTITY_JAVA, MYBATIS_PLUG_SINGLE_MAPPER_JAVA,MYBATIS_PLUG_SINGLE_SERVICE_JAVA,MYBATIS_PLUG_SINGLE_SERVICE_IMPL_JAVA,MYBATIS_PLUG_SINGLE_EDIT_HTML,
            MYBATIS_PLUG_SINGLE_LIST_HTML);
    public  static List<String> GROUP_BEETLSQL = Lists.newArrayList(BEETLSQL_BEETLMD,BEETLSQL_BEETLENTITY,BEETLSQL_BEETLCONTROLLER);
    public  static List<String> GROUP_DB_RECORD = Lists.newArrayList(Db_RECORD_CONTROLLER,Db_RECORD_ENTITY);
    public  static List<String> GROUP_JDBCTEMPLATE = Lists.newArrayList(JDBCTEMPLATE_JTDAO,JDBCTEMPLATE_JTDAOIMPL);
    public  static List<String> GROUP_JPA = Lists.newArrayList(JPA_ENTITY,JPA_REPOSITORY,JPA_JPACONTROLLER);
    public  static List<String> GROUP_MYBATISPLUS = Lists.newArrayList(MYBATISPLUS_PLUSCONTROLLER,MYBATISPLUS_PLUSENTITY,MYBATISPLUS_PLUSMAPPER,MYBATISPLUS_PLUSSERVICE);
    public  static List<String> GROUP_MYBATIS = Lists.newArrayList(MYBATIS_CONTROLLER,MYBATIS_MAPPER,MYBATIS_MAPPER2,MYBATIS_MODEL,MYBATIS_MYBATIS_XML,
            MYBATIS_SERVICE,MYBATIS_SERVICE_IMPL);
    public  static List<String> GROUP_UI = Lists.newArrayList(UI_BOOTSTRAP_UI,UI_ELEMENT_UI,UI_LAYUI_EDIT,UI_LAYUI_LIST,UI_SWAGGER_UI);
    public  static List<String> GROUP_UTIL = Lists.newArrayList(UTIL_BEANUTIL,UTIL_JSON,UTIL_SQL,UTIL_SWAGGER_YML,UTIL_XML);
    public static Map<String, Object> customeConfig = new HashMap<String, Object>();





    public static void genCodeCustom(DataSource dataSource,String tableName,String templatePath,String templateFileName) {
        genCodeV1(true,dataSource,tableName,templatePath,templateFileName);
    }
    public static void genCode(DataSource dataSource,String tableName,String templateFileName) {
        genCodeV1(false,dataSource,tableName,null,templateFileName);
    }
    /*public static void genCodeFile(DataSource dataSource, String tableName, List<String> templateFileName, String filePath) {

    }*/
    public static void genCodeFile(DataSource dataSource, String tableName, String filePath, List<String> templateFileName) {
        for(String template : templateFileName){
            String content = genCodeV1(false, dataSource, tableName, null, template,filePath);
        }
    }
    public static void genCodeFile(DataSource dataSource, String tableName, String filePath, String templateFileName) {
        String content = genCodeV1(false, dataSource, tableName, null, templateFileName,filePath);
    }
    private static String genCodeV1(Boolean isCustom, DataSource dataSource, String tableName, String templatePath, String templateFileName) {
        return genCodeV1(isCustom, dataSource, tableName, templatePath, templateFileName,null);
    }
    private static String genCodeV1(Boolean isCustom, DataSource dataSource, String tableName, String templatePath, String templateFileName,String filePath) {

        try {
            ClassInfo classInfo = getClassInfo(dataSource,tableName);
            //params = new HashMap<>();
            params.put("classInfo", classInfo);
            params.put("ClassName", classInfo.getClassName());
            params.put(authorName, AUTHOR_NAME);
            params.put(packageName, PACKAGE_NAME);
            params.put("isAutoImport", true);
            params.put("isSwagger", true);
            params.put("isComment", true);
            params.put("isLombok", true);
            params.putAll(customeConfig);
            Map<String, String> result = new HashMap<String, String>();
            if(isCustom){
                FreemarkerUtil.init2DirectoryForTemplateLoading(templatePath);
            }else{
                FreemarkerUtil.init2ClassForTemplateLoading("/templates");
            }
            //result.put("service_code", FreemarkerUtil.processString("service.ftl", params));
            String templateContent = FreemarkerUtil.processString(templateFileName, params);
            result.put(templateFileName, templateContent);
            // 计算,生成代码行数
            int lineNum = 0;
            for (Map.Entry<String, String> item: result.entrySet()) {
                if (item.getValue() != null) {
                    lineNum += StringUtils.countMatches(item.getValue(), "\n");
                }
            }

            if(StrUtil.isNotEmpty(filePath)){
                String content = templateContent;
                String path_tmep = templateFileName.replace(".ftl","");
                String path_tmep2 = removeFirstFloderPath(path_tmep);
                String fileName = FreemarkerUtil.processStringTemplate(path_tmep2,params);
                String fulllPahtName = filePath+File.separator+fileName;
                if(StrUtil.isNotEmpty(MapUtil.getStr(params,packageName))){
                    String floder1= StrUtil.replace(MapUtil.getStr(params,packageName),".",File.separator);
                    fulllPahtName = filePath+File.separator+floder1+fileName;
                }
                FileUtil.writeString(content,fulllPahtName, Charset.defaultCharset());
                StaticLog.info("生成代码{}，生成代码行数：{}", fulllPahtName, lineNum);
            }else {
                result.keySet().forEach(key->{
                    StaticLog.info("key="+key);
                    StaticLog.info("val="+result.get(key));
                });
                StaticLog.info("生成代码{}，生成代码行数：{}", templateFileName, lineNum);
            }
            return templateContent;
        } catch (IOException | TemplateException e) {
            StaticLog.error(e.getMessage(), e);
        }

        return tableName;
    }

    public static ClassInfo getClassInfo(DataSource dataSource,String tableName) {
        Table table = MetaUtil.getTableMeta(dataSource, tableName);
        // V1 初始化数据及对象 模板V1 field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        List<FieldInfo> pkfieldList = new ArrayList<FieldInfo>();
        for (Column column : table.getColumns()) {
            // V1 初始化数据及对象
            String remarks = column.getComment();// cloumnsSet.getString("REMARKS");// 列的描述
            String columnName = column.getName();// cloumnsSet.getString("COLUMN_NAME"); // 获取列名
            String javaType = getType(column.getType()/*cloumnsSet.getInt("DATA_TYPE")*/);// 获取类型，并转成JavaType
            String columnType = column.getTypeName();// 获取类型，并转成JavaType
            long COLUMN_SIZE = column.getSize();// cloumnsSet.getInt("COLUMN_SIZE");// 获取
            String COLUMN_DEF = column.getColumnDef();// cloumnsSet.getString("COLUMN_DEF");// 获取
            Boolean nullable = column.isNullable();// cloumnsSet.getInt("NULLABLE");// 获取
            String propertyName = replace_(replaceRowPreStr(columnName));// 处理列名，驼峰
            Boolean isPk = column.isPk();
            // V1 初始化数据及对象
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setColumnName(columnName);
            fieldInfo.setColumnType(columnType);
            fieldInfo.setFieldName(propertyName);
            fieldInfo.setFieldClass(simpleName(javaType));
            fieldInfo.setFieldComment(remarks);
            fieldInfo.setColumnSize(COLUMN_SIZE);
            fieldInfo.setNullable(nullable);
            fieldInfo.setFieldType(javaType);
            fieldInfo.setIsPrimaryKey(isPk);
            fieldList.add(fieldInfo);
            if(isPk){
                pkfieldList.add(fieldInfo);
            }
        }
        if (fieldList != null && fieldList.size() > 0) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setTableName(table.getTableName());
            String className = replace_(replaceRowPreStr(table.getTableName())); // 名字操作,去掉tab_,tb_，去掉_并转驼峰
            String classNameFirstUpper = firstUpper(className); // 大写对象
            classInfo.setClassName(classNameFirstUpper);
            if(table.getComment().contains("表")){
                classInfo.setClassComment(table.getComment().replace("表",""));
            }else{
                classInfo.setClassComment(table.getComment());
            }
            classInfo.setFieldList(fieldList);
            classInfo.setPkfieldList(pkfieldList);
            classInfo.setPkSize(table.getPkNames().size());
            return classInfo;
        }
        return null;
    }

    public static String replaceRowPreStr(String str) {
		//str = str.toLowerCase().replaceFirst("tab_", "").replaceFirst("tb_", "").replaceFirst("t_", "");
        for (String x : namePreRemove.split(",")) {
            if(str.startsWith(x.toLowerCase())){
                str = str.replaceFirst(x.toLowerCase(), "");
            }
        }
        return str;
    }

    public static String replace_(String str) {
        // 根据下划线分割
        String[] split = str.split("_");
        // 循环组装
        String result = split[0];
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                result += firstUpper(split[i]);
            }
        }
        return result;
    }

    public static String firstUpper(String str) {
        // log.info("str:"+str+",str.length="+str.length());
        if (!StrUtil.isEmpty(str)) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str;
        }
    }

    public static String simpleName(String type) {
        return type.replace("java.lang.", "").replaceFirst("java.util.", "");
    }

    private static String removeFirstFloderPath(String str) {
        String strPath = new String(str.getBytes(StandardCharsets.UTF_8));
        if(StrUtil.isNotEmpty(strPath) && strPath.contains("/") && strPath.split("/").length>1){
            List<String> strs = StrUtil.split(strPath,"/");
            String newFloderPath = strPath.replaceFirst("/"+strs.get(1),"");
            System.out.println(newFloderPath);
            return newFloderPath;
        }
        return strPath;
    }


    public static String getType(int value) {
        switch (value) {
            case -6:
                return "java.lang.Integer";
            case 5:
                return "java.lang.Integer";
            case 4:
                return "java.lang.Integer";
            case -5:
                return "java.lang.Long";
            case 6:
                return "java.lang.Float";
            case 8:
                return "java.lang.Double";
            case 1:
                return "java.lang.String";
            case 12:
                return "java.lang.String";
            case -1:
                return "java.lang.String";
            case 91:
                return "java.util.Date";
            case 92:
                return "java.util.Date";
            case 93:
                return "java.util.Date";
            case 16:
                return "java.lang.Boolean";
            default:
                return "java.lang.String";
        }
    }




}
