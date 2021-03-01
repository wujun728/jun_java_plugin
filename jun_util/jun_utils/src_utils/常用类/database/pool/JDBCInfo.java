package book.database.pool;
/**
 * 创建一个数据库连接池需要的信息：用户名、密码、最大连接数等
 */
public class JDBCInfo {
    /**连接数据库的相关信息*/
    private String driver;	//连接数据库的驱动类
    private String url;	//数据库的路径
    private String user;	//用户名
    private String password;//密码
    private String name;	//数据库连接池的名字
    private int maxconn;	//最大连接数
    
    public JDBCInfo() {
        this.driver = "";
        this.url = "";
        this.name = "";	
        this.maxconn = 0;
    } 
    public JDBCInfo(String name, String driver, String url, 
    		String user, String password, int maxconn) {
        this.name = name;
        this.driver = driver;
        this.user = user;
        this.password = password;
        this.maxconn = maxconn;
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public int getMaxconn() {
        return maxconn;
    }
    public void setMaxconn(int maxconn) {
        this.maxconn = maxconn;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}