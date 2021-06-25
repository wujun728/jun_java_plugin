package com.caland.common.data;

import java.util.List;

public interface DataDao {
	
	public List<Table> listTables();

	public List<Field> listFields(String tablename);

	public List<Constraints> listConstraints(String tablename);

	public Table findTable(String tablename);
}
