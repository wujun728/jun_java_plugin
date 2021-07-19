package com.jun.plugin.picturemanage.conf;

import lombok.Getter;


/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 15:56
 */
public class Constant {

    /**
     * 文件根路径
     */
    public static String ROOT_DIR = null;

    /**
     * Http路径访问前缀Key
     */
    public static final String URL_PREFIX_KEY_NAME = "url_prefix_conf";

    /**
     * 第一次配置的文件根路径
     */
    public static final String FILE_ROOT_KEY_NAME = "file_root_path";

    /**
     * 存储用户信息的Key
     */
    public static final String USER_KEY_NAME = "user_entity";

    /**
     * UUID重写文件名称开关
     */
    public static final String UUID_FILE_NAME_SWITCH_KEY_NAME = "uuid_replace_switch";

    /**
     * 登陆状态
     */
    public static final String LOGIN_STATE = "login_state";

    @Getter
    public enum FileType {
        FOLDER(1, "文件夹"),
        FILE(0, "文件");

        private Integer type;

        private String describe;

        FileType(Integer type, String describe) {
            this.type = type;
            this.describe = describe;
        }
    }
}
