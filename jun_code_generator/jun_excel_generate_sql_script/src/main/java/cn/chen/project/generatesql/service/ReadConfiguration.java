package cn.chen.project.generatesql.service;

import cn.chen.project.generatesql.entity.GenerateProperties;
import cn.hutool.setting.dialect.Props;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @Author Eddy·Chen
 * @Email 835033913@qq.com
 * @Create 2019-03-12 14:38
 */
public class ReadConfiguration {

    private File root;

    /**
     * 要读取的配置文件名称
     */
    public static final String CON_NAME = "config.properties";

    private GenerateProperties generateProperties;

    public ReadConfiguration() {
        String jarLocal = null;
        try {
            jarLocal = new String(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.root = new File(jarLocal).getParentFile();
        System.out.println("---初始化获得路径--" + this.root.getAbsolutePath());

        Props props = new Props(new File(root, CON_NAME), "utf-8");

        generateProperties = new GenerateProperties();
        generateProperties.setExcelPath(props.getStr("excelPath"));
        generateProperties.setExportSqlDir(props.getStr("exportSqlDir"));
        generateProperties.setAutoRunScript(props.getBool("autoRunScript", false));
        GenerateProperties.DataSource dataSource = new GenerateProperties.DataSource();
        dataSource.setHost(props.getStr("dataSource.host"));
        dataSource.setPassword(props.getStr("dataSource.password"));
        dataSource.setUser(props.getStr("dataSource.user"));
        dataSource.setDatabase(props.getStr("dataSource.database"));
        dataSource.setPort(props.getInt("dataSource.port"));
        generateProperties.setDataSource(dataSource);

        generateProperties.setOutputScriptFile(props.getBool("outputScriptFile", true));
        generateProperties.setBuildJava(props.getBool("buildJava", false));
        GenerateProperties.BuildConf buildConf = new GenerateProperties.BuildConf();
        buildConf.setAuthor(props.getStr("buildConf.author"));
        buildConf.setPackageName(props.getStr("buildConf.packageName"));
        buildConf.setExcludeTable(props.getStr("buildConf.excludeTable"));
        generateProperties.setBuildConf(buildConf);
    }

    public GenerateProperties getGenerateProperties() {
        return generateProperties;
    }
}
