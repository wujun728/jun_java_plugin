package io.github.wujun728.code;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import com.hp.common.constant.Constants;
import com.hp.common.constant.GenConstants;
import com.hp.domain.GenTable;
import com.hp.domain.GenTableColumn;
import com.hp.util.StringUtils;
import com.hp.util.VelocityInitializer;
import com.hp.util.VelocityUtils;
import io.github.wujun728.db.record.Db;
import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.generator.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class CodeUtilTest {

    public static void main(String[] args) {
        //testAllGroup();
//        testDbRecordGroup();
        testDbRuoyiGroup();
    }


    private static void testDbRuoyiGroup()  {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
        Db.init("main",ds);
        CodeUtil.customeConfig = MapUtil.ofEntries(MapUtil.entry(CodeUtil.authorName,"wujun"),MapUtil.entry(CodeUtil.packageName,"com.hp.test1"));
        String absPath = "D:\\workspace\\github\\jun_code_generator\\jun-code-generator\\src\\main\\java";
//        CodeUtil.genCodeFile(ds,"biz_test",absPath, CodeUtil.GROUP_DB_RECORD);

        String[] tableNames = {"api_sql"};
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        FileUtil.writeBytes(outputStream.toByteArray(),new File("D:/biz_test1.zip"));
    }
    private static void generatorCode(String tableName, ZipOutputStream zip)  {
        String sql1 = " SELECT t.table_id, t.table_name, t.table_comment, t.sub_table_name, t.sub_table_fk_name, t.class_name, t.tpl_category, t.package_name, t.module_name, t.business_name, t.function_name, t.function_author, t.gen_type, t.gen_path, t.options, t.remark,\n" +
                "\t\t\t   c.column_id, c.column_name, c.column_comment, c.column_type, c.java_type, c.java_field, c.is_pk, c.is_increment, c.is_required, c.is_insert, c.is_edit, c.is_list, c.is_query, c.query_type, c.html_type, c.dict_type, c.sort\n" +
                "\t\tFROM gen_table t\n" +
                "\t\t\t LEFT JOIN gen_table_column c ON t.table_id = c.table_id\n" +
                "\t\twhere t.table_name = ? order by c.sort ";
        // 查询表信息
        List<GenTable> tables = Db.findByColumnValueBeans(GenTable.class,"table_name",tableName);
        List<GenTableColumn> columns = Db.findBeanList(GenTableColumn.class,sql1,tableName);
        if(CollectionUtil.isEmpty(tables)){
            String tableSql = " select table_name, table_comment, create_time, update_time from information_schema.tables\n" +
                    "\t\twhere table_name NOT LIKE 'qrtz_%' and table_name NOT LIKE 'gen_%' and table_schema = (select database())\n" +
                    "\t\tand table_name ='"+tableName+"' ";

        }
        GenTable  table =  tables.get(0);
        table.setColumns(columns);
        //GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }
    public static void setSubTable(GenTable table)
    {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName))
        {
            //table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
        }
    }
    public static void setPkColumn(GenTable table)
    {
        for (GenTableColumn column : table.getColumns())
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn()))
        {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory()))
        {
            for (GenTableColumn column : table.getSubTable().getColumns())
            {
                if (column.isPk())
                {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSubTable().getPkColumn()))
            {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }


    private static void testDbRecordGroup() {
        String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
        CodeUtil.customeConfig = MapUtil.ofEntries(MapUtil.entry(CodeUtil.authorName,"wujun"),MapUtil.entry(CodeUtil.packageName,"com.hp.test1"));
        String absPath = "D:\\workspace\\github\\jun_code_generator\\jun-code-generator\\src\\main\\java";
        CodeUtil.genCodeFile(ds,"biz_test",absPath, CodeUtil.GROUP_DB_RECORD);
    }

    private static void testAllGroup() {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_VO_JAVA);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_CONTROLLER_JAVA);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.UTIL_SQL);
//        CodeUtil.genCodeFile(ds,"biz_mail","D:/jva/test", CodeUtil.BEETLSQL_BEETLCONTROLLER);
        CodeUtil.customeConfig = MapUtil.ofEntries(MapUtil.entry(CodeUtil.authorName,"wujun"),MapUtil.entry(CodeUtil.packageName,"io.github.wujun728.test1"));
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_BEETLSQL);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_JPA);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_JDBCTEMPLATE);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATIS);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATIS_PLUG_NO1);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATISPLUS);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_UI);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_UTIL);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_DB_RECORD);
//        CodeUtil.genCodeFile(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_SINGLE_VO_JAVA,"D:/jva/test");


//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.JDBCTEMPLATE_JTDAO);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.JDBCTEMPLATE_JTDAOIMPL);
    }

}
