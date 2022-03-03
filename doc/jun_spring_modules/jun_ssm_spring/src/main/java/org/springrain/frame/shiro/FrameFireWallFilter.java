package org.springrain.frame.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.IPUtils;
/**
 * 记录访问日志的过滤器
 * @author caomei
 *
 */


public class FrameFireWallFilter extends OncePerRequestFilter {
	//private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private CacheManager cacheManager;
	
	
	//同一IP防火墙阀值
	private Integer firewallLockCheckCount=GlobalStatic.FRIEWALL_LOCK_CHECK_COUNT;
	//同一IP阀值时间,单位是 秒
	private Integer firewallLockCheckSecond=GlobalStatic.FRIEWALL_LOCK_CHECK_SECOND;
	
	//锁定秒数
	private Integer firewallLockedSecond=GlobalStatic.FRIEWALL_LOCKED_SECOND;
	
	
	//白名单
	private List<String> whiteList=new ArrayList<>();
	
	//黑名单
	private List<String> blackList=new ArrayList<>();
	

	@Override
	protected void doFilterInternal(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {

		
	    HttpServletRequest request=(HttpServletRequest) req;
	    //获取IP地址
	    String ip=IPUtils.getClientAddress(request);
	    //黑名单IP
	    if(blackList.contains(ip)){
	    	return;
	    }
	    
	    //设置编码
	   if( request.getCharacterEncoding() == null){
		   request.setCharacterEncoding(GlobalStatic.defaultCharset);
	   }
	    
	    
	    
	    //次数小于0,认为不限制
	    if(firewallLockCheckCount<0){
	        chain.doFilter(req, res);
	    	return ;
	    	
	    }
	    
	    //白名单IP
	    if(whiteList.contains(ip)){
	    	chain.doFilter(req, res);
	    	return;
	    }
	    
	    
	    Cache cache = cacheManager.getCache(GlobalStatic.springrainfirewallCacheKey);
	    //访问记录
	    String fw=cache.get(ip,String.class);
	    
	    //当前时间
	    Long now=System.currentTimeMillis()/1000;
	    Long _end=now+firewallLockCheckSecond;
	    if(fw==null){//第一次访问
	    	cache.put(ip, 1+"_"+_end+"_0");
	    	chain.doFilter(req, res);
	    	return ;
	    }
	    String[] strs=fw.split("_");
	    if(strs==null||strs.length!=3){
	    	cache.put(ip, 1+"_"+_end+"_0");
	    	chain.doFilter(req, res);
	    	return;
	    }
	    //已请求次数
	    Integer _count=Integer.valueOf(strs[0]);
	    //阀值判断时间
	    Long endDateLong=Long.valueOf(strs[1]);
	    //是否在锁定期
	    Integer active=Integer.valueOf(strs[2]);
	    _count=_count+1;
	    if(_count<=firewallLockCheckCount){//不到阀值
	    	cache.put(ip, _count+"_"+endDateLong+"_"+active);
	    	chain.doFilter(req, res);
	    	return;
	    	
	    }
	    
	  //访问超过阀值
	    
	    if(active==0){//未进入锁定
	    	endDateLong=endDateLong+firewallLockedSecond*60;
	    	active=1;
	    }
	    
	    
	    if(now>endDateLong){//已经过期
	    	cache.put(ip, 1+"_"+_end+"_0");
	    	chain.doFilter(req, res);
	    	return;
	    }
	    
	    
	    cache.put(ip, _count+"_"+endDateLong+"_"+active);
    	return;
	

	}


	public List<String> getWhiteList() {
		return whiteList;
	}



	public void setWhiteList(List<String> whiteList) {
		this.whiteList = whiteList;
	}



	public List<String> getBlackList() {
		return blackList;
	}



	public void setBlackList(List<String> blackList) {
		this.blackList = blackList;
	}


    public Integer getFirewallLockCheckCount() {
        return firewallLockCheckCount;
    }


    public void setFirewallLockCheckCount(Integer firewallLockCheckCount) {
        this.firewallLockCheckCount = firewallLockCheckCount;
    }


    public Integer getFirewallLockCheckSecond() {
        return firewallLockCheckSecond;
    }


    public void setFirewallLockCheckSecond(Integer firewallLockCheckSecond) {
        this.firewallLockCheckSecond = firewallLockCheckSecond;
    }


    public Integer getFirewallLockedSecond() {
        return firewallLockedSecond;
    }


    public void setFirewallLockedSecond(Integer firewallLockedSecond) {
        this.firewallLockedSecond = firewallLockedSecond;
    }



	





	
}
