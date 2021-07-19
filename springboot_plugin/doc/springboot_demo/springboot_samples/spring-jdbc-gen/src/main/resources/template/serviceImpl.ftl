package ${packageBase}.service.impl;

import ${packageBase}.model.${tableBean.tableNameCapitalized};
import ${packageBase}.dao.${tableBean.tableNameCapitalized}${classPrefix}DAO;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("${tableBean.tableNameNoDash}${classPrefix}Service")
public class ${tableBean.tableNameCapitalized}${classPrefix}ServiceImpl implements ${tableBean.tableNameCapitalized}${classPrefix}Service {
@Resource
private ${tableBean.tableNameCapitalized}${classPrefix}DAO ${tableBean.tableNameNoDash}${classPrefix}DAO;

@Override
public int insert(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.insert(${tableBean.tableNameNoDash});
}

@Override
public int save(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.save(${tableBean.tableNameNoDash});
}

@Override
public int change(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.change(${tableBean.tableNameNoDash});
}

@Override
public int saveOrUpdate(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash}) {
if (null == ${tableBean.tableNameNoDash}.getId() || 0 == ${tableBean.tableNameNoDash}.getId()) {
return this.save(${tableBean.tableNameNoDash});
} else {
return this.change(${tableBean.tableNameNoDash});
}
}

@Override
public ${tableBean.tableNameCapitalized} getById(Integer id) {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.getByPrimaryKey(id);
}

@Override
public List<${tableBean.tableNameCapitalized}> getAll() {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.getAll();
}

@Override
public int remove(Integer id) {
return ${tableBean.tableNameNoDash}${classPrefix}DAO.removeByPrimaryKey(id);
}
}
