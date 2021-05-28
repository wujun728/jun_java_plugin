package cn.com.bluemoon.common.interceptor;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.util.concurrent.RateLimiter;

import cn.com.bluemoon.common.exception.WebException;
import cn.com.bluemoon.common.exception.WebExceptionEnum;

/**
 * Guava 限流器
 * @author Wujun
 *
 */
public class LimitInterceptor extends HandlerInterceptorAdapter {
	
	public enum LimitType {
		DROP,	//丢弃
		WAIT	//等待
	}
	
	/**
	 * 限流器
	 */
	private RateLimiter limiter;
	
	/**
	 * 限流方式
	 */
	private LimitType limitType = LimitType.DROP;
	
	public LimitInterceptor() {
		this.limiter = RateLimiter.create(1);
	}
	
	/**
	 * @param tps	限流（每秒处理量）
	 * @param limitType
	 */
	public LimitInterceptor(int tps, LimitInterceptor.LimitType limitType) {
		this.limiter = RateLimiter.create(tps);
		this.limitType = limitType;
	}
	
	/**
	 * @param permitsPerSecond	每秒新增的令牌数
	 * @param limitType	限流类型
	 */
	public LimitInterceptor(double permitsPerSecond, LimitInterceptor.LimitType limitType) {
		this.limiter = RateLimiter.create(permitsPerSecond, 1000, TimeUnit.MILLISECONDS);
		this.limitType = limitType;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    if (limitType.equals(LimitType.DROP)) {
		    if (limiter.tryAcquire()) {
		        return super.preHandle(request, response, handler);
		    }
	    } else {
	    	limiter.acquire();
	    	return super.preHandle(request, response, handler);
	    }
	    throw new WebException(WebExceptionEnum.REQUEST_LIMIT);//达到限流后，往页面提示的错误信息。
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
	public RateLimiter getLimiter() {
	    return limiter;
	}
	 
	public void setLimiter(RateLimiter limiter) {
	    this.limiter = limiter;
	}

}
