package org.myframework.web;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
/**
 * @rem: spring annotation mvc
 * @author: wanghui
 * @version: 1.0
 * @since 2011-3-7
 */
public class MyDispatchServlet extends DispatcherServlet {   
	private static Log log = LogFactory.getLog(MyDispatchServlet.class); 
    /**
	 * 
	 */
	private static final long serialVersionUID = -5593420945616849603L;
	private static List<ModelAndView> modelAndViews = new ArrayList<ModelAndView>(20);   
       
    static{   
        for(int i = 0; i < 20; i++){  //缓存量为20   
            modelAndViews.add(new ModelAndView(""));   
        }   
    }   
       
    public void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response)   
    throws Exception{   
        super.render(mv, request, response); 
        releaseModelAndView(mv);  
    }   
       
        //回收ModelAndView   
    private void releaseModelAndView(ModelAndView mv){   
        mv.clear();   
        mv.getModelMap().clear();   
        synchronized(modelAndViews){   
            modelAndViews.add(mv);   
        }   
    }   
       
        //取ModelAndView   
    public static ModelAndView getModelAndView(){ 
    	log.debug("modelAndViews size :" + modelAndViews.size());
        synchronized(modelAndViews){   
            if(modelAndViews.size() > 0) {
            	ModelAndView mv =modelAndViews.remove(0);
            	return mv;
            }else{  
                return new ModelAndView(""); 
            }
        }   
    }   
       
}   
