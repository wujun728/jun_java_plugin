package org.springrain.frame.shiro;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.GlobalStatic;

/**
 * 保存最新的用户在线，踢出上一个用户
 * @author caomei
 *
 */

@Component("keepone")
public class KeepOneSessionControlFilter extends AccessControlFilter {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
	private SessionManager sessionManager;
    @Resource
    private CacheManager cacheManager;


	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		String userId = SessionUser.getUserId();
		if (StringUtils.isBlank(userId)) {// 没有登录
			return true;
		}
		
		//当前session 的Id
		Serializable sessionId =SessionUser.getSession().getId();

		
		//当前用户缓存中的sessionId
		  Cache cache = cacheManager.getCache(GlobalStatic.springrainkeeponeCacheKey);
		  String deleteSessionId = cache.get(userId,String.class);
	
		if (sessionId.toString().equalsIgnoreCase(deleteSessionId)) {
			return true;
		} else if(StringUtils.isBlank(deleteSessionId)){
			cache.put(userId, sessionId.toString());
			return true;
		}else {
			cache.put(userId, sessionId.toString());
			
			//Session deletetSession = sessionManager.getSession(new DefaultSessionKey(deleteSessionId));
			Session deletetSession=null;
			try {
			    deletetSession = sessionManager.getSession(new DefaultSessionKey(deleteSessionId));
			} catch (UnknownSessionException e) {//no session with  id [deleteSessionId]
				logger.error(e.getMessage(),e);
			} catch(ExpiredSessionException e){//Session with id [deleteSessionId] has expired
				logger.error(e.getMessage(),e);
			}
			
			if (deletetSession == null) {
				return true;
			}
			//根据 需要删除的 sessionId,生成subject
			Subject deleteSubject = new Subject.Builder().sessionId(deleteSessionId).buildSubject();
            //退出
			deleteSubject.logout();
			
			//在此可以自定义json格式的回复
			return true;

		}

	}


}
