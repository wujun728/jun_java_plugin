package cn.jiangzeyin.database.config;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public class ConfigProperties {
    // 多数据库标识
    public static final String PROP_SOURCE_TAG = "sourceTag";
    // 多数据源对应配置路径
    public static final String PROP_CONFIG_PATH = "configPath";

    public static final String PROP_SYSTEM_KEY = "systemKey";

    public static final String PROP_SYSTEM_KEY_COLUMN = "systemKeyColumn";
    // 修改
    public static final String PROP_LAST_MODIFY = "lastModify";
    // 标记修改时间表达式
    public static final String PROP_LAST_MODIFY_TIME = PROP_LAST_MODIFY + ".time";
    // 有修改者实体类
    public static final String PROP_LAST_MODIFY_CLASS = PROP_LAST_MODIFY + ".class";
    // 实体对应修改者id 字段
    public static final String PROP_LAST_MODIFY_COLUMN_USER = PROP_LAST_MODIFY + ".column.user";
    // 实体修改时间字段
    public static final String PROP_LAST_MODIFY_COLUMN_TIME = PROP_LAST_MODIFY + ".column.time";
    // 创建
    public static final String PROP_CREATE = "create";
    // 有创建者实体类
    public static final String PROP_CREATE_CLASS = PROP_CREATE + ".class";
    // 实体对应创建者id 字段
    public static final String PROP_CREATE_COLUMN_USER = PROP_CREATE + ".column.user";
    // 系统字段
    public static final String PROP_SYSTEM_COLUMN = "systemColumn";
    // 系统数据库密码字段（使用mysql password 函数）
    public static final String PROP_SYSTEM_COLUMN_PWD = PROP_SYSTEM_COLUMN + ".pwd";
    // 活跃字段
    public static final String PROP_SYSTEM_COLUMN_ACTIVE = PROP_SYSTEM_COLUMN + ".active";
    //
    public static final String PROP_SYSTEM_COLUMN_UN_ACTIVE = PROP_SYSTEM_COLUMN + ".inActive.value";
    // 活跃字段值
    public static final String PROP_SYSTEM_COLUMN_ACTIVE_VALUE = PROP_SYSTEM_COLUMN_ACTIVE + ".value";

    public static final String PROP_SYSTEM_COLUMN_MODIFY = PROP_SYSTEM_COLUMN + ".modify";

    public static final String PROP_SYSTEM_COLUMN_MODIFY_STATUS = PROP_SYSTEM_COLUMN_MODIFY + ".status";

    public static final String PROP_SYSTEM_COLUMN_MODIFY_COLUMN = PROP_SYSTEM_COLUMN_MODIFY + ".column";

    public static final String PROP_SYSTEM_COLUMN_MODIFY_TIME = PROP_SYSTEM_COLUMN_MODIFY + ".time";

    public static final String PROP_SYSTEM_COLUMN_NOT_PUT_UPDATE = PROP_SYSTEM_COLUMN + ".notPutUpdate";

    public static final String PROP_SYSTEM_COLUMN_COLUMN_DEFAULT_VALUE = PROP_SYSTEM_COLUMN + ".columnDefaultValue";

    public static final String PROP_SYSTEM_COLUMN_WRITE_DEFAULT_REMOVE = PROP_SYSTEM_COLUMN + ".writeDefaultRemove";

    public static final String PROP_SYSTEM_COLUMN_READ_DEFAULT_REMOVE = PROP_SYSTEM_COLUMN + ".readDefaultRemove";

    public static final String PROP_SYSTEM_COLUMN_SELECT_DEFAULT_COLUMNS = PROP_SYSTEM_COLUMN + ".selectDefaultColumns";

    public static final String PROP_SYSTEM_COLUMN_DEFAULT_REF_KEY_NAME = PROP_SYSTEM_COLUMN + ".defaultRefKeyName";

    public static final String PROP_SYSTEM_COLUMN_DEFAULT_KEY_NAME = PROP_SYSTEM_COLUMN + ".defaultKeyName";


}
