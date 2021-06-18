package com.itheima.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.util.Dom4JUtil;

public class UserDaoXmlImpl implements UserDao {

	public User findByUsername(String username) {
		try {
			Document doc = Dom4JUtil.getDocument();
//			List<Node> userNodes = doc.selectNodes("//user");
			Node node = doc.selectSingleNode("//user[@username='"+username+"']");
			if(node==null)
				return null;
			
			String xmlBirthday = node.valueOf("@birthday");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date birthday = df.parse(xmlBirthday);
			
			User user = new User(node.valueOf("@username"), node.valueOf("@password"), node.valueOf("@email"), birthday);
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	//<user username="abc" password="123" email="wzt@itcast.cn" birthday="1980-10-01"/>
	public void save(User user) {
		try {
			Document doc = Dom4JUtil.getDocument();
			Element root = doc.getRootElement();
			root.addElement("user")
				.addAttribute("username", user.getUsername())
				.addAttribute("password", user.getPassword())
				.addAttribute("email", user.getEmail())
				.addAttribute("birthday", user.getBirthday().toLocaleString());
			Dom4JUtil.write2xml(doc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findUser(String username, String password) {
		try {
			Document doc = Dom4JUtil.getDocument();
//			List<Node> userNodes = doc.selectNodes("//user");
			Node node = doc.selectSingleNode("//user[@username='"+username+"' and @password='"+password+"']");
			if(node==null)
				return null;
			
			String xmlBirthday = node.valueOf("@birthday");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date birthday = df.parse(xmlBirthday);
			
			User user = new User(node.valueOf("@username"), node.valueOf("@password"), node.valueOf("@email"), birthday);
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
