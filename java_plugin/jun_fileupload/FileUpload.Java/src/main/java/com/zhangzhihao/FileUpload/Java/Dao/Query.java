package com.zhangzhihao.FileUpload.Java.Dao;


import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Query
 * 封装JPA CriteriaBuilder查询条件
 */
@SuppressWarnings({"unused", "unchecked", "rawtypes", "null", "hiding"})
public class Query implements Serializable {

	private static final long serialVersionUID = 3366932251068926942L;

	private EntityManager entityManager;

	/**
	 * 要查询的实体类型
	 */
	private Class clazz;

	/**
	 * 查询根，定义查询的From子句中能出现的类型。
	 * Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似。
	 * Root实例也是类型化的，且定义了查询的FROM子句中能够出现的类型。
	 * 查询根实例能通过传入一个实体类型给 AbstractQuery.from方法获得。
	 * Criteria查询，可以有多个查询根。
	 */
	private From from;

	/**
	 * 谓词，也就是过滤条件，用CriteriaBuilder生成。
	 * 过滤条件应用到SQL语句的FROM子句中，
	 * 在Criteria 查询中，查询条件通过Predicate 或Expression 实例应用到CriteriaQuery 对象上。
	 */
	private List<Predicate> predicates;

	/**
	 * 安全查询主语句
	 */
	private CriteriaQuery criteriaQuery;

	/**
	 * 安全查询创建工厂。
	 * CriteriaBuilder是一个工厂对象，安全查询的开始，
	 * 用于构建JPA安全查询，创建CriteriaQuery，创建查询具体具体条件Predicate 等。
	 */
	private CriteriaBuilder criteriaBuilder;

	/**
	 * select条件，用于处理平均值之类的查询
	 */
	private Selection selection;

	/**
	 * 排序方式列表
	 */
	private List<Order> orders;

	/**
	 * 查询参数
	 */
	private Map<ParameterExpression, Object> parameters;

	/**
	 * 子查询
	 */
	private Map<String, Query> subQuery;

	/**
	 * 关联查询
	 */
	private Map<String, Query> linkQuery;

	/**
	 * 子查询字段
	 */
	private String projection;

	/**
	 * 或条件
	 */
	private List<Query> orQuery;

	/**
	 * 分组条件
	 */
	private String groupBy;

	private Query() {
	}

	/**
	 * 创建查询条件
	 *
	 * @param clazz         要查询的类型
	 * @param entityManager EntityManager
	 */
	public Query(@NotNull Class clazz, @NotNull EntityManager entityManager) {
		this.clazz = clazz;
		this.entityManager = entityManager;
		this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
		this.criteriaQuery = criteriaBuilder.createQuery(this.clazz);
		this.from = criteriaQuery.from(this.clazz);
		this.predicates = new ArrayList();
		this.orders = new ArrayList();
	}

	/**
	 * 构造函数
	 *
	 * @param entityManager EntityManager
	 */
	public Query(@NotNull EntityManager entityManager) {
		this.entityManager = entityManager;
		this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
		this.predicates = new ArrayList();
		this.orders = new ArrayList();
	}

	/**
	 * FluentAPI 式构造函数
	 *
	 * @param clazz 要查询的类型
	 * @return 返回Query对象
	 */
	public Query from(@NotNull Class clazz) {
		this.clazz = clazz;
		this.criteriaQuery = criteriaBuilder.createQuery(this.clazz);
		this.from = criteriaQuery.from(this.clazz);
		return this;
	}

	/**
	 * 创建强类型查询,并且自动注入参数！！！
	 * 如果使用参数化自动注入的方法，必须调用此方法创建强类型查询
	 *
	 * @return 返回强类型查询的实例
	 */
	public TypedQuery createTypedQuery() {
		TypedQuery typedQuery = entityManager.createQuery(this.createCriteriaQuery());
		if (parameters != null) {
			for (ParameterExpression parameter : parameters.keySet()) {
				typedQuery.setParameter(parameter, parameters.get(parameter));
			}
		}
		return typedQuery;
	}

	/**
	 * 将ParameterExpression和对应的值添加进一个map里，最后在createTypedQuery时放入TypedQuery里面，完成参数化查询
	 *
	 * @param parameter ParameterExpression参数
	 * @param object    参数值
	 */
	private void setParameter(@NotNull final ParameterExpression parameter, @NotNull final Object object) {
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		parameters.put(parameter, object);
	}

	/**
	 * 自动创建ParameterExpression参数
	 *
	 * @param value 参数值
	 * @return 返回ParameterExpression参数
	 */
	private ParameterExpression makeParameter(@NotNull final Object value) {
		Class<?> aClass = value.getClass();
		ParameterExpression<Enum> parameter = this.createParameter(aClass);
		setParameter(parameter, value);
		return parameter;
	}


	/**
	 * 创建查询条件
	 *
	 * @return JPA标准查询
	 */
	public CriteriaQuery createCriteriaQuery() {
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		if (!isNullOrEmpty(groupBy)) {
			criteriaQuery.groupBy(from.get(groupBy));
		}
		if (orders != null) {
			criteriaQuery.orderBy(orders);
		}
		if (selection != null) {
			criteriaQuery.select(selection);
		}
		addLinkCondition(this);
		return criteriaQuery;
	}

	/**
	 * 参数化查询
	 */
	public ParameterExpression createParameter(@NotNull Class clazz) {
		return this.getCriteriaBuilder().parameter(clazz);
	}

	/**
	 * 添加连接查询,在最后创建CriteriaQuery的时候调用
	 *
	 * @param query query对象
	 */
	private void addLinkCondition(@NotNull Query query) {
		Map linkQuery = query.linkQuery;
		if (linkQuery == null)
			return;
		for (Object o : linkQuery.keySet()) {
			String key = o.toString();
			Query sub = (Query) linkQuery.get(key);
			from.join(key);
			criteriaQuery.where(sub.predicates.toArray(new Predicate[0]));
			addLinkCondition(sub);
		}
	}

	/**
	 * 增关联查询
	 */
	public void addLinkQuery(@NotNull final String propertyName, @NotNull Query query) {
		if (this.linkQuery == null)
			this.linkQuery = new HashMap();

		this.linkQuery.put(propertyName, query);
	}

	/**
	 * 增加子查询,必须设置子查询字段 projection
	 */
	private void addSubQuery(@NotNull final String propertyName, @NotNull Query query) {
		if (this.subQuery == null)
			this.subQuery = new HashMap();

		if (query.projection == null)
			throw new RuntimeException("子查询字段未设置");

		this.subQuery.put(propertyName, query);
	}

	/**
	 * 直接添加JPA内部的查询条件,
	 * 用于应付一些复杂查询的情况,例如 "或"
	 */
	public Query addCriterions(@NotNull Predicate predicate) {
		this.predicates.add(predicate);
		return this;
	}


	/**
	 * 增加升序还是降序排序，可以同时包含多个排序规则
	 *
	 * @param propertyName 排序的属性名
	 * @param order        排序方式 asc或desc
	 * @return 返回query实例
	 */
	public Query addOrder(@NotNull final String propertyName, @NotNull final String order) {
		if (this.orders == null)
			this.orders = new ArrayList();

		if (order.equalsIgnoreCase("asc"))
			this.orders.add(criteriaBuilder.asc(from.get(propertyName)));
		else if (order.equalsIgnoreCase("desc"))
			this.orders.add(criteriaBuilder.desc(from.get(propertyName)));
		return this;
	}

	/**
	 * 设置升序还是降序排序，只能有一个排序规则
	 *
	 * @param propertyName 排序的属性名
	 * @param order        排序方式 asc或desc
	 * @return 返回query实例
	 */
	public Query setOrder(@NotNull final String propertyName, @NotNull final String order) {
		this.orders = null;
		addOrder(propertyName, order);
		return this;
	}


	/**
	 * 设置查询，此方法为高级用法，用于应付比较复杂的查询规则
	 * 比如 Or Some Any
	 *
	 * @param selection 查询规则
	 * @return query实例
	 */
	public Query setSelect(Selection selection) {
		this.selection = selection;
		return this;
	}

	/**
	 * 此方法只是为了读起来更有语义感，实际上不做任何工作，可以不用
	 *
	 * @return query实例
	 */
	public Query select() {
		return this;
	}

	/**
	 * 查询总数
	 *
	 * @return query实例
	 */
	public Query selectCount() {
		Expression<Long> count = criteriaBuilder.count(from);
		setSelect(count);
		return this;
	}

	/**
	 * 查询总数并去重
	 *
	 * @return query实例
	 */
	public Query selectCountDistinct() {
		Expression<Long> count = criteriaBuilder.countDistinct(from);
		setSelect(count);
		return this;
	}

	/**
	 * 查询特定属性的平均值
	 *
	 * @param propertyName 要计算平均值的属性名
	 * @return query实例
	 */
	public Query selectAvg(@NotNull final String propertyName) {
		Expression<Double> avg = criteriaBuilder.avg(from.get(propertyName));
		setSelect(avg);
		return this;
	}

	/**
	 * 查询特定属性的值之和
	 *
	 * @param propertyName 要计算和的属性名
	 * @return query实例
	 */
	public Query selectSum(@NotNull final String propertyName) {
		setSelect(criteriaBuilder.sum(from.get(propertyName)));
		return this;
	}

	/**
	 * 查询特定属性值的和，并设置查询结果类型为Long
	 *
	 * @param propertyName 要计算和的属性名
	 * @return query实例
	 */
	public Query selectSumAsLong(@NotNull final String propertyName) {
		setSelect(criteriaBuilder.sumAsLong(from.get(propertyName)));
		return this;
	}

	/**
	 * 查询特定属性值的和，并设置查询结果类型为Double
	 *
	 * @param propertyName 要计算和的属性名
	 * @return query实例
	 */
	public Query selectSumAsDouble(@NotNull final String propertyName) {
		setSelect(criteriaBuilder.sumAsDouble(from.get(propertyName)));
		return this;
	}

	/**
	 * 查询特定属性的最大值
	 *
	 * @param propertyName 要查询最大值的属性名
	 * @return query实例
	 */
	public Query selectMax(@NotNull final String propertyName) {
		setSelect(criteriaBuilder.max(from.get(propertyName)));
		return this;
	}

	/**
	 * 查询特定属性的最小值
	 *
	 * @param propertyName 要查询最小值的属性名
	 * @return query实例
	 */
	public Query selectMin(@NotNull final String propertyName) {
		setSelect(criteriaBuilder.min(from.get(propertyName)));
		return this;
	}


	/**
	 * 查找属性值等于特定值的实体
	 *
	 * @param propertyName 要查询属性的属性名
	 * @param parameter    参数表达式
	 * @return query实例
	 */
	public Query whereEqual(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.equal(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值等于特定值的实体
	 *
	 * @param propertyName 要查询属性的属性名
	 * @param value        属性值
	 * @return query实例
	 */
	public Query whereEqual(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.equal(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 查找属性值不等于特定值的实体
	 *
	 * @param propertyName 要查询属性的属性名
	 * @param parameter    参数表达式
	 * @return query实例
	 */
	public Query whereNotEqual(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.notEqual(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值不等于特定值的实体
	 *
	 * @param propertyName 要查询属性的属性名
	 * @param value        属性值
	 * @return query实例
	 */
	public Query whereNotEqual(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.notEqual(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 查找属性值小于等于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param parameter    参数表达式
	 */
	public Query whereLessThanOrEqual(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.le(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值小于等于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param value        属性值
	 */
	public Query whereLessThanOrEqual(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.le(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 查找属性值小于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param parameter    参数表达式
	 */
	public Query whereLessThan(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.lt(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值小于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param value        属性值
	 */
	public Query whereLessThan(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.lt(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 查找属性值大于等于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param parameter    参数表达式
	 */
	public Query whereGreaterThanOrEqual(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.ge(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值大于等于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param value        属性值
	 */
	public Query whereGreaterThanOrEqual(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.ge(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 查找属性值大于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param parameter    参数表达式
	 */
	public Query whereGreaterThan(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.gt(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 查找属性值大于特定值的实体
	 *
	 * @param propertyName 属性名称
	 * @param value        属性值
	 */
	public Query whereGreaterThan(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.gt(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 或者特定属性名等于特定值的实体
	 *
	 * @param propertyName 要查询的属性的属性名
	 * @param parameter    参数表达式
	 * @return query实例
	 */
	public Query whereOr(@NotNull final List<String> propertyName, @NotNull final ParameterExpression parameter) {
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.equal(from.get(propertyName.get(0)), parameter));
		for (int i = 1; i < propertyName.size(); i++)
			predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(from.get(propertyName.get(i)), parameter));
		this.predicates.add(predicate);
		return this;
	}

	/**
	 * 或者特定属性名等于特定值的实体
	 *
	 * @param propertyName 要查询的属性的属性名
	 * @param value        属性值
	 * @return query实例
	 */
	public Query whereOr(@NotNull final List<String> propertyName, @NotNull final Object value) {
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.equal(from.get(propertyName.get(0)), makeParameter(value)));
		for (int i = 1; i < propertyName.size(); i++)
			predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(from.get(propertyName.get(i)), makeParameter(value)));
		this.predicates.add(predicate);
		return this;
	}

	/**
	 * 模糊匹配
	 *
	 * @param propertyName 属性名称
	 * @param parameter    参数表达式
	 */
	public Query whereLike(@NotNull final String propertyName, @NotNull final ParameterExpression parameter) {
		this.predicates.add(criteriaBuilder.like(from.get(propertyName), parameter));
		return this;
	}

	/**
	 * 模糊匹配
	 *
	 * @param propertyName 属性名称
	 * @param value        属性值
	 */
	public Query whereLike(@NotNull final String propertyName, @NotNull final Object value) {
		this.predicates.add(criteriaBuilder.like(from.get(propertyName), makeParameter(value)));
		return this;
	}

	/**
	 * 模糊查询,或者包含
	 *
	 * @param propertyName 要查询的特定属性的属性名
	 * @param parameter    参数表达式
	 * @return query实例
	 */
	public Query whereOrLike(@NotNull final List<String> propertyName, @NotNull final ParameterExpression parameter) {
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(from.get(propertyName.get(0)), parameter));
		for (int i = 1; i < propertyName.size(); i++)
			predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(from.get(propertyName.get(i)), parameter));
		this.predicates.add(predicate);
		return this;
	}

	/**
	 * 模糊查询,或者包含
	 *
	 * @param propertyName 要查询的特定属性的属性名
	 * @param value        参数值
	 * @return query实例
	 */
	public Query whereOrLike(@NotNull final List<String> propertyName, @NotNull final Object value) {
		Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(from.get(propertyName.get(0)), makeParameter(value)));
		for (int i = 1; i < propertyName.size(); i++)
			predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(from.get(propertyName.get(i)), makeParameter(value)));
		this.predicates.add(predicate);
		return this;
	}

	/**
	 * 查找特定字段为空的实体
	 *
	 * @param propertyName 特定属性的属性名
	 * @return query实例
	 */
	public Query whereIsNull(@NotNull final String propertyName) {
		this.predicates.add(criteriaBuilder.isNull(from.get(propertyName)));
		return this;
	}

	/**
	 * 查找特定字段非空的实体
	 *
	 * @param propertyName 特定属性的属性名
	 * @return query实例
	 */
	public Query whereIsNotNull(@NotNull final String propertyName) {
		this.predicates.add(criteriaBuilder.isNotNull(from.get(propertyName)));
		return this;
	}


	/**
	 * 时间区间查询
	 *
	 * @param propertyName 属性名称
	 * @param lo           属性起始值
	 * @param go           属性结束值
	 */
	public Query whereBetween(@NotNull final String propertyName, @NotNull final Date lo, @NotNull final Date go) {
		this.predicates.add(criteriaBuilder.between(from.get(propertyName), lo, go));
		return this;
	}

	/**
	 * 数字区间查询
	 *
	 * @param propertyName 属性名称
	 * @param lo           数字起始值
	 * @param go           数字结束值
	 */
	public Query whereBetween(@NotNull final String propertyName, @NotNull final ParameterExpression lo, @NotNull final ParameterExpression go) {
		whereGreaterThanOrEqual(propertyName, lo)
				.whereLessThanOrEqual(propertyName, go);
		return this;
	}

	/**
	 * 数字区间查询
	 *
	 * @param propertyName 属性名称
	 * @param lo           数字起始值
	 * @param go           数字结束值
	 */
	public Query whereBetween(@NotNull final String propertyName, @NotNull final Number lo, @NotNull final Number go) {
		whereGreaterThanOrEqual(propertyName, lo)
				.whereLessThanOrEqual(propertyName, go);
		return this;
	}


	/**
	 * 包含
	 *
	 * @param propertyName 属性名称
	 * @param values       参数集合
	 */
	public Query whereIn(@NotNull final String propertyName, @NotNull final List<ParameterExpression> values) {
		In in = criteriaBuilder.in(from.get(propertyName));
		values.stream().forEach(in::value);
		this.predicates.add(in);
		return this;
	}

	/**
	 * 包含
	 *
	 * @param propertyName 属性名称
	 * @param values       参数值集合
	 */
	public Query whereValueIn(@NotNull final String propertyName, @NotNull final List<Object> values) {
		In in = criteriaBuilder.in(from.get(propertyName));
		values.stream()
				.map(this::makeParameter)
				.collect(Collectors.toList())
				.stream()
				.forEach(in::value);
		this.predicates.add(in);
		return this;
	}

	/**
	 * 不包含
	 *
	 * @param propertyName 属性名称
	 * @param values       参数集合
	 */
	public Query whereNotIn(@NotNull final String propertyName, @NotNull final List<ParameterExpression> values) {
		In in = criteriaBuilder.in(from.get(propertyName));
		values.stream().forEach(in::value);
		this.predicates.add(criteriaBuilder.not(in));
		return this;
	}

	/**
	 * 不包含
	 *
	 * @param propertyName 属性名称
	 * @param values       参数集合
	 */
	public Query whereValueNotIn(@NotNull final String propertyName, @NotNull final List<Object> values) {
		In in = criteriaBuilder.in(from.get(propertyName));
		values.stream()
				.map(this::makeParameter)
				.collect(Collectors.toList())
				.stream()
				.forEach(in::value);
		this.predicates.add(criteriaBuilder.not(in));
		return this;
	}

	/**
	 * 设置分组方式
	 *
	 * @param groupBy 分组查询的属性名
	 */
	public Query groupBy(@NotNull final String groupBy) {
		this.groupBy = groupBy;
		return this;
	}

	/**
	 * 工具方法
	 */
	private boolean isNullOrEmpty(Object value) {
		if (value instanceof String) {
			return "".equals(value);
		}
		return value == null;
	}

	/**
	 * 深拷贝
	 *
	 * @return
	 */
	public Object deepClone() throws Exception {
		// 序列化
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		oos.writeObject(this);

		// 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);

		return ois.readObject();
	}

	public Class getModelClass() {
		return this.clazz;
	}

	public String getProjection() {
		return this.projection;
	}

	public void setProjection(String projection) {
		this.projection = projection;
	}

	public Class getClazz() {
		return this.clazz;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public From getFrom() {
		return from;
	}

	public List<Predicate> getPredicates() {
		return predicates;
	}

	public void setPredicates(List<Predicate> predicates) {
		this.predicates = predicates;
	}

	public CriteriaQuery getCriteriaQuery() {
		return criteriaQuery;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public Map<ParameterExpression, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<ParameterExpression, Object> parameters) {
		this.parameters = parameters;
	}

	public void setFetchModes(List<String> fetchField, List<String> fetchMode) {

	}

}