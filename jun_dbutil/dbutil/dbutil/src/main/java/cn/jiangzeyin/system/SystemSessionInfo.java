package cn.jiangzeyin.system;

/**
 * 系统session 信息
 *
 * @author jiangzeyin
 */
public class SystemSessionInfo {
    private static SessionUser sessionUser;

    private SystemSessionInfo() {

    }

    /**
     * @param sessionUser 接口
     * @author jiangzeyin
     */
    public static void setSessionUser(SessionUser sessionUser) {
        SystemSessionInfo.sessionUser = sessionUser;
    }

    /**
     * 获取当前操作session 用户名
     *
     * @return 用户名
     * @author jiangzeyin
     */
    public static String getUserName() {
        if (sessionUser == null)
            return "";
        return sessionUser.getUserName();
    }

    /**
     * 获取当前操作session 用户id
     *
     * @return id
     * @author jiangzeyin
     */
    public static int getUserId() {
        if (sessionUser == null)
            return -1;
        return sessionUser.getUserId();
    }

    public static String userIdGetName(int userId) {
        if (sessionUser == null)
            return "";
        return sessionUser.userIdGetName(userId);
    }

    /**
     * 获取session 信息接口
     *
     * @author jiangzeyin
     */
    public interface SessionUser {

        String getUserName();

        int getUserId();

        String userIdGetName(int userId);
    }
}
