package com.zhaodui.springboot.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaodui.springboot.common.mdoel.Page;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wujun
 */
public abstract class AbstractService<T>  {
	@Autowired(required = false)
	private Mapper<T> mapper;

	/**
	 * 查询所有
	 *
	 * @return 所有数据
	 */
	public List<T> getAll() {
		return mapper.selectAll();
	}

	/**
	 * 分页查询
	 *
	 * @param t        要查询的对象
	 * @param pageNum  第几页
	 * @param pageSize 每页几条
	 * @return 分页查询结果
	 */
	public Page<T> getPage(T t, Integer pageNum, Integer pageSize) {
		pageNum = pageNum != null ? pageNum : 0;
		pageSize = pageSize != null ? pageSize : 0;
		// 开始分页
		PageHelper.startPage(pageNum, pageSize);
		// 进行查询
		List<T> list = getByParam(t);
		// 分页信息
		PageInfo<T> pageInfo = new PageInfo<>(list);
		// 返回page对象
		return new Page<T>(pageInfo.getList(), pageInfo.getTotal());
	}

	/**
	 * 按条件查询列表（模糊查询需要重写该方法）
	 *
	 * @param param 条件
	 * @return 查询结果
	 */
	public List<T> getByParam(T param) {
		return mapper.select(param);
	}

	/**
	 * 按id查询
	 *
	 * @param id 要查询的id
	 * @return 查询结果
	 */
	public T getById(Serializable id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 增加
	 *
	 * @param t 要增加的对象
	 */
	public void add(T t) {
		mapper.insert(t);
	}

	/**
	 * 根据id删除
	 *
	 * @param id 要删除的id
	 */
	public void deleteById(Serializable id) {
		mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 修改
	 *
	 * @param t 要修改的对象
	 */
	public void update(T t) {
		mapper.updateByPrimaryKeySelective(t);
	}
}