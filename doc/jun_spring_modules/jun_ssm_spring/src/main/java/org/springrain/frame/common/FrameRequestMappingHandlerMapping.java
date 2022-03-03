package org.springrain.frame.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 扩展实现 restful 路由查找,提高spring mvc 性能,适用于通配符且数量可控的情况. <br />
 * 不建议id放入url,不然数量会非常庞大,需要在请求header或者参数里放入服务名,根据服务名查找<br />
 * 参考资料:https://tech.imdada.cn/2015/12/23/springmvc-restful-optimize/
 * @author caomei
 *
 */
public class FrameRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    //绑定HandlerMethod 和 RequestMappingInfo的关系
    private static Map<HandlerMethod, RequestMappingInfo> registerHandlerMethodMap = new HashMap<HandlerMethod, RequestMappingInfo>();
    
    //绑定lookupPath 和 HandlerMethod的关系
    private static Map<String, HandlerMethod> lookupHandlerMethodMap = new HashMap<String, HandlerMethod>();

   /**
    * 注册时绑定 HandlerMethod 和 RequestMappingInfo的关系
    */
    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod handlerMethod = createHandlerMethod(handler, method);
       // RequestMapping rMapping = AnnotationUtils.getAnnotation(method, RequestMapping.class);
        registerHandlerMethodMap.put(handlerMethod, mapping);
        super.registerHandlerMethod(handler, method, mapping);
    }

    
    /**
     * 根据路由直接返回HandlerMethod,不再查找.
     */
    @Override
    @Nullable
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        
        //查找的key,默认使用请求方法+uri实现,如果id在uri里,需要前端请求时放入服务名,通过服务名做key,不然uri会非常多,缓存会比较吃力.
        String mapKey=request.getMethod()+"_"+lookupPath;
        
        HandlerMethod handlerMethod = lookupHandlerMethodMap.get(mapKey);
        
        if (handlerMethod != null) {
            //处理PathVariable的参数,registerHandlerMethodMap就是为了这个功能记录了HandlerMethod 和 RequestMappingInfo的关系
            handleMatch(registerHandlerMethodMap.get(handlerMethod), lookupPath, request);
            return handlerMethod;
        } else {
            handlerMethod = super.lookupHandlerMethod(lookupPath, request);
            lookupHandlerMethodMap.put(mapKey, handlerMethod);
        }

        return handlerMethod;

    }

}
