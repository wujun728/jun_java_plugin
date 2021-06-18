package com.itstyle.jwt.common.interceptor;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itstyle.jwt.common.constant.SystemConstant;
import com.itstyle.jwt.common.entity.CheckResult;
import com.itstyle.jwt.common.entity.R;
import com.itstyle.jwt.common.util.JwtUtils;
/**
 * 拦截器 用户权限校验
 * 创建者   科帮网 
 * 创建时间  2017年11月24日
 */
public class SysInterceptor implements HandlerInterceptor {  
    
	private static final Logger logger = LoggerFactory.getLogger(SysInterceptor.class);
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  
            throws Exception {  
    	if (handler instanceof HandlerMethod){
    		String authHeader = request.getHeader("token");
        	if (StringUtils.isEmpty(authHeader)) {
        	  logger.info("验证失败");
        	  print(response,R.error(SystemConstant.JWT_ERRCODE_NULL,"签名验证不存在"));
              return false;
            }else{
            	//验证JWT的签名，返回CheckResult对象
                CheckResult checkResult = JwtUtils.validateJWT(authHeader);
                if (checkResult.isSuccess()) {
                	return true;
                } else {
                    switch (checkResult.getErrCode()) {
                    // 签名验证不通过
                    case SystemConstant.JWT_ERRCODE_FAIL:
                    	logger.info("签名验证不通过");
                    	print(response,R.error(checkResult.getErrCode(),"签名验证不通过"));
                    	break;
                     // 签名过期，返回过期提示码
                    case SystemConstant.JWT_ERRCODE_EXPIRE:
                    	logger.info("签名过期");
                    	print(response,R.error(checkResult.getErrCode(),"签名过期"));
                    	break;
                    default:
                        break;
                    }
                    return false;
                }
            }
		}else{
			return true;
		}
    }  
    public void print(HttpServletResponse response,Object message){
    	try {
			response.setStatus(HttpStatus.OK.value());
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			PrintWriter writer = response.getWriter();
			writer.write(message.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  
                           ModelAndView modelAndView) throws Exception {  
        if(response.getStatus()==500){  
            modelAndView.setViewName("/error/500");  
        }else if(response.getStatus()==404){  
            modelAndView.setViewName("/error/404");  
        }  
    }  
  
    /**  
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，  
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。  
     */    
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
            throws Exception {  
    }  
}  