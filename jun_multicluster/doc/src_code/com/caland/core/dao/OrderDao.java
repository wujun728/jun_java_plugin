package com.caland.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.caland.common.page.Pagination;
import com.caland.core.bean.Order;
import com.caland.core.query.OrderQuery;

@Repository
public class OrderDao {

	@Resource
	SqlMapClientTemplate sqlMapClientTemplate;

	public Integer addOrder(Order order) throws SQLException {
		return (Integer) this.sqlMapClientTemplate.insert("Order.insertOrder", order);
	}

	/**
	 * 根据主键查找
	 * 
	 * @throws SQLException
	 */
	public Order getOrderByKey(Integer id) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Order result = (Order) this.sqlMapClientTemplate.queryForObject(
				"Order.getOrderByKey", params);
		return result;
	}

	/**
	 * 根据主键批量查找
	 * 
	 * @throws SQLException
	 */
	public List<Order> getOrderByKeys(List<Integer> idList) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", idList);
		return (List<Order>) this.sqlMapClientTemplate.queryForList(
				"Order.getOrdersByKeys", params);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer deleteByKey(Integer id) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Integer row = (Integer) this.sqlMapClientTemplate.delete(
				"Order.deleteByKey", params);
		return row;
	}

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer deleteByKeys(List<Integer> idList) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keys", idList);
		Integer row = (Integer) this.sqlMapClientTemplate.delete(
				"Order.deleteByKeys", params);
		return row;
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer updateOrderByKey(Order order) throws SQLException {
		return (Integer) this.sqlMapClientTemplate.update(
				"Order.updateOrderByKey", order);
	}

	@SuppressWarnings("unchecked")
	public Pagination getOrderListWithPage(OrderQuery orderQuery) {
		Pagination p = null;
		try {
			p = new Pagination(orderQuery.getPage(), orderQuery.getPageSize(), (Integer) this.sqlMapClientTemplate.queryForObject(
					"Order.getOrderListCount", orderQuery));
			if (orderQuery.getFields() != null && orderQuery.getFields() != "") {
				p.setList((List<Order>) this.sqlMapClientTemplate.queryForList(
						"Order.getOrderListWithPageFields", orderQuery));
			} else {
				p.setList((List<Order>) this.sqlMapClientTemplate.queryForList(
						"Order.getOrderListWithPage", orderQuery));
			}
		} catch (Exception e) {

		}
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrderList(OrderQuery orderQuery) throws SQLException {
		if (orderQuery.getFields() != null && orderQuery.getFields() != "") {
			return (List<Order>) this.sqlMapClientTemplate.queryForList(
					"Order.getOrderListFields", orderQuery);
		}
		return (List<Order>) this.sqlMapClientTemplate.queryForList(
				"Order.getOrderList", orderQuery);
	}
}
