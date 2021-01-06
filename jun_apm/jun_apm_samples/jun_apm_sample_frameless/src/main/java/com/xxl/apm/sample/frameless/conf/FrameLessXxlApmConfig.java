package com.xxl.apm.sample.frameless.conf;

import com.xxl.apm.client.factory.XxlApmFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author xuxueli 2018-10-31 19:05:43
 */
public class FrameLessXxlApmConfig {
    private static Logger logger = LoggerFactory.getLogger(FrameLessXxlApmConfig.class);


    private static XxlApmFactory xxlApmFactory = null;

    /**
     * init
     */
    public static void start() {
        Properties prop = loadProperties("xxl-apm.properties");

        // start
        XxlApmFactory xxlApmFactory = new XxlApmFactory();
        xxlApmFactory.setAppname(prop.getProperty("xxl-apm.appname"));
        xxlApmFactory.setAdminAddress(prop.getProperty("xxl-apm.adminAddress"));
        xxlApmFactory.setAccessToken(prop.getProperty("xxl-apm.rpc.accessToken"));
        xxlApmFactory.setMsglogpath(prop.getProperty("xxl-apm.msglog.path"));
        xxlApmFactory.start();
    }

    /**
     * destory
     */
    public static void stop() {
        xxlApmFactory.stop();
    }



    public static Properties loadProperties(String propertyFileName) {
        InputStreamReader in = null;
        try {
            ClassLoader loder = Thread.currentThread().getContextClassLoader();

            in = new InputStreamReader(loder.getResourceAsStream(propertyFileName), "UTF-8");;
            if (in != null) {
                Properties prop = new Properties();
                prop.load(in);
                return prop;
            }
        } catch (IOException e) {
            logger.error("load {} error!", propertyFileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close {} error!", propertyFileName);
                }
            }
        }
        return null;
    }

}
