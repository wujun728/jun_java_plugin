package com.baijob.commonTools.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.Span;
import com.baijob.commonTools.XmlUtil;

/**
 * 访问控制对象
 * @author Luxiaolei
 *
 */
public class AccessControl{
	private static Logger logger = LoggerFactory.getLogger(AccessControl.class);
	
	/*--------------------------常量 start-------------------------------*/
	/** 访问控制信息文件路径 */
	public final static String ACCESS_PATH = "config/access.xml";
	
	/** 白名单模式 */
	public final static String TYPE_WHITE = "white";
	/** 黑名单模式 */
	public final static String TYPE_BLACK = "black";
	/*--------------------------常量 end-------------------------------*/
	
	/** 控制访问模式 */
	private static String type;
	/** IP区间名单列表 */
	private static List<Span> ipSections = new ArrayList<Span>();
	
	/** 用户组 */
	private static Set<String> groups = new HashSet<String>();
	/** 用户列表 */
	private static Map<String, Connector> users = new HashMap<String, Connector>();
	
	public AccessControl() {
		this.init(ACCESS_PATH);
	}
	
	public AccessControl(String pathBaseClassLoader) {
		this.init(pathBaseClassLoader);
	}
	
	/**
	 * 初始化访问控制
	 */
	@SuppressWarnings("unchecked")
	public void init(String pathBaseClassLoader){
		Document doc = null;
		try {
			doc =  XmlUtil.readDoc(ACCESS_PATH);
		} catch (DocumentException e) {
			logger.error("解析控制配置文件失败！", e);
			return;
		}
		Element access = doc.getRootElement();
		
		//初始化用户组
		Element auth = access.element("auth");
		List<Element> groupList = auth.elements("group");
		for (Element group : groupList) {
			groups.add(group.attributeValue("name"));
		}
		//初始化用户列表
		List<Element> userList = auth.elements("user");
		for (Element user : userList) {
			String name =user.attributeValue("name");
			String pass =user.attributeValue("pass");
			String group =user.attributeValue("group");
			if(groups.contains(group)) {
				//用户必须在组中
				users.put(buildKey(name, pass, group), new Connector(name, pass, group));
			}else {
				logger.error("【Access】Bad group {}", group);
				continue;
			}
		}
		
		//初始化黑名单（白名单）
		Element list = access.element("list");
		type = list.attributeValue("type").trim();
		Iterator<Element> iterator = list.elementIterator("section");
		while(iterator.hasNext()){
			String[] ipSection = iterator.next().getText().trim().split("-");
			if(ipSection.length == 1){
				long ipLong = SocketUtil.ipv4ToLong(ipSection[0]);
				if(ipLong != 0){
					ipSections.add(new Span(ipLong, ipLong));
				}
			}else if(ipSection.length == 2){
				long ipLong1 = SocketUtil.ipv4ToLong(ipSection[0]);
				long ipLong2 = SocketUtil.ipv4ToLong(ipSection[1]);
				if(ipLong1 != 0 && ipLong2 != 0 && ipLong1 <= ipLong2){
					ipSections.add(new Span(ipLong1, ipLong2));
				}
			}
		}
	}
	
	/**
	 * 指定用户是否通过验证
	 * @param username 用户名
	 * @param pass 密码
	 * @param group 用户组
	 * @return 是否通过验证
	 */
	public boolean isAuthorized(String username, String pass, String group){
		Connector connector = users.get(buildKey(username, pass, group));
		if(connector != null) {
			if(connector.getUser().equals(username) && connector.getPassword().equals(pass) && connector.getGroup().equals(group)) {
				return true;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 是否允许指定的请求访问
	 * @param request 请求对象
	 * @return 是否允许访问
	 */
	public boolean isPermit(HttpServletRequest request){
		return isPermit(HttpUtil.getClientIP(request));
	}
	
	/**
	 * 是否允许给定的IP访问
	 * @param ip IP地址
	 * @return 是否允许
	 */
	public boolean isPermit(String ip){
		if(TYPE_WHITE.equals(type)){
			return isInSection(ip);
		}else if(TYPE_BLACK.equals(type)){
			return ! isInSection(ip);
		}
		logger.error("访问控制类型 " + type + " 错误！");
		return false;
	}
	
	/**
	 * 是否是允许IP段内的指定用户
	 * @param username 用户名
	 * @param pass 密码
	 * @param group 用户组
	 * @param ip IP段
	 * @return 是否允许
	 */
	public boolean isAuthorizedWithIP(String username, String pass, String group, String ip) {
		return isPermit(ip) && isAuthorized(username, pass, group);
	}
	
	/**
	 * IP是否位于名单中
	 * @param ip IP地址
	 * @return 是否在名单中
	 */
	private boolean isInSection(String ip){
		long ipLong = SocketUtil.ipv4ToLong(ip);
		if(ipLong != 0){
			for (Span ipSection : ipSections) {
				if(ipSection.isInBetween(ipLong)) return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据用户名、密码和组构建一个key，用于查找用户
	 * @param name 用户名
	 * @param pass 密码
	 * @param group 组
	 * @return KEY
	 */
	private String buildKey(String name, String pass, String group) {
		return name + "#" + pass + "#" + group;
	}
}
