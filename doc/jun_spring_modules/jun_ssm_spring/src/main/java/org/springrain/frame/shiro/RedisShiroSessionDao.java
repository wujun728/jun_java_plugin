package org.springrain.frame.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.cache.ICache;
import org.springrain.frame.util.SerializeUtils;

/**
 * Redis实现的 ShiroSessionDao，暂不使用
 * @author caomei
 *
 */
@Deprecated
public class RedisShiroSessionDao extends AbstractSessionDAO {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	private String sessionprefix="ss-";
	public RedisShiroSessionDao (){
	}
	private ICache cache;
	@Override
	public void update(Session session) throws UnknownSessionException {
		try {
			cache.set(session.getId().toString().getBytes(),SerializeUtils.serialize(session),session.getTimeout()/1000);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@Override
	public void delete(Session session) {
		try {
			cache.del(session.getId().toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Session> getActiveSessions() {
		String keys=sessionprefix+"*";
		List<Session> list=null;
		try {
		 list=	(List<Session>) cache.keys(keys.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId=session.getId();
		try {
			super.assignSessionId(session,sessionprefix+ super.generateSessionId(session));
			update(session);
			sessionId=session.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return sessionId;
		
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session=null;
		try {
			session=	(Session) cache.get(sessionId.toString().getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return session;
		
	
	}

	public ICache getCached() {
		return cache;
	}

	public void setCached(ICache cached) {
		this.cache = cached;
	}

	public String getSessionprefix() {
		return sessionprefix;
	}

	public void setSessionprefix(String sessionprefix) {
		this.sessionprefix = sessionprefix;
	}
	
}
