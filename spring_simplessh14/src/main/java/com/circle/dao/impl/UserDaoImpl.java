package com.circle.dao.impl;

import java.util.List;

import com.circle.dao.IUserDao;
import com.circle.entity.User;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

//@Repository
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {

	//查询用户条数
	public int getUsersCount() {
		//调用Hibernate保存
		Number n = (Number) this.currentSession().createQuery("select count(u) from User u").uniqueResult();
		return n.intValue();
	}
	//查询所有的用户
	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		//使用Template查询
		return (List<User>) this.getHibernateTemplate().find("select u from User u order by id");
	}
	//根据名字查询Usur
	@SuppressWarnings("unchecked")
	public User findUserByName(String name) {
		//使用Template
		List<User> userList = (List<User>)this.getHibernateTemplate().find("select u from User u where u.name= ? ",name);
		if(userList.size()>0)		//如果有数据
			return userList.get(0);	//返回第一条
		return null;
	}
	//注册
	public void register(User user) {
		//使用Hibernate保存
		this.getHibernateTemplate().save(user);

	}
	//登录
	public int login(String name, String password) {
		Query query = currentSession().createQuery("select count(u.id) from User u where u.name = ? and u.password = ?");
		query.setParameter(0, name);
		query.setParameter(1, password);
		return Integer.parseInt(query.uniqueResult().toString());
	}
	//删除
	public void delete(User user) {
		//使用Hibernate删除
		this.getHibernateTemplate().delete(user);

	}
	//修改
	public void update(User user) {
		//使用Hibernate修改
		this.getHibernateTemplate().update(user);

	}
	//添加
	public void add(User user) {
		//使用Hibernate添加
		this.getHibernateTemplate().save(user);

	}
}

