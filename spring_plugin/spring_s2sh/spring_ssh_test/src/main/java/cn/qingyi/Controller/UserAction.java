package cn.qingyi.Controller;

import cn.qingyi.Entity.User;
import cn.qingyi.Service.UserService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.*;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户动作控制类
 *
 * @author qingyi xuelongqy@foxmail.com
 * @Title: UserAction类
 * @Description: 用户动作控制类
 * @version: V1.0
 * @date 2017 -03-06 11:27:22
 */
@ParentPackage("default")
@Namespace("/User")
public class UserAction implements ModelDriven {

    //获取request和session
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;

    //用户服务类
    @Autowired
    private UserService userService;

    //用户模型(Model)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 用户登录的Action
     *
     * @return the string
     * @Title: login方法
     * @Description: 这里用一句话描述这个方法的作用
     * @author qingyi xuelongqy@foxmail.com
     * @date 2017 -03-15 13:22:17
     */
    @Action(
            interceptorRefs = {@InterceptorRef("LoginStack")},
            value = "login",
            results = {
                    @Result(name="ok",location="/index.jsp"),
                    @Result(name="fail",location="/login.jsp")
            }
    )
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(type= ValidatorType.SIMPLE,fieldName="User.username",message="用户名不能为空!"),
                    @RequiredStringValidator(type=ValidatorType.SIMPLE,fieldName="User.password",message="密码不能为空!"),
            },
            stringLengthFields = {
                    @StringLengthFieldValidator(fieldName="User.password",minLength="6",maxLength="20",message="密码的长度必须大于6小于20个字符!")
            }
    )
    public String login(){
        if (userService.login(user)){
            request.setAttribute("Loginstatus",user.getUsername()+"登录成功!");

            //记录登录信息
            session.setAttribute("username",user.getUsername());
            session.setAttribute("password",user.getPassword());
            session.setAttribute("loginStatus","true");
            return "ok";
        }
        else{
            request.setAttribute("Loginstatus","登录失败!");
            request.setAttribute("registerStatus","登录失败!");
            return "fail";
        }
    }

    @Action(
            value = "register",
            results = {
                    @Result(name="success",location="/login.jsp"),
                    @Result(name="fail",location="/login.jsp")
            }
    )
    public String register(){
        if (userService.register(user)){
            request.setAttribute("registerStatus","注册成功!");
            return "success";
        }
        else{
            request.setAttribute("registerStatus","注册失败!");
            return "fail";
        }
    }

    @Override
    public Object getModel() {
        if(user == null){
            user = new User();
        }
        return user;
    }
}
