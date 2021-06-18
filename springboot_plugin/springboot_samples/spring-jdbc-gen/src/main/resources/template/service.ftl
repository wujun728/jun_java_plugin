package ${packageBase}.service;

import ${packageBase}.model.${tableBean.tableNameCapitalized};

import java.util.List;

public interface ${tableBean.tableNameCapitalized}${classPrefix}Service {
/**
* 保存数据对象
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功操作记录数
*/
public int insert(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 保存数据对象，并获取主键id到${tableBean.tableNameNoDash}中
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功操作记录数
*/
public int save(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 更新数据对象
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功操作记录数
*/
public int change(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 保存或更新对象，判断逻辑根据主键id，为null或0，则为保存，否则为更新
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功操作记录数
*/
public int saveOrUpdate(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 获取一个数据对象
* @param id 主键
* @return 数据对象，无数据时返回null
*/
public ${tableBean.tableNameCapitalized} getById(Integer id);

/**
* 获取所有数据对象
* @return 所有数据对象，无数据时返回空记录集
*/
public List<${tableBean.tableNameCapitalized}> getAll();

/**
* 根据主键id删除对象
* @return 成功操作记录数
*/
public int remove(Integer id);
}
