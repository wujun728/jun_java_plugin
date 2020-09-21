package com.github.ghsea.dbtracer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.dbtracer.config.DbTraceConfigException;
import com.github.ghsea.dbtracer.db.DataSourceFactory;
import com.github.ghsea.dbtracer.xml.TableConfiguration;
import com.github.ghsea.dbtracer.xml.XmlParser;

public class Bootstrap {

    private static Map<String, TableConfiguration> tableConfig;

    private static DataSource ds;

    private static boolean init = false;

    private static Object lock = new byte[0];

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * 
     * @Description: TODO
     * @param fileName 全局配置文件的相对路径
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void setUp(String fileName) throws FileNotFoundException, IOException {
        synchronized (lock) {
            if (init) {
                logger.error("DbTracerConfig has been already setUp.");
                return;
            }

            Properties config = loadProperties(fileName);
            String tblConfFile = config.getProperty("table.config");
            if (StringUtils.isBlank(tblConfFile)) {
                throw new DbTraceConfigException(
                        "Property named 'table.config' can not be null nor blank.Please check the config file:"
                                + fileName);
            }
            initTableConfig(tblConfFile);

            String dsFactoryClz = config.getProperty("datasource.factory.class");
            if (StringUtils.isBlank(dsFactoryClz)) {
                throw new DbTraceConfigException(
                        "Property named 'datasource.factory.class' can not be null nor blank.Please check the config file:"
                                + fileName);
            }
            initDsFactory(dsFactoryClz);

            init = true;
        }
    }

    private static Properties loadProperties(String fileName) throws IOException {
        FileReader configFileReader = null;
        try {
            String rootClassPath = Class.class.getResource("/").getPath();
            String path = rootClassPath + fileName;

            logger.info("Load config file from" + path);

            File file = new File(path);
            Properties config = new Properties();
            configFileReader = new FileReader(file);
            config.load(configFileReader);
            return config;
        } finally {
            if (configFileReader != null) {
                configFileReader.close();
            }
        }
    }

    private static void initDsFactory(String dsFactoryClz) {

        try {
            Object dsFactory = Class.forName(dsFactoryClz).newInstance();
            if (!DataSourceFactory.class.isAssignableFrom(dsFactory.getClass())) {
                throw new DbTraceConfigException(
                        "dsFactoryClz must a subclass of com.github.ghsea.dbtracer.db.DataSourceFactory");
            }
            ds = ((DataSourceFactory) dsFactory).getDataSource();
        } catch (Exception ex) {
            throw new DbTraceConfigException(ex);
        }
    }

    private static void initTableConfig(String tblConfFile) {
        XmlParser parse = new XmlParser(tblConfFile);
        tableConfig = parse.parse();
    }

    public static DataSource getDataSource() {
        checkState();
        return ds;
    }

    public static Map<String, TableConfiguration> getTableConfiguration() {
        checkState();
        return tableConfig;
    }

    private static void checkState() {
        if (!init) {
            throw new IllegalStateException("DbTracerConfig has not been setUp.Please call setUp() method first.");
        }
    }

}
