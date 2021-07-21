package com.caland.common.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataMngImpl implements DataMng{
	
	@Transactional(readOnly = true)
	public List<Table> listTabels() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)
	public Table findTable(String tablename) {
		return dao.findTable(tablename);
	}

	@Transactional(readOnly = true)
	public List<Field> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Transactional(readOnly = true)
	public List<Constraints> listConstraints(String tablename) {
		return dao.listConstraints(tablename);
	}


	private DataDao dao;

	@Autowired
	public void setDao(DataDao dao) {
		this.dao = dao;
	}

}
