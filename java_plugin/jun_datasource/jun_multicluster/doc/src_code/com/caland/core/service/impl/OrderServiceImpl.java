package com.caland.core.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caland.common.page.Pagination;
import com.caland.core.bean.Order;
import com.caland.core.dao.OrderDao;
import com.caland.core.query.OrderQuery;
import com.caland.core.service.OrderService;
/**
 * 
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);

	@Resource
	OrderDao orderDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addOrder(Order order) {
		try {
			return orderDao.addOrder(order);
		} catch (SQLException e) {
			log.error("dao addOrder error.:" + e.getMessage(), e);
		}
		return 0;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Order getOrderByKey(Integer id) {
		try {
			return orderDao.getOrderByKey(id);
		} catch (SQLException e) {
			log.error("dao getOrderbyKey error.:" + e.getMessage(), e);
		}
		return null;
	}
	@Transactional(readOnly = true)
	public List<Order> getOrderByKeys(List<Integer> idList) {
		try {
			return orderDao.getOrderByKeys(idList);
		} catch (SQLException e) {
			log.error("dao getOrdersByKeys erorr." + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		try {
			return orderDao.deleteByKey(id);
		} catch (SQLException e) {
			log.error("dao deleteByKey error. :" + e.getMessage(), e);
		}
		return -1;
	}

	public Integer deleteByKeys(List<Integer> idList) {
		try {
			return orderDao.deleteByKeys(idList);
		} catch (SQLException e) {
			log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order Order) {
		try {
			return orderDao.updateOrderByKey(Order);
		} catch (SQLException e) {
			log.error("dao updateOrder error.Order:" + e.getMessage(), e);
		}
		return -1;
	}
	@Transactional(readOnly = true)
	public Pagination getOrderListWithPage(OrderQuery OrderQuery) {
		try {
			return orderDao.getOrderListWithPage(OrderQuery);
		} catch (Exception e) {
			log.error("get Order pagination error." + e.getMessage(), e);
		}

		return new Pagination();
	}
	@Transactional(readOnly = true)
	public List<Order> getOrderList(OrderQuery OrderQuery) {
		try {
			return orderDao.getOrderList(OrderQuery);
		} catch (SQLException e) {
			log.error("get Order list error." + e.getMessage(), e);
		}
		return Collections.emptyList();
	}
}
