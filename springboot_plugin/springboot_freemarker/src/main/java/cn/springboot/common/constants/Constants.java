package cn.springboot.common.constants;

/** 
 * @Description 常量类
 * @author Wujun
 * @date Apr 12, 2017 9:42:34 AM  
 */
public class Constants {

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;

    /* 状态,1=有效，0=失效 */
    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;

    /* session & session key */
    public static final String PERMISSION_SESSION = "permission_session";
    public static final String SESSION_KEY = "session_key";

    // url & roleName
    public static final String ROLE_CODE = "role_code";
    public static final String PERMISSION_URL = "permission_url";

    public static final String ROLE_BOSS_CODE = "boss_role";
    public static final String ROLE_MANAGER_CODE = "manager_role";// 管理员
    public static final String COMMON_ROLE_CODE = "common_role";// 普通用户

    public static final String ROLE_BOSS_NAME = "总经理";
    public static final String ROLE_MANAGER_NAME = "管理员";
    public static final String ROLE_COMMON_NAME = "普通用户";
}
