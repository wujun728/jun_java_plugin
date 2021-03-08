package cn.yunhe.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.yunhe.dao.IUserDao;
import cn.yunhe.model.User;
import cn.yunhe.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserDao userDao;

	public User selectByPrimaryKey(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	public List<User> selectAll() {
		return userDao.selectAll();
	}

	public int addUser(User user) {
		return userDao.addUser(user);
	}

	public int delUser(User user) {
		return userDao.delUser(user);
	}

	public Integer selectByNameAndPass(User user) {
		return userDao.selectByNameAndPass(user);
	}

	public Page queryUsersByPage(int currPage, int pageSize) {
		Page page = PageHelper.startPage(currPage, pageSize);
		List<User> list = userDao.selectAll();
		return page;
	}

	public User getUser(User user) {
		return userDao.getUser(user);
	}

	public void editUser(User user) {
		userDao.editUser(user);
	}

	public Page queryLikeUsers(Map<String, Object> cond) {
		Page page = new Page();
		// 根据条件查询符合的用户列表记录总数，赋值给page的totalNum变量
		page.setTotal(userDao.getLikeUsersCount(cond));
		// 从请求参数中获取每页大小
		int pageSize = Integer.parseInt(String.valueOf(cond.get("pageSize")));
		page.setPageSize(pageSize);
		// 从请求参数中获取当前页码
		int curPageNum = Integer.parseInt(String.valueOf(cond.get("pageNum")));
		page.setPageNum(curPageNum);		
		//动态计算总页数(总记录数 除以 每页大小，加上  总页数 求余 每页大小，如果余数不为0，则 加 1，否则 加 0 )
		page.setPageNum((int)(page.getTotal()/pageSize+(page.getTotal()%pageSize==0?0:1)));
		//根据条件查询符合的用户列表记录，赋值给page的result变量
		page = PageHelper.startPage(curPageNum, pageSize);
		List list=userDao.getLikeUsers(cond);
			
		return page;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
