package cn.chen.project.generatesql.entity;

import lombok.Data;

/**
 * @Author chenzedeng
 * @Email yustart@foxmail.com
 * @Create 2020-02-29 4:05 下午
 */
@Data
public class GenerateProperties {

    private String excelPath;

    /**
     * 导出的SQL脚本路径地址
     */
    private String exportSqlDir;

    /**
     * 是否写出脚本到文件
     */
    private boolean outputScriptFile;

    /**
     * 是否自动运行SQL脚本
     */
    private boolean autoRunScript;

    /**
     * 配置数据库
     */
    private DataSource dataSource;

    /**
     * 构建Java项目的文件
     */
    private BuildConf buildConf;

    /**
     * 是否构建Java项目文件
     */
    private boolean buildJava = false;

    @Data
    public static class DataSource {
        private String host;
        private String database;
        private Integer port = 3306;
        private String user;
        private String password;
    }

    @Data
    public static class BuildConf{
        private String packageName;
        private String author;
        private String excludeTable;
    }

}
