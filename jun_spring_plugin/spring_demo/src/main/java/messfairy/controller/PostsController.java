package messfairy.controller;

import messfairy.dao.PostsDao;
import messfairy.entity.Post;
import messfairy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by hasee-pc on 2014/11/12.
 */
@Controller
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    PostsDao postsDao;
    @RequestMapping(value={"/new"}, method={RequestMethod.GET})
    public String newPostView(@ModelAttribute User user, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("current_user");
        if(currentUser==null){
            return "redirect:/sessions/login";
        }else{
            model.addAttribute("current_user", currentUser);
            return "pages/posts/new";
        }
    }

    @RequestMapping(value={""}, method={RequestMethod.POST})
    public String newPost(@ModelAttribute Post post, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("current_user");
        if(currentUser==null)return "redirect:/sessions/login";
        post.setUserId(currentUser.getId());
        try{
            postsDao.create(post);
        }catch (Exception e){
            model.addAttribute("error", "创建文章后台失败");
        }
        return "redirect:/posts";
    }

    @RequestMapping(value={""}, method={RequestMethod.GET})
    public String index(Model model) {
        try{
            model.addAttribute("posts", postsDao.all());
        }catch (Exception e){
            model.addAttribute("error", "创建文章后台失败");
        }
        return "pages/posts/index";
    }

    @RequestMapping(value={"/{id}"}, method={RequestMethod.GET})
    public String show(@PathVariable("id") int id, Model model) {
        try{
            model.addAttribute("post", postsDao.byId(id));
        }catch (Exception e){
            model.addAttribute("error", "获取id为"+id+"的文章失败");
        }
        return "pages/posts/show";
    }
}
