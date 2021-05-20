package org.springjdbc.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springjdbc.dao.ForumDao;
import org.springjdbc.dao.PostDao;
import org.springjdbc.domain.Forum;

import com.mysql.jdbc.Connection;

public final class Boot {

	  private static ApplicationContext applicationContext;
	  private static final String CONFIGPATH= "applicationContext.xml";

	  public static void main(final String[] args) throws Exception {
		  applicationContext = new ClassPathXmlApplicationContext(CONFIGPATH);
		  ForumDao forumDao = (ForumDao) applicationContext.getBean("forumDao");
//		  List<Forum> forums = new ArrayList();
//		  for (int i = 0; i < 10 ; i++) {
//			  Forum forum = new Forum();
//			  forum.setForumName("houjinxin"+i);
//			  forum.setForumDesc("desc"+i);
//			  forums.add(forum);	
//		  }
//		  forumDao.addForums(forums);
//		  System.out.println(forumDao.getForum(1));
		  
//		  for (Forum forum : forumDao.getForums(1, 10,true)) {
//			System.out.println(forum);
//		 }
//		  System.out.println(forumDao.getForumNum());
//		  System.out.println(forumDao.getReplayRate(2));
//		  System.out.println(forumDao.getUserTopicNum(1));
//		  System.out.println(forumDao.getUserTopicNum2(1));
		  PostDao postDao = (PostDao) applicationContext.getBean("postDao");
		  Connection connection =  (Connection) postDao.getNativeConn();
		  System.out.println(connection.getMetaData().getDatabaseProductName()+" "+connection.getMetaData().getDatabaseProductVersion());
	  }
	}