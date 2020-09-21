package com.redis.proxy.server.mgr;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import java.io.FileInputStream;

/**
 *
 * @author zhanggaofeng
 *
 */
public abstract class AbstDisconf {

        private final static Logger LOGGER = LoggerFactory.getLogger(AbstDisconf.class);
        private volatile Properties props;

        protected void fileLoad() throws Exception {
                Class<?> clazz = this.getClass();
                DisconfFile disFile = clazz.getAnnotation(DisconfFile.class);
                String fileName = disFile.filename();
                LOGGER.info(clazz.getName() + " reload......");
                Properties newprops = new Properties();
                try {
                        newprops.load(new FileInputStream(fileName));
                } catch (Exception e) {
                        newprops.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
                }
                props = newprops;
                LOGGER.info(clazz.getName() + " size = " + props.size());
                if (props.isEmpty() || getString("config.switch") == null) {
                        LOGGER.info(clazz.getName() + " start update callback");
                        try {
                                this.updateCallBack();
                                LOGGER.info(clazz.getName() + " end update callback");
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                }
        }

        public String getString(String key) {
                return props.getProperty(key);
        }

        public int getInt(String key) {
                return Integer.parseInt(props.getProperty(key));
        }

        public int getInt(String key, int defaultVal) {
                String val = props.getProperty(key);
                if (val == null || val.isEmpty()) {
                        return defaultVal;
                }
                return Integer.parseInt(val);
        }

        public int getIntMinDefault(String key, int defaultVal) {
                String val = props.getProperty(key);
                if (val == null || val.isEmpty()) {
                        return defaultVal;
                }
                return Integer.parseInt(val) < defaultVal ? defaultVal : Integer.parseInt(val);
        }

        public String getString(String key, String defaultVal) {
                String val = props.getProperty(key);
                if (val == null || val.isEmpty()) {
                        return defaultVal;
                }
                return val;
        }

        protected final Properties getDisconfProps() {
                return props;
        }

        /**
         * 更新回调
         */
        protected abstract void updateCallBack() throws Exception;
}
