package com.kind.run;

import com.kind.core.analyze.FreemarkerAnalyer;
import com.kind.core.configration.CustomizedPropertyConfigurer;
import com.kind.core.domain.ColumnDO;
import com.kind.core.domain.TableDO;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generate {
    /**
     * 输出路径绝对地址
     */
    public static final String genPath = "kind.genPath";
    public static final String templatePath = "kind.template";
    /**
     * 生成代码的package前缀
     */
    public static final String basePackage = "kind.package";
    /**
     * 生成类的前缀，如xxxSimpleDAO中的Simple
     */
    public static final String classPrefix = "kind.classPrefix";
    public static final String JDBC_TEMPLATE_NAME = "jdbcTemplate";
    private static JdbcTemplate jdbcTemplate = (JdbcTemplate) new ClassPathXmlApplicationContext("beans.xml")
            .getBean("jdbcTemplate");
    /**
     * 后台跳转地址
     */
    private static String schemaName = "kind.schemaName";

    private static String getScheamaName() {
        return (String) CustomizedPropertyConfigurer.getContextProperty(schemaName);
    }

    private static String getGenPath() {
        return (String) CustomizedPropertyConfigurer.getContextProperty(genPath);
    }

    private static String getTemplatePath() {
        return (String) CustomizedPropertyConfigurer.getContextProperty(templatePath);
    }

    private static String getBasePackage() {
        return (String) CustomizedPropertyConfigurer.getContextProperty(basePackage);
    }

    private static String getClassPrefix() {
        return (String) CustomizedPropertyConfigurer.getContextProperty(classPrefix);
    }

    public static List<TableDO> getTables() {
        System.out.println("schemaName:" + getScheamaName());
        List<TableDO> tableBeanList = jdbcTemplate.query(
                "SELECT * FROM INFORMATION_SCHEMA.TABLES  WHERE TABLE_SCHEMA= ?", new Object[]{getScheamaName()},
                new RowMapper<TableDO>() {
                    @Override
                    public TableDO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TableDO bean = new TableDO();
                        String tableName = rs.getString("table_name");
                        String tableNameTrimed = tableName;
                        if (tableName.startsWith("t_")) {
                            tableNameTrimed = tableName.substring(2);
                        }
                        bean.setTableName(tableName);
                        bean.setTableNameNoDash(removeDash(tableNameTrimed));
                        bean.setTableNameCapitalized(StringUtils.capitalize(bean.getTableNameNoDash()));
                        bean.setTableComment(rs.getString("table_comment"));
                        return bean;
                    }
                });

        for (TableDO tableDO : tableBeanList) {
            System.out.println("tableName:" + tableDO.getTableName());
            tableDO.setColumnBeanList(getColumns(tableDO.getTableName(), tableDO));
        }

        return tableBeanList;
    }

    public static List<ColumnDO> getColumns(String tableName, final TableDO tableDO) {
        return jdbcTemplate.query("select * from Information_schema.columns  where TABLE_SCHEMA = ? and TABLE_NAME = ?",
                new Object[]{getScheamaName(), tableName}, new RowMapper<ColumnDO>() {
                    @Override
                    public ColumnDO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ColumnDO bean = new ColumnDO();
                        String columnName = rs.getString("column_name");
                        bean.setColumnName(columnName);
                        bean.setColumnNameNoDash(removeDash(columnName));
                        bean.setColumnNameCapitalized(StringUtils.capitalize(bean.getColumnNameNoDash()));
                        bean.setColumnComment(rs.getString("column_comment"));

                        String columnType = rs.getString("column_type").toLowerCase();
                        this.getPropertityType(tableDO, bean, columnType);

                        return bean;
                    }

                    /**
                     * @param tableDO
                     * @param bean
                     * @param columnType
                     */
                    private void getPropertityType(final TableDO tableDO, ColumnDO bean, String columnType) {
                        if (columnType.startsWith("varchar") || ("text").equals(columnType)
                                || columnType.startsWith("enum") || columnType.endsWith("Blob")) {
                            bean.setColumnType("String");
                            bean.setColumnTypeRsGetter("getString");
                        } else if (columnType.startsWith("int")) {
                            if (columnType.contains("unsigned")) {
                                bean.setColumnType("Long");
                                bean.setColumnTypeRsGetter("getLong");
                            } else {
                                bean.setColumnType("Integer");
                                bean.setColumnTypeRsGetter("getInt");
                            }
                        } else if (columnType.startsWith("bigint")) {
                            bean.setColumnType("Long");
                            bean.setColumnTypeRsGetter("getLong");
                        } else if (("timestamp").equals(columnType) || ("datetime").equals(columnType)) {
                            bean.setColumnType("Date");
                            bean.setColumnTypeRsGetter("getDate");
                            tableDO.setHasDateColumn(true);
                        } else if (columnType.startsWith("float")) {
                            bean.setColumnType("Float");
                            bean.setColumnTypeRsGetter("getFloat");
                        } else if (columnType.startsWith("double")) {
                            bean.setColumnType("Double");
                            bean.setColumnTypeRsGetter("getDouble");
                        } else if (columnType.startsWith("decimal")) {
                            bean.setColumnType("BigDecimal");
                            bean.setColumnTypeRsGetter("getBigDecimal");
                            tableDO.setHasBigDecimal(true);
                        } else {
                            throw new RuntimeException("Unsupported type: [" + columnType + "]!");
                        }
                    }
                });
    }

    /**
     * @param outputDirs
     * @throws IOException
     */
    private static void handleTemplate() throws IOException {
        File outputPath = new File(getGenPath());
        if (!outputPath.isDirectory()) {
            FileUtils.forceMkdir(outputPath);
        }
        String outputDirs = getGenPath() + "/" + StringUtils.replace(getBasePackage(), ".", "/");
        List<TableDO> tableBeanList = getTables();
        System.out.println("tableList:" + tableBeanList.size());
        for (TableDO tableDO : tableBeanList) {
            Map<String, Object> varMap = new HashMap<String, Object>();
            varMap.put("tableBean", tableDO);
            varMap.put("schemaName", getScheamaName());
            varMap.put("packageBase", getBasePackage());
            varMap.put("classPrefix", getClassPrefix());
            varMap.put("jdbcTemplateName", JDBC_TEMPLATE_NAME);
            varMap.put("jdbcTemplateNameCapitalized", StringUtils.capitalize(JDBC_TEMPLATE_NAME));

            Template modelTemplate = FreemarkerAnalyer.getTemplate(getTemplatePath() + "/model.ftl");
            String filePath = outputDirs + "/model/" + tableDO.getTableNameCapitalized() + ".java";
            FreemarkerAnalyer.writeResult(filePath, modelTemplate, varMap);

            Template daoTemplate = FreemarkerAnalyer.getTemplate(getTemplatePath() + "/dao.ftl");
            filePath = outputDirs + "/dao/" + tableDO.getTableNameCapitalized() + getClassPrefix() + "DAO.java";
            FreemarkerAnalyer.writeResult(filePath, daoTemplate, varMap);

            Template daoImplTemplate = FreemarkerAnalyer.getTemplate(getTemplatePath() + "/daoImpl.ftl");
            filePath = outputDirs + "/dao/impl/" + tableDO.getTableNameCapitalized() + getClassPrefix() + "DAOImpl.java";
            FreemarkerAnalyer.writeResult(filePath, daoImplTemplate, varMap);

            Template serviceTemplate = FreemarkerAnalyer.getTemplate(getTemplatePath() + "/service.ftl");
            filePath = outputDirs + "/service/" + tableDO.getTableNameCapitalized() + getClassPrefix() + "Service.java";
            FreemarkerAnalyer.writeResult(filePath, serviceTemplate, varMap);

            Template serviceImplTemplate = FreemarkerAnalyer.getTemplate(getTemplatePath() + "/serviceImpl.ftl");
            filePath = outputDirs + "/service/impl/" + tableDO.getTableNameCapitalized() + getClassPrefix()
                    + "ServiceImpl.java";
            FreemarkerAnalyer.writeResult(filePath, serviceImplTemplate, varMap);
        }
    }

    private static String removeDash(String str) {
        String lowerCaseStr = str.toLowerCase();
        String[] noDashArray = StringUtils.split(lowerCaseStr, '_');
        StringBuilder noDash = new StringBuilder(noDashArray[0]);
        for (int i = 1; i < noDashArray.length; i++) {
            noDash.append(StringUtils.capitalize(noDashArray[i]));
        }
        return noDash.toString();
    }

    public static void main(String[] args) throws Exception {

        Generate.handleTemplate();
        System.out.println("生成成功****");

    }

}
