package cc.mrbird.febs.common.entity;

/**
 * 常量
 *
 * @author MrBird
 */
public interface FebsConstant {

    /**
     * 注册用户角色ID
     */
    Long REGISTER_ROLE_ID = 2L;

    /**
     * 排序规则：降序
     */
    String ORDER_DESC = "desc";

    /**
     * 排序规则：升序
     */
    String ORDER_ASC = "asc";

    /**
     * 前端页面路径前缀
     */
    String VIEW_PREFIX = "febs/views/";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    /**
     * febs-shiro线程池名称
     */
    String FEBS_SHIRO_THREAD_POOL = "febsShiroThreadPool";

    /**
     * febs-shiro线程名称前缀
     */
    String FEBS_SHIRO_THREAD_NAME_PREFIX = "febs-shiro-thread-";

    /**
     * 开发环境
     */
    String DEVELOP = "dev";

    /**
     * Windows 操作系统
     */
    String SYSTEM_WINDOWS = "windows";

    String REQUEST_ALL = "/**";

    String DAY_START_PATTERN_SUFFIX = " 00:00:00";
    String DAY_END_PATTERN_SUFFIX = " 23:59:59";

    /**
     * 验证码ey前缀
     */
    String VALIDATE_CODE_PREFIX = "febs_captcha_";

    /**
     * 验证码有效期Key前缀
     */
    String VALIDATE_CODE_TIME_PREFIX = "febs_captcha_time_";
}
