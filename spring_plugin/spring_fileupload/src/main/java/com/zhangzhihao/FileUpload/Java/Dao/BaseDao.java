package com.zhangzhihao.FileUpload.Java.Dao;


import com.zhangzhihao.FileUpload.Java.Utils.PageResults;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;


/**
 * 只需要一个BaseDao就可以，需要访问数据库的地方，比如BaseService<T>只需要private BaseDao<T> baseDao即可
 *
 * @param <T> 实体类型
 */
@SuppressWarnings({"unchecked"})
@Transactional(timeout = 5)
@Repository
@Primary
public class BaseDao<T> {

	//获取到和当前事务关联的 EntityManager 对象
	//实际上是获得EntityManager的代理对象，是线程安全的
	@PersistenceContext
	private EntityManager entityManager;


	/**
	 * 这个实体是否存在在数据库
	 *
	 * @param model 实体
	 * @return 是否存在
	 */
	public boolean contains(@NotNull final T model) {
		return entityManager.contains(model);
	}

	/**
	 * 使实体变为不受管理的状态
	 *
	 * @param model 实体
	 */
	public void detach(@NotNull final T model) {
		entityManager.detach(model);
	}

	/**
	 * 保存对象
	 *
	 * @param model 需要添加的对象
	 */
	public void save(@NotNull final T model) {
		entityManager.persist(model);
	}

	/**
	 * 批量保存对象
	 *
	 * @param modelList 需要增加的对象的集合
	 *                  失败会抛异常
	 */
	public void saveAll(@NotNull final List<T> modelList) {
		modelList.stream().forEach(entityManager::persist);
	}

	/**
	 * 删除对象
	 *
	 * @param model 需要删除的对象
	 *              失败会抛异常
	 */
	public void delete(@NotNull final T model) {
		entityManager.remove(entityManager.contains(model) ? model : entityManager.merge(model));
	}

	/**
	 * 批量删除对象
	 *
	 * @param modelList 需要删除的对象的集合
	 *                  失败会抛异常
	 */
	public void deleteAll(@NotNull final List<T> modelList) {
		modelList.stream().forEach(this::delete);
	}

	/**
	 * 按照id删除对象
	 *
	 * @param modelClass 类型，比如User.class
	 * @param id         需要删除的对象的id
	 *                   失败抛出异常
	 */
	public void deleteById(final Class<T> modelClass, @NotNull final Serializable id) {
		this.delete(this.getById(modelClass, id));
	}

	/**
	 * 更新或保存对象
	 *
	 * @param model 需要更新的对象
	 *              失败会抛出异常
	 */
	public void saveOrUpdate(@NotNull final T model) {
		entityManager.merge(model);
	}

	/**
	 * 批量更新或保存对象
	 *
	 * @param modelList 需要更新或保存的对象
	 *                  失败会抛出异常
	 */
	public void saveOrUpdateAll(@NotNull final List<T> modelList) {
		modelList.stream().forEach(entityManager::merge);
	}

	/**
	 * 通过主键, 查询对象
	 *
	 * @param modelClass 类型，比如User.class
	 * @param id         主键(Serializable)
	 * @return model
	 */
	@Transactional(readOnly = true)
	public T getById(Class<T> modelClass, @NotNull final Serializable id) {
		return entityManager.find(modelClass, id);
	}

	/**
	 * 获得全部
	 *
	 * @param modelClass 类型，比如User.class
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<T> getAll(Class<T> modelClass) {
		Query query = new Query(modelClass, entityManager);
		return entityManager.createQuery(query.createCriteriaQuery()).getResultList();
	}


	/**
	 * 分页查询
	 *
	 * @param modelClass        类型，比如User.class
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public List<T> getListByPage(Class<T> modelClass,
	                             @NotNull final Integer currentPageNumber,
	                             @NotNull final Integer pageSize) {
		if (currentPageNumber <= 0 || pageSize <= 0) {
			return null;
		}
		Query query = new Query(modelClass, entityManager);
		return entityManager.createQuery(query.createCriteriaQuery())
				.setFirstResult((currentPageNumber - 1) * pageSize)
				.setMaxResults(pageSize)
				.getResultList();
	}

	/**
	 * 按条件分页
	 *
	 * @param currentPageNumber 页码
	 * @param pageSize          每页数量
	 * @param query             封装的查询条件
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public PageResults<T> getListByPageAndQuery(@NotNull Integer currentPageNumber,
	                                            @NotNull Integer pageSize,
	                                            @NotNull Query query)
			throws Exception {
		//获得符合条件的总数目
		int totalCount = getCountByQuery((Query) query.deepClone());
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;

		if (currentPageNumber > pageCount && pageCount != 0) {
			currentPageNumber = pageCount;
		}
		TypedQuery typedQuery = query.createTypedQuery();
		//查看是否要分页
		if (currentPageNumber > 0 && pageSize > 0) {
			typedQuery
					.setFirstResult((currentPageNumber - 1) * pageSize)
					.setMaxResults(pageSize);
		}
		List<T> list = typedQuery.getResultList();
		return new PageResults<>(currentPageNumber + 1, currentPageNumber, pageSize, totalCount, pageCount, list);
	}

	/**
	 * 获得数量 利用Count(*)实现
	 *
	 * @param modelClass 类型，比如User.class
	 * @return 数量
	 */
	@Transactional(readOnly = true)
	public int getCount(Class<T> modelClass) {
		Query query = new Query(modelClass, entityManager);
		return getCountByQuery(query);
	}

	/**
	 * 获得符合对应条件的数量 利用Count(*)实现
	 *
	 * @param query 查询条件
	 * @return 数量
	 */
	@Transactional(readOnly = true)
	public int getCountByQuery(@NotNull final Query query) {
		query.selectCount();
		return Integer.parseInt(
				query.createTypedQuery()
						.getSingleResult()
						.toString()
		);
	}

	/**
	 * 执行Sql语句
	 *
	 * @param sql    sql
	 * @param values 不定参数数组
	 * @return 受影响的行数
	 */
	public int executeSql(@NotNull String sql, @NotNull final Object... values) {
		javax.persistence.Query nativeQuery = entityManager.createNativeQuery(sql);
		for (int i = 0; i < values.length; i++) {
			nativeQuery.setParameter(i, values[i]);
		}
		return nativeQuery.executeUpdate();
	}

	/**
	 * 通过jpql查询
	 *
	 * @param jpql
	 * @param values
	 * @return
	 */
	@Transactional(readOnly = true)
	public Object queryByJpql(@NotNull final String jpql, @NotNull final Object... values) {
		javax.persistence.Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.getResultList();
	}

	/**
	 * 获得符合对应条件的数量 利用Count(*)实现
	 *
	 * @param jpql jpql查询条件
	 * @return 数量
	 */
	@Transactional(readOnly = true)
	public int getCountByJpql(@NotNull final String jpql, @NotNull final Object... values) {
		javax.persistence.Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.getResultList().size();
	}

	/**
	 * 通过Jpql分页查询
	 *
	 * @param currentPageNumber 当前页
	 * @param pageSize          每页数量
	 * @param jpql              jpql语句
	 * @param values            jpql参数
	 * @return 查询结果
	 */
	@Transactional(readOnly = true)
	public PageResults<Object> getListByPageAndJpql(@NotNull Integer currentPageNumber,
	                                                @NotNull Integer pageSize,
	                                                @NotNull final String jpql,
	                                                @NotNull final Object... values) {
		//参数验证
		int totalCount = getCountByJpql(jpql, values);
		int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;

		if (currentPageNumber > pageCount && pageCount != 0) {
			currentPageNumber = pageCount;
		}

		javax.persistence.Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		//查看是否要分页
		if (currentPageNumber > 0 && pageSize > 0) {
			query
					.setFirstResult((currentPageNumber - 1) * pageSize)
					.setMaxResults(pageSize);
		}
		List<Object> list = query.getResultList();
		return new PageResults<>(currentPageNumber + 1, currentPageNumber, pageSize, totalCount, pageCount, list);
	}

	/**
	 * 执行jpql语句
	 *
	 * @param jpql
	 * @param values
	 * @return
	 */
	public int executeJpql(@NotNull final String jpql, @NotNull final Object... values) {
		javax.persistence.Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * refresh 刷新实体状态
	 *
	 * @param model 实体
	 */
	public void refresh(@NotNull T model) {
		entityManager.refresh(model);
	}


}

