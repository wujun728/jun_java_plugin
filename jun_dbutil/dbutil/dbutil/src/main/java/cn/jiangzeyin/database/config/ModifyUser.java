package cn.jiangzeyin.database.config;

import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.util.Assert;
import cn.jiangzeyin.util.StringUtil;
import com.alibaba.druid.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public class ModifyUser {


    public static class Modify {
        private static List<Class<?>> modify_class = new ArrayList<>();
        private static String modifyTime;
        private static String columnUser;
        private static String columnTime;

        public static String getColumnTime() {
            return columnTime;
        }

        public static String getColumnUser() {
            return columnUser;
        }

        public static String getModifyTime() {
            return modifyTime;
        }

        public static boolean isModifyClass(Class tClass) {
            if (tClass == null)
                return false;
            for (Class<?> item : modify_class) {
                if (item.isAssignableFrom(tClass))
                    return true;
            }
            return false;
        }
    }

    public static class Create {
        private static List<Class<?>> create_class = new ArrayList<>();
        private static String columnUser;

        public static String getColumnUser() {
            return columnUser;
        }

        public static boolean isCreateClass(Class tClass) {
            if (tClass == null)
                return false;
            for (Class<?> item : create_class) {
                if (item.isAssignableFrom(tClass))
                    return true;
            }
            return false;
        }
    }

    static void initCreate(Properties properties) {
        Assert.notNull(properties);
        String createClass = properties.getProperty(ConfigProperties.PROP_CREATE_CLASS);
        if (StringUtils.isEmpty(createClass))
            return;

        String[] createClass_s = StringUtil.stringToArray(createClass);
        if (createClass_s == null || createClass_s.length < 1) {
            SystemDbLog.getInstance().warn(ConfigProperties.PROP_CREATE_CLASS + " is null");
        } else {
            for (String item : createClass_s) {
                try {
                    Create.create_class.add(Class.forName(item));
                } catch (ClassNotFoundException e) {
                    SystemDbLog.getInstance().error("load class", e);
                }
            }

            String column_user = properties.getProperty(ConfigProperties.PROP_CREATE_COLUMN_USER);
            if (StringUtils.isEmpty(column_user)) {
                SystemDbLog.getInstance().warn(ConfigProperties.PROP_LAST_MODIFY_COLUMN_USER + " is null");
            } else {
                Create.columnUser = column_user;
            }
        }
    }

    static void initModify(Properties properties) {
        Assert.notNull(properties);
        String modifyClass = properties.getProperty(ConfigProperties.PROP_LAST_MODIFY_CLASS);
        if (!StringUtils.isEmpty(modifyClass)) {
            String[] modifyClass_s = StringUtil.stringToArray(modifyClass);
            if (modifyClass_s == null || modifyClass_s.length < 1) {
                SystemDbLog.getInstance().warn(ConfigProperties.PROP_LAST_MODIFY_CLASS + " is null");
            } else {
                for (String item : modifyClass_s) {
                    try {
                        Modify.modify_class.add(Class.forName(item));
                    } catch (ClassNotFoundException e) {
                        SystemDbLog.getInstance().error("load class", e);
                    }
                }

                String modify_time = properties.getProperty(ConfigProperties.PROP_LAST_MODIFY_TIME);
                if (StringUtils.isEmpty(modify_time)) {
                    SystemDbLog.getInstance().warn(ConfigProperties.PROP_LAST_MODIFY_TIME + " is null");
                } else {
                    Modify.modifyTime = modify_time;
                }

                String column_user = properties.getProperty(ConfigProperties.PROP_LAST_MODIFY_COLUMN_USER);
                if (StringUtils.isEmpty(column_user)) {
                    SystemDbLog.getInstance().warn(ConfigProperties.PROP_LAST_MODIFY_COLUMN_USER + " is null");
                } else {
                    Modify.columnUser = column_user;
                }

                String column_time = properties.getProperty(ConfigProperties.PROP_LAST_MODIFY_COLUMN_TIME);
                if (StringUtils.isEmpty(column_time)) {
                    SystemDbLog.getInstance().warn(ConfigProperties.PROP_LAST_MODIFY_COLUMN_TIME + " is null");
                } else {
                    Modify.columnTime = column_time;
                }
            }
        }
    }
}
