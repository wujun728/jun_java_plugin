package messfairy.controller;
import messfairy.dao.UsersDao;
import messfairy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sessions")
public class SessionsController{
    @Autowired
    private UsersDao userDao;

    @RequestMapping(value={"/signup"}, method={RequestMethod.GET})
    public String signUpView() {
        return "pages/signup";
    }

    @RequestMapping(value={"/login"}, method={RequestMethod.GET})
    public String loginView(){
        return "pages/login";
    }

    @RequestMapping(value={"/signup"}, method={RequestMethod.POST})
    public String create(@ModelAttribute User user, Model model) {
        User existUser = null;
        try{
            existUser = userDao.byName(user.getName());
        }catch (Exception e){
            existUser = null;
        }
        if(existUser!=null){
            model.addAttribute("error", "用户名已存在，请更换用户名");
            System.out.println(existUser.toString());
            return "redirect:/sessions/signup";
        }
        this.userDao.create(user);
        return "redirect:/sessions/login";
    }

    @RequestMapping(value={"/login"}, method={RequestMethod.POST})
    public String login(@ModelAttribute User user, Model model,  HttpSession session) {
        User current_user = null;
        try{
            current_user = userDao.byLogin(user);
        }catch (Exception e){
            current_user = null;
        }
        if(current_user==null){
            model.addAttribute("error", "用户名或密码错误，登录失败, 请重新登录或注册新账户");
            return "redirect:/sessions/login";
        }else{
            session.setAttribute("current_user", current_user);
            System.out.println(current_user.toString());
        }
        return "redirect:/posts/new";
    }

    @RequestMapping(value={"/loginout"}, method={RequestMethod.GET})
    public String loginout(HttpSession session) {
        session.removeAttribute("current_user");
        session.invalidate();
        return "redirect:/";
    }
}
