package com.caland.common.data;

import java.util.List;

public interface DataMng {
	
	public List<Table> listTabels();

	public Table findTable(String tablename) ;

	public List<Field> listFields(String tablename);

	public List<Constraints> listConstraints(String tablename);
}
