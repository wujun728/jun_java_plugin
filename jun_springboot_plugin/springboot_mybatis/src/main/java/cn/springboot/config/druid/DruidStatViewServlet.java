package cn.springboot.config.druid;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/** 
 * @Description druid数据源状态监控
 * @author Wujun
 * @date Mar 16, 2017 3:15:56 PM  
 */
@WebServlet(urlPatterns = "/druid/*", initParams = { @WebInitParam(name = "allow", value = "192.168.1.72,127.0.0.1"), // IP白名单 (没有配置或者为空，则允许所有访问)
    @WebInitParam(name = "deny", value = "192.168.1.73"), // IP黑名单 (存在共同时，deny优先于allow)
    @WebInitParam(name = "loginUsername", value = "admin"), // 用户名
    @WebInitParam(name = "loginPassword", value = "123456"), // 密码
    @WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

    private static final long serialVersionUID = 1776463055477495407L;

}
