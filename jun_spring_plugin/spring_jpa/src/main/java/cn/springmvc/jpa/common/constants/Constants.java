package cn.springmvc.jpa.common.constants;

/**
 * @author Wujun
 *
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

    public static final String SYSTEM_ROLE_CODE = "system_role";// 系统管理员
    public static final String COMMON_ROLE_CODE = "common_role";// 普通用户

    public static final String SYSTEM_ROLE_NAME = "系统管理员";
    public static final String COMMON_ROLE_NAME = "普通用户";

    public final static String PATH = "/home/vincent/";

    /**
     * 角色： 系统管理员：ROLE_SYSTEM_ADMIN<br/>
     * QA：ROLE_QA <br/>
     * 客服管理员：ROLE_CUSTOMER_SERVICE_ADMIN <br/>
     * 客服用户：ROLE_CUSTOMER_SERVICE<br/>
     */
    public static final String ROLE_SYSTEM_ADMIN = "role_system_admin";
    public static final String ROLE_QA = "role_qa";
    public static final String ROLE_CUSTOMER_SERVICE_ADMIN = "role_customer_service_admin";
    public static final String ROLE_CUSTOMER_SERVICE = "role_customer_service";

    public final static int DB_COUNT = 2;
    public final static int TB_DB_COUNT = 2;
    public final static int TB_DB_USERTABLE_COUNT = 256;
    public final static int TB_DB_RESEARCHTABLE_COUNT = 256;
    public final static int DB_USERTABLE_COUNT = 256;
    public final static int DB_RESEARCHTABLE_COUNT = 256;

    // mail spot
    public static final String MAIL_USER = "mail.user";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String MAIL_SMTP = "mail.smtp";
    public static final String MAIL_POP = "mail.pop";
    public static final String MAIL_IMAP = "mail.imap";

    /**
     * Queue name
     */
    public static final String QUEUE_FOO_BAR = "foo.bar";

}
