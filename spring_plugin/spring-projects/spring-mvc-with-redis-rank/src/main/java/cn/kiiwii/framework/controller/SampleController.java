package cn.kiiwii.framework.controller;

import cn.kiiwii.framework.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhong on 2016/9/17.
 */
@Controller
@RequestMapping("/")
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Autowired
    private ITestService testService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/add/{id}/{score}")
    public void add(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id,@PathVariable("score") int score){

        this.testService.addCore(id,score);
    }
    @RequestMapping("/getTop/{top}")
    public String getTop(HttpServletRequest request, HttpServletResponse response,@PathVariable("top") int top){

        Set set = this.testService.getTop(top);
        System.out.println(set);
        request.setAttribute("tops",set);
        return "top";
    }
    @RequestMapping("/getTopScore/{top}")
    public String getTopScore(HttpServletRequest request, HttpServletResponse response,@PathVariable("top") int top){

        Set set = this.testService.getTopWithScore(top);
        request.setAttribute("topScores",set);
        Iterator<DefaultTypedTuple> iterator = set.iterator();
        while (iterator.hasNext()){
            DefaultTypedTuple defaultTypedTuple = iterator.next();
            System.out.println(defaultTypedTuple.getValue()+":"+defaultTypedTuple.getScore());
        }
        return "topScore";
    }
    @RequestMapping("/getTopScore/{start}/{limit}")
    public String getLimitTopScore(HttpServletRequest request, HttpServletResponse response,@PathVariable("start") int start,@PathVariable("limit") int limit){

        Set set = this.testService.getTopWithScore(start,limit);
        request.setAttribute("topScores",set);
        Iterator<DefaultTypedTuple> iterator = set.iterator();
        return "topScore";
    }
}
